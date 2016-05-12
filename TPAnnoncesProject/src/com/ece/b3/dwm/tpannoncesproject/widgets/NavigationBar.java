package com.ece.b3.dwm.tpannoncesproject.widgets;

import com.ece.b3.dwm.tpannoncesproject.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class NavigationBar extends RelativeLayout {
	
	private Button nextBtn;
	private Button backBtn;
	private Button refreshBtn;
	private Button deleteBtn;
	private Button addBtn;

	public NavigationBar(Context context) {
		super(context);
		init(context);
	}
	
	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}
	
	private void init (Context context){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.navigation_bar_layout, this, true);
		
		nextBtn = (Button)findViewById(R.id.btnNextBar);
		backBtn = (Button)findViewById(R.id.btnBackBar);
		refreshBtn = (Button)findViewById(R.id.btnRefreshBar);
		deleteBtn = (Button)findViewById(R.id.btnDeleteBar);
		addBtn = (Button)findViewById(R.id.btnAddBar);
		
		initPopUps ();
	}
	
	public void setNextButtonAction (OnClickListener listener){
		nextBtn.setOnClickListener(listener);
	}
	
	public void setBacktButtonAction (OnClickListener listener){
		backBtn.setOnClickListener(listener);
	}
	
	public void setRefreshtButtonAction (OnClickListener listener){
		refreshBtn.setOnClickListener(listener);
	}
	
	public void setDeleteButtonAction (OnClickListener listener){
		deleteBtn.setOnClickListener(listener);
	}
	
	public void setAddButtonAction (OnClickListener listener){
		addBtn.setOnClickListener(listener);
	}
	
	public void playRefreshAnim (){
		refreshBtn.startAnimation(getRoatationAnim (refreshBtn));
	}
	
	public Animation getRoatationAnim (View v){
		Animation anim = new RotateAnimation(0f, 360f, v.getPivotX(), v.getPivotY());
		anim.setDuration(500);               // duration in ms
		anim.setRepeatCount(0);                // -1 = infinite repeated
		anim.setRepeatMode(Animation.REVERSE); // reverses each repeat
		anim.setFillAfter(true);
		return anim;
	}
	
	private void initPopUps (){
		refreshBtn.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		addBtn.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		deleteBtn.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}
}
