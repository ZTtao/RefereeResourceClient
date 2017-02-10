package pers.zhentao.refereeresourceclient.globalvariable;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.activity.Chat;
import pers.zhentao.refereeresourceclient.activity.FriendRequest;
import pers.zhentao.refereeresourceclient.bean.IMMessage;
import pers.zhentao.refereeresourceclient.bean.MessageType;
import pers.zhentao.refereeresourceclient.bean.Player;
import pers.zhentao.refereeresourceclient.bean.Referee;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.database.RefereeResourceDB;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.service.FriendResponseService;
import pers.zhentao.refereeresourceclient.service.PlayerService;
import pers.zhentao.refereeresourceclient.service.RefereeService;
import pers.zhentao.refereeresourceclient.service.UserService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/21 19:27.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class CommonManager {
    private static CommonManager manager = null;
    private CommonManager(){}   //Singleton Pattern
    private static NotificationManager notificationManager = null;

    //Double Check Lock
    public static CommonManager getInstance(){
        if(manager == null){
            synchronized (CommonManager.class){
                if(manager == null){
                    manager = new CommonManager();
                    notificationManager = (NotificationManager) ContextUtil.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
                }
            }
        }
        return manager;
    }

    //更新用户信息
//    public void updateUserOnServer(User user){
//        new UserService().getUser(user.getUserId(), new GetListener<User>() {
//            @Override
//            public void onSuccess(User commonUser) {
//                RefereeResourceDB.getInstance().saveUser(commonUser);
//                ContextUtil.setUserInstance(commonUser);
//                Toast.makeText(ContextUtil.getInstance(), "用户信息更新成功", Toast.LENGTH_SHORT).show();
//                new PlayerService().getPlayer(commonUser.getUserId(), new GetListener<Player>() {
//                    @Override
//                    public void onSuccess(Player obj) {
//                        ContextUtil.getUserInstance().setPlayer(obj);
//                    }
//
//                    @Override
//                    public void onFailure(int errorCode, String result) {
//
//                    }
//                });
//
////                BmobQuery<Player> query1 = new BmobQuery<>();
////                query1.addWhereEqualTo("user",Common.USER);
////                query1.findObjects(ContextUtil.getInstance(), new FindListener<Player>() {
////                    @Override
////                    public void onSuccess(List<Player> list) {
////                        if(list.size()>0)
////                            Common.PLAYER_USER = list.get(0);
////                    }
////                    @Override
////                    public void onError(int i, String s) {}
////                });
//                new RefereeService().getReferee(commonUser.getUserId(), new GetListener<Referee>() {
//                    @Override
//                    public void onSuccess(Referee obj) {
//                        ContextUtil.getUserInstance().setReferee(obj);
//                    }
//
//                    @Override
//                    public void onFailure(int errorCode, String result) {
//
//                    }
//                });
////                BmobQuery<Referee> query2 = new BmobQuery<>();
////                query2.addWhereEqualTo("user",Common.USER);
////                query2.findObjects(ContextUtil.getInstance(), new FindListener<Referee>() {
////                    @Override
////                    public void onSuccess(List<Referee> list) {
////                        if(list.size()>0)
////                            Common.REFEREE_USER = list.get(0);
////                    }
////                    @Override
////                    public void onError(int i, String s) {}
////                });
//            }
//
//            @Override
//            public void onFailure(int errorCode, String result) {
//                Toast.makeText(ContextUtil.getInstance(), "用户信息更新失败", Toast.LENGTH_SHORT).show();
//            }
//        });
////        final BmobQuery<User> query = new BmobQuery<>();
////        query.getObject(ContextUtil.getInstance(), user.getObjectId(), new GetListener<User>() {
////            @Override
////            public void onSuccess(User commonUser) {
////
////            }
////
////            @Override
////            public void onFailure(int i, String s) {
////                Toast.makeText(ContextUtil.getInstance(), "用户信息更新失败", Toast.LENGTH_SHORT).show();
////            }
////        });
//    }
    //初始化IM模块
    public void initIMModule(User user){

    }
    //退出登录，断开连接
    public void disconnectIMModule(){
//        BmobIM.getInstance().disConnect();
    }

    //发送通知栏通知
    public void sendFriendRequestNotifiy(Context context,String content,String name){
        Intent intent = new Intent(context, FriendRequest.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, Common.FRIEND_REQUEST_REQUEST_CODE, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("新的好友请求")
                .setContentText(name+"请求加你为好友")
                .setContentIntent(pendingIntent)
                .setTicker("新的好友请求")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.icon);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify( Common.NEW_FRIEND_REQUEST_NOTIFY_ID, notification);
    }

    //发送接受好友申请信息
    public void sendAddFriendResponseAgreeMessage(final Context context, final User user){
        IMMessage message = new IMMessage();
        message.setContent(ContextUtil.getUserInstance().getNickName()+"同意添加你为好友");
        message.setMsgType(Common.TYPE_AGREE);
        new FriendResponseService().sendAddFriendResponse(user.getUserId(),message);
//        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(),user.getNickName(),user.getAvatar());
//        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
//        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
//        BmobIMTextMessage message = new BmobIMTextMessage();
//        message.setIsTransient(false);
//        message.setContent(Common.USER.getNickName() + "同意添加你为好友");
//        //直接向对方发送文本信息
//        conversation.sendMessage(message, new MessageSendListener() {
//            @Override
//            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
//                if (e == null) {
//                    Friend friend = new Friend();
//                    friend.setUser(user);
//                    friend.setFriend(Common.USER);
//                    friend.save(context);
//                    AddFriendResponse response = new AddFriendResponse();
//                    response.setType(Common.TYPE_AGREE);
//                    response.setUser_send(Common.USER);
//                    response.setUser_receive(user);
//                    response.setNote(Common.USER.getNickName() + "同意添加你为好友");
//                    response.save(context);
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    //发送拒绝好友申请信息
    public void sendAddFriendResponseRefuseMessage(final Context context, final User user){
        IMMessage message = new IMMessage();
        message.setContent(ContextUtil.getUserInstance().getNickName()+"拒绝添加你为好友");
        message.setMsgType(Common.TYPE_REFUSE);
        new FriendResponseService().sendAddFriendResponse(user.getUserId(),message);
//        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(),user.getNickName(),user.getAvatar());
//        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
//        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
//        AddFriendResponseMessage message = new AddFriendResponseMessage();
//        message.setUser(Common.USER);
//        message.setMsgType(Common.TYPE_REFUSE);
//        Map<String,Object> map = new HashMap<>();
//        map.put("content",Common.USER.getNickName()+"拒绝添加你为好友");
//        map.put("objectIdSend", Common.USER.getObjectId());
//        message.setExtraMap(map);
//        conversation.sendMessage(message, new MessageSendListener() {
//            @Override
//            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
//                if(e == null){
//                    AddFriendResponse response = new AddFriendResponse();
//                    response.setType(Common.TYPE_REFUSE);
//                    response.setUser_send(Common.USER);
//                    response.setUser_receive(user);
//                    response.setNote(Common.USER.getNickName() + "拒绝添加你为好友");
//                    response.save(context);
//                }else{
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    //新消息提示
    public void sendNewMessage(Context context,IMMessage message){
        if(message.getMsgType().equals(MessageType.TEXT)){
            if(!Common.currentConversationId.equals(message.getConversation().getConversationId())) {
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("conversation", message.getConversation());
                PendingIntent pendingIntent = PendingIntent.getActivities(context, Common.CHAT_REQUEST_CODE, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                String name = "";
                try {
                    JSONObject jsonObject = new JSONObject(message.getExtra());
                    name = jsonObject.getString("sendName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                builder.setContentTitle(name)
                        .setContentText("[文本消息]")
                        .setContentIntent(pendingIntent)
                        .setTicker("一条新消息")
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.mipmap.icon);
                Notification notification = builder.build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(Common.NEW_CHAT_MESSAGE_NOTIFY_ID, notification);
            }
        }else if(message.getMsgType().equals(MessageType.IMAGE)){
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("conversation", message.getConversation());
            PendingIntent pendingIntent = PendingIntent.getActivities(context, Common.CHAT_REQUEST_CODE, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            String name = "";
            try {
                JSONObject jsonObject = new JSONObject(message.getExtra());
                name = jsonObject.getString("sendName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            builder.setContentTitle(name)
                    .setContentText("[图片]")
                    .setContentIntent(pendingIntent)
                    .setTicker("一条新消息")
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.mipmap.icon);
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify( Common.NEW_CHAT_MESSAGE_NOTIFY_ID, notification);
        }else if(message.getMsgType().equals(MessageType.VIDEO)){
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("conversation", message.getConversation());
            PendingIntent pendingIntent = PendingIntent.getActivities(context, Common.CHAT_REQUEST_CODE, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            String name = "";
            try {
                JSONObject jsonObject = new JSONObject(message.getExtra());
                name = jsonObject.getString("sendName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            builder.setContentTitle(name)
                    .setContentText("[视频]")
                    .setContentIntent(pendingIntent)
                    .setTicker("一条新消息")
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.mipmap.icon);
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify( Common.NEW_CHAT_MESSAGE_NOTIFY_ID, notification);
        }else if(message.getMsgType().equals(MessageType.SOUND)){
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("conversation", message.getConversation());
            PendingIntent pendingIntent = PendingIntent.getActivities(context, Common.CHAT_REQUEST_CODE, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            String name = "";
            try {
                JSONObject jsonObject = new JSONObject(message.getExtra());
                name = jsonObject.getString("sendName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            builder.setContentTitle(name)
                    .setContentText("[语音]")
                    .setContentIntent(pendingIntent)
                    .setTicker("一条新消息")
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.mipmap.icon);
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify( Common.NEW_CHAT_MESSAGE_NOTIFY_ID, notification);
        }
    }
    public void cancleNotificationById(int id){
        notificationManager.cancel(id);
    }
    public void cancleAllNotification(){
        notificationManager.cancelAll();
    }
    //本地取头像
    public Bitmap getAvatarAtLocal(String userId){
        Bitmap bitmap = null;
        File file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",userId+".jpg");
        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(file.getPath());
        }
        return bitmap;
    }

    //本地取图片
    public Bitmap getPictureAtLocal(String imgName){
        Bitmap bitmap = null;
        File file = null;
        if(imgName!=null) {
            file = new File(Environment.getExternalStorageDirectory() + "/refereeResource", imgName);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.getPath());
            }
        }
        return bitmap;
    }


}
