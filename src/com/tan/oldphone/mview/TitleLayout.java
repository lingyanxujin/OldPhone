package com.tan.oldphone.mview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tan.oldphone.R;

public class TitleLayout extends LinearLayout implements OnClickListener{
	private Context context;
	private TextView center;
	private Button right;
	private RelativeLayout layout_right;
	private Button left;
	private RelativeLayout layout_left;
	private TextView mTextNumber;
	private Intent mIntent = null;
	public TitleLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}
	
	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}
	
	private void init(){
		View child = LayoutInflater.from(context).inflate(R.layout.activity_linear_top, null);
		center = (TextView) child.findViewById(R.id.example_center);
		right = (Button) child.findViewById(R.id.example_right);
		layout_right = (RelativeLayout) child.findViewById(R.id.layout_right);
		left = (Button) child.findViewById(R.id.example_left);
		layout_left = (RelativeLayout) child.findViewById(R.id.layout_left);
		addView(child);
	}
	
	public void setCenter(String str){
		center.setText(str);
	}
	
	public void setCenter(String str,int color){
		center.setText(str);
		center.setTextColor(color);
	}
	
	public void setCenter(String str,boolean isLeft){
		center.setText(str);
		if(isLeft){
			layout_left.setVisibility(View.VISIBLE);
			left.setOnClickListener(this);
		}
	}
	
	public void setCenter(String str,int size,int color){
		center.setText(str);
		if(size != 0){
			center.setTextSize(size);
		}
		if(color!=0){
			center.setTextColor(color);
		}
	}
	
	public void setCenter(String str,int size,int color,boolean isLeft){
		center.setText(str);
		if(size != 0){
			center.setTextSize(size);
		}
		if(color!=0){
			center.setTextColor(color);
		}
		if(isLeft){
			layout_left.setVisibility(View.VISIBLE);
			left.setOnClickListener(this);
		}
	}
	
	public void setCenter(String str,int size,int color,boolean isLeft,int drawable){
		center.setText(str);
		if(size != 0){
			center.setTextSize(size);
		}
		if(color!=0){
			center.setTextColor(color);
		}
		if(isLeft){
			layout_left.setVisibility(View.VISIBLE);
			left.setBackgroundResource(drawable);
			left.setOnClickListener(this);
		}
	}
	
	public void setRight(String str){
		layout_right.setVisibility(View.VISIBLE);
		right.setText(str);
	}
	
	public void setRight(String str,Intent mIntent){
		layout_right.setVisibility(View.VISIBLE);
		right.setText(str);
		layout_right.setOnClickListener(this);
		right.setOnClickListener(this);
		this.mIntent = mIntent;
	}
	
	public void setRight(int drawable,OnClickListener oil){
		layout_right.setVisibility(View.VISIBLE);
		layout_right.setOnClickListener(oil);
		right.setBackgroundResource(drawable);
		right.setOnClickListener(oil);
	}
	
	public void setLeft(String str,OnClickListener oil){
		layout_left.setVisibility(View.VISIBLE);
		left.setText(str);
		layout_left.setOnClickListener(oil);
		left.setOnClickListener(oil);
	}
	
	public void setLeft(String str){
		layout_left.setVisibility(View.VISIBLE);
		left.setText(str);
		layout_left.setOnClickListener(this);
		left.setOnClickListener(this);
	}
	
	public void setLeftBack(int drawble){
		layout_left.setVisibility(View.VISIBLE);
		left.setBackgroundResource(drawble);
		layout_left.setOnClickListener(this);
		left.setOnClickListener(this);
	}
	
	public void setRightHide(){
		if(layout_right.getVisibility() == View.VISIBLE)
		layout_right.setVisibility(View.INVISIBLE);
	}
	public void setTextNumber(String text){
		mTextNumber.setVisibility(View.VISIBLE);
		mTextNumber.setText(text);
	}
	public void setTextHide(){
		mTextNumber.setVisibility(View.GONE);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.example_left || arg0.getId()==R.id.layout_left){
			((Activity) context).finish();
		}else if(arg0.getId()==R.id.example_right || arg0.getId()==R.id.layout_right){
			if(mIntent != null){
				context.startActivity(mIntent);
			}
		}
	}
}
