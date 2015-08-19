package edu.nju.shalbum.base;

import java.util.HashMap;

import edu.nju.shalbum.util.AppUtil;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

public class BaseFragment extends Fragment{

	protected BaseFragmentHandler handler;
	protected BaseTaskPool taskPool;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// debug memory
		debugMemory("onCreate");
		// async task handler
		this.handler = new BaseFragmentHandler(this);
		// init task pool
		this.taskPool = new BaseTaskPool((BaseUi)getActivity());
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
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// util method
	
	public void toast (String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}
	public BaseFragmentHandler getHandler () {
		return this.handler;
	}
	
	public void setHandler (BaseFragmentHandler handler) {
		this.handler = handler;
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
	public void debugMemory (String tag) {
			Log.w(this.getClass().getSimpleName(), tag+":"+AppUtil.getUsedMemory());
	}
}
