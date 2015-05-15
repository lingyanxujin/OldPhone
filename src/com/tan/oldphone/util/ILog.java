package com.tan.oldphone.util;

import android.util.Log;


public class ILog{
	private static boolean isOpen = true;
	public static void e(String tag,String msg){
		if(isOpen){
			Log.e(tag, msg);
		}
	}
	public static void d(String tag,String msg){
		if(isOpen){
			Log.d(tag, msg);
		}
	}
}
