import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Panel Main Menu (Spesifikasi Wajib 5)
public class MainMenu extends JPanel {
    private JButton startButton;
    private JButton exitButton;

    // Constructor MainMenu dengan listener tombol
    public MainMenu(ActionListener onStart, ActionListener onExit) {
        setPreferredSize(new Dimension(360, 640));
        setLayout(new GridBagLayout());
        setBackground(new Color(100, 200, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        // Judul Game
        JLabel titleLabel = new JLabel("FLAPPY BIRD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Tombol Start Game
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(150, 40));
        startButton.addActionListener(onStart);
        gbc.gridy = 1;
        add(startButton, gbc);

        // Tombol Exit Game
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setPreferredSize(new Dimension(150, 40));
        exitButton.addActionListener(onExit);
        gbc.gridy = 2;
        add(exitButton, gbc);
    }
}