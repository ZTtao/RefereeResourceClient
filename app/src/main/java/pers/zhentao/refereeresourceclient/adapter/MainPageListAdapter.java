package pers.zhentao.refereeresourceclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.Post;
import pers.zhentao.refereeresourceclient.listener.CountListener;
import pers.zhentao.refereeresourceclient.service.ReadService;
import pers.zhentao.refereeresourceclient.service.ReviewService;


/**
 * Created by ZhangZT on 2016/7/7 21:32.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MainPageListAdapter extends ArrayAdapter<Post> {

    private View view;
    private List<Post> list_data = null;
    private Map<Integer,View> map_view = null;
    private Post post = null;
    private LayoutInflater inflater = null;
    private Context context = null;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_author;
    private TextView tv_time;
    private TextView tv_review_count;
    private TextView tv_read_count;
    private int resource_id;

    public MainPageListAdapter(Context context, int resource, List<Post> list) {

        super(context, resource,list);
        this.list_data = list;
        this.context = context;
        this.resource_id = resource;

    }

    @Override
    public void notifyDataSetChanged(){
        map_view = new HashMap<>();
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position,View convertView,ViewGroup group) {
        post = getItem(position);
        if(map_view.containsKey(position)){
            view = map_view.get(position);
        }else {
            inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource_id, null);
            tv_title = (TextView) view.findViewById(R.id.tv_mainpage_list_title);
            tv_content = (TextView) view.findViewById(R.id.tv_mainpage_list_content);
            tv_author = (TextView) view.findViewById(R.id.tv_mainpage_list_author);
            tv_time = (TextView) view.findViewById(R.id.tv_mainpage_list_time);
            tv_review_count = (TextView) view.findViewById(R.id.tv_mainpage_list_review);
            tv_read_count = (TextView) view.findViewById(R.id.tv_mainpage_list_read);

            tv_title.setText(post.getTitle() + "");
            tv_content.setText(post.getContent() + "");
            tv_author.setText(post.getUser().getNickName() + "");
            tv_time.setText(post.getCreateTime().toString() + "");
            map_view.put(position,view);
        }
        queryReadAndReview(view, post);
        return view;
    }

    private void queryReadAndReview(final View v,final Post post){
        new ReadService().countRead(post.getPostId(), new CountListener() {
            @Override
            public void onSuccess(int count) {
                ((TextView) v.findViewById(R.id.tv_mainpage_list_read)).setText(count + "");
            }

            @Override
            public void onFailure(int errorCode, String result) {

            }
        });
        new ReviewService().countReview(post.getPostId(), new CountListener() {
            @Override
            public void onSuccess(int count) {
                ((TextView) v.findViewById(R.id.tv_mainpage_list_review)).setText(count + "");
            }

            @Override
            public void onFailure(int errorCode, String result) {

            }
        });
//        BmobQuery<Read> query = new BmobQuery<>();
//        query.addWhereEqualTo("post", post);
//        query.count(MyApplication.getContext(), Read.class, new CountListener() {
//            @Override
//            public void onSuccess(int i) {
//                final int read = i;
//                BmobQuery<Review> query1 = new BmobQuery<>();
//                query1.addWhereEqualTo("post", post);
//                query1.addWhereEqualTo("is_delete",false);
//                query1.count(MyApplication.getContext(), Review.class, new CountListener() {
//                    @Override
//                    public void onSuccess(int i) {
//                        ((TextView) v.findViewById(R.id.tv_mainpage_list_read)).setText(read + "");
//                        ((TextView) v.findViewById(R.id.tv_mainpage_list_review)).setText(i + "");
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
    }
}
