package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.FriendListAdapter;
import pers.zhentao.refereeresourceclient.bean.Friend;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshFriendRequestOrResponseEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.FriendService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/13 19:19.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyFriend extends Activity {

    private ListView listView;
    private FriendListAdapter adapter;
    private List<Friend> list;
    private ImageView img_add_friend;
    private Bitmap bitmapAddFriend = null;
    private LinearLayout ly_friend_request;
    private ImageView btn_back;
    private ImageView red_hint;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_friend);
        init();
    }
    @Override
    public void onResume(){
        bitmapAddFriend = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(),R.mipmap.icon_add_friend);
        img_add_friend.setImageBitmap(bitmapAddFriend);
        img_add_friend.setVisibility(View.VISIBLE);
        super.onResume();
    }
    @Override
    public void finish(){
        if(bitmapAddFriend!=null) {
            img_add_friend.setImageBitmap(null);
            bitmapAddFriend.recycle();
            bitmapAddFriend = null;
            super.finish();
        }
    }
    @Override
    public void onPause(){
        if(bitmapAddFriend!=null) {
            img_add_friend.setImageBitmap(null);
            bitmapAddFriend.recycle();
            bitmapAddFriend = null;
        }
        super.onPause();
    }
    private void init(){
        EventBus.getDefault().register(this);
        tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("我的朋友");
        tvTitle.setVisibility(View.VISIBLE);
        listView = (ListView)findViewById(R.id.lv_friend);
        red_hint = (ImageView)findViewById(R.id.img_red_hint_new_friend);
        EventBus.getDefault().post(new FreshFriendRequestOrResponseEvent());
        btn_back = (ImageView)findViewById(R.id.back_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back.setVisibility(View.VISIBLE);
        img_add_friend = (ImageView)findViewById(R.id.btn_right_add_friend);
        ly_friend_request = (LinearLayout)findViewById(R.id.ly_my_friend_new);
        ly_friend_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.hadUnreadRequest = false;
                EventBus.getDefault().post(new FreshFriendRequestOrResponseEvent());
                Intent intent = new Intent(MyFriend.this, FriendRequest.class);
                startActivity(intent);
            }
        });
        img_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyFriend.this, SearchFriend.class);
                startActivity(intent);
            }
        });
        list = new ArrayList<>();
        adapter = new FriendListAdapter(MyFriend.this,R.layout.item_friend_list,list);
        listView.setAdapter(adapter);
        doQuery();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyFriend.this,PerfectInfo.class);
                intent.putExtra("user",list.get(position).getFriend());
                Common.INFO_EDIT_FLAG = Common.OTHER_BROWSE;
                startActivity(intent);
            }
        });
    }
    private void doQuery(){
        new FriendService().find(" where user_id=" + ContextUtil.getUserInstance().getUserId() + " or friend_id="+ContextUtil.getUserInstance().getUserId(), new FindListener<Friend>() {
            @Override
            public void onSuccess(final List<Friend> l) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        if (l.size() > 0) {
                            for (int i = 0; i < l.size(); i++) {
                                if (!l.get(i).getUser().getUserId().equals(ContextUtil.getUserInstance().getUserId())) {
                                    User temp = l.get(i).getUser();
                                    l.get(i).setUser(l.get(i).getFriend());
                                    l.get(i).setFriend(temp);
                                }
                                list.add(l.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {
                Toast.makeText(MyFriend.this, "获取好友列表失败", Toast.LENGTH_SHORT).show();
            }
        });
//        BmobQuery<Friend> q1 = new BmobQuery<>();
//        q1.addWhereEqualTo("user", Common.USER);
//        BmobQuery<Friend> q2 = new BmobQuery<>();
//        q2.addWhereEqualTo("friend", Common.USER);
//        List<BmobQuery<Friend>> queries = new ArrayList<>();
//        queries.add(q1);
//        queries.add(q2);
//        BmobQuery<Friend> mainQuery = new BmobQuery<>();
//        mainQuery.or(queries);
//        mainQuery.include("user,friend");
//        mainQuery.findObjects(MyFriend.this, new FindListener<Friend>() {
//            @Override
//            public void onSuccess(List<Friend> l) {
//                if (l.size() > 0) list.clear();
//                for (int i = 0; i < l.size(); i++) {
//                    if (!l.get(i).getUser().getObjectId().equals(Common.USER.getObjectId())) {
//                        CommonUser temp = l.get(i).getUser();
//                        l.get(i).setUser(l.get(i).getFriend());
//                        l.get(i).setFriend(temp);
//                    }
//                    list.add(l.get(i));
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Toast.makeText(MyFriend.this, "获取好友列表失败", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void onEventMainThread(FreshFriendRequestOrResponseEvent event){
        if(Common.hadUnreadRequest ||Common.hadUnreadResponse)
            red_hint.setVisibility(View.VISIBLE);
        else
            red_hint.setVisibility(View.GONE);
    }
}
