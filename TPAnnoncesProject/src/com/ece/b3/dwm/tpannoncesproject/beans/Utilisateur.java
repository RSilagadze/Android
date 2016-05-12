package com.ece.b3.dwm.tpannoncesproject.beans;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1;
    private String contact;
    private String dateInscription;
    private String description;
    private String iconAdress;
    private int id;
    private int idType;
    private String logoAdress;
    private String nom;
    private String prenom;
    private String site;
    public UserType usertype;

    public Utilisateur() {
        this.usertype = new UserType();
    }

    public Utilisateur(int newId, int newIdType, String newNom, String newPrenom, String newDesc, String newDateInsc, String newLogo, String newSite, String newContact, String newIconAdress) {
        this.usertype = new UserType();
        this.id = newId;
        this.idType = newIdType;
        this.nom = newNom;
        this.prenom = newPrenom;
        this.description = newDesc;
        this.dateInscription = newDateInsc;
        this.logoAdress = newLogo;
        this.site = newSite;
        this.contact = newContact;
        this.iconAdress = newIconAdress;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdType() {
        return this.idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateInscription() {
        return this.dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getLogoAdress() {
        return this.logoAdress;
    }

    public void setLogoAdress(String logoAdress) {
        this.logoAdress = logoAdress;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIconAdress() {
        return this.iconAdress;
    }

    public void setIconAdress(String iconAdress) {
        this.iconAdress = iconAdress;
    }
}
