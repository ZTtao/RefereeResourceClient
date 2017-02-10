package pers.zhentao.refereeresourceclient.bean;

import java.util.Date;


/**
 * Created by ZhangZT on 2016/7/16 15:09.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class AddFriendResponse {

    private String type;
    private User user_send;
    private User user_receive;
    private String note;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser_send() {
        return user_send;
    }

    public void setUser_send(User user_send) {
        this.user_send = user_send;
    }

    public User getUser_receive() {
        return user_receive;
    }

    public void setUser_receive(User user_receive) {
        this.user_receive = user_receive;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
