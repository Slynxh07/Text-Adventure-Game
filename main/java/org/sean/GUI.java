package org.sean;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {


    final public static ZorkULGame game = new ZorkULGame();

    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/org/sean/fonts/VT323-Regular.ttf"), 16);
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("gui-view.fxml"));
        Parent root = fxmlLoader.load();
        GUIController controller = fxmlLoader.getController();
        game.setController(controller);
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("FXML Example");

        stage.setOnCloseRequest(event -> {
            ZorkUL.quit();
        });

        new Thread(game).start();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}