package com.phantomdetective.swing;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * Exits / Map panel at the bottom-left.
 * Demonstrates GridLayout — arranges exit buttons in a uniform grid.
 */
public class ExitsPanel extends JPanel {

    private MainGamePanel gamePanel;
    private JPanel buttonGrid;

    public ExitsPanel(MainGamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLayout(new BorderLayout());
        setBackground(new Color(25, 20, 42));
        setPreferredSize(new Dimension(0, 110));

        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 70, 180), 1),
            "Exits  (GridLayout)",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("SansSerif", Font.PLAIN, 11),
            new Color(140, 110, 210)
        );
        setBorder(border);

        buttonGrid = new JPanel();
        add(buttonGrid, BorderLayout.CENTER);
    }

    /**
     * Called every time the player moves to a new room.
     * Rebuilds exit buttons using GridLayout.
     */
    public void setExits(List<String> exits) {
        buttonGrid.removeAll();

        int cols = Math.max(2, exits.size());
        buttonGrid.setLayout(new GridLayout(1, cols, 6, 0));
        buttonGrid.setBackground(new Color(25, 20, 42));

        for (String exit : exits) {
            JButton btn = new JButton(exit);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
            btn.setBackground(new Color(55, 40, 90));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(new Color(90, 60, 140), 1));
            btn.addActionListener(e -> gamePanel.processCommand("go " + exit));
            buttonGrid.add(btn);
        }

        revalidate();
        repaint();
    }
}
