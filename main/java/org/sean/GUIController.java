package org.sean;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class GUIController {

    @FXML
    private TextField inputText;

    @FXML
    private TextArea outputText;

    @FXML
    private ImageView roomImage, mapImage;

    @FXML
    private TextArea inventoryText;

    @FXML
    private ProgressBar healthBar;

    private Image cell, hallway, dinningHall, gaurdOffice, sewer1, sewer2, artifactRoom, mainHall, courtyard, map;

    @FXML
    private void initialize() {
        cell = new Image(getClass().getResource("/org/sean/images/Cell.png").toExternalForm());
        hallway = new Image(getClass().getResource("/org/sean/images/Hallway.png").toExternalForm());
        dinningHall = new Image(getClass().getResource("/org/sean/images/DinningHall.png").toExternalForm());
        gaurdOffice = new Image(getClass().getResource("/org/sean/images/GaurdOffice.png").toExternalForm());
        sewer1 = new Image(getClass().getResource("/org/sean/images/Sewer1.png").toExternalForm());
        sewer2 = new Image(getClass().getResource("/org/sean/images/Sewer2.png").toExternalForm());
        artifactRoom = new Image(getClass().getResource("/org/sean/images/ArtifactRoom.png").toExternalForm());
        mainHall = new Image(getClass().getResource("/org/sean/images/MainHall.png").toExternalForm());
        courtyard = new Image(getClass().getResource("/org/sean/images/Courtyard.png").toExternalForm());
        map = new Image(getClass().getResource("/org/sean/images/Map.png").toExternalForm());
        inputText.setOnKeyPressed(this::keyPress);
        outputText.setText(ZorkUL.WELCOME_MESSAGE);
        roomImage.setImage(cell);
        mapImage.setImage(map);
        inventoryText.setText("Inventory");
    }

    public void outputGUI(String s) {
        Platform.runLater(() -> outputText.appendText("\n" + s));
    }

    public void addInventory(String s) {
        Platform.runLater(() -> inventoryText.appendText("\n" + s));
    }

    public void setHealthBar(float health) {
        float progress = health / 100;
        healthBar.setProgress(progress);
    }

    public void removeInventory(String s) {
        String currText = inventoryText.getText();
        String[] split = currText.split("\\s+");
        ArrayList<String> itemNames = new ArrayList<String>();
        for (String string : split) {
            if (!s.equals(string)) itemNames.add(string);
        }
        String finalText = "";
        for (String string : itemNames) {
            if (string.equals("Inventory")) finalText += string;
            else finalText += ("\n" + string);
        }
        inventoryText.clear();
        inventoryText.setText(finalText);
    }

    public void setRoomImage(String roomName) {
        switch (roomName) {
            case "cell":
                roomImage.setImage(cell);
                break;
            case "hallway":
                roomImage.setImage(hallway);
                break;
            case "diningHall":
                roomImage.setImage(dinningHall);
                break;
            case "guardOffice":
                roomImage.setImage(gaurdOffice);
                break;
            case "sewer1":
                roomImage.setImage(sewer1);
                break;
            case "sewer2":
                roomImage.setImage(sewer2);
                break;
            case "artifactRoom":
                roomImage.setImage(artifactRoom);
                break;
            case "mainHall":
                roomImage.setImage(mainHall);
                break;
            case "courtyard":
                roomImage.setImage(courtyard);
                break;
        }
    }

    public void newLine() {
        outputText.appendText("\n");
    }

    private void keyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            String input = inputText.getText();
            inputText.clear();
            outputText.appendText("\n" + input);
            if (ZorkUL.isRunning()) {
                Command command = ZorkULGame.parser.getCommand(input);
                GUI.game.processCommand(command);
            }
        }
    }
}
