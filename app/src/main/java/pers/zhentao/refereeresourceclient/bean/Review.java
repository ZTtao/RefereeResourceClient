package pers.zhentao.refereeresourceclient.bean;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ZhangZT on 2016/7/9 10:59.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class Review{
    private Integer id ;
    private User user = null;
    private Post post = null;
    private Timestamp time = null;
    private String content = null;
    private boolean isDelete = false;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean is_delete) {
        this.isDelete = is_delete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
