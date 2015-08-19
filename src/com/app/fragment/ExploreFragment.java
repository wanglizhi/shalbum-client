package com.app.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.app.activity.ExploreActivity;
import com.example.localphotodemo.MyListView4;
import com.example.localphotodemo.TagActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseFragment;

public class ExploreFragment extends BaseFragment{
		private ListView listView;
		private ItemAdapter adapter = null;  
		private String[] tagArray = TagActivity.getTagArray();
		
	   
	    
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.exploretag_list, container, false);
			
	        //初始化适配器和Listview
	        adapter = new ItemAdapter (getActivity());  
	        listView =(ListView) rootView.findViewById(android.R.id.list);
	        listView.setAdapter(adapter);  
	        //设置间隔
	        listView.setDividerHeight(2);
	        //点击Tag
	        listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					//点击列表item事件
					Toast.makeText(getActivity(), "您选择了："+tagArray[position], Toast.LENGTH_SHORT).show();
					ExploreActivity.tag=tagArray[position];
					Intent intent = new Intent(getActivity(),ExploreActivity.class);
					getActivity().startActivity(intent);
				}
	        	
	        });
	        return rootView;
	    }
		
		/**自定义图片适配器**/
		class ItemAdapter extends BaseAdapter {

		    private LayoutInflater mInflater = null;  
		    
		    public ItemAdapter(Context context){  
		        super();  
		        mInflater = (LayoutInflater) context  
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		    }  
			private class ViewHolder {
				public TextView text;
				public ImageView image;
			}

			@Override
			public int getCount() {
				return tagArray.length;
			}

			@Override
			public Object getItem(int position) {
				return position;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View view = convertView;
				final ViewHolder holder;
				if (convertView == null) {
					view = mInflater.inflate(R.layout.item_exploretag,null);
					holder = new ViewHolder();
					holder.text = (TextView) view.findViewById(R.id.text);
					holder.image = (ImageView) view.findViewById(R.id.image);
					
					view.setTag(holder);
				} else {
					holder = (ViewHolder) view.getTag();
				}

				holder.text.setText(tagArray[position]);

				//Adds display image task to execution pool. Image will be set to ImageView when it's turn.
				switch(position){
				case 0:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag1));
					break;
				case 1:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag2));
					break;
				case 2:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag3));
					break;
				case 3:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag4));
					break;
				case 4:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag5));
					break;
				case 5:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag6));
					break;
				case 6:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag7));
					break;
				case 7:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag8));
					break;
				case 8:
					holder.image.setImageDrawable(getResources().getDrawable(R.drawable.tag9));
					break;
				default:
					break;
				
				}
				return view;
			}
		}

		

	

}
