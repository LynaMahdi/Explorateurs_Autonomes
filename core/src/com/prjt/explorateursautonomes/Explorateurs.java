package com.prjt.explorateursautonomes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.prjt.explorateursautonomes.joueur.Joueur;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


public class Explorateurs extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img; //image of the explorateur
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tmr;

	TiledMapTileLayer collisionLayer;

	OrthographicCamera camera = new OrthographicCamera();
	Joueur joueur;

	@Override
	public void create() {
		batch = new SpriteBatch();

		// Initialize your TiledMap
		tiledMap = new TmxMapLoader().load("map.tmx");
		tmr = new OrthogonalTiledMapRenderer(tiledMap);
		camera.setToOrtho(false, 1000, 950);

		// Initialize the texture for the player
		img = new Texture("crab_rest.png");


		// Initialize the player instance
		joueur = new Joueur(100, 10, 5, 6, 6, 1); // Example parameters

	}


	@Override
	public void render() {
		joueur.updatePreviousPosition();
		joueur.updatePosition();

		// les collisions avec les murs
		checkCollisionWithObstacleLayer("mur supp2");
		checkCollisionWithObstacleLayer("obstacle");
		// Add more layers as needed

		// Move the camera to follow the player
		camera.position.set(joueur.getX(), joueur.getY(), 0);

		// Render the Tiled map
		tmr.setView(camera);
		tmr.render();

		// Render the player
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, joueur.getX(), joueur.getY());
		batch.end();
	}

	private void checkCollisionWithObstacleLayer(String layerName) {
		TiledMapTileLayer obstacleLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
		if (joueur.isCollision(obstacleLayer, joueur.getX(), joueur.getY())) {
			joueur.handleCollisionWithObstacle(obstacleLayer);
		}
	}
	@Override
	public void dispose() {
		batch.dispose();
		tiledMap.dispose();
		tmr.dispose();
		img.dispose();
	}
}