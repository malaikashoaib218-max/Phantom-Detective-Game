package com.phantomdetective.characters;

public class Phantom extends Character {
    private int power;
    private boolean isRevealed;
    private String trueIdentity;
    private String weaknessClue;

    public Phantom(String trueIdentity, String weaknessClue) {
        super("The Phantom", "A terrifying supernatural entity haunting the mansion.");
        this.trueIdentity = trueIdentity;
        this.weaknessClue = weaknessClue;
        this.power = 100;
        this.isRevealed = false;
    }

    @Override
    public String speak() {
        String[] taunts = {
            "\"You will never uncover the truth, detective...\"",
            "\"Leave now, while you still can!\"",
            "\"The darkness will swallow you whole!\"",
            "\"Ha ha ha... your sanity is slipping away!\""
        };
        int idx = (int)(Math.random() * taunts.length);
        return "The Phantom: " + taunts[idx];
    }

    @Override
    public String react(String input) {
        if (input.toLowerCase().contains(weaknessClue.toLowerCase())) {
            power -= 30;
            isRevealed = true;
            return "The Phantom SHRIEKS in pain! Its form flickers and weakens! [Power: " + power + "]";
        }
        if (input.toLowerCase().contains("who are you")) {
            if (isRevealed) {
                return "The Phantom's mask cracks... It is " + trueIdentity + "!";
            }
            return "\"YOU DARE QUESTION ME?!\" The Phantom vanishes into the shadows.";
        }
        return speak();
    }

    public String haunt() {
        return "Suddenly the lights flicker! Cold air fills the room! The Phantom appears!\n" + speak();
    }

    public boolean isDefeated() {
        return power <= 0;
    }

    public void weakenPhantom(int amount) {
        power -= amount;
        if (power < 0) power = 0;
    }

    public String reveal() {
        isRevealed = true;
        return "=== THE PHANTOM REVEALED ===\n"
             + "The mask crumbles away... Standing before you is " + trueIdentity + "!\n"
             + "Their secret is exposed. The haunting is over.";
    }

    public int getPower() {
        return power;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public String getTrueIdentity() {
        return trueIdentity;
    }
}
