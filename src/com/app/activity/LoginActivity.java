package com.app.activity;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import shared.ui.actionscontentview.ActionsContentView;

import com.app.qin.CallbackImplements;
import com.app.weibo.AccessTokenKeeper;
import com.app.weibo.Constants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.WeiboAuth.AuthInfo;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.widget.LoginButton;
import com.sina.weibo.sdk.widget.LoginoutButton;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseUi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity  extends BaseUi {
	private static final String TAG = LoginActivity.class.getName();
	private final int SPLASH_DISPLAY_LENGHT = 1000; // 延迟2秒  
	private boolean isLogoutClick = false;
	/** UI元素列表 */
    private TextView mTokenView;
    private LoginoutButton mLoginoutBtnSilver;
    
    /** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;
    /** 用户信息接口 */
    private UsersAPI mUsersAPI;
    /** 用户对象 */
    private static User user;
    
    /** 登陆认证对应的listener */
    private AuthListener mLoginListener = new AuthListener();
    /** 登出操作对应的listener */
    private LogOutRequestListener mLogoutListener = new LogOutRequestListener();

    private Oauth2AccessToken token;
    /**
     * 该按钮用于记录当前点击的是哪一个 Button，用于在 {@link #onActivityResult}
     * 函数中进行区分。通常情况下，我们的应用中只需要一个合适的 {@link LoginButton} 
     * 或者 {@link LoginoutButton} 即可。
     */
    private Button mCurrentClickedButton;
    //加载过渡动画
    private LinearLayout loadingLay;
    
    /**
     * @see {@link Activity#onCreate}
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_logout);
        
        mTokenView = (TextView) findViewById(R.id.result);
        //加载过渡动画
        loadingLay = (LinearLayout)findViewById(R.id.loadingLay);
        loadingLay.setVisibility(View.INVISIBLE);
        // 创建授权认证信息
        AuthInfo authInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // 登陆按钮（样式二：银灰色）
        mLoginoutBtnSilver = (LoginoutButton) findViewById(R.id.login_out_button_silver);
        mLoginoutBtnSilver.setWeiboAuthInfo(authInfo, mLoginListener);
        mLoginoutBtnSilver.setLogoutListener(mLogoutListener);
        
        /**
         * 请注意：为每个 Button 设置一个额外的 Listener 只是为了记录当前点击的
         * 是哪一个 Button，用于在 {@link #onActivityResult} 函数中进行区分。
         * 通常情况下，我们的应用不需要调用该函数。
         */
        mLoginoutBtnSilver.setExternalOnClickListener(mButtonClickListener);
        
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken != null && mAccessToken.isSessionValid()) {
        	loadingLay.setVisibility(View.VISIBLE);
        	// 由于 LoginLogouButton 并不保存 Token 信息，因此，如果您想在初次
            // 进入该界面时就想让该按钮显示"注销"，请放开以下代码
            token = AccessTokenKeeper.readAccessToken(this);
            mLoginoutBtnSilver.setLogoutInfo(token, mLogoutListener);
        	// 获取当前已保存过的 Token
            // 获取用户信息接口
            mUsersAPI = new UsersAPI(mAccessToken);
            long uid = Long.parseLong(mAccessToken.getUid());
            mUsersAPI.show(uid, mListener);
            
    	}else{
    		
    	}
        
        
        
        
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     * 
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (mCurrentClickedButton != null) {
            if (mCurrentClickedButton instanceof LoginButton) {
                ((LoginButton)mCurrentClickedButton).onActivityResult(requestCode, resultCode, data);
            } else if (mCurrentClickedButton instanceof LoginoutButton) {
                ((LoginoutButton)mCurrentClickedButton).onActivityResult(requestCode, resultCode, data);
            }
        }
        
        
    }
    
    /**
     * 请注意：为每个 Button 设置一个额外的 Listener 只是为了记录当前点击的
     * 是哪一个 Button，用于在 {@link #onActivityResult} 函数中进行区分。
     * 通常情况下，我们的应用不需要定义该 Listener。
     */
    private OnClickListener mButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	isLogoutClick = true;
            if (v instanceof Button) {
                mCurrentClickedButton = (Button)v;
            }
        }
    };
    
    /**
     * 微博 OpenAPI 回调接口。
     */
    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                LogUtil.i(TAG, response);
                // 调用 User#parse 将JSON串解析成User对象
                user = User.parse(response);
                if (user != null) {
                    //延迟进入主界面
                    new Handler().postDelayed(new Runnable() { 
                    	public void run() {  
            				if(!isLogoutClick){
            					loadingLay.setVisibility(View.GONE);
			                    Intent mainIntent = new Intent(LoginActivity.this,  
										MainActivity.class);  
								mainIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								LoginActivity.this.startActivity(mainIntent);  
								LoginActivity.this.finish(); 
							}
                    	}
                    },SPLASH_DISPLAY_LENGHT);
            		             
                } else {
                    Toast.makeText(LoginActivity.this, "用户信息获取失败，请重新登录", Toast.LENGTH_LONG).show();
                    
                }
            }
            
            
        }

		@Override
		public void onWeiboException(WeiboException e) {
			// TODO Auto-generated method stub
			LogUtil.e(TAG, e.getMessage());
	        ErrorInfo info = ErrorInfo.parse(e.getMessage());
	        Toast.makeText(LoginActivity.this, "网络出现异常哦亲~", Toast.LENGTH_LONG).show();
	        System.out.println("微博登录发生错误:" + info.error);
		}
    };
    

    /**
     * 登入按钮的监听器，接收授权结果。
     */
    private class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
            if (accessToken != null && accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                        new java.util.Date(accessToken.getExpiresTime()));
                String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
                mTokenView.setText(String.format(format, accessToken.getToken(), date));

                AccessTokenKeeper.writeAccessToken(getApplicationContext(), accessToken);
                
                mUsersAPI = new UsersAPI(accessToken);
                long uid = Long.parseLong(accessToken.getUid());
                mUsersAPI.show(uid, mListener);
                
                loadingLay.setVisibility(View.VISIBLE);
                isLogoutClick = false;
               
            }
       
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, 
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登出按钮的监听器，接收登出处理结果。（API 请求结果的监听器）
     */
    private class LogOutRequestListener implements RequestListener {
        @Override
        public void onComplete(String response) {
        	//点击了‘注销’按钮
            isLogoutClick = true;
            loadingLay.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(response)) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String value = obj.getString("result");
                    //这里可能会出现注销失败的情况
                    if ("true".equalsIgnoreCase(value)) {
                        AccessTokenKeeper.clear(LoginActivity.this);
                    }
                    Toast.makeText(LoginActivity.this, 
                            "注销成功", Toast.LENGTH_SHORT).show();
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }     

		@Override
		public void onWeiboException(WeiboException e) {
			mTokenView.setText(R.string.weibosdk_demo_logout_failed);
		}
    }
    
    public static User getUser(){
    	return user;
    }
}

