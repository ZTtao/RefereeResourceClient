package pers.zhentao.refereeresourceclient.adapter;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.IMConversation;
import pers.zhentao.refereeresourceclient.bean.MessageType;
import pers.zhentao.refereeresourceclient.util.CommonUtil;

/**
 * Created by ZhangZT on 2016/7/13 18:39.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ChatListAdapter extends ArrayAdapter<IMConversation> {

    private Context context;
    private List<IMConversation> list;
    private int resource;

    public ChatListAdapter(Context context, int resource, List<IMConversation> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup group){
        final ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,null);
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.tv_chat_list_name);
            holder.content = (TextView)convertView.findViewById(R.id.tv_chat_list_content);
            holder.time = (TextView)convertView.findViewById(R.id.tv_chat_list_time);
            holder.avatar = (ImageView)convertView.findViewById(R.id.img_chat_list_avatar);
            holder.redHint = (ImageView) convertView.findViewById(R.id.img_red_hint_chat_list);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(list.get(position).getToUser().getNickName());
//            BmobQuery<CommonUser> query = new BmobQuery<>();
//            query.getObject(MyApplication.getContext(), list.get(position).getConversationId(), new GetListener<CommonUser>() {
//                @Override
//                public void onSuccess(CommonUser user) {
//                    holder.name.setText(user.getNickName());
//                }
//                @Override
//                public void onFailure(int i, String s) {}
//            });
//            Bitmap bitmap = CommonManager.getInstance().getAvatarAtLocal(list.get(position).getConversationId());
//            if (bitmap == null && list.get(position).getConversationIcon()!=null &&!list.get(position).getConversationIcon().equals("")) {
//                final File file = new File(Environment.getExternalStorageDirectory() + "/refereeResource", list.get(position).getConversationId() + ".jpg");
//                BmobFile bmobFile = new BmobFile(list.get(position).getConversationId() + ".jpg", "", list.get(position).getConversationIcon());
//                bmobFile.download(context, file, new DownloadFileListener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        Bitmap bitmap1 = BitmapFactory.decodeFile(s);
//                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = 4;
//                        options.inJustDecodeBounds = false;
//                        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//                        bitmap1 = BitmapFactory.decodeStream(inputStream,null,options);
//                        try {
//                            outputStream.close();
//                            inputStream.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        holder.avatar.setImageBitmap(bitmap1);
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        LogUtil.v("failure", s);
//                    }
//                });
//
//            }
//            if (bitmap == null) {
                holder.avatar.setImageResource(R.mipmap.default_avatar);
//            }
//            else {
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 4;
//                options.inJustDecodeBounds = false;
//                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//                bitmap = BitmapFactory.decodeStream(inputStream,null,options);
//                try {
//                    outputStream.close();
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                holder.avatar.setImageBitmap(bitmap);
//            }
            if (list.get(position).getMessages().size() > 0) {
                if(list.get(position).getMessages().get(0).getMsgType().equals(MessageType.TEXT))
                {   holder.content.setText("");
                    String text = list.get(position).getMessages().get(0).getContent();
                    int i=0;
                    for(;i<text.length()-10;i++){
                        if(text.substring(i,i+7).equals("[/micro") && text.charAt(i+10) == ']'){
                            int num = Integer.parseInt(text.substring(i+7,i+10));
                            HashMap<String,Object> hashMap = CommonUtil.getInstance().findMicroExpressionByTag(num);
                            SpannableString spannableString = (SpannableString)hashMap.get("spannableString");
                            int resource = (int)hashMap.get("resource");
                            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),resource);
                            bm = ThumbnailUtils.extractThumbnail(bm, 40, 35);
                            ImageSpan imageSpan = new ImageSpan(context,bm);
                            spannableString.setSpan(imageSpan,0,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            holder.content.append(spannableString);
                            i+=10;
                        }else {
                            holder.content.append(text.substring(i,i+1));
                        }
                    }
                    holder.content.append(text.substring(i));
                }
                Date date = new Date(list.get(position).getMessages().get(0).getUpdateTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                holder.time.setText(dateFormat.format(date));
            }
            if (list.get(position).getUnreadCount() > 0) {
                holder.redHint.setVisibility(View.VISIBLE);
            }else {
                holder.redHint.setVisibility(View.GONE);
            }
        return convertView;
    }
    static class ViewHolder{
        TextView name;
        TextView content;
        TextView time;
        ImageView avatar;
        ImageView redHint;
    }
}
