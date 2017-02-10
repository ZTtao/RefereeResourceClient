package pers.zhentao.refereeresourceclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.activity.PerfectInfo;
import pers.zhentao.refereeresourceclient.bean.Review;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/9 10:58.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class PostDetailAdapter extends ArrayAdapter<Review> {

    private Context context = null;
    private int resource;
    private List<Review> list_data = null;
    private View view = null;
    private LayoutInflater inflater = null;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_author;
    private TextView tv_time;
    private TextView tv_review_count;
    private TextView tv_slash;
    private TextView tv_read;
    private Review review;

    public PostDetailAdapter(Context context, int resource, List<Review> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list_data = objects;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup group){
        review = getItem(position);
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource, null);
        tv_title = (TextView)view.findViewById(R.id.tv_mainpage_list_title);
        tv_content = (TextView)view.findViewById(R.id.tv_mainpage_list_content);
        tv_author = (TextView)view.findViewById(R.id.tv_mainpage_list_author);
        tv_time = (TextView)view.findViewById(R.id.tv_mainpage_list_time);
        tv_review_count = (TextView)view.findViewById(R.id.tv_mainpage_list_review);
        tv_slash = (TextView)view.findViewById(R.id.tv_mainpage_list_slash);
        tv_read = (TextView)view.findViewById(R.id.tv_mainpage_list_read);

        tv_slash.setVisibility(View.GONE);
        tv_read.setVisibility(View.GONE);
        tv_title.setVisibility(View.GONE);
        tv_review_count.setVisibility(View.GONE);
        tv_content.setText(review.getContent() + "");
        tv_content.setMaxLines(100);
        tv_author.setText(review.getUser().getNickName() + "");
        tv_time.setText(review.getTime().toString()+"");
        tv_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PerfectInfo.class);
                if(review.getUser().getUserId().equals(ContextUtil.getUserInstance().getUserId()))Common.INFO_EDIT_FLAG = Common.USER_BROWSE;
                else Common.INFO_EDIT_FLAG = Common.OTHER_BROWSE;
                intent.putExtra("user",review.getUser());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
