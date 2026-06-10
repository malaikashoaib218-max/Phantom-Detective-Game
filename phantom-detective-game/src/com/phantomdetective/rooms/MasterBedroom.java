package com.phantomdetective.rooms;

public class MasterBedroom extends Room {

    public MasterBedroom() {
        super("Master Bedroom",
              "Lord Blackwood's opulent bedroom. "
            + "The four-poster bed has been ransacked, pillows torn and drawers hanging open.",
              false);
        addExit("East Wing");
    }

    @Override
    public String getAmbience() {
        return "A family portrait sits face-down on the floor. "
             + "The wardrobe door is ajar — something has been hastily removed from inside.";
    }
}
