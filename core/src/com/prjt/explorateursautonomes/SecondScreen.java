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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.prjt.explorateursautonomes.bouttons.StartButton;
import com.prjt.explorateursautonomes.bouttons.StartButtonController;


public class SecondScreen implements Screen {


    Texture img, greed, safe;
    private Game game;

    Sprite backGround;
    StartButton buttonSafe,buttonGreed,button2,button3,button4;
    SpriteBatch batch;
    static MyMultiplexer multiplexer;
    StartButtonController startButtonController;

    private OrthographicCamera camera;
    private FitViewport viewport;
    BitmapFont bitmapFont;
    Skin skin;
    private String selectedStrategy = ""; // Initialize as an empty string


    @Override
    public void show() {
        batch = new SpriteBatch();

        safe = new Texture("buttons/safe.png");
        greed = new Texture("buttons/greed.png");
        img = new Texture("Images/entrance.png");



        button2 = new StartButton(new Texture("buttons/2.png"));
        button2.setPosition(150, 30);

       button3 = new StartButton(new Texture("buttons/3.png"));
        button3.setPosition(300, 30);

         button4 = new StartButton(new Texture("buttons/4.png"));
        button4.setPosition(450, 30);

        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));

        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));

        //initilisser le multiplexer et le boutton demarrer
        multiplexer = new MyMultiplexer();
        startButtonController = new StartButtonController();
        backGround = new Sprite(img);
        backGround.setPosition((Gdx.graphics.getWidth() - backGround.getWidth()) / 2, (Gdx.graphics.getHeight() - backGround.getHeight()) / 2);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




        camera.update();


        // Positionner les boutons dans la fenêtre
        buttonSafe = new StartButton(safe);
        buttonGreed= new StartButton(greed);
        buttonGreed.setPosition(350, 7);
        buttonSafe.setPosition(90, 7);
    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.6f, 0.3f, 0.1f, 1f);
        int numberOfPlayers = 0; // Initialisation de la variable nombre Joueuers

        batch.begin();
        viewport.apply();
        backGround.draw(batch);
        bitmapFont.draw(batch, "Selectionner    votre    strategie    et    nombre    de     joueurs", 100, 450); // Dessiner le texte

        if (selectedStrategy == "") {
            // Afficher les boutons de stratégie
            buttonSafe.draw(batch);
            buttonGreed.draw(batch);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                if (buttonSafe.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                    selectedStrategy = "Safe";
                } else if (buttonGreed.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                    selectedStrategy = "Greed";
                }
            }
        }else {
            // Afficher les boutons de nombre en fonction de la stratégie choisie
            if (selectedStrategy.equals("Greed")) {
                button2.draw(batch);
                button3.draw(batch);
                button4.draw(batch);
                // Si un bouton de nombre est sélectionné, mettez à jour numberOfPlayers en conséquence
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    if (button2.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                        numberOfPlayers = 2;
                    } else if (button3.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                        numberOfPlayers = 3;
                    } else if (button4.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                        numberOfPlayers = 4;
                    }
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Explorateurs(numberOfPlayers));
                }
            } else if (selectedStrategy.equals("Safe")) {
                button2.draw(batch);
                button3.draw(batch);
                button4.draw(batch);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    if (button2.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                        numberOfPlayers = 2;
                    } else if (button3.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                        numberOfPlayers = 3;
                    } else if (button4.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                        numberOfPlayers = 4;
                    }
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ExplorateursSafe(numberOfPlayers));
                }
                } else if (selectedStrategy.equals("Safe")) {
                    button2.draw(batch);
                    button3.draw(batch);
                    button4.draw(batch);
                    if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                        if (button2.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                            numberOfPlayers = 2;
                        } else if (button3.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                            numberOfPlayers = 3;
                        } else if (button4.isWithin(Gdx.input.getX(), Gdx.input.getY())) {
                            numberOfPlayers = 4;
                        }
                    //((Game) Gdx.app.getApplicationListener()).setScreen(new Explorateurs());
                  }
            }

        }


        batch.end();

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
        safe.dispose();
        greed.dispose();

    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }
}