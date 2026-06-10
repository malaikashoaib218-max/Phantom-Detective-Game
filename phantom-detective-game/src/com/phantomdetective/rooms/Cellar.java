package com.phantomdetective.rooms;

public class Cellar extends Room {

    public Cellar() {
        super("Cellar",
              "A dark, damp cellar beneath the mansion. "
            + "Wine racks line the walls and the only light comes from a single flickering oil lamp.",
              true);
        addExit("Grand Hall");
    }

    @Override
    public String getAmbience() {
        return "Water drips from the stone ceiling. "
             + "In the corner you notice a hidden trapdoor, recently disturbed — the dust around it has been cleared.";
    }
}
