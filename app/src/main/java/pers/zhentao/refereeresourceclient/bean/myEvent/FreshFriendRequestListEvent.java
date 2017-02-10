package pers.zhentao.refereeresourceclient.bean.myEvent;


import pers.zhentao.refereeresourceclient.bean.AddFriendRequest;

/**
 * Created by ZhangZT on 2016/7/22 00:26.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FreshFriendRequestListEvent {
    private int position;
    private AddFriendRequest request;
    public FreshFriendRequestListEvent(int pos,AddFriendRequest request){
        this.position = pos;
        this.request = request;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AddFriendRequest getRequest() {
        return request;
    }

    public void setRequest(AddFriendRequest request) {
        this.request = request;
    }
}
