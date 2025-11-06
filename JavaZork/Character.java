import java.util.ArrayList;

public class Character {
    private String name;
    private int health;

    public Character(String name) {
        this.name = name;
        health = 100;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void inflictEffect(String effect) {
        System.out.println(name + "has been inflicted with " + effect);
    }

    void takeDamage(int damage) {
        health -= damage;
    }
}
