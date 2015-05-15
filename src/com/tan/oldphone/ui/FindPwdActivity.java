package com.tan.oldphone.ui;

import com.tan.oldphone.mview.TitleLayout;
import com.tan.oldphone.util.Util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tan.oldphone.R;

public class FindPwdActivity extends BaseActivity implements OnClickListener, OnFocusChangeListener {

	private TitleLayout mTitleLayout = null;
	private Context mContext = null;
	private int pading = 0;
	private EditText mEditNewPwd = null;
	private EditText mEditTelephone = null;
	private ImageView mImageEye = null;
	private Button mBtnChange = null;
	private EditText mEditCode = null;
	private Button mBtnCode = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pwd);
		initView();
	}

	private void initView() {
		mContext = this;
		mTitleLayout = (TitleLayout) findViewById(R.id.layout_title);
		mTitleLayout.setCenter("找回密码", getResources().getColor(R.color.gray_normal));

		pading = (int) getResources().getDimension(R.dimen.regist_editor_pading);

		mEditNewPwd = (EditText) findViewById(R.id.et_new_pwd);
		mEditTelephone = (EditText) findViewById(R.id.et_find_telephoto);
		mEditNewPwd.setOnFocusChangeListener(this);
		mEditTelephone.setOnFocusChangeListener(this);

		mImageEye = (ImageView) findViewById(R.id.image_new_pwd_show);
		mImageEye.setOnClickListener(this);

		mBtnChange = (Button) findViewById(R.id.btn_find_pwd_save);
		mBtnChange.setOnClickListener(this);

		mEditCode = (EditText) findViewById(R.id.et_code);
		mEditCode.setOnFocusChangeListener(new MyOnFocusChangeListener((ImageView) findViewById(R.id.image_code_state)));

		mBtnCode = (Button) findViewById(R.id.btn_code);
		mBtnCode.setOnClickListener(this);

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.arg1 == 0) {
				mBtnCode.setEnabled(true);
				mBtnCode.setBackgroundResource(R.drawable.btn_regist_code_selector);
				mBtnCode.setText("获取验证码");
				return;
			}
			mBtnCode.setText("获取验证码(" + msg.arg1 + ")");
		}
	};

	class MyOnFocusChangeListener implements OnFocusChangeListener {

		private ImageView image;

		public MyOnFocusChangeListener(ImageView image) {
			this.image = image;
		}

		@Override
		public void onFocusChange(View arg0, boolean focused) {
			// TODO Auto-generated method stub
			EditText text = (EditText) arg0;
			if (focused) {
				text.setBackgroundResource(R.drawable.regist_edit_bg_press);
				text.setPadding(pading, pading, pading, pading);
			} else {
				text.setBackgroundResource(R.drawable.regist_edit_bg);
				text.setPadding(pading, pading, pading, pading);
				if (text.getText().toString().length() == 6 && Util.getInstance(mContext).isNumeric(text.getText().toString())) {
					image.setBackgroundResource(R.drawable.regist_correct);
					image.setVisibility(View.VISIBLE);
				} else {
					image.setBackgroundResource(R.drawable.regist_error);
					image.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	private void getFindPwd() {
/*		try {
			String url = URL.FIND_PWD.replace("phone_num", URLEncoder.encode(mEditTelephone.getText().toString().trim(), "UTF-8"))
					.replace("captcha", URLEncoder.encode(mEditCode.getText().toString().trim(), "UTF-8"))
					.replace("new_password", URLEncoder.encode(mEditNewPwd.getText().toString().trim(), "UTF-8"))
					.replace("imei",URLEncoder.encode(Util.getInstance(mContext).getImei(), "UTF-8"));

			RequestAncy mRAncy = new RequestAncy(url, null, false, HttpMethod.POST, mContext, true, new GetResultInterface() {
				@Override
				public void getResult(String result) {
					// TODO Auto-generated method stub
					if (Response.getInstance(mContext).getChangePwdJson(result)) {
						finish();
					}
				}
			});
			mRAncy.execute("0");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	private void ChangePwdClick() {
		if (mEditTelephone.getText().toString().trim().equals("")) {
			Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
			return;
		} else if (!Util.getInstance(mContext).isMobileNO(mEditTelephone.getText().toString().trim())) {
			Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
			return;
		} else if (mEditCode.getText().toString().trim().equals("")) {
			Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
			return;
		} else if (mEditCode.getText().toString().trim().length() != 6
				|| !Util.getInstance(mContext).isNumeric(mEditCode.getText().toString().trim())) {
			Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
			return;
		} else if (mEditNewPwd.getText().toString().trim().equals("")) {
			Toast.makeText(this, "请设置新密码", Toast.LENGTH_SHORT).show();
			return;
		} else if (mEditNewPwd.getText().toString().trim().length() < 6) {
			Toast.makeText(this, "密码长度必须是6-16位字母.数字或组合", Toast.LENGTH_SHORT).show();
			return;
		}
		getFindPwd();
	}

	@Override
	public void onFocusChange(View arg0, boolean focused) {
		// TODO Auto-generated method stub
		EditText text = (EditText) arg0;
		if (focused) {// 失去焦点
			text.setBackgroundResource(R.drawable.regist_edit_bg_press);
			text.setPadding(pading, pading, pading, pading);
		} else {
			text.setBackgroundResource(R.drawable.regist_edit_bg);
			text.setPadding(pading, pading, pading, pading);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.image_new_pwd_show:
			if (arg0.isSelected()) {
				arg0.setSelected(false);
				mEditNewPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				mEditNewPwd.setSelection(mEditNewPwd.getText().toString().length());
			} else {
				arg0.setSelected(true);
				mEditNewPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				mEditNewPwd.setSelection(mEditNewPwd.getText().toString().length());
			}
			break;
		case R.id.btn_find_pwd_save:
			ChangePwdClick();
			break;
		case R.id.btn_code:
			codeClick();
			break;
		default:
			break;
		}
	}

	private void codeClick() {
		if (mEditTelephone.getText().toString().trim().equals("")) {
			Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
			return;
		} else if (!Util.getInstance(mContext).isMobileNO(mEditTelephone.getText().toString().trim())) {
			Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
			return;
		} else {
			getCode();
		}
	}

	private void getCode() {
/*		String url = URL.CODE.replace("captcha_type", "2").replace("phone_num", mEditTelephone.getText().toString().trim())
				.replace("imei", Util.getInstance(mContext).getImei());
		RequestAncy mRequestAncy = new RequestAncy(url, null, false, HttpMethod.GET, mContext, true, new GetResultInterface() {
			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub
				ILog.e("getCode", "===result=====" + result);
				Toast.makeText(mContext, "已向您" + mEditTelephone.getText().toString().trim() + "手机发送短信验证码，若60秒内未收到短信，请点击重新获取.", Toast.LENGTH_SHORT)
						.show();
				mBtnCode.setEnabled(false);
				mBtnCode.setBackgroundResource(R.drawable.regist_code_press);
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						for (int i = 60; i >= 0; i--) {
							try {
								Thread.sleep(1000);
								Message msg = new Message();
								msg.arg1 = i;
								msg.what = 0;
								handler.sendMessage(msg);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});
		mRequestAncy.execute("");*/
	}
}
