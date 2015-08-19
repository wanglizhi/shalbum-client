package edu.nju.shalbum.base;
/**
 * 主要的网络配置文件
 * @author wlz
 *
 */
public final class C {

	//文件目录
	public static final class dir {
		public static final String base				= "/sdcard/shalbum";
		public static final String faces			= base + "/faces";
		public static final String images			= base + "/images";
	}
	//URL地址
	public static final class api {
		public static final String base				= "http://shalbums.sinaapp.com/index.php";
		public static final String index			= "/albumC/get_all_albums";
		public static final String login			= "/login/logins";
		public static final String logout			= "/index/logout";
		public static final String uploadFace 		= "/login/do_upload";
		public static final String faceList 		= "/image/faceList";
		public static final String albumList		= "/albumC/get_all_albums";
		public static final String photoList		= "/albumC/get_album_photos";
		public static final String albumCreate		= "/albumC/create_album";
		public static final String photoCreate		= "/albumC/create_photo";
		public static final String uploadPhoto		= "/albumC/upload_photo";
		public static final String commentList		= "/comment/commentList";
		public static final String commentCreate	= "/comment/commentCreate";
		public static final String customerView		= "/customer/customerView";
		public static final String customerEdit		= "/customer/customerEdit";
		public static final String fansAdd			= "/userC/fan_add";
		public static final String fansDel			= "/userC/fan_delete";
		public static final String notice			= "/notify/notice";
		public static final String getRecommend		= "/userC/get_all_user";
		public static final String getFollows		= "/userC/get_follows";
		public static final String getFans			= "/userC/get_fans";
		public static final String getAlbumsByTag	= "/albumC/get_tag_album";
		public static final String getFollowsAlbum	= "/albumC/get_follows_album";
		public static final String getHotAlbums		= "/albumC/get_hot_albums";
		public static final String getUserAlbums	= "/albumC/get_user_albums";
		public static final String getStoreAlbums 	= "/albumC/get_store_albums";
		public static final String zan				= "/albumC/zan";
		public static final String zanCancle		= "/albumC/zan_cancle";
		public static final String storeAlbum 		= "/albumC/store_album";
		public static final String storeCancel 		= "/albumC/store_cancel";
		
		
	}
	//任务编号
	public static final class task {
		public static final int login				= 1000;
		public static final int index				= 1001;
		public static final int logout				= 1003;
		public static final int uploadFace			= 1004;
		public static final int photoList			= 1005;
		public static final int albumList			= 1006;
		public static final int albumCreate			= 1007;
		public static final int photoCreate			= 1008;
		public static final int commentList			= 1009;
		public static final int commentCreate		= 1010;
		public static final int customerView		= 1011;
		public static final int customerEdit		= 1012;
		public static final int fansAdd				= 1013;
		public static final int fansDel				= 1014;
		public static final int notice				= 1015;
		public static final int uploadPhoto			= 1016;
		public static final int getRecomment		= 1017;
		public static final int getFollows			= 1018;
		public static final int getFans				= 1019;
		public static final int getAlbumsByTag		= 1020;
		public static final int getFollowsAlbum		= 1021;
		public static final int getHotAlbums		= 1022;
		public static final int getUserAlbums		= 1023;
		public static final int zan					= 1024;
		public static final int zanCancel			= 1025;
		public static final int storeAlbum 			= 1026;
		public static final int storeCancel			= 1027;
		public static final int getStoreAlbums		= 1028;
	}
	
	public static final class err {
		public static final String network			= "网络错误";
		public static final String message			= "消息错误";
		public static final String jsonFormat		= "消息格式错误";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// intent & action settings
	
	public static final class intent {
		public static final class action {
			public static final String EDITTEXT		= "com.app.demos.EDITTEXT";
			public static final String EDITBLOG		= "com.app.demos.EDITBLOG";
		}
	}
	
	public static final class action {
		public static final class edittext {
			public static final int CONFIG			= 2001;
			public static final int COMMENT			= 2002;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// additional settings
	
	public static final class web {
		public static final String base				= "http://192.168.1.2:8002";
		public static final String index			= base + "/index.php";
		public static final String gomap			= base + "/gomap.php";
	}
}