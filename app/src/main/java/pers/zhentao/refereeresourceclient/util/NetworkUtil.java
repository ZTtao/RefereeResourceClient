package pers.zhentao.refereeresourceclient.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZhangZT on 2016/3/9 14:59.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class NetworkUtil {

    private static NetworkUtil networkUtil = null;
    private NetworkUtil(){}

    public static NetworkUtil getInstance(){
        if(networkUtil==null){
            synchronized (NetworkUtil.class){
                if(networkUtil == null){
                    networkUtil = new NetworkUtil();
                }
            }
        }
        return networkUtil;
    }
    /**
     * 检查网络状态
     * @return 是否已连接网络
     */
    public boolean checkNetworkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ContextUtil.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        try {
            if (networkInfo.isConnected())
            {
                NetworkInfo.State state = null;
                state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                if(state == NetworkInfo.State.CONNECTED) {
                    Looper.prepare();
                    Toast.makeText(ContextUtil.getInstance(), "网络连接正常,当前网络：GPRS", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                if(state == NetworkInfo.State.CONNECTED){
                    Looper.prepare();
                    Toast.makeText(ContextUtil.getInstance(), "网络连接正常,当前网络：WIFI", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            } else {
                Looper.prepare();
                Toast.makeText(ContextUtil.getInstance(), "网络连接异常！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            return true;
        } catch (NullPointerException e) {
            Looper.prepare();
            Toast.makeText(ContextUtil.getInstance(), "网络连接异常！", Toast.LENGTH_SHORT).show();
            Looper.loop();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送网络请求
     */
    public static void sendHttpRequest(final String address,final String requestMethod,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod(requestMethod);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    if(listener!=null){
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if(listener!=null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection!=null)connection.disconnect();
                }
            }
        }).start();
    }

}
