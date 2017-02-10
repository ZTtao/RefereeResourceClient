package pers.zhentao.refereeresourceclient.bean.myEvent;

/**
 * Created by ZhangZT on 2016/7/23 19:07.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FreshChatListNameEvent extends FreshChatListEvent{
    private int position;

    public FreshChatListNameEvent(int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
