package com.phantomdetective.swing;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Action buttons bar at the bottom of the game screen.
 * Demonstrates FlowLayout — buttons flow left to right, wrap if needed.
 */
public class ActionPanel extends JPanel {

    private MainGamePanel gamePanel;
    private JTextField commandField;

    public ActionPanel(MainGamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLayout(new BorderLayout(0, 4));
        setBackground(new Color(22, 18, 38));
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(80, 60, 140)));
        buildUI();
    }

    private void buildUI() {
        // Quick action buttons using FlowLayout
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 5));
        buttonRow.setBackground(new Color(22, 18, 38));

        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(),
            "Quick Actions  (FlowLayout)",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("SansSerif", Font.PLAIN, 11),
            new Color(140, 110, 210)
        );
        buttonRow.setBorder(border);

        String[][] actions = {
            {"Look Around", "look"},
            {"Inventory",   "inventory"},
            {"Notebook",    "notebook"},
            {"Status",      "status"},
            {"Accuse",      "accuse"},
            {"Help",        "help"}
        };

        for (String[] action : actions) {
            JButton btn = makeButton(action[0], action[1]);
            buttonRow.add(btn);
        }

        // Command input row
        JPanel inputRow = new JPanel(new BorderLayout(6, 0));
        inputRow.setBackground(new Color(22, 18, 38));
        inputRow.setBorder(BorderFactory.createEmptyBorder(2, 8, 8, 8));

        JLabel cmdLabel = new JLabel("Command:");
        cmdLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        cmdLabel.setForeground(new Color(180, 130, 255));

        commandField = new JTextField();
        commandField.setFont(new Font("Monospaced", Font.PLAIN, 13));
        commandField.setBackground(new Color(30, 25, 50));
        commandField.setForeground(Color.WHITE);
        commandField.setCaretColor(Color.WHITE);
        commandField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(90, 60, 140), 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        commandField.addActionListener(e -> submitCommand());

        JButton sendBtn = new JButton("Send");
        sendBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        sendBtn.setBackground(new Color(100, 60, 180));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFocusPainted(false);
        sendBtn.setPreferredSize(new Dimension(70, 30));
        sendBtn.addActionListener(e -> submitCommand());

        inputRow.add(cmdLabel,     BorderLayout.WEST);
        inputRow.add(commandField, BorderLayout.CENTER);
        inputRow.add(sendBtn,      BorderLayout.EAST);

        add(buttonRow,  BorderLayout.CENTER);
        add(inputRow,   BorderLayout.SOUTH);
    }

    private JButton makeButton(String label, String command) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setBackground(new Color(50, 38, 80));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(90, 60, 140), 1));
        btn.addActionListener(e -> gamePanel.processCommand(command));
        return btn;
    }

    private void submitCommand() {
        String cmd = commandField.getText().trim();
        if (!cmd.isEmpty()) {
            gamePanel.processCommand(cmd);
            commandField.setText("");
        }
    }

    public void focusInput() {
        commandField.requestFocusInWindow();
    }
}
