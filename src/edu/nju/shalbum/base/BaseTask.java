package edu.nju.shalbum.base;
/**
 * BaseTask类，有id和name两个属性，并且设置了5个回调方法。
 * @author wlz
 *
 */
public class BaseTask {

	public static final int TASK_COMPLETE = 0;//任务完成
	public static final int NETWORK_ERROR = 1;//网络错误
	public static final int SHOW_LOADBAR = 2;//显示进度条
	public static final int HIDE_LOADBAR = 3;//隐藏进度条
	public static final int SHOW_TOAST = 4;//显示toast
	public static final int LOAD_IMAGE = 5;//加载图片
	
	private int id = 0;
	private String name = "";
	
	public BaseTask() {}
	
	public int getId () {
		return this.id;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void onStart () {
//		Log.w("BaseTask", "onStart");
	}
	
	public void onComplete () {
//		Log.w("BaseTask", "onComplete");
	}
	
	public void onComplete (String httpResult) {
//		Log.w("BaseTask", "onComplete");
	}
	
	public void onError (String error) {
//		Log.w("BaseTask", "onError");
	}
	
	public void onStop () throws Exception {
//		Log.w("BaseTask", "onStop");
	}
	
}