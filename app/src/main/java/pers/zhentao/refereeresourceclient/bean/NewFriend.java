package pers.zhentao.refereeresourceclient.bean;

import java.util.Date;

/**
 * Created by ZhangZT on 2016/7/14 21:56.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class NewFriend {

    private User user;
    private User friend;
    private String msg;
    private Integer status;
    private Date time;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
