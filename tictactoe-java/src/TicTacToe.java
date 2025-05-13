import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JPanel panel = new JPanel();
    JPanel gameStatusPanel = new JPanel();
    JButton restartButton = new JButton(">> RESTART <<");

    String currentPlayer = "X";
    JLabel label = new JLabel(currentPlayer + " to play!");

    int turn = 1;
    boolean isOver = false;

    // Matriz 3x3 que representa as casas do jogo (resolução lógica)
    JButton[][] buttons = new JButton[3][3];

    // Configura a interface gráfica e inicializa o jogo
    TicTacToe() {
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Centraliza a janela
        
        // Painel com layout em grade para organizar os botões (3x3)
        panel.setLayout(new GridLayout(3, 3));

        label.setOpaque(true); // Habilita cor de fundo
        label.setBackground(Color.GRAY); 
        label.setForeground(Color.WHITE);
        label.setFont(new Font("DialogInput", Font.BOLD, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        restartButton.setBackground(Color.black);
        restartButton.setForeground(Color.RED);
        restartButton.setFocusable(false);
        restartButton.setFont(new Font("ARIAL", Font.BOLD, 25));
        
        gameStatusPanel.setLayout(new BorderLayout());
        gameStatusPanel.setBackground(Color.GRAY);
        gameStatusPanel.add(label, BorderLayout.CENTER);
        gameStatusPanel.add(restartButton, BorderLayout.SOUTH);

        // Criação dos botões pra cada slot do tabuleiro
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                JButton tile = new JButton();
                tile.setBackground(Color.darkGray);
                tile.setFont(new Font("Arial", Font.BOLD, 135));
                tile.setFocusable(false);
                buttons[i][j] = tile; // Atribui o botão à posição correspondente na matriz

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
                                    selectedTile.setForeground(Color.white);
                                }

                                // Alterna entre os jogadores X e O
                                currentPlayer = currentPlayer == "X" ? "O" : "X";
                                label.setText(currentPlayer + " to play!");
                                
                                // Condição de parada
                                if (hasWinner()) {
                                    isOver = true;
                                } else if (turn == 9) {
                                    isOver = true;
                                    handleDraw();
                                }

                                turn++;
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
                if(buttons[0][c].getText() == buttons[1][c].getText() &&
                   buttons[1][c].getText() == buttons[2][c].getText() &&
                   buttons[0][c].getText() != "") {
                    setWinner(buttons[0][c], buttons[1][c], buttons[2][c]);
                    return true;
                }
            }

            // Horizontal
            for (int r=0; r < 3; r++){
                if(buttons[r][0].getText() == buttons[r][1].getText() &&
                   buttons[r][1].getText() == buttons[r][2].getText() &&
                   buttons[r][0].getText() != "") {
                    setWinner(buttons[r][0], buttons[r][1], buttons[r][2]);
                    return true;
                }
            }

            // Diagonal (↘)
            if (buttons[0][0].getText() == buttons[1][1].getText() &&
                buttons[1][1].getText() == buttons[2][2].getText() &&
                buttons[0][0].getText() != "") {
                    setWinner(buttons[0][0], buttons[1][1], buttons[2][2]);
                    return true;
                }

            // Diagonal (↗)
            if (buttons[0][2].getText() == buttons[1][1].getText() &&
                buttons[1][1].getText() == buttons[2][0].getText() &&
                buttons[0][2].getText() != "") {
                    setWinner(buttons[0][2], buttons[1][1], buttons[2][0]);
                    return true;
                }
            return false;
        }

    void setWinner(JButton firstButton, JButton secondButton, JButton thirdButton) {
        firstButton.setBackground(Color.GREEN);
        secondButton.setBackground(Color.GREEN);
        thirdButton.setBackground(Color.GREEN);
        label.setText(firstButton.getText() + " is the winner!");
    }

    void handleDraw() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setBackground(Color.BLACK);
                buttons[i][j].setForeground(Color.GRAY);
                label.setText("It's a draw!");
            }
        }
    }

}
