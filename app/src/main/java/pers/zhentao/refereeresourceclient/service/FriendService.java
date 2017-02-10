package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.zhentao.refereeresourceclient.bean.Friend;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class FriendService {
    public void find(String whereClause, final FindListener<Friend> listener){
        //find List<Friend> from server according to whereClause
        String url = "friend/findFriend";
        Map<String,String> map = new HashMap<>();
        map.put("whereClause",whereClause);
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("FriendService",response);
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                List<Friend> list = JSON.parseArray(response,Friend.class);
                listener.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {
                Log.d("FriendService",e.getMessage());
                e.printStackTrace();
                listener.onError(101,"网络错误");
            }
        });
    }

}
