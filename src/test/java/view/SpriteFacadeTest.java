package view;

import static org.junit.Assert.assertTrue;
import java.awt.image.BufferedImage;
import org.junit.Test;
import model.Direction;
import model.PacManState;

public class SpriteFacadeTest {

  private static final String FILE = "sprites";
  private static final int TILE_SIZE = 8;
  private SpriteFacade spriteFacade = new SpriteFacade();
  private Sprite sprite = new Sprite(FILE, TILE_SIZE);

  private boolean compareImages(BufferedImage image1, BufferedImage image2) {
    for (int i = 0; i < image1.getWidth(); i++)
    {
      for (int j = 0; j < image1.getHeight(); j++)
      {
        if (image1.getRGB(i, j) != image2.getRGB(i, j)) {
          return false;
        }
      }
    }
    return true;
  }
  
  @Test
  public void getWallTest() throws Exception {
    BufferedImage image1;
    BufferedImage image2;
    for (int j = 0; j < 2; j++) {
      for (int i = 0; i < 18; i++) {
        image1 = sprite.getSprite(i, j);
        image2 = spriteFacade.getWall(i + (19 * j));
        assertTrue(compareImages(image1, image2));
      }
    }
  }
  
  @Test
  public void getRightPacManTest() throws Exception {
    BufferedImage image1;
    BufferedImage image2;
    
    image1 = sprite.getSprite(0, 3);
    image2 = spriteFacade.getPacman(Direction.RIGHT, PacManState.STATE5);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(1, 3);
    image2 = spriteFacade.getPacman(Direction.RIGHT, PacManState.STATE4);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(2, 3);
    image2 = spriteFacade.getPacman(Direction.RIGHT, PacManState.STATE3);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(3, 3);
    image2 = spriteFacade.getPacman(Direction.RIGHT, PacManState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(4, 3);
    image2 = spriteFacade.getPacman(Direction.RIGHT, PacManState.STATE1);
    assertTrue(compareImages(image1, image2));
  }
}
