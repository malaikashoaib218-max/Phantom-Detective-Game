package com.phantomdetective.rooms;

public class Library extends Room {

    public Library() {
        super("Library",
              "A vast, dusty library filled with ancient books and towering shelves. "
            + "Cobwebs hang from the ceiling and candles flicker in the draught.",
              false);
        addExit("Grand Hall");
        addExit("Study");
    }

    @Override
    public String getAmbience() {
        return "The smell of old parchment fills the air. "
             + "A grandfather clock ticks loudly in the corner.";
    }
}
