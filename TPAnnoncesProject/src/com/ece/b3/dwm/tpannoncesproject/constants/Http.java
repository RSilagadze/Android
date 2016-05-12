package com.ece.b3.dwm.tpannoncesproject.constants;

public abstract class Http {
    public static final int Msg_Annonce = 1;
    public static final String getAnnoncesURL = "annonces";
    public static final String getAnnoncesURLTag = "annonces";
    
    public static final int limit = 5;
    
    public static final String getAnnonceByIdURL(int id) {
        return new StringBuilder ("annonce/").append(id).toString();
    }
   
    public static final String getAnnoncesByLimitURL(int start) {
        return new StringBuilder ("annonces/").append(start).append("/").append(limit).toString();
    }
    
}
