package com.tan.oldphone.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tan.oldphone.R;
import com.tan.oldphone.common.AndroidUtils;
import com.tan.oldphone.http.CommHttpClient;

public class LoginActivity extends Activity{

	private EditText userIdText;
	private EditText passwordText;
	private Button loginButton,registerButton;
	private ImageView ivLogin, ivRegister;
	
	private CommHttpClient chttpClient;
	
	 private ProgressDialog progressDialog;  
	 
	 private LoginHandler loginHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initView();
		
		chttpClient = new CommHttpClient(LoginActivity.this);
		loginHandler = new LoginHandler();
		
/*		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				login();
			}
		});
		
		registerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				startActivity(intent);
			}
		});*/
	}
	
	class LoginHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	}

	private void login(){
		String userId=userIdText.getText().toString();
		String password=passwordText.getText().toString();
		if("".equals(userId)){
			AndroidUtils.createNDialog(LoginActivity.this, getResources().getString(R.string.login_userid_empty), null);
			return;
		}
		
		if("".equals(password)){
			AndroidUtils.createNDialog(LoginActivity.this, getResources().getString(R.string.login_password_empty), null);
			return;
		}	
		
		//显示ProgressDialog  
        progressDialog = ProgressDialog.show(LoginActivity.this, "Loading...", "Please wait...", true, false);  
        chttpClient.login(userId, password, loginHandler);
	}
	
	private void initView(){
		ivLogin = (ImageView)findViewById(R.id.ivLogin);
		ivRegister = (ImageView)findViewById(R.id.ivRegister);
		ivLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMQTTService();
			}
		});
		ivRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopMyService();
			}
		});
		
	}
	
	private void startMQTTService(){
		//启动服务
//		Intent intent=new Intent(this,MQTTService.class);
		

        Intent intent = new Intent();
        intent.setAction("com.tan.lodphone.service.mqtt");
		
		startService(intent);
	}

    
    void stopMyService(){
    	
    	 Intent intent = new Intent();
         intent.setAction("com.tan.lodphone.service.mqtt");
         
		 stopService(intent);
    }
	
}
