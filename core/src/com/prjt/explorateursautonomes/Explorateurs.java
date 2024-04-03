package com.prjt.explorateursautonomes;

import com.badlogic.gdx.Game;
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
import com.prjt.explorateursautonomes.joueur.JoueurInitializer;
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
	private ArrayList<Joueur> listOfPlayers = new ArrayList<Joueur>();
	private final ArrayList<Monstre> listOfMonsters = new ArrayList<Monstre>();
	private Monstre[] monstre;

	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tmr;
	private Tresor[] tresor; // Tableau des trésors
	BitmapFont bitmapFont;

	private OrthographicCamera camera = new OrthographicCamera();
	private ThreadPoolExecutor pathThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
	private Joueur joueur1, joueur2, joueur3, joueur4;
	private String[] playerMessages; // Tableau pour stocker les messages des joueurs

	private int numberOfPlayers; // Ajouter un attribut pour le nombre de joueurs

	// Modifier le constructeur pour prendre en compte le nombre de joueurs
	public Explorateurs(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public ArrayList<Joueur> getListOfPlayers() {
		return listOfPlayers;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));



		// Initialisez votre carte TiledMap
		tiledMap = new TmxMapLoader().load("map.tmx");
		tmr = new OrthogonalTiledMapRenderer(tiledMap);
		camera.setToOrtho(false, 950, 950);
		playerMessages = new String[listOfPlayers.size()]; // Initialisation du tableau de messages


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
		listOfPlayers = JoueurInitializer.initializePlayers(numberOfPlayers);


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
		int labyrinthCount = numberOfPlayers; // Adjust the number of treasures based on the number of players
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
	private boolean allPlayersDead() {
		for (Joueur joueur : listOfPlayers) {
			if (joueur.getPointsDeVie() >0) {
				return false; // Si au moins un joueur est encore en vie, retournez faux
			}
		}
		return true; // Tous les joueurs sont morts
	}
	// Méthode pour calculer le nombre total de trésors collectés par tous les joueurs
	public int getTotalTresorsCollected() {
		int total = 0;
		for (Joueur joueur : listOfPlayers) {
			total += joueur.getTresorsRecoltes();
		}
		return total;
	}

	// Méthode pour obtenir les statistiques individuelles de chaque joueur
	public String getPlayerStatistics() {
		StringBuilder statistics = new StringBuilder();
		for (int i = 0; i < listOfPlayers.size(); i++) {
			Joueur joueur = listOfPlayers.get(i);
			statistics.append("Le    Joueur ").append(i + 1).append("     a    recolte    ").append(joueur.getTresorsRecoltes()).append("    tresors \n");
		}
		return statistics.toString();
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

		// Rendu des joueurs
		batch.setProjectionMatrix(camera.combined);
		batch.begin();


		//afficher les joueurs
		for (int i = 0; i < listOfPlayers.size(); i++) {
			Joueur joueur = listOfPlayers.get(i);
			Tresor t = tresor[i];

			batch.draw(joueur.getImage(), joueur.getX(), joueur.getY());
			bitmapFont.draw(batch, joueur.getPlayerMessage(), 100, 920-i*32);
			if (joueur.getPointsDeVie() <= 10) {
				int nbJ=i+1;
				bitmapFont.draw(batch, "Alerte !  le   joueur   " + nbJ + "  sante    faible !", 490, 920);
				batch.draw(Bomb, joueur.getX() + 16, joueur.getY() + 16);
			}
			bitmapFont.draw(batch, "*" + joueur.getTresorsRecoltes(), joueur.getX(), joueur.getY());

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
		batch.end();

		// Calcul du nombre total de trésors collectés par tous les joueurs
		int totalTresors = getTotalTresorsCollected();
		System.out.println("Total des trésors collectés : " + totalTresors);

		// Affichage des statistiques individuelles de chaque joueur
		String playerStats = getPlayerStatistics();
		System.out.println("Statistiques des joueurs :\n" + playerStats);

		if (allPlayersDead()) {
			((Game) Gdx.app.getApplicationListener()).setScreen(new StatScreen(getTotalTresorsCollected(), getPlayerStatistics()));
			return; // Arrêter le rendu car nous avons changé d'écran
		}

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
