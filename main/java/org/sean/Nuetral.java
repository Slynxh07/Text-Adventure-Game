package org.sean;

public class Nuetral extends Character implements Interactable {
    String dialog;

    public Nuetral(String name, String dialog) {
        super(name);
        this.dialog = dialog;
    }

    @Override
    public String getDialog() { return dialog; }

    @Override
    public void setDialog(String dialog) { this.dialog = dialog; }

    @Override
    public void die() { return; }
}