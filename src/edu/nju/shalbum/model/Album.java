package edu.nju.shalbum.model;
import java.util.ArrayList;

/**
 * 相册类，Model中的类均作为VO，例如Album类中可能需要添加Face属性，用来直接显示用户头像
 * @author wlz
 */
import edu.nju.shalbum.base.BaseModel;

public class Album extends BaseModel {
	
	// model columns
	public final static String COL_ALBUMID = "albumid";
	public final static String COL_USERID = "userid";
	public final static String COL_NAME = "name";
	public final static String COL_COMMENTCOUNT = "commentcount";
	public final static String COL_UPTIME = "uptime";
	
	private String albumid;
	private String userid;
	private String name;
	private String commentcount;
	private String uptime;
	private String tag;
	private String zancount;
	private boolean zan;
	private boolean store;
	public boolean isStore() {
		return store;
	}


	public void setStore(boolean store) {
		this.store = store;
	}


	public boolean isZan() {
		return zan;
	}


	public void setZan(boolean zan) {
		this.zan = zan;
	}


	public String getZancount() {
		return zancount;
	}


	public void setZancount(String zancount) {
		this.zancount = zancount;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	private ArrayList<Photo> photo_list;
	
	private User user;
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Album () {}


	public ArrayList<Photo> getPhoto_list() {
		return photo_list;
	}


	public void setPhoto_list(ArrayList<Photo> photo_list) {
		this.photo_list = photo_list;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCommentcount() {
		return commentcount;
	}


	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}


	public String getUptime() {
		return uptime;
	}


	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
}