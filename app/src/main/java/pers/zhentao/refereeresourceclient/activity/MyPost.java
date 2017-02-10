package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.MainPageListAdapter;
import pers.zhentao.refereeresourceclient.bean.Post;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshPostListEvent;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.PostService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/26 18:45.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyPost extends Activity {

    private List<Post> list;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_post);
        ImageButton btnBack = (ImageButton)findViewById(R.id.back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBack.setVisibility(View.VISIBLE);
        TextView tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("我的帖子");
        tvTitle.setVisibility(View.VISIBLE);
        ListView listView = (ListView)findViewById(R.id.lv_my_post);
        list = new ArrayList<>();
        final MainPageListAdapter adapter = new MainPageListAdapter(MyPost.this,R.layout.item_mainpage_list,list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog dialog = new AlertDialog.Builder(MyPost.this)
                        .setTitle("删除该贴?")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.get(position).setIsDelete(true);
//                                DeletedPost deletedPost = new DeletedPost();
//                                deletedPost.setPostId(list.get(position).getObjectId());
//                                deletedPost.save(MyPost.this);
                                new PostService().deletePost(list.get(position).getPostId());
                                EventBus.getDefault().post(new FreshPostListEvent( list.get(position).getPostId()));
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyPost.this,PostDetail.class);
                intent.putExtra("post",list.get(position));
                startActivity(intent);
            }
        });
        new PostService().findOrderByLimit(" where user="+ ContextUtil.getUserInstance().getUserId()+" and is_delete=false","","",new FindListener<Post>(){
            @Override
            public void onSuccess(final List<Post> li) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        if(li != null)list.addAll(li);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<Post> query = new BmobQuery<>();
//        query.addWhereEqualTo("user", Common.USER);
//        query.include("user");
//        query.findObjects(MyPost.this, new FindListener<Post>() {
//            @Override
//            public void onSuccess(final List<Post> li) {
//                BmobQuery<DeletedPost> bmobQuery = new BmobQuery<DeletedPost>();
//                bmobQuery.findObjects(MyPost.this, new FindListener<DeletedPost>() {
//                    @Override
//                    public void onSuccess(List<DeletedPost> l) {
//                        for(int i=0;i<li.size();i++){
//                            int j=0;
//                            for(;j<l.size();j++){
//                                if(li.get(i).getObjectId().equals(l.get(j).getPostId()))break;
//                            }
//                            if(j>=l.size())list.add(li.get(i));
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
    }
}
