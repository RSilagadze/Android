package com.ece.b3.dwm.tpannoncesproject.adapter;

import android.util.SparseArray;
import android.view.View;

public class RelativeAdapterHolder {

	private SparseArray<View> viewArr;
	
	public RelativeAdapterHolder (){
		viewArr = new SparseArray<View> ();
	}
	
	public void clearViewMapping (){
		viewArr.clear();
	}
	
	public void putToHolder (int viewPosition, View view){
		viewArr.put(viewPosition, view);
	}
	
	public View findRowByPosition (int viewPosition){
		return viewArr.get(viewPosition);
	}
	
	public int getViewCount (){
		return viewArr.size();
	}
	
	public int getKeyAt (int index){
		return viewArr.keyAt(index);
	}

}
