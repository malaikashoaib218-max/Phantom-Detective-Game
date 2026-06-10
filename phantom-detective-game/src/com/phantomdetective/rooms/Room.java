package com.phantomdetective.rooms;

import com.phantomdetective.characters.Character;
import com.phantomdetective.items.Evidence;
import com.phantomdetective.items.Weapon;
import java.util.ArrayList;
import java.util.List;

public abstract class Room {
    private String name;
    private String description;
    private boolean isHaunted;
    private boolean hasBeenVisited;
    private List<Evidence> evidenceList;
    private List<Character> characters;
    private List<Weapon> weapons;
    private List<String> exits;

    public Room(String name, String description, boolean isHaunted) {
        this.name = name;
        this.description = description;
        this.isHaunted = isHaunted;
        this.hasBeenVisited = false;
        this.evidenceList = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.weapons = new ArrayList<>();
        this.exits = new ArrayList<>();
    }

    public abstract String getAmbience();

    public void enter() {
        System.out.println("\n=== " + name.toUpperCase() + " ===");
        System.out.println(description);
        System.out.println(getAmbience());
        if (isHaunted && !hasBeenVisited) {
            System.out.println("You sense a supernatural presence here...");
        }
        hasBeenVisited = true;
        listContents();
    }

    private void listContents() {
        if (!evidenceList.isEmpty()) {
            System.out.println("\nEvidence visible:");
            for (Evidence e : evidenceList) {
                System.out.println("  [E] " + e.getClueName());
            }
        }
        if (!characters.isEmpty()) {
            System.out.println("\nPeople here:");
            for (Character c : characters) {
                System.out.println("  [P] " + c.getName() + " - " + c.getDescription());
            }
        }
        if (!weapons.isEmpty()) {
            System.out.println("\nItems found:");
            for (Weapon w : weapons) {
                System.out.println("  [W] " + w.getName());
            }
        }
        if (!exits.isEmpty()) {
            System.out.println("\nExits: " + String.join(", ", exits));
        }
    }

    public void addEvidence(Evidence evidence) {
        evidenceList.add(evidence);
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void addExit(String exitName) {
        exits.add(exitName);
    }

    public Evidence findEvidence(String name) {
        for (Evidence e : evidenceList) {
            if (e.getClueName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    public Evidence removeEvidence(String name) {
        Evidence found = findEvidence(name);
        if (found != null) {
            evidenceList.remove(found);
        }
        return found;
    }

    public Weapon findWeapon(String name) {
        for (Weapon w : weapons) {
            if (w.getName().equalsIgnoreCase(name)) {
                return w;
            }
        }
        return null;
    }

    public Character findCharacter(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHaunted() {
        return isHaunted;
    }

    public boolean hasBeenVisited() {
        return hasBeenVisited;
    }

    public List<Evidence> getEvidenceList() {
        return new ArrayList<>(evidenceList);
    }

    public List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    public List<Weapon> getWeapons() {
        return new ArrayList<>(weapons);
    }

    public List<String> getExits() {
        return new ArrayList<>(exits);
    }
}
