import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main App / Entry point
public class App {
    public static void main(String[] args) {
        // Setup JFrame
        JFrame frame = new JFrame("FlappyBird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // CardLayout untuk switch menu dan game
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Label Skor di atas window (Spesifikasi Wajib 3)
        JLabel scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.DARK_GRAY);
        scoreLabel.setForeground(Color.WHITE);

        // Inisialisasi Logic dan View
        Logic logika = new Logic(scoreLabel);
        View tampilan = new View(logika);
        logika.setView(tampilan);

        // Inisialisasi Main Menu (Spesifikasi Wajib 5)
        MainMenu mainMenu = new MainMenu(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "Game");
                        tampilan.requestFocusInWindow();
                        logika.startGame();
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );

        // Container layar game (Skor + Kanvas)
        JPanel gameContainer = new JPanel(new BorderLayout());
        gameContainer.add(scoreLabel, BorderLayout.NORTH);
        gameContainer.add(tampilan, BorderLayout.CENTER);

        // Tambah panel ke CardLayout
        mainPanel.add(mainMenu, "Menu");
        mainPanel.add(gameContainer, "Game");

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}