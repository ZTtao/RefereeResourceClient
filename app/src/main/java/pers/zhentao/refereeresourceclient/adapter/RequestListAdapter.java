package pers.zhentao.refereeresourceclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.activity.FriendRequestDetail;
import pers.zhentao.refereeresourceclient.bean.AddFriendRequest;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshFriendRequestListEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.globalvariable.CommonManager;
import pers.zhentao.refereeresourceclient.service.FriendRequestService;

/**
 * Created by ZhangZT on 2016/7/16 13:15.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class RequestListAdapter extends ArrayAdapter<AddFriendRequest> {

    private Context context;
    private int resource;
    private List<AddFriendRequest> list;
    private LayoutInflater inflater;
    private View view;
    private ImageView imageView;
    private TextView tv_name;
    private TextView tv_note;
    private TextView tv_status;
    private Button btn_status;
    private RelativeLayout relativeLayout;
    private AddFriendRequest request;

    public RequestListAdapter(Context context, int resource, List<AddFriendRequest> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;

    }

    @Override
    public View getView(final int position,View convertView,ViewGroup group){
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource, null);
        imageView = (ImageView)view.findViewById(R.id.img_friend_request_list_avatar);
        tv_name = (TextView)view.findViewById(R.id.tv_friend_request_list_name);
        tv_note = (TextView)view.findViewById(R.id.tv_friend_request_list_note);
        tv_status = (TextView)view.findViewById(R.id.tv_friend_request_list_status);
        btn_status = (Button)view.findViewById(R.id.btn_friend_request_list_status);

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
//                        imageView.setImageBitmap(BitmapFactory.decodeFile(s));
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//
//            }else
//                imageView.setImageBitmap(bitmap);
//        }else
        imageView.setImageResource(R.mipmap.default_avatar);

        relativeLayout = (RelativeLayout)view.findViewById(R.id.ly_left_item_friend_request_list);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FriendRequestDetail.class);
                intent.putExtra("request",list.get(position));
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
        request = list.get(position);
        tv_name.setText(request.getUser_send().getNickName());
        tv_note.setText(request.getNote());
        if(request.getStatus()== Common.MSG_ADDED){
            tv_status.setText("已添加");
            tv_status.setVisibility(View.VISIBLE);
            btn_status.setVisibility(View.GONE);
        }else if(request.getStatus()==Common.MSG_REJECTED){
            tv_status.setText("已拒绝");
            tv_status.setVisibility(View.VISIBLE);
            btn_status.setVisibility(View.GONE);
        }else {
            tv_status.setVisibility(View.GONE);
            btn_status.setVisibility(View.VISIBLE);
            btn_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonManager.getInstance().sendAddFriendResponseAgreeMessage(context,list.get(position).getUser_send());
                    list.get(position).setStatus(Common.MSG_ADDED);
                    new FriendRequestService().updateAddRequest(list.get(position));
//                    list.get(position).update(MyApplication.getContext());
                    tv_status.setText("已添加");
                    tv_status.setVisibility(View.VISIBLE);
                    btn_status.setVisibility(View.GONE);
                    EventBus.getDefault().post(new FreshFriendRequestListEvent(position, list.get(position)));
                }
            });
        }

        return view;
    }
}
