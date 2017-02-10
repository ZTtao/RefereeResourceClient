package pers.zhentao.refereeresourceclient.service;

import pers.zhentao.refereeresourceclient.bean.IMConversation;
import pers.zhentao.refereeresourceclient.bean.IMMessage;
import pers.zhentao.refereeresourceclient.listener.MessageQueryListener;
import pers.zhentao.refereeresourceclient.listener.MessageSendListener;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class ConversationService {

    public void sendMessage(IMConversation conversation,IMMessage message, MessageSendListener listener){
        //send an IMMessage

    }
    public void queryMessage(IMConversation conversation, MessageQueryListener listener){
        //query all chat history

    }
}
