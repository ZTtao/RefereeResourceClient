package pers.zhentao.refereeresourceclient.listener;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public interface UpdateListener {
    void onSuccess();
    void onFailure(int errorCode,String result);
}
