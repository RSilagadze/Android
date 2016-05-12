package com.ece.b3.dwm.tpannoncesproject.adapter;

import java.util.ArrayList;

import com.ece.b3.dwm.tpannoncesproject.CustomBaseActivity;
import com.ece.b3.dwm.tpannoncesproject.R;
import com.ece.b3.dwm.tpannoncesproject.beans.Annonce;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Extra;
import com.ece.b3.dwm.tpannoncesproject.tasks.DownloadImgTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AnnonceRelativeAdapter extends BaseAdapter{

	protected LayoutInflater inflater;
	protected ArrayList<Annonce> lstAnnonces;
	protected int widthX;
	protected int heightY;
	protected RelativeAdapterHolder holder;
	
	public AnnonceRelativeAdapter (CustomBaseActivity sender, ArrayList<Annonce> lstAnnonces) {
	   	super ();
        this.inflater = (LayoutInflater) sender.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lstAnnonces = lstAnnonces;
        this.holder = new RelativeAdapterHolder ();
    	Point p = Extra.getDisplaySize(sender);
    	this.widthX = p.x;
    	this.heightY = p.y;
	}
	
    @Override
	public void notifyDataSetChanged() {
    	this.holder.clearViewMapping();
		super.notifyDataSetChanged();
	}

	public int getCount() {
        return this.lstAnnonces.size();
    }

    public Annonce getItem(int position) {
        return lstAnnonces.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }
	
    public View getView(int position, View rowView, ViewGroup parent) {
        	try{
	        	if (rowView == null)
	        		rowView = this.inflater.inflate(R.layout.annonces_multiple_gdv_layout, parent, false);
	        	
				Annonce a = this.getItem(position);
				
				ImageView imageView = (ImageView) rowView.findViewById(R.id.imgIcon);
				TextView txtLibelle = (TextView) rowView.findViewById(R.id.txtLibelleAnnonce);
				TextView txtAuteur = (TextView) rowView.findViewById(R.id.txtAuthorAnnonce);
	        	TextView txtDateAnnonce = (TextView) rowView.findViewById(R.id.txtDateAnnonce);
	        	CheckBox cbx = (CheckBox) rowView.findViewById(R.id.cbxAnnonce);
	        
	        	Bitmap bitmap = new DownloadImgTask(a.getIconAdress()).get();
	        	if (bitmap != null)
	        		imageView.setImageBitmap(bitmap);
	        	else 
	        		imageView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.logo_na)); 

	        	txtLibelle.setText(a.getLabel());
	        	txtDateAnnonce.setText (new StringBuilder ("Le : ").append(a.getDate()).toString());
	        	txtAuteur.setText(new StringBuilder ("Par : ").append(a.utilisateur.getNom()).append(" ").
	        					append(a.utilisateur.getPrenom()).toString());
	        	cbx.setChecked(false);
	        	
	        	imageView.getLayoutParams().width = this.widthX / 4;
	        	imageView.getLayoutParams().height = this.heightY / 8;

	        	if (this.getCount() > this.holder.getViewCount())
	        		this.holder.putToHolder(position, rowView);
	        }   
        	catch (Exception e){
        		Log.i(Debug.DEBUG_TAG, e.getMessage());
        		return null;
        	}
        	
        return rowView;
    }

	public ArrayList<Annonce> getLstAnnonces() {
		return lstAnnonces;
	}

	public void setLstAnnonces(ArrayList<Annonce> lstAnnonces) {
		this.lstAnnonces = lstAnnonces;
	}

	public RelativeAdapterHolder getHolder() {
		return holder;
	}
}
