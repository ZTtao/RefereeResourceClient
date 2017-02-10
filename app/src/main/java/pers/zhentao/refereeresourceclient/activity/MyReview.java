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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.MyReviewAdapter;
import pers.zhentao.refereeresourceclient.bean.Post;
import pers.zhentao.refereeresourceclient.bean.Review;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.service.PostService;
import pers.zhentao.refereeresourceclient.service.ReviewService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/26 21:40.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyReview extends Activity {

    private List<Review> list;
    private MyReviewAdapter adapter;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_post);
        TextView tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("我的评论");
        tvTitle.setVisibility(View.VISIBLE);
        ImageButton btnBack = (ImageButton)findViewById(R.id.back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBack.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        final ListView listView = (ListView)findViewById(R.id.lv_my_post);
        adapter = new MyReviewAdapter(MyReview.this,R.layout.item_my_review,list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog dialog = new AlertDialog.Builder(MyReview.this)
                        .setTitle("删除该评论?")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.get(position).setIsDelete(true);
                                new ReviewService().deleteReview(list.get(position).getId());
//                                list.get(position).update(MyReview.this);
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new PostService().getPost(list.get(position).getPost().getPostId(), new GetListener<Post>() {
                    @Override
                    public void onSuccess(final Post post) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!post.getIsDelete()){
                                    Intent intent = new Intent(MyReview.this,PostDetail.class);
                                    intent.putExtra("post",post);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MyReview.this,"该贴已被删除",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(int errorCode, String result) {

                    }
                });
//                BmobQuery<DeletedPost> query = new BmobQuery<DeletedPost>();
//                query.addWhereEqualTo("postId",list.get(position).getPost().getObjectId());
//                query.count(MyReview.this, DeletedPost.class, new CountListener() {
//                    @Override
//                    public void onSuccess(int i) {
//                        if(i == 0){
//                            BmobQuery<Post> bmobQuery = new BmobQuery<Post>();
//                            bmobQuery.include("user");
//                            bmobQuery.getObject(MyReview.this, list.get(position).getPost().getObjectId(), new GetListener<Post>() {
//                                @Override
//                                public void onSuccess(Post post) {
//                                    Intent intent = new Intent(MyReview.this,PostDetail.class);
//                                    intent.putExtra("post",post);
//                                    startActivity(intent);
//                                }
//
//                                @Override
//                                public void onFailure(int i, String s) {
//
//                                }
//                            });
//                        }else{
//                            Toast.makeText(MyReview.this,"该贴已被删除",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
            }
        });
        new ReviewService().findReview(" where user_id=" + ContextUtil.getUserInstance().getUserId() + " and is_delete=false", "", "", new FindListener<Review>() {
            @Override
            public void onSuccess(final List<Review> li) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.addAll(li);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<Review> query = new BmobQuery<>();
//        query.addWhereEqualTo("user", Common.USER);
//        query.addWhereEqualTo("is_delete", false);
//        query.include("post");
//        query.findObjects(MyReview.this, new FindListener<Review>() {
//            @Override
//            public void onSuccess(List<Review> li) {
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
