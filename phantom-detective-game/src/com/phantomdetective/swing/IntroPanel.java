package com.phantomdetective.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Intro/Welcome screen.
 * Demonstrates BorderLayout — title NORTH, content CENTER, button SOUTH.
 */
public class IntroPanel extends JPanel {

    private GameWindow window;
    private JTextField nameField;

    public IntroPanel(GameWindow window) {
        this.window = window;
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 35));
        buildUI();
    }

    private void buildUI() {
        // NORTH — title area using BorderLayout
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(20, 20, 35));
        titlePanel.setBorder(new EmptyBorder(40, 0, 10, 0));

        JLabel titleLabel = new JLabel("PHANTOM DETECTIVE GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 34));
        titleLabel.setForeground(new Color(180, 130, 255));

        JLabel subtitleLabel = new JLabel("Blackwood Mansion Mystery", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        subtitleLabel.setForeground(new Color(200, 200, 200));

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        // CENTER — story and name entry
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(20, 20, 35));
        centerPanel.setBorder(new EmptyBorder(30, 100, 20, 100));

        JTextArea storyText = new JTextArea(
            "Lord Arthur Blackwood has vanished from his family mansion.\n" +
            "Witnesses report ghostly apparitions, flickering lights, and unearthly sounds.\n\n" +
            "You have been summoned to investigate.\n\n" +
            "Explore 9 rooms. Collect clues. Interrogate suspects.\n" +
            "Defeat the phantom. Unmask the culprit.\n\n" +
            "Do you dare enter Blackwood Mansion?"
        );
        storyText.setEditable(false);
        storyText.setOpaque(false);
        storyText.setForeground(new Color(220, 220, 220));
        storyText.setFont(new Font("Serif", Font.PLAIN, 15));
        storyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);

        JLabel nameLabel = new JLabel("Enter your detective's name:");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        nameLabel.setForeground(new Color(180, 130, 255));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setBorder(new EmptyBorder(20, 0, 6, 0));

        nameField = new JTextField("Detective Cross");
        nameField.setMaximumSize(new Dimension(300, 32));
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setHorizontalAlignment(JTextField.CENTER);

        centerPanel.add(storyText);
        centerPanel.add(nameLabel);
        centerPanel.add(nameField);

        // SOUTH — start button using FlowLayout
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        southPanel.setBackground(new Color(20, 20, 35));

        JButton startButton = new JButton("Begin Investigation");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        startButton.setBackground(new Color(100, 60, 180));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(200, 42));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.addActionListener(e -> startGame());

        nameField.addActionListener(e -> startGame());

        southPanel.add(startButton);

        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void startGame() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) name = "Detective Cross";
        window.startGame(name);
    }
}
