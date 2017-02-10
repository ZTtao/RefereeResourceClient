package pers.zhentao.refereeresourceclient.bean;

import java.util.Date;

/**
 * Created by ZhangZT on 2016/7/9 21:23.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class Read{
    private Integer id;
    private Post post;
    private User user;
    private Date createTime;
    private Boolean isDelete = false;
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
