package pers.zhentao.refereeresourceclient.service;

import pers.zhentao.refereeresourceclient.bean.AddFriendRequest;
import pers.zhentao.refereeresourceclient.bean.AddFriendResponse;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class FriendRequestService {
    public void updateAddRequest(AddFriendRequest request){
        //update AddFriendRequest selective to server

    }
    public void findAddRequest(String whereClause, FindListener<AddFriendRequest> listener){
        //find List<AddFriendRequest> from server according to whereClause

    }
    public void findAddResponse(String whereClause, FindListener<AddFriendResponse> listener){
        //find List<AddFriendResponse> from server according to whereClause

    }
    public void sendAddFriendRequest(Integer userId,AddFriendRequest request, SaveListener listener){
        //send AddFriendRequest to user

    }
}
