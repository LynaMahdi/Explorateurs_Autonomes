package com.prjt.explorateursautonomes.monstre;

import com.prjt.explorateursautonomes.algo.Node;
import com.prjt.explorateursautonomes.joueur.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Monstre {
    // Attributs communs à tous les monstres
    private List<Node> pathAddon;
    private int pointsDeVie;
    private int degats;
    private int armor;

    private double rangeDetection;
    private double vitesseDeplacement;
    private int posX;
    private int posY;


    // Constructeur
    public Monstre(int posX, int posY, int pointsDeVie, int degats, int armor, double rangeDetection, double vitesseDeplacement) {
        this.pathAddon = new ArrayList<>();
        this.pointsDeVie = pointsDeVie;
        this.degats = degats;
        this.armor = armor;
        this.rangeDetection = rangeDetection;
        this.vitesseDeplacement = vitesseDeplacement;
        this.posX=posX;
        this.posY=posY;


    }


    // Méthode pour donner des dégâts à une cible
    public void attaquer(Joueur cible) {
        cible.recevoirDegats(this.degats);
    }

    // Méthode pour recevoir des dégâts
    public void recevoirDegats(int degats) {
        this.pointsDeVie-=degats; // Calcul des dégâts subis en tenant compte de l'armure
        if (this.pointsDeVie <= 0) {
            System.out.println("Le monstre a été vaincu!");
        }
    }



    // Getters et setters pour tous les attributs
    public List<Node> getPathAddon() {
        return pathAddon;
    }

    public void setPathAddon(List<Node> pathAddon) {
        this.pathAddon = pathAddon;
    }

    public int getPointsDeVie(){ return pointsDeVie;}

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

    public float getPositionX() {
        return posX;
    }

    public float getPositionY(){
        return posY;
    }
}