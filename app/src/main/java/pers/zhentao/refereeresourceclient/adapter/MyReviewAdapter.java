package pers.zhentao.refereeresourceclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.Review;

/**
 * Created by ZhangZT on 2016/7/26 21:47.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyReviewAdapter extends ArrayAdapter<Review> {

    private Context context;
    private int resource;
    private List<Review> list;
    public MyReviewAdapter(Context context,int resource,List<Review> list){
        super(context,resource,list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tv_my_review_title);
        TextView tvContent = (TextView)convertView.findViewById(R.id.tv_my_review_content);
        TextView tvTime = (TextView)convertView.findViewById(R.id.tv_my_review_time);
        tvTitle.setText(list.get(position).getPost().getTitle());
        tvContent.setText(list.get(position).getContent());
        tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(list.get(position).getTime()));
        return convertView;
    }
}
