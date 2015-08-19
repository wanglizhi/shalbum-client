package com.app.qin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
public class SyncImageLoader {
		public static String headImagePath = Environment.getExternalStorageDirectory().toString() + "/apps_images/head_image.jpg";
        private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
        // 根据URL从网络上下载图片,生成Drawable对象
        public Drawable loadImageFormUrl(String imageUrl) {
                try {
                        return Drawable.createFromStream(new URL(imageUrl).openStream(),
                                        "src");
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                }
        }
        public Drawable loadDrawable(final String imageUrl,
                        final ImageCallback imageCallback) {
        	
        	//异步线程下载图片到SD卡中
        	Thread thread = new Thread(new Runnable(){
        	    @Override
        	    public void run() {
		        	String path = null; 
		        	String strImgPath = Environment.getExternalStorageDirectory()
		                    .toString() + "/apps_images/";
		            boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);  
		            String packageName = "head_image.jpg";  
		            if(hasSDCard){  
		                path=strImgPath+packageName;  
		            }else{  
		                path="/data/data/"+packageName;       
		            }    
		            File temp = new File(strImgPath);
		            if (!temp.exists()) {
		            	temp.mkdirs();
		            }
		            File file = new File(strImgPath,packageName); 
		        	try {  
		        		 System.out.println("异步下载图片开始"+imageUrl);
		                InputStream in = new URL(imageUrl).openStream();  
		                Bitmap bitmap = BitmapFactory.decodeStream(in);  
		                FileOutputStream out = new FileOutputStream(file);  
		                bitmap.compress(Bitmap.CompressFormat.PNG,100, out);  
		                out.flush();  
		                out.close();  
		                in.close();  
		                System.out.println("异步下载图片结束");
		            } catch (MalformedURLException e) {  
		            	System.out.println("下载问题1："+e.toString()); 
		            } catch (IOException e) {  
		                e.printStackTrace(); 
		                System.out.println("下载问题1："+e.toString()); 
		                
		            } finally{  
		            	
		            }
        	    }
        	});
        	thread.start(); 
        	    
        		
                // 判断缓存中是否有imageUrl这个缓存存在
                if (imageCache.containsKey(imageUrl)) {
                        SoftReference<Drawable> softReference = imageCache.get(imageUrl);
                        if (softReference.get() != null) {
                                return softReference.get();
                        }
                }
                final Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                                imageCallback.imageLoaded((Drawable) msg.obj);
                        }
                };
                // 缓存中不存在的话,启动异步线程下载图片
                new Thread() {
                        @Override
                        public void run() {
                                // 将图片放入缓存
                                Drawable drawable = loadImageFormUrl(imageUrl);
                                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                                Message msg = handler.obtainMessage(0, drawable);
                                handler.sendMessage(msg);
                        }
                }.start();
                return null;
        }
        // 回调函数,图片加载完毕后调用该函数
        public interface ImageCallback {
                public void imageLoaded(Drawable imageDrawable);
        }
}
