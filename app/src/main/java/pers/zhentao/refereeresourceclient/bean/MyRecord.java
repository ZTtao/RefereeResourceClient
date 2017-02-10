package pers.zhentao.refereeresourceclient.bean;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.IOException;

/**
 * Created by ZhangZT on 2016/7/24 19:48.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyRecord {

    private static MyRecord myRecord = null;
    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null;
    private Boolean is_play = false;


    private MyRecord(){}
    public static MyRecord getInstance(){
        if(myRecord == null){
            synchronized (MyRecord.class){
                if(myRecord == null){
                    myRecord = new MyRecord();
                }
            }
        }
        return myRecord;
    }

    public void onStart(String fileName){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    public void onStop(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public void onPlay(String fileName , MediaPlayer.OnCompletionListener listener){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(listener);
        is_play = true;
    }

    public void onStopPlay(){
        mediaPlayer.release();
        mediaPlayer = null;
        is_play = false;
    }

    public Boolean getIs_play() {
        return is_play;
    }

    public void setIs_play(Boolean is_play) {
        this.is_play = is_play;
    }
}
