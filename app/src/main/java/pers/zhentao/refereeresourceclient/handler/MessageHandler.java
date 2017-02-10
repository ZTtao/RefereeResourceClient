//package pers.zhentao.refereeresourceclient.handler;
//
//import android.content.Context;
//
//import com.zhangzt.refereeresource.bean.myEvent.FreshFriendResponseEvent;
//import com.zhangzt.refereeresource.bean.myEvent.RefreshUnreadMessageCountEvent;
//import com.zhangzt.refereeresource.globalvariable.Common;
//import com.zhangzt.refereeresource.globalvariable.CommonManager;
//import com.zhangzt.refereeresource.util.LogUtil;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.List;
//import java.util.Map;
//
//import cn.bmob.newim.bean.BmobIMMessage;
//import cn.bmob.newim.bean.BmobIMMessageType;
//import cn.bmob.newim.bean.BmobIMUserInfo;
//import cn.bmob.newim.event.MessageEvent;
//import cn.bmob.newim.event.OfflineMessageEvent;
//import cn.bmob.newim.listener.BmobIMMessageHandler;
//import de.greenrobot.event.EventBus;
//
///**
// * Created by ZhangZT on 2016/7/15 19:50.
// * E-mail: 327502540@qq.com
// * Project: RefereeResource
// */
//public class MessageHandler extends BmobIMMessageHandler {
//
//    private Context context;
//    public MessageHandler(Context context){
//        this.context = context;
//    }
//
//    @Override
//    public void onMessageReceive(final MessageEvent event){
//        //服务器发来消息时，该方法被调用
//        executeMessage(event);
//    }
//
//    @Override
//    public void onOfflineReceive(final OfflineMessageEvent event){
//        //每次调用connect方法时会查询离线消息，如果有，则调用此方法
//        executeOfflineMessage(event);
//    }
//
//    private void executeMessage(MessageEvent event){
//        BmobIMMessage message = event.getMessage();
//        if(BmobIMMessageType.getMessageTypeValue(message.getMsgType())==0){
//            //用户自定义消息类型处理
//            executeMyMessage(message);
//        }else{
//            //系统消息类型处理
//            executeSystemMessage(message);
//        }
//    }
//    private void executeOfflineMessage(OfflineMessageEvent event){
//        Map<String,List<MessageEvent>> map = event.getEventMap();
//        for(Map.Entry<String , List<MessageEvent>> entry:map.entrySet()){
//            List<MessageEvent> list = entry.getValue();
//            for(int i=0;i<list.size();i++){
//                if(BmobIMMessageType.getMessageTypeValue(list.get(i).getMessage().getMsgType())==0){
//                    executeMyMessage(list.get(i).getMessage());
//                }else {
//                    executeSystemMessage(list.get(i).getMessage());
//                }
//            }
//        }
//    }
//    private void executeSystemMessage(BmobIMMessage message){
//        if(message.getMsgType().equals(BmobIMMessageType.TEXT.getType())){
//            //接收文本消息
//            if(!Common.unreadConversationsIdList.contains(message.getConversationId()))
//                Common.unreadConversationsIdList.add(message.getConversationId());
//            EventBus.getDefault().post(new RefreshUnreadMessageCountEvent());
//            CommonManager.getInstance().sendNewMessage(context,message);
//        }else if(message.getMsgType().equals(BmobIMMessageType.IMAGE.getType())){
//            if(!Common.unreadConversationsIdList.contains(message.getConversationId()))
//                Common.unreadConversationsIdList.add(message.getConversationId());
//            EventBus.getDefault().post(new RefreshUnreadMessageCountEvent());
//            CommonManager.getInstance().sendNewMessage(context,message);
//        }
//    }
//    private void executeMyMessage(BmobIMMessage message){
//        String type = message.getMsgType();
//        if(type.equals("request")){
//            //添加好友请求 消息处理
//            String extra = message.getExtra();
//            String name = null;
//            try {
//                JSONObject jsonObject = new JSONObject(extra);
//                name = jsonObject.getString("name");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            CommonManager.getInstance().sendFriendRequestNotifiy(context,message.getContent(),name);
//        }else if(type.equals(Common.TYPE_REFUSE)){
//            //对方拒绝添加好友请求 消息处理
//            Common.hadUnreadResponse = true;
//            EventBus.getDefault().post(new FreshFriendResponseEvent());
//        }
//    }
//}
