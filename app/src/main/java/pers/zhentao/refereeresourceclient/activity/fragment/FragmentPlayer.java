package pers.zhentao.refereeresourceclient.activity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.activity.PostDetail;
import pers.zhentao.refereeresourceclient.adapter.MainPageListAdapter;
import pers.zhentao.refereeresourceclient.bean.Post;
import pers.zhentao.refereeresourceclient.bean.Read;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshPostListEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.PostService;
import pers.zhentao.refereeresourceclient.service.ReadService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;


/**
 * 球员社区fragment
 * Created by ZhangZT on 2016/7/7 20:34.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FragmentPlayer extends Fragment {

    private ListView listView = null;//帖子列表listView
    private List<Post> listData = null;//帖子数据列表
    private MainPageListAdapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;//下拉刷新控件

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup group,Bundle bundle){
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_player,group,false);
        listView = (ListView)view.findViewById(R.id.lv_player_post_list);
        listData = new ArrayList<>();
        adapter = new MainPageListAdapter(ContextUtil.getInstance(),R.layout.item_mainpage_list, listData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Read read = new Read();
                read.setUser(ContextUtil.getUserInstance());
                read.setPost(listData.get(position));
                read.setCreateTime(new Date());
                new ReadService().save(read);
                Intent intent = new Intent(ContextUtil.getInstance(), PostDetail.class);
                intent.putExtra("post", listData.get(position));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        PostService postService = new PostService();
                        swipeRefreshLayout.setRefreshing(true);
                        Date start = listData.get(view.getLastVisiblePosition()).getCreateTime();
                        //where create_time < start and type = Common.PLAYER_POST
                        postService.findOrderByLimit(" where create_time<'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start)+"' and type="+Common.PLAYER_POST," order by create_time desc"," limit 0,20",new FindListener<Post>() {
                            @Override
                            public void onSuccess(List<Post> l) {
                                if (l.size() == 0) {
                                    if (swipeRefreshLayout != null)
                                        swipeRefreshLayout.setRefreshing(false);
                                    Toast.makeText(ContextUtil.getInstance(), "无更多内容", Toast.LENGTH_SHORT).show();
                                } else {
                                    listData.addAll(l);
                                    adapter.notifyDataSetChanged();
                                    if (swipeRefreshLayout != null)
                                        swipeRefreshLayout.setRefreshing(false);
                                }
                            }

                            @Override
                            public void onError(int errorCode, String result) {

                            }
                        });
//                        query.addWhereLessThan("createdAt", new BmobDate(date));
//                        query.order("-createdAt");
//                        query.include("user");
//                        query.setLimit(20);
//                        query.addWhereEqualTo("type", Common.PLAYER_POST);
//                        query.findObjects(MyApplication.getContext(), new FindListener<Post>() {
//                            @Override
//                            public void onSuccess(final List<Post> list) {
//                                if (list.size() == 0) {
//                                    if (swipeRefreshLayout != null)
//                                        swipeRefreshLayout.setRefreshing(false);
//                                    Toast.makeText(MyApplication.getContext(), "无更多内容", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    BmobQuery<DeletedPost> bmobQuery = new BmobQuery<DeletedPost>();
//                                    bmobQuery.findObjects(MyApplication.getContext(), new FindListener<DeletedPost>() {
//                                        @Override
//                                        public void onSuccess(List<DeletedPost> l) {
//                                            for (int i = 0; i < list.size(); i++) {
//                                                int j = 0;
//                                                for (; j < l.size(); j++) {
//                                                    if (list.get(i).getObjectId().equals(l.get(j).getPostId()))
//                                                        break;
//                                                }
//                                                if (j >= l.size()) listData.add(list.get(i));
//                                            }
//                                            adapter.notifyDataSetChanged();
//                                            if (swipeRefreshLayout != null)
//                                                swipeRefreshLayout.setRefreshing(false);
//                                        }
//
//                                        @Override
//                                        public void onError(int i, String s) {
//
//                                        }
//                                    });
//                                }
//                            }
//
//                            @Override
//                            public void onError(int i, String s) {
//
//                            }
//                        });
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_player_post_list);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(68, 119, 255), Color.rgb(255, 0, 0), Color.rgb(124, 252, 0));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doQueryNew();
            }
        });
        doQueryNew();
        LinearLayout linearLayoutFind = (LinearLayout)view.findViewById(R.id.ly_fragment_find);
        linearLayoutFind.setVisibility(View.GONE);
        return view;
    }
    /**
     * 更新列表数据
     */
    public void doQueryNew(){
        PostService postService = new PostService();
        swipeRefreshLayout.setRefreshing(true);
        postService.findOrderByLimit(" where is_delete=false and type="+Common.PLAYER_POST," order by create_time desc"," limit 0,20",new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> l) {
                if(listData.size()==0) {
                    Toast.makeText(ContextUtil.getInstance(),"暂无新内容",Toast.LENGTH_SHORT).show();
                }else if(listData.size()>0 && listData.get(0).getPostId().equals(listData.get(0).getPostId())){
                    Toast.makeText(ContextUtil.getInstance(),"暂无新内容",Toast.LENGTH_SHORT).show();
                }else {
                    listData.clear();
                    for (int i = 0; i < listData.size(); i++) {
                        int j = 0;
                        for (; j < l.size(); j++) {
                            if (listData.get(i).getPostId().equals(l.get(j).getPostId()))
                                break;
                        }
                        if (j >= l.size()) listData.add(listData.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<Post> query = new BmobQuery<>();
//        query.setLimit(20);
//        query.order("-createdAt");
//        query.addWhereEqualTo("is_delete", false);
//        query.addWhereEqualTo("type",Common.PLAYER_POST);
//        query.include("user");
//        query.findObjects(MyApplication.getContext(), new FindListener<Post>() {
//            @Override
//            public void onSuccess(final List<Post> list) {
//                BmobQuery<DeletedPost> bmobQuery = new BmobQuery<DeletedPost>();
//                bmobQuery.findObjects(MyApplication.getContext(), new FindListener<DeletedPost>() {
//                    @Override
//                    public void onSuccess(List<DeletedPost> l) {
//                        if(listData.size()>0 && list.get(0).getObjectId().equals(listData.get(0).getObjectId())){
//                            Toast.makeText(MyApplication.getContext(),"暂无新内容",Toast.LENGTH_SHORT).show();
//                        }else {
//                            listData.clear();
//                            for (int i = 0; i < list.size(); i++) {
//                                int j = 0;
//                                for (; j < l.size(); j++) {
//                                    if (list.get(i).getObjectId().equals(l.get(j).getPostId()))
//                                        break;
//                                }
//                                if (j >= l.size()) listData.add(list.get(i));
//                            }
//                            adapter.notifyDataSetChanged();
//                        }
//                            if (swipeRefreshLayout != null)
//                                swipeRefreshLayout.setRefreshing(false);
//
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Toast.makeText(MyApplication.getContext(), "更新内容失败", Toast.LENGTH_SHORT).show();
//            }
//        });
        listView.setSelection(0);
    }
    public void onEventMainThread(FreshPostListEvent event){
        for(int i=0;i<listData.size();i++){
            if(listData.get(i).getPostId().equals(event.getPostId())){
                listData.remove(i);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
