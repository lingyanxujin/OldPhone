package com.tan.oldphone.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tan.oldphone.R;
import com.tan.oldphone.broadreceiver.MyPhoneBroadcastReceiver;

public class MyPhone extends BaseActivity implements OnClickListener{
	public final static String TAG = "MyPhone";
	
	public final static String B_PHONE_STATE = TelephonyManager.ACTION_PHONE_STATE_CHANGED;
	
	private Button btnRegister;
	private Button btnUnRegister;
	private TextView tvShow;
	
	PhoneHandler mHandler;
	
	private MyPhoneBroadcastReceiver mBroadcastReceiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_phone);
		initView();
		mHandler = new PhoneHandler();
	}
	
	private void initView(){
		btnRegister = (Button)findViewById(R.id.btnRegister);
		btnUnRegister = (Button)findViewById(R.id.btnUnRegister);
		tvShow = (TextView)findViewById(R.id.tvShow);
		
		btnRegister.setOnClickListener(this);
		btnUnRegister.setOnClickListener(this);
	}
	
	//按钮1-注册广播
	public void registerThis(View v) {
		Log.i(TAG, "registerThis");
		mBroadcastReceiver = new MyPhoneBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(B_PHONE_STATE);
		intentFilter.setPriority(Integer.MAX_VALUE);
		registerReceiver(mBroadcastReceiver, intentFilter);
		
		MyPhoneReceive mpr = new MyPhoneReceive();
		intentFilter = new IntentFilter();
		intentFilter.addAction("do_phone_result");
		registerReceiver(mpr, intentFilter);
		
	}
	
	//按钮2-撤销广播
	public void unregisterThis(View v) {
		Log.i(TAG, "unregisterThis");
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnRegister:
			registerThis(v);
			break;
		case R.id.btnUnRegister:
			unregisterThis(v);
			break;
			
		}
		
	}
	
	class PhoneHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				Intent intent = new Intent(mContext, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				break;
			}
		}
	}
	
	class MyPhoneReceive extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Message msg = mHandler.obtainMessage();
			msg.what = 0;
			mHandler.sendMessage(msg);
		}
		
	}
}