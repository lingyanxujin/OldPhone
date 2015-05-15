package com.tan.oldphone.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tan.oldphone.R;
import com.tan.oldphone.model.ContactContent;
import com.tan.oldphone.mview.MyGallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class MyGallaryAdapter extends BaseAdapter {

	protected static final String TAG = "MyGallaryAdapter";
	
	private Context mctxt;
	private List<ContactContent>  contents;
	DisplayImageOptions options;
	ImageLoader imageLoader;
	
	public MyGallaryAdapter(Context mctxt, List<ContactContent>  contents, DisplayImageOptions options, ImageLoader imageLoader){
		this.mctxt = mctxt;
		this.contents = contents;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return contents.size();
	}

	@Override
	public Object getItem(int position) {
		return contents.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		
		if( contents.size() <= 0) return null;

//		TNetworkImageView imageView = new TNetworkImageView(mctxt);
//		//TNetworkImageView  ivAppIcon = (TNetworkImageView )mViewItems[i].findViewById(R.id.ivAppIcon);
//		imageView.setDefaultImageResId(R.drawable.ppppp_c);
//		imageView.setErrorImageResId(R.drawable.ppppp_d);
//		int w = 107;
//		int h =107;
//		ImageRequest.CustomParse customParse = 
//			new ImageRequest.CustomParse(w, h, Config.ARGB_8888,false);
//		System.out.println("app.getIcon()=" + contents.get(position).getPicture() + ";;");
//
//		imageView.setImageUrl(contents.get(position).getPicture(), TVolley.getImageLoader(),w,h,customParse);

		// 创建一个ImageView
		ImageView imageView = new ImageView(mctxt);
//		Log.d(TAG, "BaseAdapter,getView position:"+position);
		if(position >= contents.size() ) position=0;
		
		if(contents.get(position)==null){
//			imageView.setImageResource(R.drawable.ppppp_a);
		}else{
//			imageView.setImageResource(contents.get(position));
		}		
		
		// 设置ImageView的缩放类型
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new MyGallery.LayoutParams(125, 150));
		TypedArray typedArray = mctxt.obtainStyledAttributes(
			R.styleable.Gallery);
		imageView.setBackgroundResource(typedArray.getResourceId(
			R.styleable.Gallery_android_galleryItemBackground, 0));
		
		imageLoader.displayImage(contents.get(position).getPicture(), imageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
//				spinner.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {		// ��ȡͼƬʧ������
					case IO_ERROR:				// �ļ�I/O����
						message = "Input/Output error";
						break;
					case DECODING_ERROR:		// �������
						message = "Image can't be decoded";
						break;
					case NETWORK_DENIED:		// �����ӳ�
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:		    // �ڴ治��
						message = "Out Of Memory error";
						break;
					case UNKNOWN:				// ԭ����
						message = "Unknown error";
						break;
				}
				Toast.makeText(mctxt, message, Toast.LENGTH_SHORT).show();

//				spinner.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				spinner.setVisibility(View.GONE);		// ����ʾԲ�ν����
			}
		});
		
		
		
		
		return imageView;
		
	}

}
