package com.app.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.app.fragment.HaveAttentionFragment;
import com.app.fragment.RecommendAttention;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseAuth;
import edu.nju.shalbum.base.BaseMessage;
import edu.nju.shalbum.base.BaseUi;
import edu.nju.shalbum.base.C;
import edu.nju.shalbum.model.User;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FansActivity extends BaseUi{

	private DisplayImageOptions options; //配置图片加载及显示选项
	private ImageLoader imageLoader = ImageLoader.getInstance();
	public static List<Map<String, String>> mData;
	private ListView listView;
	private ItemAdapter adapter = null;  
	private ArrayList<User> fansList;
    
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_fans);
		//配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)    //在ImageView加载过程中显示图片
			.showImageForEmptyUri(R.drawable.ic_empty)  //image连接地址为空时
			.showImageOnFail(R.drawable.ic_error)  //image加载失败
			.cacheInMemory(true)  //加载图片时会在内存中加载缓存
			.cacheOnDisc(true)   //加载图片时会在磁盘中加载缓存
			.displayer(new SimpleBitmapDisplayer())  //设置用户加载图片task(这里是正常图片显示)
			.build();
		
		HashMap<String, String> fansParams = new HashMap<String, String>();
		fansParams.put("userid", BaseAuth.getUser().getUserid());
		this.doTaskAsync(C.task.getFans, C.api.getFans, fansParams);
		
		/**
		 * 初始化数据
		 */
		mData = new ArrayList<Map<String, String>>();
        
		/*
		 * 初始化适配器
		 */
        adapter = new ItemAdapter (this);  
        listView =(ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter); 
        listView.setDividerHeight(2);
    }  
  
    @Override
   	public void onTaskComplete(int taskId, BaseMessage message) {
   		super.onTaskComplete(taskId, message);
   		switch (taskId) {
   		case C.task.getFans:
   			try {
   				@SuppressWarnings("unchecked")
   				ArrayList<User> userList = (ArrayList<User>)message.getResultList("User");
   				this.fansList=userList;
   				mData.clear();
   		    	for(User u:fansList){
   		    		Map<String, String> a = new HashMap<String, String>();
   		            a.put("image",u.getFace());
   		            a.put("name",u.getUsername());
   		            a.put("userid", u.getUserid());
   		            mData.add(a);
   		    	}
   		    	adapter = new ItemAdapter (this); 
   		        listView.setAdapter(adapter);  
   		    	this.onStart();
   		    	//刷新等待结束
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
		//清空动画
		HaveAttentionFragment.AnimateFirstDisplayListener.displayedImages.clear();
		RecommendAttention.AnimateFirstDisplayListener.displayedImages.clear();
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
        
            finish();
            //返回动画
    		overridePendingTransition(R.anim.back_slide_in_left,  
                    R.anim.back_slide_in_right);
            return true;
        //return super.onOptionsItemSelected(item);
    }
   

	/**自定义图片适配器**/
	class ItemAdapter extends BaseAdapter {

		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	    private LayoutInflater mInflater = null;  
	    
	    public ItemAdapter(Context context){  
	        super();  
	        mInflater = (LayoutInflater) context  
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	    }  
		private class ViewHolder {
			public TextView text;
			public ImageView image;
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = mInflater.inflate(R.layout.item_fans,null);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.text);
				holder.image = (ImageView) view.findViewById(R.id.image);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.text.setText(mData.get(position).get("name"));

			//Adds display image task to execution pool. Image will be set to ImageView when it's turn.
			imageLoader.displayImage(mData.get(position).get("image"), holder.image, options, animateFirstListener);

			return view;
		}
	}

	/**图片加载监听事件**/
	public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500); //设置image隐藏动画500ms
					displayedImages.add(imageUri); //将图片uri添加到集合中
				}
			}
		}
	}
	
}