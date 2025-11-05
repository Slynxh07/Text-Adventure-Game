public class Potion extends Item {
    String effect;
    boolean full;

    public Potion(String name, String description, String effect) {
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
        character.inflictEffect(effect);
    }
}