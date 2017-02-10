package pers.zhentao.refereeresourceclient.listener;

import pers.zhentao.refereeresourceclient.bean.IMMessage;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public interface MessageSendListener {
    void done(IMMessage message, Exception e);
}
