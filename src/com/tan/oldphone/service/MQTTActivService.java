package com.tan.oldphone.service;

import java.net.URISyntaxException;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.tan.oldphone.common.Config;
import com.tan.oldphone.interf.MqttPublishImp;
import com.tan.oldphone.interf.ServiceImp;
import com.tan.oldphone.ui.TestActivity;

public class MQTTActivService extends Service implements ServiceImp{

	private Handler handler;

	private Intent intent = new Intent("com.tan.communication.RECEIVER");

	MqttPublishImp mqttPublishImp;

	String strtopic;

	boolean hasConnMqtt = false;

	private boolean hasSubscribe = false;
	
	private SlaveBinder binder = new SlaveBinder();

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("lifecycle", "onBind..."+this);
		return binder;
	}

	/*******  service  lifecycle  *********/
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("lifecycle", "onCreate..."+this);
		TestActivity.registerImp(this);
		initMQTT();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("lifecycle", "onStartCommand..."+this);
		return super.onStartCommand(intent, flags, startId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d("lifecycle", "onStart..."+this);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("lifecycle", "onUnbind..."+this);
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("lifecycle","onDestroy..."+this);

//		connection = null;
//		mqtt = null;
		
//		Log.d("recycleLife","onDestroy...不允许关闭service");
//		Intent intent = new Intent();
//		intent.setAction("com.tan.lodphone.service.mqtt");
//		startService(intent);
	}
	/*******  service  lifecycle  *********/

	CallbackConnection connection = null;
	MQTT mqtt = null;
	/**
	 * just initting mqtt with url and port, setting listener.
	 */
	private void initMQTT() {

		if(mqtt == null){
			mqtt = new MQTT();
		}else{
			System.out.println("mqtt is not null...");
			connection = null;
		}
		
//		mqtt = new MQTT();
		
		mqtt.setKeepAlive((short) 30);

		try {
			mqtt.setHost(Config.MQTT_URL, Config.MQTT_PORT);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		connection = mqtt.callbackConnection();
		connection.listener(new org.fusesource.mqtt.client.Listener() {
			public void onConnected() {
				Log.d("lifecycle","MQTT>>>>  listener onConnected..."+connection);
				System.out.println("mqtt listener,  onConnected。。。");
			}
			public void onDisconnected() {
				Log.d("lifecycle","MQTT>>>>  listener onDisconnected..."+connection);
				System.out.println("mqtt listener,  onDisconnected。。。");
				System.out.println("mqtt listener,  reconnect。。。");
				hasConnMqtt = false;
				connMQTTServerAndSubscribeTopic(strtopic);
			}
			public void onFailure(Throwable value) {
				Log.d("lifecycle","MQTT>>>>  listener onFailure..."+connection);
				System.out.println("mqtt listener,  onFailure。。。");
				value.printStackTrace();
				System.exit(-2);
			}
			public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
				Log.d("lifecycle","MQTT>>>>  listener onPublish..." + "mqtt: "+mqtt.toString() + ", connection: "+connection.toString() );
//				connMQTTServerAndSubscribeTopic(strtopic);
				System.out.println("mqtt listener,  onPublish。。。");
				System.out.println("mqtt onPublish info: " + msg.utf8().toString());
				mqttPublishImp.onMQTTPublish(msg.utf8().toString());
			}
		});
	}

	/**
	 * conn mqtt server and subscribe topic.
	 * @param topic
	 */
	private void connMQTTServerAndSubscribeTopic(final String topic){

		connection.connect(new Callback<Void>() {
			@Override
			public void onSuccess(Void value) {            	

				Log.d("lifecycle","MQTT>>>>  connect onSuccess..."+connection);
				System.out.println("mqtt conn成功...");      

				strtopic = topic ;
				hasConnMqtt = true;

				Topic[] topics = {new Topic(topic, QoS.AT_LEAST_ONCE)};

				connection.subscribe(topics, new Callback<byte[]>() {

					public void onSuccess(byte[] qoses) {
						
						Log.d("lifecycle","MQTT>>>>  subscribe onSuccess..."+connection);
						
						hasSubscribe = true;
						System.out.println("mqtt 已订阅topic："+topic);
					}

					public void onFailure(Throwable value) {
						
						Log.d("lifecycle","MQTT>>>>  subscribe onFailure..."+connection);

						hasSubscribe  = false;
						System.out.println("mqtt 订阅topic：" + topic + "失败");

						value.printStackTrace();
						System.exit(-2);
					}

				});
			}

			@Override
			public void onFailure(Throwable value) {
				Log.d("lifecycle","MQTT>>>>  connect onFailure..."+this);
				System.out.println("mqtt conn失败...");
				hasConnMqtt = false;
				value.printStackTrace();
				System.exit(-2);
			}

		});

	}

	@Override
	public void doconn(String topic) {
		System.out.println("mqtt doconn, topic: " + topic );
		connMQTTServerAndSubscribeTopic(topic);
	}

	public class SlaveBinder extends Binder{

		public void connAndSubscribe(String topic){
			connMQTTServerAndSubscribeTopic(topic);
		}

		public boolean isSubscribed(){
			return hasSubscribe;
		}

		public void setOnMqttPublishImp(MqttPublishImp imp){
			mqttPublishImp = imp;
		}

		public boolean hasConnMqtt(){
			return hasConnMqtt;
		}

		public void reInitMQTT(){
			initMQTT();
		}

	}

}
