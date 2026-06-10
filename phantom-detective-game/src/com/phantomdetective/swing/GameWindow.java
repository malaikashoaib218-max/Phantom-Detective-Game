package com.phantomdetective.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Main game window using CardLayout to switch between screens.
 * CardLayout: displays one panel at a time — used for intro, game, and game-over screens.
 */
public class GameWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardContainer;

    private IntroPanel introPanel;
    private MainGamePanel mainGamePanel;
    private GameOverPanel gameOverPanel;

    public static final String CARD_INTRO    = "INTRO";
    public static final String CARD_GAME     = "GAME";
    public static final String CARD_GAMEOVER = "GAMEOVER";

    public GameWindow() {
        setTitle("Phantom Detective Game — Blackwood Mansion Mystery");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);

        introPanel    = new IntroPanel(this);
        mainGamePanel = new MainGamePanel(this);
        gameOverPanel = new GameOverPanel(this);

        cardContainer.add(introPanel,    CARD_INTRO);
        cardContainer.add(mainGamePanel, CARD_GAME);
        cardContainer.add(gameOverPanel, CARD_GAMEOVER);

        add(cardContainer);
        showCard(CARD_INTRO);
        setVisible(true);
    }

    public void showCard(String cardName) {
        cardLayout.show(cardContainer, cardName);
    }

    public void startGame(String detectiveName) {
        mainGamePanel.initialise(detectiveName);
        showCard(CARD_GAME);
    }

    public void showGameOver(String message, boolean won) {
        gameOverPanel.setMessage(message, won);
        showCard(CARD_GAMEOVER);
    }

    public void restartGame() {
        showCard(CARD_INTRO);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
