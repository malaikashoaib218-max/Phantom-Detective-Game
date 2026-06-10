package com.phantomdetective.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Main gameplay screen.
 * Demonstrates BorderLayout — the primary layout for full-window applications.
 *
 *   NORTH  — room name header
 *   CENTER — game text output (JScrollPane + JTextArea)
 *   EAST   — StatusPanel  (BoxLayout)
 *   SOUTH  — ActionPanel + ExitsPanel combined
 */
public class MainGamePanel extends JPanel {

    private GameWindow   window;
    private SwingGameEngine engine;

    private JLabel       roomHeader;
    private JTextArea    outputArea;
    private StatusPanel  statusPanel;
    private ActionPanel  actionPanel;
    private ExitsPanel   exitsPanel;

    public MainGamePanel(GameWindow window) {
        this.window = window;
        setLayout(new BorderLayout(4, 4));
        setBackground(new Color(18, 15, 30));
        buildUI();
    }

    private void buildUI() {
        // NORTH — room name banner
        roomHeader = new JLabel("BLACKWOOD MANSION", SwingConstants.CENTER);
        roomHeader.setFont(new Font("Serif", Font.BOLD, 18));
        roomHeader.setForeground(new Color(220, 180, 255));
        roomHeader.setBackground(new Color(30, 22, 55));
        roomHeader.setOpaque(true);
        roomHeader.setBorder(new EmptyBorder(8, 12, 8, 12));
        add(roomHeader, BorderLayout.NORTH);

        // CENTER — scrollable game text output
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(new Color(12, 10, 22));
        outputArea.setForeground(new Color(210, 210, 210));
        outputArea.setMargin(new Insets(10, 12, 10, 12));
        outputArea.setCaretColor(new Color(180, 130, 255));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(4, 4, 0, 4),
            BorderFactory.createLineBorder(new Color(60, 45, 100), 1)
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // EAST — status panel (BoxLayout)
        statusPanel = new StatusPanel();
        add(statusPanel, BorderLayout.EAST);

        // SOUTH — action buttons + exits stacked vertically
        JPanel southContainer = new JPanel(new BorderLayout(0, 2));
        southContainer.setBackground(new Color(18, 15, 30));

        exitsPanel  = new ExitsPanel(this);
        actionPanel = new ActionPanel(this);

        southContainer.add(exitsPanel,  BorderLayout.NORTH);
        southContainer.add(actionPanel, BorderLayout.CENTER);
        add(southContainer, BorderLayout.SOUTH);
    }

    public void initialise(String detectiveName) {
        engine = new SwingGameEngine(detectiveName, new SwingGameEngine.GameStateListener() {
            @Override
            public void onStateChanged() {
                refreshSidebar();
            }
            @Override
            public void onGameOver(String message, boolean won) {
                appendOutput(message);
                SwingUtilities.invokeLater(() ->
                    window.showGameOver(message, won)
                );
            }
        });

        outputArea.setText("");
        appendOutput("Welcome, " + detectiveName + ". Your investigation begins now.\n\n"
                   + engine.getInitialDescription());
        refreshSidebar();
        actionPanel.focusInput();
    }

    public void processCommand(String command) {
        if (engine == null || engine.isGameOver()) return;
        appendOutput("\n> " + command + "\n");
        String result = engine.processCommand(command);
        appendOutput(result);
        refreshSidebar();
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void refreshSidebar() {
        if (engine == null) return;
        statusPanel.update(
            engine.getDetective().getHealth(),
            engine.getDetective().getSanity(),
            engine.getCluesFoundCount(),
            engine.getCurrentRoom().getName(),
            engine.getInventoryNames()
        );
        exitsPanel.setExits(engine.getCurrentRoom().getExits());
        roomHeader.setText(engine.getCurrentRoom().getName().toUpperCase());
    }
}
