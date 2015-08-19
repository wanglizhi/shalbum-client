package edu.nju.shalbum.model;
/**
 * 照片类
 * @author wlz
 */
import edu.nju.shalbum.base.*;

public class Photo extends BaseModel{

	public static final String COL_POTOID = "photoid";
	public static final String COL_ALBUMID = "albumid";
	public static final String COL_CONTENT = "content";
	public static final String COL_DESRCIBE = "describe";
	
	private String photoid;
	private String albumid;
	private String content;
	private String describe;
	
	public Photo(){}

	public String getPhotoid() {
		return photoid;
	}

	public void setPhotoid(String photoid) {
		this.photoid = photoid;
	}

	public String getAlbumid() {
		return albumid;
	}

	public void setAlbumid(String albumid) {
		this.albumid = albumid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}
