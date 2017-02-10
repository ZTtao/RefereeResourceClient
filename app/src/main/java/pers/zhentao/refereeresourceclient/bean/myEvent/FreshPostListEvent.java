package pers.zhentao.refereeresourceclient.bean.myEvent;

/**
 * Created by ZhangZT on 2016/7/26 20:30.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FreshPostListEvent {
    private Integer postId;
    public FreshPostListEvent(Integer id){
        this.postId = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
