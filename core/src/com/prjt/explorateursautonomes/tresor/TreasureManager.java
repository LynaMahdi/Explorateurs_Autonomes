package com.prjt.explorateursautonomes.tresor;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Random;

public class TreasureManager {

    public static Tresor generateTreasure(TiledMap tiledMap, Tresor[] tresors, int i) {
        Random random = new Random();

        // Tableau des couches d'obstacles
        TiledMapTileLayer[] obstacleLayers = {
                (TiledMapTileLayer) tiledMap.getLayers().get("obstacle"),
                (TiledMapTileLayer) tiledMap.getLayers().get("mur supp2"),
                (TiledMapTileLayer) tiledMap.getLayers().get("mur inférieur"),
                (TiledMapTileLayer) tiledMap.getLayers().get("mur supérieur"),
                (TiledMapTileLayer) tiledMap.getLayers().get("sol supp"),
                (TiledMapTileLayer) tiledMap.getLayers().get("déco")
        };

        // Définir les limites des labyrinthes
        int[][] labyrinthBounds = {
                {5, 440, 5, 450},   // Labyrinthe 1
                {450, 950, 5, 450}, // Labyrinthe 2
                {5, 420, 480, 950}, // Labyrinthe 3
                {450, 950, 480, 950} // Labyrinthe 4
        };

        int posX, posY;
        do {
            // Générer des coordonnées aléatoires à l'intérieur des limites du labyrinthe actuel
            posX = random.nextInt(labyrinthBounds[i][1] - labyrinthBounds[i][0]) + labyrinthBounds[i][0];
            posY = random.nextInt(labyrinthBounds[i][3] - labyrinthBounds[i][2]) + labyrinthBounds[i][2];
            // Convertir en multiples de 16
            posX = (int) (Math.round(posX / 16.0f) * 16.0f);
            posY = (int) (Math.round(posY / 16.0f) * 16.0f);
        } while (!Pos(posX, posY, obstacleLayers)); // Vérifier si la position est valide(pas dans un obstacle)

        // Créer le trésor pour le labyrinthe actuel
        tresors[i] = new Tresor("addon", posX, posY, i);
        return tresors[i];
    }

    private static boolean Pos(int x, int y, TiledMapTileLayer[] obstacleLayers) {
        for (TiledMapTileLayer obstacleLayer : obstacleLayers) {
            // Check if the position is not on an obstacle in any of the layers
            TiledMapTileLayer.Cell cell = obstacleLayer.getCell(x / obstacleLayer.getTileWidth(), y / obstacleLayer.getTileHeight());
            if (cell != null && cell.getTile() != null) {
                return false; // obstacle trouvé
            }

        }

        return true; // pas d'obstacle
    }

}
