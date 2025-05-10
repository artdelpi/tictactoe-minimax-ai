import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JPanel panel = new JPanel();
    String currentPlayer = "X";
    JLabel label = new JLabel("Now it's " + currentPlayer + "'s turn!");
    int turn = 1;
    boolean isOver = false;

    // Matriz 3x3 que representa as casas do jogo (resolução lógica)
    JButton[][] buttons = new JButton[3][3];

    // Configura a interface gráfica e inicializa o jogo
    TicTacToe() {
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Centraliza a janela
        
        // Painel com layout em grade para organizar os botões (3x3)
        panel.setLayout(new GridLayout(3, 3));
        
        label.setOpaque(true); // Habilita cor de fundo
        label.setBackground(Color.BLACK); 
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        
        // Criação dos botões pra cada slot do tabuleiro
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                JButton slot = new JButton();
                slot.setBackground(Color.darkGray);
                slot.setForeground(Color.WHITE);
                slot.setFont(new Font("Arial", Font.BOLD, 100));
                slot.setFocusable(false);
                buttons[i][j] = slot; // Atribui o botão à posição correspondente na matriz

                panel.add(slot); // Insere botão no grid do painel

                // Adiciona o evento de clique para cada botão (slot)
                slot.addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) {
                        JButton slot = (JButton) e.getSource(); // Toma componente GUI acionado

                        // Só realiza o evento se o jogo não tiver acabado
                        if (!isOver) {
                            // Preenche o slot se estiver vazio
                            if (slot.getText() == "") {
                                slot.setText(currentPlayer);

                                // Alterna entre os jogadores X e O
                                currentPlayer = currentPlayer == "X" ? "O" : "X";
                                label.setText("Now it's " + currentPlayer + "'s turn!");
                            }
                            turn++;

                            // Condição de parada
                            if (hasWinner() || turn > 9) {
                                isOver = true;
                            }
                        }
                    }
                });

            }
        }

        frame.add(label, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    boolean hasWinner() {
            // Vertical
            for (int j=0; j < 3; j++){
                if(buttons[0][j].getText() == buttons[1][j].getText() &&
                   buttons[1][j].getText() == buttons[2][j].getText() &&
                   buttons[0][j].getText() != "") {
                    buttons[0][j].setBackground(Color.YELLOW);
                    buttons[0][j].setForeground(Color.GREEN);

                    buttons[1][j].setBackground(Color.YELLOW);
                    buttons[1][j].setForeground(Color.GREEN);

                    buttons[2][j].setBackground(Color.YELLOW);
                    buttons[2][j].setForeground(Color.GREEN);
                    
                    label.setText(buttons[0][j].getText() + " is the winner!");
                    return true;
                }
            }

            // Horizontal
            for (int i=0; i < 3; i++){
                if(buttons[i][0].getText() == buttons[i][1].getText() &&
                   buttons[i][1].getText() == buttons[i][2].getText() &&
                   buttons[i][0].getText() != "") {
                    buttons[i][0].setBackground(Color.YELLOW);
                    buttons[i][0].setForeground(Color.GREEN);

                    buttons[i][1].setBackground(Color.YELLOW);
                    buttons[i][1].setForeground(Color.GREEN);

                    buttons[i][2].setBackground(Color.YELLOW);
                    buttons[i][2].setForeground(Color.GREEN);
                    
                    label.setText(buttons[i][0].getText() + " is the winner!");
                    return true;
                }
            }

            // Diagonal (↘)
            if (buttons[0][0].getText() == buttons[1][1].getText() &&
                buttons[1][1].getText() == buttons[2][2].getText() &&
                buttons[0][0].getText() != "") {
                    buttons[0][0].setBackground(Color.YELLOW);
                    buttons[0][0].setForeground(Color.GREEN);

                    buttons[1][1].setBackground(Color.YELLOW);
                    buttons[1][1].setForeground(Color.GREEN);

                    buttons[2][2].setBackground(Color.YELLOW);
                    buttons[2][2].setForeground(Color.GREEN);

                    label.setText(buttons[0][0].getText() + " is the winner!");
                    return true;
                }

            // Diagonal (↗)
            if (buttons[0][2].getText() == buttons[1][1].getText() &&
                buttons[1][1].getText() == buttons[2][0].getText() &&
                buttons[0][2].getText() != "") {
                    buttons[0][2].setBackground(Color.YELLOW);
                    buttons[0][2].setForeground(Color.GREEN);

                    buttons[1][1].setBackground(Color.YELLOW);
                    buttons[1][1].setForeground(Color.GREEN);

                    buttons[2][0].setBackground(Color.YELLOW);
                    buttons[2][0].setForeground(Color.GREEN);

                    label.setText(buttons[0][2].getText() + " is the winner!");
                    return true;
                }
            
            return false;
        }
}
