package org.sean;

public class Sword extends Item implements Usable {
    int damage = 40;
    Sword(String name, String description) {
        super(name, description);
    }

    @Override
    public void use(Character character) {
        character.takeDamage(damage);
    }
}