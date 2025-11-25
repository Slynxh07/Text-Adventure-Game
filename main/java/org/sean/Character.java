package org.sean;

import java.io.Serializable;

abstract public class Character implements Serializable {
    private String name;
    private int health;
    private boolean isVisable;
    protected boolean alive;

    public Character(String name) {
        this.name = name;
        health = 100;
        isVisable = true;
        alive = true;
    }

    public String getName() {
        if (!alive) return null;
        return name;
    }

    public int getHealth() {
        if (!alive) return -420;
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

    abstract public void die();

    public boolean isAlive() { return alive; }
}
