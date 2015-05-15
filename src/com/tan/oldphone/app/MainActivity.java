//package com.tan.oldphone.app;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Environment;
//
//import com.qoen.db.DownloadTask;
//import com.qoen.db.DownloadTaskDao;
//
//import de.greenrobot.dao.async.AsyncOperation;
//import de.greenrobot.dao.async.AsyncOperationListener;
//import de.greenrobot.dao.async.AsyncSession;
//import de.greenrobot.dao.async.AsyncOperation.OperationType;
//
//public class MainActivity extends Activity {
//	int a;
//	private static AsyncSession asyncSession;
//	private  DownloadTaskDao downloadTaskDao; 
//	
//	private List<DownloadTask> tasks = new ArrayList<DownloadTask>();
//	
//	private String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/mygreendao";
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		
//		DataBaseContext dataBaseContext =  new DataBaseContext(this, dataPath);		
//		
//		downloadTaskDao = TApplication.getDaoSession(dataBaseContext).getDownloadTaskDao();
////		downloadTaskDao= TApplication.getDaoSession(this).getDownloadTaskDao();
//		
////		asyncSession= TApplication.getAsynDaoSession(this);
//		asyncSession = TApplication.getAsynDaoSession(dataBaseContext);
//		asyncSession.setListener(asyncOperationListener);//.loadAll(DownloadTask.class);
//		
//		OutputQuery(1);
//		
//		asyncSession.deleteAll(DownloadTask.class);
//		
//		OutputQuery(2);
//		
//		for(int i=0; i<10; i++){
//			DownloadTask task = new DownloadTask("pic_"+i+i+i+".jpg", "32"+i);	
////			DownloadTask task = new DownloadTask(null, "pic_"+i+i+i+".jpg", null, "32"+i);	
//			tasks.add(task);
//		}
//		
//		for(int i=0; i<tasks.size(); i++){
//			System.out.println("向数据库插入数据......");
//			AsyncOperation oper = asyncSession.insert(tasks.get(i), 1001);	
//			System.out.println("insert result:"+oper.isCompleted()+", result:"+oper.getResult()+", failure:"+oper.isFailed() );
//		}
//		
//		OutputQuery(3);
//		
//		asyncSession.deleteAll(DownloadTask.class);
//		OutputQuery(4);
//		
////        new Thread(new Runnable() {
////			
////			@Override
////			public void run() {
////				// TODO Auto-generated method stub
////				
////				List<DownloadTask> tasks = new ArrayList<DownloadTask>();
////				for(int i=0; i<10; i++){
////					DownloadTask task = new DownloadTask("pic_"+i+i+i+".jpg", "32"+i);	
//////					DownloadTask task = new DownloadTask(null, "pic_"+i+i+i+".jpg", null, "32"+i);	
////					tasks.add(task);
////				}
////				
////				for(int i=0; i<tasks.size(); i++){
////					AsyncOperation oper = asyncSession.insert(tasks.get(i), 1001);	
////					System.out.println("insert result:"+oper.isCompleted()+", result:"+oper.getResult()+", failure:"+oper.isFailed() );
////				}
////				
////			}
////		}).start();
//		
//	}
//	
//	public void OutputQuery(int nums){
//		System.out.println("+++++++++++第 " + nums + " 次++++++++++++++");
//		tasks = DaoUtils.queryAllData(downloadTaskDao);
//		
//		if(tasks==null || tasks.size()<=0 )  {
//			System.out.println("数据库为空...");
//			return;
//		}
//		
//		System.out.println("输出数据库内容：");
//		for(int i=0; i<tasks.size(); i++){
//			System.out.println(tasks.get(i).toString());
//		}
//	}	
//	
//	AsyncOperationListener asyncOperationListener = new AsyncOperationListener() {		
//		@Override
//		public void onAsyncOperationCompleted(AsyncOperation arg0) {
//			// TODO Auto-generated method stub
//			OperationType type = arg0.getType();
//			switch(type){
//			case LoadAll:
//				System.out.println("LoadAll...");
//				break;
//			case Delete:
//				System.out.println("Delete...");
//				break;
//			case Insert:
//				System.out.println("Insert...");
//				break;
//			case Update:
//				System.out.println("Update...");
//				break;
//			case QueryList:
//				System.out.println("QueryList...");
//				break;
//			}
//		}
//	};
//	
//
//}
