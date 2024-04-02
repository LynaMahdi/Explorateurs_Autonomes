package com.prjt.explorateursautonomes.joueur;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.prjt.explorateursautonomes.algo.Node;
import com.prjt.explorateursautonomes.monstre.Monstre;

import java.util.ArrayList;
import java.util.List;


public class Joueur {

    // Attributs
    private int pointsDeVie;

    private Texture image;
    private Texture deadImage; // Texture de l'explorateur lorsqu'il est vaincu

    private int tresorsRecoltes=0; //  nombre de trésors collectés
    private String playerMessage=""; // Champ pour stocker le message du joueur

    private int degats;
    private double rangeDetection;
    private float x;
    private float y;
    private float speed;
    private List<Node> path;
    private PlayerState state;


    // Constructeur
    public Joueur(int pointsDeVie, int degats, double rangeDetection, float x, float y, float speed) {
        this.pointsDeVie = pointsDeVie;
        this.degats = degats;
        this.rangeDetection = rangeDetection;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.path = new ArrayList<>();
        this.state = PlayerState.FINDING_TREASURE;
        image = new Texture("Images/crab_rest.png");
        deadImage = new Texture("Images/crab_marche_mort.png"); // Utilisation de la texture "crab_marche_mort.png" pour l'état de défaite


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

    // Méthode pour récupérer le nombre de trésors collectés
    public int getTresorsRecoltes() {
        return tresorsRecoltes;
    }

    // Méthode pour incrémenter le nombre de trésors collectés
    public void incrementerTresorsRecoltes() {
        tresorsRecoltes++;
    }
    public void setPlayerMessage(String playerMessage) {
        this.playerMessage = playerMessage;
    }

    public String getPlayerMessage(){
        return this.playerMessage;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


    public void detecterProximite(Monstre monstre) {
            double distance = Math.abs(this.getX() - monstre.getPositionX())+Math.abs(this.getY() - monstre.getPositionY());
            if (distance <=32) {
                this.setState(PlayerState.COMBATTING);
            }
    }


    // Fonction pour lancer le combat entre le joueur et un monstre
    public int lancerCombat(Monstre monstre) {
        // Tant que le joueur et le monstre sont en vie
        if (this.getPointsDeVie() > 0 && monstre.getPointsDeVie() > 0) {
            // Calculer les dégâts infligés par le joueur au monstre
            int degatsDuJoueur = this.getDegats() - monstre.getArmor();
            // Vérifier si les dégâts sont positifs
            if (degatsDuJoueur > 0) {
                // Infliger des dégâts au monstre
                monstre.recevoirDegats(degatsDuJoueur);
                System.out.println("degat M "+monstre.getPointsDeVie());
            }

            // Vérifier si le monstre est toujours en vie après l'attaque du joueur
            if (monstre.getPointsDeVie() > 0) {
                // Calculer les dégâts infligés par le monstre au joueur
                int degatsDuMonstre = monstre.getDegats();
                // Vérifier si les dégâts sont positifs
                if (degatsDuMonstre > 0) {
                    // Infliger des dégâts au joueur
                    this.recevoirDegats(degatsDuMonstre);
                    System.out.println("degat J "+this.getPointsDeVie());

                }

            }
        }

        if(monstre.getPointsDeVie() < 0){
            System.out.println("tes mort monstre");
        }

        // true si Le joueur a gagné
        return this.getPointsDeVie() ;
    }


    // Méthode pour démarrer le mouvement autonome
    public void movePlayerinThePath(List<Node> path/*,Monstre monstre*/) {
        while (!path.isEmpty() ) {
            Node nextNode = path.get(0);
            float dx = nextNode.getX() - this.getX();
            float dy = nextNode.getY() - this.getY();
            float angle = MathUtils.atan2(dy, dx);
            float moveX = this.getSpeed() * MathUtils.cos(angle);
            float moveY = this.getSpeed() * MathUtils.sin(angle);
            this.setX(this.getX() + moveX);
            this.setY(this.getY() + moveY);

            if (Math.abs(this.getX() - nextNode.getX()) < this.getSpeed() &&
                    Math.abs(this.getY() - nextNode.getY()) < this.getSpeed()) {
                path.remove(0);
            } else {
                break; // Sortir de la boucle si le joueur n'est pas encore arrivé au prochain nœud
            }
        }
    }


    public Texture getImage(){
        return this.image;
    }
    // Méthode pour définir la texture de l'explorateur
    public void setImage(Texture texture) {
        this.image = texture;
    }

    public Texture getDeadTexture() {
        return this.deadImage;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    public List<Node> getPath() {
        return path;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

}

