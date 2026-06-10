package com.phantomdetective.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Game Over / Victory screen.
 * Uses BorderLayout with centered content and FlowLayout for buttons.
 */
public class GameOverPanel extends JPanel {

    private GameWindow window;
    private JLabel     titleLabel;
    private JTextArea  messageArea;

    public GameOverPanel(GameWindow window) {
        this.window = window;
        setLayout(new BorderLayout());
        setBackground(new Color(15, 10, 25));
        buildUI();
    }

    private void buildUI() {
        // NORTH — title
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(15, 10, 25));
        northPanel.setBorder(new EmptyBorder(50, 20, 10, 20));

        titleLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(new Color(200, 80, 80));
        northPanel.add(titleLabel, BorderLayout.CENTER);

        // CENTER — message
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setOpaque(false);
        messageArea.setFont(new Font("Serif", Font.PLAIN, 15));
        messageArea.setForeground(new Color(220, 220, 220));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setMargin(new Insets(20, 80, 20, 80));

        JScrollPane scroll = new JScrollPane(messageArea);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        // SOUTH — buttons using FlowLayout
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        southPanel.setBackground(new Color(15, 10, 25));

        JButton playAgainBtn = new JButton("Play Again");
        playAgainBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        playAgainBtn.setBackground(new Color(80, 50, 160));
        playAgainBtn.setForeground(Color.WHITE);
        playAgainBtn.setFocusPainted(false);
        playAgainBtn.setPreferredSize(new Dimension(150, 40));
        playAgainBtn.addActionListener(e -> window.restartGame());

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitBtn.setBackground(new Color(60, 40, 80));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFocusPainted(false);
        exitBtn.setPreferredSize(new Dimension(150, 40));
        exitBtn.addActionListener(e -> System.exit(0));

        southPanel.add(playAgainBtn);
        southPanel.add(exitBtn);

        add(northPanel,  BorderLayout.NORTH);
        add(scroll,      BorderLayout.CENTER);
        add(southPanel,  BorderLayout.SOUTH);
    }

    public void setMessage(String message, boolean won) {
        if (won) {
            titleLabel.setText("MYSTERY SOLVED!");
            titleLabel.setForeground(new Color(100, 220, 120));
        } else {
            titleLabel.setText("GAME OVER");
            titleLabel.setForeground(new Color(200, 80, 80));
        }
        messageArea.setText(message);
        messageArea.setCaretPosition(0);
    }
}
