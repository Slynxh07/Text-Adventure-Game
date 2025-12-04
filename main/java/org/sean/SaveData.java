package org.sean;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class SaveData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Player player;
    private HashMap<String, Room> rooms;
    private int roomCount;
    private int invisLimit;
    private int turnCount;
    private int poisonLimit;

    SaveData(Player player, HashMap<String, Room> rooms, int roomCount, int invisLimit, int turnCount, int poisonLimit) {
        this.player = player;
        this.rooms = rooms;
        this.roomCount = roomCount;
        this.invisLimit = invisLimit;
        this.turnCount = turnCount;
        this.poisonLimit = poisonLimit;
    }

    public Player getPlayer() { return player; }
    public HashMap<String, Room> getRooms() { return rooms; }
    public int getInvisLimit() { return invisLimit; }
    public int getRoomCount() { return roomCount; }
    public int getTurnCount() { return turnCount; }
    public int getPoisonLimit() { return poisonLimit; }
}
