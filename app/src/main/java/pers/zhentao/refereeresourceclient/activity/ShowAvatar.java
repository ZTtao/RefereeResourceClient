//package pers.zhentao.refereeresourceclient.activity;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.zhangzt.refereeresource.R;
//import com.zhangzt.refereeresource.util.MyApplication;
//
//import java.io.File;
//import java.util.logging.SocketHandler;
//
//import cn.bmob.v3.datatype.BmobFile;
//import cn.bmob.v3.listener.DownloadFileListener;
//
///**
// * Created by ZhangZT on 2016/7/20 19:07.
// * E-mail: 327502540@qq.com
// * Project: RefereeResource
// */
//public class ShowAvatar extends Activity {
//
//    private ImageView imageView;
//    private RelativeLayout layout;
//    @Override
//    protected void onCreate(Bundle bundle){
//        super.onCreate(bundle);
//        setContentView(R.layout.activity_show_avatar);
//        init();
//    }
//
//    private void init(){
//        imageView = (ImageView)findViewById(R.id.img_show_avatar);
//        layout = (RelativeLayout)findViewById(R.id.ly_show_avatar);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        String objectId = getIntent().getStringExtra("objectId");
//        String url = getIntent().getStringExtra("url");
//        final File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",objectId+".jpg");
//        if(!file.exists()&& url!=null &&!url.equals("")){
//            //服务器取图片，打开我的资料界面，再删除头像，再点击头像查看大图，则出现该情况
//            BmobFile bmobFile = new BmobFile(objectId+".jpg","",url);
//            bmobFile.download(ShowAvatar.this, file, new DownloadFileListener() {
//                @Override
//                public void onSuccess(String s) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                    imageView.setImageBitmap(bitmap);
//                }
//
//                @Override
//                public void onFailure(int i, String s) {
//                    Toast.makeText(ShowAvatar.this,"头像下载失败",Toast.LENGTH_SHORT).show();
//                }
//            });
//        }else {
//            if(file.exists()) {
//                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                imageView.setImageBitmap(bitmap);
//            }else{
//                Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getContext().getResources(),R.mipmap.default_avatar);
//                imageView.setImageBitmap(bitmap);
//            }
//
//        }
//    }
//}
