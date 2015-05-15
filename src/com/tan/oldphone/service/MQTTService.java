//package com.tan.oldphone.service;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.util.Log;
//
//public class MQTTService extends Service {
//	
//	private String host = "tcp://10.0.2.2:61613";   //真机IP地址要改
//	private String userName = "admin";
//	private String passWord = "password";
//
//	private Handler handler;
//
//	private MqttClient client;
//
//	private String myTopic = "test/topic";
//
//	private MqttConnectOptions options;
//
//	private ScheduledExecutorService scheduler;
//	
//	private Intent intent = new Intent("com.tan.communication.RECEIVER");
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		return null;
//	}
//
//	@Override
//	public void onCreate() {
//		// TODO Auto-generated method stub
//		super.onCreate();
//		Log.d("recycleLife", "onCreate...");
//	}
//	
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		// TODO Auto-generated method stub
//		Log.d("recycleLife", "onStartCommand...");
//		return super.onStartCommand(intent, flags, startId);
//	}
//
//	@Override
//	public void onStart(Intent intent, int startId) {
//		// TODO Auto-generated method stub
//		super.onStart(intent, startId);
//		Log.d("recycleLife", "onStart...");
//		initMQTT();
//		startReconnect();
//	}
//
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		Log.d("recycleLife","onDestroy...不允许关闭service");
//        Intent intent = new Intent();
//        intent.setAction("com.tan.lodphone.service.mqtt");
//        startService(intent);
//	}
//
//	private void initMQTT() {
//		try {
//			//host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
//			client = new MqttClient(host, "test",
//					new MemoryPersistence());
//			//MQTT的连接设置
//			options = new MqttConnectOptions();
//			//设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
//			options.setCleanSession(true);
//			//设置连接的用户名
//			options.setUserName(userName);
//			//设置连接的密码
//			options.setPassword(passWord.toCharArray());
//			// 设置超时时间 单位为秒
//			options.setConnectionTimeout(10);
//			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
//			options.setKeepAliveInterval(20);
//			//设置回调
//			client.setCallback(new MqttCallback() {
//
//				@Override
//				public void connectionLost(Throwable cause) {
//					//连接丢失后，一般在这里面进行重连
//					System.out.println("connectionLost----------");
//				}
//
//				@Override
//				public void deliveryComplete(IMqttDeliveryToken token) {
//					//publish后会执行到这里
//					System.out.println("deliveryComplete---------"
//							+ token.isComplete());
//				}
//
//				@Override
//				public void messageArrived(String topicName, MqttMessage message)
//				throws Exception {
//					//subscribe后得到的消息会执行到这里面
//					System.out.println("messageArrived----------");
////					Message msg = new Message();
////					msg.what = 1;
////					msg.obj = topicName+"---"+message.toString();
////					handler.sendMessage(msg);
//					
//					//发送Action为com.example.communication.RECEIVER的广播
//					intent.putExtra("service_msg", topicName+"---"+message.toString() );
////					intent.putExtra("", value);
//					sendBroadcast(intent);
//					
//				}
//			});
//			//				connect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void connect() {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					client.connect(options);
////					Message msg = new Message();
////					msg.what = 2;
////					handler.sendMessage(msg);
//				} catch (Exception e) {
//					e.printStackTrace();
////					Message msg = new Message();
////					msg.what = 3;
////					handler.sendMessage(msg);
//				}
//			}
//		}).start();
//	}
//	
//	private void startReconnect() {
//		scheduler = Executors.newSingleThreadScheduledExecutor();
//		scheduler.scheduleAtFixedRate(new Runnable() {
//
//			@Override
//			public void run() {
//				if(!client.isConnected()) {
//					connect();
//				}
//			}
//		}, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
//	}
//
//}
