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
import pers.zhentao.refereeresourceclient.bean.User;

/**
 * Created by ZhangZT on 2016/7/13 20:24.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class SearchFriendAdapter extends ArrayAdapter<User> {

    private View view;
    private Context context;
    private int resource;
    private List<User> list;
    private LayoutInflater inflater;
    private ImageView img_avatar;
    private TextView tv_name;

    public SearchFriendAdapter(Context context, int resource, List<User> list) {
        super(context,resource,list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource, null);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup group){
        if(convertView == null) {
            convertView = view;
        }
        img_avatar = (ImageView) convertView.findViewById(R.id.img_friend_list_avatar);
        tv_name = (TextView) convertView.findViewById(R.id.tv_friend_list_name);
        tv_name.setText(list.get(position).getNickName());
//        Bitmap bitmap = null;
//        if(list.get(position).getAvatar()!=null&&!list.get(position).getAvatar().equals(""))
//        {
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(list.get(position).getObjectId());
//            if(bitmap == null){
//                BmobFile bmobFile = new BmobFile(list.get(position).getObjectId()+".jpg","",list.get(position).getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",list.get(position).getObjectId()+".jpg");
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
        return convertView;
    }
}
