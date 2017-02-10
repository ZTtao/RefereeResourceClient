package pers.zhentao.refereeresourceclient.activity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import pers.zhentao.refereeresourceclient.R;
import pers.zhentao.refereeresourceclient.activity.LikePost;
import pers.zhentao.refereeresourceclient.activity.MyPost;
import pers.zhentao.refereeresourceclient.activity.MyReview;
import pers.zhentao.refereeresourceclient.activity.PerfectInfo;
import pers.zhentao.refereeresourceclient.bean.myEvent.FreshMainpageEvent;
import pers.zhentao.refereeresourceclient.globalvariable.Common;
import pers.zhentao.refereeresourceclient.util.ContextUtil;

/**
 * 我的资料fragment
 * Created by ZhangZT on 2016/7/26 18:07.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FragmentMy extends Fragment {
    private ImageView imgAvatar;
    private TextView tvName;
    private ImageView imgLikePost;
    private Bitmap bitmapLikePost = null;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup group,Bundle bundle){
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_my,group,false);
        imgAvatar = (ImageView)view.findViewById(R.id.img_avatar_fragment_my);
        tvName = (TextView)view.findViewById(R.id.tv_user_name_fragment_my);
        imgLikePost = (ImageView)view.findViewById(R.id.img_third_fragment_my);
        RelativeLayout lyUser = (RelativeLayout)view.findViewById(R.id.ly_user_fragment_my);
        RelativeLayout lyMyPost = (RelativeLayout)view.findViewById(R.id.ly_my_post_fragment_my);
        RelativeLayout lyMyReview = (RelativeLayout)view.findViewById(R.id.ly_my_review_fragment_my);
        RelativeLayout lyLikePost = (RelativeLayout)view.findViewById(R.id.ly_like_post_fragment_my);
//        Bitmap bitmap = CommonManager.getInstance().getAvatarAtLocal(Common.USER.getObjectId());
//        if(bitmap == null)
        //使用默认头像
            imgAvatar.setImageResource(R.mipmap.default_avatar);
//        else
//            imgAvatar.setImageBitmap(bitmap);
        tvName.setText(ContextUtil.getUserInstance().getNickName());
        lyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.INFO_EDIT_FLAG = Common.USER_BROWSE;
                startActivity(new Intent(ContextUtil.getInstance(), PerfectInfo.class));
            }
        });
        lyMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContextUtil.getInstance(), MyPost.class));
            }
        });
        lyLikePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContextUtil.getInstance(), LikePost.class));
            }
        });
        lyMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContextUtil.getInstance(), MyReview.class));
            }
        });
        return view;
    }
    @Override
    public void onResume(){
        if(bitmapLikePost == null){
            bitmapLikePost = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(),R.mipmap.icon_like_post);
            imgLikePost.setImageBitmap(bitmapLikePost);
        }
        if(ContextUtil.getUserInstance() != null){
//            Bitmap bitmap = CommonManager.getInstance().getAvatarAtLocal(Common.USER.getObjectId());
//            if(bitmap == null)
                imgAvatar.setImageResource(R.mipmap.default_avatar);
//            else
//                imgAvatar.setImageBitmap(bitmap);
            tvName.setText(ContextUtil.getUserInstance().getNickName());
        }
        super.onResume();
    }
    @Override
    public void onPause(){
        if(bitmapLikePost!=null){
            imgLikePost.setImageBitmap(null);
            bitmapLikePost.recycle();
            bitmapLikePost = null;
        }
        super.onPause();
    }
    @Override
    public void onHiddenChanged(boolean isHidden){
        if(isHidden){
            if(bitmapLikePost!=null){
                imgLikePost.setImageBitmap(null);
                bitmapLikePost.recycle();
                bitmapLikePost = null;
            }
        }else {
            if(bitmapLikePost == null){
                bitmapLikePost = BitmapFactory.decodeResource(ContextUtil.getInstance().getResources(),R.mipmap.icon_like_post);
                imgLikePost.setImageBitmap(bitmapLikePost);
            }
            if(ContextUtil.getUserInstance() != null){
//                Bitmap bitmap = CommonManager.getInstance().getAvatarAtLocal(Common.USER.getObjectId());
//                if(bitmap == null)
                    imgAvatar.setImageResource(R.mipmap.default_avatar);
//                else
//                    imgAvatar.setImageBitmap(bitmap);
                tvName.setText(ContextUtil.getUserInstance().getNickName());
            }
        }
        super.onHiddenChanged(isHidden);
    }
    public void onEventMainThread(FreshMainpageEvent event){
        try {
//            Bitmap bitmap = CommonManager.getInstance().getAvatarAtLocal(ContextUtil.getUserInstance().getUserId());
//            if (bitmap == null)
                imgAvatar.setImageResource(R.mipmap.default_avatar);
//            else
//                imgAvatar.setImageBitmap(bitmap);
            tvName.setText(ContextUtil.getUserInstance().getNickName());
        }catch (NullPointerException e){
            imgAvatar.setImageResource(R.mipmap.default_avatar);
            tvName.setText("");
        }
    }
}
