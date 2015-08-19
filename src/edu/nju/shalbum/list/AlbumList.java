//package edu.nju.shalbum.list;
//
//
//import java.util.ArrayList;
//
//import edu.nju.shalbum.R;
//import edu.nju.shalbum.base.BaseList;
//import edu.nju.shalbum.base.BaseUi;
//import edu.nju.shalbum.model.Album;
//import edu.nju.shalbum.util.AppCache;
//import edu.nju.shalbum.util.AppFilter;
//import android.graphics.Bitmap;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class AlbumList extends BaseList {
//
//	private BaseUi ui;
//	private LayoutInflater inflater;
//	private ArrayList<Album> blogList;
//	
//	public final class BlogListItem {
//		public ImageView face;
//		public TextView content;
//		public TextView uptime;
//		public TextView comment;
//	}
//	
//	public AlbumList (BaseUi ui, ArrayList<Album> blogList) {
//		this.ui = ui;
//		this.inflater = LayoutInflater.from(this.ui);
//		this.blogList = blogList;
//	}
//	
//	@Override
//	public int getCount() {
//		return blogList.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int p, View v, ViewGroup parent) {
//		// init tpl
//		BlogListItem  blogItem = null;
//		// if cached expired
//		if (v == null) {
//			v = inflater.inflate(R.layout.tpl_list_blog, null);
//			blogItem = new BlogListItem();
//			blogItem.face = (ImageView) v.findViewById(R.id.tpl_list_blog_image_face);
//			blogItem.content = (TextView) v.findViewById(R.id.tpl_list_blog_text_content);
//			blogItem.uptime = (TextView) v.findViewById(R.id.tpl_list_blog_text_uptime);
//			blogItem.comment = (TextView) v.findViewById(R.id.tpl_list_blog_text_comment);
//			v.setTag(blogItem);
//		} else {
//			blogItem = (BlogListItem) v.getTag();
//		}
//		// fill data
//		blogItem.uptime.setText(blogList.get(p).getUptime());
//		// fill html data
//		blogItem.content.setText(AppFilter.getHtml(blogList.get(p).getName()));
//		blogItem.comment.setText(AppFilter.getHtml(blogList.get(p).getCommentcount()));
//		// load face image
//		String faceUrl = "";
//		Bitmap faceImage = AppCache.getImage(faceUrl);
//		if (faceImage != null) {
//			blogItem.face.setImageBitmap(faceImage);
//		}
//		return v;
//	}
//}