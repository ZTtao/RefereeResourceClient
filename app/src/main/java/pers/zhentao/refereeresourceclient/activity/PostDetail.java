package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.PostDetailAdapter;
import pers.zhentao.refereeresourceclient.bean.Like;
import pers.zhentao.refereeresourceclient.bean.Post;
import pers.zhentao.refereeresourceclient.bean.Review;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.CountListener;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.service.LikeService;
import pers.zhentao.refereeresourceclient.service.ReadService;
import pers.zhentao.refereeresourceclient.service.ReviewService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/8 23:08.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class PostDetail extends Activity {

    private ImageButton btn_back;
    private ImageView btn_like;
    private ImageView btn_user_icon;
    private ImageButton btn_send;

    private TextView tv_top_title;
    private TextView tv_author;
    private TextView tv_time;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_read_count;
    private TextView tv_review_count;
    private TextView tv_like_count;

    private EditText et_review;

    private Intent intent;

    private Post post;
    private Review review_new;
    private List<Review> list_review = null;
    private PostDetailAdapter adapter = null;
    private Boolean liked = false;
    private Boolean hadLike = false;
    private ListView listView;
    private View header_view;
    private Like like = null;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_post_detail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        init();
    }
    private void init(){
        intent = this.getIntent();
        post = (Post)intent.getSerializableExtra("post");
        header_view = getLayoutInflater().inflate(R.layout.header_view_post_detail,null);
        btn_back = (ImageButton)findViewById(R.id.back_btn);
        btn_like = (ImageView)header_view.findViewById(R.id.img_btn_post_detail_like);
        btn_user_icon = (ImageView)header_view.findViewById(R.id.img_btn_post_detail_author);

        tv_top_title = (TextView)findViewById(R.id.title_text);
        tv_author = (TextView)header_view.findViewById(R.id.btn_post_detail_author);
        tv_time = (TextView)header_view.findViewById(R.id.tv_post_detail_time);
        tv_title = (TextView)header_view.findViewById(R.id.tv_post_detail_title);
        tv_content = (TextView)header_view.findViewById(R.id.tv_post_detail_content);
        tv_read_count = (TextView)header_view.findViewById(R.id.tv_post_detail_read_count);
        tv_review_count = (TextView)header_view.findViewById(R.id.tv_post_detail_review_count);
        tv_like_count = (TextView)header_view.findViewById(R.id.tv_post_detail_like_count);

        listView = (ListView)findViewById(R.id.lv_post_detail_review);
        btn_send = (ImageButton)findViewById(R.id.img_btn_post_detail_send);
        et_review = (EditText)findViewById(R.id.et_post_detail_review);

        listView.addHeaderView(header_view,null,false);
        list_review = new ArrayList<>();
        adapter = new PostDetailAdapter(PostDetail.this,R.layout.item_mainpage_list,list_review);
        listView.setAdapter(adapter);
        btn_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetail.this, PerfectInfo.class);
                intent.putExtra("user", post.getUser());
                Common.INFO_EDIT_FLAG = Common.OTHER_BROWSE;
                startActivity(intent);
            }
        });
        queryLike();
        queryLikeCount();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back.setVisibility(View.VISIBLE);
        tv_top_title.setVisibility(View.GONE);

//        Bitmap bitmap = null;
//        if(post.getUser().getAvatar()!=null&&!post.getUser().getAvatar().equals(""))
//        {
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(post.getUser().getObjectId());
//            if(bitmap == null){
//                BmobFile bmobFile = new BmobFile(post.getUser().getObjectId()+".jpg","", post.getUser().getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource", post.getUser().getObjectId()+".jpg");
//                bmobFile.download(PostDetail.this, file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        btn_user_icon.setImageBitmap(BitmapFactory.decodeFile(s));
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//
//            }else
//                btn_user_icon.setImageBitmap(bitmap);
//        }else
        btn_user_icon.setImageResource(R.mipmap.default_avatar);

        tv_author.setText(post.getUser().getNickName()+"");
        tv_time.setText(post.getCreateTime()+"");
        tv_title.setText(post.getTitle()+"");
        tv_content.setText(post.getContent()+"");
        tv_read_count.setText(post.getReadCount()+"");
        tv_review_count.setText(post.getReviewCount()+"");
        tv_like_count.setText(post.getLikeCount() + "");

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
//                        BmobQuery<Review> query = new BmobQuery<>();
//                        String start = list_review.get(view.getLastVisiblePosition()-1).getCreatedAt();
//                        Date date = null;
//                        try {
//                            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(date);
//                        calendar.add(Calendar.SECOND,1);
//                        date = calendar.getTime();
//                        query.addWhereGreaterThan("createdAt", new BmobDate(date));
//                        query.addWhereEqualTo("post", post);
//                        query.addWhereEqualTo("is_delete",false);
//                        query.setLimit(20);
//                        query.order("updatedAt");
//                        query.include("user");
//                        query.findObjects(PostDetail.this, new FindListener<Review>() {
//                            @Override
//                            public void onSuccess(List<Review> list) {
//                                if(list.size()==0){
//                                    Toast.makeText(PostDetail.this,"无更多内容",Toast.LENGTH_SHORT).show();
//                                }else {
//                                    for (int i = 0; i < list.size(); i++) {
//                                        list_review.add(list.get(i));
//                                    }
//                                    adapter.notifyDataSetChanged();
//                                    queryReviewCount();
//                                }
//                            }
//
//                            @Override
//                            public void onError(int i, String s) {
//                                Toast.makeText(PostDetail.this, "failed" + s, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_review.getText().toString().equals("")) {
                    review_new = new Review();
                    review_new.setContent(et_review.getText().toString() + "");
                    review_new.setPost(post);
                    review_new.setUser(ContextUtil.getUserInstance());
                    review_new.setTime(Timestamp.valueOf(new Date().toString()));
                    new ReviewService().saveReview(review_new, new SaveListener() {
                        @Override
                        public void onSuccess(int id) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    et_review.setText("");
                                    if(list_review.size()<=20)
                                        queryReview();
                                    Toast.makeText(PostDetail.this, "评论成功", Toast.LENGTH_SHORT).show();
                                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(PostDetail.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            });
                        }

                        @Override
                        public void onFailure(int errorCode, String result) {
                            Toast.makeText(PostDetail.this, "评论失败", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    review_new.save(PostDetail.this, new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            et_review.setText("");
//                            if(list_review.size()<=20)
//                                queryReview();
//                            Toast.makeText(PostDetail.this, "评论成功", Toast.LENGTH_SHORT).show();
//                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(PostDetail.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            Toast.makeText(PostDetail.this, "评论失败", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                } else {
                    Toast.makeText(PostDetail.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        queryRead();
        queryReview();
    }
    private void queryRead(){
        new ReadService().countRead(post.getPostId(), new CountListener() {
            @Override
            public void onSuccess(final int count) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_read_count.setText(count+"");
                    }
                });
            }

            @Override
            public void onFailure(int errorCode, String result) {

            }
        });
//        BmobQuery<Read> bmobQuery = new BmobQuery<>();
//        bmobQuery.addWhereEqualTo("post", post);
//        bmobQuery.count(PostDetail.this, Read.class, new CountListener() {
//            @Override
//            public void onSuccess(int i) {
//                tv_read_count.setText(i+"");
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
    }
    private void queryReview(){
        new ReviewService().findReview(" where post_id=" + post.getPostId() + " and is_delete=false", "", "", new FindListener<Review>() {
            @Override
            public void onSuccess(final List<Review> li) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list_review.clear();
                        for (int i = 0; i < li.size(); i++) {
                            list_review.add(li.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        queryReviewCount();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<Review> query = new BmobQuery<>();
//        query.addWhereEqualTo("post", post);
//        query.addWhereEqualTo("is_delete",false);
//        query.setLimit(20);
//        query.order("updatedAt");
//        query.include("user");
//        query.findObjects(PostDetail.this, new FindListener<Review>() {
//            @Override
//            public void onSuccess(List<Review> list) {
//                list_review.clear();
//                for (int i = 0; i < list.size(); i++) {
//                    list_review.add(list.get(i));
//                }
//                adapter.notifyDataSetChanged();
//                queryReviewCount();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Toast.makeText(PostDetail.this, "failed" + s, Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    private void queryReviewCount(){
        new ReviewService().countReview(post.getPostId(), new CountListener() {
            @Override
            public void onSuccess(final int count) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_review_count.setText(count + "");
                    }
                });
            }

            @Override
            public void onFailure(int errorCode, String result) {

            }
        });
//        BmobQuery<Review> query = new BmobQuery<>();
//        query.addWhereEqualTo("post", post);
//        query.addWhereEqualTo("is_delete", false);
//        query.count(PostDetail.this, Review.class, new CountListener() {
//            @Override
//            public void onSuccess(int i) {
//                tv_review_count.setText(i + "");
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
    }
    private void queryLike(){
        new LikeService().isLiked(post.getPostId(), ContextUtil.getUserInstance().getUserId(), new CountListener() {
            @Override
            public void onSuccess(int count) {
                if(count > 0 ){
                    liked = true;
                    btn_like.setImageResource(R.drawable.icon_cancle_like);
                }else{
                    liked = false;
                    btn_like.setImageResource(R.drawable.icon_like);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_like.setVisibility(View.VISIBLE);
                        btn_like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!liked) {
                                    new LikeService().like(post.getPostId(), ContextUtil.getUserInstance().getUserId(), new SaveListener() {
                                        @Override
                                        public void onSuccess(int id) {
                                            queryLikeCount();
                                        }

                                        @Override
                                        public void onFailure(int errorCode, String result) {

                                        }
                                    });


//
//                                    if (hadLike) {
//                                        like.setIs_delete(false);
//                                        like.update(PostDetail.this, new UpdateListener() {
//                                            @Override
//                                            public void onSuccess() {
//
//                                            }
//
//                                            @Override
//                                            public void onFailure(int i, String s) {
//
//                                            }
//                                        });
//                                    } else {
//                                        like = new Like();
//                                        like.setPost(post);
//                                        like.setUser(Common.USER);
//                                        like.setCreate_time(new Date());
//                                        like.save(PostDetail.this, new SaveListener() {
//                                            @Override
//                                            public void onSuccess() {
//                                                queryLikeCount();
//                                            }
//
//                                            @Override
//                                            public void onFailure(int i, String s) {
//
//                                            }
//                                        });
//                                    }
                                    liked = true;
                                } else {
                                    new LikeService().dislike(post.getPostId(), ContextUtil.getUserInstance().getUserId(), new SaveListener() {
                                        @Override
                                        public void onSuccess(int id) {
                                            queryLikeCount();
                                        }

                                        @Override
                                        public void onFailure(int errorCode, String result) {

                                        }
                                    });
//                                    like.setIs_delete(true);
//                                    like.update(PostDetail.this, new UpdateListener() {
//                                        @Override
//                                        public void onSuccess() {
//
//                                        }
//
//                                        @Override
//                                        public void onFailure(int i, String s) {
//
//                                        }
//                                    });
                                    liked = false;
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        btn_like.setImageResource(liked?R.drawable.icon_cancle_like:R.drawable.icon_like);
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(int errorCode, String result) {

            }
        });
//        BmobQuery<Like> query = new BmobQuery<>();
//        BmobQuery<Like> query1 = new BmobQuery<>();
//        query1.addWhereEqualTo("user", Common.USER);
//        BmobQuery<Like> query2 = new BmobQuery<>();
//        query2.addWhereEqualTo("post", post);
//        List<BmobQuery<Like>> bmobQueryList = new ArrayList<>();
//        bmobQueryList.add(query1);
//        bmobQueryList.add(query2);
//        query.and(bmobQueryList);
//        query.findObjects(PostDetail.this, new FindListener<Like>() {
//            @Override
//            public void onSuccess(List<Like> list_like) {
//                if (list_like.size() > 0) {
//                    hadLike = true; //赞过
//                    like = list_like.get(0);
//                    if (!list_like.get(0).getIs_delete()) {
//                        //已赞
//                        liked = true;
//                        btn_like.setImageResource(R.drawable.icon_cancle_like);
//                    } else {
//                        //已取消赞
//                        liked = false;
//                        btn_like.setImageResource(R.drawable.icon_like);
//                    }
//                } else {
//                    //未赞
//                    liked = false;
//                    btn_like.setImageResource(R.drawable.icon_like);
//                }
//
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
    }
    private void queryLikeCount(){
        new LikeService().getLikeCount(post.getPostId(), new CountListener() {
            @Override
            public void onSuccess(final int count) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_like_count.setText(count+"");
                    }
                });
            }

            @Override
            public void onFailure(int errorCode, String result) {

            }
        });
//        BmobQuery<Like> bmobQuery = new BmobQuery<>();
//        BmobQuery<Like> bmobQuery1 = new BmobQuery<>();
//        BmobQuery<Like> bmobQuery2 = new BmobQuery<>();
//        bmobQuery1.addWhereEqualTo("is_delete",false);
//        bmobQuery2.addWhereEqualTo("post", post);
//        List<BmobQuery<Like>> bmobQueryList = new ArrayList<>();
//        bmobQueryList.add(bmobQuery1);
//        bmobQueryList.add(bmobQuery2);
//        bmobQuery.and(bmobQueryList);
//        bmobQuery.count(PostDetail.this, Like.class, new CountListener() {
//            @Override
//            public void onSuccess(int i) {
//
//            }
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
    }
}
