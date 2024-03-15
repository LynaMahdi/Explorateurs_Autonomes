package com.prjt.explorateursautonomes.joueur;

import com.prjt.explorateursautonomes.monstre.Monstre;
public class Joueur {

        // Attributs
        private int pointsDeVie;
        private int degats;
        private double rangeDetection;

        // Constructeur
        public Joueur(int pointsDeVie, int degats, double rangeDetection) {
            this.pointsDeVie = pointsDeVie;
            this.degats = degats;
            this.rangeDetection = rangeDetection;
        }

        // Méthode pour attaquer un monstre
        public void attaquer(Monstre monstre) {
            monstre.recevoirDegats(this.degats);
        }

        // Méthode pour recevoir des dégâts
        public void recevoirDegats(int degats) {
            this.pointsDeVie -= degats;
            if (this.pointsDeVie <= 0) {
                System.out.println("L'explorateur a été vaincu!");
                // Modif l'addon si mort
            }
        }

        // Getters et setters
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

        public double getRangeDetection() {
            return rangeDetection;
        }

        public void setRangeDetection(double rangeDetection) {
            this.rangeDetection = rangeDetection;
        }

}
