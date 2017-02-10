package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.zhentao.refereeresourceclient.bean.Review;
import pers.zhentao.refereeresourceclient.listener.CountListener;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.listener.UpdateListener;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class ReviewService {
    public void deleteReview(int reviewId){
        String url = "review/deleteReview";
        Map<String,String> map = new HashMap<>();
        map.put("reviewId",reviewId+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("ReviewService",response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("ReviewService",e.getMessage());
            }
        });
    }
    public void findReview(String whereClause, String orderByClause, String limitClause, final FindListener<Review> listener){
        String url = "review/findReview";
        Map<String,String> map = new HashMap<>();
        map.put("whereClause",whereClause);
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("ReviewService",response);
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                List<Review> list = JSON.parseArray(response,Review.class);
                listener.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("ReviewService",e.getMessage());
                listener.onError(101,"网络错误");
            }
        });
    }
    public void saveReview(Review review, final SaveListener listener){
        String url = "review/saveReview";
        Map<String,String> map = new HashMap<>();
        if(review.getUser()!=null)
            map.put("userId",review.getUser().getUserId()+"");
        if(review.getPost()!=null)
            map.put("postId",review.getPost().getPostId()+"");
        if(review.getTime()!=null)
            map.put("time",review.getTime().toString());
        if(review.getContent()!=null)
            map.put("content",review.getContent());
        map.put("isDelete",review.getIsDelete()+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("ReviewService",response);
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("ReviewService",e.getMessage());
                listener.onFailure(101,"网络错误");
            }
        });
    }
    public void countReview(Integer postId, final CountListener listener){
        String url = "review/countReviewByPostId";
        Map<String,String> map = new HashMap<>();
        map.put("postId",postId+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("ReviewService",response);
                response = response.substring(1,response.length()-1);
                listener.onSuccess(Integer.parseInt(response));
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("ReviewService",e.getMessage());
                listener.onFailure(101,"网络错误");
            }
        });
    }
}
