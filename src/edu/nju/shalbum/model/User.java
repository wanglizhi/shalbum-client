package edu.nju.shalbum.model;
/**
 * 用户类，存储用户信息
 * @author wlz
 */
import edu.nju.shalbum.base.BaseModel;

public class User extends BaseModel {
	
	// model columns
	public final static String COL_USERID = "userid";
	public final static String COL_SESSIONID = "sessionid";
	public final static String COL_USERNAME = "username";
	public final static String COL_PASSWORD = "password";
	public final static String COL_SIGN = "sign";
	public final static String COL_FACE = "face";
	public final static String COL_FACEURL = "faceurl";
	public final static String COL_ALBUMCOUNT = "albumcount";
	public final static String COL_FANSCOUNT = "fanscount";
	public final static String COL_UPTIME = "uptime";
	public final static String COL_FOLLOWCOUNT= "followcount";
	
	private String userid;
	private String sessionid;
	private String username;
	private String password;
	private String sign;//个性签名
	
	//是否登录
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	private String face;
	private String albumcount;
	private String fanscount;
	private String uptime;
	private String followcount;
	
	// default is no login
	private boolean isLogin = false;
	
	// single instance for login
	static private User user = null;
	
	static public User getInstance () {
		if (User.user == null) {
			User.user = new User();
		}
		return User.user;
	}
	
	public User () {}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

//	public String getFaceurl() {
//		return faceurl;
//	}
//
//	public void setFaceurl(String faceurl) {
//		this.faceurl = faceurl;
//	}

	public String getAlbumcount() {
		return albumcount;
	}

	public void setAlbumcount(String albumcount) {
		this.albumcount = albumcount;
	}

	public String getFanscount() {
		return fanscount;
	}

	public void setFanscount(String fanscount) {
		this.fanscount = fanscount;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getFollowcount() {
		return followcount;
	}

	public void setFollowcount(String followcount) {
		this.followcount = followcount;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		User.user = user;
	}
}