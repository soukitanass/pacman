/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.panel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import ca.usherbrooke.pacman.view.spirites.SpriteFacade;
import ca.usherbrooke.pacman.view.utilities.ImageUtilities;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public abstract class AbstractPanel extends JPanel {

  private ImageUtilities imageUtilities = new ImageUtilities();
  private SpriteFacade spriteFacade = new SpriteFacade();

  protected int pixelTileSize;
  protected int offsetX;
  protected int offsetY;

  protected BufferedImage getLabelImage(final String label, ca.usherbrooke.pacman.view.utilities.Color color,
      final double scaleFactor) {
    List<BufferedImage> images = new ArrayList<>();

    for (int i = 0; i < label.length(); i++) {
      BufferedImage image = null;
      try {
        if (Character.isLetter(label.charAt(i)) || label.charAt(i) == ' ') {
          image = spriteFacade.getLetter(label.charAt(i), color);
        } else {
          image = spriteFacade.getDigit(Character.getNumericValue(label.charAt(i)), color);
        }
        final int newWidth = (int) (image.getWidth() * scaleFactor);
        final int newHeight = (int) (image.getHeight() * scaleFactor);
        BufferedImage resizedImage = imageUtilities.resize(image, newWidth, newHeight);
        images.add(resizedImage);
      } catch (Exception exception) {
        WarningDialog.display("Error while painting the panel. ", exception);
      }
    }

    return imageUtilities.joinImages(images);
  }

  public void setPixelTileSize(int pixelTileSize) {
    this.pixelTileSize = pixelTileSize;
  }

  public void setOffsetX(int offsetX) {
    this.offsetX = offsetX;
  }

  public void setOffsetY(int offsetY) {
    this.offsetY = offsetY;
  }
}
