/*******************************************************************************
 * Team agilea18b, Pacman
 *
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.utilities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.view.spirites.SpriteFacade;

public class ImageUtilities {

  private static final char DOT = '.';
  private static final char SPACE = ' ';

  private ImageUtilities() {
    throw new IllegalStateException("Utility class");
  }

  private static SpriteFacade spriteFacade = new SpriteFacade();

  public static BufferedImage resize(BufferedImage img, int width, int height) {
    Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resized.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    return resized;
  }

  public static BufferedImage joinImages(List<BufferedImage> images) {
    if (images.isEmpty()) {
      return null;
    }
    final int height = images.get(0).getHeight();
    int width = 0;
    int x = 0;

    for (BufferedImage image : images) {
      width += image.getWidth();
    }

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = image.createGraphics();
    for (int i = 0; i < images.size(); i++) {
      g2d.drawImage(images.get(i), x, 0, null);
      x += images.get(i).getWidth();
    }
    g2d.dispose();
    return image;
  }

  public static BufferedImage getTextImage(String text,
      ca.usherbrooke.pacman.view.utilities.Color color, final double scaleFactor) {
    List<BufferedImage> images = new ArrayList<>();

    for (int i = 0; i < text.length(); i++) {
      BufferedImage image = null;
      try {
        if (Character.isLetter(text.charAt(i))) {
          image = spriteFacade.getLetter(text.charAt(i), color);
        } else if (text.charAt(i) == SPACE) {
          image = spriteFacade.getSpace();
        } else if (text.charAt(i) == DOT) {
          image = spriteFacade.getDot(color);
        } else {
          image = spriteFacade.getDigit(Character.getNumericValue(text.charAt(i)), color);
        }
        images.add(image);
      } catch (Exception exception) {
        WarningDialog.display("Error while painting the panel. ", exception);
      }
    }
    BufferedImage textImageNotScaled = ImageUtilities.joinImages(images);

    if (textImageNotScaled == null) {
      //Prevent to have a "NullPointerException"
      textImageNotScaled = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    final int newWidth = (int) (scaleFactor * textImageNotScaled.getWidth());
    final int newHeight = (int) (scaleFactor * textImageNotScaled.getHeight());
    return ImageUtilities.resize(textImageNotScaled, newWidth, newHeight);
  }

}
