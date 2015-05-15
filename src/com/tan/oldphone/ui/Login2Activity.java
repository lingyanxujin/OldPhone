package com.tan.oldphone.ui;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tan.oldphone.R;
import com.tan.oldphone.common.Config;
import com.tan.oldphone.http.MD5Util;
import com.tan.oldphone.http.RequestAncy;
import com.tan.oldphone.interf.GetResultInterface;
import com.tan.oldphone.model.ChannelJson;
import com.tan.oldphone.model.MessageJson;
import com.tan.oldphone.mview.TitleLayout;
import com.tan.oldphone.util.SharedManager;

public class Login2Activity extends BindSrvcBaseActivity implements OnClickListener, OnFocusChangeListener {

	private TitleLayout mTitleLayout = null;
	private Button mBtnLogin = null;
	private TextView mBtnRegist = null;
	private EditText mEditName = null;
	private EditText mEditPwd = null;
	private ImageView mImageTelephone = null;
	private ImageView mImagePwd = null;
	private ImageView mImageUserPhoto = null;
	private ImageView mImageDelete = null;
	private ImageView mImageEye = null;
	private ImageView mImageSave = null;
	private TextView mBtnChangePwd = null;
	private LinearLayout mLinearSave = null;
	
	private boolean isBind = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login2);

		if(mottActivSlave ==null){
			this.setBindCallback(new IBindCallback() {

				@Override
				public void bindSuccess() {
					//showToast("服务绑定成功");
//					initDownTasks();
					System.out.println("Login2Activity服务绑定成功");
					isBind = true;
				}

				@Override
				public void bindFail() {
					System.out.println("服务绑定失败");
					_startService();
					mottActivSlave = null;
					_bindService();
				}
			});
		}else{
//			initDownTasks();
			_startService();
			mottActivSlave = null;  //
			_bindService();
		}
		
		initView();	
	}

    private long firstClick;  
    private long lastClick;  
    // 计算点击的次数  
    private int count; 
	private void initView() {
		mTitleLayout = (TitleLayout) findViewById(R.id.layout_title);
		mTitleLayout.setCenter("用户登陆", getResources().getColor(R.color.gray_normal));		

		mBtnLogin = (Button) findViewById(R.id.btn_login);
		mBtnRegist = (TextView) findViewById(R.id.btn_regist);
		mBtnChangePwd = (TextView) findViewById(R.id.tv_change_pwd);
		mEditName = (EditText) findViewById(R.id.et_telephone);
		mEditPwd = (EditText) findViewById(R.id.et_pwd);
		mBtnLogin.setOnClickListener(this);
		mBtnRegist.setOnClickListener(this);
		mBtnChangePwd.setOnClickListener(this);
		mBtnRegist.setText(Html.fromHtml("<u>" + "马上注册" + "</u>"));
		mBtnChangePwd.setText(Html.fromHtml("<u>" + "忘记密码" + "</u>"));

		mEditName.setOnFocusChangeListener(this);
		mEditPwd.setOnFocusChangeListener(this);

		mImageTelephone = (ImageView) findViewById(R.id.image_telephone);
		mImagePwd = (ImageView) findViewById(R.id.image_pwd);
		mImageUserPhoto = (ImageView) findViewById(R.id.image_user_photo);

		mImageDelete = (ImageView) findViewById(R.id.image_telephone_delete);
		mImageEye = (ImageView) findViewById(R.id.image_pwd_eye);
		mImageSave = (ImageView) findViewById(R.id.image_save);
		mLinearSave = (LinearLayout) findViewById(R.id.linear_save);
//		mImageUserPhoto.setOnClickListener(this);
		mImageDelete.setOnClickListener(this);
		mImageEye.setOnClickListener(this);
		mLinearSave.setOnClickListener(this);

		if(SharedManager.getInstance(mContext).getSave()){
			mEditName.setText(SharedManager.getInstance(mContext).getUserName());
			mEditPwd.setText(SharedManager.getInstance(mContext).getUserPwd());
			mImageSave.setSelected(true);
		}
		

		mImageUserPhoto.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 switch (event.getAction()) {  
			        case MotionEvent.ACTION_DOWN:  
			            // 如果第二次点击 距离第一次点击时间过长 那么将第二次点击看为第一次点击  
			            if (firstClick != 0 && System.currentTimeMillis() - firstClick > 300) {  
			                count = 0;  
			            }  
			            count++;  
			            if (count == 1) {  
			                firstClick = System.currentTimeMillis();  
			            } else if (count == 2) {  
			                lastClick = System.currentTimeMillis();  
			                // 两次点击小于300ms 也就是连续点击  
			                if (lastClick - firstClick < 300) {// 判断是否是执行了双击事件  
			                    System.out.println(">>>>>>>>执行了双击事件"); 
			                    
			        			Intent mIntent = new Intent(Login2Activity.this, SettingActivity.class);
//			        			startActivity(mIntent);
			        			startActivityForResult(mIntent, Config.REQUESTCODE_SETTINGACTIVITY);
			  
			                }  
			            }  
			            break;  
			        case MotionEvent.ACTION_MOVE:  
			            break;  
			        case MotionEvent.ACTION_UP:  
			            break;  
			        }  
			        return true;  
			}
		});
		
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent mIntent = null;
		switch (arg0.getId()) {
		case R.id.btn_login:
			isConditions();
			break;
		case R.id.btn_regist:
			mIntent = new Intent(this, RegisterActivity.class);
			startActivity(mIntent);
			break;
		case R.id.tv_change_pwd:
			mIntent = new Intent(this, FindPwdActivity.class);
			startActivity(mIntent);
			break;
		case R.id.image_telephone_delete:
			if (mEditName.getText().toString().equals("")) {
				return;
			}
			mEditName.setText("");
			break;
		case R.id.image_pwd_eye:
			if(mImageSave.isSelected()){
				return;
			}
			if (arg0.isSelected()) {
				arg0.setSelected(false);
				mEditPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				mEditPwd.setSelection(mEditPwd.getText().toString().length());
			} else {
				arg0.setSelected(true);
				mEditPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				mEditPwd.setSelection(mEditPwd.getText().toString().length());
			}
			break;
		case R.id.linear_save:
			if (mImageSave.isSelected()) {
				mImageSave.setSelected(false);
			} else {
				mImageSave.setSelected(true);
				if (mImageEye.isSelected()) {
					mImageEye.setSelected(false);
					mEditPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					mEditPwd.setSelection(mEditPwd.getText().toString().length());
				}
			}
			break;
		case R.id.image_user_photo:
			mIntent = new Intent(this, SettingActivity.class);
//			startActivity(mIntent);
			startActivityForResult(mIntent, Config.REQUESTCODE_SETTINGACTIVITY);
			break;
		default:
			break;
		}
	}

	private void isConditions() {

		String account = mEditName.getText().toString().trim();
		String password = mEditPwd.getText().toString().trim();
		if (account.equals("")) {
			Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
			return;
		} 
		//		else if (!Util.getInstance(mContext).isMobileNO(account)) {
		//			Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
		//			return;
		//		} 
		else if (password.equals("")) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}

		HashMap<String, String> params = new HashMap<String, String>();
		String pw = MD5Util.string2MD5(password).toUpperCase();
		params.put("username", account);
		params.put("password", pw);

		showProgressDialog("登陆中,请稍后...");

		RequestAncy<MessageJson> requesAncy = 
			new RequestAncy<MessageJson>(Login2Activity.this, Config.LOGIN_URL, params, false, MessageJson.class, new GetResultInterface<MessageJson>() {

				@Override
				public void onErrorResponse(int errorCode) {
					memoryInputtext();
					doErrorResult(errorCode);
				}

				@Override
				public void onSuccessResponse(MessageJson response) {

					memoryInputtext();

					if(response.getCode() != 200){
						closeProgressDialog();
						Toast.makeText(Login2Activity.this, response.getMessage()+", login failure", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(Login2Activity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

						//after 1.login success, 2.get channel, 3.register topic,then 4.jump to  Main2Activity
						toGetChannel();
					}

				}
			});

		requesAncy.execute("");
	}

	private void toGetChannel(){
		RequestAncy<ChannelJson> requesAncy = new RequestAncy<ChannelJson>(Login2Activity.this, Config.GETCHANNEL_URL, null, false, ChannelJson.class, new GetResultInterface<ChannelJson>() {

			@Override
			public void onSuccessResponse(ChannelJson response) {

				closeProgressDialog();

				if(response.getCode() != 200){
					Toast.makeText(Login2Activity.this, response.getMessage()+", getchannel failure", Toast.LENGTH_SHORT).show();					
				}else{
//					Toast.makeText(Login2Activity.this, "getchannel"+response.getMessage(), Toast.LENGTH_SHORT).show();

					String channelInfo = response.getData().getChannel();
					
					SharedManager.getInstance(mContext).setChannelInfo(channelInfo);

					Intent mIntent = new Intent(mContext, Main2Activity.class);
					startActivity(mIntent);
					finish();
				}
			}

			@Override
			public void onErrorResponse(int errorCode) {
				// TODO Auto-generated method stub
				doErrorResult(errorCode);
			}
		});
		requesAncy.execute("");
	}

	private void memoryInputtext(){

		if(mImageSave.isSelected()){
			SharedManager.getInstance(mContext).setSave(true);
			SharedManager.getInstance(mContext).setUserName(mEditName.getText().toString().trim());
			SharedManager.getInstance(mContext).setUserPwd(mEditPwd.getText().toString().trim());
		}else{
			SharedManager.getInstance(mContext).setSave(false);
			SharedManager.getInstance(mContext).setUserName("");
			SharedManager.getInstance(mContext).setUserPwd("");
		}
	}

	@Override
	public void onFocusChange(View arg0, boolean focused) {
		// TODO Auto-generated method stub
		ImageView mImage = null;
		switch (arg0.getId()) {
		case R.id.et_telephone:
			mImage = mImageTelephone;
			break;
		case R.id.et_pwd:
			mImage = mImagePwd;
			break;
		default:
			break;
		}

		if (focused) {
			mImage.setImageResource(R.drawable.linpress);
		} else {
			mImage.setImageResource(R.drawable.lin);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case Config.REQUESTCODE_SETTINGACTIVITY:
			if(resultCode == Config.RESULTCODE_OK){
				System.out.println("has setting and reset initMQTT...");
				System.out.println("MQTT_URL:" + Config.MQTT_URL);
				System.out.println("BASE_URL:" + Config.BASE_URL);
				//					System.out.println("PRE_PIC_URL:" + Config.PRE_PIC_URL);
				System.out.println("MQTT_PORT:" + Config.MQTT_PORT);
				
				mottActivSlave.reInitMQTT();
			}else if(resultCode == Config.RESULTCODE_FAILURE){
				System.out.println("no setting...");
			}
			break;
		}
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && backID == 0) {
//			backID++;
//			Toast.makeText(this, "在按一次退出程序.", Toast.LENGTH_SHORT).show();
//			return true;
//		}
//		
////		if (getIntent() != null && getIntent().getBooleanExtra("isClass", false)) {
////			Intent mIntent = new Intent();
////			mIntent.putExtra("eixt", true);
////			setResult(RESULT_OK, mIntent);
////		}
//
//		return super.onKeyDown(keyCode, event);
//	}

}
