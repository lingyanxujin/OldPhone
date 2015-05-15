package com.tan.oldphone.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tan.oldphone.R;
import com.tan.oldphone.common.Config;
import com.tan.oldphone.common.QohenUtils;
import com.tan.oldphone.util.SharedManager;

public class SettingActivity extends Activity {

	EditText et_IP;
	EditText et_mqtt_IP;
	EditText et_server_port;
	EditText et_mqtt_server_port;
	Button btnSure;
	Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initView();
		initViewData();

	}

	private void initView(){

		et_IP = (EditText)findViewById(R.id.et_IP);
		et_mqtt_IP = (EditText)findViewById(R.id.et_mqtt_IP);
		et_server_port = (EditText)findViewById(R.id.et_server_port);
		et_mqtt_server_port = (EditText)findViewById(R.id.et_mqtt_server_port);

		btnSure = (Button)findViewById(R.id.btnSure);
		btnCancel = (Button)findViewById(R.id.btnCancel);

		//		 et_IP.setOnFocusChangeListener(this);
		//		 et_mqtt_IP.setOnFocusChangeListener(this);
		//		 et_server_port.setOnFocusChangeListener(this);
		//		 et_mqtt_server_port.setOnFocusChangeListener(this);

		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_IP = et_IP.getText().toString().trim();
				String str_mqtt_IP = et_mqtt_IP.getText().toString().trim();
				String str_server_port = et_server_port.getText().toString().trim();
				String str_mqtt_server_port = et_mqtt_server_port.getText().toString().trim();
				if("".equals(str_IP) || !QohenUtils.isIp(str_IP)){
					Toast.makeText(SettingActivity.this, "Server:非法IP地址", 2000).show();
				}else 	if("".equals(str_mqtt_IP) || !QohenUtils.isIp(str_mqtt_IP)){
					Toast.makeText(SettingActivity.this, "MQTTServer:非法IP地址", 2000).show();
				}else if("".equals(et_server_port)){
					Toast.makeText(SettingActivity.this, "ServerPort:不能为空", 2000).show();
				}else if("".equals(str_mqtt_server_port)){
					Toast.makeText(SettingActivity.this, "MQTTServerPort:不能为空", 2000).show();
				}else{

					SharedManager.getInstance(SettingActivity.this).setServerIP(str_IP);
					SharedManager.getInstance(SettingActivity.this).setMQTTServerIP(str_mqtt_IP);
					SharedManager.getInstance(SettingActivity.this).setServerPort(str_server_port);
					SharedManager.getInstance(SettingActivity.this).setMQTTServerPort(str_mqtt_server_port);

					Config.resetURL(str_mqtt_IP, str_IP, str_server_port, str_mqtt_server_port);
					
					Intent intent = new Intent(SettingActivity.this, Login2Activity.class);
					setResult(Config.RESULTCODE_OK, intent);
					finish();
				}
			}
		});


		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(SettingActivity.this, Login2Activity.class);
				setResult(Config.RESULTCODE_FAILURE, intent);
				finish();
			}
		});


	}

	private void initViewData(){
		//SharedManager
		String str_IP = SharedManager.getInstance(this).getServerIP();
		String str_mqtt_IP = SharedManager.getInstance(this).getMQTTServerIP();
		String str_server_port = SharedManager.getInstance(this).getServerPort();
		String str_mqtt_server_port = SharedManager.getInstance(this).getMQTTServerPort();

		if(!"".equals(str_IP)){
			et_IP.setText(str_IP);
		}
		if(!"".equals(str_mqtt_IP)){
			et_mqtt_IP.setText(str_mqtt_IP);
		}		
		if(!"".equals(str_server_port)){
			et_server_port.setText(str_server_port);
		}		
		if(!"".equals(str_mqtt_server_port)){
			et_mqtt_server_port.setText(str_mqtt_server_port);
		}
	}

	//	@Override
	//	public void onFocusChange(View v, boolean flag) {
	//		if(!flag){
	//			switch(v.getId()){
	//			case R.id.et_IP:
	//				Toast.makeText(SettingActivity.this, "et_IP", 2000).show();
	//				break;
	//			case R.id.et_mqtt_IP:
	//				Toast.makeText(SettingActivity.this, "et_mqtt_IP", 2000).show();
	//				break;
	//			case R.id.et_server_port:
	//				Toast.makeText(SettingActivity.this, "et_server_port", 2000).show();
	//				break;
	//			case R.id.et_mqtt_server_port:
	//				Toast.makeText(SettingActivity.this, "et_mqtt_server_port", 2000).show();
	//				System.out.println("et_mqtt_server_port");
	//				break;
	//			}
	//		}
	//		
	//	}


}
