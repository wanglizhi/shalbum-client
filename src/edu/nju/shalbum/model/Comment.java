package edu.nju.shalbum.model;
/**
 * 评论类
 * @author wlz
 */
import edu.nju.shalbum.base.BaseModel;

public class Comment extends BaseModel {
	
	// model columns
	public final static String COL_COMMENTID = "commentid";
	public final static String COL_ALBUMID = "albumid";
	public final static String COL_USERID = "userid";
	public final static String COL_CONTENT = "content";
	public final static String COL_UPTIME = "uptime";
	
	private String commentid;
	private String albumid;
	private String userid;
	private String content;
	private String uptime;
	
	public Comment () {}

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	
}