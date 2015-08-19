package edu.nju.shalbum.base;


import edu.nju.shalbum.util.AppUtil;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
/**
 * 自定义BaseHandler，用来处理message，并且回调ui变量的onTaskComplete、onNetworkError等方法。
 * @author wlz
 *
 */
public class BaseFragmentHandler extends Handler {
	
	protected BaseFragment ui;
	
	public BaseFragmentHandler (BaseFragment ui) {
		this.ui = ui;
	}
	
	public BaseFragmentHandler (Looper looper) {
		super(looper);
	}
	
	@Override
	public void handleMessage(Message msg) {
		try {
			int taskId;
			String result;
			switch (msg.what) {
				case BaseTask.TASK_COMPLETE:
					taskId = msg.getData().getInt("task");
					result = msg.getData().getString("data");
					if (result != null) {
						ui.onTaskComplete(taskId, AppUtil.getMessage(result));
					} else if (!AppUtil.isEmptyInt(taskId)) {
						ui.onTaskComplete(taskId);
					} else {
						ui.toast(C.err.message);
					}
					break;
				case BaseTask.NETWORK_ERROR:
					taskId = msg.getData().getInt("task");
					ui.onNetworkError(taskId);
					break;
				case BaseTask.SHOW_LOADBAR:
//					ui.showLoadBar();
					break;
				case BaseTask.HIDE_LOADBAR:
					break;
				case BaseTask.SHOW_TOAST:
					result = msg.getData().getString("data");
					ui.toast(result);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ui.toast(e.getMessage());
		}
	}
	
}