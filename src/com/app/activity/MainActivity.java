package com.app.activity;

import it.gmariotti.cardslib.library.view.CardView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import shared.ui.actionscontentview.ActionsContentView;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fragment.ShareCardFragment;
import com.app.helper.CircularImage;
import com.app.http.HttpUtil;
import com.app.qin.CallbackImplements;
import com.app.qin.CameraFragement;
import com.app.qin.HomeFragment;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseAuth;
import edu.nju.shalbum.base.BaseMessage;
import edu.nju.shalbum.base.BaseUi;
import edu.nju.shalbum.base.C;
import edu.nju.shalbum.model.Album;
import edu.nju.shalbum.model.Photo;

import com.app.qin.SyncImageLoader;
import com.app.weibo.AccessTokenKeeper;
import com.example.localphotodemo.SelectPhotoActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;

public class MainActivity extends BaseUi {
	private static final String TAG = MainActivity.class.getName();
	/** 头像 */
	private ImageView head_image;
	/** 收藏 */
	private ImageButton favorite_image;
	/** 用户名 */
	private TextView name;
	/** 下载图片 */
	private SyncImageLoader syncImageLoader = new SyncImageLoader();
	/** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;
    /** 用户信息接口 */
    private UsersAPI mUsersAPI;
    /** 头像地址 */
    private String sinaurl;
    private ActionsContentView viewActionsContentView;
    private User muser = LoginActivity.getUser();
    private CircularImage userinfo;
    private ImageButton message,share,collect;
    
 // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",
                                                                                 RequestType.SOCIAL);
  @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.activity_main);
    viewActionsContentView = (ActionsContentView) findViewById(R.id.actionsContentView);
    
    /**初始化图片加载类配置信息**/
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
	.threadPriority(Thread.NORM_PRIORITY - 2)//加载图片的线程数
	.denyCacheImageMultipleSizesInMemory() //解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
	.discCacheFileNameGenerator(new Md5FileNameGenerator())//设置磁盘缓存文件名称
	.tasksProcessingOrder(QueueProcessingType.LIFO)//设置加载显示图片队列进程
	.writeDebugLogs() // Remove for release app
	.build();
    // Initialize ImageLoader with configuration.
    ImageLoader.getInstance().init(config);
    
    userinfo = (CircularImage) findViewById(R.id.cover_user_photo);
    userinfo.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(MainActivity.this,Personal_pageActivity.class));
			//前进切换动画
			overridePendingTransition(R.anim.forward_slide_in_left,  
                    R.anim.forward_slide_out_right); 
		}
    });
    message = (ImageButton) findViewById(R.id.message);
    message.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(MainActivity.this,MessageActivity.class));
			overridePendingTransition(R.anim.forward_slide_in_left,  
                    R.anim.forward_slide_out_right); 
		}
    	
    });
    share = (ImageButton) findViewById(R.id.share);
    share.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(MainActivity.this,ShareActivity.class));
			overridePendingTransition(R.anim.forward_slide_in_left,  
                    R.anim.forward_slide_out_right); 
		}
    	
    });
    collect = (ImageButton) findViewById(R.id.collect);
    collect.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(MainActivity.this,CollectActivity.class));
			overridePendingTransition(R.anim.forward_slide_in_left,  
                    R.anim.forward_slide_out_right); 
		}
    	
    });
    

    //主页
    final Fragment main;
    main = new HomeFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.content,main).commit();
    viewActionsContentView.showContent();
    
    //默认头像
	name = (TextView) findViewById(R.id.user);
	head_image = (CircularImage) findViewById(R.id.cover_user_photo);  
	head_image.setImageResource(R.drawable.head_default);  
	muser = LoginActivity.getUser();
	//初始化
	init();
	
	//启用友盟消息推送
	PushAgent mPushAgent = PushAgent.getInstance(getContext());
	mPushAgent.enable();
  }

  /**
   * 每次刷新都会调用
   */
	@Override
	public void onStart(){
		super.onStart();
		
		//得到所有的相册
		HashMap<String, String> albumParams = new HashMap<String, String>();
//		albumParams.put("typeId", "0");
//		albumParams.put("pageId", "0");
//		this.doTaskAsync(C.task.albumList, C.api.albumList, albumParams);
	}
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle presses on the action bar items
      switch (item.getItemId()) {
          case R.id.action_camera:
        	  Intent intent = new Intent(MainActivity.this,SelectPhotoActivity.class);
	          startActivity(intent);
	          overridePendingTransition(R.anim.forward_slide_in_left,  
	                    R.anim.forward_slide_out_right); 
              return true;
          case R.id.action_fb:
        	  FeedbackAgent agent = new FeedbackAgent(getContext());
        	  agent.startFeedbackActivity();
        	  return true;
          default:
              return super.onOptionsItemSelected(item);
      }
  }
  /**
   * 设置友盟的分享内容
   */
  private void setUMController(){
	// 设置分享内容
	  mController.setShareContent("记录你购物的一点一滴");
	  // 设置分享图片, 参数2为图片的url地址
	  mController.setShareMedia(new UMImage(this, 
	                                        "http://www.umeng.com/images/pic/banner_module_social.png"));
	  // 设置分享图片，参数2为本地图片的资源引用
	  //mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
	  // 设置分享图片，参数2为本地图片的路径(绝对路径)
	  //mController.setShareMedia(new UMImage(getActivity(), 
//	                                  BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

	  // 设置分享音乐
	  //UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
	  //uMusic.setAuthor("GuGu");
	  //uMusic.setTitle("天籁之音");
	  // 设置音乐缩略图
	  //uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
	  //mController.setShareMedia(uMusic);

	  // 设置分享视频
	  //UMVideo umVideo = new UMVideo(
//	            "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
	  // 设置视频缩略图
	  //umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
	  //umVideo.setTitle("友盟社会化分享!");
	  //mController.setShareMedia(umVideo);
  }
  @Override 
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      /**使用SSO授权必须添加如下代码 */
      UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
      if(ssoHandler != null){
         ssoHandler.authorizeCallBack(requestCode, resultCode, data);
      }
  }
  /**
   * 连接服务器，刷新界面--初始化。
   */
  private void  init(){
     
              if (muser != null) {
            	  sinaurl = muser.avatar_hd;
                  name.setText(muser.screen_name);      
                  //服务器用户登录验证
                  doTaskLogin();
                  
                File headImageFile = new File(SyncImageLoader.headImagePath);
                if(!headImageFile.exists()){
	                CallbackImplements callbackImplements = new CallbackImplements(head_image);
	          		Drawable cacheImage = syncImageLoader.loadDrawable(sinaurl,
	                          callbackImplements);
	          		System.out.println("头像地址"+sinaurl);
	          		// 如果图片缓存存在,则在外部设置image.否则是调用的callback函数设置
	          		if (cacheImage != null) {
	          			head_image.setImageDrawable(cacheImage);
	          			System.out.println("读取内存");
	          		}
                }else{
                	Bitmap bitmap = BitmapFactory.decodeFile(SyncImageLoader.headImagePath);
                	head_image.setImageBitmap(bitmap);
                	System.out.println("读取缓存");
                }
                headImageFile.exists();
          
          		new Thread(checkUserInfo).start();
          		             
              } else {
                  Toast.makeText(MainActivity.this, "用户信息获取失败", Toast.LENGTH_LONG).show();
                  
              }

      
  };
  
	//开启登录任务，连接服务器验证
	private void doTaskLogin() {
		app.setLong(System.currentTimeMillis());
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("username", muser.screen_name);
		urlParams.put("face", muser.avatar_hd);
		urlParams.put("sign", muser.description);
		try {
			this.doTaskAsync(C.task.login, C.api.login, urlParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case C.task.login:
				edu.nju.shalbum.model.User temp = null;
				// login logic
				try {
					temp = (edu.nju.shalbum.model.User) message.getResult("User");
					BaseAuth.setUser(temp);
					BaseAuth.setLogin(true);
//					//头像为空，则上传头像
//					if(temp.getFace().equals("")){
//						this.doUploadImage(C.task.uploadFace, C.api.uploadFace, user.avatar_hd,temp.getUserid());
//						this.toast(user.avatar_hd);
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// login complete
				long startTime = app.getLong();
				long loginTime = System.currentTimeMillis() - startTime;
				Log.w("LoginTime", Long.toString(loginTime));
				break;
			case C.task.albumList:
			try {
				ArrayList<Album> albumList=(ArrayList<Album>)message.getResultList("Album");
				for(Album a:albumList){
					
					Log.w("album", a.getAlbumid()+" "+a.getName()+" "+a.getUserid()+(a.getPhoto_list().toString()));
//					HashMap<String, String> photoParams = new HashMap<String, String>();
//					photoParams.put("albumid", a.getAlbumid());
//					this.doTaskAsync(C.task.photoList, C.api.photoList, photoParams);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
//			case C.task.photoList:
//				try {
//					ArrayList<Photo> photoList=(ArrayList<Photo>)message.getResultList("Photo");
//					for(Photo p:photoList){
//						Log.w("photo", p.getAlbumid()+" "+p.getPhotoid()+" "+p.getContent()+" "+p.getDescribe());
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				break;
		}
	}
	
  
  
  
  
  	 /** 
	   * 用户验证
	   */  
  	Runnable checkUserInfo = new Runnable(){  
	  
	@Override  
	public void run() {  
	    // TODO Auto-generated method stub  
		try {
			List <NameValuePair> params = new ArrayList <NameValuePair>();   
	        params.add(new BasicNameValuePair("id", muser.id));
	        params.add(new BasicNameValuePair("name", muser.screen_name));
	        params.add(new BasicNameValuePair("avatar", muser.avatar_large));
			HttpPost post = HttpUtil.getHttpPost(HttpUtil.BASE_URL);
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(post);
			String strResult = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
			Log.i("test", strResult);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}  
	  };

}
