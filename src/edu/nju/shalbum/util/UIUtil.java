package edu.nju.shalbum.util;

import edu.nju.shalbum.R;
import edu.nju.shalbum.model.User;
import android.content.Context;
import android.content.res.Resources;
/**
 * ui工具类，包含取得用户信息的函数
 * @author wlz
 *
 */
public class UIUtil {

	// tag for log
//	private static String TAG = UIUtil.class.getSimpleName();
	//格式化输出用户信息（后续补充……）
	public static String getCustomerInfo (Context ctx, User user) {
		Resources resources = ctx.getResources();
		StringBuffer sb = new StringBuffer();
		//
		sb.append("X相册数目");
		sb.append(" ");
		sb.append(user.getAlbumcount());
		sb.append(" | ");
		sb.append("粉丝数目");
		sb.append(" ");
		sb.append(user.getFanscount());
		return sb.toString();
	}
}