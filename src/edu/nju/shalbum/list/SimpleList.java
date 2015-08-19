package edu.nju.shalbum.list;


import java.util.List;
import java.util.Map;
import edu.nju.shalbum.util.AppFilter;
import android.content.Context;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SimpleList extends SimpleAdapter {

	public SimpleList(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
	}

	@Override
	public void setViewText(TextView v, String text) {
		AppFilter.setHtml(v, text);
	}
}