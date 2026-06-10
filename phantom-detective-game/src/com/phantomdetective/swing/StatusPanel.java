package com.phantomdetective.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Right-side status panel.
 * Demonstrates BoxLayout (Y_AXIS) — stacks labels vertically.
 */
public class StatusPanel extends JPanel {

    private JLabel healthLabel;
    private JLabel sanityLabel;
    private JLabel cluesLabel;
    private JLabel roomLabel;
    private JTextArea inventoryArea;

    public StatusPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(30, 25, 50));
        setPreferredSize(new Dimension(200, 0));
        setBorder(new EmptyBorder(10, 8, 10, 8));
        buildUI();
    }

    private void buildUI() {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 70, 180), 1),
            "Detective Status",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 12),
            new Color(180, 130, 255)
        );
        setBorder(border);

        healthLabel  = makeLabel("Health:  100");
        sanityLabel  = makeLabel("Sanity:  100");
        cluesLabel   = makeLabel("Clues:   0");
        roomLabel    = makeLabel("Room:    —");

        add(Box.createVerticalStrut(10));
        add(healthLabel);
        add(Box.createVerticalStrut(6));
        add(sanityLabel);
        add(Box.createVerticalStrut(6));
        add(cluesLabel);
        add(Box.createVerticalStrut(6));
        add(roomLabel);
        add(Box.createVerticalStrut(14));

        JLabel invTitle = makeLabel("Inventory:");
        invTitle.setForeground(new Color(180, 130, 255));
        add(invTitle);
        add(Box.createVerticalStrut(4));

        inventoryArea = new JTextArea(8, 14);
        inventoryArea.setEditable(false);
        inventoryArea.setBackground(new Color(20, 18, 38));
        inventoryArea.setForeground(new Color(200, 220, 200));
        inventoryArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        inventoryArea.setLineWrap(true);
        inventoryArea.setWrapStyleWord(true);
        inventoryArea.setText("(empty)");

        JScrollPane scroll = new JScrollPane(inventoryArea);
        scroll.setMaximumSize(new Dimension(200, 200));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(70, 50, 120)));
        add(scroll);
        add(Box.createVerticalGlue());
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Monospaced", Font.PLAIN, 12));
        label.setForeground(new Color(210, 210, 210));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public void update(int health, int sanity, int clues, String roomName, java.util.List<String> inventory) {
        healthLabel.setText("Health:  " + health);
        sanityLabel.setText("Sanity:  " + sanity);
        cluesLabel.setText("Clues:   " + clues);
        roomLabel.setText("Room:    " + roomName);

        healthLabel.setForeground(health > 50 ? new Color(100, 220, 100) : new Color(220, 80, 80));
        sanityLabel.setForeground(sanity > 50 ? new Color(100, 180, 255) : new Color(220, 150, 50));

        if (inventory.isEmpty()) {
            inventoryArea.setText("(empty)");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String item : inventory) {
                sb.append("• ").append(item).append("\n");
            }
            inventoryArea.setText(sb.toString());
        }
    }
}
