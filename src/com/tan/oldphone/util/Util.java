package com.tan.oldphone.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class Util {
	public static Util mUtil = null;
	private Context mContext = null;

	public static Util getInstance(Context mContext) {
		if (mUtil == null) {
			mUtil = new Util(mContext);
		}
		return mUtil;
	}

	private Util(Context mContext) {
		this.mContext = mContext;

	}

	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public boolean GetIsNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean status = true;
		if (cm.getActiveNetworkInfo() != null) {
			if (!cm.getActiveNetworkInfo().isAvailable()) {
				status = false;
			} else {
				status = true;
			}
		} else {
			status = false;
		}
		return status;
	}

	public String getImei() {
		String imei;
		TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();
		if (imei == null || imei.equals("")) {
			imei = getLocalMacAddress();
		}
		return imei;
	}

	public String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	public Point getWindowPixels() {
		Point mPoint = new Point();
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		WindowManager manager = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(mDisplayMetrics);
		mPoint.x = mDisplayMetrics.widthPixels;
		mPoint.y = mDisplayMetrics.heightPixels;
		return mPoint;
	}

	public List<String> MapSort(Map<String, String> map) {
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		List<String> list = new ArrayList<String>();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			list.add(entry.getKey());
		}
		ComparatorUser comparator = new ComparatorUser();
		Collections.sort(list, comparator);
		return list;
	}

	public class ComparatorUser implements Comparator {

		public int compare(Object arg0, Object arg1) {
			return Integer.parseInt((String) arg0) - Integer.parseInt((String) arg1);
		}
	}

	public Bitmap getSizeBitmap(InputStream inStream, int width, int height, Config config) throws Exception {
		Bitmap bitmap = getBitmapFromBytes(inStream);
		if (bitmap == null) {
			return null;
		}
		Bitmap bit = Bitmap.createScaledBitmap(bitmap, width, height, true);
		// bitmap.recycle();
		return bit;
	}

	/**
	 * Byte to bitmap
	 * 
	 * @param bytes
	 * @param opts
	 * @return
	 */
	public Bitmap getBitmapFromBytes(InputStream inStream) throws Exception {
		byte[] bytes = readInputStream(inStream);
		Options mOptions = new BitmapFactory.Options();
		mOptions.inJustDecodeBounds = true; // 确保图片不加载到内存
		mOptions.inDither = false; /* 不进行图片抖动处理 */
		mOptions.inPreferredConfig = null; /* 设置让解码器以最佳方式解码 */
		/* 下面两个字段需要组合使用 */
		mOptions.inPurgeable = true;
		mOptions.inInputShareable = true;
		mOptions.inJustDecodeBounds = false;
		int heightRatio = (int) Math.ceil(mOptions.outHeight / (float) getWindowPixels().x);
		int widthRatio = (int) Math.ceil(mOptions.outWidth / (float) getWindowPixels().y);

		if (heightRatio > 1 && widthRatio > 1) {
			int ii = heightRatio > widthRatio ? heightRatio : widthRatio;
			mOptions.inSampleSize = ii;
		}
		if (bytes != null) {
			if (mOptions != null) {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, mOptions);
			} else {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			}
		}
		return null;
	}

	/**
	 * InputStream to byte
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public byte[] readInputStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}

		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();

		return data;
	}

	// 获取当前程序的版本号
	public String getVersionName(Context mContext) {

		PackageInfo packInfo = null;
		try {
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			packInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packInfo.versionName;
	}

	// //获取版本号(内部识别号)
	public int getVersionCode(Context context) {
		try {
			PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public void installAPK(Context context, String path) {
		File file = new File(path);
		if (!file.exists())
			return;
		// 通过Intent安装APK文件
		Intent intent = new Intent(Intent.ACTION_VIEW);
		ILog.d("Util", "apk path = " + file.getPath());

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
		context.startActivity(intent);
		// 如果不加上这句的话在apk安装完成之后点击单开会崩溃
		// android.os.Process.killProcess(android.os.Process.myPid());

	}

	// 检测是否需要升级
	public static boolean isNeedUpdate(Context context, String newVersion) {
		boolean update = false;
		if(newVersion == null || newVersion.equals("")){
			return false;
		}
		String[] newversions = newVersion.split("\\.");
		if(newversions.length<=2){
			return false;
		}
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			String[] currentversions = packageInfo.versionName.split("\\.");
			
			if (Integer.parseInt(newversions[0]) > Integer.parseInt(currentversions[0])) {
				update = true;
			} else if (Integer.parseInt(newversions[0]) == Integer.parseInt(currentversions[0])) {
				if (Integer.parseInt(newversions[1]) > Integer.parseInt(currentversions[1])) {
					update = true;
				} else if (Integer.parseInt(newversions[1]) == Integer.parseInt(currentversions[1])) {
					if (Integer.parseInt(newversions[2]) > Integer.parseInt(currentversions[2])) {
						update = true;
					} else {
						update = false;
					}
				} else {
					update = false;
				}
			} else {
				update = false;
			}

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return update;
	}

	public void uninstallAPK(Context context) {
		Uri packageURI = Uri.parse("package:com.example.updateversion");
		Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
		context.startActivity(intent);
	}

	public boolean comparatorVersion(String[] oldVersion, String[] newVersion, int index) {
		if (index > oldVersion.length || index > newVersion.length) {
			return false;
		}
		if (Integer.parseInt(oldVersion[index]) > Integer.parseInt(newVersion[index])) {
			return false;
		} else if (Integer.parseInt(oldVersion[index]) == Integer.parseInt(newVersion[index])) {
			index++;
			comparatorVersion(oldVersion, newVersion, index);
		} else if (Integer.parseInt(oldVersion[index]) < Integer.parseInt(newVersion[0])) {
			return true;
		}
		return false;
	}

	public static int dp2px(Context context,float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}

	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 
	
	public static Bitmap decodeFile(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 400;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public static void saveFile(String path, String fileName, Bitmap bitmap, String type) {

		try {
			File dirFile = new File(path);
			File saveimg = new File(path + fileName);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveimg));
			if (bitmap != null && !bitmap.isRecycled()) {
				if ("png".equals(type)) {
					bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
				} else {
					bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
				}
			}
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1).toLowerCase(Locale.getDefault());
	}

	public static String getImageType(String url) {
		int index = url.lastIndexOf(".");
		return url.substring(index + 1);
	}

	public static String getSDCardPath(Context context, String uniqueName) {
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? Environment.getExternalStorageDirectory()
				.getAbsolutePath() : context.getCacheDir().getAbsolutePath();

		return cachePath + File.separator + uniqueName;
	}
	
	public static boolean isExistSDCard() {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	public static String getCachePath() {
		if (isExistSDCard()) {
			return Environment.getExternalStorageDirectory() + "/kunpeng/CutPics/";
		} else {
			return "/data/kunpeng/CutPics/";
		}
	}
	
}
