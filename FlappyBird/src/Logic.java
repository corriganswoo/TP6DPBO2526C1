import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

// Kelas pengontrol alur dan logika game
public class Logic implements ActionListener, KeyListener {
    // Ukuran frame
    int frameWidth = 360;
    int frameHeight = 640;

    // Posisi awal player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    // Spesifikasi pipa
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    int pipeVelocityX = -2;

    // Timer game dan gravitasi
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    // Aset gambar
    View view;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    Player player;
    ArrayList<Pipe> pipes;

    // Status Game dan Skor
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private double score = 0;
    private JLabel scoreLabel;

    // Constructor Logic
    public Logic(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;

        // Load aset gambar dari folder assets
        birdImage = new ImageIcon(getClass().getResource("/assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("/assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("/assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        // Timer spawn pipa (2.2 detik)
        pipesCooldown = new Timer(2200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        // Timer game loop (60 FPS)
        gameLoop = new Timer(1000 / 60, this);
    }

    // Dipanggil dari MainMenu (Set ke mode standby dulu)
    public void startGame() {
        resetState();
        gameStarted = false; // Menunggu player tekan Spasi
        gameLoop.start();     // Tetap di-start agar View dirender, tapi move() ditahan
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public boolean isGameStarted() { return gameStarted; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return (int) score; }

    // Spawn pipa atas dan bawah secara acak
    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    // Update posisi objek dan kondisi game
    public void move() {
        // Jika game belum mulai atau sudah game over, jangan gerakkan player & pipa
        if (!gameStarted || gameOver) return;

        // Pergerakan player
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // Pergerakan pipa dan cek tabrakan / skor
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            // Tambah skor (+1 per pasang pipa)
            if (!pipe.passed && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.passed = true;
                score += 0.5;
                scoreLabel.setText("Score: " + (int) score);
                playSound("/assets/point.wav");
            }

            // Cek tabrakan pipa (Spesifikasi Wajib 1)
            if (checkCollision(player, pipe)) {
                triggerGameOver();
            }
        }

        // Cek jatuh ke batas bawah (Spesifikasi Wajib 1)
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            triggerGameOver();
        }
    }

    // Deteksi benturan (AABB Collision)
    private boolean checkCollision(Player p, Pipe pipe) {
        return p.getPosX() < pipe.getPosX() + pipe.getWidth() &&
                p.getPosX() + p.getWidth() > pipe.getPosX() &&
                p.getPosY() < pipe.getPosY() + pipe.getHeight() &&
                p.getPosY() + p.getHeight() > pipe.getPosY();
    }

    // Set Game Over dan stop timer
    private void triggerGameOver() {
        gameOver = true;
        gameLoop.stop();
        pipesCooldown.stop();
        playSound("/assets/hit.wav");
    }

    // Helper untuk mereset posisi variabel
    private void resetState() {
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        pipes.clear();
        score = 0;
        scoreLabel.setText("Score: 0");
        gameOver = false;
    }

    // Reset data game saat tombol R ditekan
    public void restartGame() {
        resetState();
        gameStarted = true; // Langsung jalan jika di-restart
        gameLoop.start();
        pipesCooldown.start();
    }

    // Play efek suara (Bonus Audio)
    private void playSound(String soundFilePath) {
        try {
            URL url = getClass().getResource(soundFilePath);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }
        } catch (Exception e) {
            // Abaikan error jika audio tidak ada
        }
    }

    // Game loop tick
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // Input keyboard Spasi & tombol R
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Jika game belum mulai, tekan SPASI pertama kali akan memulai game
            if (!gameStarted) {
                gameStarted = true;
                pipesCooldown.start(); // Jalankan timer pemunculan pipa
            }

            // Jika game sedang berjalan dan belum game over, buat burung melompat
            if (!gameOver) {
                player.setVelocityY(-10);
                playSound("/assets/wing.wav");
            }
        }

        // Tombol R untuk restart (Spesifikasi Wajib 2)
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}