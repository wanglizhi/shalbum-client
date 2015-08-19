package edu.nju.shalbum.base;

import edu.nju.shalbum.model.User;

/**
 * 验证用户登录的类
 * @author wlz
 *
 */
public class BaseAuth {
	
	static public boolean isLogin () {
		User user = User.getInstance();
		if (user.isLogin() == true) {
			return true;
		}
		return false;
	}
	
	static public void setLogin (Boolean status) {
		User customer = User.getInstance();
		customer.setLogin(status);
	}
	
	static public void setUser (User mc) {
		User user = User.getInstance();
		user.setUserid(mc.getUserid());
		user.setSessionid(mc.getSessionid());
		user.setUsername(mc.getUsername());
		user.setSign(mc.getSign());
		user.setFace(mc.getFace());
		user.setAlbumcount(mc.getAlbumcount());
		user.setFanscount(mc.getFanscount());
		user.setFollowcount(mc.getFollowcount());
		user.setUptime(mc.getUptime());
		user.setPassword(mc.getPassword());
	}
	
	static public User getUser () {
		return User.getInstance();
	}
}