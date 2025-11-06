import java.util.ArrayList;

public class Character {
    private String name;
    private int health;
    private boolean isVisable;

    public Character(String name) {
        this.name = name;
        health = 100;
        isVisable = true;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void inflictEffect(Effects effect) {
        switch (effect) {
            case HEALING:
                health += 30;
                if (health > 100) health = 100;
                break;
            case IVISABILITY:
                isVisable = false;
                break;
            case POISION:
                break;
        }
    }

    void takeDamage(int damage) {
        health -= damage;
    }
}
