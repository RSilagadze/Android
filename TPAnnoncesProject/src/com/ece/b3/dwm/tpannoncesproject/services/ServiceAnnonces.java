package com.ece.b3.dwm.tpannoncesproject.services;

import java.util.ArrayList;
import android.util.Pair;

public class ServiceAnnonces extends Service {
    public ServiceAnnonces(int identifier, String url, String tag, String method) {
        super(identifier, url, tag, method);
    }
    
    public ServiceAnnonces(int identifier, String url, String tag, String method,ArrayList<Pair<String,String>> headers) {
        super(identifier, url, tag, method, headers);
    }
}
