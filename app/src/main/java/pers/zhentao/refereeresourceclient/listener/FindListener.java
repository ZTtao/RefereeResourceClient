package pers.zhentao.refereeresourceclient.listener;

import java.util.List;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public interface FindListener<T> {
    void onSuccess(List<T> list);
    void onError(int errorCode,String result);
}
