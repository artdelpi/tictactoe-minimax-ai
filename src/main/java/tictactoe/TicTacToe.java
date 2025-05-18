package tictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    // Declaration of GUI components
    private JFrame menuFrame;
    private JFrame gameFrame;
    private JPanel menuFramePanel;
    private JPanel gameFramePanel;
    private JPanel gameStatusPanel;
    private JButton restartButton;
    private JButton[][] gameBoard = new JButton[3][3];
    private JLabel turnLabel;
    private JLabel xScoreLabel;
    private JLabel oScoreLabel;

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
        menuFramePanel = new JPanel();
        gameFramePanel = new JPanel();
        gameStatusPanel = new JPanel();

        // Create GUI components
        restartButton = new JButton(">> RESTART <<");
        turnLabel = new JLabel(currentPlayer + " to play!");
        xScoreLabel = new JLabel("X: 0");
        oScoreLabel = new JLabel("O: 0");
    }

    private void initializeGameVariables() {
        currentPlayer = "X";
        xScore = 0;
        oScore = 0;
        turn = 1;
        isOver = false;
        gameModes = new String[2];
        gameModes[0] = "computerVsPlayer";
        gameModes[1] = "playerVsPlayer";
    }

    private void setUpUserInterface() {
        gameFrame.setSize(800, 800);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null); // Center the window
        gameFramePanel.setLayout(new GridLayout(3, 3));
        
        // Configure the restart button
        restartButton.setBackground(Color.black);
        restartButton.setForeground(Color.RED);
        restartButton.setFocusable(false);
        restartButton.setFont(new Font("ARIAL", Font.BOLD, 25));

        // Restart game when the button is clicked
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int r=0; r<3; r++) {
                    for (int c=0; c<3; c++) {
                        gameBoard[r][c].setText("");
                        gameBoard[r][c].setBackground(Color.DARK_GRAY);
                    }
                }
                turnLabel.setText(currentPlayer + " to play!");
                isOver = false;
                turn = 1;
            }
        });
        
        gameStatusPanel.setLayout(new BorderLayout());
        gameStatusPanel.setBackground(Color.GRAY);
        gameStatusPanel.add(turnLabel, BorderLayout.CENTER);
        gameStatusPanel.add(restartButton, BorderLayout.SOUTH);
        gameStatusPanel.add(oScoreLabel, BorderLayout.WEST);
        gameStatusPanel.add(xScoreLabel, BorderLayout.EAST);
        
        formatLabel(turnLabel);
        formatLabel(oScoreLabel);
        formatLabel(xScoreLabel);
        gameFrame.add(gameStatusPanel, BorderLayout.NORTH);
        gameFrame.add(gameFramePanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
    }

    private void setUpTiles() {
        // Create and configure each tile (button) on the game board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                JButton tile = new JButton();
                tile.setBackground(Color.darkGray);
                tile.setFont(new Font("Arial", Font.BOLD, 135));
                tile.setFocusable(false);
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
                                selectedTile.setForeground(currentPlayer.equals("X") ? Color.RED : Color.BLUE);
                                
                                // Check for a winner or a draw
                                if (hasWinner()) {
                                    isOver = true;
                                    if (currentPlayer.equals("X")) {
                                        xScore++;
                                        xScoreLabel.setText("X: " + String.valueOf(xScore));
                                    } else if (currentPlayer.equals("O")) {
                                        oScore++;
                                        oScoreLabel.setText("O: " + String.valueOf(oScore));
                                    }
                                } else if (turn == 9) {
                                    isOver = true;
                                    handleDraw();
                                } else {
                                    turn++;
                                    // Alternates between X and O
                                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
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

    private void formatLabel(JLabel label) {
        label.setOpaque(true); // Habilita cor de fundo
        label.setBackground(Color.GRAY); 
        label.setForeground(Color.WHITE);
        label.setFont(new Font("DialogInput", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void handleDraw() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                gameBoard[i][j].setBackground(Color.BLACK);
                gameBoard[i][j].setForeground(Color.GRAY);
                turnLabel.setText("It's a draw!");
            }
        }
    }

}
