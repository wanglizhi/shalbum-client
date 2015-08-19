package com.app.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.app.activity.OtherPeopleActivity;
import com.app.fragment.RecommendAttention.ItemAdapter;
import com.example.localphotodemo.MyListView4;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseAuth;
import edu.nju.shalbum.base.BaseFragment;
import edu.nju.shalbum.base.BaseMessage;
import edu.nju.shalbum.base.C;
import edu.nju.shalbum.model.User;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class HaveAttentionFragment extends BaseFragment {
	private DisplayImageOptions options; //配置图片加载及显示选项
	private ImageLoader imageLoader = ImageLoader.getInstance();
	public static List<Map<String, String>> mData= new ArrayList<Map<String, String>>();;
	private ListView listView;
	private ItemAdapter adapter = null;  
	
	/**
	 * 下拉刷新
	 * 
	 */
	private PullToRefreshListView mPullRefreshListView;
	private  HashMap<String, String> albumParams;
    
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        
		//配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)    //在ImageView加载过程中显示图片
			.showImageForEmptyUri(R.drawable.ic_empty)  //image连接地址为空时
			.showImageOnFail(R.drawable.ic_error)  //image加载失败
			.cacheInMemory(true)  //加载图片时会在内存中加载缓存
			.cacheOnDisc(true)   //加载图片时会在磁盘中加载缓存
			.displayer(new SimpleBitmapDisplayer())  //设置用户加载图片task(这里是正常图片显示)
			.build();
		albumParams = new HashMap<String, String>();
		albumParams.put("userid", BaseAuth.getUser().getUserid());
		this.doTaskAsync(C.task.getFollows, C.api.getFollows, albumParams);
          
    }
  
   
    @Override
	public void onStart() {
		super.onStart();
	}
    @Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case C.task.getFollows:
			try {
				@SuppressWarnings("unchecked")
				ArrayList<User> userList = (ArrayList<User>)message.getResultList("User");
				mData.clear();
		    	for(User u:userList){
		    		Map<String, String> a = new HashMap<String, String>();
		            a.put("image",u.getFace());
		            a.put("name",u.getUsername());
		            a.put("userid", u.getUserid());
		            a.put("userPhotoCount",u.getAlbumcount());
		            a.put("userFollowCount", u.getFollowcount());
		            a.put("userFansCount",u.getFanscount());
		            a.put("usersign", u.getSign());
		            mData.add(a);
		    	}
		    	adapter = new ItemAdapter (getActivity()); 
		        listView.setAdapter(adapter);  
		    	this.onStart();
	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//刷新等待结束
	    	mPullRefreshListView.onRefreshComplete();
			break;
		case C.task.fansDel:
			Toast.makeText(getActivity(), "取消关注成功~", Toast.LENGTH_SHORT).show();
			break;
		}
	}
      
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.haveattention_list, container, false);
        
        /**
         * 设置下拉刷新
         */
        mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				albumParams = new HashMap<String, String>();
				albumParams.put("userid", BaseAuth.getUser().getUserid());
				doTaskAsync(C.task.getFollows, C.api.getFollows, albumParams);
			}
		});
		
        //初始化适配器和Listview
        adapter = new ItemAdapter (getActivity());  
        listView =(ListView) rootView.findViewById(android.R.id.list);
        listView.setAdapter(adapter);  
        //设置间隔
        listView.setDividerHeight(2);
        //点击进入他人主页
        listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Map<String,String> info = mData.get(position-1);
				Intent intent = new Intent(getActivity(),OtherPeopleActivity.class);  
				
				//绑定数据   
				intent.putExtra("image",info.get("image"));   
				intent.putExtra("name",info.get("name"));
				intent.putExtra("userid",info.get("userid"));  
				intent.putExtra("userPhotoCount",info.get("userPhotoCount"));  
				intent.putExtra("userFollowCount",info.get("userFollowCount"));  
				intent.putExtra("userFansCount",info.get("userFansCount"));  
				intent.putExtra("usersign",info.get("usersign")); 
				getActivity().startActivity(intent);
				//前进切换动画
				getActivity().overridePendingTransition(R.anim.forward_slide_in_left,  
	                    R.anim.forward_slide_out_right);
			}
        	
        });
        return rootView;
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
			public Button button;
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
				view = mInflater.inflate(R.layout.item_haveattention,null);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.text);
				holder.image = (ImageView) view.findViewById(R.id.image);
				
				holder.button = (Button) view.findViewById(R.id.button1);
				holder.button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						//取消关注
						HashMap<String, String> followParams = new HashMap<String, String>();
						followParams.put("userid", BaseAuth.getUser().getUserid());
						followParams.put("followid", mData.get(position).get("userid"));
						doTaskAsync(C.task.fansDel, C.api.fansDel, followParams);
						mData.remove(position);
						adapter.notifyDataSetChanged();
					}
				});
				//让按钮失去焦点，让ListView能够获得点击响应
				holder.button.setFocusableInTouchMode(false);                            
				holder.button.setFocusable(false);
				
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
