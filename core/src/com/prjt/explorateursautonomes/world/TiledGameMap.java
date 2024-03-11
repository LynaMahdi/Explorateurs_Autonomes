package com.prjt.explorateursautonomes.world;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap extends GameMap{


    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    public TiledGameMap () {
        tiledMap = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render (OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


    }

    public void update (float delta) {
    }

    public void dipose(){
        tiledMap.dispose();
    }

    public TileType getTileTypeByCoordinate (int layer, int col, int row) {
        return null;
    }

    @Override
    public int getWidth () {
        return 0;
    }

    @Override
    public int getHeight () {
        return 0;
    }

    @Override
    public int getLayers() {
        return 0;
    }
}
