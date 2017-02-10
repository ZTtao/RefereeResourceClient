package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.ChatContentAdapter;
import pers.zhentao.refereeresourceclient.adapter.MyPagerAdapter;
import pers.zhentao.refereeresourceclient.bean.IMConversation;
import pers.zhentao.refereeresourceclient.bean.IMMessage;
import pers.zhentao.refereeresourceclient.bean.IMTextMessage;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.bean.myEvent.ImageLoadedEvent;
import pers.zhentao.refereeresourceclient.bean.myEvent.MessageEvent;
import pers.zhentao.refereeresourceclient.bean.myEvent.RefreshUnreadMessageCountEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.GetListener;
import pers.zhentao.refereeresourceclient.handler.MessageListHandler;
import pers.zhentao.refereeresourceclient.listener.MessageQueryListener;
import pers.zhentao.refereeresourceclient.listener.MessageSendListener;
import pers.zhentao.refereeresourceclient.service.ConversationService;
import pers.zhentao.refereeresourceclient.service.UserService;
import pers.zhentao.refereeresourceclient.util.CommonUtil;
import pers.zhentao.refereeresourceclient.util.ContextUtil;
import pers.zhentao.refereeresourceclient.util.IM;
import pers.zhentao.refereeresourceclient.util.IMClient;

/**
 * Created by ZhangZT on 2016/7/16 16:40.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class Chat extends Activity implements MessageListHandler {

    private ImageButton back;
    private TextView tv_title;
    private ListView listView;
//    private ImageButton btn_more;
    private EditText editText;
    private ImageButton btn_send;
    private ImageButton btn_micro_expression;
//    private ImageButton btn_sounds;
//    private ImageButton btn_picture;
//    private ImageButton btn_video;
//    private ImageButton btn_locate;
//    private LinearLayout linearLayout;
    private IMConversation conversation;
    private List<IMMessage> list;
    private ChatContentAdapter adapter;
//    private PopupWindow popupWindow;
//    private File file;
//    private RelativeLayout ly_record;
//    private TextView tv_record_second;
//    private ImageView img_btn_record;
//    private ImageView img_btn_record_send;
//    private ImageView img_btn_record_cancle;
//    private Boolean is_record_start = false;
//    private Boolean has_record = false;
//    private Boolean is_record_play = false;
//    private String RecordFileName;
//    private File RecordFile;
//    private int record_second = 0;
//    private Runnable runnable;
//    private Handler handler;
    private RelativeLayout relativeLayoutMicroExpression;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_chat);
        EventBus.getDefault().register(this);
        init();
    }

    private void init(){
        back = (ImageButton)findViewById(R.id.back_btn);
        tv_title = (TextView)findViewById(R.id.title_text);
        listView = (ListView)findViewById(R.id.lv_chat);
//        btn_more = (ImageButton)findViewById(R.id.img_btn_chat_more);
        editText = (EditText)findViewById(R.id.et_chat);
        btn_micro_expression = (ImageButton)findViewById(R.id.img_btn_chat_micro_expression);
        btn_send = (ImageButton)findViewById(R.id.img_btn_chat_send);
//        btn_sounds = (ImageButton)findViewById(R.id.img_btn_chat_sounds);
//        btn_picture = (ImageButton)findViewById(R.id.img_btn_chat_picture);
//        btn_video = (ImageButton)findViewById(R.id.img_btn_chat_video);
//        btn_locate = (ImageButton)findViewById(R.id.img_btn_chat_locate);
//        linearLayout = (LinearLayout)findViewById(R.id.ly_chat_bottom_down);
//        ly_record = (RelativeLayout)findViewById(R.id.ly_chat_bottom_record);
        relativeLayoutMicroExpression = (RelativeLayout)findViewById(R.id.ly_chat_bottom_micro_expression);
        conversation = IMConversation.obtain(IMClient.getInstance(),(IMConversation)getIntent().getSerializableExtra("conversation"));
        Common.currentConversationId = conversation.getConversationId();
        list = new ArrayList<>();
        adapter = new ChatContentAdapter(this,R.layout.item_chat_content,list);
        listView.setAdapter(adapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                ly_record.setVisibility(View.GONE);
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
                relativeLayoutMicroExpression.setVisibility(View.GONE);
//                linearLayout.setVisibility(View.GONE);
                return false;
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutMicroExpression.setVisibility(View.GONE);
            }
        });
        initData();
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if (view.getFirstVisiblePosition() == 0) {
//                        final int pos = view.getLastVisiblePosition();
//
//                        conversation.queryMessages(list.get(0), 20, new MessagesQueryListener() {
//                            @Override
//                            public void done(final List<BmobIMMessage> li, BmobException e) {
//                                if(e == null){
//                                    if(li.size()>0) {
//                                        list.addAll(0, li);
//                                        adapter.notifyDataSetChanged();
//                                        listView.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                listView.requestFocusFromTouch();
//                                                listView.setSelection(li.size());
//                                            }
//                                        });
//                                    }
//
//                                }else {
//                                    Toast.makeText(Chat.this,"加载历史消息失败",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
        new UserService().getUser(conversation.getToUser().getUserId(), new GetListener<User>() {
            @Override
            public void onSuccess(final User user) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_title.setText(user.getNickName());
                        tv_title.setTextSize(20);
                        tv_title.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFailure(int errorCode, String result) {

            }
        });
//        BmobQuery<CommonUser> query = new BmobQuery<>();
//        query.getObject(MyApplication.getContext(), conversation.getConversationId(), new GetListener<CommonUser>() {
//            @Override
//            public void onSuccess(CommonUser user) {
//                tv_title.setText(user.getNickName());
//                tv_title.setTextSize(20);
//                tv_title.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back.setVisibility(View.VISIBLE);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(Chat.this, "空消息", Toast.LENGTH_SHORT).show();
                } else {
                    IMTextMessage textMessage = new IMTextMessage();
                    textMessage.setContent(editText.getText().toString());
                    Map<String, Object> map = new HashMap<>();
                    map.put("sendName", ContextUtil.getUserInstance().getNickName());
                    textMessage.setExtraMap(map);
                    new ConversationService().sendMessage(conversation,textMessage, new MessageSendListener() {
                        @Override
                        public void done(final IMMessage message, Exception e) {
                            if (e == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        list.add(message);
                                        adapter.notifyDataSetChanged();
                                        listView.setSelection(adapter.getCount() - 1);
                                        editText.setText("");
                                    }
                                });
                            } else {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Chat.this, "发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
//                    conversation.sendMessage(textMessage, new MessageSendListener() {
//
//                        @Override
//                        public void onStart(BmobIMMessage message) {
//                            //list.add(message);
//                            //adapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
//                            if (e == null) {
//                                list.add(bmobIMMessage);
//                                adapter.notifyDataSetChanged();
//                                listView.setSelection(adapter.getCount() - 1);
//                                editText.setText("");
//                            } else {
//                                e.printStackTrace();
//                                Toast.makeText(Chat.this, "发送失败", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
            }
        });
//        btn_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (linearLayout.getVisibility() == View.GONE)
//                    linearLayout.setVisibility(View.VISIBLE);
//                else
//                    linearLayout.setVisibility(View.GONE);
//                ly_record.setVisibility(View.GONE);
//                relativeLayoutMicroExpression.setVisibility(View.GONE);
//            }
//        });
//        btn_picture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ly_record.setVisibility(View.GONE);
//                View view = LayoutInflater.from(Chat.this).inflate(R.layout.item_popupwindow, null);
//                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ly_item_popupwindow_top);
//                Button btn_take_photo = (Button) view.findViewById(R.id.btn_popupwindow_take_photo);
//                Button btn_select_photo = (Button) view.findViewById(R.id.btn_popupwindow_select_photo);
//                popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//                linearLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }
//                });
//                popupWindow.setFocusable(true);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//                btn_take_photo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        takePhoto();
//                    }
//                });
//                btn_select_photo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        selectPhoto();
//                    }
//                });
//            }
//        });
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                record_second++;
//                tv_record_second.setText(record_second/60+":"+record_second%60);
//                handler.postDelayed(this,1000);
//            }
//        };
//        btn_sounds.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                tv_record_second = (TextView)findViewById(R.id.tv_chat_bottom_record_second);
//                img_btn_record = (ImageView)findViewById(R.id.img_btn_chat_bottom_record);
//                img_btn_record.setBackgroundResource(R.drawable.icon_start_record);
//                img_btn_record_send = (ImageView)findViewById(R.id.img_btn_chat_bottom_record_send);
//                img_btn_record_cancle = (ImageView)findViewById(R.id.img_btn_chat_bottom_record_cancle);
//                ly_record.setVisibility(View.VISIBLE);
//                img_btn_record.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!is_record_start && !has_record) {
//                            //开始录音
//                            Calendar calendar = Calendar.getInstance();
//                            RecordFileName = Common.USER.getObjectId() + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) + ".mp3";
//                            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "refereeResource" + File.separator + "record" + File.separator);
//                            if (!f.exists() || !f.isDirectory())
//                                f.mkdir();
//                            RecordFile = new File(Environment.getExternalStorageDirectory() + "/refereeResource/record", RecordFileName);
//                            MyRecord.getInstance().onStart(RecordFile.getPath());
//                            is_record_start = true;
//                            img_btn_record.setBackgroundResource(R.drawable.icon_stop_record);
//                            record_second = 0;
//                            tv_record_second.setText("0:0");
//                            handler.postDelayed(runnable, 1000);
//                        } else if (has_record && !is_record_play) {
//                            //播放
//                            MyRecord.getInstance().onPlay(RecordFile.getPath(), new MediaPlayer.OnCompletionListener() {
//                                @Override
//                                public void onCompletion(MediaPlayer mp) {
//                                    is_record_play = false;
//                                    handler.removeCallbacks(runnable);
//                                }
//                            });
//                            is_record_play = true;
//                            record_second = 0;
//                            img_btn_record.setBackgroundResource(R.drawable.icon_stop_record);
//                            tv_record_second.setText("0:0");
//                            handler.postDelayed(runnable, 1000);
//                        } else if (has_record && is_record_play) {
//                            //停止播放
//                            MyRecord.getInstance().onStopPlay();
//                            is_record_play = false;
//                            img_btn_record.setBackgroundResource(R.drawable.icon_play_record);
//                            handler.removeCallbacks(runnable);
//                        } else {
//                            //停止录音
//                            MyRecord.getInstance().onStop();
//                            is_record_start = false;
//                            has_record = true;
//                            img_btn_record.setBackgroundResource(R.drawable.icon_play_record);
//                            img_btn_record_send.setVisibility(View.VISIBLE);
//                            img_btn_record_cancle.setVisibility(View.VISIBLE);
//                            handler.removeCallbacks(runnable);
//                            record_second = 0;
//                        }
//                    }
//                });
//                img_btn_record_cancle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(RecordFile.exists())RecordFile.delete();
//                        has_record = false;
//                        if(is_record_play)MyRecord.getInstance().onStopPlay();
//                        is_record_play = false;
//                        is_record_start = false;
//                        img_btn_record.setBackgroundResource(R.drawable.icon_start_record);
//                        img_btn_record_send.setVisibility(View.GONE);
//                        img_btn_record_cancle.setVisibility(View.GONE);
//                        handler.removeCallbacks(runnable);
//                        tv_record_second.setText("0:0");
//                        record_second = 0;
//                    }
//                });
//                img_btn_record_send.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (is_record_play) MyRecord.getInstance().onStopPlay();
//                        img_btn_record_send.setVisibility(View.GONE);
//                        img_btn_record_cancle.setVisibility(View.GONE);
//                        is_record_play = false;
//                        is_record_start = false;
//                        has_record = false;
//                        tv_record_second.setText("0:0");
//                        img_btn_record.setBackgroundResource(R.drawable.icon_start_record);
//                        //send audio message
//                        BmobIMAudioMessage message = new BmobIMAudioMessage(RecordFile);
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("recordName", RecordFileName);
//                        message.setExtraMap(map);
//                        conversation.sendMessage(message, new MessageSendListener() {
//                            @Override
//                            public void onStart(BmobIMMessage message){
//                                list.add(message);
//                                adapter.notifyDataSetChanged();
//                                listView.setSelection(adapter.getCount() - 1);
//                            }
//                            @Override
//                            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
//                                if (e == null) {
////                                    list.add(bmobIMMessage);
////                                    adapter.notifyDataSetChanged();
////                                    listView.setSelection(adapter.getCount()-1);
//                                } else {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                });
//
//            }
//        });
//        btn_video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ly_record.setVisibility(View.GONE);
//                Intent intent = new Intent(MyApplication.getContext(),RecordVideo.class);
//                startActivityForResult(intent, Common.VIDEO_RECORD_REQUEST_CODE);
//            }
//        });
        btn_micro_expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(),0);
                if (relativeLayoutMicroExpression.getVisibility() == View.GONE)
                    relativeLayoutMicroExpression.setVisibility(View.VISIBLE);
                else
                    relativeLayoutMicroExpression.setVisibility(View.GONE);
//                linearLayout.setVisibility(View.GONE);
                ViewPager viewPager = (ViewPager) findViewById(R.id.vp_chat_micro_expression);
                List<View> viewList = new ArrayList<>();
                viewList.add(LayoutInflater.from(ContextUtil.getInstance()).inflate(R.layout.fragment_micro_expression_first, null));
                viewList.add(LayoutInflater.from(ContextUtil.getInstance()).inflate(R.layout.fragment_micro_expression_second, null));
                viewList.add(LayoutInflater.from(ContextUtil.getInstance()).inflate(R.layout.fragment_micro_expression_third, null));
                viewList.add(LayoutInflater.from(ContextUtil.getInstance()).inflate(R.layout.fragment_micro_expression_forth, null));
                viewPager.setAdapter(new MyPagerAdapter(viewList));
                viewPager.setCurrentItem(0);
                setMicroExpressionListener(viewList);
            }

            private void setMicroExpressionListener(List<View> viewList) {
                ImageView microExpressionFA = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fa);
                ImageView microExpressionFB = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fb);
                ImageView microExpressionFC = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fc);
                ImageView microExpressionFD = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fd);
                ImageView microExpressionFE = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fe);
                ImageView microExpressionFF = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_ff);
                ImageView microExpressionFG = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fg);
                ImageView microExpressionFH = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fh);
                ImageView microExpressionFI = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fi);
                ImageView microExpressionFJ = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fj);
                ImageView microExpressionFK = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fk);
                ImageView microExpressionFL = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fl);
                ImageView microExpressionFM = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fm);
                ImageView microExpressionFN = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fn);
                ImageView microExpressionFO = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fo);
                ImageView microExpressionFP = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fp);
                ImageView microExpressionFQ = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fq);
                ImageView microExpressionFR = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fr);
                ImageView microExpressionFS = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_fs);
                ImageView microExpressionFT = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_ft);

                ImageView microExpressionSA = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sa);
                ImageView microExpressionSB = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sb);
                ImageView microExpressionSC = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sc);
                ImageView microExpressionSD = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sd);
                ImageView microExpressionSE = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_se);
                ImageView microExpressionSF = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sf);
                ImageView microExpressionSG = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sg);
                ImageView microExpressionSH = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sh);
                ImageView microExpressionSI = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_si);
                ImageView microExpressionSJ = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sj);
                ImageView microExpressionSK = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sk);
                ImageView microExpressionSL = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sl);
                ImageView microExpressionSM = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sm);
                ImageView microExpressionSN = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sn);
                ImageView microExpressionSO = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_so);
                ImageView microExpressionSP = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sp);
                ImageView microExpressionSQ = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sq);
                ImageView microExpressionSR = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_sr);
                ImageView microExpressionSS = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_ss);
                ImageView microExpressionST = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_st);

                ImageView microExpressionTA = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_ta);
                ImageView microExpressionTB = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tb);
                ImageView microExpressionTC = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tc);
                ImageView microExpressionTD = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_td);
                ImageView microExpressionTE = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_te);
                ImageView microExpressionTF = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tf);
                ImageView microExpressionTG = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tg);
                ImageView microExpressionTH = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_th);
                ImageView microExpressionTI = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_ti);
                ImageView microExpressionTJ = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tj);
                ImageView microExpressionTK = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tk);
                ImageView microExpressionTL = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tl);
                ImageView microExpressionTM = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tm);
                ImageView microExpressionTN = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tn);
                ImageView microExpressionTO = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_to);
                ImageView microExpressionTP = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tp);
                ImageView microExpressionTQ = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tq);
                ImageView microExpressionTR = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tr);
                ImageView microExpressionTS = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_ts);
                ImageView microExpressionTT = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_tt);

                ImageView microExpressionFRA = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_fra);
                ImageView microExpressionFRB = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frb);
                ImageView microExpressionFRC = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frc);
                ImageView microExpressionFRD = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frd);
                ImageView microExpressionFRE = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_fre);
                ImageView microExpressionFRF = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frf);
                ImageView microExpressionFRG = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frg);
                ImageView microExpressionFRH = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frh);
                ImageView microExpressionFRI = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_fri);
                ImageView microExpressionFRJ = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frj);
                ImageView microExpressionFRK = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frk);
                ImageView microExpressionFRL = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frl);
                ImageView microExpressionFRM = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frm);
                ImageView microExpressionFRN = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frn);
                ImageView microExpressionFRO = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_fro);
                ImageView microExpressionFRP = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frp);
                ImageView microExpressionFRQ = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frq);
                ImageView microExpressionFRR = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frr);
                ImageView microExpressionFRS = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frs);
                ImageView microExpressionFRT = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_frt);
                ImageView microExpressionBackFirst = (ImageView) viewList.get(0).findViewById(R.id.micro_expression_f_back);
                ImageView microExpressionBackSecond = (ImageView) viewList.get(1).findViewById(R.id.micro_expression_s_back);
                ImageView microExpressionBackThird = (ImageView) viewList.get(2).findViewById(R.id.micro_expression_t_back);
                ImageView microExpressionBackForth = (ImageView) viewList.get(3).findViewById(R.id.micro_expression_fr_back);
                ArrayList<View> list = new ArrayList<>();
                list.add(microExpressionFA);list.add(microExpressionFB);
                list.add(microExpressionFC);list.add(microExpressionFD);
                list.add(microExpressionFE);list.add(microExpressionFF);
                list.add(microExpressionFG);list.add(microExpressionFH);
                list.add(microExpressionFI);list.add(microExpressionFJ);
                list.add(microExpressionFK);list.add(microExpressionFL);
                list.add(microExpressionFM);list.add(microExpressionFN);
                list.add(microExpressionFO);list.add(microExpressionFP);
                list.add(microExpressionFQ);list.add(microExpressionFR);
                list.add(microExpressionFS);list.add(microExpressionFT);

                list.add(microExpressionSA);list.add(microExpressionSB);
                list.add(microExpressionSC);list.add(microExpressionSD);
                list.add(microExpressionSE);list.add(microExpressionSF);
                list.add(microExpressionSG);list.add(microExpressionSH);
                list.add(microExpressionSI);list.add(microExpressionSJ);
                list.add(microExpressionSK);list.add(microExpressionSL);
                list.add(microExpressionSM);list.add(microExpressionSN);
                list.add(microExpressionSO);list.add(microExpressionSP);
                list.add(microExpressionSQ);list.add(microExpressionSR);
                list.add(microExpressionSS);list.add(microExpressionST);

                list.add(microExpressionTA);list.add(microExpressionTB);
                list.add(microExpressionTC);list.add(microExpressionTD);
                list.add(microExpressionTE);list.add(microExpressionTF);
                list.add(microExpressionTG);list.add(microExpressionTH);
                list.add(microExpressionTI);list.add(microExpressionTJ);
                list.add(microExpressionTK);list.add(microExpressionTL);
                list.add(microExpressionTM);list.add(microExpressionTN);
                list.add(microExpressionTO);list.add(microExpressionTP);
                list.add(microExpressionTQ);list.add(microExpressionTR);
                list.add(microExpressionTS);list.add(microExpressionTT);

                list.add(microExpressionFRA);list.add(microExpressionFRB);
                list.add(microExpressionFRC);list.add(microExpressionFRD);
                list.add(microExpressionFRE);list.add(microExpressionFRF);
                list.add(microExpressionFRG);list.add(microExpressionFRH);
                list.add(microExpressionFRI);list.add(microExpressionFRJ);
                list.add(microExpressionFRK);list.add(microExpressionFRL);
                list.add(microExpressionFRM);list.add(microExpressionFRN);
                list.add(microExpressionFRO);list.add(microExpressionFRP);
                list.add(microExpressionFRQ);list.add(microExpressionFRR);
                list.add(microExpressionFRS);list.add(microExpressionFRT);

                microExpressionBackFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    }
                });
                microExpressionBackSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    }
                });
                microExpressionBackThird.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    }
                });
                microExpressionBackForth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    }
                });
                for(int i=0;i<list.size();i++){
                    list.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showMicroExpression(v.getId());
                        }
                    });
                }
            }
        });
    }
    private void showMicroExpression(int id){


        HashMap<String,Object> hashMap = CommonUtil.getInstance().findMicroExpressionById(id);
        SpannableString spannableString = (SpannableString)hashMap.get("spannableString");
        int resource = (int)hashMap.get("resource");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resource);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 40, 35);
        ImageSpan imageSpan = new ImageSpan(ContextUtil.getInstance(),bitmap);
        spannableString.setSpan(imageSpan, 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.append(spannableString);
    }
    private void initData(){
        new ConversationService().queryMessage(conversation, new MessageQueryListener() {
            @Override
            public void done(final List<IMMessage> li, final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (e == null) {
                            list.addAll(li);
                            adapter.notifyDataSetChanged();
                            listView.setSelection(adapter.getCount() - 1);
                        } else {
                            //查询失败
                            e.printStackTrace();
                            Toast.makeText(Chat.this, "加载历史消息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
//        conversation.queryMessages(null, 20, new MessagesQueryListener() {
//            @Override
//            public void done(List<BmobIMMessage> li, BmobException e) {
//                if (e == null) {
//                    list.addAll(li);
//                    adapter.notifyDataSetChanged();
//                    listView.setSelection(adapter.getCount() - 1);
//                } else {
//                    //查询失败
//                    e.printStackTrace();
//                    Toast.makeText(Chat.this, "加载历史消息失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public void onResume(){
        IM.getInstance().addMessageListHandler(this);
        super.onResume();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    @Override
    public void onPause() {
        IM.getInstance().removeMessageListHandler(this);
        super.onPause();
    }

    @Override
    public void finish(){
        conversation.setUnreadCount(0);
        Common.unreadConversationsIdList.remove(conversation.getConversationId());
        EventBus.getDefault().post(new RefreshUnreadMessageCountEvent());
        Common.currentConversationId = -1;

        super.finish();
    }
//    private void takePhoto(){
//        Calendar calendar = Calendar.getInstance();
//        //Date date = calender.getTime();
//        String fileName = "img"+calendar.get(Calendar.YEAR)+calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH)+calendar.get(Calendar.HOUR_OF_DAY)+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND)+".jpg";
//        file = new File(Environment.getExternalStorageDirectory()+"/refereeResource",Common.USER.getObjectId()+fileName);
//        String SDState = Environment.getExternalStorageState();
//        if(SDState.equals(Environment.MEDIA_MOUNTED)){
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            File f = new File(Environment.getExternalStorageDirectory()+File.separator+"refereeResource"+File.separator);
//            if(!f.exists()||!f.isDirectory())
//                f.mkdir();
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//            startActivityForResult(intent, Common.TAKE_PHOTO_REQUEST_CODE);
//        }else {
//            Toast.makeText(Chat.this,"内存卡不存在",Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void selectPhoto(){
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
//        startActivityForResult(intent, Common.SELECT_PHOTO_REQUEST_CODE);
//    }
    @Override
    protected void onActivityResult(int requestCode,int responseCode,Intent intent){
//        switch (requestCode){
//            case Common.TAKE_PHOTO_REQUEST_CODE:
//                popupWindow.dismiss();
//                if(responseCode == RESULT_OK) {
//                    Intent in = new Intent(MyApplication.getContext(), ShowSelectedPicture.class);
//                    in.putExtra("imgName", file.getName());
//                    in.putExtra("localPath", file.getPath());
//                    startActivityForResult(in, Common.SHOW_SELECTED_PICTURE_REQUEST_CODE);
//                }
//                break;
//            case Common.SELECT_PHOTO_REQUEST_CODE:
//                popupWindow.dismiss();
//                if(responseCode == RESULT_OK) {
//                    Uri uri = intent.getData();
//                    Cursor cursor = managedQuery(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
//                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    cursor.moveToFirst();
//                    String path = cursor.getString(index);
//                    File file = new File(path);
//                    Intent in1 = new Intent(MyApplication.getContext(), ShowSelectedPicture.class);
//                    in1.putExtra("imgName", file.getName());
//                    in1.putExtra("localPath", path);
//                    startActivityForResult(in1, Common.SHOW_SELECTED_PICTURE_REQUEST_CODE);
//                }
//                break;
//            case Common.SHOW_SELECTED_PICTURE_REQUEST_CODE:
//                if(responseCode == Common.SHOW_SELECTED_PICTURE_RESULT_CODE){
//                    File f = new File(intent.getStringExtra("localPath"));
//                    BmobIMImageMessage message1 = new BmobIMImageMessage(f);
//                    Map<String,Object> map1 = new HashMap<>();
//                    map1.put("imgName",intent.getStringExtra("imgName"));
//                    map1.put("localPath",intent.getStringExtra("localPath"));
//                    message1.setExtraMap(map1);
//                    conversation.sendMessage(message1, new MessageSendListener() {
//                        @Override
//                        public void onFinish(){
//
//                        }
//                        @Override
//                        public void onStart(BmobIMMessage message){
//                            list.add(message);
//                            adapter.notifyDataSetChanged();
//                            listView.setSelection(adapter.getCount() - 1);
//                        }
//                        @Override
//                        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
//                            if(e == null){
//
//                            }else {
//                                e.printStackTrace();
//                                Toast.makeText(Chat.this,"发送失败",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//                break;
//            case Common.VIDEO_RECORD_REQUEST_CODE:
//
//                if(intent.getBooleanExtra("is_send",false)){
//                    String record_file_name = intent.getStringExtra("record_file_name");
//                    File file1 = new File(Environment.getExternalStorageDirectory()+"/refereeResource/video",record_file_name);
//                    final BmobIMVideoMessage message2 = new BmobIMVideoMessage(file1);
//                    Map<String,Object> map2 = new HashMap<>();
//                    map2.put("record_file_name",record_file_name);
//                    message2.setExtraMap(map2);
//                    conversation.sendMessage(message2, new MessageSendListener() {
//                        @Override
//                        public void onStart(BmobIMMessage message){
//                            list.add(message);
//                            adapter.notifyDataSetChanged();
//                            listView.setSelection(adapter.getCount() - 1);
//                        }
//                        @Override
//                        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
////                            list.add(message2);
////                            adapter.notifyDataSetChanged();
////                            listView.setSelection(adapter.getCount() - 1);
//                        }
//                    });
//                }
//
//                break;
//        }
    }
    @Override
    public void onMessageReceive(List<MessageEvent> li) {
        for (MessageEvent event : li) {
            if(event.getMessage().getConversation().getConversationId().equals(Common.currentConversationId)) {
                list.add(event.getMessage());
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }
        }
    }
    public void onEventMainThread(ImageLoadedEvent event){
        adapter.notifyDataSetChanged();
    }
}
