package com.tan.oldphone.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tan.oldphone.R;
import com.tan.oldphone.adapter.MyGallaryAdapter;
import com.tan.oldphone.common.Config;
import com.tan.oldphone.http.RequestAncy;
import com.tan.oldphone.interf.GetResultInterface;
import com.tan.oldphone.interf.MqttPublishImp;
import com.tan.oldphone.model.ContactContent;
import com.tan.oldphone.model.ContactJson;
import com.tan.oldphone.model.ContactPublishData;
import com.tan.oldphone.model.ContactPublishJson;
import com.tan.oldphone.mview.MyGallery;
import com.tan.oldphone.util.PhoneUtil;
import com.tan.oldphone.util.SharedManager;

public class Main2Activity extends BindSrvcBaseActivity {

	private boolean isBind = false;

	long bootNumber;

	protected static final String TAG = "Main2Activity";
	private MyGallery gallery;
	private ImageSwitcher switcher;
	private int downX,upX; 

	LinearLayout llayout_name;
	private TextView tvName;
	private ImageView ivDial;
	private TextView tvShowInfo;

	public List<ContactContent> contents = new ArrayList<ContactContent>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		final String channelInfo = SharedManager.getInstance(mContext).getChannelInfo();
		System.out.println("Main2Activity,  onCreate, channelInfo: " + channelInfo );

		this.setBindCallback(new IBindCallback() {
			@Override
			public void bindSuccess() {

				if(mottActivSlave != null ){
					

					System.out.println("Main2Activity, onCreate, mottActivSlave:" +mottActivSlave.toString() );
					
					if(mottActivSlave.hasConnMqtt()) {
						System.out.println("mqtt hasConnMqtt。。。");
						return;
					}
					
					System.out.println("to subscrib...");
					mottActivSlave.connAndSubscribe(channelInfo);

					mottActivSlave.setOnMqttPublishImp(new MqttPublishImp() {

						@Override
						public void onMQTTPublish(String result) {
							
							System.out.println("Main2Activity, setOnMqttPublishImp: " + result );
							
							ContactPublishJson cpJson = new Gson().fromJson(result, ContactPublishJson.class);
							ContactPublishData cpData = cpJson.getData();
							long id = cpData.getId();
							
							System.out.println("=====before,  contents.size:"+contents.size() +", id:"+id+", curID:"+curID);
							
							if(cpJson.getAction().equals("action.contacts.delete")){
								
								for(int i=0; i<contents.size(); i++ ){
									ContactContent cc = contents.get(i);
									if( id == cc.getId() ){
										contents.remove(cc);
										curID--;
									}
								}
								
							}else if(cpJson.getAction().equals("action.contacts.add") ){

								ContactContent cc = new ContactContent(cpData.getPhoneNumber(), 
										cpData.getName(), cpData.getId(), /*Config.PRE_PIC_URL+*/cpData.getPicture() );
								contents.add(cc);
								curID++;
								
							}else if(cpJson.getAction().equals("action.contacts.update")){
								
								for(int i=0; i<contents.size(); i++ ){
									
									ContactContent cc = contents.get(i);
									if( id == cc.getId() ){
										System.out.println( "before: "+contents.get(i).toString() );
										
										contents.get(i).setId(cpData.getId());
										contents.get(i).setName(cpData.getName());
										contents.get(i).setPhoneNumber(cpData.getPhoneNumber());
										contents.get(i).setPicture(/*Config.PRE_PIC_URL+*/cpData.getPicture());
										
										System.out.println( "after: "+contents.get(i).toString() );
									}
								}
								
							}
							
							System.out.println("=====after,  contents.size:"+contents.size() +", id:"+id+", curID:"+curID);
								

							Message message = handler.obtainMessage();
							message.what = Config.HANDLER_REFRESH_MAIN_UI;
							handler.sendMessage(message);
							
							
						}
					});

				}else{
					System.out.println("mottActivSlave is null...");
				}
			}

			@Override
			public void bindFail() {
				System.out.println("服务绑定失败");
			}
		});

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main2);

		bootNumber = SharedManager.getInstance(mContext).getBootNumber();
		System.out.println("boot number: "+bootNumber);
		SharedManager.getInstance(mContext).setBootNumber(++bootNumber);

		initView();
		showContactData();

	}

	private void showContactData() {


		//		if( bootNumber <= 0 ){    //request from net.
		setNetData();
		//		}else{                   //get data from db.
		//			setDBData();
		//		}

	}

	private void setDBData() {

	}
	
//	boolean isMainViewVisible = true;
//	private void setMainViewVisible(){
//		int viewInv;
//            if(isMainViewVisible){
//            	viewInv = View.VISIBLE;
//            }else{
//            	viewInv = View.INVISIBLE;
//            }
//            
//            llayout_name.setVisibility(viewInv);
//			gallery.setVisibility(viewInv);
//			switcher.setVisibility(viewInv);
//			tvName.setVisibility(viewInv);
//			ivDial.setVisibility(viewInv);
//	}

	List<ContactContent>  contactContentList ;
	
	private void setNetData() {
		System.out.println("Main2Activity, setNetData...");
		RequestAncy<ContactJson> requestAncy = new RequestAncy<ContactJson>(mContext, Config.GETCONTACTLIST_URL, null, true, ContactJson.class, new GetResultInterface<ContactJson>() {

			@Override
			public void onSuccessResponse(ContactJson response) {
				if( response.getCode() != 200 ){
					Toast.makeText(mContext, response.getMessage()+", get contacts info failure", Toast.LENGTH_SHORT).show();
				}else{

					contents = response.getData().getContent();
					
					gallery.setAdapter(new MyGallaryAdapter(mContext, contents, Main2Activity.this.options, Main2Activity.this.imageLoader));
					
					if(contents.size() <= 0){
//						Toast.makeText(Main2Activity.this, "请去后台添加数据", 2000).show();
//						isMainViewVisible = false;
//						setMainViewVisible();
						showUI(Config.MAIN_UI_SHOW_NO_DATA);
						return;
					}
					
					showUI(Config.MAIN_UI_SHOW_DATA);

					

					Timer timer = new Timer();
					timer.schedule(task, 5000, 5000);

					if(contents.size()>2){
						curID = 2;
					}else{
						curID = 0;
					}
					gallery.setSelection(curID);
					setImageSwitcherData(curID);
				}
			}

			@Override
			public void onErrorResponse(int errorCode) {
				doErrorResult(errorCode);
			}

		});
		requestAncy.execute("");
	}
	
	private void showUI(int state){
		switch(state){
		case Config.MAIN_UI_SHOW_NO_DATA:
			gallery.setVisibility(View.INVISIBLE);
			switcher.setVisibility(View.INVISIBLE);
			llayout_name.setVisibility(View.INVISIBLE);
			tvName.setVisibility(View.INVISIBLE);
			ivDial.setVisibility(View.INVISIBLE);
			tvShowInfo.setVisibility(View.VISIBLE);
			break;
		case Config.MAIN_UI_LOAD:
			gallery.setVisibility(View.INVISIBLE);
			switcher.setVisibility(View.INVISIBLE);
			llayout_name.setVisibility(View.INVISIBLE);
			tvName.setVisibility(View.INVISIBLE);
			ivDial.setVisibility(View.INVISIBLE);
			tvShowInfo.setVisibility(View.INVISIBLE);
			break;
		case Config.MAIN_UI_SHOW_DATA:
			gallery.setVisibility(View.VISIBLE);
			switcher.setVisibility(View.VISIBLE);
			llayout_name.setVisibility(View.VISIBLE);
			tvName.setVisibility(View.VISIBLE);
			ivDial.setVisibility(View.VISIBLE);
			tvShowInfo.setVisibility(View.INVISIBLE);
			break;
		}
	}
	
	

	private void initView(){

		gallery = (MyGallery) findViewById(R.id.gallery);
		//		gallery.setDataLength(imageIds.size());
		// 获取显示图片的ImageSwitcher对象
		switcher = (ImageSwitcher)findViewById(R.id.switcher);
		llayout_name = (LinearLayout)findViewById(R.id.llayout_name);
		tvName = (TextView)findViewById(R.id.tvName);
		ivDial = (ImageView)findViewById(R.id.ivDial);
		tvShowInfo = (TextView)findViewById(R.id.tvShowInfo);
		
		
		
		ivDial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String pn = contents.get(curID).getPhoneNumber();
				PhoneUtil.callPhone(Main2Activity.this, pn);

			}
		});

		// 为ImageSwitcher对象设置ViewFactory对象
		switcher.setFactory(new ViewFactory()
		{
			@Override
			public View makeView()
			{
				ImageView imageView = new ImageView(Main2Activity.this);
				imageView.setBackgroundColor(0xff0000);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
						android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
				return imageView;
			}
		});

		switcher.setOnTouchListener(new OnTouchListener(){   
			/*  
			 * 在ImageSwitcher控件上滑动可以切换图片  
			 */  

			@Override
			public boolean onTouch(View v, MotionEvent event) {   
				if(event.getAction()==MotionEvent.ACTION_DOWN)   
				{   
					downX=(int) event.getX();//取得按下时的坐标   
					return true;   
				}   
				else if(event.getAction()==MotionEvent.ACTION_UP)   
				{   
					upX=(int) event.getX();//取得松开时的坐标   
					int index=0;   
					if(upX-downX>50)//从左拖到右，即看前一张   
					{   
						//如果是第一，则去到尾部   
						if(gallery.getSelectedItemPosition()==0)   
							index=gallery.getCount()-1;   
						else  
							index=gallery.getSelectedItemPosition()-1;   
					}   
					else if(downX-upX>50)//从右拖到左，即看后一张   
					{   
						//如果是最后，则去到第一   
						if(gallery.getSelectedItemPosition()==(gallery.getCount()-1))   
							index=0;   
						else  
							index=gallery.getSelectedItemPosition()+1;   
					}   
					//改变gallery图片所选，自动触发ImageSwitcher的setOnItemSelectedListener   
					gallery.setSelection(index, true);   
					return true;   
				}   
				return false;   
			}   

		});   

		// 设置图片更换的动画效果
		switcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		switcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));


		gallery.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			// 当Gallery选中项发生改变时触发该方法
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{

				Log.d(TAG, "MyGallery,getView position:"+position);

				setImageSwitcherData(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});	
		
		showUI(Config.MAIN_UI_LOAD);

	}

	private int curID = 0;
	private void setImageSwitcherData(int position){

		if(contents.size() <= 0 ) {  //没有任何数据时
			return;
		}

		//		URL picUrl = new URL(getIntent().getExtras().getString("map_url"));
		//		Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream()); 
		//		mapIMG.setImageBitmap(pngBM);

		this.tvName.setText(contents.get(position).getName());
		this.imageLoader.loadImage(contents.get(position).getPicture(), this.options, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				// TODO Auto-generated method stub
				Drawable drawable = new BitmapDrawable(loadedImage);
				switcher.setImageDrawable(drawable);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub

			}
		});

		curID = position;
	}

	private int index = 0;
	/**
	 * 定时器，实现自动播放
	 */
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 2;
			index = gallery.getSelectedItemPosition();
			index++;
			handler.sendMessage(message);
		}
	};

	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				if(index>= contents.size()) index=0;
				gallery.setSelection(index);
				break;
			case Config.HANDLER_REFRESH_MAIN_UI:	
				if(contents.size() <= 0){
					showUI(Config.MAIN_UI_SHOW_NO_DATA);
					return;
				}else if(contents.size() == 1){
					tvName.setText( contents.get(0).getName() );
				}
				System.out.println("refresh...");
				showUI(Config.MAIN_UI_SHOW_DATA);
				((MyGallaryAdapter)gallery.getAdapter()).notifyDataSetChanged();  //如果要刷新的数据源改变了就调用notifyDataSetChanged （）
//			((MyGallaryAdapter)gallery.getAdapter()).notifyDataSetInvalidated();  //如果那个数据源失效了之后就 调用notifyDataSetInvalidated（）
//			gallery.invalidate();
//			gallery.postInvalidate();
				break;
			default:
				break;
			}
		}
	};

	

}
