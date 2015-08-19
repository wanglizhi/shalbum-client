package com.app.qin;

import java.util.ArrayList;

import com.app.fragment.ExploreFragment;
import com.app.fragment.LatestFragment;
import com.app.fragment.ShareCardFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.nju.shalbum.R;
import edu.nju.shalbum.model.Album;

public class HomeFragment extends Fragment {

	private FragmentTabHost mTabHost = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(),
				getChildFragmentManager(),R.id.tabhost );
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("首页"),
				ShareCardFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("最新"),
				LatestFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("探索"),
				ExploreFragment.class, null);

		return mTabHost;

	}

	public void refresh(ArrayList<Album> index,ArrayList<Album> explore,ArrayList<Album> newest){
		
	}
	

}