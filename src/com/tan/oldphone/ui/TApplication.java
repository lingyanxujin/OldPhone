package com.tan.oldphone.ui;

import com.tan.oldphone.mview.TVolley;
import android.app.Application;
import android.content.Context;

		
public class TApplication extends Application {
    private static Context applicationContext;
	@Override
	public void onCreate() {
		applicationContext = this.getApplicationContext();
		init();
		super.onCreate();
		
	}
	private void init() {
		TVolley.init(this);
		//callDialog = new CallDialog();
	}
	
	public static Context getContext() {
    	return applicationContext;
    }
	
}
