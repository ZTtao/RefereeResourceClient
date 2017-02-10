package pers.zhentao.refereeresourceclient.listener;

import pers.zhentao.refereeresourceclient.bean.User;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public interface GetListener<T> {
    void onSuccess(T obj);
    void onFailure(int errorCode,String result);
}
