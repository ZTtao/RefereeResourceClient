package pers.zhentao.refereeresourceclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.AddFriendResponse;

/**
 * Created by ZhangZT on 2016/7/22 16:16.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FriendRequestMsgAdapter extends ArrayAdapter<AddFriendResponse> {
    private Context context;
    private int resource;
    private List<AddFriendResponse> list;
    private LayoutInflater inflater;
    private View view;
    private TextView tv_name;
    private TextView tv_content;
    private TextView tv_time;
    private ImageView imgAvatar;
    public FriendRequestMsgAdapter(Context context,int resource,List<AddFriendResponse> list){
        super(context,resource,list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource, null);
        tv_name = (TextView)view.findViewById(R.id.tv_name_friend_request_msg);
        tv_content = (TextView)view.findViewById(R.id.tv_content_friend_request_msg);
        tv_time = (TextView)view.findViewById(R.id.tv_time_friend_request_msg);
        imgAvatar = (ImageView)view.findViewById(R.id.img_avatar_friend_request_msg);
        tv_name.setText(list.get(position).getUser_send().getNickName());
        tv_content.setText(list.get(position).getNote());
        tv_time.setText(list.get(position).getCreateTime().toString());
//        Bitmap bitmap = null;
//        if(list.get(position).getUser_send().getAvatar()!=null&&!list.get(position).getUser_send().getAvatar().equals(""))
//        {
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(list.get(position).getUser_send().getObjectId());
//            if(bitmap == null){
//                BmobFile bmobFile = new BmobFile(list.get(position).getUser_send().getObjectId()+".jpg","",list.get(position).getUser_send().getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",list.get(position).getUser_send().getObjectId()+".jpg");
//                bmobFile.download(context, file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        imgAvatar.setImageBitmap(BitmapFactory.decodeFile(s));
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//
//            }else
//                imgAvatar.setImageBitmap(bitmap);
//        }else
        imgAvatar.setImageResource(R.mipmap.default_avatar);
        return view;
    }
}
