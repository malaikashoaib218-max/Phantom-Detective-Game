package com.phantomdetective.swing;

import com.phantomdetective.characters.Character;
import com.phantomdetective.characters.Detective;
import com.phantomdetective.characters.Phantom;
import com.phantomdetective.characters.Suspect;
import com.phantomdetective.items.Evidence;
import com.phantomdetective.items.Weapon;
import com.phantomdetective.rooms.*;
import java.util.*;

/**
 * Game engine adapted for Java Swing.
 * All output is returned as String instead of printed to console.
 * This cleanly separates game logic from the UI layer.
 */
public class SwingGameEngine {

    private Detective detective;
    private Phantom phantom;
    private Map<String, Room> rooms;
    private Room currentRoom;
    private boolean gameOver;
    private boolean playerWon;
    private int cluesFoundCount;

    private GameStateListener listener;

    public interface GameStateListener {
        void onStateChanged();
        void onGameOver(String message, boolean won);
    }

    public SwingGameEngine(String detectiveName, GameStateListener listener) {
        this.listener = listener;
        this.gameOver = false;
        this.playerWon = false;
        this.cluesFoundCount = 0;
        detective = new Detective(detectiveName);
        setupRooms();
        setupEvidence();
        setupSuspects();
        phantom = new Phantom("Victor Blackwood", "Victor");
        currentRoom = rooms.get("Grand Hall");
    }

    private void setupRooms() {
        rooms = new LinkedHashMap<>();
        rooms.put("Grand Hall",     new GrandHall());
        rooms.put("Library",        new Library());
        rooms.put("Study",          new Study());
        rooms.put("Dining Room",    new DiningRoom());
        rooms.put("Kitchen",        new Kitchen());
        rooms.put("East Wing",      new EastWing());
        rooms.put("Master Bedroom", new MasterBedroom());
        rooms.put("Secret Room",    new SecretRoom());
        rooms.put("Cellar",         new Cellar());
    }

    private void setupEvidence() {
        rooms.get("Library").addEvidence(
            new Evidence("Torn Letter", "A letter torn in half — '...meet me at midnight in the east wing...'", "Library", true));
        rooms.get("Study").addEvidence(
            new Evidence("Empty Safe", "Safe is open and scorched inside — someone burned documents here.", "Study", true));
        rooms.get("Study").addEvidence(
            new Evidence("Fountain Pen", "Expensive pen engraved with initials 'V.B.'", "Study", false));
        rooms.get("Dining Room").addEvidence(
            new Evidence("Shattered Glass", "Wine glass with traces of strange powder at the bottom.", "Dining Room", true));
        rooms.get("Master Bedroom").addEvidence(
            new Evidence("Hidden Diary", "Diary under mattress: 'Victor knows about the will. I am not safe.'", "Master Bedroom", true));
        rooms.get("Cellar").addEvidence(
            new Evidence("Oil Lantern", "Lantern still warm — someone was here recently.", "Cellar", false));
        rooms.get("Cellar").addEvidence(
            new Evidence("Locked Box", "Iron box with a four-digit lock. Something rattles inside.", "Cellar", true));
        rooms.get("Secret Room").addEvidence(
            new Evidence("Ritual Circle", "Red chalk circle with symbols — used to summon something.", "Secret Room", true));
        rooms.get("Kitchen").addEvidence(
            new Evidence("Dropped Glove", "Single black leather glove, monogrammed 'V.B.'", "Kitchen", false));
        rooms.get("Grand Hall").addEvidence(
            new Evidence("Portrait Clue", "Portrait turned face-down. On the back: 'Victor Blackwood — disgraced, 1891.'", "Grand Hall", true));
    }

    private void setupSuspects() {
        rooms.get("Grand Hall").addCharacter(new Suspect(
            "Edmund the Butler",
            "Long-serving butler. Calm and composed, but hiding something.",
            "Resentment of the new heirs.",
            "Claims to have been polishing silver in the pantry all evening.",
            false));
        rooms.get("Library").addCharacter(new Suspect(
            "Lady Margaret",
            "Lord Blackwood's niece. Stands to inherit the mansion.",
            "Stands to inherit the entire estate.",
            "Says she was reading in the library — no one can confirm.",
            false));
        rooms.get("Dining Room").addCharacter(new Suspect(
            "Victor Blackwood",
            "Disgraced cousin. Banished 10 years ago, returned uninvited last week.",
            "Disinherited — wants revenge against the family.",
            "Claims he was at the village pub. Landlord has not confirmed.",
            true));
        rooms.get("Kitchen").addCharacter(new Suspect(
            "Rose the Maid",
            "Quiet, nervous young maid. Worked here 2 years.",
            "Lord Blackwood was about to dismiss her for stealing.",
            "Was in the kitchen preparing supper.",
            false));

        rooms.get("East Wing").addWeapon(
            new Weapon("Silver Crucifix", "Ancient silver crucifix. Feels warm in your hand.", 40));
        rooms.get("Cellar").addWeapon(
            new Weapon("Holy Water Flask", "Flask filled with holy water. Effective against spirits.", 35));
        rooms.get("Secret Room").addWeapon(
            new Weapon("Banishment Scroll", "Scroll with Latin text. Reading it weakens the phantom.", 50));
    }

    public String getRoomDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(currentRoom.getName().toUpperCase()).append(" ===\n");
        sb.append(currentRoom.getDescription()).append("\n\n");
        sb.append(currentRoom.getAmbience()).append("\n");
        if (currentRoom.isHaunted() && !currentRoom.hasBeenVisited()) {
            sb.append("\nYou sense a supernatural presence here...\n");
        }
        if (!currentRoom.getEvidenceList().isEmpty()) {
            sb.append("\nEvidence visible:\n");
            for (Evidence e : currentRoom.getEvidenceList()) {
                sb.append("  [E] ").append(e.getClueName()).append("\n");
            }
        }
        if (!currentRoom.getCharacters().isEmpty()) {
            sb.append("\nPeople here:\n");
            for (Character c : currentRoom.getCharacters()) {
                sb.append("  [P] ").append(c.getName()).append(" — ").append(c.getDescription()).append("\n");
            }
        }
        if (!currentRoom.getWeapons().isEmpty()) {
            sb.append("\nWeapons / Items:\n");
            for (Weapon w : currentRoom.getWeapons()) {
                sb.append("  [W] ").append(w.getName()).append("\n");
            }
        }
        return sb.toString();
    }

    public String processCommand(String input) {
        if (gameOver) return "The game has ended. Please start a new game.";
        String lower = input.trim().toLowerCase();
        String result;

        if (lower.equals("look") || lower.equals("look around") || lower.equals("l")) {
            result = getRoomDescription();
        } else if (lower.startsWith("go ") || lower.startsWith("move ") || lower.startsWith("enter ")) {
            result = handleMove(lower);
        } else if (lower.startsWith("examine ") || lower.startsWith("inspect ") || lower.startsWith("look at ")) {
            result = handleExamine(lower);
        } else if (lower.startsWith("take ") || lower.startsWith("pick up ") || lower.startsWith("collect ")) {
            result = handleTake(lower);
        } else if (lower.startsWith("talk") || lower.startsWith("speak") || lower.startsWith("interrogate")) {
            result = handleTalk(lower);
        } else if (lower.startsWith("use ")) {
            result = handleUse(lower);
        } else if (lower.equals("inventory") || lower.equals("inv") || lower.equals("i")) {
            result = handleInventory();
        } else if (lower.equals("notebook") || lower.equals("notes")) {
            result = handleNotebook();
        } else if (lower.equals("status") || lower.equals("stats")) {
            result = handleStatus();
        } else if (lower.startsWith("accuse")) {
            result = handleAccuse(lower);
        } else if (lower.equals("help") || lower.equals("?")) {
            result = getHelp();
        } else {
            result = "Unknown command. Type 'help' for a list of commands.";
        }

        checkWinLose(result);
        listener.onStateChanged();
        return result;
    }

    private String handleMove(String input) {
        String destination = input.replaceFirst("(?i)(go|move|enter)\\s+to\\s+", "")
                                  .replaceFirst("(?i)(go|move|enter)\\s+", "").trim();
        destination = toTitleCase(destination);

        if (!rooms.containsKey(destination)) {
            return "No place called '" + destination + "' exists.\nAvailable exits: "
                 + String.join(", ", currentRoom.getExits());
        }
        if (!currentRoom.getExits().contains(destination)) {
            return "You cannot reach " + destination + " from here.\nAvailable exits: "
                 + String.join(", ", currentRoom.getExits());
        }

        currentRoom = rooms.get(destination);
        StringBuilder sb = new StringBuilder(getRoomDescription());

        if (currentRoom.isHaunted() && Math.random() < 0.4) {
            sb.append("\n").append(phantom.haunt());
            detective.loseSanity(10);
        }
        return sb.toString();
    }

    private String handleExamine(String input) {
        String target = input.replaceFirst("(?i)(examine|inspect|look at)\\s+", "").trim();
        Evidence evidence = currentRoom.findEvidence(toTitleCase(target));
        if (evidence != null) {
            cluesFoundCount++;
            detective.addNote("Examined: " + evidence.getClueName() + " — " + evidence.getClueDetail());
            String result = "You examine the " + evidence.getClueName() + "...\n" + evidence.getClueDetail();
            if (evidence.isKeyClue()) result += "\n\n[KEY CLUE] This could be important!";
            return result;
        }
        Character npc = findCharacterByName(target);
        if (npc != null) return npc.toString();
        return "You don't see '" + target + "' here.";
    }

    private String handleTake(String input) {
        String target = input.replaceFirst("(?i)(take|pick up|collect)\\s+", "").trim();
        Evidence evidence = currentRoom.removeEvidence(toTitleCase(target));
        if (evidence != null) {
            detective.collectEvidence(evidence);
            cluesFoundCount++;
            return "You collected: " + evidence.getClueName() + "\n" + evidence.getClueDetail();
        }
        return "There is no '" + target + "' here to take.";
    }

    private String handleTalk(String input) {
        String target = input.replaceFirst("(?i)(talk to|talk|speak to|speak|interrogate)\\s+", "").trim();
        Character npc = findCharacterByName(target);
        if (npc instanceof Suspect) {
            Suspect suspect = (Suspect) npc;
            return suspect.interrogate() + "\n\n" + suspect.speak()
                + "\n\nTip: type 'interrogate " + suspect.getName()
                + " alibi' or '...motive' to ask specific questions.";
        }
        if (npc != null) return npc.speak();
        return "There is nobody called '" + target + "' here.";
    }

    private String handleUse(String input) {
        String target = input.replaceFirst("(?i)use\\s+", "").trim();
        Weapon weapon = currentRoom.findWeapon(toTitleCase(target));
        if (weapon == null) return "There is no '" + target + "' here to use.";
        StringBuilder sb = new StringBuilder(weapon.use());
        if (currentRoom.isHaunted()) {
            phantom.weakenPhantom(weapon.getPhantomDamage());
            sb.append("\n\nThe phantom SHRIEKS and weakens! [Phantom Power: ").append(phantom.getPower()).append("]");
            if (phantom.isDefeated()) {
                sb.append("\n\n").append(phantom.reveal());
                sb.append("\n\nNow type 'accuse <name>' to make your final accusation!");
            }
        } else {
            sb.append("\nNo supernatural presence here to use it against.");
        }
        return sb.toString();
    }

    private String handleInventory() {
        List<Evidence> inv = detective.getInventory();
        if (inv.isEmpty()) return "Your inventory is empty.";
        StringBuilder sb = new StringBuilder("=== INVENTORY ===\n");
        for (int i = 0; i < inv.size(); i++) {
            Evidence e = inv.get(i);
            sb.append((i + 1)).append(". ").append(e.getClueName())
              .append(" — ").append(e.getClueDetail()).append("\n");
        }
        return sb.toString();
    }

    private String handleNotebook() {
        List<String> notes = detective.getNotebook();
        if (notes.isEmpty()) return "Your notebook is empty.";
        StringBuilder sb = new StringBuilder("=== NOTEBOOK ===\n");
        for (int i = 0; i < notes.size(); i++) {
            sb.append((i + 1)).append(". ").append(notes.get(i)).append("\n");
        }
        return sb.toString();
    }

    private String handleStatus() {
        return "=== DETECTIVE STATUS ===\n"
             + "Name   : " + detective.getName() + "\n"
             + "Health : " + detective.getHealth() + "/100\n"
             + "Sanity : " + detective.getSanity() + "/100\n"
             + "Clues found  : " + cluesFoundCount + "\n"
             + "Evidence held: " + detective.getInventory().size() + "\n"
             + "Notes taken  : " + detective.getNotebook().size();
    }

    private String handleAccuse(String input) {
        if (cluesFoundCount < 5) {
            return "You need at least 5 clues before making an accusation.\n"
                 + "Clues so far: " + cluesFoundCount + " / 5\n\nKeep investigating!";
        }
        String accusation = input.replaceFirst("(?i)accuse\\s*", "").trim().toLowerCase();
        if (accusation.isEmpty()) {
            return "Accuse who? Type: accuse <name>\n\nSuspects:\n"
                 + "  • Edmund the Butler\n  • Lady Margaret\n  • Victor Blackwood\n  • Rose the Maid";
        }
        if (accusation.contains("victor") || accusation.contains("blackwood")) {
            playerWon = true;
            gameOver = true;
            return "*** CORRECT! ***\n\n" + phantom.reveal()
                 + "\n\nVictor Blackwood used dark rituals to fake his death and haunt "
                 + "the mansion, driving everyone away to reclaim his stolen inheritance.\n\n"
                 + "The phantom is banished. Blackwood Mansion is at peace.\n\n"
                 + "CONGRATULATIONS, " + detective.getName().toUpperCase() + "! YOU SOLVED THE MYSTERY!";
        }
        detective.loseSanity(15);
        return "Wrong accusation! The real culprit is still out there...\n[Sanity -15]\nKeep investigating.";
    }

    private void checkWinLose(String lastOutput) {
        if (gameOver) {
            listener.onGameOver(lastOutput, playerWon);
            return;
        }
        if (!detective.isConscious()) {
            gameOver = true;
            String msg = detective.getHealth() <= 0
                ? "The phantom's curse has claimed you. " + detective.getName() + " collapses.\nGAME OVER."
                : "Your sanity shattered, you flee the mansion screaming. The mystery is never solved.\nGAME OVER.";
            listener.onGameOver(msg, false);
        }
    }

    private Character findCharacterByName(String name) {
        Character c = currentRoom.findCharacter(toTitleCase(name));
        if (c != null) return c;
        for (Character ch : currentRoom.getCharacters()) {
            if (ch.getName().toLowerCase().contains(name.toLowerCase())) return ch;
        }
        return null;
    }

    public String getInitialDescription() { return getRoomDescription(); }
    public Room getCurrentRoom()           { return currentRoom; }
    public Detective getDetective()        { return detective; }
    public int getCluesFoundCount()        { return cluesFoundCount; }
    public boolean isGameOver()            { return gameOver; }

    public List<String> getInventoryNames() {
        List<String> names = new ArrayList<>();
        for (Evidence e : detective.getInventory()) names.add(e.getClueName());
        return names;
    }

    private String toTitleCase(String input) {
        if (input == null || input.isEmpty()) return input;
        StringBuilder sb = new StringBuilder();
        boolean cap = true;
        for (char c : input.toCharArray()) {
            if (java.lang.Character.isWhitespace(c)) { cap = true; sb.append(c); }
            else if (cap) { sb.append(java.lang.Character.toUpperCase(c)); cap = false; }
            else sb.append(c);
        }
        return sb.toString();
    }

    private String getHelp() {
        return "=== COMMANDS ===\n"
             + "go <room>          — Move to an adjacent room\n"
             + "look               — Describe the current room\n"
             + "examine <item>     — Examine something closely\n"
             + "take <item>        — Pick up evidence\n"
             + "interrogate <name> — Question a suspect\n"
             + "use <weapon>       — Use a weapon against the phantom\n"
             + "inventory          — Show collected evidence\n"
             + "notebook           — Show your notes\n"
             + "status             — Show health and sanity\n"
             + "accuse <name>      — Make your accusation (need 5+ clues)\n\n"
             + "=== MAP ===\n"
             + "Grand Hall → Library, Dining Room, East Wing, Cellar\n"
             + "Library    → Study\n"
             + "East Wing  → Master Bedroom, Secret Room\n"
             + "Dining Room→ Kitchen\n\n"
             + "TIP: The culprit's initials appear as evidence throughout the mansion.";
    }
}
