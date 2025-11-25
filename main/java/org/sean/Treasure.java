package org.sean;

public class Treasure implements Storable {
    String description;

    Treasure(String description) {
        this.description = description;
    }

    @Override
    public String getInfo() {
        return description;
    }
}
