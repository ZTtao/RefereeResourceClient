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
import pers.zhentao.refereeresourceclient.adapter.ShowApplyAdapter;
import pers.zhentao.refereeresourceclient.bean.ApplyRefereeMessage;
import pers.zhentao.refereeresourceclient.bean.FindRefereeMessage;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.ApplyRefereeService;

/**
 * Created by ZhangZT on 2016/8/21 12:22.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ShowApply extends Activity {

    private List<ApplyRefereeMessage> list;
    private ShowApplyAdapter adapter;
    private ListView listView;
    private FindRefereeMessage message;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_show_apply);
        init();
    }
    @Override
    protected void onResume(){
        super.onResume();
        refreshList();
    }
    private void init(){
        message = (FindRefereeMessage)getIntent().getSerializableExtra("findRefereeMessage");
        ImageButton btnBack = (ImageButton)findViewById(R.id.back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBack.setVisibility(View.VISIBLE);
        TextView tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("查看报名");
        tvTitle.setVisibility(View.VISIBLE);
        listView = (ListView)findViewById(R.id.lv_show_apply);
        list = new ArrayList<>();
        adapter = new ShowApplyAdapter(this,R.layout.item_show_apply,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowApply.this,ApplyDetail.class);
                intent.putExtra("applyRefereeMessage",list.get(position));
                startActivity(intent);
            }
        });
    }
    private void refreshList(){
        new ApplyRefereeService().find(" where findRefereeMessage=" + message.getId() + " and is_delete=false", new FindListener<ApplyRefereeMessage>() {
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
//        query.addWhereEqualTo("findRefereeMessage",message)
//            .addWhereEqualTo("isDelete",false)
//            .include("referee,refereeUser");
//        query.findObjects(this, new FindListener<ApplyRefereeMessage>() {
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
