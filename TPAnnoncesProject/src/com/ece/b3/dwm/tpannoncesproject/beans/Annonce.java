package com.ece.b3.dwm.tpannoncesproject.beans;

import java.io.Serializable;

public class Annonce implements Serializable {
    private static final long serialVersionUID = 1;
    private float cost;
    private String date;
    private String description;
    private String iconAdress;
    private int id;
    private int idAuthor;
    private String label;
    private String pictureAdress;
    
    public 	Utilisateur utilisateur;

	public Annonce() {
        this.utilisateur = new Utilisateur();
    }

    public Annonce(int newId, String newDate, int newIdAuthor, String newLabel, String newDescription, float newCost, String newPictureAdress, String newIconAdress) {
        this.utilisateur = new Utilisateur();
        this.id = newId;
        this.date = newDate;
        this.idAuthor = newIdAuthor;
        this.label = newLabel;
        this.description = newDescription;
        this.cost = newCost;
        this.pictureAdress = newPictureAdress;
        this.iconAdress = newIconAdress;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdAuthor() {
        return this.idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCost() {
        return this.cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getPictureAdress() {
        return this.pictureAdress;
    }

    public void setPictureAdress(String pictureAdress) {
        this.pictureAdress = pictureAdress;
    }

    public String getIconAdress() {
        return this.iconAdress;
    }

    public void setIconAdress(String iconAdress) {
        this.iconAdress = iconAdress;
    }

}
