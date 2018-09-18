package view;

import static org.junit.Assert.assertTrue;
import java.awt.image.BufferedImage;
import org.junit.Test;
import model.Color;
import model.Direction;
import model.GhostState;
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

    image1 = sprite.getSprite(16, 3);
    image2 = spriteFacade.getPacman(Direction.RIGHT, PacManState.STATE1);
    assertTrue(compareImages(image1, image2));
  }

  @Test
  public void getLeftPacManTest() throws Exception {
    BufferedImage image1;
    BufferedImage image2;
    
    image1 = sprite.getSprite(4, 3);
    image2 = spriteFacade.getPacman(Direction.LEFT, PacManState.STATE5);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(5, 3);
    image2 = spriteFacade.getPacman(Direction.LEFT, PacManState.STATE4);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(6, 3);
    image2 = spriteFacade.getPacman(Direction.LEFT, PacManState.STATE3);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(7, 3);
    image2 = spriteFacade.getPacman(Direction.LEFT, PacManState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(16, 3);
    image2 = spriteFacade.getPacman(Direction.LEFT, PacManState.STATE1);
    assertTrue(compareImages(image1, image2));
  }

  @Test
  public void getDownPacManTest() throws Exception {
    BufferedImage image1;
    BufferedImage image2;
    
    image1 = sprite.getSprite(8, 3);
    image2 = spriteFacade.getPacman(Direction.DOWN, PacManState.STATE5);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(9, 3);
    image2 = spriteFacade.getPacman(Direction.DOWN, PacManState.STATE4);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(10, 3);
    image2 = spriteFacade.getPacman(Direction.DOWN, PacManState.STATE3);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(11, 3);
    image2 = spriteFacade.getPacman(Direction.DOWN, PacManState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(16, 3);
    image2 = spriteFacade.getPacman(Direction.DOWN, PacManState.STATE1);
    assertTrue(compareImages(image1, image2));
  }

  @Test
  public void getUpPacManTest() throws Exception {
    BufferedImage image1;
    BufferedImage image2;
    
    image1 = sprite.getSprite(12, 3);
    image2 = spriteFacade.getPacman(Direction.UP, PacManState.STATE5);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(13, 3);
    image2 = spriteFacade.getPacman(Direction.UP, PacManState.STATE4);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(14, 3);
    image2 = spriteFacade.getPacman(Direction.UP, PacManState.STATE3);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(15, 3);
    image2 = spriteFacade.getPacman(Direction.UP, PacManState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(16, 3);
    image2 = spriteFacade.getPacman(Direction.UP, PacManState.STATE1);
    assertTrue(compareImages(image1, image2));
  }

  @Test
  public void getRedGhostTest() throws Exception {
    BufferedImage image1;
    BufferedImage image2;
    
    image1 = sprite.getSprite(0, 4);
    image2 = spriteFacade.getGhost(Direction.UP, Color.RED, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(1, 4);
    image2 = spriteFacade.getGhost(Direction.UP, Color.RED, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(2, 4);
    image2 = spriteFacade.getGhost(Direction.LEFT, Color.RED, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(3, 4);
    image2 = spriteFacade.getGhost(Direction.LEFT, Color.RED, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(4, 4);
    image2 = spriteFacade.getGhost(Direction.DOWN, Color.RED, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(5, 4);
    image2 = spriteFacade.getGhost(Direction.DOWN, Color.RED, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(6, 4);
    image2 = spriteFacade.getGhost(Direction.RIGHT, Color.RED, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(7, 4);
    image2 = spriteFacade.getGhost(Direction.RIGHT, Color.RED, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
  }

  @Test
  public void getOrangeGhostTest() throws Exception {
    BufferedImage image1;
    BufferedImage image2;
    
    image1 = sprite.getSprite(8, 4);
    image2 = spriteFacade.getGhost(Direction.UP, Color.ORANGE, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(9, 4);
    image2 = spriteFacade.getGhost(Direction.UP, Color.ORANGE, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(10, 4);
    image2 = spriteFacade.getGhost(Direction.LEFT, Color.ORANGE, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(11, 4);
    image2 = spriteFacade.getGhost(Direction.LEFT, Color.ORANGE, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(12, 4);
    image2 = spriteFacade.getGhost(Direction.DOWN, Color.ORANGE, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(13, 4);
    image2 = spriteFacade.getGhost(Direction.DOWN, Color.ORANGE, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(14, 4);
    image2 = spriteFacade.getGhost(Direction.RIGHT, Color.ORANGE, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));
    
    image1 = sprite.getSprite(15, 4);
    image2 = spriteFacade.getGhost(Direction.RIGHT, Color.ORANGE, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
  }
}
