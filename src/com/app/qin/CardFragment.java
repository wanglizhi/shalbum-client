package com.app.qin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.nju.shalbum.R;

public class CardFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		TextView tv =new TextView(getActivity());
		tv.setText("this is a tabcontent");
		return tv;
	}
}
