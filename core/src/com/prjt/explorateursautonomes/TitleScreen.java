package com.prjt.explorateursautonomes;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.prjt.explorateursautonomes.bouttons.StartButton;
import com.prjt.explorateursautonomes.bouttons.StartButtonController;
import com.prjt.explorateursautonomes.world.TextHandler;


public class TitleScreen implements Screen {

    private StringBuilder inputText;

    Texture img, img2;
    private Game game;

    Sprite backGround;
    StartButton button;
    SpriteBatch batch;
    TextField textField;
    int userInput = 0;
    static MyMultiplexer multiplexer;
    StartButtonController startButtonController;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Explorateurs explorateurs;
    Stage stage;
    BitmapFont bitmapFont;
    Skin skin;
    TextHandler textInputHandler;

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("Images/entrance.png");
        img2 = new Texture("buttons/boutton.png");




        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));

        //initilisser le multiplexer et le boutton demarrer
        multiplexer = new MyMultiplexer();
        startButtonController = new StartButtonController();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        backGround = new Sprite(img);


        backGround.setPosition((Gdx.graphics.getWidth() - backGround.getWidth()) / 2, (Gdx.graphics.getHeight() - backGround.getHeight()) / 2);

        camera.update();


        // Positionner le bouton Demarrer en haut de la fenêtre
        button = new StartButton(img2);
        button.setPosition(445, 415);

    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.6f, 0.3f, 0.1f, 1f);

        batch.begin();
        viewport.apply();

        backGround.draw(batch);


        bitmapFont.draw(batch, "EXPLORATEURS       INTELLIGENTS", 100,450); // Dessiner le texte
        button.draw(batch);


        batch.end();

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && button.isWithin(Gdx.input.getX(),Gdx.input.getY())){
            ((Game) Gdx.app.getApplicationListener()).setScreen( new SecondScreen());
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
        img.dispose();
        img2.dispose();

    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }
}