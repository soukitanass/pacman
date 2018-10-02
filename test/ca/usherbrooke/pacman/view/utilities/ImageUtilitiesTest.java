package ca.usherbrooke.pacman.view.utilities;

import static org.junit.Assert.assertEquals;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import ca.usherbrooke.pacman.model.exceptions.InvalidColorException;
import ca.usherbrooke.pacman.model.exceptions.InvalidLetterException;
import ca.usherbrooke.pacman.view.Color;
import ca.usherbrooke.pacman.view.SpriteFacade;

public class ImageUtilitiesTest {

  private ImageUtilities utilities = new ImageUtilities();

  @Test
  public void resizeImageTest() throws InvalidLetterException, InvalidColorException {
    SpriteFacade spriteFacade = new SpriteFacade();
    BufferedImage image = spriteFacade.getLetter('a', Color.WHITE);

    final int newWidth = 200;
    final int newHeight = 200;
    BufferedImage resizedImage = utilities.resize(image, newHeight, newWidth);

    assertEquals(resizedImage.getWidth(), 200);
    assertEquals(resizedImage.getHeight(), 200);
  }

  @Test
  public void joinImagesTest() throws InvalidLetterException, InvalidColorException {
    SpriteFacade spriteFacade = new SpriteFacade();
    BufferedImage image1 = spriteFacade.getLetter('a', Color.WHITE);
    BufferedImage image2 = spriteFacade.getLetter('b', Color.WHITE);

    List<BufferedImage> images = new ArrayList<>();
    images.add(image1);
    images.add(image2);

    BufferedImage image = utilities.joinImages(images);
    BufferedImage subimage1 = image.getSubimage(0, 0, image1.getWidth(), image1.getHeight());
    BufferedImage subimage2 =
        image.getSubimage(image1.getWidth(), 0, image2.getWidth(), image2.getHeight());

    assertEquals(16, image.getHeight());
    assertEquals(32, image.getWidth());
    compareImage(image1, subimage1);
    compareImage(image2, subimage2);
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
