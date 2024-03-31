package com.prjt.explorateursautonomes.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

// Classe pour gérer la saisie de texte
public class TextHandler {
    private TextField textField;

    public TextHandler(Stage stage, Skin skin) {
        TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textField = new TextField("", textFieldStyle);
        textField.setMessageText("Enter a number");
        textField.setWidth(200);
        textField.setPosition((Gdx.graphics.getWidth() - textField.getWidth()) / 2, Gdx.graphics.getHeight() / 2);

        stage.addActor(textField);
    }

    public String getText() {
        return textField.getText();
    }

    public void clearText() {
        textField.setText("");
    }

    public void draw(SpriteBatch batch) {
        textField.draw(batch, 1);
    }
}
