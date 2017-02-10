package pers.zhentao.refereeresourceclient.bean.myMessage;

import pers.zhentao.refereeresourceclient.bean.User;

/**
 * Created by ZhangZT on 2016/7/14 22:07.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class AddFriendResponseMessage{

    private User user;
    public String getMsgType(){
        return "response";
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
