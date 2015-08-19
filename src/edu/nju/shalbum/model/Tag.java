package edu.nju.shalbum.model;
/**
 * 相册标签类
 * @author wlz
 */
import edu.nju.shalbum.base.BaseModel;

public class Tag extends BaseModel{
	public static final String COL_TAGID = "tagid";
	public static final String COL_CONTENT = "content";

	private String tagid;
	private String content;
	
	public Tag(){}

	public String getTagid() {
		return tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
