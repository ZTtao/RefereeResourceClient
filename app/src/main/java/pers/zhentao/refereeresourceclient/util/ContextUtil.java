package pers.zhentao.refereeresourceclient.util;

import android.app.Application;

import pers.zhentao.refereeresourceclient.bean.Player;
import pers.zhentao.refereeresourceclient.bean.Referee;
import pers.zhentao.refereeresourceclient.bean.User;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class ContextUtil extends Application {
    private static ContextUtil instance;
    private static User userInstance = null;
    public static ContextUtil getInstance(){
        return instance;
    }

    public static User getUserInstance(){
        return userInstance;
    }
    public static void setUserInstance(User user){
        userInstance = user;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }
}
