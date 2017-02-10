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
 * Created by ZhangZT on 2016/8/21 13:33.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ShowApplyAdapter extends ArrayAdapter<ApplyRefereeMessage> {

    private List<ApplyRefereeMessage> list;
    private int resource;
    private Context context;

    public ShowApplyAdapter(Context context, int resource, List<ApplyRefereeMessage> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.resource = resource;
        this.context = context;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,null);
            viewHolder = new ViewHolder();
            viewHolder.tvRank = (TextView)convertView.findViewById(R.id.tv_item_show_apply_rank);
            viewHolder.tvNote = (TextView)convertView.findViewById(R.id.tv_item_show_apply_note);
            viewHolder.tvUser = (TextView)convertView.findViewById(R.id.tv_item_show_apply_user);
            viewHolder.tvTime = (TextView)convertView.findViewById(R.id.tv_item_show_apply_time);
            viewHolder.tvStatus = (TextView)convertView.findViewById(R.id.tv_item_show_apply_status);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tvRank.setText(list.get(position).getReferee().getRank());
        viewHolder.tvNote.setText(list.get(position).getNote());
        viewHolder.tvUser.setText(list.get(position).getRefereeUser().getNickName());
        viewHolder.tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(list.get(position).getApplyDate()));
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
        TextView tvRank;
        TextView tvNote;
        TextView tvUser;
        TextView tvTime;
        TextView tvStatus;
    }
}
