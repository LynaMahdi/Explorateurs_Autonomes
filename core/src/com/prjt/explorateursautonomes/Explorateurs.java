package com.prjt.explorateursautonomes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.prjt.explorateursautonomes.joueur.Joueur;
import com.prjt.explorateursautonomes.joueur.JoueurThread;
import com.prjt.explorateursautonomes.monstre.Monstre;
import com.prjt.explorateursautonomes.tresor.Tresor;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Explorateurs implements Screen {
	private SpriteBatch batch;
	private Texture imgMonstre, Bomb;
	private Texture[] tresorImages; // Images des trésors
	private final ArrayList<Joueur> listOfPlayers = new ArrayList<Joueur>();
	private final ArrayList<Monstre> listOfMonsters = new ArrayList<Monstre>();
	private Monstre[] monstre;

	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tmr;
	private Tresor[] tresor; // Tableau des trésors


	private OrthographicCamera camera = new OrthographicCamera();
	private ThreadPoolExecutor pathThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
	private Joueur joueur1, joueur2, joueur3, joueur4;


	@Override
	public void show() {
		batch = new SpriteBatch();


		// Initialisez votre carte TiledMap
		tiledMap = new TmxMapLoader().load("map.tmx");
		tmr = new OrthogonalTiledMapRenderer(tiledMap);
		camera.setToOrtho(false, 950, 950);


		Bomb = new Texture("Images/explosion_donut.png");

		// Initialisez la texture pour le joueur

		imgMonstre=new Texture("Images/squelette_rest.png");

		// Initialisez la texture pour les trésors
		tresorImages = new Texture[4];
		tresorImages[0] = new Texture("tresors/tresor_amethyst.png");
		tresorImages[1] = new Texture("tresors/tresor_diamond.png");
		tresorImages[2] = new Texture("tresors/tresor_emerald.png");
		tresorImages[3] = new Texture("tresors/tresor_ruby.png");

		// Initialisez les instances des joueurs
		joueur1 = new Joueur(50, 20, 5, 8 * 16, 320 - 16, 1);
		joueur2 = new Joueur(50, 20, 5, 896, 384, 1);
		joueur3 = new Joueur(50, 20, 5, 144, 672, 1);
		joueur4 = new Joueur(50, 20, 5, 784, 768, 1);
		listOfPlayers.add(joueur1);listOfPlayers.add(joueur2);listOfPlayers.add(joueur3);listOfPlayers.add(joueur4);



		// Générez 4 trésors et monstres
		generateTreasures();
		generateMonsters();


		// Lancez le thread pour chaque joueur
		for (int i = 0; i < listOfPlayers.size(); i++) {
			JoueurThread joueurThread = new JoueurThread(i, this, tresor, tiledMap, tresor[i], listOfPlayers.get(i), monstre,monstre[i]);
			joueurThread.start();

		}


	}

	private void generateMonsters() {
		monstre = new Monstre[tresor.length];

		for (int i = 0; i < tresor.length; i++) {
			// Obtenez la position du trésor
			int tresorX = tresor[i].getPositionX();
			int tresorY = tresor[i].getPositionY();

			// Générez la position aléatoire à côté du trésor
			int monsterX, monsterY;
			do {
				// Générer des positions aléatoires à proximité du trésor
				Random random = new Random();
				monsterX = tresorX + random.nextInt(48) - 24; // Décalage X entre -24 et 24
				monsterY = tresorY + random.nextInt(48) - 24; // Décalage Y entre -24 et 24
			} while (!isPositionValid(monsterX, monsterY)); // Vérifiez si la position est valide

			// Créez le monstre à cette position
			monstre[i] = new Monstre(monsterX, monsterY,  45, 10, 5, 8, 5);
		}
	}

	private boolean isPositionValid(int x, int y) {
		// Vérifiez si la position est à l'intérieur des limites de la carte
		if (x < 0 || y < 0 || x >= tiledMap.getProperties().get("width", Integer.class) * 16 ||
				y >= tiledMap.getProperties().get("height", Integer.class) * 16) {
			return false; // En dehors des limites de la carte
		}

		// Vérifiez si la position est sur un obstacle
		TiledMapTileLayer obstacleLayer = (TiledMapTileLayer) tiledMap.getLayers().get("obstacle");
		TiledMapTileLayer.Cell cell = obstacleLayer.getCell(x / obstacleLayer.getTileWidth(), y / obstacleLayer.getTileHeight());
		return cell == null || cell.getTile() == null; // Valide si la cellule est vide (pas d'obstacle)
	}


	private void generateTreasures() {
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
				{480, 950, 5, 450}, // Labyrinthe 2
				{5, 420, 480, 950}, // Labyrinthe 3
				{450, 950, 480,950} // Labyrinthe 4
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
			} while (!Pos(posX, posY, obstacleLayers)); // Vérifier si la position est valide(pas dans un obstacle)

			// Créer le trésor pour le labyrinthe actuel
			tresor[i] = new Tresor("addon", posX, posY, i);
		}
	}

	// Checker si la position du trésor est valide
	private boolean Pos(int x, int y, TiledMapTileLayer[] obstacleLayers) {
		for (TiledMapTileLayer obstacleLayer : obstacleLayers) {
			// Vérifier si la position n'est pas sur un obstacle dans l'une des couches
			TiledMapTileLayer.Cell cell = obstacleLayer.getCell(x / obstacleLayer.getTileWidth(), y / obstacleLayer.getTileHeight());
			if (cell != null && cell.getTile() != null) {
				return false; // Obstacle trouvé
			}
		}
		return true; // Pas d'obstacle
	}

	@Override
	public void render(float delta) {

        //deplacer les 04 joueurs
		for (int i = 0; i < listOfPlayers.size(); i++) {
			listOfPlayers.get(i).movePlayerinThePath(listOfPlayers.get(i).getPath()/*,monstre[i]*/);

		}

		// Déplacer la caméra pour suivre le joueur
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();

		// Rendu de la carte Tiled
		tmr.setView(camera);
		tmr.render();
		BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));

		// Rendu des joueurs
		batch.setProjectionMatrix(camera.combined);
		batch.begin();


		//afficher les joueurs
		for (int i = 0; i < listOfPlayers.size(); i++) {
			Joueur joueur = listOfPlayers.get(i);
			Tresor t = tresor[i];

			batch.draw(joueur.getImage(), joueur.getX(), joueur.getY());
			if (joueur.getPointsDeVie() <= 10) {
				batch.draw(Bomb, joueur.getX()+16, joueur.getY()+16);
			}
		}

		//afficher les tresors
		for (Tresor t : tresor) {
			Texture tresorTexture = tresorImages[t.getValeur()]; // Obtenir l'image du trésor
			batch.draw(tresorTexture, t.getPositionX(), t.getPositionY());
		}

		//afficher les monstres

		for (Monstre m : monstre) {
			batch.draw(imgMonstre, m.getPositionX(), m.getPositionY());
		}

		//afficher le nombre de tresors récoltés pour chaque joueur
		bitmapFont.draw(batch,"*" + joueur2.getTresorsRecoltes(), joueur2.getX(), joueur2.getY());
		bitmapFont.draw(batch, "*" +joueur1.getTresorsRecoltes(), joueur1.getX(), joueur1.getY());
		bitmapFont.draw(batch, "*" +joueur3.getTresorsRecoltes(), joueur3.getX(), joueur3.getY());
		bitmapFont.draw(batch, "*" +joueur4.getTresorsRecoltes(), joueur4.getX(), joueur4.getY());



		batch.end();
	}


	@Override
	public void resize(int width, int height) {
		// Mettre à jour la taille de la vue de la caméra lorsque la fenêtre est redimensionnée
		camera.viewportWidth = 950;
		camera.viewportHeight = 950;
		camera.update();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		// Libérer les ressources utilisées par l'écran
		batch.dispose();
		tiledMap.dispose();
		tmr.dispose();

	}

	public Tresor[] getTresor() {
		return tresor;
	}

	public ThreadPoolExecutor getPathThreadPool() {
		return pathThreadPool;
	}

	public Monstre[] getMonstre() {
		return monstre;
	}
}
