package com.tan.oldphone.ui;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tan.oldphone.R;
import com.tan.oldphone.common.Config;
import com.tan.oldphone.http.MD5Util;
import com.tan.oldphone.http.RequestAncy;
import com.tan.oldphone.interf.GetResultInterface;
import com.tan.oldphone.model.MessageJson;
import com.tan.oldphone.mview.TitleLayout;
import com.tan.oldphone.util.Util;

public class RegisterActivity extends BaseActivity {

	TitleLayout mTitleLayout;
	EditText et_name;
	EditText et_pwd;
	EditText et_pwd_sure;
	EditText et_telephone;
	Button btn_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);

		initView();
	}

	private void initView(){
		mTitleLayout = (TitleLayout) findViewById(R.id.layout_title);
		mTitleLayout.setCenter("用户注册", getResources().getColor(R.color.gray_normal));

		et_name = (EditText)findViewById(R.id.et_name);
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		et_pwd_sure = (EditText)findViewById(R.id.et_pwd_sure);	
		et_telephone = (EditText)findViewById(R.id.et_telephone);	
		btn_sure = (Button)findViewById(R.id.btn_sure);	

		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isConditions();
			}
		});

	}

	private void isConditions() {

		String account = et_name.getText().toString().trim();
		String password = et_pwd.getText().toString().trim();
		String str_telephone = et_telephone.getText().toString().trim();
		String passwordsure = et_pwd_sure.getText().toString().trim();
		if (account.equals("")) {
			Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
			return;
		}else if(account.length()<6){
			Toast.makeText(this, "请输入账号为6位或以上字母、数字或组合", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (!Util.getInstance(mContext).isMobileNO(str_telephone)) {
			Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
			return;
		} 
		else if (password.equals("")) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}else if(password.length()<8){
			Toast.makeText(this, "请输入密码为8位或以上字母、数字或组合", Toast.LENGTH_SHORT).show();
			return;
		}
		else if(passwordsure.equals("")){
			Toast.makeText(this, "请确定密码", Toast.LENGTH_SHORT).show();
			return;
		}else if(!password.equals(passwordsure)){
			Toast.makeText(this, "两次密码输入不匹配", Toast.LENGTH_SHORT).show();
			et_pwd.setText("");
			et_pwd_sure.setText("");
			return;
		}

		HashMap<String, String> params = new HashMap<String, String>();
		//		String pw = MD5Util.string2MD5(password).toUpperCase();
		params.put("username", account);
		params.put("password", password);
		params.put("mobilePhone", str_telephone);

		showProgressDialog("注册中,请稍后...");

		RequestAncy<MessageJson> requesAncy = 
				new RequestAncy<MessageJson>(mContext, Config.REGISTER_URL, params, false, MessageJson.class, new GetResultInterface<MessageJson>() {

					@Override
					public void onErrorResponse(int errorCode) {
						doErrorResult(errorCode);
					}

					@Override
					public void onSuccessResponse(MessageJson response) {

						if(response.getCode() != 200){
							closeProgressDialog();
							Toast.makeText(mContext, response.getMessage()+", register failure", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(mContext, response.getMessage(), Toast.LENGTH_SHORT).show();

							Intent intent = new Intent(mContext, Login2Activity.class);
							startActivity(intent);
							finish();
						}

					}
				});

		requesAncy.execute("");
	}

}
