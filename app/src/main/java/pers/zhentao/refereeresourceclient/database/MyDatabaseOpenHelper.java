package pers.zhentao.refereeresourceclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZhangZT on 2016/3/16 15:01.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

    private final String CREATE_USER_TABLE = "create table refereeResource_user(id varchar(12),birth_day int,birth_month int,birth_year int,gender int,address varchar(50),name varchar(10),e_mail varchar(20),nick_name varchar(20),county varchar(20),province varchar(10),city varchar(20),phone_number varchar(20),avatarUrl varchar(100),updatedAt Date)";
    private final String CREATE_FRIEND_TABLE = "create table refereeResource_friend(id varchar(12),birth_day int,birth_month int,birth_year int,gender int,address varchar(50),name varchar(10),e_mail varchar(20),nick_name varchar(20),county varchar(20),province varchar(10),city varchar(20),phone_number varchar(20),avatarUrl varchar(100),updatedAt Date)";
    public MyDatabaseOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FRIEND_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
