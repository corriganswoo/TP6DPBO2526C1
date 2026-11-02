import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Canvas utama game
public class View extends JPanel {
    int width = 360;
    int height = 640;
    private Logic logic;
    private Image bgImage;

    // Constructor View
    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(logic);

        try {
            bgImage = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();
        } catch (Exception e) {
            bgImage = null;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Render komponen grafis
    public void draw(Graphics g) {
        // Render background
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, width, height, null);
        } else {
            g.setColor(new Color(100, 200, 255));
            g.fillRect(0, 0, width, height);
        }

        // Render player
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        // Render list pipa
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // Render overlay Game Over
        if (logic.isGameOver()) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, width, height);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            FontMetrics fm = g.getFontMetrics();
            String goText = "GAME OVER";
            int x1 = (width - fm.stringWidth(goText)) / 2;
            g.drawString(goText, x1, height / 2 - 20);

            g.setFont(new Font("Arial", Font.PLAIN, 18));
            fm = g.getFontMetrics();
            String restartText = "Press 'R' to Restart";
            int x2 = (width - fm.stringWidth(restartText)) / 2;
            g.drawString(restartText, x2, height / 2 + 30);
        }

        if (!logic.isGameStarted() && !logic.isGameOver()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fm = g.getFontMetrics();
            String startText = "Press SPACE to Start";
            int x = (width - fm.stringWidth(startText)) / 2;
            g.drawString(startText, x, height / 2 + 50);
        }
    }
}