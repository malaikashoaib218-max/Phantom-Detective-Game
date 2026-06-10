package com.phantomdetective.rooms;

public class EastWing extends Room {

    public EastWing() {
        super("East Wing",
              "A long dark corridor in the east wing of the mansion. "
            + "Doors hang open on either side, swaying in an unexplained breeze.",
              true);
        addExit("Grand Hall");
        addExit("Master Bedroom");
        addExit("Secret Room");
    }

    @Override
    public String getAmbience() {
        return "The temperature drops sharply as you step in. "
             + "Strange scratch marks are carved into the wooden floor, forming patterns you don't recognise.";
    }
}
