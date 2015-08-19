package com.example.localphotodemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.app.activity.MainActivity;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseMessage;
import edu.nju.shalbum.base.BaseUi;
import edu.nju.shalbum.base.C;
import edu.nju.shalbum.model.Album;
import edu.nju.shalbum.model.Photo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TitleActivity extends BaseUi{
	//标题
	private String title;
	//所有图片信息(描述,图片地址)
	private List<Map<String, Object>> photoInfo = MyListView4.getAllPhotoData();
	//标签
	private String tag = TagActivity.getTag();
	private ImageButton btnback,btnright;
	private EditText edit;
	private LinearLayout loadingLay;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 这里要主要requestWindowFeature和setContentView先后顺序哦
		setContentView(R.layout.activity_title);
		
		//获取标题
		edit=(EditText) findViewById(R.id.editText);
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		//返回按钮
		btnback = (ImageButton)findViewById(R.id.btnback);
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				//返回动画
				overridePendingTransition(R.anim.back_slide_in_left,  
		                R.anim.back_slide_in_right); 
			}
		});
		//前进按钮
		btnright = (ImageButton)findViewById(R.id.btnright);
		btnright.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnright.setEnabled(false);
				title = edit.getText().toString();
				if(title == null || title.equals(""))
					Toast.makeText(TitleActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
				else{
					System.out.println("标题:"+title);
					System.out.println("标签:"+tag);
					for(Map<String, Object> eachPhoto :photoInfo){
						System.out.println("描述："+ eachPhoto.get("info"));
						//格式化地址
						String path = ((String) eachPhoto.get("img")).substring(7);
						System.out.println("图片地址："+ path);
					}
					//调用添加一个Album相册的任务
					HashMap<String, String> albumParams = new HashMap<String, String>();
					albumParams.put("name", title);
					//稍后再加tag
					albumParams.put("tag", tag);
					loadingLay.setVisibility(View.VISIBLE);
					doTaskAsync(C.task.albumCreate, C.api.albumCreate, albumParams);
				}
			}
		});
		edit.requestFocus();
		edit.requestFocusFromTouch();

		//自动弹出键盘
		Timer timer = new Timer();   
        	timer.schedule(new TimerTask(){   
  
        		@Override  
        		public void run() {   
        			InputMethodManager m = (InputMethodManager)   
        					edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);   
        			m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);   

        		}   
               
        	}, 300);  
        	
        	 //加载过渡动画
            loadingLay = (LinearLayout)findViewById(R.id.loadingLay);
            loadingLay.setVisibility(View.INVISIBLE);

		}
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);

		switch (taskId) {
			case C.task.albumCreate:
				try {
					@SuppressWarnings("unchecked")
					final Album album = (Album) message.getResult("Album");
					//以此添加Photo
					for(Map<String, Object> eachPhoto :photoInfo){
						//格式化地址
						String path = ((String) eachPhoto.get("img")).substring(7);
						//调用添加一个photo的任务
						HashMap<String, String> photoParams = new HashMap<String, String>();
						photoParams.put("describe", (String) eachPhoto.get("info"));
						photoParams.put("albumid",album.getAlbumid());
						photoParams.put("content",path);
						doTaskAsync(C.task.photoCreate, C.api.photoCreate, photoParams);
					}
					//最后跳转
					Intent intent = new Intent(TitleActivity.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					loadingLay.setVisibility(View.GONE);
					startActivity(intent);
					//返回动画
					overridePendingTransition(R.anim.back_slide_in_left,  
			                R.anim.back_slide_in_right); 
					Toast.makeText(TitleActivity.this, "创建成功~", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
					loadingLay.setVisibility(View.GONE);
				}
				break;
			case C.task.photoCreate:
				//新建完Photo上传图片
			try {
				final Photo photo = (Photo) message.getResult("Photo");
				//上传图片
				this.doUploadImage(C.task.uploadPhoto, C.api.uploadPhoto,photo.getContent(),photo.getPhotoid());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		}
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
}
