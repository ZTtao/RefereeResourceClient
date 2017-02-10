package pers.zhentao.refereeresourceclient.bean;

import java.util.Map;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class IMMessage {

    private Integer id;
    private String msgType;
    private String content;
    private long updateTime;
    private Integer fromId;
    private Integer toId;
    private IMConversation conversation;
    private Map<String,String> Extra;

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public IMConversation getConversation() {
        return conversation;
    }

    public void setConversation(IMConversation conversation) {
        this.conversation = conversation;
    }

    public Map<String, String> getExtra() {
        return Extra;
    }

    public void setExtra(Map<String, String> extra) {
        Extra = extra;
    }
}
