package com.prjt.explorateursautonomes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Camera;
import com.prjt.explorateursautonomes.world.GameMap;
import com.prjt.explorateursautonomes.world.TileType;
import com.prjt.explorateursautonomes.world.TiledGameMap;

import com.badlogic.gdx.ApplicationAdapter;
public class Explorateurs extends ApplicationAdapter {


	SpriteBatch batch;
	Texture img;
	GameMap gameMap;
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tmr;
	OrthographicCamera camera=new OrthographicCamera();

	@Override
	public void create () {
		tiledMap=new TmxMapLoader().load("map.tmx");
		tmr= new OrthogonalTiledMapRenderer(tiledMap);
		camera.setToOrtho(false,512,512);
	}

	@Override
	public void render () {
		tmr.setView(camera);
		tmr.render();
	}
	
	@Override
	public void dispose () {

	}



	public SpriteBatch getBatch() {
		return batch;
	}

}
