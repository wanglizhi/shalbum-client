package edu.nju.shalbum.util;


import edu.nju.shalbum.R;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
/**
 * AppFilter，将String转换成html显示在View控件中。
 * @author wlz
 *
 */

public class AppFilter {

	public static Spanned getHtml (String text) {
		return Html.fromHtml(text);
	}
	
	/* used by list classes */
	public static void setHtml (TextView tv, String text) {
		//此处应该更改================
//		if (tv.getId() == R.id.tpl_list_blog_text_content ||
//			tv.getId() == R.id.tpl_list_blog_text_comment ||
//			tv.getId() == R.id.tpl_list_comment_content
//			) {
//			tv.setText(AppFilter.getHtml(text));
//		} else {
//			tv.setText(text);
//		}
		tv.setText(text);
	}
}