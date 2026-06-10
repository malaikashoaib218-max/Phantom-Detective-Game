package com.phantomdetective.items;

import com.phantomdetective.interfaces.Interactable;

public class Weapon implements Interactable {
    private String name;
    private String description;
    private boolean isUsed;
    private int phantomDamage;

    public Weapon(String name, String description, int phantomDamage) {
        this.name = name;
        this.description = description;
        this.phantomDamage = phantomDamage;
        this.isUsed = false;
    }

    @Override
    public void interact() {
        System.out.println("You pick up the " + name + ".");
        System.out.println(description);
        System.out.println("This weapon deals " + phantomDamage + " damage to supernatural entities.");
    }

    @Override
    public String getDescription() {
        return name + " — " + description;
    }

    public String use() {
        isUsed = true;
        return "You use the " + name + " against the phantom! [Damage: " + phantomDamage + "]";
    }

    public String getName() {
        return name;
    }

    public int getPhantomDamage() {
        return phantomDamage;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
