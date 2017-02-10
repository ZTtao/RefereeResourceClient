package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pers.zhentao.refereeresourceclient.bean.Referee;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class RefereeService {
    public void saveReferee(int userId, Referee referee, final SaveListener listener){
        String url = "referee/addRefereeAPI";
        Map<String,String> map = new HashMap<>();
        map.put("rank",referee.getRank());
        map.put("experience",referee.getExperience());
        map.put("honor",referee.getHonor());
        map.put("userId",userId+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("RefereeService",response);
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                JSONObject jsonObject = JSONObject.parseObject(response);
                int errorCode = jsonObject.getInteger("errorCode");
                if(errorCode == 100){
                    listener.onSuccess(jsonObject.getInteger("result"));
                }else
                    listener.onFailure(jsonObject.getInteger("errorCode"),jsonObject.getString("result"));
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                listener.onFailure(102,e.getMessage());
            }
        });
    }
    public void updateReferee(Referee referee){
        String url = "referee/updateRefereeAPI";
        Map<String,String> map = new HashMap<>();
        map.put("refereeId",referee.getRefereeId()+"");
        map.put("rank",referee.getRank());
        map.put("experience",referee.getExperience());
        map.put("honor",referee.getHonor());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("RefereeService",response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void deleteReferee(Referee referee){
        String url = "referee/deleteRefereeAPI";
        Map<String,String> map = new HashMap<>();
        map.put("refereeId",referee.getRefereeId()+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("RefereeService",response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
//    public void getReferee(Integer userId, GetListener<Referee> listener){
//
//    }
}
