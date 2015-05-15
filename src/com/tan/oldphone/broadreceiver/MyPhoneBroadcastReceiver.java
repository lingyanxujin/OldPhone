package com.tan.oldphone.broadreceiver;

import com.android.internal.telephony.ITelephony;
import com.tan.oldphone.ui.MyPhone;
import com.tan.oldphone.util.PhoneUtils;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyPhoneBroadcastReceiver extends BroadcastReceiver {

	private final static String TAG = MyPhone.TAG;
	
	TelephonyManager telMgr;  
	
//    private static final String TAG = "message";
    private static boolean mIncomingFlag = false;
    private static String mIncomingNumber = null;
    Context mcontext;

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("onReceive...");
		String action = intent.getAction();
		Log.i(TAG, "[Broadcast]"+action);
		
		this.mcontext = context;
		
        // 如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            mIncomingFlag = false;
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:" + phoneNumber);
            
        } else {
            // 如果是来电
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            switch (tManager.getCallState()) {
            
            case TelephonyManager.CALL_STATE_RINGING:
                mIncomingNumber = intent.getStringExtra("incoming_number");
                Log.i(TAG, "RINGING :" + mIncomingNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (mIncomingFlag) {
                    Log.i(TAG, "incoming ACCEPT :" + mIncomingNumber);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (mIncomingFlag) {
                    Log.i(TAG, "incoming IDLE");
                }
                break;
            }
        }

		//呼入电话
		if(action.equals(MyPhone.B_PHONE_STATE)){
			Log.i(TAG, "[Broadcast]PHONE_STATE");
			doReceivePhone(context,intent);
		}        
		
		telMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        switch (telMgr.getCallState()) {
        case TelephonyManager.CALL_STATE_RINGING:
        	String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER); 
        	Log.v(TAG,"number:"+number);
/*        	if (!getPhoneNum(context).contains(number)) {
        		SharedPreferences phonenumSP = context.getSharedPreferences("in_phone_num", Context.MODE_PRIVATE);
        		Editor editor = phonenumSP.edit();
        		editor.putString(number,number);
        		editor.commit();
        		endCall();
        	}*/
        	doReceivePhone(context,intent);
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:                               
            break;
        case TelephonyManager.CALL_STATE_IDLE:                               
            break;
    }
        
        
	}

	/**
	 * 处理电话广播.
	 * @param context
	 * @param intent
	 */
	public void doReceivePhone(Context context, Intent intent) {
		
		
		String phoneNumber = intent.getStringExtra(
				TelephonyManager.EXTRA_INCOMING_NUMBER);
		TelephonyManager telephony = (TelephonyManager)context.getSystemService(
				Context.TELEPHONY_SERVICE);
		int state = telephony.getCallState();

		switch(state){
		case TelephonyManager.CALL_STATE_RINGING:
			Log.i(TAG, "[Broadcast]等待接电话="+phoneNumber);
			try {
				ITelephony iTelephony = PhoneUtils.getITelephony(telephony);
//				iTelephony.answerRingingCall();//自动接通电话
//				iTelephony.endCall();//自动挂断电话
				Toast.makeText(context, phoneNumber+" calling...", 5000).show();
				intent = new Intent();
				intent.setAction("do_phone_result");
				context.sendBroadcast(intent);
			} catch (Exception e) {
				Log.e(TAG, "[Broadcast]Exception="+e.getMessage(), e);
			}
			break;
		case TelephonyManager.CALL_STATE_IDLE:
			Log.i(TAG, "[Broadcast]电话挂断="+phoneNumber);
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			Log.i(TAG, "[Broadcast]通话中="+phoneNumber);
			break;
		}
	}
	
	public void setIncomingCallRingThoughSpeaker(){
		AudioManager audioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);  
		audioManager.setMicrophoneMute(false);          
//		audioManager.setSpeakerphoneOn(true);//使用扬声器外放，即使已经插入耳机  
		audioManager.setSpeakerphoneOn(false);
		
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

		
		
//            ((Activity) mcontext).setVolumeControlStream(AudioManager.STREAM_MUSIC);//控制声音的大小  
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

}
