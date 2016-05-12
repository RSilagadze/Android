package com.ece.b3.dwm.tpannoncesproject.services;

import java.util.ArrayList;
import android.util.Pair;

public abstract class Service {
    public String adress;
    public int identifier;
    public String method;
    public String tag;
    public ArrayList<Pair<String,String>> queryParams;

    public Service(int identifier, String adress, String tag, String method) {
        this.identifier = identifier;
        this.adress = adress;
        this.tag = tag;
        this.method = method;
        this.queryParams = new ArrayList<Pair<String,String>>();
    }
    
    public Service(int identifier, String adress, String tag, String method, ArrayList<Pair<String,String>> headers) {
        this.identifier = identifier;
        this.adress = adress;
        this.tag = tag;
        this.method = method;
        this.queryParams = headers;
    }
 
    
}
