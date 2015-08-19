package edu.nju.shalbum.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import com.umeng.analytics.MobclickAgent;

import edu.nju.shalbum.R;
import edu.nju.shalbum.util.AppCache;
import edu.nju.shalbum.util.AppUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

/**
 * BaseUI，继承了Activity，所有应用中需要使用Activity的类均需要继承此类
 * @author wlz
 *
 */
public class BaseUi extends FragmentActivity {
	
	protected BaseApp app;
	protected BaseHandler handler;
	protected BaseTaskPool taskPool;
	protected boolean showLoadBar = false;
	protected boolean showDebugMsg = true;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// debug memory
		debugMemory("onCreate");
		// async task handler
		this.handler = new BaseHandler(this);
		// init task pool
		this.taskPool = new BaseTaskPool(this);
		// init application
		this.app = (BaseApp) this.getApplicationContext();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// debug memory
		debugMemory("onResume");
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// debug memory
		debugMemory("onPause");
		MobclickAgent.onPause(this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		// debug memory
		debugMemory("onStart");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		// debug memory
		debugMemory("onStop");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// util method
	
	public void toast (String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	public void overlay (Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setClass(this, classObj);
        startActivity(intent);
	}
	
	public void overlay (Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivity(intent);
	}
	
	public void forward (Class<?> classObj) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		this.finish();
	}
	
	public void forward (Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
		this.finish();
	}
	
	public Context getContext () {
		return this;
	}
	
	public BaseHandler getHandler () {
		return this.handler;
	}
	
	public void setHandler (BaseHandler handler) {
		this.handler = handler;
	}
	
	public LayoutInflater getLayout () {
		return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getLayout (int layoutId) {
		return getLayout().inflate(layoutId, null);
	}
	
	public View getLayout (int layoutId, int itemId) {
		return getLayout(layoutId).findViewById(itemId);
	}
	
	public BaseTaskPool getTaskPool () {
		return this.taskPool;
	}
	
//	public void showLoadBar () {
//		this.findViewById(R.id.main_load_bar).setVisibility(View.VISIBLE);
//		this.findViewById(R.id.main_load_bar).bringToFront();
//		showLoadBar = true;
//	}
	
	public void hideLoadBar () {
		if (showLoadBar) {
			this.findViewById(R.id.main_load_bar).setVisibility(View.GONE);
			showLoadBar = false;
		}
	}
	//此处先将对话框的功能注释掉==================================
//	public void openDialog(Bundle params) {
//		new BaseDialog(this, params).show();
//	}
	
	public void loadImage (final String url) {
		taskPool.addTask(0, new BaseTask(){
			@Override
			public void onComplete(){
				AppCache.getCachedImage(getContext(), url);
				sendMessage(BaseTask.LOAD_IMAGE);
			}
		}, 0);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// logic method
	
	public void doFinish () {
		this.finish();
	}
	
	public void doLogout () {
		BaseAuth.setLogin(false);
	}
	
	public void doEditText () {
		Intent intent = new Intent();
		intent.setAction(C.intent.action.EDITTEXT);
		this.startActivity(intent);
	}
	
	public void doEditText (Bundle data) {
		Intent intent = new Intent();
		intent.setAction(C.intent.action.EDITTEXT);
		intent.putExtras(data);
		this.startActivity(intent);
	}
	
	public void doEditBlog () {
		Intent intent = new Intent();
		intent.setAction(C.intent.action.EDITBLOG);
		this.startActivity(intent);
	}
	
	public void doEditBlog (Bundle data) {
		Intent intent = new Intent();
		intent.setAction(C.intent.action.EDITBLOG);
		intent.putExtras(data);
		this.startActivity(intent);
	}
	
	public void sendMessage (int what) {
		Message m = new Message();
		m.what = what;
		handler.sendMessage(m);
	}
	
	public void sendMessage (int what, String data) {
		Bundle b = new Bundle();
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}
	
	public void sendMessage (int what, int taskId, String data) {
		Bundle b = new Bundle();
		b.putInt("task", taskId);
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}
	
	public void doTaskAsync (int taskId, int delayTime) {
		taskPool.addTask(taskId, new BaseTask(){
			@Override
			public void onComplete () {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), null);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, delayTime);
	}
	
	public void doTaskAsync (int taskId, BaseTask baseTask, int delayTime) {
		taskPool.addTask(taskId, baseTask, delayTime);
	}
	
	public void doTaskAsync (int taskId, String taskUrl) {
//		showLoadBar();
		taskPool.addTask(taskId, taskUrl, new BaseTask(){
			@Override
			public void onComplete (String httpResult) {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, 0);
	}
	public void doUploadImage(final int taskId,final String taskUrl,final String imagePath,final String newName){
		new Thread(){
			@Override
			public void run(){
				String actionUrl = C.api.base+taskUrl;
				Log.v("logeee", actionUrl);
				String uploadFile = imagePath;
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "*****";
				URL url;
				try {
					url = new URL(actionUrl);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					/* 允许Input、Output，不使用Cache */
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setUseCaches(false);
					/* 设置传送的method=POST */
					con.setRequestMethod("POST");
					/* setRequestProperty */
					con.setRequestProperty("Connection", "Keep-Alive");
					con.setRequestProperty("Charset", "UTF-8");
					con.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					/* 设置DataOutputStream */
					DataOutputStream ds = new DataOutputStream(con.getOutputStream());
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; "
							+ "name=\"userfile\";filename=\"" + newName + "\"" + end);
					ds.writeBytes(end);
					/* 取得文件的FileInputStream */
					Bitmap image = compressImageFromFile(uploadFile);
					InputStream inputStream = Bitmap2InputStream(image, 80);
					/* 设置每次写入1024bytes */
					int bufferSize = 1024;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					/* 从文件读取数据至缓冲区 */
					while ((length = inputStream.read(buffer)) != -1) {
						/* 将资料写入DataOutputStream中 */
						ds.write(buffer, 0, length);
					}
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					/* close streams */
					inputStream.close();
					ds.flush();
					/* 取得Response内容 */
					InputStream is = con.getInputStream();
					int ch;
					StringBuffer b = new StringBuffer();
					while ((ch = is.read()) != -1) {
						b.append((char) ch);
					}
					/* 将Response显示于Dialog */
					Log.v("logeee", ("上传成功" + b.toString().trim()));
					/* 关闭DataOutputStream */
					ds.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}.start();
	}
	//压缩文件
	private Bitmap compressImageFromFile(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        newOpts.inJustDecodeBounds = true;//只读边,不读内容  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
  
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        float hh = 600f;//  
        float ww = 600f;//  
        int be = 1;  
        if (w > h && w > ww) {  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置采样率  
          
        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设  
        newOpts.inPurgeable = true;// 同时设置才会有效  
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收  
          
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩  
                                    //其实是无效的,大家尽管尝试  
        return bitmap;  
    }
	//将Bitmap转换成InputStream  
    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    }  
	public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
//		showLoadBar();
		taskPool.addTask(taskId, taskUrl, taskArgs, new BaseTask(){
			@Override
			public void onComplete (String httpResult) {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, 0);
	}
	
	public void onTaskComplete (int taskId, BaseMessage message) {
		
	}
	
	public void onTaskComplete (int taskId) {
		
	}
	
	public void onNetworkError (int taskId) {
		toast(C.err.network);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// debug method
	
	public void debugMemory (String tag) {
		if (this.showDebugMsg) {
			Log.w(this.getClass().getSimpleName(), tag+":"+AppUtil.getUsedMemory());
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// common classes
	
	public class BitmapViewBinder implements ViewBinder {
		// 
		@Override
		public boolean setViewValue(View view, Object data, String textRepresentation) {
			if ((view instanceof ImageView) & (data instanceof Bitmap)) {
				ImageView iv = (ImageView) view;
				Bitmap bm = (Bitmap) data;
				iv.setImageBitmap(bm);
				return true;
			}
			return false;
		}
	}
}