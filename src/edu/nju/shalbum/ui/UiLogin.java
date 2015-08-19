//package edu.nju.shalbum.ui;
//
//
//import java.util.HashMap;
//
//import edu.nju.shalbum.R;
//import edu.nju.shalbum.base.BaseAuth;
//import edu.nju.shalbum.base.BaseMessage;
//import edu.nju.shalbum.base.BaseService;
//import edu.nju.shalbum.base.BaseUi;
//import edu.nju.shalbum.base.C;
//import edu.nju.shalbum.model.User;
//import edu.nju.shalbum.service.NoticeService;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//
///**
// * 用户登录界面，整个应用的总入口
// * @author wlz
// *
// */
//public class UiLogin extends BaseUi {
//
//	private EditText mEditName;
//	private EditText mEditPass;
//	private CheckBox mCheckBox;
//	private SharedPreferences settings;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		// 已经登录则切换至首页
////		if (BaseAuth.isLogin()) {
////			this.forward(UiIndex.class);
////		}
//		
//		// set view after check login
//		setContentView(R.layout.ui_login);
//		
//		// remember password
//		mEditName = (EditText) this.findViewById(R.id.app_login_edit_name);
//		mEditPass = (EditText) this.findViewById(R.id.app_login_edit_pass);
//		mCheckBox = (CheckBox) this.findViewById(R.id.app_login_check_remember);
//		settings = getPreferences(Context.MODE_PRIVATE);
//		if (settings.getBoolean("remember", false)) {
//			mCheckBox.setChecked(true);
//			mEditName.setText(settings.getString("username", ""));
//			mEditPass.setText(settings.getString("password", ""));
//		}
//		
//		// remember checkbox
//		mCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				SharedPreferences.Editor editor = settings.edit();
//				if (mCheckBox.isChecked()) {
//					editor.putBoolean("remember", true);
//					editor.putString("username", mEditName.getText().toString());
//					editor.putString("password", mEditPass.getText().toString());
//				} else {
//					editor.putBoolean("remember", false);
//					editor.putString("username", "");
//					editor.putString("password", "");
//				}
//				editor.commit();
//			}
//		});
//		
//		// 登录
//		OnClickListener mOnClickListener = new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				switch (v.getId()) {
//					case R.id.app_login_btn_submit : 
//						doTaskLogin();
//						break;
//				}
//			}
//		};
//		findViewById(R.id.app_login_btn_submit).setOnClickListener(mOnClickListener);
//	}
//	//开启登录任务，连接服务器验证
//	private void doTaskLogin() {
//		app.setLong(System.currentTimeMillis());
//		if (mEditName.length() > 0 && mEditPass.length() > 0) {
//			HashMap<String, String> urlParams = new HashMap<String, String>();
//			urlParams.put("username", mEditName.getText().toString());
//			urlParams.put("password", mEditPass.getText().toString());
//			try {
//				this.doTaskAsync(C.task.login, C.api.login, urlParams);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	////////////////////////////////////////////////////////////////////////////////////////////////
//	// async task callback methods
//	
//	@Override
//	public void onTaskComplete(int taskId, BaseMessage message) {
//		super.onTaskComplete(taskId, message);
//		switch (taskId) {
//			case C.task.login:
//				User user = null;
//				// login logic
//				try {
//					user = (User) message.getResult("User");
//					// login success
//					if (user.getUsername() != null) {
//						BaseAuth.setUser(user);
//						BaseAuth.setLogin(true);
//					// login fail
//					} else {
//						BaseAuth.setUser(user); // set sid
//						BaseAuth.setLogin(false);
//						toast(this.getString(R.string.msg_loginfail));
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					toast(e.getMessage());
//				}
//				// login complete
//				long startTime = app.getLong();
//				long loginTime = System.currentTimeMillis() - startTime;
//				Log.w("LoginTime", Long.toString(loginTime));
//				// turn to index
//				if (BaseAuth.isLogin()) {
//					// start service
//					BaseService.start(this, NoticeService.class);
//					// turn to index
//					forward(UiIndex.class);
//				}
//				break;
//		}
//	}
//	
//	@Override
//	public void onNetworkError (int taskId) {
//		super.onNetworkError(taskId);
//	}
//	
//	////////////////////////////////////////////////////////////////////////////////////////////////
//	// other methods
//	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			doFinish();
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//	
//}