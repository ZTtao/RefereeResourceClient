package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import pers.zhentao.refereeresourceclient.R;

/**
 * Created by ZhangZT on 2016/7/26 13:14.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ShowVideo extends Activity {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private int playSecond = 0;
    private Handler handler;
    private Runnable runnable;
    private TextView tvPlaySecond;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_show_video);
        init();
    }
    private void init(){
        tvPlaySecond = (TextView)findViewById(R.id.tv_second_show_video);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                playSecond++;
                int minute = playSecond/60;
                int second = playSecond%60;
                tvPlaySecond.setText(minute+":"+second);
                handler.postDelayed(runnable,1000);
            }
        };
        surfaceView = (SurfaceView)findViewById(R.id.surface_view_popupwindow_show_video);
        surfaceHolder = surfaceView.getHolder();
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                handler.removeCallbacks(runnable);
            }
        });
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getIntent().getStringExtra("record_file"));
            //mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.prepare();
            mediaPlayer.start();
            handler.postDelayed(runnable,1000);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void finish(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(runnable);
        super.finish();
    }
}
