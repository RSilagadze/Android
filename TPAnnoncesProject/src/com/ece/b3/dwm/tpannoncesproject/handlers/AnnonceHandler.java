package com.ece.b3.dwm.tpannoncesproject.handlers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ece.b3.dwm.tpannoncesproject.CustomBaseActivity;
import com.ece.b3.dwm.tpannoncesproject.beans.Annonce;
import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Extra;
import com.ece.b3.dwm.tpannoncesproject.services.Service;
import com.ece.b3.dwm.tpannoncesproject.widgets.ErrorDialog;
import com.ece.b3.dwm.tpannoncesproject.widgets.NoConnectionDialog;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class AnnonceHandler extends CustomBaseHandler {
	
    private boolean resetAdapter;
    
    public AnnonceHandler(Service service, CustomBaseActivity sender, boolean resetAdapter) {
    	super (sender,service);
        this.resetAdapter = resetAdapter;
        this.service = service;
    }
    
    public AnnonceHandler(Service service, CustomBaseActivity sender) {
    	super (sender,service);
        this.resetAdapter = false;
    }
   
	private Object formatRecievedMessage(Message msg) {
        if (msg.what == this.service.identifier) {
            String parsableData = msg.getData().getString(this.service.tag);
            
            if (Extra.isParsableToInt(parsableData)) {
                return Integer.parseInt(parsableData);
            }
            
            try {
                JSONObject root = new JSONObject(parsableData);
                JSONArray dataArray = root.getJSONArray(this.service.tag);
             	ArrayList<Annonce> lstData = new ArrayList<Annonce>();
                for (int i = 0; i < dataArray.length(); i++) {
                     JSONObject jArrObject = dataArray.getJSONObject(i);
                     
                     Annonce a = fillAnnonceData(jArrObject);
                     lstData.add(a);
                }
                return lstData;
            } catch (Exception e) {
                Log.e(Debug.DEBUG_TAG, e.getMessage());
                return null;
            }
        }
        return null;
    }

    private Annonce fillAnnonceData(JSONObject jArrObject) {
        Annonce a = new Annonce();
       
        try {
            a.setId(jArrObject.getInt("id"));
            a.setLabel(jArrObject.getString("Libelle"));
            a.setDescription(jArrObject.getString("Description"));
            a.setDate(Extra.DateEnToFr(jArrObject.getString("Date")));
            
            a.utilisateur.setId(jArrObject.getInt("idUser"));
            
            a.setIdAuthor(jArrObject.getInt("idUser"));
            a.setCost(Float.parseFloat(jArrObject.getString("Prix")));
            a.setPictureAdress(jArrObject.getString("LogoAdress"));
            a.setIconAdress(jArrObject.getString("IconAdress"));
            
            a.utilisateur.setNom(jArrObject.getString("Nom"));
            a.utilisateur.setPrenom(jArrObject.getString("Prenom"));
            a.utilisateur.setSite(jArrObject.getString("Site"));
            a.utilisateur.setDescription(jArrObject.getString("UserDesc"));
            a.utilisateur.setContact(jArrObject.getString("Contact"));
            a.utilisateur.setDateInscription(Extra.DateEnToFr(jArrObject.getString("DateInscription")));
            a.utilisateur.setLogoAdress(jArrObject.getString("UserLogo"));
            
            a.utilisateur.usertype.setLibelle(jArrObject.getString("UserType"));
            
            a.utilisateur.setIconAdress(jArrObject.getString("UserIcon"));
        } catch (Exception e) {
        	Log.e(Debug.DEBUG_TAG, e.getMessage());
        }
        return a;
    }

	public void dispatchMessage(Message msg) {
    	Object obj = formatRecievedMessage(msg);
    	Bundle args = new Bundle ();
    	if (obj instanceof Integer){
    		int code = (int)obj;
    		if (Extra.isError(code)){
    			//handle errors here
    			if (code == Debug.NO_CONNECTION)
	    			 new NoConnectionDialog(this.sender).show();
    			else
    				new ErrorDialog(this.sender).show();
    		}
    		else {
    			args.putInt(AppPrefs.getWSQueryTag(), code);
    		}
    	}
    	if (obj instanceof ArrayList){
    		args.putSerializable(AppPrefs.getLstAnnonceTag(), (ArrayList<?>)obj);
    		args.putBoolean(AppPrefs.getAdapterBehaviourTag(), this.resetAdapter);
    	}
		sender.onCallBack(args);
    }

}
