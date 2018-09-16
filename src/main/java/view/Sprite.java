package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
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
    BufferedImage subImage = null;
    try {
      subImage = spriteSheet.getSubimage(xGrid * tileSize, yGrid * tileSize, tileSize, tileSize);
    } catch (RasterFormatException e) {
      System.out.println(e.toString());
    }
    return subImage;
  }

  private BufferedImage loadSprite(String file) {
    BufferedImage sprite = null;
    try {
      sprite = ImageIO.read(new File("src/main/res/" + file + ".png"));
    } catch (IOException e) {
      System.out.println(e.toString());
    }
    return sprite;
  }

}
