//package edu.nju.shalbum.ui;
//
///**
// * 相册主界面，相册列表
// */
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import edu.nju.shalbum.R;
//import edu.nju.shalbum.base.BaseAuth;
//import edu.nju.shalbum.base.BaseHandler;
//import edu.nju.shalbum.base.BaseList;
//import edu.nju.shalbum.base.BaseMessage;
//import edu.nju.shalbum.base.BaseSqlite;
//import edu.nju.shalbum.base.BaseTask;
//import edu.nju.shalbum.base.BaseUi;
//import edu.nju.shalbum.base.BaseUiAuth;
//import edu.nju.shalbum.base.C;
//import edu.nju.shalbum.list.AlbumList;
//import edu.nju.shalbum.model.Album;
//import edu.nju.shalbum.sqlite.BlogSqlite;
//import android.os.Bundle;
//import android.os.Message;
//import android.view.View;
//import android.view.KeyEvent;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ImageButton;
//import android.widget.ListView;
//
//public class UiIndex extends BaseUiAuth {
//
//	private ListView blogListView;
//	private BaseList blogListAdapter;
//	private BlogSqlite blogSqlite;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.ui_index);
//		
//		// set handler
//		this.setHandler(new IndexHandler(this));
//		
//		// tab button
//		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_1);
//		ib.setImageResource(R.drawable.tab_blog_2);
//		
//		// init sqlite
//		blogSqlite = new BlogSqlite(this);
//	}
//	
//	@Override
//	public void onStart(){
//		super.onStart();
//		
//		// show all blog list
//		HashMap<String, String> blogParams = new HashMap<String, String>();
//		blogParams.put("typeId", "0");
//		blogParams.put("pageId", "0");
//		this.doTaskAsync(C.task.albumList, C.api.albumList, blogParams);
//	}
//	
//	////////////////////////////////////////////////////////////////////////////////////////////////
//	// async task callback methods
//	
//	@Override
//	public void onTaskComplete(int taskId, BaseMessage message) {
//		super.onTaskComplete(taskId, message);
//
//		switch (taskId) {
//			case C.task.albumList:
//				try {
//					@SuppressWarnings("unchecked")
//					final ArrayList<Album> albumList = (ArrayList<Album>) message.getResultList("Album");
//					// 加载头像
//					for (Album album : albumList) {
//						loadImage("");
//						blogSqlite.updateBlog(album);
//					}
//					// show text
//					blogListView = (ListView) this.findViewById(R.id.app_index_list_view);
//					blogListAdapter = new AlbumList(this, albumList);
//					blogListView.setAdapter(blogListAdapter);
//					blogListView.setOnItemClickListener(new OnItemClickListener(){
//						@Override
//						public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//							Bundle params = new Bundle();
//							params.putString("albumid", albumList.get(pos).getAlbumid());
//							overlay(UiBlog.class, params);
//						}
//					});
//				} catch (Exception e) {
//					e.printStackTrace();
//					toast(e.getMessage());
//				}
//				break;
//		}
//	}
//	
//	@Override
//	public void onNetworkError (int taskId) {
//		super.onNetworkError(taskId);
//		toast(C.err.network);
//		switch (taskId) {
//			case C.task.albumList:
//				try {
////					final ArrayList<Album> blogList = blogSqlite.getAllBlogs();
//					Album a = new Album();a.setAlbumid("1");a.setCommentcount("comment 5");a.setName("测试的Album a");a.setUptime("1993");a.setUserid("1");
//					Album b = new Album();b.setAlbumid("2");b.setCommentcount("comment 5");b.setName("测试的Album b");b.setUptime("2014");b.setUserid("1");
//					final ArrayList<Album> blogList = new ArrayList<Album>();
//					blogList.add(a);
//					blogList.add(b);
//					// load face image
//					for (Album blog : blogList) {
//						loadImage("");
//						blogSqlite.updateBlog(blog);
//					}
//					// show text
//					blogListView = (ListView) this.findViewById(R.id.app_index_list_view);
//					blogListAdapter = new AlbumList(this, blogList);
//					blogListView.setAdapter(blogListAdapter);
//					blogListView.setOnItemClickListener(new OnItemClickListener(){
//						@Override
//						public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//							Bundle params = new Bundle();
//							params.putString("blogId", blogList.get(pos).getAlbumid());
//							overlay(UiBlog.class, params);
//						}
//					});
//				} catch (Exception e) {
//					e.printStackTrace();
//					toast(e.getMessage());
//				}
//				break;
//		}
//	}
//	
//	////////////////////////////////////////////////////////////////////////////////////////////////
//	// other methods
//	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			doFinish();
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//	
//	////////////////////////////////////////////////////////////////////////////////////////////////
//	// inner classes
//	
//	private class IndexHandler extends BaseHandler {
//		public IndexHandler(BaseUi ui) {
//			super(ui);
//		}
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			try {
//				switch (msg.what) {
//					case BaseTask.LOAD_IMAGE:
//						blogListAdapter.notifyDataSetChanged();
//						break;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				ui.toast(e.getMessage());
//			}
//		}
//	}
//}