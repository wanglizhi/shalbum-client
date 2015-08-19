package com.app.fragment;

import it.gmariotti.cardslib.library.view.CardView;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.app.cards.ShareCard;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseMessage;
import edu.nju.shalbum.base.C;
import edu.nju.shalbum.model.Album;

public class FriendFragment extends BaseFragment {
	private PullToRefreshScrollView mPullRefreshScrollView;
	protected ScrollView mScrollView;
	private CardView cardView;
	private ShareCard shareCard;
	ArrayList<Album> albumList;
	private int count=0;
	private boolean isFirstUse = true;
	private boolean isRefresh = false;
	private boolean isLoad = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// doTask();
		HashMap<String, String> albumParams = new HashMap<String, String>();
		albumParams.put("pageid", 0+"");
		this.doTaskAsync(C.task.albumList, C.api.albumList, albumParams);
		setHasOptionsMenu(true);
	}

	@Override
	public void onStart() {
		super.onStart();
		// HashMap<String, String> albumParams = new HashMap<String, String>();
		// this.doTaskAsync(C.task.albumList, C.api.albumList, albumParams);
		initShareCard(albumList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sharecard_fragment, container, false);
		mPullRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setMode(Mode.BOTH);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener2<ScrollView>(){

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_LONG).show();
				HashMap<String, String> albumParams = new HashMap<String, String>();
				albumParams.put("pageid", 0+"");
				doTaskAsync(C.task.albumList, C.api.albumList, albumParams);
				isRefresh = true;
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
				// TODO Auto-generated method stub
				count++;
				LinearLayout innerLayout = (LinearLayout) getActivity().findViewById(
						R.id.sharecard_cards);
				HashMap<String, String> albumParams = new HashMap<String, String>();
				albumParams.put("pageid", count+"");
				doTaskAsync(C.task.albumList, C.api.albumList, albumParams);
				isLoad = true;
				
			}
			
		});
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mPullRefreshScrollView = (PullToRefreshScrollView) getActivity().findViewById(
				R.id.pull_refresh_scrollview);
		mScrollView = mPullRefreshScrollView.getRefreshableView();
		// initShareCard(albumList);

	}

	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		Log.w("123456", message.getResult());
		switch (taskId) {
		case C.task.albumList:
			try {
				@SuppressWarnings("unchecked")
				ArrayList<Album> albumList = (ArrayList<Album>) message
						.getResultList("Album");
				this.albumList = albumList;
				if(isFirstUse){
					this.onStart();
					isFirstUse = false;
				}
				if(isRefresh){
					this.onStart();
					isRefresh = false;
				}
				if(isLoad){
					loadingShareCard(albumList);
					isLoad = false;
				}
				//initShareCard(albumList);
//
//				for (Album a : albumList) {
//
//					Log.w("album_fragment",
//							a.getAlbumid() + " " + a.getName() + " "
//									+ a.getUserid()
//									+ (a.getPhoto_list().toString()));
//					for (Photo p : a.getPhoto_list()) {
//						Log.w("photo", p.getContent() + p.getDescribe());
//					}
//					// HashMap<String, String> photoParams = new HashMap<String,
//					// String>();
//					// photoParams.put("albumid", a.getAlbumid());
//					// this.doTaskAsync(C.task.photoList, C.api.photoList,
//					// photoParams);
//				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				if(isLoad)
					count--;
				e.printStackTrace();
			}
			mPullRefreshScrollView.onRefreshComplete();
			break;
		}
	}

	public void initShareCard(ArrayList<Album> albumList) {

		LinearLayout innerLayout = (LinearLayout) getActivity().findViewById(
				R.id.sharecard_cards);
		
		//初始化前清空LinearLayout中所有View
		innerLayout.removeAllViews();
		
		if (albumList != null && albumList.size() != 0) {
			for (int i = 0; i < albumList.size(); i++) {

				// Add views to inner layouts
				CardView cardView = new CardView(getActivity());
				ShareCard shareCard = new ShareCard(getActivity(),
						albumList.get(i));
				cardView.setCard(shareCard);
				innerLayout.addView(cardView);
			}
		}

	}
	
	public void loadingShareCard(ArrayList<Album> albumList){
		LinearLayout innerLayout = (LinearLayout) getActivity().findViewById(
				R.id.sharecard_cards);
		
		
		if (albumList != null && albumList.size() != 0) {
			for (int i = 0; i < albumList.size(); i++) {

				// Add views to inner layouts
				CardView cardView = new CardView(getActivity());
				ShareCard shareCard = new ShareCard(getActivity(),
						albumList.get(i));
				cardView.setCard(shareCard);
				innerLayout.addView(cardView);
			}
		}
	}

	@Override
	public int getTitleResourceId() {
		// TODO Auto-generated method stub
		return 0;
	}

}