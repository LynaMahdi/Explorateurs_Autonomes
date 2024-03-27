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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.prjt.explorateursautonomes.bouttons.StartButton;
import com.prjt.explorateursautonomes.bouttons.StartButtonController;


public class TitleScreen implements Screen {


    Texture img, img2;
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


    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("entrance.png");
        img2 = new Texture("boutton.png");



        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));

        multiplexer = new MyMultiplexer();
        startButtonController = new StartButtonController();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        backGround = new Sprite(img);

        // Redimensionner l'image pour qu'elle s'adapte à la taille de la fenêtre
       // backGround.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backGround.setPosition((Gdx.graphics.getWidth() - backGround.getWidth()) / 2, (Gdx.graphics.getHeight() - backGround.getHeight()) / 2);

        camera.update();
      /*  GlyphLayout layout = new GlyphLayout(bitmapFont, "EXPLORATEURS AUTONOMES");
        float textWidth = layout.width;
        float textHeight = layout.height;*/

        // Positionner le bouton "Start" en bas de la fenêtre
        button = new StartButton(img2);
      //  button.setSize(200, 50);
        button.setPosition(445, 415);
        // Dessiner le texte et le bouton
        batch.begin();
        bitmapFont.draw(batch, "EXPLORATEUR AUTONOMES", Gdx.graphics.getWidth()/2 - button.getWidth()/2, Gdx.graphics.getHeight()/20);
        backGround.draw(batch); // Dessiner l'image
        button.draw(batch); // Dessiner le bouton
        batch.end();
    }





    @Override
    public void render(float v) {
        ScreenUtils.clear(0.6f, 0.3f, 0.1f, 1f);

        batch.begin();
        viewport.apply();

        backGround.draw(batch);


        bitmapFont.draw(batch, "EXPLORATEUR INTELLIGENTS", 100,450); // Dessiner le texte
        button.draw(batch);
        batch.end();

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && button.isWithin(Gdx.input.getX(),Gdx.input.getY())){
            ((Game) Gdx.app.getApplicationListener()).setScreen( new Explorateurs());


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