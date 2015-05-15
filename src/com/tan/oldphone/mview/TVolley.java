
package com.tan.oldphone.mview;

import java.net.URLEncoder;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.tan.oldphone.net.cache.BitmapLruCache;
import com.tan.oldphone.net.cache.ImageCacheManager;
import com.tan.oldphone.net.cache.ImageCacheManager.CacheType;
import com.tan.oldphone.net.cache.RequestManager;
public class TVolley {
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided
	private static CacheType TCACHETYPE =CacheType.DISK;
	private TVolley() {
		// no instances
	}

	public static void init(Context context) {
		RequestManager.init(context);
		mRequestQueue = RequestManager.getRequestQueue();

		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
		.getMemoryClass();
		// Use 1/8th of the available memory for this memory cache.
		int cacheSize = 1024 * 1024 * memClass / 8;
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
		//  mImageLoader = new ImageLoader(mRequestQueue, new BitmapDiskImageCache(context.getCacheDir(),cacheSize));
		//        mImageLoader = new ImageLoader(mRequestQueue, new ImageDiskLruCache(context.getCacheDir(),cacheSize));

		// createImageCache(context);
		//        mImageLoader =  ImageCacheManager.getInstance().getImageLoader();
	}

	public static String encoderUrl(String url)
	{
		try
		{
			int index = url.lastIndexOf('/');
			if (index < 0)
			{
				return URLEncoder.encode(url, "UTF-8");
			}
			String head = url.substring(0, index + 1);
			return head + URLEncoder.encode(url.substring(index + 1), "UTF-8");
		}
		catch (Exception e)
		{
		}
		return null;
	}


	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}


	/**
	 * Returns instance of ImageLoader initialized with {@see FakeImageCache} which effectively means
	 * that no memory caching is used. This is useful for images that you know that will be show
	 * only once.
	 * 
	 * @return
	 */
	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}
	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.  
	 */
	private static void createImageCache(Context context){
		ImageCacheManager.getInstance().init(context,
				context.getPackageCodePath()
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, TCACHETYPE);
	}
}
