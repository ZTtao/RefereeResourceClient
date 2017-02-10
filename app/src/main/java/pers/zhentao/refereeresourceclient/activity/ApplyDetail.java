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

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.ApplyRefereeMessage;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.UpdateListener;
import pers.zhentao.refereeresourceclient.service.ApplyRefereeService;

/**
 * Created by ZhangZT on 2016/8/21 14:31.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ApplyDetail extends Activity{

    private ApplyRefereeMessage message;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_apply_detail);
        init();
    }
    private void init(){
        message = (ApplyRefereeMessage)getIntent().getSerializableExtra("applyRefereeMessage");
        ImageButton btnBack = (ImageButton)findViewById(R.id.back_btn);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("报名详情");
        tvTitle.setVisibility(View.VISIBLE);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.ly_apply_detail);
        final ImageView imgUser = (ImageView)findViewById(R.id.img_apply_detail);
        TextView tvUser = (TextView)findViewById(R.id.tv_name_apply_detail);
        TextView tvNote = (TextView)findViewById(R.id.tv_content_apply_detail);
        final TextView tvStatus = (TextView)findViewById(R.id.tv_status_apply_detail);
        final Button btnAccept = (Button)findViewById(R.id.btn_agree_apply_detail);
        final Button btnReject = (Button)findViewById(R.id.btn_refuse_apply_detail);
        switch (message.getStatus()){
            case Common.APPLY_WAIT:
                btnAccept.setVisibility(View.VISIBLE);
                btnReject.setVisibility(View.VISIBLE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        message.setStatus(Common.APPLY_ACCEPT);
                        new ApplyRefereeService().update(message, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                tvStatus.setText("已接受");
                                tvStatus.setVisibility(View.VISIBLE);
                                btnAccept.setVisibility(View.GONE);
                                btnReject.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    }
                });
                btnReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        message.setStatus(Common.APPLY_REJECT);
                        new ApplyRefereeService().update(message, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                tvStatus.setText("已拒绝");
                                tvStatus.setVisibility(View.VISIBLE);
                                btnAccept.setVisibility(View.GONE);
                                btnReject.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    }
                });
                break;
            case Common.APPLY_ACCEPT:
                tvStatus.setText("已接受");
                tvStatus.setVisibility(View.VISIBLE);
                break;
            case Common.APPLY_REJECT:
                tvStatus.setText("已拒绝");
                tvStatus.setVisibility(View.VISIBLE);
                break;
        }
        tvUser.setText(message.getRefereeUser().getNickName());
        tvNote.setText(message.getNote());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplyDetail.this,PerfectInfo.class);
                intent.putExtra("user",message.getRefereeUser());
                startActivity(intent);
            }
        });
//        Bitmap bitmap = null;
//        if(message.getRefereeUser().getAvatar()!=null&&!message.getRefereeUser().getAvatar().equals(""))
//        {
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(message.getRefereeUser().getObjectId());
//            if(bitmap == null){
//                BmobFile bmobFile = new BmobFile(message.getRefereeUser().getObjectId()+".jpg","",message.getRefereeUser().getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",message.getRefereeUser().getObjectId()+".jpg");
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
    }
}
