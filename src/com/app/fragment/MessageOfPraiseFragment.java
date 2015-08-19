package com.app.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseFragment;
import edu.nju.shalbum.base.C;

public class MessageOfPraiseFragment extends BaseFragment {
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
		/**
		 * 初始化数据
		 */
		mData = new ArrayList<Map<String, String>>();
        Map<String, String> a = new HashMap<String, String>();
        a.put("image", "http://t11.baidu.com/it/u=2816911771,1041897738&fm=56");
        a.put("name", "蜡笔小新");
        mData.add(a);
        Map<String, String> b = new HashMap<String, String>();
        b.put("image", "http://t12.baidu.com/it/u=3092593067,2507026721&fm=56");
        b.put("name", "王协");
        mData.add(b);
        Map<String, String> c = new HashMap<String, String>();
        c.put("image", "http://t12.baidu.com/it/u=3092593067,2507026721&fm=56");
        c.put("name", "王立志");
        mData.add(c);
    }  
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.messageofpraise_list, container, false);
        
        //初始化适配器和Listview
        adapter = new ItemAdapter (getActivity());  
        listView =(ListView) rootView.findViewById(android.R.id.list);
        listView.setAdapter(adapter);  
        //设置间隔
        listView.setDividerHeight(2);
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
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
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
				view = mInflater.inflate(R.layout.item_messageofpraise,null);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.name);
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
