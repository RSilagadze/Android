package com.ece.b3.dwm.tpannoncesproject.handlers;

import com.ece.b3.dwm.tpannoncesproject.CustomBaseActivity;
import com.ece.b3.dwm.tpannoncesproject.services.Service;

import android.os.Handler;

public abstract class CustomBaseHandler extends Handler {
	
    protected CustomBaseActivity sender;
    protected Service service;
    
	public CustomBaseHandler (CustomBaseActivity sender, Service service){
		super ();
		this.sender = sender;
		this.service = service;
	}
	
	public CustomBaseActivity getSender() {
		return sender;
	}

	public void setSender(CustomBaseActivity sender) {
		this.sender = sender;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
