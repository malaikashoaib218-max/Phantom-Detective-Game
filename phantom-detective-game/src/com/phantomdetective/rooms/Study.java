package com.phantomdetective.rooms;

public class Study extends Room {

    public Study() {
        super("Study",
              "Lord Blackwood's private study. "
            + "Papers are strewn across the desk and a safe in the wall hangs open and empty.",
              false);
        addExit("Library");
    }

    @Override
    public String getAmbience() {
        return "A half-written letter lies on the desk. "
             + "The ink is still slightly wet — someone was here very recently.";
    }
}
