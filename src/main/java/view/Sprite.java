package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    private BufferedImage spriteSheet;
    private int tileSize = 32;

    public Sprite(String file, int tileSize) {
        this.spriteSheet = this.loadSprite(file);
        this.tileSize = tileSize;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) {
        return this.spriteSheet.getSubimage(xGrid * this.tileSize, yGrid * this.tileSize, this.tileSize, this.tileSize);
    }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("res/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }

}
