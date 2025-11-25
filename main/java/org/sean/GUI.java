package org.sean;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

    final private static ZorkULGame game = new ZorkULGame();

    @Override
    public void start(Stage stage) throws IOException {
        Button north = new Button("North");
        Button south = new Button("South");
        Button east = new Button("East");
        Button west = new Button("West");
        //TextArea textArea = new TextArea();
        TextField textField = new TextField();

        HBox centerBox = new HBox(textField);
        centerBox.setAlignment(Pos.CENTER);

        HBox topBox = new HBox(north);
        topBox.setAlignment(Pos.CENTER);

        HBox bottomBox = new HBox(south);
        bottomBox.setAlignment(Pos.CENTER);

        VBox leftBox = new VBox(west);
        leftBox.setAlignment(Pos.CENTER);

        VBox rightBox = new VBox(east);
        rightBox.setAlignment(Pos.CENTER);

        north.setOnAction(e -> game.goRoom(new Command("go", "north")));
        south.setOnAction(e -> game.goRoom(new Command("go", "south")));
        east.setOnAction(e -> game.goRoom(new Command("go", "east")));
        west.setOnAction(e -> game.goRoom(new Command("go", "west")));

        BorderPane bp = new BorderPane();
        bp.setTop(topBox);
        bp.setBottom(bottomBox);
        bp.setRight(rightBox);
        bp.setLeft(leftBox);
        bp.setCenter(centerBox);

        Scene mainScene = new Scene(bp, 320, 240);
        stage.setTitle("Button Test");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        Thread gameThread = new Thread(game);
        gameThread.start();
        launch(args);
    }
}