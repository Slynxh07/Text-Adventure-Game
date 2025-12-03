package org.sean;

import java.io.Serializable;

abstract public class Character implements Serializable {
    private String name;
    protected int health;
    protected boolean visable;
    protected boolean alive;

    public Character(String name) {
        this.name = name;
        health = 100;
        visable = true;
        alive = true;
    }

    public boolean isVisable() { return visable; }

    public String getName() {
        if (!alive) return null;
        return name;
    }

    public int getHealth() {
        if (!alive) return -420;
        return health;
    }

    void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    abstract public void die();

    public boolean isAlive() { return alive; }
}
