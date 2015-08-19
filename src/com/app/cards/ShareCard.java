package com.app.cards;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a.a.a.a.a;
import com.app.activity.DisplayPhotoActivity;
import com.app.activity.OtherPeopleActivity;
import com.app.activity.Personal_pageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseUi;
import edu.nju.shalbum.base.C;
import edu.nju.shalbum.model.Album;
import edu.nju.shalbum.model.Zan;

public class ShareCard extends Card {

	protected ImageView iv_portrait;
	protected TextView tv_name;
	protected TextView tvZanCount;
	protected Album album;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	int photoPosition;

	final UMSocialService mController = UMServiceFactory.getUMSocialService(
			"com.umeng.share", RequestType.SOCIAL);

	// public ShareCard(Context context) {
	// this(context, R.layout.sharecard_inner_content);
	// }
	//
	// public ShareCard(Context context, int innerLayout) {
	// super(context, innerLayout);
	// init();
	// }

	public ShareCard(Context context, Album album) {
		this(context, R.layout.sharecard_inner_content, album);
	}

	public ShareCard(Context context, int innerLayout, Album album) {
		super(context, innerLayout);
		this.album = album;
		init_card_elements();

	}

	public void init_card_elements() {

		if (album != null) {
			// Add header
			ShareCardHeader header = new ShareCardHeader(getContext(),
					R.layout.sharecard_inner_header);
			addCardHeader(header);
		}

	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_logo) // 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.ic_empty) // image连接地址为空时
				.showImageOnFail(R.drawable.ic_error) // image加载失败displayedImages
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.displayer(new SimpleBitmapDisplayer()) // 设置用户加载图片task(这里是正常图片显示)
				.build();

		// 设置主页面标题
		TextView tvContentTitle = (TextView) view
				.findViewById(R.id.sharecard_content_title);
		tvContentTitle.setText(album.getName());

		int photoCount = album.getPhoto_list().size();
		ImageView[] ivPhotos = new ImageView[photoCount];
		LinearLayout llImageAreaLine1 = (LinearLayout) view
				.findViewById(R.id.sharecard_image_area_line1);
		LinearLayout llImageAreaLine2 = (LinearLayout) view
				.findViewById(R.id.sharecard_image_area_line2);

		for (int i = 0; i < photoCount; i++) {
			ivPhotos[i] = new ImageView(getContext());
			String photoUrl = album.getPhoto_list().get(i).getContent();
			imageLoader.displayImage(photoUrl, ivPhotos[i], options);
			photoPosition = i;
			// 设置图片onClickListener
			ivPhotos[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// 设置DisplayPhotoActivity中的PhotoList用以显示

					DisplayPhotoActivity.photoList = album.getPhoto_list();

					Intent intent = new Intent(getContext(),
							DisplayPhotoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("photo_position", photoPosition);
					intent.putExtras(bundle);
					v.getContext().startActivity(intent);

				}
			});

		}

		if (photoCount == 1) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(450,
					450);
			ivPhotos[0].setScaleType(ScaleType.CENTER_INSIDE);
			ivPhotos[0].setLayoutParams(lp);
			llImageAreaLine1.addView(ivPhotos[0]);
		} else if (2 <= photoCount && photoCount <= 3) {
			for (ImageView photo : ivPhotos) {
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						150, 150);
				lp.setMargins(3, 3, 3, 3);
				photo.setLayoutParams(lp);
				photo.setScaleType(ScaleType.CENTER);
				llImageAreaLine1.addView(photo);
			}
		} else if (photoCount >= 4) {

			for (int i = 0; i < 4; i++) {
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						150, 150);
				lp.setMargins(3, 3, 3, 3);
				ivPhotos[i].setLayoutParams(lp);
				ivPhotos[i].setScaleType(ScaleType.CENTER);

			}

			llImageAreaLine1.addView(ivPhotos[0]);
			llImageAreaLine1.addView(ivPhotos[1]);
			llImageAreaLine2.addView(ivPhotos[2]);
			llImageAreaLine2.addView(ivPhotos[3]);

		}

		// 设置发布时间
		TextView tvPublishTime = (TextView) view
				.findViewById(R.id.tv_published_time);
		tvPublishTime.setText(album.getUptime());

		// 设置赞数量
		tvZanCount = (TextView) view.findViewById(R.id.text_zan_count);
		tvZanCount.setText(album.getZancount());

		// 设置底部图标监听

		ImageView ivThumbup = (ImageView) view.findViewById(R.id.icon_thumbup);

		// 已经被赞的情况，设置被赞图标
		if (album.isZan()) {
			ivThumbup.setImageResource(R.drawable.icon_fullthumbup);
		}

		// 设置赞图标监听
		ivThumbup.setOnClickListener(new OnClickListener() {

			boolean isZan = album.isZan();
			int zanCount = Integer.parseInt(album.getZancount());

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isZan == true) {
					if (v instanceof ImageView) {
						ImageView ivThumbup = (ImageView) v;
						ivThumbup.setImageResource(R.drawable.icon_thumbup);
						zanCount--;
						tvZanCount.setText(String.valueOf(zanCount));

						isZan = false;

						HashMap<String, String> zanParams = new HashMap<String, String>();
						zanParams.put("pageid", 0 + "");
						zanParams.put("albumid", album.getAlbumid());
						zanParams.put("album_userid", album.getUser()
								.getUserid());

						BaseUi fatherActivity = (BaseUi) v.getContext();
						fatherActivity.doTaskAsync(C.task.zanCancel,
								C.api.zanCancle, zanParams);

					}
				} else {
					if (v instanceof ImageView) {
						ImageView ivThumbup = (ImageView) v;
						ivThumbup.setImageResource(R.drawable.icon_fullthumbup);
						zanCount++;
						tvZanCount.setText(String.valueOf(zanCount));

						isZan = true;

						HashMap<String, String> zanParams = new HashMap<String, String>();
						zanParams.put("pageid", 0 + "");
						zanParams.put("albumid", album.getAlbumid());
						zanParams.put("album_userid", album.getUser()
								.getUserid());

						BaseUi fatherActivity = (BaseUi) v.getContext();
						fatherActivity.doTaskAsync(C.task.zan, C.api.zan,
								zanParams);

					}
				}

			}
		});

		// 设置分享图标监听
		ImageView ivShare = (ImageView) view.findViewById(R.id.icon_share);
		ivShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
						SHARE_MEDIA.DOUBAN);
				// 是否只有已登录用户才能打开分享选择页
				mController.openShare((Activity) v.getContext(), false);
			}
		});

		// 设置收藏图标监听
		ImageView ivStar = (ImageView) view.findViewById(R.id.icon_star);

		if (album.isStore()) {
			ivStar.setImageResource(R.drawable.icon_star_chosen);
		}

		ivStar.setOnClickListener(new OnClickListener() {

			boolean isStored = album.isStore();

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isStored == true) {
					if (v instanceof ImageView) {
						ImageView ivThumbup = (ImageView) v;
						ivThumbup.setImageResource(R.drawable.icon_star);
						isStored = false;

						HashMap<String, String> storeParams = new HashMap<String, String>();
						storeParams.put("pageid", 0 + "");
						storeParams.put("albumid", album.getAlbumid());
						storeParams.put("album_userid", album.getUser()
								.getUserid());

						BaseUi fatherActivity = (BaseUi) v.getContext();
						fatherActivity.doTaskAsync(C.task.storeCancel,
								C.api.storeCancel, storeParams);

					}
				} else {
					if (v instanceof ImageView) {
						ImageView ivThumbup = (ImageView) v;
						ivThumbup.setImageResource(R.drawable.icon_star_chosen);
						isStored = true;

						HashMap<String, String> storeParams = new HashMap<String, String>();
						storeParams.put("pageid", 0 + "");
						storeParams.put("albumid", album.getAlbumid());
						storeParams.put("album_userid", album.getUser()
								.getUserid());

						BaseUi fatherActivity = (BaseUi) v.getContext();
						fatherActivity.doTaskAsync(C.task.storeAlbum,
								C.api.storeAlbum, storeParams);
					}
				}
			}
		});

	}

	class ShareCardHeader extends CardHeader {

		public ShareCardHeader(Context context, int innerLayout) {
			super(context, innerLayout);
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View view) {

			// 创建获取用户头像的ImageLoader

			ImageView ivUserImage = (ImageView) view
					.findViewById(R.id.iv_user_image);
			String userImageUrl = album.getUser().getFace();
			imageLoader.displayImage(userImageUrl, ivUserImage);

			TextView tvName = (TextView) view.findViewById(R.id.tv_nickname);
			tvName.setText(album.getUser().getUsername());

			ivUserImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					// 设置用户头像的onClickListener,点击用户头像跳转到个人主页
					Intent intent = new Intent(getContext(),
							OtherPeopleActivity.class);
					//绑定数据   
					intent.putExtra("image",album.getUser().getFace());   
					intent.putExtra("name",album.getUser().getUsername());
					intent.putExtra("userid",album.getUser().getUserid());  
					intent.putExtra("userPhotoCount",album.getUser().getAlbumcount());  
					intent.putExtra("userFollowCount",album.getUser().getFollowcount());  
					intent.putExtra("userFansCount",album.getUser().getFanscount());  
					intent.putExtra("usersign",album.getUser().getSign());
					view.getContext().startActivity(intent);

				}
			});

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
