package pers.zhentao.refereeresourceclient.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by ZhangZT on 2016/7/7 19:50.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class Post implements Serializable{

    private Integer postId;
    private String title = null;
    private String content = null;
    private Boolean isDelete = false;
    private Date createTime = null;

    private User user = null;
    private Integer reviewCount = 0;
    private Integer likeCount = 0;
    private Integer readCount = 0;
    private Integer type = 0;

    public Post(String title, String content, User user, Date createTime, Integer type){
        this.title = title;
        this.content = content;
        this.user = user;
        this.createTime = createTime;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean is_delete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
