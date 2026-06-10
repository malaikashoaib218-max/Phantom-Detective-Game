package com.phantomdetective.characters;

public abstract class Character {
    private String name;
    private String description;
    private boolean isAlive;

    public Character(String name, String description) {
        this.name = name;
        this.description = description;
        this.isAlive = true;
    }

    public abstract String speak();

    public abstract String react(String input);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAlive() {
        return isAlive;
    }

    protected void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + "] " + name + " - " + description;
    }
}
