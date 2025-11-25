package org.sean;

public class Enemy extends Character {
    static int count = 0;
    private int enemyId;

    Enemy(String name) {
        super(name);
        enemyId = count;
        count++;
    }

    @Override
    public void die() {
        
    }
}