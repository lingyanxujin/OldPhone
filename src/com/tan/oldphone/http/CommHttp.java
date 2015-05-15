package com.tan.oldphone.http;

import java.util.HashMap;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;


import com.tan.oldphone.R;
import com.tan.oldphone.common.Config;
import com.tan.oldphone.model.ChannelJson;
import com.tan.oldphone.model.ContactJson;
import com.tan.oldphone.model.MessageJson;
import com.tan.oldphone.model.ReturnMsg;

public class CommHttp {
	
	public static void Login(final Context context,final String userName, final String passWord, final Handler handler){

//		final ProgressDialog pd = ProgressDialog.show(context, null,
//				context.getString(R.string.login_logining));
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HashMap<String, String> params = new HashMap<String, String>();
				String pw = MD5Util.string2MD5(passWord).toUpperCase();
				params.put("username", userName);
//				params.put("password", pw);
				params.put("password", pw);
				try {
					MessageJson messageJson = HttpUtils.URLPost(Config.LOGIN_URL, params,MessageJson.class);
					CommHttp.sendSuccess(messageJson, handler, Config.LOGIN_SUCCESS);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					CommHttp.sendError(context, handler);
				}
			}
		}).start();
		
		
	}
	
	
	public static void getChannel(final Context context, final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ChannelJson channelJson = HttpUtils.URLPost(Config.GETCHANNEL_URL, ChannelJson.class);
					CommHttp.sendSuccess(channelJson, handler, Config.GETCHANNEL_SUCCESS);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					CommHttp.sendError(context, handler);
				}
			}
		}).start();
	}
	
	
	public static void getContactList(final Context context, final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
/*				try {
					ContactJson2 contactJson = HttpUtils.URLPost(Config.GETCONTACTLIST_URL, ContactJson2);
					CommHttp.sendSuccess(contactJson, handler, Config.GETCONTACTLIST_SUCCESS);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					CommHttp.sendError(context, handler);
				}*/
			}
		}).start();
	}


	public static void sendSuccess(Object msg, Handler handler, int index) {
		System.out.println("info  msg: " + msg.toString() );
		Message message = new Message();
		message.what = index;
		message.obj = msg;
		handler.sendMessage(message);
	}

	public static void sendError(Context context, Handler handler) {
		Message message = new Message();
		message.what = Config.ERROR;
		message.obj = context.getString(R.string.common_netword_error);
		handler.sendMessage(message);
	}
	
	

}
