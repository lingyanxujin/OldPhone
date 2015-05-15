/**
 * Copyright 2013 Mani Selvaraj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tan.oldphone.net.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * LruCache: For caches that do not override sizeOf(K, V), this is the maximum number of entries in the cache. 
 * For all other caches, this is the maximum sum of the sizes of the entries in this cache.
 * Ref:http://developer.android.com/reference/android/util/LruCache.html#LruCache(int)
 * 
 * Currently, the cache keeps maximum of 100 Cache entries.
 * @author Mani Selvaraj
 *
 */
public class BitmapLruCache extends LruCache<String,Bitmap> implements ImageCache {
    public BitmapLruCache(int maxSize) {
        super(maxSize);
        //or setLimit(Runtime.getRuntime().maxMemory()/4);
    }
    
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
 
//    @Override
//    public Bitmap getBitmap(String url) {
//    	System.out.println("######## BitmapLruCache GET ######## "+url);
//        return get(url);
//    }
// 
//    @Override
//    public void putBitmap(String url, Bitmap bitmap) {
//    	System.out.println("######## BitmapLruCache PUT ######## "+url);
//        put(url, bitmap);
//    }
    
    @Override
    public Bitmap getBitmap(String url) {
    	
    	Bitmap img = get(url);
//    	Log.d(null,"######## BitmapLruCache GET ######## img="+img+";;url;="+url);
         return img;
    }
 
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
//    	Log.d(null,"######## BitmapLruCache PUT ######## bitmap="+bitmap+";;url="+url);
        put(url, bitmap);
    }
    
    
    
}
