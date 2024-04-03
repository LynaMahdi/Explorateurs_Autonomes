package com.prjt.explorateursautonomes;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.prjt.explorateursautonomes.bouttons.StartButton;
import com.prjt.explorateursautonomes.bouttons.StartButtonController;


public class StatScreen implements Screen {


    Texture menu , quitter;

    StartButton bt,bt1;
    SpriteBatch batch;
    static MyMultiplexer multiplexer;
    StartButtonController startButtonController;

    private OrthographicCamera camera;
    private FitViewport viewport;
    BitmapFont bitmapFont;

    private int totalTresorsCollected;
    private String playerStatistics;

    public StatScreen(int totalTresorsCollected, String playerStatistics) {
        this.totalTresorsCollected = totalTresorsCollected;
        this.playerStatistics = playerStatistics;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        menu = new Texture("buttons/menu.png");
        quitter = new Texture("buttons/quitter.png");



        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));

        //initilisser le multiplexer et le boutton demarrer
        multiplexer = new MyMultiplexer();
        startButtonController = new StartButtonController();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




        camera.update();


        // Positionner le bouton Demarrer en haut de la fenêtre
        bt = new StartButton(menu);
        bt1= new StartButton(quitter);
        bt.setPosition(350, 30);
        bt1.setPosition(350, 150);

    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.6f, 0.3f, 0.1f, 1f);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dessiner le titre des statistiques de jeu
        bitmapFont.draw(batch, "Statistiques de jeu :", 200, 450);

        // Dessiner le total des trésors collectés
        bitmapFont.draw(batch, "Total des trésors collectés : " + totalTresorsCollected, 200, 400);

        // Dessiner les statistiques individuelles de chaque explorateur
        String[] lines = playerStatistics.split("\n");
        for (int i = 0; i < lines.length; i++) {
            bitmapFont.draw(batch, lines[i], 200, 350 - i * 30); // Ajustez la position en fonction de l'index
        }

        // Dessiner le bouton
        bt.draw(batch);
        bt1.draw(batch);
        batch.end();

        // Vérifier si le bouton est cliqué
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && bt.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
            // Passer à l'écran suivant
            ((Game) Gdx.app.getApplicationListener()).setScreen(new TitleScreen());
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && bt1.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
            // Passer à l'écran suivant
            Gdx.app.exit();
        }

    }


    @Override
    public void resize(int i, int i1) {

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
        batch.dispose();
        menu.dispose();
        quitter.dispose();

    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }
}