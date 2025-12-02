package org.sean;

import java.util.Scanner;

public class Parser {
    private CommandWords commands;

    public Parser() {
        commands = new CommandWords();
    }

    public Command getCommand(String inputText) {
        String word1 = null;
        String word2 = null;

        Scanner tokenizer = new Scanner(inputText);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        if (commands.isCommand(word1)) {
            return new Command(word1, word2);
        } else {
            return new Command(null, word2);
        }
    }

    public String showCommands() {
        return commands.showAll();
    }

    public void enableCheats() {
        commands.enableCheats();
    }
    public void disableCheats() {
        commands.disableCheats();
    }
}
