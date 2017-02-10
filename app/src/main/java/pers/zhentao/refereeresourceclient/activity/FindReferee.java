package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.FindRefereeAdapter;
import pers.zhentao.refereeresourceclient.bean.FindRefereeMessage;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.FindRefereeService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/8/20 16:26.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FindReferee extends Activity {

    private ListView listView;
    private List<FindRefereeMessage> list;
    private List<FindRefereeMessage> listMy;
    private FindRefereeAdapter adapter;
    private FindRefereeAdapter adapterMy;
    private PopupMenu popupMenu;
    private Menu menu;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_find_referee);
        init();
    }
    @Override
    protected void onResume(){
        super.onResume();
        refreshList();
    }
    private void init(){
        tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("裁判招募");
        tvTitle.setVisibility(View.VISIBLE);
        ImageButton btnBack = (ImageButton)findViewById(R.id.back_btn);
        ImageView btnMore = (ImageView)findViewById(R.id.btn_right);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBack.setVisibility(View.VISIBLE);
        popupMenu = new PopupMenu(this,btnMore);
        menu = popupMenu.getMenu();
        menu.add(Menu.NONE,Menu.FIRST+0,0,"我的招募");
        menu.add(Menu.NONE,Menu.FIRST+1,0,"发起招募");
        menu.add(Menu.NONE,Menu.FIRST+3,0,"我的报名");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case Menu.FIRST + 0:
                        listView.setAdapter(adapterMy);
                        menu.removeItem(Menu.FIRST + 0);
                        menu.removeItem(Menu.FIRST + 1);
                        menu.removeItem(Menu.FIRST + 3);
                        menu.add(Menu.NONE, Menu.FIRST + 2, 0, "全部招募");
                        menu.add(Menu.NONE, Menu.FIRST + 1, 0, "发起招募");
                        menu.add(Menu.NONE,Menu.FIRST+3,0,"我的报名");
                        tvTitle.setText("我的招募");
                        break;
                    case Menu.FIRST + 1:
                        startActivity(new Intent(FindReferee.this, AddFindReferee.class));
                        break;
                    case Menu.FIRST + 2:
                        listView.setAdapter(adapter);
                        menu.removeItem(Menu.FIRST + 2);
                        menu.removeItem(Menu.FIRST + 1);
                        menu.removeItem(Menu.FIRST + 3);
                        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "我的招募");
                        menu.add(Menu.NONE, Menu.FIRST + 1, 0, "发起招募");
                        menu.add(Menu.NONE,Menu.FIRST+3,0,"我的报名");
                        tvTitle.setText("全部招募");
                        break;
                    case Menu.FIRST+3:
                        startActivity(new Intent(FindReferee.this,MyApply.class));
                        break;
                }
                return false;
            }
        });
        btnMore.setImageBitmap(BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(), R.mipmap.menu_plus));
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        btnMore.setVisibility(View.VISIBLE);
        listView = (ListView)findViewById(R.id.lv_find_referee);
        list = new ArrayList<>();
        adapter = new FindRefereeAdapter(this,R.layout.item_find_referee,list);
        listMy = new ArrayList<>();
        adapterMy = new FindRefereeAdapter(FindReferee.this,R.layout.item_find_referee,listMy);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FindReferee.this,FindRefereeDetail.class);
                intent.putExtra("findRefereeMessage",list.get(position));
                startActivity(intent);
            }
        });
    }
    private void refreshList(){
        new FindRefereeService().query(" where is_accept=false and is_delete=false and time>'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "'", " order by publish_time desc", "", new FindListener<FindRefereeMessage>() {
            @Override
            public void onSuccess(final List<FindRefereeMessage> li) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        list.addAll(li);
                        adapter.notifyDataSetChanged();
                        listMy.clear();
                        for(FindRefereeMessage message:list){
                            if(message.getUser().getUserId().equals(ContextUtil.getUserInstance().getUserId())){
                                listMy.add(message);
                            }
                        }
                        adapterMy.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<FindRefereeMessage> query = new BmobQuery<>();
//        query.addWhereEqualTo("isAccept",false)
//            .addWhereEqualTo("isDelete", false)
//            .addWhereGreaterThan("time", new BmobDate(new Date()))
//            .order("-createdAt")
//            .include("user");
//        query.findObjects(MyApplication.getContext(), new FindListener<FindRefereeMessage>() {
//            @Override
//            public void onSuccess(List<FindRefereeMessage> li) {
//                list.clear();
//                list.addAll(li);
//                adapter.notifyDataSetChanged();
//                listMy.clear();
//                for(FindRefereeMessage message:list){
//                    if(message.getUser().getObjectId().equals(Common.USER.getObjectId())){
//                        listMy.add(message);
//                    }
//                }
//                adapterMy.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
    }
}
