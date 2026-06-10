package com.phantomdetective.game;

import com.phantomdetective.characters.Character;
import com.phantomdetective.characters.Detective;
import com.phantomdetective.characters.Phantom;
import com.phantomdetective.characters.Suspect;
import com.phantomdetective.items.Evidence;
import com.phantomdetective.items.Weapon;
import com.phantomdetective.rooms.*;
import java.util.*;

public class GameEngine {

    private Detective detective;
    private Phantom phantom;
    private Map<String, Room> rooms;
    private Room currentRoom;
    private boolean gameRunning;
    private boolean phantomDefeated;
    private int cluesFoundCount;
    private Scanner scanner;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
        this.gameRunning = true;
        this.phantomDefeated = false;
        this.cluesFoundCount = 0;
    }

    public void initialise() {
        setupDetective();
        setupRooms();
        setupEvidence();
        setupSuspects();
        setupPhantom();
        currentRoom = rooms.get("Grand Hall");
    }

    private void setupDetective() {
        System.out.print("Enter your detective's name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Detective Cross";
        detective = new Detective(name);
        System.out.println("\nWelcome, " + detective.getName() + ".");
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
            new Evidence("Torn Letter", "A letter torn in half. It reads: '...meet me at midnight in the east wing...'", "Library", true));
        rooms.get("Study").addEvidence(
            new Evidence("Empty Safe", "The safe is open and empty. The inside is scorched — someone burned documents here.", "Study", true));
        rooms.get("Study").addEvidence(
            new Evidence("Fountain Pen", "An expensive fountain pen engraved with the initials 'V.B.'", "Study", false));
        rooms.get("Dining Room").addEvidence(
            new Evidence("Shattered Glass", "A shattered wine glass. Traces of a strange powder remain at the bottom.", "Dining Room", true));
        rooms.get("Master Bedroom").addEvidence(
            new Evidence("Hidden Diary", "A leather diary hidden under the mattress. The last entry: 'Victor knows about the will. I am not safe.'", "Master Bedroom", true));
        rooms.get("Cellar").addEvidence(
            new Evidence("Oil Lantern", "An oil lantern still warm to the touch — someone was down here recently.", "Cellar", false));
        rooms.get("Cellar").addEvidence(
            new Evidence("Locked Box", "A small iron box, locked with a four-digit code. Something rattles inside.", "Cellar", true));
        rooms.get("Secret Room").addEvidence(
            new Evidence("Ritual Circle", "A circle drawn in red chalk with strange symbols. This was used to summon something.", "Secret Room", true));
        rooms.get("Kitchen").addEvidence(
            new Evidence("Dropped Glove", "A single black leather glove, monogrammed 'V.B.'", "Kitchen", false));
        rooms.get("Grand Hall").addEvidence(
            new Evidence("Portrait Clue", "One portrait has been turned to face the wall. Written on the back: 'Victor Blackwood — disgraced, 1891.'", "Grand Hall", true));
    }

    private void setupSuspects() {
        Suspect butler = new Suspect(
            "Edmund the Butler",
            "The long-serving butler of Blackwood Mansion. Calm and composed, but hiding something.",
            "Loyalty to the old master, resentment of new heirs.",
            "Claims to have been polishing silver in the pantry all evening.",
            false);
        rooms.get("Grand Hall").addCharacter(butler);

        Suspect niece = new Suspect(
            "Lady Margaret",
            "Lord Blackwood's niece. She stands to inherit the mansion if the current lord disappears.",
            "Stands to inherit the entire estate.",
            "Says she was reading in the library — but no one can confirm it.",
            false);
        rooms.get("Library").addCharacter(niece);

        Suspect cousin = new Suspect(
            "Victor Blackwood",
            "A disgraced distant cousin. Banished from the family 10 years ago, he returned uninvited last week.",
            "Was disinherited and wants revenge against the family.",
            "Claims he was in the village pub — the landlord has not confirmed this.",
            true);
        rooms.get("Dining Room").addCharacter(cousin);

        Suspect maid = new Suspect(
            "Rose the Maid",
            "A quiet, nervous young woman who has worked at the mansion for two years.",
            "Caught stealing — Lord Blackwood was about to dismiss her.",
            "Was in the kitchen preparing supper, she says.",
            false);
        rooms.get("Kitchen").addCharacter(maid);

        rooms.get("East Wing").addWeapon(
            new Weapon("Silver Crucifix", "An ancient silver crucifix found mounted on the wall. It feels warm in your hand.", 40));
        rooms.get("Cellar").addWeapon(
            new Weapon("Holy Water Flask", "A small flask filled with holy water. Effective against supernatural entities.", 35));
        rooms.get("Secret Room").addWeapon(
            new Weapon("Banishment Scroll", "A scroll with text in Latin. Reading it aloud should weaken the phantom significantly.", 50));
    }

    private void setupPhantom() {
        phantom = new Phantom("Victor Blackwood", "Victor");
    }

    public void run() {
        printIntro();
        currentRoom.enter();

        while (gameRunning) {
            System.out.print("\n> What do you do? ");
            String input = scanner.nextLine().trim().toLowerCase();
            handleInput(input);
            checkWinLose();
        }
    }

    private void handleInput(String input) {
        if (input.isEmpty()) return;

        if (input.equals("help") || input.equals("?")) {
            printHelp();
        } else if (input.startsWith("go ") || input.startsWith("move ") || input.startsWith("enter ")) {
            handleMove(input);
        } else if (input.startsWith("examine ") || input.startsWith("inspect ") || input.startsWith("look at ")) {
            handleExamine(input);
        } else if (input.startsWith("take ") || input.startsWith("pick up ") || input.startsWith("collect ")) {
            handleTake(input);
        } else if (input.startsWith("talk") || input.startsWith("speak") || input.startsWith("interrogate")) {
            handleTalk(input);
        } else if (input.startsWith("use ")) {
            handleUse(input);
        } else if (input.equals("look") || input.equals("look around") || input.equals("l")) {
            currentRoom.enter();
        } else if (input.equals("status") || input.equals("stats")) {
            detective.showStatus();
        } else if (input.equals("inventory") || input.equals("inv") || input.equals("i")) {
            detective.showInventory();
        } else if (input.equals("notebook") || input.equals("notes")) {
            detective.showNotebook();
        } else if (input.equals("accuse")) {
            handleAccuse();
        } else if (input.equals("quit") || input.equals("exit")) {
            System.out.println("You leave Blackwood Mansion. The mystery remains unsolved...");
            gameRunning = false;
        } else {
            System.out.println("I'm not sure how to do that. Type 'help' for a list of commands.");
        }
    }

    private void handleMove(String input) {
        String destination = input.replaceFirst("(?i)(go|move|enter)\\s+to\\s+", "")
                                  .replaceFirst("(?i)(go|move|enter)\\s+", "").trim();
        destination = toTitleCase(destination);

        if (rooms.containsKey(destination)) {
            if (currentRoom.getExits().contains(destination)) {
                currentRoom = rooms.get(destination);
                currentRoom.enter();
                if (currentRoom.isHaunted() && Math.random() < 0.4) {
                    System.out.println("\n" + phantom.haunt());
                    detective.loseSanity(10);
                }
            } else {
                System.out.println("You cannot reach " + destination + " from here.");
                System.out.println("Available exits: " + String.join(", ", currentRoom.getExits()));
            }
        } else {
            System.out.println("There is no place called '" + destination + "'.");
            System.out.println("Available exits: " + String.join(", ", currentRoom.getExits()));
        }
    }

    private void handleExamine(String input) {
        String target = input.replaceFirst("(?i)(examine|inspect|look at)\\s+", "").trim();
        Evidence evidence = currentRoom.findEvidence(toTitleCase(target));
        if (evidence != null) {
            evidence.interact();
            detective.addNote("Examined: " + evidence.getClueName() + " in " + currentRoom.getName()
                            + " — " + evidence.getClueDetail());
            cluesFoundCount++;
            return;
        }
        Character npc = currentRoom.findCharacter(toTitleCase(target));
        if (npc != null) {
            System.out.println(npc.toString());
            return;
        }
        System.out.println("You don't see a '" + target + "' here to examine.");
    }

    private void handleTake(String input) {
        String target = input.replaceFirst("(?i)(take|pick up|collect)\\s+", "").trim();
        Evidence evidence = currentRoom.removeEvidence(toTitleCase(target));
        if (evidence != null) {
            detective.collectEvidence(evidence);
            cluesFoundCount++;
            return;
        }
        System.out.println("There is no '" + target + "' here to take.");
    }

    private void handleTalk(String input) {
        String target = input.replaceFirst("(?i)(talk to|talk|speak to|speak|interrogate)\\s+", "").trim();
        Character npc = currentRoom.findCharacter(toTitleCase(target));
        if (npc == null) {
            for (Character c : currentRoom.getCharacters()) {
                if (c.getName().toLowerCase().contains(target.toLowerCase())) {
                    npc = c;
                    break;
                }
            }
        }
        if (npc instanceof Suspect) {
            Suspect suspect = (Suspect) npc;
            System.out.println(suspect.interrogate());
            System.out.println(suspect.speak());
            System.out.print("\nAsk about (alibi / motive / night / done): ");
            String question = scanner.nextLine().trim().toLowerCase();
            if (!question.equals("done")) {
                System.out.println(suspect.react(question));
                detective.addNote("Interrogated " + suspect.getName() + " — asked about: " + question);
            }
        } else if (npc != null) {
            System.out.println(npc.speak());
        } else {
            System.out.println("There is nobody called '" + target + "' here.");
        }
    }

    private void handleUse(String input) {
        String target = input.replaceFirst("(?i)use\\s+", "").trim();
        Weapon weapon = currentRoom.findWeapon(toTitleCase(target));
        if (weapon == null) {
            System.out.println("There is no '" + target + "' here to use.");
            return;
        }
        System.out.println(weapon.use());
        if (currentRoom.isHaunted()) {
            phantom.weakenPhantom(weapon.getPhantomDamage());
            System.out.println("The phantom weakens! [Phantom Power: " + phantom.getPower() + "]");
            if (phantom.isDefeated()) {
                System.out.println(phantom.reveal());
                phantomDefeated = true;
            }
        } else {
            System.out.println("There is no supernatural presence here to use it against.");
        }
    }

    private void handleAccuse() {
        if (cluesFoundCount < 5) {
            System.out.println("You don't have enough evidence to make an accusation yet.");
            System.out.println("Clues gathered: " + cluesFoundCount + " (need at least 5)");
            return;
        }
        System.out.println("\n=== MAKE YOUR ACCUSATION ===");
        System.out.println("Suspects:");
        System.out.println("1. Edmund the Butler");
        System.out.println("2. Lady Margaret");
        System.out.println("3. Victor Blackwood");
        System.out.println("4. Rose the Maid");
        System.out.print("Who do you accuse? (enter name or number): ");
        String accusation = scanner.nextLine().trim().toLowerCase();

        if (accusation.contains("victor") || accusation.equals("3")) {
            System.out.println("\n*** CORRECT! ***");
            System.out.println(phantom.reveal());
            System.out.println("\nVictor Blackwood used dark rituals to fake his own death and haunt the mansion,");
            System.out.println("trying to drive everyone away so he could reclaim his stolen inheritance.");
            System.out.println("\nThe phantom is banished. Blackwood Mansion is at peace.");
            System.out.println("\n=== CONGRATULATIONS, " + detective.getName().toUpperCase() + "! YOU SOLVED THE MYSTERY! ===");
            gameRunning = false;
        } else {
            System.out.println("\nWrong accusation! The real culprit is still out there...");
            detective.loseSanity(15);
            System.out.println("Keep investigating before you accuse again.");
        }
    }

    private void checkWinLose() {
        if (!detective.isConscious()) {
            System.out.println("\n=== GAME OVER ===");
            if (detective.getHealth() <= 0) {
                System.out.println("The phantom's curse has claimed you. " + detective.getName() + " collapses.");
            } else {
                System.out.println("Your sanity shattered, you flee the mansion screaming. The mystery is never solved.");
            }
            gameRunning = false;
        }
        if (phantomDefeated && gameRunning) {
            System.out.println("\nThe phantom is weakened! Now make your final accusation. Type 'accuse'.");
        }
    }

    private void printIntro() {
        System.out.println("=========================================================");
        System.out.println("           PHANTOM DETECTIVE GAME                        ");
        System.out.println("           Blackwood Mansion Mystery                     ");
        System.out.println("=========================================================");
        System.out.println(" Lord Arthur Blackwood has vanished from his mansion.    ");
        System.out.println(" Witnesses report ghostly apparitions and strange noises.");
        System.out.println(" You have been called to investigate.                    ");
        System.out.println("                                                         ");
        System.out.println(" Collect clues, interrogate suspects, defeat the phantom,");
        System.out.println(" and unmask the culprit before your sanity runs out.     ");
        System.out.println(" Type 'help' for a list of commands.                     ");
        System.out.println("=========================================================");
        System.out.println();
    }

    private void printHelp() {
        System.out.println("=== COMMANDS ===");
        System.out.println("  go/move/enter <room>   — Move to an adjacent room");
        System.out.println("  look / look around     — Describe the current room again");
        System.out.println("  examine <item/person>  — Examine something closely");
        System.out.println("  take <item>            — Pick up a piece of evidence");
        System.out.println("  talk/interrogate <name>— Question a suspect");
        System.out.println("  use <weapon>           — Use a weapon against the phantom");
        System.out.println("  inventory              — Show your collected evidence");
        System.out.println("  notebook               — Show your investigation notes");
        System.out.println("  status                 — Show your health and sanity");
        System.out.println("  accuse                 — Make your final accusation");
        System.out.println("  quit                   — Exit the game");
        System.out.println();
        System.out.println("=== ROOM MAP ===");
        System.out.println("  Grand Hall --> Library, Dining Room, East Wing, Cellar");
        System.out.println("  Library    --> Study");
        System.out.println("  East Wing  --> Master Bedroom, Secret Room");
        System.out.println("  Dining Room--> Kitchen");
    }

    private String toTitleCase(String input) {
        if (input == null || input.isEmpty()) return input;
        StringBuilder sb = new StringBuilder();
        boolean capitalise = true;
        for (char c : input.toCharArray()) {
            if (java.lang.Character.isWhitespace(c)) {
                capitalise = true;
                sb.append(c);
            } else if (capitalise) {
                sb.append(java.lang.Character.toUpperCase(c));
                capitalise = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
