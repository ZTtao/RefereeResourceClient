package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import pers.zhentao.refereeresourceclient.bean.Read;
import pers.zhentao.refereeresourceclient.listener.CountListener;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class ReadService {

    public void save(Read read){
        String url = "read/saveRead";
        Map<String,String> map = new HashMap<>();
        if(read.getPost() != null)
            map.put("post_id",read.getPost().getPostId()+"");
        if(read.getUser() != null)
            map.put("user_id",read.getUser().getUserId()+"");
        if(read.getCreateTime() != null)
            map.put("createTime",read.getCreateTime().toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("ReadService",response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("ReadService",e.getMessage());
            }
        });
    }
    public void countRead(Integer postId, final CountListener listener){
        String url = "read/countRead";
        Map<String,String> map = new HashMap<>();
        map.put("postId",postId+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("ReadService",response);
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("ReadService",e.getMessage());
                listener.onFailure(101,"网络错误");
            }
        });
    }
}
