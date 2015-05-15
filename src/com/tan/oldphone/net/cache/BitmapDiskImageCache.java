package com.tan.oldphone.net.cache;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.tan.oldphone.util.BitmapUtil;

/*
 * Extends from DisckBasedCache --> Utility from volley toolbox.
 * Also implements ImageCache, so that we can pass this custom implementation
 * to ImageLoader. 
 */
public  class BitmapDiskImageCache extends DiskBasedCache implements ImageCache {

	public BitmapDiskImageCache(File rootDirectory, int maxCacheSizeInBytes) {
		super(rootDirectory, maxCacheSizeInBytes);
	}

	public BitmapDiskImageCache(File cacheDir) {
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