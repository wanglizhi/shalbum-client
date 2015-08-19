package com.example.localphotodemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localphotodemo.PhotoFolderFragment.OnPageLodingClickListener;
import com.example.localphotodemo.PhotoFragment.OnPhotoSelectClickListener;
import com.example.localphotodemo.bean.PhotoInfo;
import com.example.localphotodemo.bean.PhotoSerializable;
import com.example.localphotodemo.util.CheckImageLoaderConfiguration;

import edu.nju.shalbum.R;
/**    
 * @title SelectPhotoActivity.java
 * @package com.centaline.mhc.activity.friendGroupActivity
 * @author guilin   
 * @date 2013-8-6 上午10:42:15  
 */
public class SelectPhotoActivity extends FragmentActivity implements OnPageLodingClickListener
				,OnPhotoSelectClickListener{

	private PhotoFolderFragment photoFolderFragment;
	
	private ImageButton btnback;
	private ImageButton btnright;
	private TextView title;
	
	private List<PhotoInfo> hasList;
	
	private FragmentManager manager;
	private int backInt = 0;
	/**
	 * 已选择图片数量
	 */
	private int count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//无title      
	    requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_selectphoto);
		 
		getWindowManager().getDefaultDisplay().getMetrics(MyApplication.getDisplayMetrics());   
		
		count = getIntent().getIntExtra("count", 0);
		
		manager = getSupportFragmentManager();
		
		hasList = new ArrayList<PhotoInfo>();
		
		btnback = (ImageButton)findViewById(R.id.btnback);
		btnright = (ImageButton)findViewById(R.id.btnright);
		title = (TextView)findViewById(R.id.title);
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(backInt==0){
					finish();
					//返回动画
					overridePendingTransition(R.anim.back_slide_in_left,  
			                R.anim.back_slide_in_right);
				}else if(backInt==1){
					backInt--;
					hasList.clear();
					title.setText("请选择相册");
					FragmentTransaction transaction = manager.beginTransaction();
					transaction.show(photoFolderFragment).commit();
					manager.popBackStack(0, 0);
				}
			}
		});
		btnright.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(hasList.size()>0){
					System.out.println("图片地址："+hasList.get(0).getPath_absolute());
					Intent intent = new Intent(SelectPhotoActivity.this,MyListView4.class);
					Bundle map = new Bundle();
					String[] path = new String[hasList.size()];
					for(int i=0;i<hasList.size();i++)
						path[i] = "file://" + hasList.get(i).getPath_absolute();
					map.putSerializable("photo_path",path);
					intent.putExtra("path", map);
		        	startActivity(intent);
		        	//前进动画
		        	overridePendingTransition(R.anim.forward_slide_in_left,  
		                    R.anim.forward_slide_out_right); 
				}else{
					Toast.makeText(SelectPhotoActivity.this, "至少选择一张图片", Toast.LENGTH_SHORT).show();
				}
			}
		});
		title.setText("请选择相册");

		photoFolderFragment = new PhotoFolderFragment();

		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.body,photoFolderFragment);  
		transaction.addToBackStack(null);
		// Commit the transaction  
		transaction.commit(); 
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		CheckImageLoaderConfiguration.checkImageLoaderConfiguration(this);
	}

	@Override
	public void onPageLodingClickListener(List<PhotoInfo> list) {
		// TODO Auto-generated method stub
		title.setText("已选择0张");
		FragmentTransaction transaction = manager.beginTransaction();
		PhotoFragment photoFragment = new PhotoFragment();
		Bundle args = new Bundle();
		PhotoSerializable photoSerializable = new PhotoSerializable();
		for (PhotoInfo photoInfoBean : list) {
			photoInfoBean.setChoose(false);
		}
		photoSerializable.setList(list);
		args.putInt("count", count);
		args.putSerializable("list", photoSerializable);
		photoFragment.setArguments(args);
		transaction = manager.beginTransaction();
		transaction.hide(photoFolderFragment).commit();
		transaction = manager.beginTransaction();
		transaction.add(R.id.body,photoFragment);  
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		// Commit the transaction  
		transaction.commit(); 
		backInt++;
	}

	@Override
	public void onPhotoSelectClickListener(List<PhotoInfo> list) {
		// TODO Auto-generated method stub
		hasList.clear();
		for (PhotoInfo photoInfoBean : list) {
			if(photoInfoBean.isChoose()){
				hasList.add(photoInfoBean);
			}
		}
		title.setText("已选择"+hasList.size()+"张");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK&&backInt==0){
			finish();
			//返回动画
			overridePendingTransition(R.anim.back_slide_in_left,  
	                R.anim.back_slide_in_right); 
		}else if(keyCode == KeyEvent.KEYCODE_BACK&&backInt==1){
			backInt--;
			hasList.clear();
			title.setText("请选择相册");
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.show(photoFolderFragment).commit();
			manager.popBackStack(0, 0);
		}
		return false;
	}
	
	
}
