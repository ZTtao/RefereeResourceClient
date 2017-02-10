package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.database.RefereeResourceDB;
import pers.zhentao.refereeresourceclient.globalvariable.CommonManager;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.service.LoginService;
import pers.zhentao.refereeresourceclient.service.UserService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;
import pers.zhentao.refereeresourceclient.util.NetworkUtil;

public class SplashScreen extends Activity {

    private Handler handler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        handler = new Handler();
        Runnable checkNetwork = new Runnable() {
            @Override
            public void run() {
                NetworkUtil.getInstance().checkNetworkStatus();
            }
        };
        new Thread(checkNetwork).start();
        User user = RefereeResourceDB.getInstance().getLocateUser();
        if(user != null) {
            //本地user表中存在用户记录，即已登录
            ContextUtil.setUserInstance(user);
            new LoginService().loginIM(user.getUserId());//初始化IM模块
            new UserService().getUser(user.getUserId(), new GetListener<User>() {
                @Override
                public void onSuccess(User obj) {
                    ContextUtil.setUserInstance(obj);
                    Log.d("SplashScreen",obj.getUserId()+"");
                }

                @Override
                public void onFailure(int errorCode, String result) {
                    Log.d("SplashScreen",result);
                }
            });//更新用户信息
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ContextUtil.getInstance(), MainPage.class));
                CommonManager.getInstance().cancleAllNotification();
                finish();
            }
        }, 1000);
    }
    @Override
    public void finish(){
        super.finish();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}