package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/25 10:19.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class RecordVideo extends Activity {

    private ImageView btn_back;
    private ImageView btn_change_camera;
    private SurfaceView videoView;
    private ImageView btn_record;
    private ImageView btn_send;
    private ImageView btn_cancel;
    private TextView tvRecordTime;
    private int record_second = 0;
    private Handler handler = null;
    private Runnable runnable = null;
    private Boolean is_recording = false;
    private Boolean is_playing = false;
    private Boolean had_record = false;
    private File record_video_file = null;
    private String record_video_file_name = null;
    private SurfaceHolder surfaceHolder = null;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;
    private Boolean is_send = false;
    private MediaPlayer mediaPlayer = null;
//    private Timer timer = null;
//    private TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_record_video);
        init();
    }
    @Override
    public void finish(){
        Intent intent = new Intent();
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        if(mediaRecorder != null){
            mediaRecorder.setOnInfoListener(null);
            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.setPreviewDisplay(null);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if(is_playing||is_recording)
            handler.removeCallbacks(runnable);
        if(!is_send){

            if(record_video_file.exists())record_video_file.delete();
            intent.putExtra("is_send",false);
        }else {
            intent.putExtra("is_send",true);
            intent.putExtra("record_file_name",record_video_file_name);
        }
        RecordVideo.this.setResult(Common.VIDEO_RECORD_RESULT_CODE,intent);
        super.finish();
    }
    private void init(){

        tvRecordTime = (TextView)findViewById(R.id.tv_second_video_record);
        btn_back = (ImageView)findViewById(R.id.img_btn_back_video_record);
        btn_change_camera = (ImageView)findViewById(R.id.img_btn_change_camera_video_record);
        videoView = (SurfaceView)findViewById(R.id.video_view_video_record);
        btn_record = (ImageView)findViewById(R.id.img_btn_record_video_record);
        btn_send = (ImageView)findViewById(R.id.img_btn_send_video_record);
        btn_cancel = (ImageView)findViewById(R.id.img_btn_cancle_video_record);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                record_second++;
                int minute = record_second / 60;
                int second = record_second % 60;
                tvRecordTime.setText(minute+":"+second);
                handler.postDelayed(this,1000);
            }
        };
        final Calendar calendar = Calendar.getInstance();
        record_video_file_name = ContextUtil.getUserInstance().getUserId()+calendar.get(Calendar.YEAR)+calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH)+calendar.get(Calendar.HOUR_OF_DAY)+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND)+".3gp";
        File f = new File(Environment.getExternalStorageDirectory()+File.separator+"refereeResource"+File.separator+"video"+File.separator);
        if(!f.exists()||!f.isDirectory())
            f.mkdir();
        record_video_file = new File(Environment.getExternalStorageDirectory()+"/refereeResource/video",record_video_file_name);

        //开始camera预览
        try {
            camera = Camera.open();
            surfaceHolder = videoView.getHolder();
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        camera.setPreviewDisplay(surfaceHolder);
                        camera.startPreview();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    System.out.print(format);
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!is_recording&&!had_record&&!is_playing){
                    //开始录制
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
                    mediaRecorder.setOutputFile(record_video_file.getAbsolutePath());
                    try {
                        mediaRecorder.prepare();
                        handler.post(runnable);
                        mediaRecorder.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    record_second = 0;
                    is_recording = true;
                    had_record = false;
                    btn_send.setVisibility(View.INVISIBLE);
                    btn_cancel.setVisibility(View.INVISIBLE);
                    btn_record.setBackgroundResource(R.drawable.icon_stop_record);
//                    timer.schedule(timerTask,1000);
                }else if(is_recording&&!had_record&&!is_playing){
                    //停止录制
                    mediaRecorder.setOnInfoListener(null);
                    mediaRecorder.setOnErrorListener(null);
                    mediaRecorder.setPreviewDisplay(null);
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    is_recording = false;
                    had_record = true;
                    btn_send.setVisibility(View.VISIBLE);
                    btn_cancel.setVisibility(View.VISIBLE);
                    btn_record.setBackgroundResource(R.drawable.icon_play_record);
                    handler.removeCallbacks(runnable);
                }else if(had_record&&!is_playing){
                    //开始播放
                    if(mediaPlayer != null){
                        mediaRecorder.release();
                    }
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.reset();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(record_video_file.getAbsolutePath());
                        mediaPlayer.setDisplay(surfaceHolder);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                is_playing = false;
                                btn_record.setBackgroundResource(R.drawable.icon_play_record);
                                handler.removeCallbacks(runnable);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    is_playing = true;
                    btn_record.setBackgroundResource(R.drawable.icon_stop_record);
                    record_second = 0;
                    handler.postDelayed(runnable,1000);
                }else{
                    //停止播放
                    if(mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    is_playing = false;
                    btn_record.setBackgroundResource(R.drawable.icon_play_record);
                    handler.removeCallbacks(runnable);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_playing || mediaPlayer != null ){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if(is_playing||is_recording)
                    handler.removeCallbacks(runnable);
                if(record_video_file.exists())
                    record_video_file.delete();
                camera = Camera.open();
                try {
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is_playing = false;
                is_recording = false;
                had_record = false;
                btn_send.setVisibility(View.INVISIBLE);
                btn_cancel.setVisibility(View.INVISIBLE);
                btn_record.setBackgroundResource(R.drawable.icon_start_record);
                record_second = 0;
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_send = true;
                finish();
                handler.removeCallbacks(runnable);
            }
        });
        btn_change_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
