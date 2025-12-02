package org.sean;

public class Chest<T extends Storable> {
    private T item;

    public void storeItem(T item) {
        this.item = item;
    }

    public Storable getLoot() { return this.item; }

    public void emptyChest() { this.item = null; }

    public boolean isEmpty() {
        return this.item == null;
    }

    public String getItemInfo() {
        return this.item.getInfo();
    }
    public String getItemName() { return this.item.getName(); }
}
