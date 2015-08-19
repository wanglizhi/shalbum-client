package com.example.localphotodemo;

import edu.nju.shalbum.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TagActivity extends Activity {
	public static String tag;
	private ImageButton btnback,btnright;
	private ImageButton btn_tag1,btn_tag2,btn_tag3,btn_tag4,btn_tag5,btn_tag6,btn_tag7,btn_tag8,btn_tag9;
	//真正的tag存放地点,即使修改xml文件也会影响
	private static String[] tagArray = {"电脑","手机","食品",
								 "游戏","服装","手机配件",
								 "电脑配件","运动","日用品"};
	private ImageButton selectedBtn=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 这里要主要requestWindowFeature和setContentView先后顺序哦
		setContentView(R.layout.activity_tag);
		
		//返回按钮
		btnback = (ImageButton)findViewById(R.id.btnback);
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tag = null;
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
				if(tag != null){
					Intent intent = new Intent(TagActivity.this,TitleActivity.class);
					startActivity(intent);
					//前进动画
		        	overridePendingTransition(R.anim.forward_slide_in_left,  
		                    R.anim.forward_slide_out_right); 
				}
				else{
					Toast.makeText(TagActivity.this, "请选择一个标签!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		btn_tag1 = (ImageButton)findViewById(R.id.button1);
		btn_tag2 = (ImageButton)findViewById(R.id.button2);
		btn_tag3 = (ImageButton)findViewById(R.id.button3);
		btn_tag4 = (ImageButton)findViewById(R.id.button4);
		btn_tag5 = (ImageButton)findViewById(R.id.button5);
		btn_tag6 = (ImageButton)findViewById(R.id.button6);
		btn_tag7 = (ImageButton)findViewById(R.id.button7);
		btn_tag8 = (ImageButton)findViewById(R.id.button8);
		btn_tag9 = (ImageButton)findViewById(R.id.button9);
		selectedBtn = btn_tag1;
		btn_tag1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[0];
				setBtnSelect(btn_tag1);
			}
		});
		btn_tag2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[1];
				setBtnSelect(btn_tag2);
			}
		});
		btn_tag3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[2];
				setBtnSelect(btn_tag3);
			}
		});
		btn_tag4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[3];
				setBtnSelect(btn_tag4);
			}
		});
		btn_tag5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[4];
				setBtnSelect(btn_tag5);
			}
		});
		btn_tag6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[5];
				setBtnSelect(btn_tag6);
			}
		});
		btn_tag7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[6];
				setBtnSelect(btn_tag7);
			}
		});
		btn_tag8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[7];
				setBtnSelect(btn_tag8);
			}
		});
		btn_tag9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tag = tagArray[8];
				setBtnSelect(btn_tag9);
			}
		});
		
	}
	
	//获取标签-String
	public static String getTag(){
		return tag;
	}
	//设置按钮的状态
	private void setBtnSelect(ImageButton btn){
		selectedBtn.setSelected(false);
		btn.setSelected(true);
		selectedBtn=btn;
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
	
	public static String[] getTagArray(){
		return tagArray;
	}
	
}