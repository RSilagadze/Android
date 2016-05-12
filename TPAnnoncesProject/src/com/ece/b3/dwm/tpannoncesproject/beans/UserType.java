package com.ece.b3.dwm.tpannoncesproject.beans;

import java.io.Serializable;

public class UserType implements Serializable {
    private static final long serialVersionUID = 1;
    private String Libelle;
    private int id;
    
    public UserType() {
    }

    public UserType(int newId, String newLibelle) {
        this.id = newId;
        this.Libelle = newLibelle;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.Libelle;
    }

    public void setLibelle(String libelle) {
        this.Libelle = libelle;
    }
}
