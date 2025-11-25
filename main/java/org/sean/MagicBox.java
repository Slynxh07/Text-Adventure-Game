package org.sean;

public class MagicBox<T extends Storable> {
    private T item;

    public void storeItem(T item) {
        this.item = item;
    }

    public String getItemInfo() {
        return this.item.getInfo();
    }
}
