package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.zhentao.refereeresourceclient.bean.FindRefereeMessage;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.ReloadListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.listener.UpdateListener;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class FindRefereeService {

    public void save(FindRefereeMessage message, final SaveListener listener){
        //new a FindRefereeMessage and save to server
        String url = "findReferee/saveFindMessage";
        Map<String,String> map = new HashMap<>();
        if(message.getUser()!=null)
            map.put("userId",message.getUser().getUserId().toString());
        if(message.getGameState()!=null)
            map.put("gameState",message.getGameState());
        if(message.getAddress()!=null)
            map.put("address",message.getAddress());
        if(message.getTime()!=null)
            map.put("time",message.getTime().toString());
        if(message.getRefereeCount()!=null)
            map.put("refereeCount",message.getRefereeCount().toString());
        if(message.getRefereeClaim()!=null)
            map.put("refereeClaim",message.getRefereeClaim());
        if(message.getPay()!=null)
            map.put("pay",message.getPay());
        if(message.getNote()!=null)
            map.put("note",message.getNote());
        if(message.getPublishTime()!=null)
            map.put("publishTime",message.getPublishTime().toString());
        if(message.getIsAccept()!=null)
            map.put("isAccept",message.getIsAccept().toString());
        if(message.getApplyCount()!=null)
            map.put("applyCount",message.getApplyCount().toString());
        if(message.getReadCount()!=null)
            map.put("readCount",message.getReadCount().toString());
        if(message.getAcceptCount()!=null)
            map.put("acceptCount",message.getAcceptCount().toString());
        if(message.getIsDelete()!=null)
            map.put("isDelete",message.getIsDelete().toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("FindRefereeService",response);
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                Log.d("FindRefereeService",e.getMessage());
                e.printStackTrace();
                listener.onFailure(101,"网络错误");
            }
        });
    }
    public void query(String whereClause, String orderByClause, final String limitClause, final FindListener<FindRefereeMessage> listener){
        //find all FindRefereeMessage from server according to whereClause,orderByClause,limitClause
        String url = "findReferee/findMessage";
        Map<String,String> map = new HashMap<>();
        map.put("whereClause",whereClause);
        map.put("orderByClause",orderByClause);
        map.put("limitClause",limitClause);
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("FindRefereeService",response);
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                List<FindRefereeMessage> list = JSON.parseArray(response,FindRefereeMessage.class);
                listener.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {
                Log.d("FindRefereeService",e.getMessage());
                e.printStackTrace();
                listener.onError(101,"网络错误");
            }
        });
    }
    public void incrementReadCount(Integer messageId){
        //alter FindRefereeMessage set ReadCount++
        String url = "findReferee/addReadCount";
        Map<String,String> map = new HashMap<>();
        map.put("messageId",messageId.toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("FindRefereeService",response);
            }

            @Override
            public void onError(Exception e) {
                Log.d("FindRefereeService",e.getMessage());
                e.printStackTrace();
            }
        });
    }
    public void incrementApplyCount(Integer messageId){
        //alter FindRefereeMessage set ApplyCount++
        String url = "findReferee/addApplyCount";
        Map<String,String> map = new HashMap<>();
        map.put("messageId",messageId.toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("FindRefereeService",response);
            }

            @Override
            public void onError(Exception e) {
                Log.d("FindRefereeService",e.getMessage());
                e.printStackTrace();
            }
        });
    }
    public void reloadMessage(Integer messageId, ReloadListener<FindRefereeMessage> listener){
        //get FindRefereeMessage by messageId from server

    }
}
