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
import pers.zhentao.refereeresourceclient.bean.Friend;

/**
 * Created by ZhangZT on 2016/7/13 19:31.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FriendListAdapter extends ArrayAdapter<Friend> {

    private Context context;
    private int resource;
    private List<Friend> list;
    private View view;
    private ImageView img_avatar;
    private TextView tv_name;
    private LayoutInflater inflater;

    public FriendListAdapter(Context context, int resource, List<Friend> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;

    }

    @Override
    public View getView(int position,View convertView,ViewGroup group){
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource, null);
        img_avatar = (ImageView)view.findViewById(R.id.img_friend_list_avatar);
        tv_name = (TextView)view.findViewById(R.id.tv_friend_list_name);
        tv_name.setText(list.get(position).getFriend().getNickName());
//        Bitmap bitmap = null;
//        if(list.get(position).getFriend().getAvatar()!=null&&!list.get(position).getFriend().getAvatar().equals(""))
//        {
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(list.get(position).getFriend().getObjectId());
//            if(bitmap == null){
//                BmobFile bmobFile = new BmobFile(list.get(position).getFriend().getObjectId()+".jpg","",list.get(position).getFriend().getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",list.get(position).getFriend().getObjectId()+".jpg");
//                bmobFile.download(context, file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        img_avatar.setImageBitmap(BitmapFactory.decodeFile(s));
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//
//            }else
//                img_avatar.setImageBitmap(bitmap);
//        }else
        img_avatar.setImageResource(R.mipmap.default_avatar);
        return view;
    }
}
