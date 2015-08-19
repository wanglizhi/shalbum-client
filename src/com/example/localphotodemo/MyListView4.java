package com.example.localphotodemo;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.example.localphotodemo.util.UniversalImageLoadTool;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.SwipeDismissAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import edu.nju.shalbum.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListView4 extends ListActivity {
	private static List<Map<String, Object>> mData;
	private ImageButton btnback,btnright;
	private TextView camera;
	private MyAdapter adapter;
	private String strImgPath;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 这里要主要requestWindowFeature和setContentView先后顺序哦
		setContentView(R.layout.listactivity_mylistview4);
		mData = getData();
		adapter = new MyAdapter(this);
		setListAdapter(adapter);
	
		btnback = (ImageButton)findViewById(R.id.btnback);
		btnright = (ImageButton)findViewById(R.id.btnright);
		camera = (TextView)findViewById(R.id.camera);
		camera.setClickable(true);
		camera.setFocusable(true);
		camera.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				letCamera();
			}
			
		});
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
		btnright.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mData.size() != 0){
					Intent intent = new Intent(MyListView4.this,TagActivity.class);
					startActivity(intent);
					//前进动画
		        	overridePendingTransition(R.anim.forward_slide_in_left,  
		                    R.anim.forward_slide_out_right); 
				}
				else{
					Toast.makeText(MyListView4.this, "请添加至少一张图片哦~", Toast.LENGTH_SHORT).show();
				}
			}
		});
		

		
		//list滑动删除
		SwipeDismissAdapter swipeDismissAdapter = new SwipeDismissAdapter(adapter,new MyOnDismissCallback());
		swipeDismissAdapter.setListView(getListView());
		getListView().setAdapter(swipeDismissAdapter);
		
		//listview从左淡入动画，依赖library包
		SwingLeftInAnimationAdapter swingLeftInAnimationAdapter = new SwingLeftInAnimationAdapter(adapter);
		swingLeftInAnimationAdapter.setListView(getListView());
		getListView().setAdapter(swingLeftInAnimationAdapter);
		
	
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
	

	private class MyOnDismissCallback implements OnDismissCallback {

		

		@Override
		public void onDismiss(ListView listView, int[] reverseSortedPositions) {
			for (int position : reverseSortedPositions) {
				mData.remove(position);
				adapter.notifyDataSetChanged();
			}
			for(int i=0;i<mData.size();i++)
				mData.get(i).put("title", "第 " +(i+1) +" 张图片");
			Toast.makeText(MyListView4.this, "移除成功~", Toast.LENGTH_SHORT).show();
		}
	}
	

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		
		String[] path = (String[])this.getIntent().getBundleExtra("path").getSerializable("photo_path");
		
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<path.length;i++){
			map = new HashMap<String, Object>();
			map.put("title", "第 "+ (i + 1)+" 张图片");
			map.put("info", "还未添加描述哦~ ");
			map.put("img", path[i]);
			list.add(map);
		}
		
	
		return list;
	}
	
	/*
	 * 获取所有图片信息,传给最后一个activity(TitleActivity)
	 */
	public static List<Map<String, Object>> getAllPhotoData(){
		return mData;
	}
	
	/**
	 * listview中点击按键弹出对话框
	 */
	public void showInfo(String title,String info,final TextView v,final int position){
		LayoutInflater factory = LayoutInflater.from(MyListView4.this);//提示框  
        final View view = factory.inflate(R.layout.dialogview, null);//这里必须是final的  
        final EditText edit=(EditText)view.findViewById(R.id.editText);//获得输入框对象  
        edit.setHint(info);
        new AlertDialog.Builder(MyListView4.this,AlertDialog.THEME_HOLO_DARK)  
                .setTitle(title)//提示框标题  
                .setView(view)  
                .setPositiveButton("确定",//提示框的两个按钮  
                        new android.content.DialogInterface.OnClickListener() {  
                            @Override  
                            public void onClick(DialogInterface dialog,  
                                    int which) {  
                            	mData.get(position).put("info", edit.getText().toString());
                            	adapter.notifyDataSetChanged();
                            	 Toast.makeText(MyListView4.this, "添加成功", Toast.LENGTH_SHORT).show();
                            }  
                        }).setNegativeButton("取消", null).create().show(); 
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case 1:// 拍照
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "拍摄成功", Toast.LENGTH_SHORT).show();
                Map<String, Object> map = new HashMap<String, Object>();
        		map = new HashMap<String, Object>();
        		map.put("title", "第 "+ (mData.size() + 1)+"张图片");
        		map.put("info", "还未添加描述哦~ ");
        		map.put("img", "file://"+ strImgPath);
        		mData.add(map);
        		adapter.notifyDataSetChanged();
            }
            break;
        default:
            break;
        }
    }
	
	protected void letCamera() {
        // TODO Auto-generated method stub
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        strImgPath = Environment.getExternalStorageDirectory()
                .toString() + "/addPicture/";// 存放照片的文件夹
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date()) + ".jpg";// 照片命名
        
        File out = new File(strImgPath);
        if (!out.exists()) {
            out.mkdirs();
        }
        out = new File(strImgPath, fileName);
        strImgPath = strImgPath + fileName;// 该照片的绝对路径
        
        Uri uri = Uri.fromFile(out);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(imageCaptureIntent, 1);
    }
	
	
	
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView info;
		public ImageButton viewBtn;
	}
	
	
	public class MyAdapter extends BaseAdapter{
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		private DisplayImageOptions options;
		private ImageLoaderConfiguration config;
		private LayoutInflater mInflater;
		
		
		public MyAdapter(Context context){
			config = new ImageLoaderConfiguration.Builder(
	                getApplicationContext())
	                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
	                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
	                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
	                .discCacheFileCount(60)// 缓存文件的最大个数
	                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
	                .build();

	        // Initialize ImageLoader with configuration
	        ImageLoader.getInstance().init(config);
	        options = new DisplayImageOptions.Builder()
	                .showStubImage(R.drawable.ic_logo)// 设置图片在下载期间显示的图片
	                .showImageForEmptyUri(R.drawable.ic_logo)// 设置图片Uri为空或是错误的时候显示的图片
	                .showImageOnFail(R.drawable.ic_logo)// 设置图片加载/解码过程中错误时候显示的图片
	                .cacheInMemory(true)// 是否緩存都內存中
	                .cacheOnDisc(true)// 是否緩存到sd卡上
	                .displayer(new RoundedBitmapDisplayer(20)).build();
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int positionn) {
			// TODO Auto-generated method stub
			return mData.get(positionn);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			final int temp_position = position;
			if (convertView == null) {
				
				holder=new ViewHolder();  
				
				convertView = mInflater.inflate(R.layout.activity_selectedphoto, null);
				holder.img = (ImageView)convertView.findViewById(R.id.img);
				holder.title = (TextView)convertView.findViewById(R.id.title);
				holder.info = (TextView)convertView.findViewById(R.id.info);
				holder.viewBtn = (ImageButton)convertView.findViewById(R.id.view_btn);
				//让按钮失去焦点，让ListView能够获得点击响应
				holder.viewBtn.setFocusableInTouchMode(false);                            
				holder.viewBtn.setFocusable(false);
				convertView.setTag(holder);
				
				
				
				
				
				
			}else {
				
				holder = (ViewHolder)convertView.getTag();
			}
			
			
			final View eachView = convertView;
			
			 ImageLoader imageLoader = ImageLoader.getInstance();
	         // 第一个参数是uri,第二个参数是显示图片的imageView，第三个参数是刚刚构造的图片显示选项，第四个参数是加载的回调方法，displayImage有很多重载方法这中介其中一种；
	         imageLoader.displayImage((String)mData.get(position).get("img"), holder.img, options, animateFirstListener);
			
			holder.title.setText((String)mData.get(position).get("title"));
			holder.info.setText((String)mData.get(position).get("info"));
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showInfo((String)mData.get(temp_position).get("title"),((TextView)eachView.findViewById(R.id.info)).getText().toString(),(TextView)eachView.findViewById(R.id.info),temp_position);
				}
			});
			
			return convertView;
		}
		
		
		
		
	}
	
	/**图片加载监听事件**/
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
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