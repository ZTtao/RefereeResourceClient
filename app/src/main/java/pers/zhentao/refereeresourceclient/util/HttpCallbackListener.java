package pers.zhentao.refereeresourceclient.util;

/**
 * Created by ZhangZT on 2016/3/17 11:20.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
