package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import pers.zhentao.refereeresourceclient.listener.CountListener;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/9.
 */

public class LikeService {

    public void isLiked(Integer postId, Integer userId, final CountListener listener){
        //get count of Like according to postId and userId
        String url = "like/isLiked";
        Map<String,String> map = new HashMap<>();
        map.put("postId",postId.toString());
        map.put("userId",userId.toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("LikeService",response);
                response = response.replace("\\","");
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                Log.d("LikeService",e.getMessage());
                e.printStackTrace();
                listener.onFailure(101,"网络错误");
            }
        });
    }

    public void like(Integer postId, Integer userId, final SaveListener listener){
        //insert or update(set isDelete=false) Like to server
        String url = "like/addLike";
        Map<String,String> map = new HashMap<>();
        map.put("postId",postId.toString());
        map.put("userId",userId.toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("LikeService",response);
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                Log.d("LikeService",e.getMessage());
                e.printStackTrace();
                listener.onFailure(101,"网络错误");
            }
        });
    }

    public void dislike(Integer postId, Integer userId, final SaveListener listener){
        //update(set isDelete=true) Like to server
        String url = "like/deleteLike";
        Map<String,String> map = new HashMap<>();
        map.put("postId",postId.toString());
        map.put("userId",userId.toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("LikeService",response);
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                Log.d("LikeService",e.getMessage());
                e.printStackTrace();
                listener.onFailure(101,"网络错误");
            }
        });
    }

    public void getLikeCount(Integer postId, final CountListener listener){
        //get count of Like according to postId
        String url = "like/countLike";
        Map<String,String> map = new HashMap<>();
        map.put("postId",postId.toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("LikeService",response);
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                Log.d("LikeService",e.getMessage());
                e.printStackTrace();
                listener.onFailure(101,"网络错误");
            }
        });
    }
}
