package com.phantomdetective;

import com.phantomdetective.game.GameEngine;
import com.phantomdetective.swing.GameWindow;
import javax.swing.SwingUtilities;

/**
 * Entry point.
 * Run with no arguments for the GUI version (default).
 * Run with --console for the original text-based console version.
 */
public class Main {
    public static void main(String[] args) {
        boolean useConsole = false;
        for (String arg : args) {
            if (arg.equals("--console")) useConsole = true;
        }

        if (useConsole) {
            GameEngine engine = new GameEngine();
            engine.initialise();
            engine.run();
        } else {
            SwingUtilities.invokeLater(GameWindow::new);
        }
    }
}
