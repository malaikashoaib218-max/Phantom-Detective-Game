# Phantom Detective Game
### Java OOP Course Project — CS-112, Air University

A mystery/detective game set in Blackwood Mansion. Explore rooms, collect clues, interrogate suspects, defeat the phantom, and unmask the culprit — featuring a full **Java Swing GUI**.

---

## How to Compile & Run

```bash
# Step 1: Compile
bash compile.sh

# Step 2: Run (Swing GUI — default)
bash run.sh

# Step 3: Run (Console text mode)
bash run-console.sh
```

---

##  Java Swing Layout Managers Used

| Layout Manager | Where it is used in this project |
|---|---|
| **BorderLayout** | `MainGamePanel` — divides the game screen into NORTH (room name), CENTER (game output), EAST (status sidebar), SOUTH (action area) |
| **BorderLayout** | `IntroPanel` — title in NORTH, story in CENTER, start button in SOUTH |
| **BorderLayout** | `GameOverPanel` — title NORTH, message CENTER, buttons SOUTH |
| **FlowLayout**   | `ActionPanel` — quick-action buttons flow left to right |
| **FlowLayout**   | `IntroPanel` and `GameOverPanel` — button rows |
| **GridLayout**   | `ExitsPanel` — exit buttons displayed in a uniform grid |
| **BoxLayout**    | `StatusPanel` — health, sanity, clue count, inventory stacked vertically (Y_AXIS) |
| **CardLayout**   | `GameWindow` — switches between Intro screen, Game screen, and Game Over screen |

---

##  Inheritance & Interface Used

| OOP Concept | Class / Interface |
|---|---|
| **Interface** | `LibraryService` → here: `Clue` and `Interactable` interfaces |
| **Abstract Class** | `Character` — abstract base with abstract `speak()` and `react()` methods |
| **Inheritance** | `Detective`, `Suspect`, `Phantom` all extend `Character` |
| **Inheritance** | All 9 room classes extend abstract `Room` |
| **Implements Interface** | `Evidence` implements both `Clue` and `Interactable` |
| **Implements Interface** | `Weapon` implements `Interactable` |
| **Polymorphism** | `speak()` returns different dialogue for each character type |
| **Encapsulation** | All fields private; accessed via getters/setters |

---

## Package Structure

```
src/com/phantomdetective/
├── Main.java                      ← Entry point (GUI default, --console flag for text mode)
│
├── interfaces/
│   ├── Clue.java                  ← Interface: getClueName(), getClueDetail(), isKeyClue()
│   └── Interactable.java          ← Interface: interact(), getDescription()
│
├── characters/
│   ├── Character.java             ← Abstract base class
│   ├── Detective.java             ← Player (extends Character)
│   ├── Suspect.java               ← NPC suspect (extends Character)
│   └── Phantom.java               ← Ghost antagonist (extends Character)
│
├── items/
│   ├── Evidence.java              ← implements Clue + Interactable
│   └── Weapon.java                ← implements Interactable
│
├── rooms/
│   ├── Room.java                  ← Abstract base room
│   ├── GrandHall.java             ← 8 concrete room classes
│   ├── Library.java
│   ├── Study.java
│   ├── DiningRoom.java
│   ├── Kitchen.java
│   ├── EastWing.java
│   ├── MasterBedroom.java
│   ├── SecretRoom.java
│   └── Cellar.java
│
├── game/
│   └── GameEngine.java            ← Console game loop (text mode)
│
└── swing/
    ├── GameWindow.java            ← Main JFrame — uses CardLayout
    ├── IntroPanel.java            ← Welcome screen — uses BorderLayout + FlowLayout
    ├── MainGamePanel.java         ← Game screen — uses BorderLayout
    ├── StatusPanel.java           ← Right sidebar — uses BoxLayout
    ├── ExitsPanel.java            ← Exit buttons — uses GridLayout
    ├── ActionPanel.java           ← Action buttons — uses FlowLayout
    ├── GameOverPanel.java         ← End screen — uses BorderLayout + FlowLayout
    └── SwingGameEngine.java       ← Game logic (UI-independent, returns String output)
```

---

## Game Story

Lord Arthur Blackwood has vanished from his family mansion. Witnesses report ghostly apparitions, flickering lights, and unearthly sounds. Four suspects are present. Your job as detective: gather at least 5 clues, interrogate suspects, weaken the phantom using supernatural weapons, then make the correct accusation.

**Rooms to explore:** Grand Hall, Library, Study, Dining Room, Kitchen, East Wing, Master Bedroom, Secret Room, Cellar

**Suspects:** Edmund the Butler, Lady Margaret, Victor Blackwood, Rose the Maid

**Tip for testing win condition:** Accuse **Victor Blackwood**

---

## Commands (works in both GUI and console mode)

| Command | Description |
|---|---|
| `go <room>` | Move to an adjacent room |
| `look` | Re-describe the current room |
| `examine <item>` | Examine a clue or person |
| `take <item>` | Pick up evidence |
| `interrogate <name>` | Question a suspect |
| `use <weapon>` | Use a weapon against the phantom |
| `inventory` | Show collected evidence |
| `notebook` | Show your notes |
| `status` | Show health and sanity |
| `accuse <name>` | Final accusation (needs 5+ clues) |
| `help` | Show all commands |
