package com.tan.oldphone.http;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tan.oldphone.R;
import com.tan.oldphone.common.Config;
import com.tan.oldphone.common.GsonUtils;
import com.tan.oldphone.model.ReturnMsg;

public class CommHttpClient {

	private RequestQueue requestQueue;
	private Context context;

	public CommHttpClient(Context context) {
		this.context = context;
		requestQueue = Volley.newRequestQueue(context);
	}

	/**
	 * test
	 */
	public void test(final Handler handler) {
		final ProgressDialog pd = ProgressDialog.show(context, null,
				context.getString(R.string.test));
		StringRequest stringRequest = new StringRequest("http://www.baidu.com",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						Log.d("TAG", response);
						sendSuccessTest(response, handler, Config.TEST);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						Log.e("TAG", error.getMessage(), error);
						sendError(handler,Config.ERROR);
					}
				});

		requestQueue.add(stringRequest);
	}
	
	public void testJson(final Handler handler){
		final ProgressDialog pd = ProgressDialog.show(context, null,
				context.getString(R.string.test));
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						String jsonstr = response.toString();
						Log.d("TAG", jsonstr);
//						ReturnMsg msg = GsonUtils.getReturnMsg(jsonstr);
						sendSuccessTest(jsonstr, handler, Config.TEST);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						Log.e("TAG", error.getMessage(), error);
						sendError(handler,Config.ERROR);
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

	/**
	 * 用户登录
	 * 
	 * @param userId
	 * @param password
	 * @param handler
	 */
	public void login(final String userId, final String password,
			final Handler handler) {
		final ProgressDialog pd = ProgressDialog.show(context, null,
				context.getString(R.string.login_logining));
		//String url = Config.url + "/member/login";
		String url = "http://192.168.1.102:8080/navim-api/member/login?username=abcdefghi&password=8AA99B1F439FF71293E95357BAC6FD94";
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						Log.d("TAG", arg0);
//						ReturnMsg msg = GsonUtils.getReturnMsg(arg0);
//						sendSuccess(msg, handler, Config.LOGIN_SUCCESS);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						sendError(handler,Config.ERROR);
					}

				}) {
			@Override
			protected HashMap<String, String> getParams() {
				HashMap<String, String> params = new HashMap<String, String>();
//				params.put("username", userId);
//				params.put("password", password);
				return params;
			}
		};

		requestQueue.add(stringRequest);
	}

	/**
	 * 用户注册
	 * 
	 * @param userId
	 * @param password
	 * @param handler
	 */
	public void register(final String userId, final String password,
			final Handler handler) {
		final ProgressDialog pd = ProgressDialog.show(context, null,
				context.getString(R.string.register_registering));
		String url = Config.url + "/member/register";
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						ReturnMsg msg = GsonUtils.getReturnMsg(arg0);
//						sendSuccess(msg, handler, Config.REGISTER_SUCCESS);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						sendError(handler, Config.ERROR);
					}

				}) {
			@Override
			protected HashMap<String, String> getParams() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("username", userId);
				params.put("password", password);
				return params;
			}
		};

		requestQueue.add(stringRequest);
	}

	/**
	 * 获取网络通讯录
	 * 
	 * @param handler
	 */
	public void getContacts(final Handler handler) {
		final ProgressDialog pd = ProgressDialog.show(context, null,
				context.getString(R.string.main_loading));
		String url = Config.url + "/contact/list";
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						ReturnMsg msg = GsonUtils.getReturnMsg(arg0);
//						sendSuccess(msg, handler, Config.GETCONTACTS_SUCCESS);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						sendError(handler,Config.ERROR);
					}

				}) {
			@Override
			protected HashMap<String, String> getParams() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("pageNo", "1");
				params.put("pageSize", "100");
				return params;
			}
		};

		requestQueue.add(stringRequest);
	}

	/**
	 * 注销登陆
	 * 
	 * @param handler
	 */
	public void logout(final Handler handler) {
		final ProgressDialog pd = ProgressDialog.show(context, null,
				context.getString(R.string.logout));
		String url = Config.url + "/contact/logout";
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						ReturnMsg msg = GsonUtils.getReturnMsg(arg0);
//						sendSuccess(msg, handler, Config.LOGOUT_SUCCESS);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						sendError(handler,Config.ERROR);
					}

				}) {
			@Override
			protected HashMap<String, String> getParams() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("pageNo", "1");
				params.put("pageSize", "100");
				return params;
			}
		};

		requestQueue.add(stringRequest);
	}

	/**
	 * 获取推送通道
	 * 
	 * @param handler
	 */
	public void getChannel(final Handler handler) {
		final ProgressDialog pd = ProgressDialog.show(context, null,
				context.getString(R.string.logout));
		String url = "http://192.168.1.101:8080/navim-api/member/getChannel";
//		String url = Config.url + "/contact/logout";
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						Log.d("TAG", arg0);
						ReturnMsg msg = GsonUtils.getReturnMsg(arg0);
						System.out.println("msg:"+msg.toString()+", "+msg.getData().toString() );
						sendSuccess(msg, handler, Config.GETCHANNEL_SUCCESS);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						sendError(handler,Config.ERROR);
					}

				}) {
			@Override
			protected HashMap<String, String> getParams() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("pageNo", "1");
				params.put("pageSize", "100");
				return params;
			}
		};

		requestQueue.add(stringRequest);
	}

	private void sendSuccessTest(String msg, Handler handler, int index) {
		Message message = new Message();
		message.what = index;
		message.obj = msg;
		handler.sendMessage(message);
	}

	private void sendSuccess(ReturnMsg msg, Handler handler, int index) {
		Message message = new Message();
		message.what = index;
		message.obj = msg;
		handler.sendMessage(message);
	}

	private void sendError(Handler handler, int index) {
		Message message = new Message();
		message.what = index;
		message.obj = context.getString(R.string.common_netword_error);
		handler.sendMessage(message);
	}
}
