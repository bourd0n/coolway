package com.coolway.common.model.session;
public class SessionConstants {

	
	//login result  0 失败 1 成功 2 账号已存在  3 账号不存在
	public static final int LOGIN_FAILE = 0; 
	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_ACCOUNT_EXIST = 2;
	public static final int LOGIN_ACCOUNT_UNEXIST = 3;
	
	//bind result 0 失败 1成功 2合并 3账号已经存在
	public static final int BIND_RESULT_FAILE = 0;
	public static final int BIND_RESULT_SUCCESS = 1;
	public static final int BIND_RESULT_MERGE = 2;
	public static final int BIND_RESULT_ISEXIST = 3;
	
	//檢查昵稱或者帳號是否存在
	public static final int RESULT_ALL_NOTEXIST = 0;
	public static final int RESULT_EMAIL_EXIST = 1;
	public static final int RESULT_NICK_EXIST = 2;
	
	public static String SESSION_USER = "loginUser";
	public static String SESSION_ADMIN = "loginAdmin";
	public static String SESSION_COOKIE_FLAG = "cookieFlag";
	public static String SESSION_COOKIE_FLAG_SEESION = "session";
	public static String SESSION_COOKIE_FLAG_COOKIE = "cookie";
}
