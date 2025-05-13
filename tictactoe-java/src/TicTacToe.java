import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JPanel panel = new JPanel();
    JPanel gameStatusPanel = new JPanel();
    JButton restartButton = new JButton(">> RESTART <<");

    String currentPlayer = "X";
    JLabel turnLabel = new JLabel(currentPlayer + " to play!");

    int xScore = 0;
    JLabel xScoreLabel = new JLabel("X: " + String.valueOf(xScore));

    int oScore = 0;
    JLabel oScoreLabel = new JLabel("O: " + String.valueOf(oScore));

    int turn = 1;
    boolean isOver = false;

    // Matriz 3x3 que representa as casas do jogo (resolução lógica)
    JButton[][] board = new JButton[3][3];

    // Configura a interface gráfica e inicializa o jogo
    TicTacToe() {
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Centraliza a janela
        
        // Painel com layout em grade para organizar os botões (3x3)
        panel.setLayout(new GridLayout(3, 3));

        formatLabel(turnLabel);
        formatLabel(oScoreLabel);
        formatLabel(xScoreLabel);

        restartButton.setBackground(Color.black);
        restartButton.setForeground(Color.RED);
        restartButton.setFocusable(false);
        restartButton.setFont(new Font("ARIAL", Font.BOLD, 25));

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int r=0; r<3; r++) {
                    for (int c=0; c<3; c++) {
                        board[r][c].setText("");
                        board[r][c].setBackground(Color.DARK_GRAY);
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

        // Criação dos botões pra cada slot do tabuleiro
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                JButton tile = new JButton();
                tile.setBackground(Color.darkGray);
                tile.setFont(new Font("Arial", Font.BOLD, 135));
                tile.setFocusable(false);
                board[i][j] = tile; // Atribui o botão à posição correspondente na matriz

                panel.add(tile); // Insere botão no grid do painel

                // Adiciona o evento de clique para cada botão (slot)
                tile.addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) {
                        JButton selectedTile = (JButton) e.getSource(); // Toma componente GUI acionado

                        // Só realiza o evento se o jogo não tiver acabado
                        if (!isOver) {
                            // Preenche o slot se estiver vazio
                            if (selectedTile.getText().equals("")) {

                                selectedTile.setText(currentPlayer);
                                if (currentPlayer.equals("X")) {
                                    selectedTile.setForeground(Color.RED);
                                } else if (currentPlayer.equals("O")) {
                                    selectedTile.setForeground(Color.BLUE);
                                }
                                
                                // Condição de parada
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
                                    // Alterna entre os jogadores X e O
                                    currentPlayer = currentPlayer == "X" ? "O" : "X";
                                    turnLabel.setText(currentPlayer + " to play!");
                                }
                                
                            }

                        }
                    }
                });

            }
        }

        frame.add(gameStatusPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    boolean hasWinner() {
            // Vertical
            for (int c=0; c < 3; c++){
                if(board[0][c].getText() == board[1][c].getText() &&
                   board[1][c].getText() == board[2][c].getText() &&
                   board[0][c].getText() != "") {
                    setWinner(board[0][c], board[1][c], board[2][c]);
                    return true;
                }
            }

            // Horizontal
            for (int r=0; r < 3; r++){
                if(board[r][0].getText() == board[r][1].getText() &&
                   board[r][1].getText() == board[r][2].getText() &&
                   board[r][0].getText() != "") {
                    setWinner(board[r][0], board[r][1], board[r][2]);
                    return true;
                }
            }

            // Diagonal (↘)
            if (board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "") {
                    setWinner(board[0][0], board[1][1], board[2][2]);
                    return true;
                }

            // Diagonal (↗)
            if (board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "") {
                    setWinner(board[0][2], board[1][1], board[2][0]);
                    return true;
                }
            return false;
        }

    void setWinner(JButton firstButton, JButton secondButton, JButton thirdButton) {
        firstButton.setBackground(Color.GREEN);
        secondButton.setBackground(Color.GREEN);
        thirdButton.setBackground(Color.GREEN);
        turnLabel.setText(firstButton.getText() + " won!");
    }

    void formatLabel(JLabel label) {
        label.setOpaque(true); // Habilita cor de fundo
        label.setBackground(Color.GRAY); 
        label.setForeground(Color.WHITE);
        label.setFont(new Font("DialogInput", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    void handleDraw() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                board[i][j].setBackground(Color.BLACK);
                board[i][j].setForeground(Color.GRAY);
                turnLabel.setText("It's a draw!");
            }
        }
    }

}
