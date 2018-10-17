/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.spirites;

import javax.imageio.ImageIO;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
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
    } catch (RasterFormatException exception) {
      WarningDialog.display("Invalid Layout. ", exception);
    }
    return subImage;
  }

  private BufferedImage loadSprite(String filename) {
    BufferedImage sprite = null;
    try {
      sprite = ImageIO.read(Sprite.class.getClassLoader().getResource(filename + ".png"));
    } catch (IOException exception) {
      WarningDialog.display("Error while opening sprite file. ", exception);
    }
    return sprite;
  }

}
