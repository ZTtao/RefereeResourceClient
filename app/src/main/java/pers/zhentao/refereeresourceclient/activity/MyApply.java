package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.MyApplyAdapter;
import pers.zhentao.refereeresourceclient.bean.ApplyRefereeMessage;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.ApplyRefereeService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/8/21 15:43.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyApply extends Activity {

    private List<ApplyRefereeMessage> list;
    private MyApplyAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_apply);
        init();
    }
    @Override
    protected void onResume(){
        super.onResume();
        refreshList();
    }
    private void init(){
        ImageButton btnBack = (ImageButton)findViewById(R.id.back_btn);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("我的报名");
        tvTitle.setVisibility(View.VISIBLE);
        listView = (ListView)findViewById(R.id.lv_my_apply);
        list = new ArrayList<>();
        adapter = new MyApplyAdapter(this,R.layout.item_my_apply,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyApply.this,FindRefereeDetail.class);
                intent.putExtra("findRefereeMessage",list.get(position).getFindRefereeMessage());
                startActivity(intent);
            }
        });
    }
    private void refreshList(){
        new ApplyRefereeService().find(" where referee_user=" + ContextUtil.getUserInstance().getUserId() + " and is_delete=false", new FindListener<ApplyRefereeMessage>() {
            @Override
            public void onSuccess(final List<ApplyRefereeMessage> li) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        list.addAll(li);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String result) {

            }
        });
//        BmobQuery<ApplyRefereeMessage> query = new BmobQuery<>();
//        query.addWhereEqualTo("refereeUser", Common.USER)
//            .addWhereEqualTo("isDelete",false);
//        query.include("findRefereeMessage,findRefereeMessage.user");
//        query.findObjects(MyApplication.getContext(), new FindListener<ApplyRefereeMessage>() {
//            @Override
//            public void onSuccess(List<ApplyRefereeMessage> li) {
//                list.clear();
//                list.addAll(li);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
    }
}
