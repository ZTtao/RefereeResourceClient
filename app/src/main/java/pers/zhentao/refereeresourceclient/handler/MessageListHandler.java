package pers.zhentao.refereeresourceclient.handler;

import java.util.List;

import pers.zhentao.refereeresourceclient.bean.myEvent.MessageEvent;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public interface MessageListHandler {
    void onMessageReceive(List<MessageEvent> li);
}
