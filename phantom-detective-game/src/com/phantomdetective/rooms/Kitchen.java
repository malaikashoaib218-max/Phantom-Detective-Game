package com.phantomdetective.rooms;

public class Kitchen extends Room {

    public Kitchen() {
        super("Kitchen",
              "The mansion's large kitchen. "
            + "Pots and pans hang from iron hooks and a cold fireplace sits empty.",
              false);
        addExit("Dining Room");
    }

    @Override
    public String getAmbience() {
        return "A half-prepared meal sits on the worktop — whoever started cooking never came back to finish it. "
             + "A door to the garden stands slightly open.";
    }
}
