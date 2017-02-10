package pers.zhentao.refereeresourceclient.listener;

import pers.zhentao.refereeresourceclient.bean.IMConversation;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public interface ConversationListener {
    void done(IMConversation conversation,Exception e);
}
