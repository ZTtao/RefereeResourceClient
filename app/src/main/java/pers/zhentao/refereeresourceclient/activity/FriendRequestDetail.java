package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.AddFriendRequest;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshFriendRequestListEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.globalvariable.CommonManager;
import pers.zhentao.refereeresourceclient.service.FriendRequestService;

/**
 * Created by ZhangZT on 2016/7/22 10:10.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FriendRequestDetail extends Activity {

    private RelativeLayout ly_user;
    private TextView tv_content;
    private TextView tv_status;
    private TextView tv_user;
    private ImageView img_avatar;
    private Button btn_refuse;
    private Button btn_agree;
    private AddFriendRequest request;
    private ImageButton btn_back;
    private int position;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_friend_request_detail);
        init();
    }
    private void init(){

        request = (AddFriendRequest)getIntent().getSerializableExtra("request");
        position = getIntent().getIntExtra("position",0);
        ly_user = (RelativeLayout)findViewById(R.id.ly_user_friend_request_detail);
        tv_content = (TextView)findViewById(R.id.tv_content_friend_request_detail);
        tv_status = (TextView)findViewById(R.id.tv_status_friend_request_detail);
        btn_refuse = (Button)findViewById(R.id.btn_refuse_friend_request_detail);
        btn_agree = (Button)findViewById(R.id.btn_agree_friend_request_detail);
        tv_user = (TextView)findViewById(R.id.tv_name_friend_request_detail);
        img_avatar = (ImageView)findViewById(R.id.img_friend_request_detail);

//        Bitmap bitmap = null;
//        if(request.getUser_send().getAvatar()!=null&&!request.getUser_send().getAvatar().equals(""))
//        {
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(request.getUser_send().getObjectId());
//            if(bitmap == null){
//                BmobFile bmobFile = new BmobFile(request.getUser_send().getObjectId()+".jpg","",request.getUser_send().getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",request.getUser_send().getObjectId()+".jpg");
//                bmobFile.download(FriendRequestDetail.this, file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        img_avatar.setImageBitmap(BitmapFactory.decodeFile(s));
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//
//            }else
//                img_avatar.setImageBitmap(bitmap);
//        }else
        img_avatar.setImageResource(R.mipmap.default_avatar);


        btn_back = (ImageButton)findViewById(R.id.back_btn);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_user.setText(request.getUser_send().getNickName());
        ly_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendRequestDetail.this,PerfectInfo.class);
                intent.putExtra("user",request.getUser_send());
                Common.INFO_EDIT_FLAG = Common.OTHER_BROWSE;
                startActivity(intent);
            }
        });
        tv_content.setText(request.getNote());

        if(request.getStatus().equals(Common.MSG_ADDED)){
            //已添加
            tv_status.setText("已同意该申请");
            tv_status.setVisibility(View.VISIBLE);
        }else if(request.getStatus().equals(Common.MSG_REJECTED)){
            //已拒绝
            tv_status.setText("已拒绝该申请");
            tv_status.setVisibility(View.VISIBLE);
        }else {
            btn_refuse.setVisibility(View.VISIBLE);
            btn_agree.setVisibility(View.VISIBLE);
            btn_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonManager.getInstance().sendAddFriendResponseAgreeMessage(FriendRequestDetail.this, request.getUser_send());
                    btn_agree.setVisibility(View.GONE);
                    btn_refuse.setVisibility(View.GONE);
                    tv_status.setText("已同意该申请");
                    tv_status.setVisibility(View.VISIBLE);
                    request.setStatus(Common.MSG_ADDED);
                    new FriendRequestService().updateAddRequest(request);
//                    request.update(FriendRequestDetail.this);
                    EventBus.getDefault().post(new FreshFriendRequestListEvent(position,request));
                }
            });
            btn_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonManager.getInstance().sendAddFriendResponseRefuseMessage(FriendRequestDetail.this,request.getUser_send());
                    btn_agree.setVisibility(View.GONE);
                    btn_refuse.setVisibility(View.GONE);
                    tv_status.setText("拒绝该申请");
                    tv_status.setVisibility(View.VISIBLE);
                    request.setStatus(Common.MSG_REJECTED);
                    new FriendRequestService().updateAddRequest(request);
//                    request.update(FriendRequestDetail.this);
                    EventBus.getDefault().post(new FreshFriendRequestListEvent(position, request));
                }
            });
        }
    }
}
