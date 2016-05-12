package com.ece.b3.dwm.tpannoncesproject.adapter;

import java.util.ArrayList;

import com.ece.b3.dwm.tpannoncesproject.CustomBaseActivity;
import com.ece.b3.dwm.tpannoncesproject.R;
import com.ece.b3.dwm.tpannoncesproject.beans.Annonce;
import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Extra;
import com.ece.b3.dwm.tpannoncesproject.tasks.DownloadImgTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AnnonceSingleViewAdapter extends AnnonceRelativeAdapter {

	private CustomBaseActivity sender;
	
	public AnnonceSingleViewAdapter(CustomBaseActivity sender, ArrayList<Annonce> lstAnnonces) {
		super(sender, lstAnnonces);
		this.sender = sender;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	};
	
	@Override
	public View getView(int position, View rowView, ViewGroup parent) {
		try{
	    	if (rowView == null)
	    		rowView = this.inflater.inflate(R.layout.annonces_single_gdv_layout, parent, false);
	    	
			Annonce a = this.getItem(position);
			
			ImageView imgView = (ImageView) rowView.findViewById(R.id.imgSingleView);
			TextView tvTitle = (TextView) rowView.findViewById(R.id.tvSingleTitleView);
			TextView tvAuthor = (TextView) rowView.findViewById(R.id.tvSingleViewAuthor);
	    	TextView tvDate = (TextView) rowView.findViewById(R.id.tvSingleViewDate);
	    	TextView tvPrix = (TextView) rowView.findViewById(R.id.tvSingleViewPrix);
	    	TextView tvContact = (TextView) rowView.findViewById(R.id.tvSingleViewContact);
	    	TextView tvSite = (TextView) rowView.findViewById(R.id.tvSingleViewSite);
	    	TextView tvDescription = (TextView) rowView.findViewById(R.id.tvSingleViewDesc);
	    	
	    	Button btnDownload = (Button) rowView.findViewById(R.id.btnDownloadImg);
	    	Button btnView = (Button) rowView.findViewById(R.id.btnViewFullImg);
	    	
	    	if (a.getPictureAdress() == null || a.getPictureAdress().equals("")){
	    		btnDownload.setVisibility(View.INVISIBLE);
	    		btnView.setVisibility(View.INVISIBLE);
	    	}
	    	
	    	imgView.getLayoutParams().width = this.widthX / 2;
	    	imgView.getLayoutParams().height = this.heightY / 4;
	    
	    	Bitmap bitmap = new DownloadImgTask(a.getPictureAdress()).get();
	    	if (bitmap != null){
	    		imgView.setImageBitmap(bitmap);
        		Extra.saveBitmapToFile(bitmap, a.getPictureAdress(), AppPrefs.getSDAppDataLocation());
	    	}
	    	else 
	    		imgView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.logo_na)); 
	
	    	tvTitle.setText(a.getLabel());
	    	tvDate.setText (new StringBuilder ("Posté le : ").append(a.getDate()).toString());
	    	tvAuthor.setText(new StringBuilder ("Par : ").append(a.utilisateur.usertype.getLibelle()).append(" ").
	    						append(a.utilisateur.getNom()).append(" ").
	        					append(a.utilisateur.getPrenom()).toString());
	    	tvPrix.setText("Prix : " +a.getCost() + " €");
	    	tvDescription.setText(a.getDescription());
	    	
	    	if (!a.utilisateur.getContact().equals("")){
	    		tvContact.setText(a.utilisateur.getContact());
	    		tvContact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_icon, 0, 0, 0);
	    	}
	    	else 
	    		tvContact.setVisibility(View.INVISIBLE);
	    	
	    	if (!a.utilisateur.getSite().equals("")){
	    		tvSite.setText(a.utilisateur.getSite());
	    		tvSite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.internet_icon, 0, 0, 0);
	    	}
	    	else 
	    		tvSite.setVisibility(View.INVISIBLE);
	    	
        	if (this.getCount() > this.holder.getViewCount()){
        		this.holder.putToHolder(position, rowView);
        		setActions (btnDownload, btnView, tvSite, tvContact);
        	}
		}   
 		catch (Exception e){
			Log.i(Debug.DEBUG_TAG, e.getMessage());
			return null;
	    }
		return rowView;
	 }
	
	private void setActions (Button btnDownload, Button btnView, TextView tvSite, TextView tvContact){
		if (this.sender != null && this.holder.getViewCount() > 0){
			final String picName = this.getItem(0).getPictureAdress();
			final String mail = this.getItem(0).utilisateur.getContact();
			final String siteAdress = this.getItem(0).utilisateur.getSite();
			
			final View row = holder.findRowByPosition(0);
			final ImageView imgView = (ImageView) row.findViewById(R.id.imgSingleView);
			final Bitmap bitmap = ((BitmapDrawable)imgView.getDrawable()).getBitmap();
			
			if (picName != null && !picName.equals("") && bitmap != null){
				btnView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.parse ("file://" +AppPrefs.getSDAppDataLocation()+"/"+picName), "image/*");
						sender.startActivity(intent);
					}
				});
		
				btnDownload.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (Extra.saveBitmapToFile(bitmap, picName, AppPrefs.getSDAppDataDownloadedLocation()))
							Toast.makeText(sender, "Image sauvegardée", Toast.LENGTH_LONG).show();
						else 
							Toast.makeText(sender, "Erreur de sauvegarde de l'image", Toast.LENGTH_LONG).show();
					}
				});
			}
			
			if (mail != null && !mail.equals("")){
				tvContact.setOnTouchListener(new OnTouchListener() {
					@Override
					 public boolean onTouch(View v, MotionEvent event) {
					    if(event.getAction() == MotionEvent.ACTION_DOWN) {
				            v.setBackgroundColor(v.getResources().getColor(R.color.lblue));
				            ((TextView)v).setTextColor(v.getResources().getColor(R.color.white));
				            
							Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
							emailIntent.setType("plain/text");
							emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mail});
							emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Objet : ");
							emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Bonjour, ");
							sender.startActivity(Intent.createChooser(emailIntent, "Envoyer un mail..."));
					    }
					    if (event.getAction() == MotionEvent.ACTION_UP) {
					    	((TextView)v).setTextColor(v.getResources().getColor(R.color.black));
				            v.setBackground(v.getResources().getDrawable(R.drawable.tv_singleview_shape));
							v.performClick();
				        } 
					    return true;
					}
				});
			}
			
			if (siteAdress != null && !siteAdress.equals("")){
				tvSite.setOnTouchListener(new OnTouchListener() {
					@Override
					 public boolean onTouch(View v, MotionEvent event) {
					    if(event.getAction() == MotionEvent.ACTION_DOWN) {
				            v.setBackgroundColor(v.getResources().getColor(R.color.lblue));
				            ((TextView)v).setTextColor(v.getResources().getColor(R.color.white));
				            
							Intent intent;
							if (!siteAdress.startsWith("http://") && !siteAdress.startsWith("https://")){
								intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + siteAdress));
							}
							else 
								intent = new Intent(Intent.ACTION_VIEW, Uri.parse(siteAdress));
							sender.startActivity(intent);
					    }
					    if (event.getAction() == MotionEvent.ACTION_UP) {
					    	((TextView)v).setTextColor(v.getResources().getColor(R.color.black));
				            v.setBackground(v.getResources().getDrawable(R.drawable.tv_singleview_shape));
							v.performClick();
				        } 
					    return true;
					}
				});
			}
		}
	}
}
