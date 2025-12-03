package org.sean;

import java.io.*;
import java.util.HashMap;

public class SaveManager implements Serializable {
    public static void saveGame(Player player, HashMap<String, Room> rooms, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(new SaveData(player, rooms, player.getRoomCount(), player.getInvisLimit()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SaveData loadGame(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (SaveData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
