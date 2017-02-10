package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.ApplyRefereeMessage;
import pers.zhentao.refereeresourceclient.bean.FindRefereeMessage;
import pers.zhentao.refereeresourceclient.listener.ReloadListener;
import pers.zhentao.refereeresourceclient.service.ApplyRefereeService;
import pers.zhentao.refereeresourceclient.service.FindRefereeService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;


/**
 * Created by ZhangZT on 2016/8/20 22:33.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FindRefereeDetail extends Activity {

    private PopupMenu popupMenu;
    private Menu menu;
    private FindRefereeMessage message;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_find_referee_detail);
        init();
    }
    private void init(){
        Intent intent = getIntent();
        message = (FindRefereeMessage)intent.getSerializableExtra("findRefereeMessage");
        new FindRefereeService().incrementReadCount(message.getId());
//        message.increment("readCount");
        new FindRefereeService().reloadMessage(message.getId(), new ReloadListener<FindRefereeMessage>() {
            @Override
            public void onSuccess(FindRefereeMessage obj) {
                message = obj;
            }
            @Override
            public void onFailure(int errorCode, String result) {

            }
        });
//        message.update(MyApplication.getContext());
        ImageButton btnBack = (ImageButton)findViewById(R.id.back_btn);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("裁判招募");
        tvTitle.setVisibility(View.VISIBLE);
        TextView tvState = (TextView)findViewById(R.id.tv_find_referee_detail_state);
        TextView tvAddress = (TextView)findViewById(R.id.tv_find_referee_detail_address);
        TextView tvTime = (TextView)findViewById(R.id.tv_find_referee_detail_time);
        TextView tvCount = (TextView)findViewById(R.id.tv_find_referee_detail_count);
        TextView tvClaim = (TextView)findViewById(R.id.tv_find_referee_detail_claim);
        TextView tvPay = (TextView)findViewById(R.id.tv_find_referee_detail_pay);
        TextView tvNote = (TextView)findViewById(R.id.tv_find_referee_detail_note);
        tvState.setText(message.getGameState());
        tvAddress.setText(message.getAddress());
//        Date date = new Date();
//        try {
//            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(message.getTime().getDate());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(message.getTime()));
        tvCount.setText(message.getRefereeCount()+"");
        tvClaim.setText(message.getRefereeClaim());
        tvPay.setText(message.getPay());
        tvNote.setText(message.getNote());
        final ImageView imgUser = (ImageView)findViewById(R.id.img_find_referee_detail_user);
        TextView tvUser = (TextView)findViewById(R.id.tv_find_referee_detail_name);
        tvUser.setText(message.getUser().getNickName());
//        Bitmap bitmap = null;
//        if(message.getUser().getAvatar()!=null&&!message.getUser().getAvatar().equals(""))
//        {
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(message.getUser().getObjectId());
//            if(bitmap == null){
//                BmobFile bmobFile = new BmobFile(message.getUser().getObjectId()+".jpg","",message.getUser().getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",message.getUser().getObjectId()+".jpg");
//                bmobFile.download(MyApplication.getContext(), file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        imgUser.setImageBitmap(BitmapFactory.decodeFile(s));
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//
//            }else
//                imgUser.setImageBitmap(bitmap);
//        }else
        imgUser.setImageResource(R.mipmap.default_avatar);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.ly_find_referee_detail_user);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FindRefereeDetail.this, PerfectInfo.class);
                intent1.putExtra("user", message.getUser());
                startActivity(intent1);
            }
        });
        ImageView imgMenu = (ImageView)findViewById(R.id.btn_right);
        imgMenu.setImageBitmap(BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(), R.mipmap.menu_plus));
        imgMenu.setVisibility(View.VISIBLE);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        popupMenu = new PopupMenu(this,imgMenu);
        menu = popupMenu.getMenu();
        if(message.getUser().getUserId().equals(ContextUtil.getUserInstance().getUserId())) {
            menu.add(Menu.NONE, Menu.FIRST + 1, 0, "查看报名");
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ly_find_referee_detail_bottom);
            linearLayout.setVisibility(View.GONE);
        }
        else
            menu.add(Menu.NONE,Menu.FIRST+0,0,"报名");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case Menu.FIRST + 0:
                        if (ContextUtil.getUserInstance().getReferee() == null) {
                            Toast.makeText(FindRefereeDetail.this, "请先完善裁判员资料信息", Toast.LENGTH_SHORT).show();
                        } else {
                            final EditText editText = new EditText(FindRefereeDetail.this);
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindRefereeDetail.this);
                            builder.setPositiveButton("报名", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ApplyRefereeMessage applyRefereeMessage = new ApplyRefereeMessage();
                                    applyRefereeMessage.setFindRefereeMessage(message);
                                    applyRefereeMessage.setRefereeUser(ContextUtil.getUserInstance());
                                    applyRefereeMessage.setReferee(ContextUtil.getUserInstance().getReferee());
                                    applyRefereeMessage.setNote(editText.getText().toString());
                                    applyRefereeMessage.setApplyDate(new Date());
                                    new ApplyRefereeService().save(applyRefereeMessage);
//                                    applyRefereeMessage.save(FindRefereeDetail.this);
                                    new FindRefereeService().incrementApplyCount(message.getId());
//                                    message.increment("applyCount");
                                    new FindRefereeService().reloadMessage(message.getId(), new ReloadListener<FindRefereeMessage>() {
                                        @Override
                                        public void onSuccess(FindRefereeMessage obj) {
                                            message = obj;
                                        }
                                        @Override
                                        public void onFailure(int errorCode, String result) {

                                        }
                                    });
//                                    message.update(MyApplication.getContext());
                                    Toast.makeText(FindRefereeDetail.this, "报名成功,请等待结果", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setTitle("备注消息")
                                    .setView(editText)
                                    .show();
                        }
                        break;
                    case Menu.FIRST + 1:
                        Intent intent1 = new Intent(FindRefereeDetail.this, ShowApply.class);
                        intent1.putExtra("findRefereeMessage", message);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ly_find_referee_detail_number);
        linearLayout.setVisibility(View.VISIBLE);
        TextView tvApply = (TextView)findViewById(R.id.tv_find_referee_detail_apply);
        tvApply.setText(message.getApplyCount()+"");
    }
}
