package pers.zhentao.refereeresourceclient.util;

import java.util.List;

import pers.zhentao.refereeresourceclient.bean.IMConversation;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.handler.MessageListHandler;
import pers.zhentao.refereeresourceclient.listener.ConversationListener;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class IM {
    private static IM instance = null;
    private MessageListHandler handler;
    private IM(){}

    public static IM getInstance(){
        if(instance == null){
            synchronized (IM.class){
                if(instance == null){
                    instance = new IM();
                }
            }
        }
        return  instance;
    }

    public List<IMConversation> loadAllConversation(){
        return null;
    }
    public void deleteConversation(Integer conversationId){

    }
    public void addMessageListHandler(MessageListHandler handler){
        this.handler = handler;
    }
    public void removeMessageListHandler(MessageListHandler handler){

    }
    public void startConversation(User user, ConversationListener listener){

    }
}
