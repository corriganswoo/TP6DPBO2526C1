import java.awt.Image;

// Kelas Player untuk karakter burung
public class Player {
    // Posisi dan ukuran burung
    private int posX;
    private int posY;
    private int width;
    private int height;

    // Gambar burung
    private Image image;

    // Kecepatan gerak vertikal
    private int velocityY;

    // Constructor Player
    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityY = 0;
    }

    // Getter dan Setter
    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }

    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }

    public int getVelocityY() { return velocityY; }
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }
}