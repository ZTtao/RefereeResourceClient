package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.FriendRequestMsgAdapter;
import pers.zhentao.refereeresourceclient.bean.AddFriendResponse;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.FriendRequestService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/22 16:11.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FriendRequestMsg extends Activity {

    private ListView listView;
    private ImageButton btn_back;
    private List<AddFriendResponse> list;
    private FriendRequestMsgAdapter adapter;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_friend_request_msg);
        init();
    }
    private void init(){
        tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("请求结果");
        tvTitle.setVisibility(View.VISIBLE);
        btn_back = (ImageButton)findViewById(R.id.back_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        listView = (ListView)findViewById(R.id.lv_friend_request_msg);
        adapter = new FriendRequestMsgAdapter(FriendRequestMsg.this,R.layout.item_friend_request_msg,list);
        listView.setAdapter(adapter);
        queryNew();
    }
    private void queryNew(){
        new FriendRequestService().findAddResponse(" where user_receive=" + ContextUtil.getUserInstance().getUserId(), new FindListener<AddFriendResponse>() {
            @Override
            public void onSuccess(final List<AddFriendResponse> li) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        list.addAll(li);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<AddFriendResponse> query = new BmobQuery<>();
//        query.addWhereEqualTo("user_receive", Common.USER);
//        query.include("user_send");
//        query.findObjects(FriendRequestMsg.this, new FindListener<AddFriendResponse>() {
//            @Override
//            public void onSuccess(List<AddFriendResponse> li) {
//                list.clear();
//                list.addAll(li);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
    }
}
