package com.ece.b3.dwm.tpannoncesproject;

import java.io.File;
import java.util.ArrayList;

import com.ece.b3.dwm.tpannoncesproject.adapter.AnnonceSingleViewAdapter;
import com.ece.b3.dwm.tpannoncesproject.beans.Annonce;
import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Http;
import com.ece.b3.dwm.tpannoncesproject.constants.ServiceCaller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

public class AnnonceSingleViewActivity extends CustomBaseActivity {
	
	private Annonce currentAnnonce;
	private GridView gdvAnnonce;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annonce_single_view);
		
		gdvAnnonce = (GridView) findViewById(R.id.gdvSingleAnnonce);
		
		Intent intent = this.getIntent();
		int id = intent.getIntExtra("id", -1);
		if (id != -1){
			ServiceCaller.startAnnonceService(this, "GET", Http.getAnnonceByIdURL(id), true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.annonce_single_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.mod_annonce) {
			Intent intent = new Intent (this, AnnonceEditActivity.class);
			intent.putExtra("id", this.currentAnnonce.getId());
			intent.putExtra("title", this.currentAnnonce.getLabel());
			intent.putExtra("cost", this.currentAnnonce.getCost());
			intent.putExtra("description", this.currentAnnonce.getDescription());
			intent.putExtra("imagePath", this.currentAnnonce.getPictureAdress());
			
			this.startActivityForResult(intent, AppPrefs.getUpdateAnnonceTag());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AppPrefs.getUpdateAnnonceTag()){
			if (resultCode == Activity.RESULT_OK){
				int id = currentAnnonce.getId();
				setResult(Activity.RESULT_OK, new Intent ());
				ServiceCaller.startAnnonceService(this, "GET", Http.getAnnonceByIdURL(id), false);
			}				
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
		return;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCallBack(Bundle args) {
		if (args != null && !args.isEmpty()){
			try{
				ArrayList<Annonce> data = (ArrayList<Annonce>) args.getSerializable(AppPrefs.getLstAnnonceTag());
				boolean resetAdapter  = args.getBoolean(AppPrefs.getAdapterBehaviourTag());
				if (data != null && data.size() > 0){
					this.currentAnnonce = data.get(0);
					fillSingleAnnonceGdv (data,resetAdapter);
				}
			}
			catch (Exception e) {
				Log.e(Debug.DEBUG_TAG,e.getMessage());
			}
		}	
	}
	
	@Override
	protected void onDestroy() {
		File root = new File (AppPrefs.getSDAppDataLocation());
		File[] files = root.listFiles();
		if (files != null && files.length > 0){
			for (File file : files){
				if (file.isFile())
					file.delete();
			}
		}
		super.onDestroy();
	}
	
	private void fillSingleAnnonceGdv (ArrayList<Annonce> data, boolean resetAdapter){
		AnnonceSingleViewAdapter adapter;
		if (resetAdapter){
			adapter = new AnnonceSingleViewAdapter(this, data);
			gdvAnnonce.setAdapter(adapter);
		}
		adapter = (AnnonceSingleViewAdapter)gdvAnnonce.getAdapter();
		if (adapter == null){
			adapter = new AnnonceSingleViewAdapter(this, data);
			gdvAnnonce.setAdapter(adapter);
		}
		else {
			adapter.setLstAnnonces(data);
			adapter.notifyDataSetChanged ();
		}
	}
}
