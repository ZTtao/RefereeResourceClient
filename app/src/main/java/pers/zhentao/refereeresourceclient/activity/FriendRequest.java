package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.RequestListAdapter;
import pers.zhentao.refereeresourceclient.bean.AddFriendRequest;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshFriendRequestListEvent;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshFriendResponseEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.FriendRequestService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/15 19:42.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FriendRequest extends Activity {

    private ListView listView;
    private List<AddFriendRequest> list;
    private RequestListAdapter adapter;
    private ImageView img_msg;
    private Bitmap bitmapMsg = null;
    private ImageView btn_back;
    private ImageView red_hint;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_friend_request);
        EventBus.getDefault().register(this);
        init();
    }
    @Override
    public void onPause(){
        img_msg.setImageBitmap(null);
        bitmapMsg.recycle();
        bitmapMsg = null;
        super.onPause();
    }
    public void onResume(){
        bitmapMsg = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(),R.mipmap.icon_friend_response_msg);
        img_msg.setImageBitmap(bitmapMsg);
        img_msg.setVisibility(View.VISIBLE);
        super.onResume();
    }
    private void init(){
        red_hint = (ImageView)findViewById(R.id.img_red_hint_title_msg);
        if(Common.hadUnreadResponse)
            red_hint.setVisibility(View.VISIBLE);
        btn_back = (ImageView)findViewById(R.id.back_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back.setVisibility(View.VISIBLE);
        tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("好友请求");
        tvTitle.setVisibility(View.VISIBLE);
        img_msg = (ImageView)findViewById(R.id.btn_right_msg);
        listView = (ListView)findViewById(R.id.lv_friend_request);
        list = new ArrayList<>();
        adapter = new RequestListAdapter(FriendRequest.this,R.layout.item_friend_request_list,list);
        listView.setAdapter(adapter);
        img_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.hadUnreadResponse = false;
                EventBus.getDefault().post(new FreshFriendResponseEvent());
                Intent intent = new Intent(FriendRequest.this,FriendRequestMsg.class);
                startActivity(intent);
            }
        });
        new FriendRequestService().findAddRequest(" where user_receive=" + ContextUtil.getUserInstance().getUserId() + " and is_delete=false", new FindListener<AddFriendRequest>() {
            @Override
            public void onSuccess(final List<AddFriendRequest> li) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        for (int i = 0; i < li.size(); i++) {
                            list.add(li.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<AddFriendRequest> query = new BmobQuery<>();
//        query.addWhereEqualTo("user_receive", Common.USER);
//        query.addWhereEqualTo("is_delete", false);
//        query.include("user_send");
//        query.findObjects(FriendRequest.this, new FindListener<AddFriendRequest>() {
//            @Override
//            public void onSuccess(List<AddFriendRequest> li) {
//                list.clear();
//                for (int i = 0; i < li.size(); i++) {
//                    list.add(li.get(i));
//                }
//                adapter.notifyDataSetChanged();
//                LogUtil.v("RefereeResource.FriendRequest:", "onSuccess");
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                LogUtil.v("RefereeResource.FriendRequest:", i + s);
//            }
//        });
    }

    public void onEventMainThread(FreshFriendRequestListEvent event){
        list.set(event.getPosition(),event.getRequest());
        adapter.notifyDataSetChanged();
    }
    public void onEventMainThread(FreshFriendResponseEvent event){
        if(Common.hadUnreadResponse)
            red_hint.setVisibility(View.VISIBLE);
        else
            red_hint.setVisibility(View.GONE);
    }
}
