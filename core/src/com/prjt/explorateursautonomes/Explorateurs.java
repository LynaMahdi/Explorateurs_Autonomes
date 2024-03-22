package com.prjt.explorateursautonomes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.prjt.explorateursautonomes.algo.Graph;
import com.prjt.explorateursautonomes.algo.Node;
import com.prjt.explorateursautonomes.algo.Pathfinding;
import com.prjt.explorateursautonomes.joueur.Joueur;
import com.prjt.explorateursautonomes.tresor.Tresor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Explorateurs extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img; //image of the explorateur
	Texture[] tresorImages;  //images des tresors

	private static ArrayList<Joueur> listOfPlayers = new ArrayList<Joueur>();

	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tmr;

	TiledMapTileLayer collisionLayer;

	OrthographicCamera camera = new OrthographicCamera();
	Joueur joueur1,joueur2,joueur3,joueur4;
	private Graph graph;
	Tresor[] tresor; // Array of treasures
	List<Node> path;
	public void create() {
		batch = new SpriteBatch();

		// Initialize your TiledMap
		tiledMap = new TmxMapLoader().load("map.tmx");
		tmr = new OrthogonalTiledMapRenderer(tiledMap);
		camera.setToOrtho(false, 950, 950);

		// Initialize the texture for the player
		img = new Texture("crab_rest.png");

		// Initialize the texture for the treasurs
		tresorImages = new Texture[4];
		tresorImages[0] = new Texture("tresors/tresor_amethyst.png");
		tresorImages[1] = new Texture("tresors/tresor_diamond.png");
		tresorImages[2] = new Texture("tresors/tresor_emerald.png");
		tresorImages[3] = new Texture("tresors/tresor_ruby.png");

		// Initialize the players instances
		joueur1 = new Joueur(100, 10, 5, 256, 224, 1);
		joueur2 = new Joueur(100, 10, 5, 896, 384, 1);
		joueur3 = new Joueur(100, 10, 5, 144, 672, 1);
		joueur4 = new Joueur(100, 10, 5, 784, 768, 1);

		listOfPlayers.add(joueur1);listOfPlayers.add(joueur2);listOfPlayers.add(joueur3);listOfPlayers.add(joueur4);


		// Generate 5 treasures
		generateTreasures(5);

		TiledMapTileLayer[] obstacleLayers = {
				(TiledMapTileLayer) tiledMap.getLayers().get("obstacle"),
				(TiledMapTileLayer) tiledMap.getLayers().get("mur supp2"),
				(TiledMapTileLayer) tiledMap.getLayers().get("mur inférieur"),
				(TiledMapTileLayer) tiledMap.getLayers().get("mur supérieur"),
				(TiledMapTileLayer) tiledMap.getLayers().get("sol supp"),
				(TiledMapTileLayer) tiledMap.getLayers().get("déco")
		};
		int TreasurPosX=tresor[0].getPositionX();
		int TreasurPosY=tresor[0].getPositionY();
		System.out.println(Pos(256,224, obstacleLayers));
		this.graph = Pathfinding.buildGraph(tiledMap);
		System.out.println(this.graph);
		path = Pathfinding.AStar(graph, new Node(256, 224, false), new Node(256, 224, false));
		System.out.println(path);
	}

	private void generateTreasures(int numberOfTreasures) {
		int labyrinthCount = 4; // Nombre de labyrinthes dans la carte
		tresor = new Tresor[labyrinthCount]; // Initialiser le tableau de trésors

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

		for (int i = 0; i < labyrinthCount; i++) {
			int posX, posY;
			do {
				// Générer des coordonnées aléatoires à l'intérieur des limites du labyrinthe actuel
				posX = random.nextInt(labyrinthBounds[i][1] - labyrinthBounds[i][0]) + labyrinthBounds[i][0];
				posY = random.nextInt(labyrinthBounds[i][3] - labyrinthBounds[i][2]) + labyrinthBounds[i][2];
				// Convertir en multiples de 16
				posX = (int) (Math.round(posX / 16.0f) * 16.0f);
				posY = (int) (Math.round(posY / 16.0f) * 16.0f);
			} while (!Pos(posX, posY, obstacleLayers)); // Vérifier si la position est valide

			// Vérifier si la position générée est à l'intérieur du labyrinthe actuel
			// Générer une valeur aléatoire pour le trésor
			int valeur = MathUtils.random(1, 10); // Plage d'exemple: 1 à 10

			// Créer le trésor pour le labyrinthe actuel
			tresor[i] = new Tresor("addon", posX, posY, i);
			System.out.println("x= " + tresor[i].getPositionX() + "y="+tresor[i].getPositionY() +"valeur= "+ tresor[i].getValeur());
		}
	}



	//cheker si la position du tresor est valide
	private boolean Pos(int x, int y, TiledMapTileLayer[] obstacleLayers) {
		for (TiledMapTileLayer obstacleLayer : obstacleLayers) {
			// Check if the position is not on an obstacle in any of the layers
			TiledMapTileLayer.Cell cell = obstacleLayer.getCell(x / obstacleLayer.getTileWidth(), y / obstacleLayer.getTileHeight());
			if (cell != null && cell.getTile() != null) {
				return false; // obstacle trouvé
			}

		}

		return true; // pas d'obstacle
	}



	@Override
	public void render() {

		// les collisions avec les murs
		boolean wallCollision = checkCollisionWithObstacleLayer("mur supp2");
		boolean obstacleCollision = checkCollisionWithObstacleLayer("obstacle");
		boolean downWallCollision = checkCollisionWithObstacleLayer("mur inférieur");
		boolean upWallCollision = checkCollisionWithObstacleLayer("mur supérieur");

		//joueur.updatePreviousPosition();

		//System.out.println("wall " + wallCollision + " obstacle " + obstacleCollision + downWallCollision);

		joueur1.movePlayerAlongPath(path);

		// Add more layers as needed

		// Move the camera to follow the player
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
		// Render the Tiled map
		tmr.setView(camera);
		tmr.render();

		// Render the player
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, joueur1.getX(), joueur1.getY());
		for (Tresor t : tresor) {
			Texture tresorTexture = tresorImages[t.getValeur()]; // Obtenir l'image  du trésor
			batch.draw(tresorTexture, t.getPositionX(), t.getPositionY());
		}
		batch.end();
	}


	//cheker les collision avec les murs
	private boolean checkCollisionWithObstacleLayer(String layerName) {
		TiledMapTileLayer obstacleLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
		return joueur1.isCollision(obstacleLayer);
		/*
		if (joueur.isCollision(obstacleLayer, joueur.getX()+joueur.getSpeed(), joueur.getY()+joueur.getSpeed())) {
			joueur.handleCollisionWithObstacle(obstacleLayer);
		}

		 */
	}



	@Override
	public void dispose() {
		batch.dispose();
		tiledMap.dispose();
		tmr.dispose();
		img.dispose();
	}
}