package com.tan.oldphone.net.cache;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.tan.oldphone.util.BitmapUtil;

public class ImageDiskLruCache  implements ImageCache {
	private final String TAG = this.getClass().getSimpleName();
	int maxCacheSizeInBytes=1024*1024*20;
	File rootDirectory;
	public ImageDiskLruCache(File rootDirectory, int maxCacheSizeInBytes) {
		this.maxCacheSizeInBytes = maxCacheSizeInBytes;
		this.rootDirectory = rootDirectory;
		init();
	}

	void init(){
		mLruCache = new TImageLruCache ();
		mDiskImageCache  = new TDiskImageCache(rootDirectory, maxCacheSizeInBytes);
	}
	TImageLruCache  mLruCache ;
	TDiskImageCache mDiskImageCache;
	static class TImageLruCache extends LruCache<String, Bitmap> implements ImageCache{

		private final String TAG = this.getClass().getSimpleName();
		public static int getDefaultLruCacheSize() {
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
			final int cacheSize = maxMemory / 8;
			return cacheSize;
		}

		public TImageLruCache() {
			this(getDefaultLruCacheSize());
		}
		public TImageLruCache(int maxSize) {
			super(maxSize);
		}

		@Override
		protected int sizeOf(String key, Bitmap value) {
			return value.getRowBytes() * value.getHeight();
		}

		@Override
		public Bitmap getBitmap(String url) {
			Log.v(TAG, "Retrieved item from Mem Cache");
			return get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			Log.v(TAG, "Added item to Mem Cache");
			put(url, bitmap);
		}
	}
	static  class TDiskImageCache extends DiskBasedCache implements ImageCache {
		private final String TAG = this.getClass().getSimpleName();
		public TDiskImageCache(File rootDirectory, int maxCacheSizeInBytes) {
			super(rootDirectory, maxCacheSizeInBytes);
		}

		public TDiskImageCache(File cacheDir) {
			super(cacheDir);
		}

		@Override
		public Bitmap getBitmap(String url) {
			final Entry requestedItem = get(url);

			if (requestedItem == null)
				return null;
			try {

				Bitmap bitmap = BitmapFactory.decodeByteArray(requestedItem.data, 0, requestedItem.data.length);
				//Log.d("w-------------------w2="+bitmap.getWidth()+";;h="+bitmap.getHeight());
				return bitmap;
			} catch (OutOfMemoryError e) {
				Log.e(TAG, "x--------------getBitmap##OutOfMemoryError#url="+url);
			}
			return null;

		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {

			final Entry entry = new Entry();

			/*			//Down size the bitmap.If not done, OutofMemoryError occurs while decoding large bitmaps.
					// If w & h is set during image request ( using ImageLoader ) then this is not required.
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Bitmap downSized = BitmapUtil.downSizeBitmap(bitmap, 50);

				downSized.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] data = baos.toByteArray();

				System.out.println("####### Size of bitmap is ######### "+url+" : "+data.length);
		        entry.data = data ; */
			//Log.d("w-------------------w="+bitmap.getWidth()+";;h="+bitmap.getHeight());
			entry.data = BitmapUtil.convertBitmapToBytes(bitmap) ;
			put(url, entry);
		}
	}
	@Override
	public Bitmap getBitmap(String url) {
		Bitmap data = mLruCache.getBitmap(url);
		if (data == null) {
			data = mDiskImageCache.getBitmap(url);
			if (data == null)
				return null;
			if (data != null) {
				mLruCache.putBitmap(url, data);
			}

		}
		return data;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mLruCache.putBitmap(url, bitmap);

		mDiskImageCache.putBitmap(url, bitmap);
	}
}
