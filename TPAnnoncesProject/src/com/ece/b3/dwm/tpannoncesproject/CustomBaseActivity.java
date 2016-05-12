package com.ece.b3.dwm.tpannoncesproject;

import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Http;
import com.ece.b3.dwm.tpannoncesproject.constants.ServiceCaller;

import android.app.Activity;

public abstract class CustomBaseActivity extends Activity implements CallBackInterface {
	
	@Override
	protected void onRestart() {
		if (this.getIntent().getExtras() != null){
			String tag = this.getIntent().getExtras().getString(AppPrefs.getWifiIntentTag());
			if (tag != null && !tag.equals("") && !tag.equals("-1")){
				ServiceCaller.startAnnonceService(this, "GET", Http.getAnnoncesByLimitURL(0));
			}
		}
		super.onRestart();
	}
}
