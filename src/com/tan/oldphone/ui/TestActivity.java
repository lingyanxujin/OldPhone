package com.tan.oldphone.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tan.oldphone.R;
import com.tan.oldphone.broadreceiver.MyPhoneBroadcastReceiver;
import com.tan.oldphone.common.Config;
import com.tan.oldphone.http.CommHttp;
import com.tan.oldphone.http.CommHttpClient;
import com.tan.oldphone.interf.ServiceImp;
import com.tan.oldphone.model.ChannelJson;
import com.tan.oldphone.util.PhoneUtil;
import com.tan.oldphone.util.SharedManager;
import com.tan.oldphone.util.Util;

public class TestActivity extends Activity implements OnClickListener{

	private Button btnTest;
	private Button btnLogin;
	private Button btnRegister;
	private Button btnGetChannel;
	private Button btnGetList;
	private Button btnLogout;
	private Button btnConnMQTTService;
	private Button btnPhone;

	static ServiceImp imp;

	public static void registerImp(ServiceImp im){
		imp = im;
	}

	private TextView tvShow;

	private TestHandler tHandler;
	CommHttpClient commHttpClient;
	private MsgReceiver msgReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		setIncomingCallRingThoughSpeaker();

		initView();
		registerBroadcast();
		tHandler = new TestHandler();
		commHttpClient = new CommHttpClient(TestActivity.this);

		startMQTTActiveService();
	}

	private void registerBroadcast(){
		//动态注册广播接收器
		msgReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.tan.communication.RECEIVER");
		registerReceiver(msgReceiver, intentFilter);
	}

	class TestHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Config.TEST:
				tvShow.setText( (String)msg.obj );
				break;
			case Config.ERROR:
				tvShow.setText("failure!");
				break;
			case Config.LOGIN_SUCCESS:
				tvShow.setText("login success!");
				CommHttp.getChannel(TestActivity.this, tHandler);
				break;
			case Config.GETCHANNEL_SUCCESS:
				//				ChannelJson cj = (ChannelJson)msg.obj;
				//				imp.doconn(cj.getData().getChannel());

				break;
			case Config.GETCONTACTLIST_SUCCESS:
				tvShow.setText("GETCONTACTLIST_SUCCESS!");
				break;

			}
		}
	}	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.btnTest:
			commHttpClient.test(tHandler);
			break;
		case R.id.btnLogin:
			//			commHttpClient.testJson(tHandler);
			//			commHttpClient.login("", null, tHandler);
			CommHttp.Login(TestActivity.this, "abcdefghi", "abcdefghi", tHandler);
			//			SharedManager.getInstance(TestActivity.this).setChannelInfo("fdsfljlksjdflsdjf");
			break;
		case R.id.btnRegister:
			tvShow.setText(SharedManager.getInstance(TestActivity.this).getChannelInfo());
			break;
		case R.id.btnGetChannel:
			commHttpClient.getChannel(tHandler);
			break;
		case R.id.btnGetList:
			CommHttp.getContactList(TestActivity.this, tHandler);
			break;
		case R.id.btnLogout:
			PhoneUtil.callPhone(TestActivity.this, "13714532428");
			break;
		case R.id.btnConnMQTTService:
			startMQTTService();
			break;
		case R.id.btnPhone:	
			//播放一段声音，查看效果          
			MediaPlayer playerSound = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory()+"/kugoumusic/qpd.mp3"));  
			playerSound.start(); 
			
			break;
		}
	}
	

	
	public void setIncomingCallRingThoughSpeaker(){
		AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);  
		audioManager.setMicrophoneMute(false);          
//		audioManager.setSpeakerphoneOn(true);//使用扬声器外放，即使已经插入耳机  
		audioManager.setSpeakerphoneOn(false);
		
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

		
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//控制声音的大小  
		audioManager.setMode(AudioManager.STREAM_MUSIC);  
//		
//		//播放一段声音，查看效果          
//		MediaPlayer playerSound = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory()+"/kugoumusic/qpd.mp3"));  
////		MediaPlayer playerSound = MediaPlayer.create(this, Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));  
//		playerSound.start();  
		
//		AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND) ;
		int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

//		audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
//		audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
	}


	/**
	 * 广播接收器
	 * @author len
	 *
	 */
	class MsgReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			//拿到进度，更新UI
			int progress = intent.getIntExtra("service_msg", 0);
			String info = intent.getStringExtra("service_msg");
			tvShow.setText(info+"");
			//			mProgressBar.setProgress(progress);
		}

	}

	private void initView(){

		btnTest = (Button)findViewById(R.id.btnTest);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnRegister = (Button)findViewById(R.id.btnRegister);
		btnGetChannel = (Button)findViewById(R.id.btnGetChannel);
		btnGetList = (Button)findViewById(R.id.btnGetList);
		btnLogout = (Button)findViewById(R.id.btnLogout);
		btnConnMQTTService = (Button)findViewById(R.id.btnConnMQTTService);
		btnPhone = (Button)findViewById(R.id.btnPhone);

		tvShow = (TextView)findViewById(R.id.etShow);
		tvShow.setTextColor(Color.BLACK);
		tvShow.setTextSize(30);

		btnTest.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		btnGetChannel.setOnClickListener(this);
		btnGetList.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
		btnConnMQTTService.setOnClickListener(this);
		btnPhone.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//注销广播
		unregisterReceiver(msgReceiver);
	}

	private void startMQTTService(){
		//启动服务
		//		Intent intent=new Intent(this,MQTTService.class);


		Intent intent = new Intent();
		intent.setAction("com.tan.lodphone.service.mqtt");

		startService(intent);
	}

	private void startMQTTActiveService(){
		//启动服务
		//		Intent intent=new Intent(this,MQTTService.class);


		Intent intent = new Intent();
		intent.setAction("com.tan.lodphone.service.mqttactiv");

		startService(intent);
	}

}
