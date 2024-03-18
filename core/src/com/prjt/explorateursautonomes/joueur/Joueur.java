package com.prjt.explorateursautonomes.joueur;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.prjt.explorateursautonomes.monstre.Monstre;
import com.prjt.explorateursautonomes.world.GameMap;


public class Joueur {

    // Attributs
    private int pointsDeVie;
    private float prevX;
    private float prevY;

    private int degats;
    private double rangeDetection;
    private float x;
    private float y;
    private float speed;

    // Constructeur
    public Joueur(int pointsDeVie, int degats, double rangeDetection, float x, float y, float speed) {
        this.pointsDeVie = pointsDeVie;
        this.degats = degats;
        this.rangeDetection = rangeDetection;
        this.x = x;
        this.y = y;
        this.speed = speed;
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

    // Méthode pour démarrer le mouvement autonome
    public void updatePosition() {
        // Simulate autonomous movement
        x += speed;
        y += speed;

        float mapWidth = 950;
        float mapHeight = 950;
        float playerWidth = 50;
        float playerHeight = 50;

        // ne pas depasser la limite de la map
        x = MathUtils.clamp(x, 0, mapWidth - playerWidth);
        y = MathUtils.clamp(y, 0, mapHeight - playerHeight);
    }

    //check si il a eu une collision avec un mur
    public boolean isCollision(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) (x / obstacleLayer.getTileWidth());
        int cellY = (int) (y / obstacleLayer.getTileHeight());

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
    }

    //revenir a lancienne case en case de collisions
    public void handleCollisionWithObstacle(TiledMapTileLayer obstacleLayer) {
        x = prevX;
        y = prevY;
    }

    // Method to update previous position
    public void updatePreviousPosition() {
        prevX = x;
        prevY = y;
    }

}

