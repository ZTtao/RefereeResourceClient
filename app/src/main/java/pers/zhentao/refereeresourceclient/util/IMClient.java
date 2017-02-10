package pers.zhentao.refereeresourceclient.util;

import pers.zhentao.refereeresourceclient.listener.ConnectListener;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class IMClient {
    private static IMClient instance = null;
    private IMClient(){}
    public static IMClient getInstance(){
        if(instance == null){
            synchronized (IMClient.class){
                if(instance == null){
                    instance = new IMClient();
                }
            }
        }
        return instance;
    }
    public static void connect(int userId, ConnectListener listener){

    }
}
