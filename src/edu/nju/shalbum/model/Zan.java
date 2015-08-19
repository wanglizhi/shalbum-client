package edu.nju.shalbum.model;
/**
 * 赞表
 * @author wlz
 */
import edu.nju.shalbum.base.BaseModel;

public class Zan extends BaseModel{

	public static final String COL_ZANID = "zanid";
	public static final String COL_ALBUMID = "albumid";
	public static final String COL_USERID = "userid";
	
	private String zanid;
	private String albumid;
	private String userid;
	
	public Zan(){}

	public String getZanid() {
		return zanid;
	}

	public void setZanid(String zanid) {
		this.zanid = zanid;
	}

	public String getAlbumid() {
		return albumid;
	}

	public void setAlbumid(String albumid) {
		this.albumid = albumid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
