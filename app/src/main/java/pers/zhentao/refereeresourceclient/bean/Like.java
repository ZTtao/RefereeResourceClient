package pers.zhentao.refereeresourceclient.bean;

import java.util.Date;


/**
 * Created by ZhangZT on 2016/7/9 19:16.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class Like{

    private Integer id;
    private User user = null;
    private Post post = null;
    private Date createTime = null;
    private Boolean isDelete = false;

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
