package com.tan.oldphone.ui;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tan.oldphone.R;
import com.tan.oldphone.common.Config;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

public class BaseActivity extends Activity {
	
	public Context mContext = null;

	private ProgressDialog progressDialog;
    private boolean shouldCloseProgress = false;

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageForEmptyUri(R.drawable.ic_empty)
	.showImageOnFail(R.drawable.ic_error)
	.resetViewBeforeLoading(true)
	.cacheOnDisc(true)
	.imageScaleType(ImageScaleType.EXACTLY)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.displayer(new FadeInBitmapDisplayer(300))
	.build();	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		
		File disCache = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "UniversalImageLoader/Cache");
//		System.out.println("discache path:"+disCache.getAbsolutePath());
//		System.out.println("abs path:"+Environment.getExternalStorageState() );
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
	    .threadPriority(Thread.NORM_PRIORITY - 2)
	    .denyCacheImageMultipleSizesInMemory()
	    .discCacheFileNameGenerator(new Md5FileNameGenerator())
	    .tasksProcessingOrder(QueueProcessingType.LIFO)
//	    .enableLogging() // Not necessary in common
	    .discCache(new UnlimitedDiscCache(disCache))
	    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
	    .build();
		imageLoader.init(config);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.add(Menu.NONE, Menu.FIRST + 1, 5, "设置").setIcon(
		R.drawable.menu_syssettings);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {  
		case Menu.FIRST+1:
			Toast.makeText(mContext, "setting", 2000).show();
			break;
		}
		return false;
	}
	
	protected void showProgressDialog(String content){
		if(progressDialog == null){
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setMessage(content);
//			progressDialog.setMessage("正在加载,请稍后...");
			progressDialog.show();
		}else{
			closeProgressDialog();
			showProgressDialog(content);
		}
		shouldCloseProgress = true;
	}

	protected void closeProgressDialog(){
		if(progressDialog != null){
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			progressDialog = null;
		}
	}
   
	protected void doErrorResult(int errorCode){
		if(shouldCloseProgress){
			closeProgressDialog();
			shouldCloseProgress = false;
		}
		switch(errorCode){
		case Config.UNABLE_CONNECT_SERVER:
			Toast.makeText(mContext, "unable conn server，request failure", Toast.LENGTH_SHORT).show();
			break;
		case Config.UNKONW_EXCEPTION:
			Toast.makeText(mContext, "unkown exception，request failure", Toast.LENGTH_SHORT).show();
			break;
		case Config.NO_NETWORK:
			Toast.makeText(mContext, "no network，request failure", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
}
