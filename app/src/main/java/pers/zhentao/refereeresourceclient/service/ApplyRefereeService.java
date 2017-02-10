package pers.zhentao.refereeresourceclient.service;

import pers.zhentao.refereeresourceclient.bean.ApplyRefereeMessage;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.listener.UpdateListener;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class ApplyRefereeService {

    public void update(ApplyRefereeMessage message, UpdateListener listener){
        //alter ApplyRefereeMessage set status = (Common.APPLY_REJECT or Common.APPLY_ACCEPT)

    }
    public void save(ApplyRefereeMessage message){
        //new ApplyRefereeMessage and save to server

    }
    public void find(String whereClause, FindListener<ApplyRefereeMessage> listener){
        //find List<ApplyRefereeMessage> from server according to whereClause

    }
}
