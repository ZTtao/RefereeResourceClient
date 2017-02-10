package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.Post;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.service.PostService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;


/**
 * Created by ZhangZT on 2016/7/7 19:10.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class NewPost extends Activity{

    private ImageButton btn_back;
    private ImageView btn_right_publish;

    private EditText et_title;
    private EditText et_content;

    private Post post;

    private int publish_flag = 0;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.new_post);
        checkUser();
    }
    @Override
    public void finish(){
        btn_right_publish.setImageBitmap(null);
        bitmap.recycle();
        bitmap = null;
        Intent intent = new Intent();
        if(publish_flag==0){
            //未发布
            intent.putExtra("is_publish",false);
        }else if(publish_flag==1){
            //player发布
            intent.putExtra("is_publish",true);
            intent.putExtra("type","player");
        }else {
            //referee发布
            intent.putExtra("is_publish",true);
            intent.putExtra("type","referee");
        }
        this.setResult(Common.NewPostResultCode,intent);
        super.finish();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode == 0){
            if(resultCode==Common.LoginAndRegisterResultCode){
                if(intent.getBooleanExtra("is_login",false)){
                    //登录成功
                    init();
                }else {
                    //登录失败
                    this.finish();
                }

            }
        }
    }
    private void checkUser(){
        if(ContextUtil.getUserInstance() == null){
            Toast.makeText(NewPost.this,"请登录",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewPost.this,LoginAndRegister.class);
            startActivityForResult(intent,0);
        }else{
            init();
        }
    }
    private void init(){
        btn_back = (ImageButton)findViewById(R.id.back_btn);
        btn_right_publish = (ImageView)findViewById(R.id.btn_right_publish);
        et_title = (EditText)findViewById(R.id.et_new_post_title);
        et_content = (EditText)findViewById(R.id.et_new_post_content);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        bitmap = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(),R.mipmap.btn_right_publish,options);
        btn_right_publish.setImageBitmap(bitmap);
        btn_right_publish.setVisibility(View.VISIBLE);
        btn_right_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTitle(et_title.getText().toString())&&checkContent(et_content.getText().toString())) {
                    post = new Post(et_title.getText().toString(), et_content.getText().toString(), ContextUtil.getUserInstance(), new Date(), getIntent().getIntExtra("currentFragment", 0));
                    new PostService().savePost(post, new SaveListener() {
                        @Override
                        public void onSuccess(int id) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NewPost.this, "发布成功", Toast.LENGTH_SHORT).show();
                                    publish_flag = post.getType();
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailure(int errorCode, String result) {
                            Toast.makeText(NewPost.this, "发布失败" + result, Toast.LENGTH_SHORT).show();
                        }
                    });
//                    post.save(NewPost.this, new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            Toast.makeText(NewPost.this, "发布成功", Toast.LENGTH_SHORT).show();
//                            publish_flag = post.getType();
//                            finish();
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            Toast.makeText(NewPost.this, "发布失败" + s, Toast.LENGTH_SHORT).show();
//                        }
//                    });

                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back.setVisibility(View.VISIBLE);

    }

    private boolean checkTitle(String title){
        if(title.length()>30||title.length()<2){
            Toast.makeText(NewPost.this,"标题字数:2~30",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean checkContent(String content){
        if(content.length()<15){
            Toast.makeText(NewPost.this,"内容字数:不小于15字",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
