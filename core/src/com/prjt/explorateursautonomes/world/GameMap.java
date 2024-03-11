package com.prjt.explorateursautonomes.world;

import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class GameMap {

    public abstract void render(OrthographicCamera camera);
    public abstract void update(float delta);
    public abstract void dipose();

    /**
     * Avoir la map par les positions des pixels dans le layer
     * @param Layer
     * @param x
     * @param y
     * @return
     */

    public   TileType getTileTypeByLocation(int Layer,float x,float y){
        return this.getTileTypeByCoordinate(Layer,(int)(x/TileType.TILE_SIZE),(int)(y/TileType.TILE_SIZE));
    }

    /**
     * Avoir la map en fonction de ses coordonnées selon le layer
     * @param Layer
     * @param col
     * @param row
     * @return
     */
    public abstract  TileType getTileTypeByCoordinate(int Layer,int col,int row);

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();


}
