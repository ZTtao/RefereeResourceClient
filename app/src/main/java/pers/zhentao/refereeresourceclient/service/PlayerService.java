package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pers.zhentao.refereeresourceclient.bean.Player;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class PlayerService {

    public void savePlayer(int userId, Player player, final SaveListener listener){
        //insert or update Player according to userId
        String url = "player/addPlayerAPI";
        Map<String,String> map = new HashMap<>();
        map.put("team",player.getTeam());
        map.put("experience",player.getExperience());
        map.put("honor",player.getHonor());
        map.put("userId",userId+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("PlayerService",response);
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
    public void updatePlayer(Player player){
        //update Player according to playerId
        String url = "player/updatePlayerAPI";
        Map<String,String> map = new HashMap<>();
        map.put("playerId",player.getPlayerId()+"");
        map.put("team",player.getTeam());
        map.put("experience",player.getExperience());
        map.put("honor",player.getHonor());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("PlayerService",response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void deletePlayer(Player player){
        //alert Player set isDelete=true
        String url = "player/deletePlayerAPI";
        Map<String,String> map = new HashMap<>();
        map.put("playerId",player.getPlayerId()+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("PlayerService",response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
//    public void getPlayer(Integer userId, GetListener<Player> listener){
//        //get Player by userId
//
//    }
}
