package org.sean;

public class Potion extends Item implements Usable {
    Effects effect;
    boolean full;

    public Potion(String name, String description, Effects effect) {
        super(name, description);
        this.effect = effect;
        this.full = true;
    }

    public boolean isFull() {
        return full;
    }

    public void empty() {
        this.full = false;
    }

    @Override
    public void use(Character character) {
        if (character instanceof Player) {
            ((Player) character).inflictEffect(effect);
        }
    }
}