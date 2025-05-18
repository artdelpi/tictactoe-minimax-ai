package tictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    // Declaration of GUI components
    private JFrame menuFrame;
    private JFrame gameFrame;
    private JPanel menuTitlePanel;
    private JPanel gameFramePanel;
    private JPanel gameStatusPanel;
    private JPanel gameStatusButtonsPanel;
    private JPanel gameStatusLabelPanel;
    private JPanel gameStatusLabelTurnPanel;
    private JPanel menuButtonPanel;
    private JButton menuAIButton;
    private JButton menuPlayerButton;
    private JButton restartButton;
    private JButton returnButton;
    private JButton[][] gameBoard = new JButton[3][3];
    private JLabel turnLabel;
    private JLabel xScoreLabel;
    private JLabel oScoreLabel;
    private JLabel menuTitleLabel;

    // Initialization of game variables
    private String currentPlayer;
    private String gameMode;
    private String[] gameModes;
    private boolean isOver;
    private int xScore;
    private int oScore;
    private int turn;

    // Constructor: Initializes the game
    TicTacToe() {
        initializeGameVariables();
        initializeUserInterface();
        setUpTiles(); // Core of the game by implementing the tile buttons
        setUpUserInterface();
    }

    private void initializeUserInterface() {
        // Initialize frames
        menuFrame = new JFrame("Tic-Tac-Toe: Menu");
        gameFrame = new JFrame("Tic-Tac-Toe");

        // Initialize panels to hold GUI components
        gameFramePanel = new JPanel();
        gameStatusPanel = new JPanel();
        gameStatusButtonsPanel = new JPanel();
        gameStatusLabelPanel = new JPanel();
        gameStatusLabelTurnPanel = new JPanel();

        // Create GUI components
        restartButton = new JButton("RESTART");
        returnButton = new JButton("MENU");
        turnLabel = new JLabel(currentPlayer + " to play!");
        xScoreLabel = new JLabel("✕: 0");
        oScoreLabel = new JLabel("⭕: 0");
    }

    private void initializeGameVariables() {
        currentPlayer = "✕"; //U+2715
        xScore = 0;
        oScore = 0;
        turn = 1;
        isOver = false;
        gameModes = new String[2];
        gameModes[0] = "computerVsPlayer";
        gameModes[1] = "playerVsPlayer";
    }

    private void setUpUserInterface() {
        /* Set up Game Board UI */
        gameFrame.setSize(800, 800);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null); // Center the window
        gameFramePanel.setLayout(new GridLayout(3, 3));
        gameFramePanel.setBackground(Color.ORANGE);
        
        // Configure the restart button
        restartButton.setFont(new Font("Arial", Font.BOLD, 25));
        restartButton.setBackground(new Color(50, 50, 50));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusable(false);
        restartButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
        restartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        restartButton.setPreferredSize(new Dimension(180, 40));

        // Restart game when the button is clicked
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int r=0; r<3; r++) {
                    for (int c=0; c<3; c++) {
                        gameBoard[r][c].setText("");
                        gameBoard[r][c].setBackground(Color.ORANGE);
                    }
                }
                turnLabel.setText(currentPlayer + " to play!");
                isOver = false;
                turn = 1;
            }
        });
        
        returnButton.setFont(new Font("Arial", Font.BOLD, 25));
        returnButton.setBackground(new Color(50, 50, 50));
        returnButton.setForeground(Color.WHITE);
        returnButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
        returnButton.setFocusable(false);
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnButton.setPreferredSize(new Dimension(180, 40));

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameFrame.setVisible(false);
                menuFrame.setVisible(true);
                for (int r=0; r<3; r++) {
                    for (int c=0; c<3; c++) {
                        gameBoard[r][c].setText("");
                        gameBoard[r][c].setBackground(Color.ORANGE);
                    }
                }
                oScore = 0;
                oScoreLabel.setText("⭕: " + String.valueOf(oScore));
                xScore = 0;
                xScoreLabel.setText("✕: " + String.valueOf(oScore));
                turn = 1;
            }
        });

        gameStatusButtonsPanel.setBackground(new Color(35, 35, 35));
        gameStatusButtonsPanel.add(restartButton);
        gameStatusButtonsPanel.add(returnButton);
        
        gameStatusLabelPanel.setLayout(new BorderLayout());
        gameStatusLabelPanel.setOpaque(false);

        gameStatusLabelTurnPanel.add(turnLabel);
        gameStatusLabelTurnPanel.setOpaque(false);

        gameStatusLabelPanel.add(oScoreLabel, BorderLayout.EAST);
        gameStatusLabelPanel.add(gameStatusLabelTurnPanel, BorderLayout.CENTER);
        gameStatusLabelPanel.add(xScoreLabel, BorderLayout.WEST);

        gameStatusPanel.setLayout(new BorderLayout());
        gameStatusPanel.setBackground(new Color(25, 25, 25));
        gameStatusPanel.add(gameStatusLabelPanel, BorderLayout.NORTH);
        gameStatusPanel.add(gameStatusButtonsPanel, BorderLayout.SOUTH);
        
        turnLabel.setOpaque(false);
        turnLabel.setText(currentPlayer + " to play!");
        turnLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 50));
        turnLabel.setForeground(Color.ORANGE);

        oScoreLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
        oScoreLabel.setForeground(Color.LIGHT_GRAY);
        xScoreLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
        xScoreLabel.setForeground(Color.LIGHT_GRAY);

        gameFramePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // (top, left, bottom, right)
        gameFrame.add(gameStatusPanel, BorderLayout.NORTH);
        gameFrame.add(gameFramePanel, BorderLayout.CENTER);

        gameFrame.setVisible(false);

        /* Set up Menu UI */
        menuFrame.setSize(600, 400);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(new BorderLayout());
        menuFrame.setResizable(false);
        menuFrame.setLocationRelativeTo(null); // Center the window

        // Panel Title Label
        menuTitlePanel = new JPanel();
        menuTitlePanel.setBackground(new Color(25, 25, 25));
        menuTitleLabel = new JLabel(("TIC-TAC-TOE"));
        menuTitleLabel.setForeground(Color.ORANGE);
        menuTitleLabel.setFont(new Font("Arial Black", Font.BOLD, 60));
        menuTitlePanel.add(menuTitleLabel);

        menuButtonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        menuButtonPanel.setBackground(new Color(35, 35, 35));
        menuButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        menuAIButton = new JButton("Player vs AI");
        menuAIButton.setFont(new Font("Arial", Font.BOLD, 28));
        menuAIButton.setBackground(new Color(50, 50, 50));
        menuAIButton.setForeground(Color.WHITE);
        menuAIButton.setFocusPainted(false);
        menuAIButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        menuAIButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        menuPlayerButton = new JButton("Player vs Player");
        menuPlayerButton.setFont(new Font("Arial", Font.BOLD, 28));
        menuPlayerButton.setBackground(new Color(50, 50, 50));
        menuPlayerButton.setForeground(Color.WHITE);
        menuPlayerButton.setFocusPainted(false);
        menuPlayerButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        menuPlayerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        menuPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                gameFrame.setVisible(true);
                gameMode = gameModes[1]; // gameMode = "playerVsPlayer"
            }
        });

        menuAIButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                gameFrame.setVisible(true);
                gameMode = gameModes[0]; // gameMode = "computerVsPlayer"
            }
        });

        menuButtonPanel.add(menuAIButton);
        menuButtonPanel.add(menuPlayerButton);

        menuFrame.add(menuTitlePanel, BorderLayout.NORTH);
        menuFrame.add(menuButtonPanel, BorderLayout.CENTER);
        menuFrame.setVisible(true);
    }

    private void setUpTiles() {
        // Create and configure each tile (button) on the game board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                JButton tile = new JButton();
                tile.setBackground(Color.ORANGE);
                tile.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
                tile.setFont(new Font("Segoe UI Symbol", Font.BOLD, 150));
                tile.setFocusable(false);
                tile.setCursor(new Cursor(Cursor.HAND_CURSOR));
                gameBoard[i][j] = tile;
    
                gameFramePanel.add(tile); // Add the tile to the game board panel
    
                // Add click event for each tile
                tile.addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) {
                        JButton selectedTile = (JButton) e.getSource(); // Toma componente GUI acionado
    
                        // Only proceed if the game is not over
                        if (!isOver) {
                            // Check if the tile is empty
                            if (selectedTile.getText().equals("")) {
                                selectedTile.setText(currentPlayer);
                                selectedTile.setForeground(currentPlayer.equals("✕") ? Color.WHITE : (new Color(10, 10, 100)));
                                
                                // Check for a winner or a draw
                                if (hasWinner()) {
                                    isOver = true;
                                    if (currentPlayer.equals("✕")) {
                                        xScore++;
                                        xScoreLabel.setText("✕: " + String.valueOf(xScore));
                                    } else if (currentPlayer.equals("⭕")) {
                                        oScore++;
                                        oScoreLabel.setText("⭕: " + String.valueOf(oScore));
                                    }
                                } else if (turn == 9) {
                                    isOver = true;
                                    handleDraw();
                                } else {
                                    turn++;
                                    // Alternates between X and O
                                    currentPlayer = currentPlayer.equals("✕") ? "⭕" : "✕";
                                    turnLabel.setText(currentPlayer + " to play!");
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private boolean hasWinner() {
            // Vertical
            for (int c=0; c < 3; c++){
                if(gameBoard[0][c].getText() == gameBoard[1][c].getText() &&
                   gameBoard[1][c].getText() == gameBoard[2][c].getText() &&
                   gameBoard[0][c].getText() != "") {
                    setWinner(gameBoard[0][c], gameBoard[1][c], gameBoard[2][c]);
                    return true;
                }
            }

            // Horizontal
            for (int r=0; r < 3; r++){
                if(gameBoard[r][0].getText() == gameBoard[r][1].getText() &&
                   gameBoard[r][1].getText() == gameBoard[r][2].getText() &&
                   gameBoard[r][0].getText() != "") {
                    setWinner(gameBoard[r][0], gameBoard[r][1], gameBoard[r][2]);
                    return true;
                }
            }

            // Diagonal (↘)
            if (gameBoard[0][0].getText() == gameBoard[1][1].getText() &&
                gameBoard[1][1].getText() == gameBoard[2][2].getText() &&
                gameBoard[0][0].getText() != "") {
                    setWinner(gameBoard[0][0], gameBoard[1][1], gameBoard[2][2]);
                    return true;
                }

            // Diagonal (↗)
            if (gameBoard[0][2].getText() == gameBoard[1][1].getText() &&
                gameBoard[1][1].getText() == gameBoard[2][0].getText() &&
                gameBoard[0][2].getText() != "") {
                    setWinner(gameBoard[0][2], gameBoard[1][1], gameBoard[2][0]);
                    return true;
                }
            return false;
        }

    private void setWinner(JButton firstButton, JButton secondButton, JButton thirdButton) {
        firstButton.setBackground(Color.GREEN);
        secondButton.setBackground(Color.GREEN);
        thirdButton.setBackground(Color.GREEN);
        turnLabel.setText(firstButton.getText() + " won!");
    }

    private void miniMax() {
        // Working on it!
    }

    private void handleDraw() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                gameBoard[i][j].setBackground(Color.DARK_GRAY);
                gameBoard[i][j].setForeground(Color.GRAY);
                turnLabel.setText("It's a draw!");
            }
        }
    }

}
