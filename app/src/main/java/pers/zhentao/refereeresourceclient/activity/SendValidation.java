package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.AddFriendRequest;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.service.FriendRequestService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/14 21:43.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class SendValidation extends Activity {

    private EditText editText;
    private Button button;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_send_validation);
        init();
    }

    private void init(){
        editText = (EditText)findViewById(R.id.et_send_validation);
        button = (Button)findViewById(R.id.btn_send_validation);
        imageButton = (ImageButton)findViewById(R.id.back_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageButton.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAddFriendRequest();
            }
        });
    }
    private void sendAddFriendRequest(){
        User user = (User)getIntent().getSerializableExtra("user");
        AddFriendRequest request = new AddFriendRequest();
        request.setUser_send(ContextUtil.getUserInstance());
        request.setUser_receive(user);
        request.setNote(editText.getText().toString());
        request.setStatus(Common.MSG_UNREAD);
        new FriendRequestService().sendAddFriendRequest(user.getUserId(),request, new SaveListener() {
            @Override
            public void onSuccess(int id) {
                //发送成功
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SendValidation.this,"好友请求发送成功，等待验证",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(int errorCode, final String result) {
                //发送失败
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SendValidation.this,"发送失败:"+result,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
//        BmobIMUserInfo info = new BmobIMUserInfo(user.getUserId(),user.getNickName(),null);
//        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info,true,null);
//        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
//        AddFriendMessage msg = new AddFriendMessage();
//        msg.setContent(editText.getText().toString());
//        Map<String,Object> map = new HashMap<>();
//        map.put("name",Common.USER.getNickName());
//        msg.setExtraMap(map);
//
//        final AddFriendRequest request = new AddFriendRequest();
//        request.setUser_send(Common.USER);
//        request.setUser_receive(user);
//        request.setNote(editText.getText().toString());
//        request.setStatus(Common.MSG_UNREAD);
//        conversation.sendMessage(msg, new MessageSendListener() {
//            @Override
//            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
//                if(e==null){
//                    //发送成功
//                    request.save(SendValidation.this);
//                    Toast.makeText(SendValidation.this,"好友请求发送成功，等待验证",Toast.LENGTH_SHORT).show();
//                    finish();
//                }else{
//                    //发送失败
//                    Toast.makeText(SendValidation.this,"发送失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
