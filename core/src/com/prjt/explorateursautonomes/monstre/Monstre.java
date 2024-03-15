package com.prjt.explorateursautonomes.monstre;

import com.prjt.explorateursautonomes.joueur.Joueur;

public abstract class Monstre {
    // Attributs communs à tous les monstres
    private String pathAddon;
    private int pointsDeVie;
    private int degats;
    private int armor;
    private double rangeDetection;
    private double vitesseDeplacement;

    // Constructeur
    public Monstre(String pathAddon, int pointsDeVie, int degats, int armor, double rangeDetection, double vitesseDeplacement) {
        this.pathAddon = pathAddon;
        this.pointsDeVie = pointsDeVie;
        this.degats = degats;
        this.armor = armor;
        this.rangeDetection = rangeDetection;
        this.vitesseDeplacement = vitesseDeplacement;
    }

    // Méthode pour donner des dégâts à une cible
    public void attaquer(Joueur cible) {
        cible.recevoirDegats(this.degats);
    }

    // Méthode pour recevoir des dégâts
    public void recevoirDegats(int degats) {
        int degatsSubis = degats - this.armor; // Calcul des dégâts subis en tenant compte de l'armure
        if (degatsSubis > 0) {
            this.pointsDeVie -= degatsSubis;
            if (this.pointsDeVie <= 0) {
                System.out.println("Le monstre a été vaincu!");
                // Ici, vous pouvez ajouter des actions spécifiques lorsque le monstre est vaincu
            }
        }
    }

    // Getters et setters pour tous les attributs
    public String getPathAddon() {
        return pathAddon;
    }

    public void setPathAddon(String pathAddon) {
        this.pathAddon = pathAddon;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = pointsDeVie;
    }

    public int getDegats() {
        return degats;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public double getRangeDetection() {
        return rangeDetection;
    }

    public void setRangeDetection(double rangeDetection) {
        this.rangeDetection = rangeDetection;
    }

    public double getVitesseDeplacement() {
        return vitesseDeplacement;
    }

    public void setVitesseDeplacement(double vitesseDeplacement) {
        this.vitesseDeplacement = vitesseDeplacement;
    }
}