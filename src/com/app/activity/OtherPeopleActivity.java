package com.app.activity;

import java.io.File;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.helper.CircularImage;
import com.app.qin.SyncImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseAuth;
import edu.nju.shalbum.base.BaseUi;
import edu.nju.shalbum.model.User;

public class OtherPeopleActivity extends BaseUi{
	private String image,name,userid,userPhotoCount,userFollowCount,userFansCount,usersign;
	private CircularImage head_image;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_otherpeople);
		
		Intent intent = getIntent();   
		//获取数据   
		image = intent.getStringExtra("image");   
		name = intent.getStringExtra("name");
		userid = intent.getStringExtra("userid");
		userPhotoCount = intent.getStringExtra("userPhotoCount");
		userFollowCount = intent.getStringExtra("userFollowCount");
		userFansCount = intent.getStringExtra("userFansCount");
		usersign = intent.getStringExtra("usersign");
		
		head_image = (CircularImage) findViewById(R.id.cover_user_photo);  
		head_image.setImageResource(R.drawable.head_default);  
		
		//显示头像
        imageLoader.displayImage(image, head_image);
        //修改标题栏
        setTitle(name);
        TextView albumcount = (TextView)findViewById(R.id.albumcount);
        TextView followcount = (TextView)findViewById(R.id.followcount);
        TextView fanscount = (TextView)findViewById(R.id.fanscount);
        TextView signature_text = (TextView)findViewById(R.id.signature_text);
        if(userPhotoCount != null)
        	albumcount.setText(userPhotoCount);
        if(userFollowCount != null)
        	followcount.setText(userFollowCount);
        if(userFansCount != null)
        	fanscount.setText(userFansCount);
        if(usersign != null)
        	signature_text.setText(usersign);
        System.out.println("他人主页"+userPhotoCount+userFollowCount+userFansCount+usersign);
        //设置ActionBar返回
        ActionBar bar = getActionBar();  //获取ActionBar的对象
        bar.setDisplayHomeAsUpEnabled(true);  
        
	}
	/*
	 * 重写手机返回键
	 */
	@Override  
    public void onBackPressed() {  
        super.onBackPressed();  
        finish();
        //返回动画
		overridePendingTransition(R.anim.back_slide_in_left,  
                R.anim.back_slide_in_right); 
    }  
	/*
	 * 重写左上角返回键
	 */
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            //返回动画
    		overridePendingTransition(R.anim.back_slide_in_left,  
                    R.anim.back_slide_in_right); 

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
