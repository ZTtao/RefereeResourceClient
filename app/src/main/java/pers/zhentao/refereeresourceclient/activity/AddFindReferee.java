package pers.zhentao.refereeresourceclient.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;
import java.util.Calendar;

import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.bean.FindRefereeMessage;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.listener.SaveListener;
import pers.zhentao.refereeresourceclient.service.FindRefereeService;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/8/20 16:53.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class AddFindReferee extends Activity{

    private Integer selectedYear = -1;
    private Integer selectedMonth = -1;
    private Integer selectedDay = -1;
    private Integer selectedHour = -1;
    private Integer selectedMinute = -1;
    private EditText etState;
    private EditText etAddress;
    private EditText etCount;
    private EditText etClaim;
    private EditText etPay;
    private EditText etNote;
    private TextView tvGameDate;
    private TextView tvGameTime;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_find_referee);
        init();
    }
    private void init(){
        tvGameDate = (TextView)findViewById(R.id.tv_game_date);
        tvGameTime = (TextView)findViewById(R.id.tv_game_time);
        etState = (EditText)findViewById(R.id.et_add_find_referee_state);
        etAddress = (EditText)findViewById(R.id.et_add_find_referee_address);
        etCount = (EditText)findViewById(R.id.et_add_find_referee_count);
        etClaim = (EditText)findViewById(R.id.et_add_find_referee_claim);
        etNote = (EditText)findViewById(R.id.et_add_find_referee_note);
        etPay = (EditText)findViewById(R.id.et_add_find_referee_pay);
        ImageButton btnBack = (ImageButton)findViewById(R.id.back_btn);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView imgPublish = (ImageView)findViewById(R.id.btn_right_publish);
        imgPublish.setImageBitmap(BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(), R.mipmap.btn_right_publish));
        imgPublish.setVisibility(View.VISIBLE);
        imgPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etState.getText().toString().equals("") || etAddress.getText().toString().equals("")
                        || etCount.getText().toString().equals("") || etClaim.getText().toString().equals("")
                        || etNote.getText().toString().equals("") || etPay.getText().toString().equals("")
                        || tvGameDate.getText().toString().equals("") || tvGameTime.getText().toString().equals("")) {
                    Toast.makeText(AddFindReferee.this, "请完善信息", Toast.LENGTH_SHORT).show();
                } else {
                    FindRefereeMessage message = new FindRefereeMessage();
                    message.setUser(ContextUtil.getUserInstance());
                    message.setGameState(etState.getText().toString());
                    message.setAddress(etAddress.getText().toString());
                    message.setRefereeCount(Integer.parseInt(etCount.getText().toString()));
                    message.setRefereeClaim(etClaim.getText().toString());
                    message.setPay(etPay.getText().toString());
                    message.setNote(etNote.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(selectedYear,selectedMonth,selectedDay,selectedHour,selectedMinute,0);
                    message.setTime(calendar.getTime());
                    message.setPublishTime(new Date());
                    new FindRefereeService().save(message, new SaveListener() {
                        @Override
                        public void onSuccess(int id) {
                            Toast.makeText(AddFindReferee.this,"发布成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(AddFindReferee.this,"发布失败，请重试",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        tvGameDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (selectedYear == -1) {
                    selectedYear = calendar.get(Calendar.YEAR);
                    selectedMonth = calendar.get(Calendar.MONTH);
                    selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
                }

                Dialog dialog = new DatePickerDialog(AddFindReferee.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvGameDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        selectedYear = year;
                        selectedMonth = monthOfYear;
                        selectedDay = dayOfMonth;
                    }
                }, selectedYear, selectedMonth, selectedDay);
                dialog.show();
            }
        });
        tvGameTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if(selectedHour == -1){
                    selectedHour = calendar.get(Calendar.HOUR_OF_DAY);
                    selectedMinute = calendar.get(Calendar.MINUTE);
                }
                Dialog dialog = new TimePickerDialog(AddFindReferee.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvGameTime.setText(hourOfDay+":"+(minute>9?minute:("0"+minute)));
                        selectedHour = hourOfDay;
                        selectedMinute = minute;
                    }
                },selectedHour,selectedMinute,true);
                dialog.show();
            }
        });
    }
}
