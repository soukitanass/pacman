/*******************************************************************************
 * Team agilea18b, Pacman
 *
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import ca.usherbrooke.pacman.model.exceptions.InvalidColorException;
import ca.usherbrooke.pacman.model.exceptions.InvalidLetterException;
import ca.usherbrooke.pacman.view.spirites.SpriteFacade;

public class ImageUtilitiesTest {

  @Test
  public void resizeImage() throws InvalidLetterException, InvalidColorException {
    SpriteFacade spriteFacade = new SpriteFacade();
    BufferedImage image = spriteFacade.getLetter('a', Color.WHITE);

    BufferedImage resizedImage = ImageUtilities.resize(image, 200, 300);

    assertEquals(resizedImage.getWidth(), 200);
    assertEquals(resizedImage.getHeight(), 300);
  }

  @Test
  public void joinImages() throws InvalidLetterException, InvalidColorException {
    SpriteFacade spriteFacade = new SpriteFacade();
    BufferedImage image1 = spriteFacade.getLetter('a', Color.WHITE);
    BufferedImage image2 = spriteFacade.getLetter('b', Color.WHITE);

    List<BufferedImage> images = new ArrayList<>();
    images.add(image1);
    images.add(image2);

    BufferedImage image = ImageUtilities.joinImages(images);
    BufferedImage subimage1 = image.getSubimage(0, 0, image1.getWidth(), image1.getHeight());
    BufferedImage subimage2 =
        image.getSubimage(image1.getWidth(), 0, image2.getWidth(), image2.getHeight());

    assertEquals(32, image.getWidth());
    assertEquals(16, image.getHeight());
    compareImage(image1, subimage1);
    compareImage(image2, subimage2);
  }

  @Test
  public void joinZeroImagesReturnNull() throws InvalidLetterException, InvalidColorException {
    assertNull(ImageUtilities.joinImages(new ArrayList<>()));
  }

  @Test
  public void getTextImage() throws InvalidLetterException, InvalidColorException {
    SpriteFacade spriteFacade = new SpriteFacade();
    BufferedImage expectedImageA = spriteFacade.getLetter('a', Color.WHITE);
    assertEquals(16, expectedImageA.getHeight());
    assertEquals(16, expectedImageA.getWidth());
    BufferedImage expectedImageB = spriteFacade.getLetter('b', Color.WHITE);
    assertEquals(16, expectedImageB.getHeight());
    assertEquals(16, expectedImageB.getWidth());

    BufferedImage imageAB = ImageUtilities.getTextImage("ab", Color.WHITE, 1);
    assertEquals(32, imageAB.getWidth());
    assertEquals(16, imageAB.getHeight());
    BufferedImage subimageA = imageAB.getSubimage(0, 0, 16, 16);
    BufferedImage subimageB = imageAB.getSubimage(16, 0, 16, 16);

    compareImage(expectedImageA, subimageA);
    compareImage(expectedImageB, subimageB);
  }

  private void compareImage(BufferedImage image1, BufferedImage image2) {
    assertEquals(image1.getWidth(), image2.getWidth());
    assertEquals(image1.getHeight(), image2.getHeight());

    for (int x = 0; x < image2.getWidth(); x++) {
      for (int y = 0; y < image2.getHeight(); y++) {
        assertEquals(image2.getRGB(x, y), image1.getRGB(x, y));
      }
    }
  }

}
