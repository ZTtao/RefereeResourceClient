package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.Friend;
import pers.zhentao.refereeresourceclient.bean.IMConversation;
import pers.zhentao.refereeresourceclient.bean.Player;
import pers.zhentao.refereeresourceclient.bean.Referee;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.database.RefereeResourceDB;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.globalvariable.CommonManager;
import pers.zhentao.refereeresourceclient.listener.ConversationListener;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.listener.UpdateListener;
import pers.zhentao.refereeresourceclient.service.FriendService;
import pers.zhentao.refereeresourceclient.service.PlayerService;
import pers.zhentao.refereeresourceclient.service.RefereeService;
import pers.zhentao.refereeresourceclient.service.UserService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;
import pers.zhentao.refereeresourceclient.util.IM;

/**
 * Created by ZhangZT on 2016/3/21 11:58.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class PerfectInfo extends Activity {

    private EditText et_nick_name;
    private EditText et_name;
    private EditText et_email;
    private EditText et_address;
    private EditText etPlayerHonor;
    private EditText etPlayerTeam;
    private EditText etPlayerCareer;
    private EditText etRefereeHonor;
    private EditText etRefereeCareer;
    private EditText etRefereeRank;

    private TextView tv_title;
    private TextView tv_nick_name;
    private TextView tv_name;
    private TextView tv_gender;
    private TextView tv_email;
    private TextView tv_birth_year;
    private TextView tv_province;
    private TextView tv_city;
    private TextView tv_county;
    private TextView tv_address;
    private TextView tv_year;
    private TextView tvPlayerHonor;
    private TextView tvPlayerTeam;
    private TextView tvPlayerCareer;
    private TextView tvRefereeHonor;
    private TextView tvRefereeRank;
    private TextView tvRefereeCareer;

    private Bitmap bitmapRightEdit = null;
    private ImageView btn_edit;
    private ImageView btn_save;
    private Bitmap bitmapSave = null;
    private ImageButton btn_add;
    private ImageButton btn_sub;
    private ImageButton btn_back;
    private Button btn_bottom;

    private ImageView img_avatar;

    private RadioGroup radioGroup;
    private RadioButton radioButton_male;
    private RadioButton radioButton_female;
    private CheckBox checkBoxPlayer;
    private CheckBox checkBoxReferee;

    private LinearLayout ly_year;
    private LinearLayout ly_bottom;
    private LinearLayout linearLayoutPlayer;
    private LinearLayout linearLayoutReferee;
    private LinearLayout linearLayoutIdentitySelect;

    private User user = null;

    private Spinner spinner_province;
    private Spinner spinner_city;
    private Spinner spinner_county;
    private ArrayAdapter<String> arrayAdapter_province = null;
    private ArrayAdapter<String> arrayAdapter_city = null;
    private ArrayAdapter<String> arrayAdapter_county = null;


    private String present_province = "北京市";
    private String present_city = "东城区";
    private String present_county = "";

    private boolean is_avatar_change = false;

    private PopupWindow popupWindow;

    private File file;
    private File default_avatar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.perfect_info);
    }
    @Override
    public void onResume(){
        init();
        super.onResume();
    }
    @Override
    public void finish(){
        if(Common.FIRST_EDIT_INFO){
            Toast.makeText(PerfectInfo.this,"请保存资料",Toast.LENGTH_SHORT).show();
        }else{
            super.finish();
        }
    }
    private void init(){

        ly_bottom = (LinearLayout)findViewById(R.id.ly_info_bottom);
        btn_bottom = (Button)findViewById(R.id.btn_info_add_or_send);

        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        if(user != null&& !user.getUserId().equals(ContextUtil.getUserInstance().getUserId())){
            //他人查看
            Common.INFO_EDIT_FLAG = Common.OTHER_BROWSE;
        }else {
            Common.INFO_EDIT_FLAG = Common.USER_BROWSE;
            user = ContextUtil.getUserInstance();
            btn_bottom.setText("退出登录");
            btn_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //清空数据库
                    RefereeResourceDB.getInstance().saveUser(null);
                    ContextUtil.setUserInstance(null);
                    CommonManager.getInstance().disconnectIMModule();
                    Intent intent = new Intent(PerfectInfo.this,LoginAndRegister.class);
                    startActivityForResult(intent, Common.LOGIN_AND_REGISTER_REQUEST_CODE);
                    finish();
                }
            });
            ly_bottom.setVisibility(View.VISIBLE);
        }

        tv_title = (TextView)findViewById(R.id.title_text);
        tv_title.setText("个人资料");
        btn_back = (ImageButton)findViewById(R.id.back_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back.setVisibility(View.VISIBLE);


        file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",user.getUserId()+".jpg");
        default_avatar = new File(Environment.getExternalStorageDirectory()+"/refereeResource","defaultAvatar.jpg");
        et_nick_name = (EditText)findViewById(R.id.et_perfect_info_nick_name);
        et_name = (EditText)findViewById(R.id.et_perfect_info_name);
        et_email = (EditText)findViewById(R.id.et_perfect_info_email);
        et_address = (EditText)findViewById(R.id.et_perfect_info_address);
        tv_nick_name = (TextView)findViewById(R.id.tv_perfect_info_nick_name);
        tv_name = (TextView)findViewById(R.id.tv_perfect_info_name);
        tv_email = (TextView)findViewById(R.id.tv_perfect_info_email);
        tv_gender = (TextView)findViewById(R.id.tv_perfect_info_gender);
        tv_birth_year = (TextView)findViewById(R.id.tv_perfect_info_birth_year);
        tv_province = (TextView)findViewById(R.id.tv_perfect_info_province);
        tv_city = (TextView)findViewById(R.id.tv_perfect_info_city);
        tv_county = (TextView)findViewById(R.id.tv_perfect_info_couty);
        tv_address = (TextView)findViewById(R.id.tv_perfect_info_address);
        tv_year = (TextView)findViewById(R.id.tv_year);
        tvPlayerHonor = (TextView)findViewById(R.id.tv_perfect_info_player_honor);
        tvPlayerTeam = (TextView)findViewById(R.id.tv_perfect_info_player_team);
        tvPlayerCareer = (TextView)findViewById(R.id.tv_perfect_info_player_career);
        tvRefereeHonor = (TextView)findViewById(R.id.tv_perfect_info_referee_honor);
        tvRefereeRank = (TextView)findViewById(R.id.tv_perfect_info_referee_rank);
        tvRefereeCareer = (TextView)findViewById(R.id.tv_perfect_info_referee_career);
        etPlayerCareer = (EditText)findViewById(R.id.et_perfect_info_player_career);
        etPlayerHonor = (EditText)findViewById(R.id.et_perfect_info_player_honor);
        etPlayerTeam = (EditText)findViewById(R.id.et_perfect_info_player_team);
        etRefereeRank = (EditText)findViewById(R.id.et_perfect_info_referee_rank);
        etRefereeHonor = (EditText)findViewById(R.id.et_perfect_info_referee_honor);
        etRefereeCareer = (EditText)findViewById(R.id.et_perfect_info_referee_career);

        btn_edit = (ImageView)findViewById(R.id.btn_right);
        btn_save = (ImageView)findViewById(R.id.btn_right_save);
        btn_add = (ImageButton)findViewById(R.id.img_add);
        btn_sub = (ImageButton)findViewById(R.id.img_sub);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup_gender);
        radioButton_male = (RadioButton)findViewById(R.id.male);
        radioButton_female = (RadioButton)findViewById(R.id.female);
        checkBoxPlayer = (CheckBox)findViewById(R.id.radio_player);
        checkBoxReferee = (CheckBox)findViewById(R.id.radio_referee);

        ly_year = (LinearLayout)findViewById(R.id.ly_year);
        linearLayoutPlayer = (LinearLayout)findViewById(R.id.ly_player);
        linearLayoutReferee = (LinearLayout)findViewById(R.id.ly_referee);
        linearLayoutIdentitySelect = (LinearLayout)findViewById(R.id.ly_perfect_info_identity_select);

        spinner_province = (Spinner)findViewById(R.id.spinner_perfect_info_province);
        spinner_city = (Spinner)findViewById(R.id.spinner_perfect_info_city);
        spinner_county = (Spinner)findViewById(R.id.spinner_perfect_info_couty);

        img_avatar = (ImageView)findViewById(R.id.img_info_avatar);

        //检查编辑状态
        if((Common.INFO_EDIT_FLAG==Common.USER_BROWSE && !Common.FIRST_EDIT_INFO)||Common.INFO_EDIT_FLAG==Common.OTHER_BROWSE)changeToStatus(2);
        else changeToStatus(1);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToStatus(1);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToStatus(0);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                int a = calendar.get(Calendar.YEAR);
                String year = tv_year.getText().toString();
                int y = Integer.parseInt(year);
                if(y<a){
                    y++;
                    tv_year.setText(y + "");
                    }
            }
        });
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                int a = calendar.get(Calendar.YEAR);
                String year = tv_year.getText().toString();
                int y = Integer.parseInt(year);
                if(y>(a-100)){
                    y--;
                    tv_year.setText(y+"");
                }
            }
        });
//        initAvatar();
//        img_avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Common.INFO_EDIT_FLAG==1){
//                    //修改头像
//                    View view = LayoutInflater.from(PerfectInfo.this).inflate(R.layout.item_popupwindow, null);
//                    LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.ly_item_popupwindow_top);
//                    Button btn_take_photo = (Button)view.findViewById(R.id.btn_popupwindow_take_photo);
//                    Button btn_select_photo = (Button)view.findViewById(R.id.btn_popupwindow_select_photo);
//                    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//                    linearLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            popupWindow.dismiss();
//                        }
//                    });
//                    popupWindow.setFocusable(true);
//                    popupWindow.setOutsideTouchable(true);
//                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                    popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//                    btn_take_photo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            takePhoto();
//                        }
//                    });
//                    btn_select_photo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            selectPhoto();
//                        }
//                    });
//                }else {
//                    //查看头像
//                    Intent intent = new Intent(PerfectInfo.this, ShowAvatar.class);
//                    intent.putExtra("objectId", user.getUserId());
//                    intent.putExtra("url",user.getAvatar());
//                    startActivity(intent);
//                }
//            }
//        });
    }
    private void changeToStatus(final int status){
        if(status==1) {
            //点击编辑按钮或注册后进入资料完善界面
            Common.INFO_EDIT_FLAG = 1;
            btn_bottom.setVisibility(View.GONE);
            tv_nick_name.setVisibility(View.GONE);
            tv_name.setVisibility(View.GONE);
            tv_gender.setVisibility(View.GONE);
            tv_email.setVisibility(View.GONE);
            tv_birth_year.setVisibility(View.GONE);
            tv_address.setVisibility(View.GONE);
            tv_province.setVisibility(View.GONE);
            tv_city.setVisibility(View.GONE);
            tv_county.setVisibility(View.GONE);
            //设置初始出生年份
            if(user.getBirthYear()!=0){
                tv_year.setText(user.getBirthYear()+"");
            }else {
                Date d = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                int ye = calendar.get(Calendar.YEAR);
                tv_year.setText((ye - 20) + "");
            }
            if(user.getNickName()!=null)
                et_nick_name.setText(user.getNickName());
            if(user.getName()!=null)
                et_name.setText(user.getName());
            if(user.getEMail()!=null)
                et_email.setText(user.getEMail());
            if(user.getAddress()!=null)
                et_address.setText(user.getAddress());
            if (user.getGender() == Common.MALE) {
                radioButton_male.setChecked(true);
                radioButton_female.setChecked(false);
            } else {
                radioButton_female.setChecked(true);
                radioButton_male.setChecked(false);
            }

            if(user.getProvince()!=null&&!user.getProvince().equals("")){
                present_province = user.getProvince();
                present_city = user.getCity();
                present_county = user.getCounty();
                arrayAdapter_province = new ArrayAdapter<>(PerfectInfo.this,android.R.layout.simple_list_item_1);
                for(int i=0;i<Common.province_list.length;i++){
                    arrayAdapter_province.add(Common.province_list[i]);
                }
                spinner_province.setAdapter(arrayAdapter_province);
                spinner_province.setSelection(arrayAdapter_province.getPosition(ContextUtil.getUserInstance().getProvince()));
            }
            spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    present_province = spinner_province.getItemAtPosition(position).toString();
                    arrayAdapter_city = jsonToArrayAdapter(Common.search_province.get(present_province));
//                    present_type = arrayAdapter_city.getItem(0);
                    arrayAdapter_city.remove(arrayAdapter_city.getItem(0));
                    spinner_city.setAdapter(arrayAdapter_city);
                    spinner_city.setSelection(arrayAdapter_city.getPosition(ContextUtil.getUserInstance().getCity()));

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    present_city = arrayAdapter_city.getItem(position);
                    arrayAdapter_county = jsonToArrayAdapter(getCityJson(Common.search_province.get(present_province), present_city));
                    if (arrayAdapter_county.getCount() != 0)
                        arrayAdapter_county.remove(arrayAdapter_county.getItem(0));
                    spinner_county.setAdapter(arrayAdapter_county);
                    spinner_county.setSelection(arrayAdapter_county.getPosition(present_county));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            et_nick_name.setVisibility(View.VISIBLE);
            et_name.setVisibility(View.VISIBLE);
            et_email.setVisibility(View.VISIBLE);
            et_address.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            ly_year.setVisibility(View.VISIBLE);
            spinner_province.setVisibility(View.VISIBLE);
            spinner_city.setVisibility(View.VISIBLE);
            spinner_county.setVisibility(View.VISIBLE);
            bitmapSave = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(),R.mipmap.btn_right_save);
            btn_save.setImageBitmap(bitmapSave);
            btn_save.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
            btn_edit.setImageBitmap(null);

            //球员、裁判信息设置
            tvPlayerCareer.setVisibility(View.GONE);
            tvPlayerHonor.setVisibility(View.GONE);
            tvPlayerTeam.setVisibility(View.GONE);
            tvRefereeCareer.setVisibility(View.GONE);
            tvRefereeHonor.setVisibility(View.GONE);
            tvRefereeRank.setVisibility(View.GONE);
            if(ContextUtil.getUserInstance().getPlayer() != null){
                etPlayerTeam.setText(ContextUtil.getUserInstance().getPlayer().getTeam());
                etPlayerCareer.setText(ContextUtil.getUserInstance().getPlayer().getExperience());
                etPlayerHonor.setText(ContextUtil.getUserInstance().getPlayer().getHonor());
                checkBoxPlayer.setChecked(true);
                linearLayoutPlayer.setVisibility(View.VISIBLE);
            }
            if(ContextUtil.getUserInstance().getReferee() != null){
                etRefereeHonor.setText(ContextUtil.getUserInstance().getReferee().getHonor());
                etRefereeRank.setText(ContextUtil.getUserInstance().getReferee().getRank());
                etRefereeCareer.setText(ContextUtil.getUserInstance().getReferee().getExperience());
                checkBoxReferee.setChecked(true);
                linearLayoutReferee.setVisibility(View.VISIBLE);
            }
            etPlayerHonor.setVisibility(View.VISIBLE);
            etPlayerCareer.setVisibility(View.VISIBLE);
            etPlayerTeam.setVisibility(View.VISIBLE);
            etRefereeCareer.setVisibility(View.VISIBLE);
            etRefereeRank.setVisibility(View.VISIBLE);
            etRefereeHonor.setVisibility(View.VISIBLE);

            linearLayoutIdentitySelect.setVisibility(View.VISIBLE);
            checkBoxPlayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        linearLayoutPlayer.setVisibility(View.VISIBLE);
                    } else {
                        linearLayoutPlayer.setVisibility(View.GONE);
                        etPlayerHonor.setText("");
                        etPlayerTeam.setText("");
                        etPlayerCareer.setText("");
                    }
                }
            });
            checkBoxReferee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        linearLayoutReferee.setVisibility(View.VISIBLE);
                    } else {
                        linearLayoutReferee.setVisibility(View.GONE);
                        etRefereeCareer.setText("");
                        etRefereeRank.setText("");
                        etRefereeHonor.setText("");
                    }
                }
            });

        }else if(status == 0) {
            //点击保存按钮
            if (et_nick_name.getText().toString().equals("")) {
                Toast.makeText(PerfectInfo.this, "请填写昵称", Toast.LENGTH_SHORT).show();
            } else {
                btn_bottom.setVisibility(View.VISIBLE);
                user.setNickName(et_nick_name.getText().toString());
                user.setName(et_name.getText().toString());
                if (radioGroup.getCheckedRadioButtonId() == R.id.male)
                    user.setGender(Common.MALE);
                else
                    user.setGender(Common.FEMALE);
                user.setEMail(et_email.getText().toString());
                user.setBirthYear(Integer.parseInt(tv_year.getText().toString()));
                user.setAddress(et_address.getText().toString());
                user.setProvince(spinner_province.getSelectedItem().toString());
                user.setCity(spinner_city.getSelectedItem().toString());
                if (spinner_county.getSelectedItem() != null)
                    user.setCounty(spinner_county.getSelectedItem().toString());
                else
                    user.setCounty("");

                tv_nick_name.setText(user.getNickName());
                tv_name.setText(user.getName());
                if (user.getGender() == Common.MALE)
                    tv_gender.setText("男");
                else
                    tv_gender.setText("女");
                tv_email.setText(user.getEMail());
                tv_birth_year.setText(user.getBirthYear() + "");
                tv_address.setText(user.getAddress());
                tv_province.setText(user.getProvince());
                tv_city.setText(user.getCity());
                tv_county.setText(user.getCounty());


                et_nick_name.setVisibility(View.GONE);
                et_name.setVisibility(View.GONE);
                et_email.setVisibility(View.GONE);
                et_address.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
                ly_year.setVisibility(View.GONE);
                spinner_province.setVisibility(View.GONE);
                spinner_city.setVisibility(View.GONE);
                spinner_county.setVisibility(View.GONE);
//                final BmobFile bmobFile;
//                if (is_avatar_change) {
//                    //已更改头像
//                    bmobFile = new BmobFile(file);
//                    bmobFile.upload(PerfectInfo.this, new UploadFileListener() {
//                        @Override
//                        public void onSuccess() {
//                            user.setAvatar(bmobFile.getUrl());
//                            user.update(PerfectInfo.this, new UpdateListener() {
//                                @Override
//                                public void onSuccess() {
//                                    RefereeResourceDB db = RefereeResourceDB.getInstance();
//                                    db.saveUser(user);
//                                    Toast.makeText(PerfectInfo.this, "保存成功！", Toast.LENGTH_SHORT).show();
//                                    Common.USER = user;
//                                }
//
//                                @Override
//                                public void onFailure(int i, String s) {
//                                    Toast.makeText(PerfectInfo.this, "保存失败！" + s, Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//
//                        }
//                    });
//                } else {
                //未更改头像

//                    user.update(PerfectInfo.this, new UpdateListener() {
//                        @Override
//                        public void onSuccess() {
//                            RefereeResourceDB db = RefereeResourceDB.getInstance();
//                            db.saveUser(user);
//                            Toast.makeText(PerfectInfo.this, "保存成功！", Toast.LENGTH_SHORT).show();
//                            Common.USER = user;
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            Toast.makeText(PerfectInfo.this, "保存失败！" + s, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }


                tv_nick_name.setVisibility(View.VISIBLE);
                tv_name.setVisibility(View.VISIBLE);
                tv_gender.setVisibility(View.VISIBLE);
                tv_email.setVisibility(View.VISIBLE);
                tv_birth_year.setVisibility(View.VISIBLE);
                tv_address.setVisibility(View.VISIBLE);
                tv_province.setVisibility(View.VISIBLE);
                tv_city.setVisibility(View.VISIBLE);
                tv_county.setVisibility(View.VISIBLE);

                btn_save.setVisibility(View.GONE);
                btn_save.setImageBitmap(null);
                bitmapSave.recycle();
                bitmapSave = null;
                bitmapRightEdit = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(), R.mipmap.btn_right_edit);
                btn_edit.setImageBitmap(bitmapRightEdit);
                btn_edit.setVisibility(View.VISIBLE);
                if (Common.FIRST_EDIT_INFO) {
                    Intent intent = new Intent(PerfectInfo.this, MainPage.class);
                    startActivity(intent);
                    PerfectInfo.this.onDestroy();
                    Common.FIRST_EDIT_INFO = false;
                }
                Common.INFO_EDIT_FLAG = 0;

            //球员、裁判信息处理
            linearLayoutIdentitySelect.setVisibility(View.GONE);
            if (checkBoxPlayer.isChecked()) {
                final Player player;
                if (ContextUtil.getUserInstance().getPlayer() == null) {
                    player = new Player();
                    player.setExperience(etPlayerCareer.getText().toString());
                    player.setHonor(etPlayerHonor.getText().toString());
                    player.setTeam(etPlayerTeam.getText().toString());
                    player.setCreateTime(new Date());
                    new PlayerService().savePlayer(ContextUtil.getUserInstance().getUserId(), player, new SaveListener() {
                        @Override
                        public void onSuccess(int id) {
                            player.setPlayerId(id);
                            ContextUtil.getUserInstance().setPlayer(player);
                        }

                        @Override
                        public void onFailure(int errorCode, String result) {

                        }
                    });
//                    player.save(MyApplication.getContext());
                } else {
                    player = ContextUtil.getUserInstance().getPlayer();
                    player.setExperience(etPlayerCareer.getText().toString());
                    player.setHonor(etPlayerHonor.getText().toString());
                    player.setTeam(etPlayerTeam.getText().toString());
                    new PlayerService().updatePlayer(player);
                    ContextUtil.getUserInstance().setPlayer(player);
//                    player.update(MyApplication.getContext());
                }
                tvPlayerCareer.setText(player.getExperience());
                tvPlayerHonor.setText(player.getHonor());
                tvPlayerTeam.setText(player.getTeam());
                etPlayerCareer.setVisibility(View.GONE);
                etPlayerTeam.setVisibility(View.GONE);
                etPlayerHonor.setVisibility(View.GONE);
                tvPlayerHonor.setVisibility(View.VISIBLE);
                tvPlayerTeam.setVisibility(View.VISIBLE);
                tvPlayerCareer.setVisibility(View.VISIBLE);
            } else {
                if (ContextUtil.getUserInstance().getPlayer() != null) {
                    new PlayerService().deletePlayer(ContextUtil.getUserInstance().getPlayer());
//                    Common.PLAYER_USER.delete(MyApplication.getContext());
                    ContextUtil.getUserInstance().setPlayer(null);
                }
            }
            if (checkBoxReferee.isChecked()) {
                final Referee referee;
                if (ContextUtil.getUserInstance().getReferee() == null) {
                    referee = new Referee();
                    referee.setExperience(etRefereeCareer.getText().toString());
                    referee.setHonor(etRefereeHonor.getText().toString());
                    referee.setRank(etRefereeRank.getText().toString());
                    new RefereeService().saveReferee(ContextUtil.getUserInstance().getUserId(), referee, new SaveListener() {
                        @Override
                        public void onSuccess(int id) {
                            referee.setRefereeId(id);
                            ContextUtil.getUserInstance().setReferee(referee);
                        }

                        @Override
                        public void onFailure(int errorCode, String result) {

                        }
                    });
//                    referee.save(MyApplication.getContext());
                } else {
                    referee = ContextUtil.getUserInstance().getReferee();
                    referee.setExperience(etRefereeCareer.getText().toString());
                    referee.setHonor(etRefereeHonor.getText().toString());
                    referee.setRank(etRefereeRank.getText().toString());
                    new RefereeService().updateReferee(referee);
                    ContextUtil.getUserInstance().setReferee(referee);
//                    referee.update(MyApplication.getContext());
                }
                tvRefereeCareer.setText(referee.getExperience());
                tvRefereeRank.setText(referee.getRank());
                tvRefereeHonor.setText(referee.getHonor());
                etRefereeHonor.setVisibility(View.GONE);
                etRefereeRank.setVisibility(View.GONE);
                etRefereeCareer.setVisibility(View.GONE);
                tvRefereeHonor.setVisibility(View.VISIBLE);
                tvRefereeCareer.setVisibility(View.VISIBLE);
                tvRefereeRank.setVisibility(View.VISIBLE);
            } else {
                if (ContextUtil.getUserInstance().getReferee() != null) {
                    new RefereeService().deleteReferee(ContextUtil.getUserInstance().getReferee());
//                    Common.REFEREE_USER.delete(MyApplication.getContext());
//                    Common.REFEREE_USER = null;
                    ContextUtil.getUserInstance().setReferee(null);
                }
            }
                new UserService().updateUser(user, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RefereeResourceDB db = RefereeResourceDB.getInstance();
                                db.saveUser(user);
                                Toast.makeText(PerfectInfo.this, "保存成功！", Toast.LENGTH_SHORT).show();
                                ContextUtil.setUserInstance(user);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int errorCode, final String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PerfectInfo.this, "保存失败！" + result, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
        }
        }else{
            //进入资料界面浏览
            tv_nick_name.setText(user.getNickName());
            tv_name.setText(user.getName());
            if(user.getGender()==Common.MALE)
                tv_gender.setText("男");
            else
                tv_gender.setText("女");
            tv_email.setText(user.getEMail());
            tv_birth_year.setText(user.getBirthYear()+"");
            tv_province.setText(user.getProvince());
            tv_city.setText(user.getCity());
            tv_county.setText(user.getCounty());
            tv_address.setText(user.getAddress());

            if(Common.INFO_EDIT_FLAG == Common.USER_BROWSE) {
                bitmapRightEdit = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(),R.mipmap.btn_right_edit);
                btn_edit.setImageBitmap(bitmapRightEdit);
                btn_edit.setVisibility(View.VISIBLE);
            }
            else if(Common.INFO_EDIT_FLAG == Common.OTHER_BROWSE){
                new FriendService().find(" where (user_id=" + ContextUtil.getUserInstance().getUserId() + " and friend_id=" + user.getUserId() + ") or (user_id=" + user.getUserId() + " and friend_id=" + ContextUtil.getUserInstance().getUserId() + ")", new FindListener<Friend>() {
                    @Override
                    public void onSuccess(final List<Friend> list) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(list.size()>0){
                                    btn_bottom.setText("发消息");
                                    btn_bottom.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            IM.getInstance().startConversation(user, new ConversationListener() {
                                                @Override
                                                public void done(final IMConversation conversation, final Exception e) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if(e == null){
                                                                Intent intent = new Intent(PerfectInfo.this,Chat.class);
                                                                intent.putExtra("conversation",conversation);
                                                                startActivity(intent);
                                                            }else {
                                                                Toast.makeText(PerfectInfo.this,"发起会话失败",Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
//                                            final BmobIMUserInfo info = new BmobIMUserInfo(user.getUserId(),user.getNickName(),user.getAvatar());
//                                            BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
//                                                @Override
//                                                public void done(BmobIMConversation bmobIMConversation, BmobException e) {
//                                                    if(e == null){
//                                                        Intent intent = new Intent(PerfectInfo.this,Chat.class);
//                                                        intent.putExtra("conversation",bmobIMConversation);
//                                                        startActivity(intent);
//                                                    }else {
//                                                        Toast.makeText(PerfectInfo.this,"发起会话失败",Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
                                        }
                                    });
                                }else{
                                    btn_bottom.setText("加好友");
                                    btn_bottom.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(PerfectInfo.this,SendValidation.class);
                                            intent.putExtra("user",user);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                ly_bottom.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onError(int errorCode, String result) {

                    }
                });
//                BmobQuery<Friend> query = new BmobQuery<>();
//                String sql = "select * from Friend where (user=pointer('User','"+Common.USER.getObjectId()+"') and friend=pointer('User','"+user.getObjectId()+"')) or (user=pointer('User','"+user.getObjectId()+"') and friend=pointer('User','"+Common.USER.getObjectId()+"'))";
//                query.setSQL(sql);
//                query.doSQLQuery(PerfectInfo.this, new SQLQueryListener<Friend>() {
//                    @Override
//                    public void done(BmobQueryResult<Friend> bmobQueryResult, BmobException e) {
//                        if (e == null) {
//                            if(bmobQueryResult.getResults().size()>0){
//                                btn_bottom.setText("发消息");
//                                btn_bottom.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    final BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(),user.getNickName(),user.getAvatar());
//                                    BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
//                                        @Override
//                                        public void done(BmobIMConversation bmobIMConversation, BmobException e) {
//                                            if(e == null){
//                                                Intent intent = new Intent(PerfectInfo.this,Chat.class);
//                                                intent.putExtra("conversation",bmobIMConversation);
//                                                startActivity(intent);
//                                            }else {
//                                                Toast.makeText(PerfectInfo.this,"发起会话失败",Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                }
//                            });
//                            }else{
//                                btn_bottom.setText("加好友");
//                                btn_bottom.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(PerfectInfo.this,SendValidation.class);
//                                        intent.putExtra("user",user);
//                                        startActivity(intent);
//                                    }
//                                });
//                            }
//                            ly_bottom.setVisibility(View.VISIBLE);
//                        } else {
//                            e.printStackTrace();
//                        }
//                    }
//                });
            }

            //球员、裁判信息处理
            if(ContextUtil.getUserInstance().getPlayer() != null){
                linearLayoutPlayer.setVisibility(View.VISIBLE);
                Player player = ContextUtil.getUserInstance().getPlayer();
                tvPlayerCareer.setText(player.getExperience());
                tvPlayerTeam.setText(player.getTeam());
                tvPlayerHonor.setText(player.getHonor());
                tvPlayerHonor.setVisibility(View.VISIBLE);
                tvPlayerTeam.setVisibility(View.VISIBLE);
                tvPlayerCareer.setVisibility(View.VISIBLE);
            }
            if(ContextUtil.getUserInstance().getReferee() != null){
                linearLayoutReferee.setVisibility(View.VISIBLE);
                Referee referee = ContextUtil.getUserInstance().getReferee();
                tvRefereeCareer.setText(referee.getExperience());
                tvRefereeRank.setText(referee.getRank());
                tvRefereeHonor.setText(referee.getHonor());
                tvRefereeHonor.setVisibility(View.VISIBLE);
                tvRefereeCareer.setVisibility(View.VISIBLE);
                tvRefereeRank.setVisibility(View.VISIBLE);
            }
        }
    }
    private ArrayAdapter<String> jsonToArrayAdapter(String data){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PerfectInfo.this,android.R.layout.simple_list_item_1);
        try {
            JSONObject jsonObject = new JSONObject(data);
            int type = jsonObject.getInt("type");
            arrayAdapter.add(type+"");
            JSONArray jsonArray = jsonObject.getJSONArray("sub");
            for(int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                arrayAdapter.add(jsonObject.getString("name"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return arrayAdapter;
    }
    private String getCityJson(String data,String city){
        String cityJson = null;
        try{
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("sub");
            for(int i=0;i<jsonArray.length();i++){
                if(jsonArray.getJSONObject(i).getString("name").equals(city)){
                    cityJson = jsonArray.getJSONObject(i).toString();
                    return cityJson;
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return cityJson;
    }
    private void takePhoto(){
        String SDState = Environment.getExternalStorageState();
        if(SDState.equals(Environment.MEDIA_MOUNTED)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(Environment.getExternalStorageDirectory()+File.separator+"refereeResource"+File.separator);
            if(!f.exists()||!f.isDirectory())
                f.mkdir();
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
            startActivityForResult(intent, Common.TAKE_PHOTO_REQUEST_CODE);
        }else {
            Toast.makeText(PerfectInfo.this,"内存卡不存在",Toast.LENGTH_SHORT).show();
        }
    }
    private void selectPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent,Common.SELECT_PHOTO_REQUEST_CODE);

    }
    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent intent){
        switch (requestCode){
            case Common.TAKE_PHOTO_REQUEST_CODE:
                try {
                    Uri uri = Uri.fromFile(file);
                    startPhotoZoom(uri);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case Common.SELECT_PHOTO_REQUEST_CODE:
                try{
                    startPhotoZoom(intent.getData());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case Common.CUT_PHOTO_REQUEST_CODE:
                if(intent!=null){
                    showPhoto(intent);
                    popupWindow.dismiss();
                }
                break;
            case Common.LOGIN_AND_REGISTER_REQUEST_CODE:
                if(intent.getBooleanExtra("is_login",false)){
                    //登录成功
                 }else {
                    //登录失败
                 this.finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
    private void startPhotoZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Common.CUT_PHOTO_REQUEST_CODE);
    }
    private void showPhoto(Intent intent){
        Bundle bundle = intent.getExtras();
        Bitmap photo = bundle.getParcelable("data");
        try {
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
            photo.compress(Bitmap.CompressFormat.JPEG,100,output);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        img_avatar.setImageBitmap(photo);
        is_avatar_change = true;
    }
    private void initAvatar(){
//        if(user.getAvatar()!=null&&!user.getAvatar().equals("")){
//            //头像已设置
//            if(!file.exists()){
//                //本地头像不存在
//                BmobFile bmobFile = new BmobFile(user.getObjectId()+".jpg","",user.getAvatar());
//                //BmobFile bmobFile = new BmobFile(user.getObjectId()+".jpg",null,)
//                bmobFile.download(PerfectInfo.this, file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                        img_avatar.setImageBitmap(bitmap);
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        Toast.makeText(PerfectInfo.this,"头像下载失败",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }else {
//                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                img_avatar.setImageBitmap(bitmap);
//            }
//        }


    }
}
