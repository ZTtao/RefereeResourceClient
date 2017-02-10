package pers.zhentao.refereeresourceclient.bean;


/**
 * Created by ZhangZT on 2016/7/13 19:33.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class Friend{

    private Integer id;
    private User user;
    private User friend;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
