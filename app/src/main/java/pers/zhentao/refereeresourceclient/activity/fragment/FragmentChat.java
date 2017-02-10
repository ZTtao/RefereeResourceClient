package pers.zhentao.refereeresourceclient.activity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.activity.Chat;
import pers.zhentao.refereeresourceclient.activity.LoginAndRegister;
import pers.zhentao.refereeresourceclient.adapter.ChatListAdapter;
import pers.zhentao.refereeresourceclient.bean.IMConversation;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshChatListEvent;
import pers.zhentao.refereeresourceclient.bean.myEvent.InitIMModuleSuccessEvent;
import pers.zhentao.refereeresourceclient.bean.myEvent.RefreshUnreadMessageCountEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.globalvariable.CommonManager;
import pers.zhentao.refereeresourceclient.listener.ConnectListener;
import pers.zhentao.refereeresourceclient.util.ContextUtil;
import pers.zhentao.refereeresourceclient.util.IM;
import pers.zhentao.refereeresourceclient.util.IMClient;

/**
 * 会话列表fragment
 * Created by ZhangZT on 2016/7/7 20:34.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FragmentChat extends Fragment {

    private ListView listView;//会话列表listView
    private View view;
    private ChatListAdapter chatListAdapter;
    private List<IMConversation> listConversation;//会话数据列表
    private Button btnLoginOrRegister;//进入登陆or注册界面按钮
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private RelativeLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup group,Bundle bundle){
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_chat,group,false);
        listView = (ListView)view.findViewById(R.id.lv_chat_list);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.ly_chat_login);
        listConversation = new ArrayList<>();
        chatListAdapter = new ChatListAdapter(ContextUtil.getInstance(),R.layout.item_chat_list, listConversation);
        listView.setAdapter(chatListAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_chat_list);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255,0,0),Color.rgb(124,252,0),Color.rgb(68, 119, 255));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doQueryNew();
            }
        });
        setListener();
        return view;
    }
    /**
     * 更新列表
     */
    public void doQueryNew(){
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(true);
        if(ContextUtil.getUserInstance() != null && IM.getInstance().loadAllConversation()!=null && IM.getInstance().loadAllConversation().size()>0) {
            listConversation.clear();
            chatListAdapter.notifyDataSetChanged();
            listConversation.addAll(IM.getInstance().loadAllConversation());
            for (int i = 0; i< Common.unreadConversationsIdList.size(); i++){
                for(int j=0;j< listConversation.size();j++){
                    if(listConversation.get(j).getConversationId().equals(Common.unreadConversationsIdList.get(i))){
                        listConversation.get(j).setUnreadCount(1);
                    }
                }
            }
            chatListAdapter.notifyDataSetChanged();
        }
        if(swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 为控件设置Listener
     */
    public void setListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listConversation.get(position).setUnreadCount(0);
                chatListAdapter.notifyDataSetChanged();
                Common.unreadConversationsIdList.remove(listConversation.get(position).getConversationId());
                EventBus.getDefault().post(new RefreshUnreadMessageCountEvent());
                Intent intent = new Intent(ContextUtil.getInstance(), Chat.class);
                intent.putExtra("conversation", listConversation.get(position));
                CommonManager.getInstance().cancleNotificationById(Common.NEW_CHAT_MESSAGE_NOTIFY_ID);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("删除会话？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IM.getInstance().deleteConversation(listConversation.get(position).getConversationId());
                                if (Common.unreadConversationsIdList.contains(listConversation.get(position).getConversationId()))
                                    Common.unreadConversationsIdList.remove(listConversation.get(position).getConversationId());
                                listConversation.remove(position);
                                chatListAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
                return true;
            }
        });
    }
    @Override
    public void onHiddenChanged(boolean hidden){
        if(!hidden) {
            CommonManager.getInstance().cancleNotificationById(Common.NEW_CHAT_MESSAGE_NOTIFY_ID);
            checkLoginStatus();
        }
        super.onHiddenChanged(hidden);
    }
    private void checkLoginStatus(){
        if(ContextUtil.getUserInstance() == null){
            if(swipeRefreshLayout!=null){
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setVisibility(View.GONE);
            }
            listConversation.clear();
            chatListAdapter.notifyDataSetChanged();
            relativeLayout.setVisibility(View.VISIBLE);
            btnLoginOrRegister = (Button)view.findViewById(R.id.btn_chat_login);
            btnLoginOrRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ContextUtil.getInstance(), LoginAndRegister.class));
                }
            });
        }else {
            if(swipeRefreshLayout!=null)swipeRefreshLayout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            IMClient.connect(ContextUtil.getUserInstance().getUserId(), new ConnectListener() {
                @Override
                public void done(String s, Exception e) {}
            });
            doQueryNew();
            EventBus.getDefault().post(new RefreshUnreadMessageCountEvent());
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        checkLoginStatus();
    }
    public void onEventMainThread(FreshChatListEvent event){
//        for(int i=0;i<Common.unreadConversationsIdList.size();i++){
//            for(int j=0;j<listConversation.size();j++){
//                if(listConversation.get(j).getConversationId().equals(Common.unreadConversationsIdList.get(i))){
//                    listConversation.get(j).setUnreadCount(1);
//                    chatListAdapter.notifyDataSetChanged();
//                }
//            }
//        }
        doQueryNew();
    }
    public void onEventMainThread(InitIMModuleSuccessEvent event){
        checkLoginStatus();
    }
}
