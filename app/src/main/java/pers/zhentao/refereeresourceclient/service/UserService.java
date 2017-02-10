package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.listener.UpdateListener;
import pers.zhentao.refereeresourceclient.util.ContextUtil;
import pers.zhentao.refereeresourceclient.util.HttpCallbackListener;
import pers.zhentao.refereeresourceclient.util.HttpURLConnectionUtil;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class UserService {
    public void getUser(Integer userId, final GetListener<User> listener){
        String url = "user/getUserById";
        Map<String,String> map = new HashMap<>();
        map.put("userId",userId+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("UserService",response);
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                User user = JSON.parseObject(response,User.class);
                listener.onSuccess(user);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("UserService",e.getMessage());
                listener.onFailure(101,"网络错误");
            }
        });
    }
    public void saveUser(User user, final SaveListener listener){
        String url = "register/addUser";
        Map<String,String> map = new HashMap<>();
        map.put("phoneNumber", user.getPhoneNumber());
        map.put("password",user.getPassword());
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                Log.d("UserService",response);
                JSONObject jsonObject = JSONObject.parseObject(response);
                int errorCode = jsonObject.getInteger("errorCode");
                if(errorCode == 100)listener.onSuccess(jsonObject.getInteger("result"));
                else listener.onFailure(errorCode,jsonObject.getString("result"));
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public void findUser(String phone, String password, final FindListener<User> listener){
        String url = "login/loginAPI";
        Map<String,String> map = new HashMap<>();
        map.put("account",phone);
        map.put("password",password);
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("UserService",response);
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                JSONObject jsonObject = JSON.parseObject(response);
                int errorCode = jsonObject.getInteger("errorCode");
                List<User> list = new ArrayList<>();
                if(errorCode == 100) {
                    User user = jsonObject.getObject("result",User.class);
                    list.add(user);
                }
                listener.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                listener.onError(102,e.getMessage());
            }
        });
    }
    public void updateUser(User user, final UpdateListener listener){
        String url = "user/updateInfoAPI";
        Map<String,String> map = new HashMap<>();
        map.put("userId",user.getUserId()+"");
        if(user.getTime() != null)
            map.put("time",user.getTime().toString());
        if(user.getName() != null)
            map.put("name",user.getName());
        map.put("nickName",user.getNickName() == null ? "null":user.getNickName());
        if(user.getEMail() != null)
            map.put("eMail",user.getEMail() == null ? "null":user.getEMail());
        if(user.getProvince() != null)
            map.put("province",user.getProvince() == null ? "null":user.getProvince());
        if(user.getCity() != null)
            map.put("city",user.getCity() == null ? "null":user.getCity());
        if(user.getCounty() != null)
            map.put("county",user.getCounty() == null ? "null":user.getCounty());
        if(user.getAddress() != null)
            map.put("address",user.getAddress() == null ? "null":user.getAddress());
        map.put("birthYear",user.getBirthYear()+"");
        map.put("birthMonth",user.getBirthMonth()+"");
        map.put("birthDay",user.getBirthDay()+"");
        map.put("gender",user.getGender()+"");
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("UserService",response);
                response = response.substring(1,response.length()-1);
                if(response.equals("success")){
                    listener.onSuccess();
                }else if(response.equals("faild")){
                    listener.onFailure(101,"服务器错误");
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                listener.onFailure(101,e.getMessage());
            }
        });
    }
    public void findUserLikeNickName(String nickName, final FindListener<User> listener){
        String url = "user/searchFriend";
        Map<String,String> map = new HashMap<>();
        map.put("condition",nickName);
        HttpURLConnectionUtil.sendRequest(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("UserService",response);
                response = response.substring(1,response.length()-1);
                response = response.replace("\\","");
                List<User> list = JSON.parseArray(response,User.class);
                listener.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("UserService",e.getMessage());
                listener.onError(101,"网络错误");
            }
        });
    }
}
