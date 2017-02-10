package pers.zhentao.refereeresourceclient.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import pers.zhentao.refereeresourceclient.bean.User;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * Created by ZhangZT on 2016/3/16 15:06.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class RefereeResourceDB {

    public static final String DB_NAME = "referee_resource";
    public static final int VERSION = 1;
    private static RefereeResourceDB refereeResourceDB = null;
    private static SQLiteDatabase db = null;

    /**
     * 构造方法私有化
     */
    private RefereeResourceDB(Context context){
        MyDatabaseOpenHelper dbHelper = new MyDatabaseOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获得SQLiteDatabase实例
     */
    public synchronized static RefereeResourceDB getInstance(){
        if(db==null){
            refereeResourceDB = new RefereeResourceDB(ContextUtil.getInstance());
        }
        return refereeResourceDB;
    }

    /**
     * 将User实例存储数据库
     */
    public void saveUser(User user){
        //保存之前清空数据库
        clearDB();
        if(user!=null){
            ContentValues values = new ContentValues();
            values.put("id",user.getUserId());
            values.put("birth_day", user.getBirthDay());
            values.put("birth_month", user.getBirthMonth());
            values.put("birth_year", user.getBirthYear());
            values.put("gender", user.getGender());
            values.put("address", user.getAddress() == null ? "" : user.getAddress());
            values.put("name", user.getName() == null ? "" : user.getName());
            values.put("e_mail", user.getEMail() == null ? "" : user.getEMail());
            values.put("nick_name", user.getNickName() == null ? "" : user.getNickName());
            values.put("county", user.getCounty() == null ? "" : user.getCounty());
            values.put("province", user.getProvince() == null ? "" : user.getProvince());
            values.put("city", user.getCity() == null ? "" : user.getCity());
            values.put("phone_number", user.getPhoneNumber());
//            values.put("avatarUrl", user.getAvatar() == null ? "" : user.getAvatar());
            values.put("updatedAt", user.getTime() == null ? null : user.getTime().toString());
            db.insert("refereeResource_user",null,values);
        }
    }
    /**
     * 从数据库读取User信息
     */
    public User getLocateUser(){
        Cursor cursor = db.query("refereeResource_user",null,null,null,null,null,null);
        User user = null;
        if(cursor.moveToNext()) {
            user = new User();
            user.setUserId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setBirthDay(cursor.getInt(cursor.getColumnIndex("birth_day")));
            user.setBirthMonth(cursor.getInt(cursor.getColumnIndex("birth_month")));
            user.setBirthYear(cursor.getInt(cursor.getColumnIndex("birth_year")));
            user.setGender(cursor.getInt(cursor.getColumnIndex("gender")));
            user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setEMail(cursor.getString(cursor.getColumnIndex("e_mail")));
            user.setNickName(cursor.getString(cursor.getColumnIndex("nick_name")));
            user.setCounty(cursor.getString(cursor.getColumnIndex("county")));
            user.setProvince(cursor.getString(cursor.getColumnIndex("province")));
            user.setCity(cursor.getString(cursor.getColumnIndex("city")));
            user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phone_number")));
//            user.setAvatar(cursor.getString(cursor.getColumnIndex("avatarUrl")));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                user.setTime(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("updatedAt"))));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return user;
    }


    private void clearDB(){
        db.delete("refereeResource_user",null,null);
    }
    private void clearPlayerUser(){
        db.delete("refereeResource_player",null,null);
    }

}
