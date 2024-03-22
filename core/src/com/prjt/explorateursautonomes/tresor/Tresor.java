package com.prjt.explorateursautonomes.tresor;

public class Tresor {
    // Attributs
    private String addon;
    private int positionX;
    private int positionY;
    private int valeur;

    // Constructeur
    public Tresor(String addon, int positionX, int positionY, int valeur) {
        this.addon = addon;
        this.positionX = positionX;
        this.positionY = positionY;
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "Tresor{" +
                "addon='" + addon + '\'' +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", valeur=" + valeur +
                '}';
    }

    // Getters et setters
    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}