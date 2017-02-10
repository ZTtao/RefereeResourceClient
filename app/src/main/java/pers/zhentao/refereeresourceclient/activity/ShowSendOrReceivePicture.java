package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import pers.zhentao.refereeresourceclient.R;

/**
 * Created by ZhangZT on 2016/7/29 10:43.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ShowSendOrReceivePicture extends Activity {

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_show_selected_picture);
        ImageView image = (ImageView)findViewById(R.id.img_show_selected_picture);
        String localPath = getIntent().getStringExtra("localPath");
//        image.setBackgroundDrawable(Drawable.createFromPath(localPath));
        image.setImageBitmap(BitmapFactory.decodeFile(localPath));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
