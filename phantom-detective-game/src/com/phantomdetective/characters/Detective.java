package com.phantomdetective.characters;

import com.phantomdetective.items.Evidence;
import java.util.ArrayList;
import java.util.List;

public class Detective extends Character {
    private int health;
    private int sanity;
    private List<Evidence> inventory;
    private List<String> notebook;

    public Detective(String name) {
        super(name, "A seasoned detective with a sharp mind and nerves of steel.");
        this.health = 100;
        this.sanity = 100;
        this.inventory = new ArrayList<>();
        this.notebook = new ArrayList<>();
    }

    @Override
    public String speak() {
        return getName() + ": \"I will get to the bottom of this mystery.\"";
    }

    @Override
    public String react(String input) {
        if (input.toLowerCase().contains("ghost") || input.toLowerCase().contains("phantom")) {
            sanity -= 5;
            return getName() + " feels a chill run down their spine. [Sanity -5]";
        }
        return getName() + " carefully notes the information.";
    }

    public void collectEvidence(Evidence evidence) {
        inventory.add(evidence);
        System.out.println("Evidence collected: " + evidence.getClueName());
    }

    public void addNote(String note) {
        notebook.add(note);
    }

    public void takeDamage(int amount) {
        health -= amount;
        System.out.println(getName() + " takes " + amount + " damage! Health: " + health);
    }

    public void loseSanity(int amount) {
        sanity -= amount;
        System.out.println(getName() + " loses " + amount + " sanity! Sanity: " + sanity);
    }

    public boolean isConscious() {
        return health > 0 && sanity > 0;
    }

    public int getHealth() {
        return health;
    }

    public int getSanity() {
        return sanity;
    }

    public List<Evidence> getInventory() {
        return new ArrayList<>(inventory);
    }

    public List<String> getNotebook() {
        return new ArrayList<>(notebook);
    }

    public void showStatus() {
        System.out.println("=== DETECTIVE STATUS ===");
        System.out.println("Name   : " + getName());
        System.out.println("Health : " + health + "/100");
        System.out.println("Sanity : " + sanity + "/100");
        System.out.println("Evidence collected: " + inventory.size());
        System.out.println("Notes taken: " + notebook.size());
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.println("=== INVENTORY ===");
        for (int i = 0; i < inventory.size(); i++) {
            Evidence e = inventory.get(i);
            System.out.println((i + 1) + ". " + e.getClueName() + " - " + e.getClueDetail());
        }
    }

    public void showNotebook() {
        if (notebook.isEmpty()) {
            System.out.println("Your notebook is empty.");
            return;
        }
        System.out.println("=== NOTEBOOK ===");
        for (int i = 0; i < notebook.size(); i++) {
            System.out.println((i + 1) + ". " + notebook.get(i));
        }
    }

    public boolean hasEvidence(String evidenceName) {
        for (Evidence e : inventory) {
            if (e.getClueName().equalsIgnoreCase(evidenceName)) {
                return true;
            }
        }
        return false;
    }
}
