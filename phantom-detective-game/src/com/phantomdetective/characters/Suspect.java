package com.phantomdetective.characters;

public class Suspect extends Character {
    private String motive;
    private String alibi;
    private boolean isGuilty;
    private boolean hasBeenInterrogated;
    private String[] dialogueLines;
    private int dialogueIndex;

    public Suspect(String name, String description, String motive, String alibi, boolean isGuilty) {
        super(name, description);
        this.motive = motive;
        this.alibi = alibi;
        this.isGuilty = isGuilty;
        this.hasBeenInterrogated = false;
        this.dialogueIndex = 0;
        this.dialogueLines = generateDialogue();
    }

    private String[] generateDialogue() {
        return new String[]{
            getName() + ": \"I had nothing to do with it, I swear!\"",
            getName() + ": \"You have no right to question me like this.\"",
            getName() + ": \"My alibi is solid. Check it if you don't believe me.\"",
            getName() + ": \"I'm telling you the truth. Leave me alone!\""
        };
    }

    @Override
    public String speak() {
        String line = dialogueLines[dialogueIndex % dialogueLines.length];
        dialogueIndex++;
        return line;
    }

    @Override
    public String react(String input) {
        hasBeenInterrogated = true;
        String lower = input.toLowerCase();
        if (lower.contains("alibi")) {
            return getName() + " nervously shifts eyes. \"My alibi? I was... in the library.\" [" + alibi + "]";
        }
        if (lower.contains("motive") || lower.contains("why")) {
            if (isGuilty) {
                return getName() + " looks away. \"I don't know what you're talking about.\"";
            }
            return getName() + ": \"I had absolutely no reason to do anything!\"";
        }
        if (lower.contains("night") || lower.contains("when")) {
            return getName() + ": \"I was nowhere near the east wing that night.\"";
        }
        return speak();
    }

    public String interrogate() {
        hasBeenInterrogated = true;
        return "--- Interrogating " + getName() + " ---\n"
             + "Description : " + getDescription() + "\n"
             + "Motive hint : " + (isGuilty ? "Has a strong grudge against the victim." : "Appears to have no clear motive.") + "\n"
             + "Alibi       : " + alibi;
    }

    public String getMotive() {
        return motive;
    }

    public String getAlibi() {
        return alibi;
    }

    public boolean isGuilty() {
        return isGuilty;
    }

    public boolean hasBeenInterrogated() {
        return hasBeenInterrogated;
    }
}
