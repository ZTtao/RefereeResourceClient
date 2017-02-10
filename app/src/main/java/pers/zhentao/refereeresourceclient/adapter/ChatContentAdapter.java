package pers.zhentao.refereeresourceclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.activity.PerfectInfo;
import pers.zhentao.refereeresourceclient.bean.IMMessage;
import pers.zhentao.refereeresourceclient.bean.MessageType;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.service.UserService;
import pers.zhentao.refereeresourceclient.util.CommonUtil;
import pers.zhentao.refereeresourceclient.util.ContextUtil;


/**
 * Created by ZhangZT on 2016/7/17 13:10.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ChatContentAdapter extends ArrayAdapter<IMMessage> {

    private Context context;
    private int resource;
    private List<IMMessage> list;
    public ChatContentAdapter(Context context, int resource, List<IMMessage> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }
    @Override
    public View getView(final int position,View convertView,ViewGroup group){
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource,null);
            holder.imageAvatarUser = (ImageView)convertView.findViewById(R.id.img_btn_chat_content_user);
            holder.imageAvatarFriend = (ImageView)convertView.findViewById(R.id.img_btn_chat_content_friend);
            holder.textContentUser = (TextView)convertView.findViewById(R.id.tv_chat_content_user);
            holder.textContentFriend = (TextView)convertView.findViewById(R.id.tv_chat_content_friend);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.imageAvatarUser.setVisibility(View.GONE);
        holder.textContentUser.setVisibility(View.GONE);
        holder.imageAvatarFriend.setVisibility(View.GONE);
        holder.textContentFriend.setVisibility(View.GONE);

        Bitmap bitmap = null;
        if (!list.get(position).getFromId().equals(ContextUtil.getUserInstance().getUserId())) {
                //接收的消息
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(list.get(position).getFromId());
//            holder.imageAvatarFriend.setImageBitmap(bitmap);
//            if (bitmap == null && list.get(position).getBmobIMUserInfo().getAvatar()!=null && !list.get(position).getBmobIMUserInfo().getAvatar().equals("")) {
//                //本地无头像 and 已设置头像 所以 从服务器取头像
//                BmobFile bmobFile = new BmobFile(list.get(position).getFromId() + ".jpg", "", list.get(position).getBmobIMUserInfo().getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory() + "/refereeResource", list.get(position).getFromId() + ".jpg");
//                bmobFile.download(context, file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        holder.imageAvatarFriend.setImageBitmap(BitmapFactory.decodeFile(s));
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//            }else if(bitmap == null && list.get(position).getBmobIMUserInfo().getAvatar().equals("")){
//                //无头像，设置默认头像
                holder.imageAvatarFriend.setImageResource(R.mipmap.default_avatar);
//            }
            holder.imageAvatarFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Common.INFO_EDIT_FLAG = Common.OTHER_BROWSE;
                    new UserService().getUser(list.get(position).getFromId(), new GetListener<User>() {
                        @Override
                        public void onSuccess(User user) {
                            Intent intent = new Intent(context, PerfectInfo.class);
                            intent.putExtra("user", user);
                            context.startActivity(intent);
                        }

                        @Override
                        public void onFailure(int errorCode, String result) {

                        }
                    });
//                    BmobQuery<CommonUser> query = new BmobQuery<>();
//                    query.getObject(context, list.get(position).getFromId(), new GetListener<CommonUser>() {
//                        @Override
//                        public void onSuccess(CommonUser user) {
//                            Intent intent = new Intent(context, PerfectInfo.class);
//                            intent.putExtra("user", user);
//                            context.startActivity(intent);
//                        }
//                        @Override
//                        public void onFailure(int i, String s) {}
//                    });
                }
            });
            holder.imageAvatarFriend.setVisibility(View.VISIBLE);
        } else {
            //发出的消息
//            bitmap = CommonManager.getInstance().getAvatarAtLocal(Common.USER.getObjectId());
//            holder.imageAvatarUser.setImageBitmap(bitmap);
//            if (bitmap == null && Common.USER.getAvatar() != null && !Common.USER.getAvatar().equals("")) {
//                BmobFile bmobFile = new BmobFile(Common.USER.getObjectId() + ".jpg", "", Common.USER.getAvatar());
//                File file = new File(Environment.getExternalStorageDirectory() + "/refereeResource", Common.USER.getObjectId() + ".jpg");
//                bmobFile.download(context, file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        holder.imageAvatarUser.setImageBitmap(BitmapFactory.decodeFile(s));
//                    }
//                    @Override
//                    public void onFailure(int i, String s) {}
//                });
//            }else{
                holder.imageAvatarUser.setImageResource(R.mipmap.default_avatar);
//            }
            holder.imageAvatarUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Common.INFO_EDIT_FLAG = Common.USER_BROWSE;
                    context.startActivity(new Intent(context, PerfectInfo.class));
                }
            });
            holder.imageAvatarUser.setVisibility(View.VISIBLE);
        }
            if (list.get(position).getMsgType().equals(MessageType.TEXT)) {
                //文本消息
                holder.textContentFriend.setText("");
                holder.textContentUser.setText("");
                String text = list.get(position).getContent();
                int i = 0;
                for (; i < text.length() - 10; i++) {
                    if (text.substring(i, i + 7).equals("[/micro") && text.charAt(i + 10) == ']') {
                        int num = Integer.parseInt(text.substring(i + 7, i + 10));
                        HashMap<String, Object> hashMap = CommonUtil.getInstance().findMicroExpressionByTag(num);
                        SpannableString spannableString = (SpannableString) hashMap.get("spannableString");
                        int resource = (int) hashMap.get("resource");
                        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resource);
                        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 40, 35);
                        ImageSpan imageSpan = new ImageSpan(context, bm);
                        spannableString.setSpan(imageSpan, 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        if (!list.get(position).getFromId().equals(ContextUtil.getUserInstance().getUserId()))
                            holder.textContentFriend.append(spannableString);
                        else
                            holder.textContentUser.append(spannableString);
                        i += 10;
                    } else {
                        if (!list.get(position).getFromId().equals(ContextUtil.getUserInstance().getUserId()))
                            holder.textContentFriend.append(text.substring(i, i + 1));
                        else
                            holder.textContentUser.append(text.substring(i, i + 1));
                    }
                }
                if (!list.get(position).getFromId().equals(ContextUtil.getUserInstance().getUserId())) {
                    holder.textContentFriend.append(text.substring(i));
                    holder.textContentFriend.setVisibility(View.VISIBLE);
                }else {
                    holder.textContentUser.append(text.substring(i));
                    holder.textContentUser.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
    }
    static class ViewHolder{
        ImageView imageAvatarUser;
        TextView textContentUser;

        ImageView imageAvatarFriend;
        TextView textContentFriend;
    }
}
