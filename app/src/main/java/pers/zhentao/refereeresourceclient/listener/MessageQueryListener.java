package pers.zhentao.refereeresourceclient.listener;

import java.util.List;

import pers.zhentao.refereeresourceclient.bean.IMMessage;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public interface MessageQueryListener {
    void done(List<IMMessage> li, Exception e);
}
