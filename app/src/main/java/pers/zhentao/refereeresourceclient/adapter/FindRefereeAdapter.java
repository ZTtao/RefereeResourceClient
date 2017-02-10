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
import pers.zhentao.refereeresourceclient.bean.FindRefereeMessage;

/**
 * Created by ZhangZT on 2016/8/20 21:04.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FindRefereeAdapter extends ArrayAdapter<FindRefereeMessage> {

    private Context context;
    private int resource;
    private List<FindRefereeMessage> list;
    public FindRefereeAdapter(Context context, int resource, List<FindRefereeMessage> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }
    @Override
     public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,null);
            viewHolder = new ViewHolder();
            viewHolder.tvTime = (TextView)convertView.findViewById(R.id.tv_item_find_referee_time);
            viewHolder.tvAddress = (TextView)convertView.findViewById(R.id.tv_item_find_referee_address);
            viewHolder.tvUser = (TextView)convertView.findViewById(R.id.tv_item_find_referee_user);
            viewHolder.tvPublishTime = (TextView)convertView.findViewById(R.id.tv_item_find_referee_publish_time);
            viewHolder.tvApplyCount = (TextView)convertView.findViewById(R.id.tv_item_find_referee_apply_count);
            viewHolder.tvReadCount = (TextView)convertView.findViewById(R.id.tv_item_find_referee_read);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        viewHolder.tvTime.setText(format.format(list.get(position).getTime()));
        viewHolder.tvAddress.setText(list.get(position).getAddress());
        viewHolder.tvUser.setText(list.get(position).getUser().getNickName());
        viewHolder.tvPublishTime.setText(format.format(list.get(position).getPublishTime()));
        viewHolder.tvApplyCount.setText(list.get(position).getApplyCount()+"");
        viewHolder.tvReadCount.setText(list.get(position).getReadCount()+"");

        return convertView;
    }
    static class ViewHolder{
        TextView tvTime;
        TextView tvAddress;
        TextView tvUser;
        TextView tvPublishTime;
        TextView tvApplyCount;
        TextView tvReadCount;
    }
}
