package com.phantomdetective.rooms;

public class GrandHall extends Room {

    public GrandHall() {
        super("Grand Hall",
              "The enormous entrance hall of Blackwood Mansion. "
            + "Portraits of stern-faced ancestors line the walls, their painted eyes seeming to follow you.",
              true);
        addExit("Library");
        addExit("Dining Room");
        addExit("East Wing");
        addExit("Cellar");
    }

    @Override
    public String getAmbience() {
        return "The wind howls outside the tall arched windows. "
             + "One of the portraits looks remarkably like the Phantom you've been hearing about...";
    }
}
