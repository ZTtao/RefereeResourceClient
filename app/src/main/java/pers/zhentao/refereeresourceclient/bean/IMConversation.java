package pers.zhentao.refereeresourceclient.bean;

import java.io.Serializable;
import java.util.List;

import pers.zhentao.refereeresourceclient.util.IMClient;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class IMConversation implements Serializable{

    private User toUser;
    private List<IMMessage> messages;
    private int unreadCount;
    private Integer conversationId;

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
    public List<IMMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<IMMessage> messages) {
        this.messages = messages;
    }

    public static IMConversation obtain(IMClient client,IMConversation conversation){
        return null;
    }
}
