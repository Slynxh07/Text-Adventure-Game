package org.sean;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class GUIController {

    @FXML
    private TextField inputText;

    @FXML
    private TextArea outputText;

    @FXML
    private void initialize() {
        inputText.setOnKeyPressed(this::keyPress);
        outputText.setText(ZorkUL.WELCOME_MESSAGE);
    }

    public void outputGUI(String s) {
        Platform.runLater(() -> outputText.appendText("\n" + s));
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
