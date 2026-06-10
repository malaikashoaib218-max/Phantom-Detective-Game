package com.phantomdetective.items;

import com.phantomdetective.interfaces.Clue;
import com.phantomdetective.interfaces.Interactable;

public class Evidence implements Clue, Interactable {
    private String name;
    private String detail;
    private String location;
    private boolean keyClue;
    private boolean examined;

    public Evidence(String name, String detail, String location, boolean keyClue) {
        this.name = name;
        this.detail = detail;
        this.location = location;
        this.keyClue = keyClue;
        this.examined = false;
    }

    @Override
    public void interact() {
        examined = true;
        System.out.println("You examine the " + name + "...");
        System.out.println(detail);
        if (keyClue) {
            System.out.println("[KEY CLUE] This could be important to solving the mystery!");
        }
    }

    @Override
    public String getDescription() {
        return name + " (found in " + location + ")";
    }

    @Override
    public String getClueName() {
        return name;
    }

    @Override
    public String getClueDetail() {
        return detail;
    }

    @Override
    public boolean isKeyClue() {
        return keyClue;
    }

    public String getLocation() {
        return location;
    }

    public boolean isExamined() {
        return examined;
    }
}
