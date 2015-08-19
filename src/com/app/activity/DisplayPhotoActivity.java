package com.app.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import edu.nju.shalbum.R;
import edu.nju.shalbum.model.Photo;

public class DisplayPhotoActivity extends Activity implements
		OnPageChangeListener {

	public static ArrayList<Photo> photoList;
	private ViewPager viewPager;
	private ImageView[] ivPhotos;
	private TextView tvDescribe;

	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 全屏设置，隐藏所有窗口、装饰
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_displayphoto);

		// 设置图片显示选项
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_logo) // 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.ic_empty) // image连接地址为空时
				.showImageOnFail(R.drawable.ic_error) // image加载失败displayedImages
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.displayer(new SimpleBitmapDisplayer()) // 设置用户加载图片task(这里是正常图片显示)
				.build();

		viewPager = (ViewPager) findViewById(R.id.view_pager);
		tvDescribe = (TextView) findViewById(R.id.photo_description);
		

		ivPhotos = new ImageView[photoList.size()];

		for (int i = 0; i < ivPhotos.length; i++) {
			ImageView imageView = new ImageView(this);
			ivPhotos[i] = imageView;
			String photoUrl = photoList.get(i).getContent();
			imageLoader.displayImage(photoUrl, ivPhotos[i], options);
		}

		viewPager.setAdapter(new PhotoAdapter());
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(0);

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub

		tvDescribe.setText(photoList.get(position).getDescribe());

	}

	class PhotoAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return photoList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {

		}

		@Override
		public Object instantiateItem(View container, int position) {
			try {
				((ViewPager) container).addView(ivPhotos[position], 0);
				TextView tvPhotoDescription = (TextView) findViewById(R.id.photo_description);
				tvPhotoDescription.setText(photoList.get(0)
						.getDescribe());
				tvPhotoDescription.setOnClickListener(new OnClickListener() {

					boolean flag;

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) v;

						if (flag) {
							flag = false;
							tv.setEllipsize(null);
							tv.setMaxLines(100);
						} else {
							flag = true;
							tv.setEllipsize(TextUtils.TruncateAt.END);
							tv.setMaxLines(2);
						}
					}
				});

			} catch (Exception e) {
				// handler something
			}
			return ivPhotos[position % ivPhotos.length];
		}

	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500); // 设置image隐藏动画500ms
					displayedImages.add(imageUri); // 将图片uri添加到集合中
				}
			}
		}
	}

}
