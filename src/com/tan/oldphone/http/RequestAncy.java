package com.tan.oldphone.http;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import com.tan.oldphone.R;
import com.tan.oldphone.common.Config;
import com.tan.oldphone.interf.GetResultInterface;
import com.tan.oldphone.util.ILog;
import com.tan.oldphone.util.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * AsyncTask定义了三种泛型类型 Params，Progress和Result。
 * Params   启动任务执行的输入参数，比如HTTP请求的URL。
 * Progress 后台任务执行的百分比。
 * Result   后台执行任务最终返回的结果，比如String,Integer等。
 * @author Administrator
 *
 */
public class RequestAncy<T> extends AsyncTask<String, String, Integer>{

	private Context mContext = null;
	private String url;
	private Map<String, String> params;
	private ProgressDialog progressDialog;
	private GetResultInterface<T> mGetResultInterface;
	private boolean isNetWork = true;
	private boolean isShowDialog = true;
	private Class<T> clazz;

	T response;

	public RequestAncy(Context context, String url, Map<String, String> params, boolean isShowDialog, Class<T> clazz, GetResultInterface<T> mGetResultInterface) {
		this.url = url;
		this.params = params;
		this.mContext = context;
		this.mGetResultInterface = mGetResultInterface;
		this.isShowDialog = isShowDialog;
		this.clazz = clazz;
		
		System.out.println("RequestAncy, url:"+this.url);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();		
		if(!Util.getInstance(mContext).GetIsNetworkAvailable()){
			Toast.makeText(mContext, mContext.getString(R.string.toast_network_error),
					Toast.LENGTH_SHORT).show();
			isNetWork = false;
		}else{
			if(isShowDialog){
				progressDialog = new ProgressDialog(mContext);
				progressDialog.setMessage("正在加载,请稍后...");
				progressDialog.show();
			}
		}
	}

	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub

		if(!isNetWork){
			return Config.NO_NETWORK;
		}

		try {
			if(this.params != null){
				response = HttpUtils.URLPost(this.url , this.params, this.clazz);
			}else{
				response = HttpUtils.URLPost(this.url, this.clazz);
			}
			
		}catch(ConnectException ce){ 
			ce.printStackTrace();
			return Config.UNABLE_CONNECT_SERVER;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Config.UNKONW_EXCEPTION;
		}
		return Config.REQUEST_SUCCESS;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if(isShowDialog&&progressDialog!=null){
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}

		if(result != Config.REQUEST_SUCCESS){
			//			Toast.makeText(mContext, "返回数据为null",
			//					Toast.LENGTH_SHORT).show();
			ILog.d("TAG", "返回数据为null");
			mGetResultInterface.onErrorResponse(result);
		}else{
			mGetResultInterface.onSuccessResponse(response);
		}
	}



	/*	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if(isShowDialog&&progressDialog!=null){
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}

		if(result == null ||result.equals("")){
			Toast.makeText(mContext, "返回数据为null",
					Toast.LENGTH_SHORT).show();
		}else if(result.equals("is error")||result.equals("is not normal")){
			Toast.makeText(mContext, mContext.getString(R.string.toast_request_error),
					Toast.LENGTH_SHORT).show();
		}else{
			mGetResultInterface.getResult(result);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(!Util.getInstance(mContext).GetIsNetworkAvailable()){
			Toast.makeText(mContext, mContext.getString(R.string.toast_network_error),
					Toast.LENGTH_SHORT).show();
			isNetWork = false;
		}else{
			if(isShowDialog){
				progressDialog = new ProgressDialog(mContext);
				progressDialog.setMessage("正在加载,请稍后...");
				progressDialog.show();
			}
		}
	}

	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected <T> T doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		if(isNetWork){
//			String result = Connector.getInstance(mContext).httpRequest(url,method, param, ishavaCookie);
			MessageJson messageJson = HttpUtils.URLPost(Config.LOGIN_URL, params,MessageJson.class);
			return result;
		}else{
			return "network error";
		}
	}*/

}
