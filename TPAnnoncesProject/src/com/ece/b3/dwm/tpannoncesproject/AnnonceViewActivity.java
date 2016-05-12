package com.ece.b3.dwm.tpannoncesproject;

import java.util.ArrayList;
import com.ece.b3.dwm.tpannoncesproject.adapter.AnnonceRelativeAdapter;
import com.ece.b3.dwm.tpannoncesproject.adapter.RelativeAdapterHolder;
import com.ece.b3.dwm.tpannoncesproject.beans.Annonce;
import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Http;
import com.ece.b3.dwm.tpannoncesproject.constants.ServiceCaller;
import com.ece.b3.dwm.tpannoncesproject.widgets.ConfirmDialog;
import com.ece.b3.dwm.tpannoncesproject.widgets.NavigationBar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

public class AnnonceViewActivity extends CustomBaseActivity {
	
	private GridView gdvAnnonces;
	private NavigationBar bar;
	private int nbClicks;
	private AnnonceViewActivity activityInstance = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		nbClicks = 0;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiple_view);
		
		initViews ();
		onCallBack(this.getIntent().getExtras());
	}
	
	private OnClickListener getOnRefreshButtonListener (){
		return new OnClickListener (){
			@Override
			public void onClick(final View v) {
				activityInstance.nbClicks = 0;
				ServiceCaller.startAnnonceService(activityInstance, "GET", Http.getAnnoncesByLimitURL(0), true);
				v.startAnimation(bar.getRoatationAnim(v));
			}
		};
	}
	
	private OnClickListener getOnNextButtonListener (){
		return new OnClickListener (){
			@Override
			public void onClick(View v) {
				final int nPerPage = Http.limit;
				final int itemsCount = activityInstance.gdvAnnonces.getAdapter().getCount();
				if (itemsCount >= Http.limit && activityInstance.nbClicks >= 0){
					activityInstance.nbClicks++;
					int i = activityInstance.nbClicks;
					int start = (i)*nPerPage;
					ServiceCaller.startAnnonceService(activityInstance, "GET", Http.getAnnoncesByLimitURL(start), false);
				}
			}
		};
	}
	
	private OnClickListener getOnBackButtonListener (){
		return new OnClickListener (){
			@Override
			public void onClick(View v) {
				final int nPerPage = Http.limit;
				final int itemsCount = activityInstance.gdvAnnonces.getAdapter().getCount();
				if (itemsCount <= nPerPage && activityInstance.nbClicks > 0){
					activityInstance.nbClicks--;
					int i = activityInstance.nbClicks;
					int start = (i)*nPerPage;
					ServiceCaller.startAnnonceService(activityInstance, "GET", Http.getAnnoncesByLimitURL(start), false);
				}
			}
		};
	}
	
	private OnClickListener getOnDeleteButtonListener (){
		return new OnClickListener (){
			@Override
			public void onClick(final View v) {
				final ArrayList<Integer> idsToRemove = new ArrayList<Integer>();
				final AnnonceRelativeAdapter adapter = (AnnonceRelativeAdapter) gdvAnnonces.getAdapter();
				final RelativeAdapterHolder holder = adapter.getHolder();
				
				for (int i = 0; i < adapter.getCount();i++){
					View row = holder.findRowByPosition(i);  //out of exception may occur
					CheckBox cbx = (CheckBox) row.findViewById(R.id.cbxAnnonce);
					if (cbx.isChecked()){
						Annonce a = adapter.getItem(i); //out of exception may occur
						idsToRemove.add(a.getId());
					}	
				}
				if (idsToRemove.size() > 0){
					ConfirmDialog dialog = new ConfirmDialog("Etes-vous sûr?","Veuillez confirmer la suppression", activityInstance);
					
					dialog.setOnClickPositiveButton("Oui", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							for (int id : idsToRemove ){
								ServiceCaller.startAnnonceService(activityInstance, "DELETE",
										Http.getAnnonceByIdURL(id), false);
							}
							final int nPerPage = Http.limit;
							int start = activityInstance.nbClicks*nPerPage;
							ServiceCaller.startAnnonceService(activityInstance, "GET", 
									Http.getAnnoncesByLimitURL(start), false);
							activityInstance.bar.playRefreshAnim ();
						}
					}); 
					
					dialog.setOnClickNegativeButton("Non", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							for (int i = 0; i < holder.getViewCount();i++){
								View row = holder.findRowByPosition(i);
								CheckBox cbx = (CheckBox) row.findViewById(R.id.cbxAnnonce);
								if (cbx.isChecked()){
									cbx.setChecked(false);
								}	
							}
						}
					});
					dialog.show();
				}
				else Toast.makeText(activityInstance, 
							"Veuillez séléctionner les annonces à supprimer", Toast.LENGTH_LONG).show();
			}};
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCallBack(Bundle args) {
		if (args != null && !args.isEmpty()){
			try{
				ArrayList<Annonce> data = (ArrayList<Annonce>) args.getSerializable(AppPrefs.getLstAnnonceTag());
				boolean resetAdapter = args.getBoolean(AppPrefs.getAdapterBehaviourTag());
				AnnonceRelativeAdapter adapter;
				
				if (resetAdapter){
				    adapter = new AnnonceRelativeAdapter (this, data);
					gdvAnnonces.setAdapter(adapter);
				}
		        adapter = (AnnonceRelativeAdapter) gdvAnnonces.getAdapter();
		        if (adapter == null){
		        	 adapter = new AnnonceRelativeAdapter (this, data);
		        	 gdvAnnonces.setAdapter(adapter);
		        }
	        	adapter.setLstAnnonces(data);
	        	adapter.notifyDataSetChanged();
			}
			catch(Exception e){
				Log.e(Debug.DEBUG_TAG, e.getMessage());
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AppPrefs.getInsertAnnonceTag() || requestCode == AppPrefs.getUpdateAnnonceTag()){
			if (resultCode == Activity.RESULT_OK){
				int i = activityInstance.nbClicks;
				int start = (i)*Http.limit;
				ServiceCaller.startAnnonceService(this, "GET", Http.getAnnoncesByLimitURL(start), false);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void initViews (){
		this.gdvAnnonces = (GridView) findViewById(R.id.gdvAnnonces);
		this.gdvAnnonces.setOnItemClickListener(getOnItemClickListener());
		this.bar = (NavigationBar) findViewById(R.id.navigationbar_id);
		this.bar.setNextButtonAction(getOnNextButtonListener());
		this.bar.setBacktButtonAction(getOnBackButtonListener());
		this.bar.setRefreshtButtonAction(getOnRefreshButtonListener());
		this.bar.setDeleteButtonAction(getOnDeleteButtonListener());
		this.bar.setAddButtonAction(getOnAddClickListener());
		
		GridLayout.LayoutParams l = new GridLayout.LayoutParams();
		l.setMarginEnd(bar.getHeight());
	}
	
	private OnClickListener getOnAddClickListener (){
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent (activityInstance, AnnonceInsertActivity.class);
				activityInstance.startActivityForResult(intent, AppPrefs.getInsertAnnonceTag());
			}
		};
	}
	
	private OnItemClickListener getOnItemClickListener(){
		return new OnItemClickListener (){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent (activityInstance, AnnonceSingleViewActivity.class);
				AnnonceRelativeAdapter adapter = (AnnonceRelativeAdapter) gdvAnnonces.getAdapter();
				Annonce a = adapter.getItem(position);
				intent.putExtra("id", a.getId());
				//activityInstance.startActivity(intent);
				activityInstance.startActivityForResult(intent, AppPrefs.getUpdateAnnonceTag());
			}
		};
	}
	
}
