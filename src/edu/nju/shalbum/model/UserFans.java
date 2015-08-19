package edu.nju.shalbum.model;
/**
 * 关注列表，粉丝类
 * @author wlz
 */
import edu.nju.shalbum.base.BaseModel;


public class UserFans extends BaseModel{
	public static final String COL_USERID = "userid";
	public static final String COL_FANSID = "fansid";
	public static final String COL_USER_FANSID = "user_fansid";
	public static final String COL_UPTIME = "uptime";
	
	private String userid;
	private String fansid;
	private String user_fansid;
	private String uptime;
	
	public UserFans(){}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFansid() {
		return fansid;
	}

	public void setFansid(String fansid) {
		this.fansid = fansid;
	}

	public String getUser_fansid() {
		return user_fansid;
	}

	public void setUser_fansid(String user_fansid) {
		this.user_fansid = user_fansid;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	
	

}
