package com.phantomdetective.rooms;

public class DiningRoom extends Room {

    public DiningRoom() {
        super("Dining Room",
              "A long mahogany dining table dominates this room. "
            + "Half-eaten plates of food sit abandoned, as if the guests fled in a hurry.",
              false);
        addExit("Grand Hall");
        addExit("Kitchen");
    }

    @Override
    public String getAmbience() {
        return "Silverware is scattered on the floor. "
             + "A shattered wine glass near the fireplace suggests a violent struggle took place here.";
    }
}
