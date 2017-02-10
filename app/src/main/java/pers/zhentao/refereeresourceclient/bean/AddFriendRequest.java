package pers.zhentao.refereeresourceclient.bean;


import java.io.Serializable;

import pers.zhentao.refereeresourceclient.globalvariable.Common;

/**
 * Created by ZhangZT on 2016/7/15 20:42.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class AddFriendRequest implements Serializable{

    private User user_send;
    private User user_receive;
    private Integer status = Common.MSG_UNREAD;
    private String note;
    private Boolean is_delete = false;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Boolean is_delete) {
        this.is_delete = is_delete;
    }
}
