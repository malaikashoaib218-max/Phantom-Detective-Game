package com.phantomdetective.rooms;

public class SecretRoom extends Room {

    public SecretRoom() {
        super("Secret Room",
              "A hidden chamber behind a bookshelf in the east wing. "
            + "This room is not on any official map of the mansion. Ritual symbols are painted on the walls.",
              true);
        addExit("East Wing");
    }

    @Override
    public String getAmbience() {
        return "This is the heart of the haunting. "
             + "The air vibrates with supernatural energy. Whatever happened here — it started in this room.";
    }
}
