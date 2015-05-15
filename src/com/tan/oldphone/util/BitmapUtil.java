package com.tan.oldphone.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.util.Log;

public class BitmapUtil {

	public static Bitmap downSizeBitmap(Bitmap bitmap,int reqSize)  {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float scaleWidth = ((float) reqSize) / width;
		float scaleHeight = ((float) reqSize) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
		return resizedBitmap;

		/*if(bitmap.getWidth() < reqSize) {
			return bitmap;
		} else {
			return Bitmap.createScaledBitmap(bitmap, reqSize, reqSize, false);
		} */
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public static byte[] convertBitmapToBytes(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
			bitmap.copyPixelsToBuffer(buffer);
			return buffer.array();
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] data = baos.toByteArray();
			return data;
		}
	}
	public static byte[] convertBitmapToBytes2(Bitmap bitmap) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] data = baos.toByteArray();
			return data;
	}
	public static int[] getBitmapWH(Context context,int res){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), res, options);
		int []wh =  {options.outWidth,options.outHeight};
		return wh;
		/* 这里返回的bmp是null *
    	这段代码之后，options.outWidth  和 options.outHeight就是我们想要的宽和高了*/
	}
	public static  void saveMyBitmap(String bitName,Bitmap mBitmap,Context c){
		
		File f = new File(c.getCacheDir()+"/" + bitName + ".png");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Log.d(null,"在保存图片时出错："+e.toString());
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d(null,"在保存图片时出错："+f.getAbsolutePath().toString());
	}
}
