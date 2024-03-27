package com.prjt.explorateursautonomes;


import com.badlogic.gdx.Game;
import com.prjt.explorateursautonomes.joueur.Joueur;

public class MyGame extends Game {
    public static MyGame ref = null;
    Joueur car1,car2;
    TitleScreen screen;

    public MyGame() {
        this.screen = new TitleScreen();
    }
    @Override
    public void create() {
        this.setScreen(screen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }

    @Override
    public TitleScreen getScreen() {
        return screen;
    }

    public static MyGame getInstance() {
        if (ref == null) {
            ref = new MyGame();
        }
        return ref;
    }

}