package com.tan.oldphone.ui;

import com.tan.oldphone.service.MQTTActivService;
import com.tan.oldphone.service.MQTTActivService.SlaveBinder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

public class BindSrvcBaseActivity extends BaseActivity {
	
	protected SlaveBinder mottActivSlave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("lifecycle","BindSrvcBaseActivity######,  onCreate..."+this);
		System.out.println("BindSrvcBaseActivity, onCreate ..."+this.toString() );
		_startService();
		mottActivSlave = null;
		_bindService();
	}

	/*******  operate service  *********/
	void _startService(){
		Intent startIntent = new Intent(this, MQTTActivService.class);  
		startService(startIntent);  
	}
	
	void _bindService(){
		System.out.println("BindSrvcBaseActivity######, _bindService..."+this.toString());
		Intent intent = new Intent(this, MQTTActivService.class);  
		bindService(intent, connection, BIND_AUTO_CREATE);
	}
	
	void _unbindService(){
		unbindService(connection);
	}
	
	void _stopService(){
		Intent stopIntent = new Intent(this, MQTTActivService.class);  
		this.stopService(stopIntent);
	}
	/*******  operate service  *********/
	
	/*******  activity  lifecycle  *********/
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("lifecycle","BindSrvcBaseActivity######,  onResume..."+this);
		System.out.println("BindSrvcBaseActivity, onResume ..."+this.toString());
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();	
		Log.d("lifecycle","BindSrvcBaseActivity######,  onPause..."+this);
		System.out.println("BindSrvcBaseActivity, onPause ..."+this.toString());
		
		if(mottActivSlave == null){
			System.out.println(this.toString() + "  onPause,  mottActivSlave = null;");
		}else{
			System.out.println(this.toString() + "  onPause,  mottActivSlave: " +mottActivSlave.toString() );
		}	
	}
	
	@Override
	protected void onStop() {
		super.onStop();	
		Log.d("lifecycle","BindSrvcBaseActivity######,  onStop..."+this);
		System.out.println("BindSrvcBaseActivity, onStop ..."+this.toString());	
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("lifecycle","BindSrvcBaseActivity######,  onDestroy..."+this);
		System.out.println("BindSrvcBaseActivity, onDestroy ..."+this.toString());
		_unbindService();
		System.out.println("onDestroy, mottActivSlave:" +mottActivSlave.toString() );
//		mottActivSlave = null;
		if(mottActivSlave == null){
			System.out.println(this.toString() + "  onDestroy,  mottActivSlave = null;");
		}else{
			System.out.println(this.toString() + "  onDestroy,  mottActivSlave: " +mottActivSlave.toString() );
		}
	}
	/*******  activity  lifecycle  *********/
	
	
	private ServiceConnection connection = new ServiceConnection() {  

		@Override  
		public void onServiceDisconnected(ComponentName name) {  
			mottActivSlave = null;
			System.out.println("BindSrvcBaseActivity, onServiceDisconnected ...");	
		}  

		@Override  
		public void onServiceConnected(ComponentName name, IBinder service) {  
			mottActivSlave = (SlaveBinder) service; 
			System.out.println("onServiceConnected: "+BindSrvcBaseActivity.this.toString()+",  "+mottActivSlave+";;");
			
			if(bindCallback!=null){
				if(mottActivSlave!=null){
					bindCallback.bindSuccess();
				}else{
					bindCallback.bindFail();
				}
			}
		}  
	};  
	
	IBindCallback bindCallback;	
	
	public void setBindCallback(IBindCallback bindCallback) {
		this.bindCallback = bindCallback;
	}


	public interface IBindCallback{
		void bindFail();
		void bindSuccess();
	}
	
	private int backID = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && backID == 0) {
			backID++;
			Toast.makeText(this, "在按一次退出程序.", Toast.LENGTH_SHORT).show();
			return true;
		}
		
		this._stopService();
        this.finish();
		return super.onKeyDown(keyCode, event);
	}
	
}
