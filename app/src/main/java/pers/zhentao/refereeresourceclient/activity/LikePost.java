package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.MainPageListAdapter;
import pers.zhentao.refereeresourceclient.bean.Like;
import pers.zhentao.refereeresourceclient.bean.Post;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.PostService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/26 20:56.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class LikePost extends Activity {

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
        tvTitle.setText("喜欢的帖子");
        tvTitle.setVisibility(View.VISIBLE);
        ListView listView = (ListView)findViewById(R.id.lv_my_post);
        list = new ArrayList<>();
        final MainPageListAdapter adapter = new MainPageListAdapter(LikePost.this,R.layout.item_mainpage_list,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LikePost.this,PostDetail.class);
                intent.putExtra("post",list.get(position));
                startActivity(intent);
            }
        });
        new PostService().findLike(" where user_id=" + ContextUtil.getUserInstance().getUserId() + " and is_delete=false", "", "", new FindListener<Like>() {
            @Override
            public void onSuccess(final List<Like> li) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        for(int i=0;i<li.size();i++){
                            list.add(li.get(i).getPost());
                        }
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<Like> query = new BmobQuery<>();
//        query.addWhereEqualTo("user", Common.USER);
//        query.addWhereEqualTo("is_delete",false);
//        query.include("post");
//        query.findObjects(LikePost.this, new FindListener<Like>() {
//            @Override
//            public void onSuccess(final List<Like> li) {
//                BmobQuery<DeletedPost> bmobQuery = new BmobQuery<DeletedPost>();
//                bmobQuery.findObjects(LikePost.this, new FindListener<DeletedPost>() {
//                    @Override
//                    public void onSuccess(List<DeletedPost> l) {
//                        for(int i=0;i<li.size();i++){
//                            int j=0;
//                            for(;j<l.size();j++){
//                                if(li.get(i).getPost().getObjectId().equals(l.get(j).getPostId()))break;
//                            }
//                            if(j>=l.size()){
//                                BmobQuery<Post> query1 = new BmobQuery<Post>();
//                                query1.include("user");
//                                final int pos = i;
//                                query1.getObject(LikePost.this, li.get(i).getPost().getObjectId(), new GetListener<Post>() {
//                                    @Override
//                                    public void onSuccess(Post post) {
//                                        list.remove(li.get(pos).getPost());
//                                        list.add(post);
//                                        adapter.notifyDataSetChanged();
//                                    }
//
//                                    @Override
//                                    public void onFailure(int i, String s) {
//
//                                    }
//                                });
//                                list.add(li.get(i).getPost());
//                            }
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
