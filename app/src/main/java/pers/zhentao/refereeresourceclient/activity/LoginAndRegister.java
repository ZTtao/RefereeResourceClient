package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.MyPagerAdapter;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshMainpageEvent;
import pers.zhentao.refereeresourceclient.database.RefereeResourceDB;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.globalvariable.CommonManager;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.service.UserService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;


public class LoginAndRegister extends Activity {

    private ViewPager viewPager;
    private int flag = 1;
    private String code = null;
    private Handler handle;
    private String phone_num_register = null;
    private String password_register = null;
    private Button btn_change;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        initView();
    }
    @Override
    public void finish(){
        Intent intent = new Intent();
        if(ContextUtil.getUserInstance() == null) {
            intent.putExtra("is_login", false);
            EventBus.getDefault().post(new FreshMainpageEvent());
        }
        else
            intent.putExtra("is_login", true);
        this.setResult(Common.LoginAndRegisterResultCode,intent);
        super.finish();
    }
    private void initView(){
        /**
         * 登陆/注册转换按钮监听
         */
        btn_change = (Button)findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()==0){
                    viewPager.setCurrentItem(1);
                    btn_change.setText("已有账号？");
                }
                else {
                    viewPager.setCurrentItem(0);
                    btn_change.setText("注册账号");
                }
            }
        });

        /**
         * 初始化ViewPager
         */
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        List<View> viewList = new ArrayList<>();
        View view ;
        inflater = getLayoutInflater();
        viewList.add(inflater.inflate(R.layout.item_login,null));
        /**
         * 添加手机注册界面按钮监听
         */
        view = inflater.inflate(R.layout.item_register, null);
        viewList.add(view);

        final LinearLayout ly1 = (LinearLayout)view.findViewById(R.id.ly_input);
        final LinearLayout ly2 = (LinearLayout)view.findViewById(R.id.ly_code);
        final TextView tv_tips_register = (TextView)view.findViewById(R.id.tv_tips_register);
        final TextView tv_tips_code = (TextView)view.findViewById(R.id.tv_tips_code);
        final EditText et_phone_register = (EditText)view.findViewById(R.id.et_account_register);
        final EditText et_password_register = (EditText)view.findViewById(R.id.et_password_register);
        final EditText et_code_register = (EditText)view.findViewById(R.id.et_code_register);
        tv_tips_register.setVisibility(View.GONE);
        Button btn_register = (Button)view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tips_register.setVisibility(View.GONE);
                phone_num_register = et_phone_register.getText().toString();
                password_register = et_password_register.getText().toString();
                if(TextUtils.isEmpty(phone_num_register)||TextUtils.isEmpty(password_register)){
                    tv_tips_register.setText("请填写完整的信息！");
                    tv_tips_register.setVisibility(View.VISIBLE);
                }else if(!checkPassword(password_register)){
                    tv_tips_register.setText("密码格式:6-20位数字或字母的组合!");
                    tv_tips_register.setVisibility(View.VISIBLE);
                }else{
                    Dialog dialog = new AlertDialog.Builder(LoginAndRegister.this)
                            .setTitle("确认注册？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //短信验证码功能待加入
                                    //注册成功，跳转到个人信息完善界面
                                    final User user = new User();
                                    user.setPhoneNumber(phone_num_register);
                                    user.setPassword(password_register);
                                    new UserService().saveUser(user, new SaveListener() {
                                        @Override
                                        public void onSuccess(final int userId) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(LoginAndRegister.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                                    user.setUserId(userId);
                                                    ContextUtil.setUserInstance(user);
                                                    Intent intent = new Intent(LoginAndRegister.this, PerfectInfo.class);
                                                    Common.FIRST_EDIT_INFO = true;
                                                    Common.INFO_EDIT_FLAG = 1;
                                                    //User信息存DB
                                                    RefereeResourceDB db = RefereeResourceDB.getInstance();
                                                    db.saveUser(ContextUtil.getUserInstance());
                                                    CommonManager.getInstance().initIMModule(ContextUtil.getUserInstance());
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(int errorCode, String result) {
                                            Looper.prepare();
                                            Toast.makeText(LoginAndRegister.this,result,Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                    });
//                                    user.save(LoginAndRegister.this, new SaveListener() {
//                                        @Override
//                                        public void onSuccess() {
//                                            Toast.makeText(LoginAndRegister.this, "注册成功！", Toast.LENGTH_SHORT).show();
//                                            Common.USER = commonUser;
//                                            Intent intent = new Intent(LoginAndRegister.this, PerfectInfo.class);
//                                            Common.FIRST_EDIT_INFO = true;
//                                            Common.INFO_EDIT_FLAG = 1;
//                                            //User信息存DB
//                                            RefereeResourceDB db = RefereeResourceDB.getInstance();
//                                            db.saveUser(Common.USER);
//                                            CommonManager.getInstance().initIMModule(Common.USER);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//
//                                        @Override
//                                        public void onFailure(int i, String s) {}
//                                    });
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create();
                        dialog.show();
                }
            }
        });
        viewPager.setAdapter(new MyPagerAdapter(viewList));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (viewPager.getCurrentItem() == 0) {
                    btn_change.setText("注册账号");
                } else {
                    btn_change.setText("已有账号？");
                }
            }
            @Override
            public void onPageSelected(int position) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        /**
         * 左上角关闭按钮监听
         */
        ImageButton imageButton = (ImageButton)findViewById(R.id.img_close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1)
                    finish();
                else {
                    ly1.setVisibility(View.VISIBLE);
                    ly2.setVisibility(View.GONE);
                    flag=1;
                }
            }
        });
        /**
         * 登陆界面监听
         */
        View view1 = viewList.get(0);
        Button btn_login = (Button)view1.findViewById(R.id.btn_login);
        final EditText et_acount_login = (EditText)view1.findViewById(R.id.et_account_login);
        final EditText et_password_login = (EditText)view1.findViewById(R.id.et_password_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acount = et_acount_login.getText().toString();
                String password = et_password_login.getText().toString();
                new UserService().findUser(acount, password, new FindListener<User>() {
                    @Override
                    public void onSuccess(List<User> list) {
                        if (list.size() > 0) {
                            ContextUtil.setUserInstance(list.get(0));
                            RefereeResourceDB.getInstance().saveUser(ContextUtil.getUserInstance());
                            CommonManager.getInstance().initIMModule(ContextUtil.getUserInstance());
//                            BmobQuery<Player> query1 = new BmobQuery<>();
//                            query1.addWhereEqualTo("user",Common.USER);
//                            query1.findObjects(MyApplication.getContext(), new FindListener<Player>() {
//                                @Override
//                                public void onSuccess(List<Player> list) {
//                                    if(list.size()>0)
//                                        Common.PLAYER_USER = list.get(0);
//                                }
//                                @Override
//                                public void onError(int i, String s) {}
//                            });
//                            BmobQuery<Referee> query2 = new BmobQuery<>();
//                            query2.addWhereEqualTo("user", Common.USER);
//                            query2.findObjects(MyApplication.getContext(), new FindListener<Referee>() {
//                                @Override
//                                public void onSuccess(List<Referee> list) {
//                                    if (list.size() > 0)
//                                        Common.REFEREE_USER = list.get(0);
//                                }
//
//                                @Override
//                                public void onError(int i, String s) {
//                                }
//                            });
                            finish();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginAndRegister.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int errorCode, String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginAndRegister.this,"请检查网络设置",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
//                BmobQuery<CommonUser> query = new BmobQuery<>();
//                query.addWhereEqualTo("phone_number", acount);
//                query.addWhereEqualTo("passWord",password);
//                query.findObjects(LoginAndRegister.this, new FindListener<CommonUser>() {
//                    @Override
//                    public void onSuccess(List<CommonUser> list) {
//                        if (list.size() > 0) {
//                            Common.USER = list.get(0);
//                            RefereeResourceDB.getInstance().saveUser(Common.USER);
//                            CommonManager.getInstance().initIMModule(Common.USER);
//                            BmobQuery<Player> query1 = new BmobQuery<>();
//                            query1.addWhereEqualTo("user",Common.USER);
//                            query1.findObjects(MyApplication.getContext(), new FindListener<Player>() {
//                                @Override
//                                public void onSuccess(List<Player> list) {
//                                    if(list.size()>0)
//                                        Common.PLAYER_USER = list.get(0);
//                                }
//                                @Override
//                                public void onError(int i, String s) {}
//                            });
//                            BmobQuery<Referee> query2 = new BmobQuery<>();
//                            query2.addWhereEqualTo("user", Common.USER);
//                            query2.findObjects(MyApplication.getContext(), new FindListener<Referee>() {
//                                @Override
//                                public void onSuccess(List<Referee> list) {
//                                    if (list.size() > 0)
//                                        Common.REFEREE_USER = list.get(0);
//                                }
//
//                                @Override
//                                public void onError(int i, String s) {
//                                }
//                            });
//                            finish();
//                        } else {
//                            Toast.makeText(LoginAndRegister.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    @Override
//                    public void onError(int i, String s) {
//                        Toast.makeText(LoginAndRegister.this,"请检查网络设置",Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
            });
    }

    /**
     * 检查密码
     * 6-20位数字或字母的组合
     * @return true 密码合格
     *         false 密码不合格
     */
    private boolean checkPassword(String pwd){
        if(pwd.length()<6||pwd.length()>20)return false;
        for(int i=0;i<pwd.length();i++){
            if(pwd.charAt(i)<'0'||pwd.charAt(i)>'z')return false;
            if(pwd.charAt(i)>'9'&&pwd.charAt(i)<'A')return false;
            if(pwd.charAt(i)>'Z'&&pwd.charAt(i)<'a')return false;
        }
        return true;
    }

    /**
     * 将map型转为请求参数型
     */
    private String urlEncode(Map<String,Object> data){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry i : data.entrySet()){
            try{
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * JSON解析
     */
    private Map<String,String> parseJSONWithJSONObject(String jsonData){
        Map<String,String> json_map = new HashMap<>();
        try{
                JSONObject jsonObject = new JSONObject(jsonData);
                json_map.put("error_code",jsonObject.getString("error_code"));
                json_map.put("reason",jsonObject.getString("reason"));
       }catch (Exception e){
            e.printStackTrace();
        }
        return json_map;
    }
    /**
     * 获取验证码
     */
    private String getCode(){
        StringBuilder sBuilder = new StringBuilder();
        for(int i=0;i<6;i++){
            Random random = new Random();
            sBuilder.append(random.nextInt(10));
        }
        return sBuilder.toString();
    }
    class SMSChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            SmsMessage message = null;
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                Object[] pdusObj = (Object[])bundle.get("pdus");
                for(Object p : pdusObj){
                    message = SmsMessage.createFromPdu((byte[])p);
                    String phoneNum = message.getOriginatingAddress(); //得到发信人
                    if(phoneNum.equals(Common.PHONE_NUM_SEND)){
                        String m = message.getMessageBody();
                        String c = m.substring(23, 29);
                        Bundle bun = new Bundle();
                        bun.putString("code",c);
                        Message msg = new Message();
                        msg.setData(bun);
                        msg.what = 0x100;
                        handle.sendMessage(msg);
                    }
                }
            }

        }
    }

}
