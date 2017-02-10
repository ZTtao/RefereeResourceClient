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
import pers.zhentao.refereeresourceclient.bean.ApplyRefereeMessage;
import pers.zhentao.refereeresourceclient.globalvariable.Common;

/**
 * Created by ZhangZT on 2016/8/21 16:01.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyApplyAdapter extends ArrayAdapter<ApplyRefereeMessage> {

    private Context context;
    private int resource;
    private List<ApplyRefereeMessage> list;
    public MyApplyAdapter(Context context, int resource, List<ApplyRefereeMessage> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resource,null);
            viewHolder.tvTime = (TextView)convertView.findViewById(R.id.tv_item_my_apply_time);
            viewHolder.tvAddress = (TextView)convertView.findViewById(R.id.tv_item_my_apply_address);
            viewHolder.tvPublishTime = (TextView)convertView.findViewById(R.id.tv_item_my_apply_publish_time);
            viewHolder.tvStatus = (TextView)convertView.findViewById(R.id.tv_item_my_apply_status);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        viewHolder.tvTime.setText(format.format(list.get(position).getFindRefereeMessage().getTime()));
        viewHolder.tvAddress.setText(list.get(position).getFindRefereeMessage().getAddress());
        viewHolder.tvPublishTime.setText(format.format(list.get(position).getApplyDate()));
        String status = "";
        switch (list.get(position).getStatus()){
            case Common.APPLY_WAIT:
                status = "等待结果";
                break;
            case Common.APPLY_ACCEPT:
                status = "已接受";
                break;
            case Common.APPLY_REJECT:
                status = "已拒绝";
                break;
        }
        viewHolder.tvStatus.setText(status);
        return convertView;
    }
    static class ViewHolder{
        TextView tvTime;
        TextView tvAddress;
        TextView tvPublishTime;
        TextView tvStatus;
    }
}
