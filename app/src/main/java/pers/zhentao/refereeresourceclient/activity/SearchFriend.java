package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.adapter.SearchFriendAdapter;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.FindListener;
import pers.zhentao.refereeresourceclient.service.UserService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/7/13 20:13.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class SearchFriend extends Activity {

    private EditText et_search;
    private ImageButton btn_search;
    private ListView listView;
    private ImageButton btn_back;
    private String name_et;
    private List<User> list;
    private SearchFriendAdapter adapter;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_search_friend);
        init();
    }

    private void init(){
        et_search = (EditText)findViewById(R.id.et_search_friend);
        btn_search = (ImageButton)findViewById(R.id.img_btn_search_friend);
        listView = (ListView)findViewById(R.id.lv_search_friend);
        btn_back = (ImageButton)findViewById(R.id.back_btn);
        tvTitle = (TextView)findViewById(R.id.title_text);
        tvTitle.setText("搜索好友");
        tvTitle.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new SearchFriendAdapter(SearchFriend.this,R.layout.item_friend_list,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchFriend.this,PerfectInfo.class);
                if(list.get(position).getUserId().equals(ContextUtil.getUserInstance().getUserId())){
                    Common.INFO_EDIT_FLAG = 0;
                }else{
                    Common.INFO_EDIT_FLAG = 2;
                    intent.putExtra("user",list.get(position));
                }
                startActivity(intent);
            }
        });
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_et = et_search.getText().toString();
                new UserService().findUserLikeNickName(name_et, new FindListener<User>() {
                    @Override
                    public void onSuccess(final List<User> li) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                list.clear();
                                for (int i = 0; i < li.size(); i++) {
                                    list.add(li.get(i));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onError(int errorCode, String result) {

                    }
                });
//                BmobQuery<CommonUser> query = new BmobQuery<>();
//                query.addWhereEqualTo("nick_name", name_et);
//                query.findObjects(SearchFriend.this, new FindListener<CommonUser>() {
//                    @Override
//                    public void onSuccess(List<CommonUser> l) {
//                        list.clear();
//                        for (int i = 0; i < l.size(); i++) {
//                            list.add(l.get(i));
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//
//                    }
//                });
            }
        });

    }
}
