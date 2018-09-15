package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
  private BufferedImage spriteSheet;
  private int tileSize;

  public Sprite(String file, int tileSize) {
    this.spriteSheet = this.loadSprite(file);
    this.tileSize = tileSize;
  }

  public BufferedImage getSprite(int xGrid, int yGrid) {
    return spriteSheet.getSubimage(xGrid * tileSize, yGrid * tileSize, tileSize, tileSize);
  }

  private BufferedImage loadSprite(String file) {
    BufferedImage sprite = null;

    try {
      sprite = ImageIO.read(new File("src/main/res/" + file + ".png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sprite;
  }

}
