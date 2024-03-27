package com.prjt.explorateursautonomes.joueur;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.prjt.explorateursautonomes.algo.Node;
import com.prjt.explorateursautonomes.monstre.Monstre;

import java.util.ArrayList;
import java.util.List;


public class Joueur {

    // Attributs
    private int pointsDeVie;
    private float prevX;
    private float prevY;
    private int tresorsRecoltes=0; //  nombre de trésors collectés

    private int degats;
    private double rangeDetection;
    private float x;
    private float y;
    private float speed;
    private List<Node> path;
    private PlayerState state;
    private Monstre[] monstres;
    private  Monstre monstre;
    private boolean combat;

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
            System.out.println(distance);
            if (distance <=32) {
                this.setState(PlayerState.COMBATTING);
            }
    }


    // Fonction pour lancer le combat entre le joueur et un monstre
    public boolean lancerCombat(Monstre monstre) {
        // Tant que le joueur et le monstre sont en vie
        while (this.getPointsDeVie() > 0 && monstre.getPointsDeVie() > 0) {
            // Calculer les dégâts infligés par le joueur au monstre
            int degatsDuJoueur = this.getDegats() - monstre.getArmor();
            // Vérifier si les dégâts sont positifs
            if (degatsDuJoueur > 0) {
                // Infliger des dégâts au monstre
                monstre.recevoirDegats(degatsDuJoueur);
            }

            // Vérifier si le monstre est toujours en vie après l'attaque du joueur
            if (monstre.getPointsDeVie() > 0) {
                // Calculer les dégâts infligés par le monstre au joueur
                int degatsDuMonstre = monstre.getDegats() - 10; // À CHANGERRR!!!!
                // Vérifier si les dégâts sont positifs
                if (degatsDuMonstre > 0) {
                    // Infliger des dégâts au joueur
                    this.recevoirDegats(degatsDuMonstre);
                }
            }
        }
        if(this.getPointsDeVie()<0){
            //faire disparaitre le joueur
        }
        if(monstre.getPointsDeVie()<0){
            //faire disparaitre
        }

        //true si Le joueur a gagné
        return this.getPointsDeVie() > 0;
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
            /*detecterProximite(monstre);
            if (PlayerState.COMBATTING==this.getState()){
               // this.lancerCombat(monstre);
              //  System.out.println("I'm combating");

                System.out.println("je suis le player "+this+" mon state est "+this.getState() );
                break;
            }*/

            if (Math.abs(this.getX() - nextNode.getX()) < this.getSpeed() &&
                    Math.abs(this.getY() - nextNode.getY()) < this.getSpeed()) {
                path.remove(0);
            } else {
                break; // Sortir de la boucle si le joueur n'est pas encore arrivé au prochain nœud
            }
        }
    }

    //check si il a eu une collision avec un mur
    public boolean isCollisionHD(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) ((x + 15) / obstacleLayer.getTileWidth());
        int cellY = (int) ((y + 15) / obstacleLayer.getTileHeight());

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
    }

    public boolean isCollisionHG(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) ((x) / obstacleLayer.getTileWidth());
        int cellY = (int) ((y + 15) / obstacleLayer.getTileHeight());

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
    }

    public boolean isCollisionBD(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) ((x + 15) / obstacleLayer.getTileWidth());
        int cellY = (int) ((y) / obstacleLayer.getTileHeight());

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
    }

    public boolean isCollisionBG(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) ((x) / obstacleLayer.getTileWidth());
        int cellY = (int) ((y) / obstacleLayer.getTileHeight());

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
    }




    public boolean isCollision(TiledMapTileLayer obstacleLayer) {
        return isCollisionHG(obstacleLayer, x + speed, y + speed) || isCollisionHD(obstacleLayer, x + speed, y + speed) || isCollisionBD(obstacleLayer, x + speed, y + speed) || isCollisionBG(obstacleLayer, x + speed, y + speed);
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

