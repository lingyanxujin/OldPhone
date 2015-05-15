package com.tan.oldphone.common;

public class Config {
	
//	public static String MQTT_URL = "192.168.1.101";
//	public static String BASE_URL="http://192.168.1.101:8080";
////			+ "/navim-api";//访问地址	
////	public static String PRE_PIC_URL = "http://192.168.1.101:8080/navim-manager";  //pic url
//	
//	public static int MQTT_PORT = 1883;
	
	public static String MQTT_URL = "115.29.228.119";
	public static String BASE_URL="http://112.124.117.254:4040";
//			+ "/navim-api";//访问地址	
//	public static String PRE_PIC_URL = "http://192.168.1.101:8080/navim-manager";  //pic url
	
	public static int MQTT_PORT = 1883;

	public static String url = null;//访问地址
	public static String LOGIN_URL= BASE_URL + "/member/login";
	public static String REGISTER_URL= BASE_URL + "/member/register";
	public static String GETCONTACTLIST_URL= BASE_URL + "/contact/list";
	public static String GETCHANNEL_URL= BASE_URL + "/member/getChannel";
	public static String LOGOUT_URL= BASE_URL + "/member/logout";
	
	public final static int ERROR = 0;
	public final static int LOGIN_SUCCESS = 1;
	public final static int GETCHANNEL_SUCCESS = 2;
	public final static int GETCONTACTLIST_SUCCESS = 4;
	public final static int TEST = 3;
	
	public final static int UNABLE_CONNECT_SERVER = 4;
	public final static int NO_NETWORK = 5;
	public final static int UNKONW_EXCEPTION = 6;
	public final static int REQUEST_SUCCESS = 7;
	
	public final static int REQUESTCODE_SETTINGACTIVITY = 1;
	public final static int RESULTCODE_OK = 2;
	public final static int RESULTCODE_FAILURE = 3;
	
	public final static int MAIN_UI_LOAD = 0;
	public final static int MAIN_UI_SHOW_NO_DATA = 1;
	public final static int MAIN_UI_SHOW_DATA = 2;
	
	public final static int HANDLER_REFRESH_MAIN_UI = 3;
	
	
	
	public static void resetURL(String str_mqtt_IP, String str_IP, String str_server_port, String str_mqtt_server_port)
	{
		Config.MQTT_URL = str_mqtt_IP;
		Config.BASE_URL = "http://"+ str_IP +":"+str_server_port;
		//							+"/navim-api";
		//					Config.PRE_PIC_URL = "http://"+ str_IP +":"+str_server_port+"/navim-manager";
		Config.MQTT_PORT = Integer.parseInt(str_mqtt_server_port);
		
		
		LOGIN_URL= BASE_URL + "/member/login";
		REGISTER_URL= BASE_URL + "/member/register";
		GETCONTACTLIST_URL = BASE_URL + "/contact/list";
		GETCHANNEL_URL= BASE_URL + "/member/getChannel";
		LOGOUT_URL= BASE_URL + "/member/logout";
		
		System.out.println("MQTT_URL:" + Config.MQTT_URL);
		System.out.println("BASE_URL:" + Config.BASE_URL);
		//					System.out.println("PRE_PIC_URL:" + Config.PRE_PIC_URL);
		System.out.println("MQTT_PORT:" + Config.MQTT_PORT);
		
//		System.out.println("Config,  LOGIN_URL:"+LOGIN_URL);
//		System.out.println("Config,  REGISTER_URL:"+REGISTER_URL);
//		System.out.println("Config,  GETCONTACTLIST_URL:"+GETCONTACTLIST_URL);
//		System.out.println("Config,  GETCHANNEL_URL:"+GETCHANNEL_URL);
//		System.out.println("Config,  LOGOUT_URL:"+LOGOUT_URL);
	}
}
