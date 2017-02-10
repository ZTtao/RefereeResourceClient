package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.globalvariable.Common;

/**
 * Created by ZhangZT on 2016/7/29 09:41.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ShowSelectedPicture extends Activity {
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_show_selected_picture);
        Button btn_send = (Button)findViewById(R.id.btn_send_show_selected_picture);
        Button btn_cancle = (Button)findViewById(R.id.btn_cancle_show_selected_picture);
        ImageView image = (ImageView)findViewById(R.id.img_show_selected_picture);
        final String path = getIntent().getStringExtra("localPath");
        final String name = getIntent().getStringExtra("imgName");
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        image.setImageBitmap(bitmap);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_cancle.setVisibility(View.VISIBLE);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("imgName",name);
                intent.putExtra("localPath",path);
                ShowSelectedPicture.this.setResult(Common.SHOW_SELECTED_PICTURE_RESULT_CODE,intent );
                finish();
            }
        });
        btn_send.setVisibility(View.VISIBLE);
    }
}
