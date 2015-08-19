package com.app.activity;

import java.io.File;

import com.app.helper.CircularImage;
import com.app.qin.CallbackImplements;
import com.app.qin.SyncImageLoader;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseAuth;
import edu.nju.shalbum.base.BaseUi;
import edu.nju.shalbum.model.User;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Personal_pageActivity extends BaseUi{
	private CircularImage head_image;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_page);

		head_image = (CircularImage) findViewById(R.id.cover_user_photo);  
		head_image.setImageResource(R.drawable.head_default);  
		
		File headImageFile = new File(SyncImageLoader.headImagePath);
		//读取SD卡上的已经下载头像
        if(headImageFile.exists()){
        	Bitmap bitmap = BitmapFactory.decodeFile(SyncImageLoader.headImagePath);
        	head_image.setImageBitmap(bitmap);
        	System.out.println("读取缓存");
        }
        User user = BaseAuth.getUser();
        BaseAuth.setUser(user);
        //修改标题栏
        if(!user.getUsername().equals("") && !(user.getUsername() == null))
        	setTitle(user.getUsername());
        TextView albumcount = (TextView)findViewById(R.id.albumcount);
        TextView followcount = (TextView)findViewById(R.id.followcount);
        TextView fanscount = (TextView)findViewById(R.id.fanscount);
        TextView signature_text = (TextView)findViewById(R.id.signature_text);
        Log.v("11111111111111111111111111111111", user.getUsername()+user.getAlbumcount());
        if(user.getAlbumcount() != null)
        	albumcount.setText(user.getAlbumcount());
        if(user.getFollowcount() != null)
        	followcount.setText(user.getFollowcount());
        if(user.getFanscount() != null)
        	fanscount.setText(user.getFanscount());
        System.out.println("签名:"+user.getSign());
        if(user.getSign() != null)
        	signature_text.setText(user.getSign());
        
        /**
         * 关注
         */
        LinearLayout  attentionLayout = (LinearLayout)findViewById(R.id.attention);
        attentionLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Personal_pageActivity.this,AttentionActivity.class));
				overridePendingTransition(R.anim.forward_slide_in_left,  
	                    R.anim.forward_slide_out_right); 
			}
        	
        });
        /**
         * 粉丝
         */
        LinearLayout  fansLayout = (LinearLayout) findViewById(R.id.fans);
        fansLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Personal_pageActivity.this,FansActivity.class));
				overridePendingTransition(R.anim.forward_slide_in_left,  
	                    R.anim.forward_slide_out_right); 
			}
        	
        });
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
