package pers.zhentao.refereeresourceclient.service;

import android.util.Log;

import net.openmob.mobileimsdk.android.core.LocalUDPDataSender;

import java.util.Observable;
import java.util.Observer;

import pers.zhentao.refereeresourceclient.util.ContextUtil;
import pers.zhentao.refereeresourceclient.util.IMClientManager;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class LoginService {
    private Observer onLoginSuccessObserver = null;

    public void loginIM(int userId){
        IMClientManager.getInstance(ContextUtil.getInstance()).initMobileIMSDK();
        onLoginSuccessObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                int code = (Integer)arg;
                if(code == 0){
                    Log.d("LoginService","登录IM成功");
                }else{
                    Log.d("LoginService","登录失败"+code);
                }
            }
        };

        IMClientManager.getInstance(ContextUtil.getInstance()).getBaseEventListener().setLoginOkForLaunchObserver(onLoginSuccessObserver);
        new LocalUDPDataSender.SendLoginDataAsync(ContextUtil.getInstance(),userId+"","0"){
            @Override
            protected void fireAfterSendLogin(int code){
                if(code == 0){
                    Log.d("LoginService","登录信息已发出");
                }else{
                    Log.d("LoginService","登录信息发送失败："+code);
                }
            }
        }.execute();
    }
}
