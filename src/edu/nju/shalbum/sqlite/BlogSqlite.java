package edu.nju.shalbum.sqlite;


import java.util.ArrayList;

import edu.nju.shalbum.base.BaseSqlite;
import edu.nju.shalbum.model.Album;
import android.content.ContentValues;
import android.content.Context;

/**
 * 在SQLite中缓存的相册列表
 * @author wlz
 *
 */
public class BlogSqlite extends BaseSqlite {

	public BlogSqlite(Context context) {
		super(context);
	}

	@Override
	protected String tableName() {
		return "blogs";
	}

	@Override
	protected String[] tableColumns() {
		String[] columns = {
			Album.COL_ALBUMID,
			Album.COL_COMMENTCOUNT,
			Album.COL_NAME,
			Album.COL_USERID,
			Album.COL_UPTIME
		};
		return columns;
	}

	@Override
	protected String createSql() {
		return "CREATE TABLE " + tableName() + " (" +
			Album.COL_ALBUMID + " INTEGER PRIMARY KEY, " +
			Album.COL_NAME + " TEXT, " +
			Album.COL_USERID + " TEXT, " +
			Album.COL_COMMENTCOUNT + " TEXT, " +
			Album.COL_UPTIME + " TEXT" +
			");";
	}

	@Override
	protected String upgradeSql() {
		return "DROP TABLE IF EXISTS " + tableName();
	}

	public boolean updateBlog (Album blog) {
//		// prepare blog data
//		ContentValues values = new ContentValues();
//		values.put(Album.COL_ID, blog.getId());
//		values.put(Album.COL_FACE, blog.getFace());
//		values.put(Album.COL_CONTENT, blog.getContent());
//		values.put(Album.COL_COMMENT, blog.getComment());
//		values.put(Album.COL_AUTHOR, blog.getAuthor());
//		values.put(Album.COL_UPTIME, blog.getUptime());
//		// prepare sql
//		String whereSql = Album.COL_ID + "=?";
//		String[] whereParams = new String[]{blog.getId()};
//		// create or update
//		try {
//			if (this.exists(whereSql, whereParams)) {
//				this.update(values, whereSql, whereParams);
//			} else {
//				this.create(values);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
		return false;
	}

	public ArrayList<Album> getAllBlogs () {
		ArrayList<Album> blogList = new ArrayList<Album>();
//		try {
//			ArrayList<ArrayList<String>> rList = this.query(null, null);
//			int rCount = rList.size();
//			for (int i = 0; i < rCount; i++) {
//				ArrayList<String> rRow = rList.get(i);
//				Album blog = new Album();
//				blog.setId(rRow.get(0));
//				blog.setFace(rRow.get(1));
//				blog.setContent(rRow.get(2));
//				blog.setComment(rRow.get(3));
//				blog.setAuthor(rRow.get(4));
//				blog.setUptime(rRow.get(5));
//				blogList.add(blog);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return blogList;
	}
}