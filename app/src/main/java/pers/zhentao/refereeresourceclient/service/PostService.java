package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.zhentao.refereeresourceclient.bean.Like;
import pers.zhentao.refereeresourceclient.bean.Post;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class PostService {
    public void findOrderByLimit(String whereClause, String orderByClause, String limitClause, final FindListener<Post> listener){
        String url = "post/getPostAPI";
        Map<String,String> map = new HashMap<>();
        map.put("whereClause",whereClause);
        map.put("orderByClause",orderByClause);
        map.put("limitClause",limitClause);
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                response = response.replace("//","");
                response = response.substring(1,response.length()-1);
                Log.d("PostService",response);
                List<Post> list = JSON.parseArray(response,Post.class);
                listener.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("PostService",e.getMessage());
                listener.onError(101,"网络错误");
            }
        });
    }
    public void findLike(String whereClause, String orderByClause, String limitClause, final FindListener<Like> listener){
        String url = "like/findLike";
        Map<String,String> map = new HashMap<>();
        map.put("whereClause",whereClause);
        map.put("orderByClause",orderByClause);
        map.put("limitClause",limitClause);
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                response = response.replace("//","");
                response = response.substring(1,response.length()-1);
                Log.d("PostService",response);
                List<Like> list = JSON.parseArray(response,Like.class);
                listener.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("PostService",e.getMessage());
                listener.onError(101,"网络错误");
            }
        });
    }
    public void deletePost(int postId){
        String url = "post/deletePost";
        Map<String,String> map = new HashMap<>();
        map.put("postId",postId+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("PostService",response);
            }

            @Override
            public void onError(Exception e) {
                Log.d("PostService",e.getMessage());
                e.printStackTrace();
            }
        });
    }
    public void getPost(Integer postId, final GetListener<Post> listener){
        String url = "post/getPost";
        Map<String,String> map = new HashMap<>();
        map.put("postId",postId+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("PostService",response);
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                Post post = JSONObject.parseObject(response,Post.class);
                listener.onSuccess(post);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("PostService",e.getMessage());
                listener.onFailure(101,"网络错误");
            }
        });
    }
    public void savePost(Post post, final SaveListener listener){
        String url = "post/savePost";
        Map<String,String> map = new HashMap<>();
        if(post.getTitle()!=null)
            map.put("title",post.getTitle());
        if(post.getContent()!=null)
            map.put("content",post.getContent());
        if(post.getIsDelete()!=null)
            map.put("isDelete",post.getIsDelete().toString());
        if(post.getCreateTime()!=null)
            map.put("createTime",post.getCreateTime().toString());
        if(post.getUser()!=null)
            map.put("userId",post.getUser().getUserId().toString());
        if(post.getType()!=null)
            map.put("type",post.getType().toString());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("PostService",response);
                response = response.replace("\\","");
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                Log.d("PostService",e.getMessage());
                e.printStackTrace();
                listener.onFailure(101,"网络错误");
            }
        });
    }
}
