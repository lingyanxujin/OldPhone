package com.tan.oldphone.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tan.oldphone.R;
import com.tan.oldphone.adapter.HorizontalScrollViewAdapter;
import com.tan.oldphone.view.MyHorizontalScrollView;
import com.tan.oldphone.view.MyHorizontalScrollView.CurrentImageChangeListener;
import com.tan.oldphone.view.MyHorizontalScrollView.OnItemClickListener;

public class MainActivity extends Activity
{

	private MyHorizontalScrollView mHorizontalScrollView;
	private HorizontalScrollViewAdapter mAdapter;
	private ImageView mImg;
	private TextView nameView;
	private ArrayList<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
			R.drawable.ppppp_a, R.drawable.ppppp_b, R.drawable.ppppp_c, R.drawable.ppppp_d,
			R.drawable.ppppp_e, R.drawable.ppppp_f, R.drawable.ppppp_g, R.drawable.ppppp_h,
			R.drawable.ppppp_l));
    private ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		getList();
		mImg = (ImageView) findViewById(R.id.id_content);
		nameView = (TextView)findViewById(R.id.name);
		TextPaint tp = nameView .getPaint();
		tp.setFakeBoldText(true);
		
		mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
		mAdapter = new HorizontalScrollViewAdapter(this, list);
		//添加滚动回调
		mHorizontalScrollView.setCurrentImageChangeListener(new CurrentImageChangeListener(){
			@Override
			public void onCurrentImgChanged(int position, View viewIndicator){
						mImg.setImageResource(mDatas.get(position));
						nameView.setText(list.get(position).get("name")+""+position);
						viewIndicator.setBackgroundColor(Color
								.parseColor("#AA024DA4"));
					}
				});
		//添加点击回调
		mHorizontalScrollView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onClick(View view, int position)
			{
				mImg.setImageResource(mDatas.get(position));
				nameView.setText(list.get(position).get("name")+""+position);
				view.setBackgroundColor(Color.parseColor("#AA024DA4"));
			}
		});
		//设置适配器
		mHorizontalScrollView.initDatas(mAdapter);
	}

	private void getList(){
		HashMap<String,Object> map=null;
		for(Integer image : mDatas){
			map =new HashMap<String,Object>();
			map.put("image", image);
			map.put("name", "姓名");
			list.add(map);
		}
	}
}
