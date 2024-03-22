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
    public void movePlayerinThePath(List<Node> path) {
        while (!path.isEmpty()) {
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

    //check si il a eu une collision avec un mur
    public boolean isCollisionHD(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) ((x + 15) / obstacleLayer.getTileWidth());
        int cellY = (int) ((y + 15) / obstacleLayer.getTileHeight());

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
    }

    public boolean isCollisionHG(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) ((x ) / obstacleLayer.getTileWidth());
        int cellY = (int) ((y + 15) / obstacleLayer.getTileHeight());

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
    }
    public boolean isCollisionBD(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) ((x + 15) / obstacleLayer.getTileWidth());
        int cellY = (int) ((y ) / obstacleLayer.getTileHeight());

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
    }
    public boolean isCollisionBG(TiledMapTileLayer obstacleLayer, float x, float y) {
        int cellX = (int) ((x ) / obstacleLayer.getTileWidth());
        int cellY = (int) ((y ) / obstacleLayer.getTileHeight());

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

    public boolean isCollision(TiledMapTileLayer obstacleLayer) {
        return isCollisionHG(obstacleLayer, x + speed, y + speed) || isCollisionHD(obstacleLayer, x + speed, y + speed) || isCollisionBD(obstacleLayer, x + speed, y + speed) || isCollisionBG(obstacleLayer, x + speed, y + speed) ;
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

