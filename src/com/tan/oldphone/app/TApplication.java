package com.tan.oldphone.app;

import com.tan.oldphone.db.dao.DaoMaster;
import com.tan.oldphone.db.dao.DaoMaster.OpenHelper;
import com.tan.oldphone.db.dao.DaoSession;

import android.app.Application;
import android.content.Context;

import de.greenrobot.dao.async.AsyncSession;

public class TApplication extends Application {
	
//    static {
//		TConstant.DATABASE_NAME = "appdownloadtasks-db";
//	}
	
	private static Context applicationContext;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("TApplication onCreate ...");
		applicationContext = this.getApplicationContext();
		
	}
	
	public final static String DATABASE_NAME = "appdownloadtasks-db";
	
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	/**
	 * 取得DaoMaster
	 *
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context){
		if (daoMaster == null){
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);

			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}
	/**
	 * 取得DaoSession
	 *
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context){
		if (daoSession == null){
			if (daoMaster == null){
				daoMaster = getDaoMaster(context);
			} 
			daoSession = daoMaster.newSession();
			
		}

		return daoSession;
	}
	
	static AsyncSession asyncSession;
	
	public static AsyncSession getAsynDaoSession(Context context){
		if (asyncSession == null){
			if (daoMaster == null){
				daoMaster = getDaoMaster(context);
			} 
			daoSession = daoMaster.newSession();
			//asyncSession = new AsyncSession(daoSession);
			asyncSession = daoSession.startAsyncSession();
		}
		return asyncSession;
	}
	
	
}
