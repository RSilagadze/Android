package com.ece.b3.dwm.tpannoncesproject.constants;

import java.io.File;

import com.ece.b3.dwm.tpannoncesproject.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class AppPrefs {
	
	private Context ctx;
	private SharedPreferences sharedPrefs;
	private static AppPrefs instance;
	private static int instances_count = 0;
	
	private AppPrefs (Context ctx){
		this.ctx = ctx;
		this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		editPreferences (sharedPrefs.edit());
		if (instances_count < 1)
			instances_count ++;
	}
	
	public static void init(Context ctx) {
		if (instances_count < 1){
			instance = new AppPrefs(ctx);
			System.setProperty("http.keepAlive", "false");
		}
		if (instances_count > 1){
			Log.w(Debug.DEBUG_TAG, "Warning! Multiple SharedPreference initializations detected!");
		}
	}
	
	private void editPreferences (Editor edit){
		String host_adress = ctx.getResources().getString(R.string.webservice_adress);
		edit.putString("adapter_behaviour", "hasReset");
		edit.putString("annonce_tag", "annonces");
		edit.putString("ws", host_adress + "/projetwebservice/");
		edit.putString("ws_img", host_adress + "/Images/");
		edit.putString("wifi_intent", "wifi_error");
		edit.putInt("insert_activity", 0x228);
		edit.putInt("update_activity", 0x220);
		edit.putString("ws_Query_tag", "ws_Query");
		
		File localData = new File (Environment.getExternalStorageDirectory() + File.separator + "ProjetAnnonce");
		if (!localData.exists()){
			localData.mkdir();
		}
		File downloaded = new File (localData.getPath()+File.separator+"Downloaded");
		if (!downloaded.exists()){
			downloaded.mkdir();
		}

		edit.putString("data_downloaded", downloaded.getPath());
		edit.putString("data_location",	localData.getPath());
		edit.commit();
	}
	
	public static String getWSAdress (){
		return instance.sharedPrefs.getString("ws", "-1");
	}
	
	public static String getIMGServerAdress (){
		return instance.sharedPrefs.getString("ws_img", "-1");
	}
	
	public static String getLstAnnonceTag (){
		return instance.sharedPrefs.getString("annonce_tag", "-1");
	}
	
	public static String getAdapterBehaviourTag (){
		return instance.sharedPrefs.getString("adapter_behaviour", "-1");
	}
	
	public static String getWifiIntentTag (){
		return instance.sharedPrefs.getString("wifi_intent", "-1");
	}
	
	public static int getInsertAnnonceTag (){
		return instance.sharedPrefs.getInt("insert_activity", -1);
	}
	
	public static String getWSQueryTag (){
		return instance.sharedPrefs.getString("ws_Query_tag", "-1");
	}
	
	public static int getUpdateAnnonceTag (){
		return instance.sharedPrefs.getInt("update_activity", -1);
	}
	
	public static String getSDAppDataLocation (){
		return instance.sharedPrefs.getString("data_location", "-1");
	}
	
	public static String getSDAppDataDownloadedLocation (){
		return instance.sharedPrefs.getString("data_downloaded", "-1");
	}
}
