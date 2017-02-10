package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.activity.fragment.FragmentChat;
import pers.zhentao.refereeresourceclient.activity.fragment.FragmentMy;
import pers.zhentao.refereeresourceclient.activity.fragment.FragmentPlayer;
import pers.zhentao.refereeresourceclient.activity.fragment.FragmentReferee;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshMainpageEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.globalvariable.CommonManager;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * 主界面activity
 * Created by ZhangZT on 2016/3/9 16:06.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MainPage extends Activity {

    private ImageView redHintAtChat;//聊天未读消息提示小红点
    private ImageView redHintAtFriend;//好友请求未读消息提示红点
    private ImageView btnNewPost;//新建帖子按钮
    private ImageView btnFriend;//进入好友列表按钮
    private Bitmap bitmapFriend = null;
    private Button btnMy;//浏览个人资料按钮
    private Button btnPlayerFragment;//进入球员社区按钮
    private Button btnRefereeFragment;//进入裁判社区按钮
    private Button btnChatFragment;//进入会话fragment按钮
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentPlayer playerFragment = null;
    private FragmentReferee refereeFragment = null;
    private FragmentChat chatFragment = null;
    private FragmentMy myFragment = null;
    private int currentFragment = -1;//当前显示的fragment
    private final int PLAYER_FRAGMENT = 1;
    private final int REFEREE_FRAGMENT = 2;
    private final int CHAT_FRAGMENT = 3;
    private final int MY_FRAGMENT = 4;
    private TextView tvTitleLeft;//主界面标题
    private Bitmap bitmapRightEdit = null;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mainpage);
        EventBus.getDefault().register(this);
        btnMy = (Button)findViewById(R.id.btn_bottom_my);
        btnNewPost = (ImageView)findViewById(R.id.btn_right);
        btnFriend = (ImageView)findViewById(R.id.btn_right_friend);
        btnPlayerFragment = (Button)findViewById(R.id.btn_bottom_player);
        btnRefereeFragment = (Button)findViewById(R.id.btn_bottom_referee);
        btnChatFragment = (Button)findViewById(R.id.btn_bottom_chat);
        tvTitleLeft = (TextView)findViewById(R.id.tv_title_left);
        redHintAtChat = (ImageView)findViewById(R.id.img_red_hint_mainpage_chat);
        redHintAtFriend = (ImageView)findViewById(R.id.img_red_hint_title_friend);
        tvTitleLeft.setVisibility(View.VISIBLE);
        btnNewPost.setVisibility(View.VISIBLE);
        setDefaultFragment();
        setButtonListener();
        onEventMainThread(null);
    }
    /**
     * 设置进入mianpage时显示的默认fragment
     */
    private void setDefaultFragment(){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        playerFragment = new FragmentPlayer();
        fragmentTransaction.add(R.id.fragment_mainPage, playerFragment);
        fragmentTransaction.show(playerFragment);
        fragmentTransaction.commit();
        currentFragment = PLAYER_FRAGMENT;
        btnPlayerFragment.setSelected(true);
    }
    /**
     * 为按钮添加Listener
     */
    private void setButtonListener(){
        btnMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextUtil.getUserInstance() == null) {
                    //未登录
                    startActivity(new Intent(MainPage.this, LoginAndRegister.class));
                    overridePendingTransition(R.anim.activity_2, R.anim.activity_1);
                } else {
                    //已登录
                    if (currentFragment != MY_FRAGMENT) {
                        btnFriend.setVisibility(View.GONE);
                        if(bitmapFriend!=null) {
                            bitmapFriend.recycle();
                            bitmapFriend = null;
                        }
                        btnNewPost.setVisibility(View.GONE);
                        if (myFragment == null) {
                            myFragment = new FragmentMy();
                        }
                        fragmentTransaction = fragmentManager.beginTransaction();
                        if (!myFragment.isAdded()) {
                            fragmentTransaction.add(R.id.fragment_mainPage, myFragment);
                        }
                        if (playerFragment != null && playerFragment.isAdded())
                            fragmentTransaction.hide(playerFragment);
                        if (refereeFragment != null && refereeFragment.isAdded())
                            fragmentTransaction.hide(refereeFragment);
                        if (chatFragment != null && chatFragment.isAdded())
                            fragmentTransaction.hide(chatFragment);
                        fragmentTransaction.show(myFragment);
                        fragmentTransaction.commit();
                        currentFragment = MY_FRAGMENT;
                        btnPlayerFragment.setSelected(false);
                        btnChatFragment.setSelected(false);
                        btnRefereeFragment.setSelected(false);
                        btnMy.setSelected(true);
                    }
                }
            }
        });
        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextUtil.getUserInstance() == null)
                    Toast.makeText(MainPage.this,"请先登录",Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(MainPage.this, NewPost.class);
                    intent.putExtra("currentFragment", currentFragment);
                    startActivityForResult(intent, 0);
                }
            }
        });
        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextUtil.getUserInstance() == null)
                    startActivity(new Intent(MainPage.this, LoginAndRegister.class));
                else {
                    CommonManager.getInstance().cancleNotificationById(Common.NEW_FRIEND_REQUEST_NOTIFY_ID);
                    startActivity(new Intent(MainPage.this, MyFriend.class));
                }
            }
        });
        btnPlayerFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment != PLAYER_FRAGMENT) {
                    btnFriend.setVisibility(View.GONE);
                    if(bitmapFriend!=null) {
                        bitmapFriend.recycle();
                        bitmapFriend = null;
                    }
                    btnNewPost.setVisibility(View.VISIBLE);
                    if (playerFragment == null) {
                        playerFragment = new FragmentPlayer();
                    }
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (!playerFragment.isAdded()) {
                        fragmentTransaction.add(R.id.fragment_mainPage, playerFragment);
                    }
                    if (refereeFragment != null && refereeFragment.isAdded())
                        fragmentTransaction.hide(refereeFragment);
                    if (chatFragment != null && chatFragment.isAdded())
                        fragmentTransaction.hide(chatFragment);
                    if (myFragment != null && myFragment.isAdded())
                        fragmentTransaction.hide(myFragment);
                    fragmentTransaction.show(playerFragment);
                    fragmentTransaction.commit();
                    currentFragment = PLAYER_FRAGMENT;
                    btnPlayerFragment.setSelected(true);
                    btnChatFragment.setSelected(false);
                    btnRefereeFragment.setSelected(false);
                    btnMy.setSelected(false);
                } else {
                    playerFragment.doQueryNew();
                }
            }
        });
        btnRefereeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment != REFEREE_FRAGMENT) {
                    btnFriend.setVisibility(View.GONE);
                    if(bitmapFriend!=null) {
                        bitmapFriend.recycle();
                        bitmapFriend = null;
                    }
                    btnNewPost.setVisibility(View.VISIBLE);
                    if (refereeFragment == null) {
                        refereeFragment = new FragmentReferee();
                    }
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (!refereeFragment.isAdded()) {
                        fragmentTransaction.add(R.id.fragment_mainPage, refereeFragment);
                    }
                    if (playerFragment != null && playerFragment.isAdded())
                        fragmentTransaction.hide(playerFragment);
                    if (chatFragment != null && chatFragment.isAdded())
                        fragmentTransaction.hide(chatFragment);
                    if (myFragment != null && myFragment.isAdded())
                        fragmentTransaction.hide(myFragment);
                    fragmentTransaction.show(refereeFragment);
                    fragmentTransaction.commit();
                    currentFragment = REFEREE_FRAGMENT;
                    btnPlayerFragment.setSelected(false);
                    btnChatFragment.setSelected(false);
                    btnRefereeFragment.setSelected(true);
                    btnMy.setSelected(false);
                } else {
                    refereeFragment.doQueryNew();
                }
            }
        });
        btnChatFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment != CHAT_FRAGMENT) {
                    bitmapFriend = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(), R.mipmap.icon_friend);
                    btnFriend.setImageBitmap(bitmapFriend);
                    btnFriend.setVisibility(View.VISIBLE);
                    btnNewPost.setVisibility(View.GONE);
                    if (chatFragment == null) {
                        chatFragment = new FragmentChat();
                    }
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (!chatFragment.isAdded()) {
                        fragmentTransaction.add(R.id.fragment_mainPage, chatFragment);
                    }
                    if (playerFragment != null && playerFragment.isAdded())
                        fragmentTransaction.hide(playerFragment);
                    if (refereeFragment != null && refereeFragment.isAdded())
                        fragmentTransaction.hide(refereeFragment);
                    if (myFragment != null && myFragment.isAdded())
                        fragmentTransaction.hide(myFragment);
                    fragmentTransaction.show(chatFragment);
                    fragmentTransaction.commit();
                    currentFragment = CHAT_FRAGMENT;
                    btnPlayerFragment.setSelected(false);
                    btnChatFragment.setSelected(true);
                    btnRefereeFragment.setSelected(false);
                    btnMy.setSelected(false);
                } else {
                    chatFragment.doQueryNew();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==0){
            if(resultCode==Common.NewPostResultCode){
                if(intent.getBooleanExtra("is_publish",false)){
                    if(intent.getStringExtra("type").equals("player")){
                        playerFragment.doQueryNew();
                    }else {
                        refereeFragment.doQueryNew();
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy(){
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    protected void onResume(){
         if(ContextUtil.getUserInstance() == null) {
             redHintAtChat.setVisibility(View.GONE);
             redHintAtFriend.setVisibility(View.GONE);
             if (currentFragment != PLAYER_FRAGMENT) {
                 btnFriend.setVisibility(View.GONE);
                 if(bitmapFriend!=null) {
                     bitmapFriend.recycle();
                     bitmapFriend = null;
                 }
                 btnNewPost.setVisibility(View.VISIBLE);
                 if (playerFragment == null) {
                     playerFragment = new FragmentPlayer();
                 }
                 fragmentTransaction = fragmentManager.beginTransaction();
                 if (!playerFragment.isAdded()) {
                     fragmentTransaction.add(R.id.fragment_mainPage, playerFragment);
                 }
                 if (refereeFragment != null && refereeFragment.isAdded())
                     fragmentTransaction.hide(refereeFragment);
                 if (chatFragment != null && chatFragment.isAdded())
                     fragmentTransaction.hide(chatFragment);
                 if (myFragment != null && myFragment.isAdded())
                     fragmentTransaction.hide(myFragment);
                 fragmentTransaction.show(playerFragment);
                 fragmentTransaction.commit();
                 currentFragment = PLAYER_FRAGMENT;
                 btnPlayerFragment.setSelected(true);
                 btnMy.setSelected(false);
                 btnRefereeFragment.setSelected(false);
                 btnChatFragment.setSelected(false);
             }
         }
        bitmapRightEdit = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(),R.mipmap.btn_right_edit);
        btnNewPost.setImageBitmap(bitmapRightEdit);
        super.onResume();
    }
    @Override
    public void onPause(){
        btnNewPost.setImageBitmap(null);
        bitmapRightEdit.recycle();
        bitmapRightEdit = null;
        super.onPause();
    }
    public void onEventMainThread(FreshMainpageEvent event){
        if(ContextUtil.getUserInstance() == null){
            redHintAtChat.setVisibility(View.GONE);
            redHintAtFriend.setVisibility(View.GONE);

        }else {
            if (Common.unreadConversationsIdList.size() > 0)
                redHintAtChat.setVisibility(View.VISIBLE);
            else
                redHintAtChat.setVisibility(View.GONE);
            if (Common.hadUnreadRequest)
                redHintAtFriend.setVisibility(View.VISIBLE);
            else
                redHintAtFriend.setVisibility(View.GONE);
        }
    }
}
