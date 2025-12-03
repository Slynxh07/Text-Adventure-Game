package org.sean;

public class Enemy extends Character {
    private final int damage;
    private Effects effect;

    Enemy(String name, int damage) {
        super(name);
        this.damage = damage;
        effect = null;
    }

    Enemy(String name, int damage, Effects effect) {
        this(name, damage);
        this.effect = effect;
    }

    public void attack(Player target) {
        target.takeDamage(damage);
        if (effect != null) {
            target.inflictEffect(effect);
        }
    }

    @Override
    public void die() {
        this.alive = false;
    }
}