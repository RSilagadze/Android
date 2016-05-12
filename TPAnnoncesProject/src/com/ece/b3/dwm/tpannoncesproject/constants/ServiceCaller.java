package com.ece.b3.dwm.tpannoncesproject.constants;

import java.util.ArrayList;

import com.ece.b3.dwm.tpannoncesproject.CustomBaseActivity;
import com.ece.b3.dwm.tpannoncesproject.handlers.AnnonceHandler;
import com.ece.b3.dwm.tpannoncesproject.services.Service;
import com.ece.b3.dwm.tpannoncesproject.services.ServiceAnnonces;
import com.ece.b3.dwm.tpannoncesproject.tasks.ServiceTask;

import android.util.Pair;

public abstract class ServiceCaller {
	
	public static void startAnnonceService (CustomBaseActivity sender, String method, String url, boolean resetAdapter){
		Service sa = new ServiceAnnonces(Http.Msg_Annonce, url, 
		Http.getAnnoncesURLTag, method);
		AnnonceHandler handler = new AnnonceHandler(sa, sender, resetAdapter);
		new ServiceTask(handler);
	}
	
	public static void startAnnonceService (CustomBaseActivity sender, String method, String url){
		Service sa = new ServiceAnnonces(Http.Msg_Annonce, url, 
		Http.getAnnoncesURLTag, method);
		AnnonceHandler handler = new AnnonceHandler(sa, sender);
		new ServiceTask(handler);
	}
	
	public static void startAnnonceService (CustomBaseActivity sender, String method, String url,
											ArrayList<Pair<String,String>>headerParams){
		Service sa = new ServiceAnnonces(Http.Msg_Annonce, url, 
		Http.getAnnoncesURLTag, method, headerParams);
		AnnonceHandler handler = new AnnonceHandler(sa, sender);
		new ServiceTask(handler);
	}
	
}
