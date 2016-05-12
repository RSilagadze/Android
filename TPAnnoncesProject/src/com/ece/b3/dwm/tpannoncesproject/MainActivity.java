package com.ece.b3.dwm.tpannoncesproject;

import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Http;
import com.ece.b3.dwm.tpannoncesproject.constants.ServiceCaller;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends CustomBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		AppPrefs.init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		ServiceCaller.startAnnonceService(this, "GET", Http.getAnnoncesByLimitURL(0), true);
	}
	
	@Override
	public void onCallBack(Bundle args) {
		Intent intent = new Intent (this, AnnonceViewActivity.class);
		intent.putExtras(args);
		this.startActivity(intent);
		this.finish();
		return;
	}

}
