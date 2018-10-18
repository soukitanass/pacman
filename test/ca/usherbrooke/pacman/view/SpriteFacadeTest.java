/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.awt.image.BufferedImage;
import org.junit.Test;
import ca.usherbrooke.pacman.view.spirites.Sprite;
import ca.usherbrooke.pacman.view.spirites.SpriteFacade;
import ca.usherbrooke.pacman.view.states.GhostState;
import ca.usherbrooke.pacman.view.states.PacGumState;
import ca.usherbrooke.pacman.view.states.PacManState;
import ca.usherbrooke.pacman.view.utilities.Color;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.exceptions.InvalidColorException;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.exceptions.InvalidLetterException;
import ca.usherbrooke.pacman.model.exceptions.InvalidDigitException;
import ca.usherbrooke.pacman.model.exceptions.InvalidScoreException;
import ca.usherbrooke.pacman.model.exceptions.InvalidStateException;
import ca.usherbrooke.pacman.model.exceptions.InvalidWallCodeException;

public class SpriteFacadeTest {

  private static final String FILE = "sprites";
  private static final int TILE_SIZE = 16;
  private SpriteFacade spriteFacade = new SpriteFacade();
  private Sprite sprite = new Sprite(FILE, TILE_SIZE);

  private boolean compareImages(BufferedImage image1, BufferedImage image2) {
    for (int i = 0; i < image1.getWidth(); i++) {
      for (int j = 0; j < image1.getHeight(); j++) {
        if (image1.getRGB(i, j) != image2.getRGB(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  @Test
  public void getWallTest() throws InvalidWallCodeException {
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
  public void getWallWithInvalidCodeThrow() {
    try {
      spriteFacade.getWall(50);
    } catch (Exception exception) {
      assertEquals("Invalid wall code: 50", exception.getMessage());
    }
  }

  @Test
  public void getRightPacManTest() throws InvalidStateException, InvalidDirectionException {
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
  public void getLeftPacManTest() throws InvalidStateException, InvalidDirectionException {
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
  public void getDownPacManTest() throws InvalidStateException, InvalidDirectionException {
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
  public void getUpPacManTest() throws InvalidStateException, InvalidDirectionException {
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
  public void getPacManWithInvalidDirectionThrow() {
    try {
      spriteFacade.getPacman(null, PacManState.STATE1);
    } catch (Exception exception) {
      assertEquals("Invalid direction", exception.getMessage());
    }
  }

  @Test
  public void getRedGhostTest()
      throws InvalidColorException, InvalidDirectionException, InvalidStateException {
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
  public void getOrangeGhostTest()
      throws InvalidColorException, InvalidDirectionException, InvalidStateException {
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

  @Test
  public void getPinkGhostTest()
      throws InvalidColorException, InvalidDirectionException, InvalidStateException {
    BufferedImage image1;
    BufferedImage image2;

    image1 = sprite.getSprite(0, 5);
    image2 = spriteFacade.getGhost(Direction.UP, Color.PINK, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(1, 5);
    image2 = spriteFacade.getGhost(Direction.UP, Color.PINK, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(2, 5);
    image2 = spriteFacade.getGhost(Direction.LEFT, Color.PINK, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(3, 5);
    image2 = spriteFacade.getGhost(Direction.LEFT, Color.PINK, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(4, 5);
    image2 = spriteFacade.getGhost(Direction.DOWN, Color.PINK, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(5, 5);
    image2 = spriteFacade.getGhost(Direction.DOWN, Color.PINK, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(6, 5);
    image2 = spriteFacade.getGhost(Direction.RIGHT, Color.PINK, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(7, 5);
    image2 = spriteFacade.getGhost(Direction.RIGHT, Color.PINK, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
  }

  @Test
  public void getTurquoiseGhostTest()
      throws InvalidColorException, InvalidDirectionException, InvalidStateException {
    BufferedImage image1;
    BufferedImage image2;

    image1 = sprite.getSprite(8, 5);
    image2 = spriteFacade.getGhost(Direction.UP, Color.TURQUOISE, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(9, 5);
    image2 = spriteFacade.getGhost(Direction.UP, Color.TURQUOISE, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(10, 5);
    image2 = spriteFacade.getGhost(Direction.LEFT, Color.TURQUOISE, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(11, 5);
    image2 = spriteFacade.getGhost(Direction.LEFT, Color.TURQUOISE, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(12, 5);
    image2 = spriteFacade.getGhost(Direction.DOWN, Color.TURQUOISE, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(13, 5);
    image2 = spriteFacade.getGhost(Direction.DOWN, Color.TURQUOISE, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(14, 5);
    image2 = spriteFacade.getGhost(Direction.RIGHT, Color.TURQUOISE, GhostState.STATE1);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(15, 5);
    image2 = spriteFacade.getGhost(Direction.RIGHT, Color.TURQUOISE, GhostState.STATE2);
    assertTrue(compareImages(image1, image2));
  }

  @Test
  public void getScoreTest() throws InvalidScoreException {
    BufferedImage image1;
    BufferedImage image2;

    image1 = sprite.getSprite(15, 2);
    image2 = spriteFacade.getScore(200);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(16, 2);
    image2 = spriteFacade.getScore(400);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(17, 2);
    image2 = spriteFacade.getScore(800);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(18, 2);
    image2 = spriteFacade.getScore(1600);
    assertTrue(compareImages(image1, image2));
  }

  public void getInvalidScoreThrow() {
    try {
      spriteFacade.getScore(200);
    } catch (Exception exception) {
      assertEquals("Invalid score", exception.getMessage());
    }
  }

  @Test
  public void getWhiteLetterTest() throws InvalidLetterException, InvalidColorException {
    BufferedImage image1;
    BufferedImage image2;
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    char[] chars = alphabet.toCharArray();

    for (int i = 0; i < 19; i++) {
      image1 = sprite.getSprite(i, 7);
      image2 = spriteFacade.getLetter(chars[i], Color.WHITE);
      assertTrue(compareImages(image1, image2));
    }
    for (int i = 19; i < chars.length; i++) {
      image1 = sprite.getSprite(i % 19, 8);
      image2 = spriteFacade.getLetter(chars[i], Color.WHITE);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getPinkLetterTest() throws InvalidLetterException, InvalidColorException {
    BufferedImage image1;
    BufferedImage image2;
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    char[] chars = alphabet.toCharArray();

    for (int i = 0; i < 19; i++) {
      image1 = sprite.getSprite(i, 9);
      image2 = spriteFacade.getLetter(chars[i], Color.PINK);
      assertTrue(compareImages(image1, image2));
    }
    for (int i = 19; i < chars.length; i++) {
      image1 = sprite.getSprite(i % 19, 10);
      image2 = spriteFacade.getLetter(chars[i], Color.PINK);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getOrangeLetterTest() throws InvalidLetterException, InvalidColorException {
    BufferedImage image1;
    BufferedImage image2;
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    char[] chars = alphabet.toCharArray();

    for (int i = 0; i < 19; i++) {
      image1 = sprite.getSprite(i, 11);
      image2 = spriteFacade.getLetter(chars[i], Color.ORANGE);
      assertTrue(compareImages(image1, image2));
    }
    for (int i = 19; i < chars.length; i++) {
      image1 = sprite.getSprite(i % 19, 12);
      image2 = spriteFacade.getLetter(chars[i], Color.ORANGE);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getRedLetterTest() throws InvalidLetterException, InvalidColorException {
    BufferedImage image1;
    BufferedImage image2;
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    char[] chars = alphabet.toCharArray();

    for (int i = 0; i < 19; i++) {
      image1 = sprite.getSprite(i, 13);
      image2 = spriteFacade.getLetter(chars[i], Color.RED);
      assertTrue(compareImages(image1, image2));
    }
    for (int i = 19; i < chars.length; i++) {
      image1 = sprite.getSprite(i % 19, 14);
      image2 = spriteFacade.getLetter(chars[i], Color.RED);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getTurquoiseLetterTest() throws InvalidLetterException, InvalidColorException {
    BufferedImage image1;
    BufferedImage image2;
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    char[] chars = alphabet.toCharArray();

    for (int i = 0; i < 19; i++) {
      image1 = sprite.getSprite(i, 15);
      image2 = spriteFacade.getLetter(chars[i], Color.TURQUOISE);
      assertTrue(compareImages(image1, image2));
    }
    for (int i = 19; i < chars.length; i++) {
      image1 = sprite.getSprite(i % 19, 16);
      image2 = spriteFacade.getLetter(chars[i], Color.TURQUOISE);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getYellowLetterTest() throws InvalidLetterException, InvalidColorException {
    BufferedImage image1;
    BufferedImage image2;
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    char[] chars = alphabet.toCharArray();

    for (int i = 0; i < 19; i++) {
      image1 = sprite.getSprite(i, 17);
      image2 = spriteFacade.getLetter(chars[i], Color.YELLOW);
      assertTrue(compareImages(image1, image2));
    }
    for (int i = 19; i < chars.length; i++) {
      image1 = sprite.getSprite(i % 19, 18);
      image2 = spriteFacade.getLetter(chars[i], Color.YELLOW);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getWhiteDigitTest() throws InvalidColorException, InvalidDigitException {
    BufferedImage image1;
    BufferedImage image2;

    for (int i = 0; i < 10; i++) {
      image1 = sprite.getSprite(7 + i, 8);
      image2 = spriteFacade.getDigit(i, Color.WHITE);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getPinkDigitTest() throws InvalidColorException, InvalidDigitException {
    BufferedImage image1;
    BufferedImage image2;

    for (int i = 0; i < 10; i++) {
      image1 = sprite.getSprite(7 + i, 10);
      image2 = spriteFacade.getDigit(i, Color.PINK);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getOrangeDigitTest() throws InvalidColorException, InvalidDigitException {
    BufferedImage image1;
    BufferedImage image2;

    for (int i = 0; i < 10; i++) {
      image1 = sprite.getSprite(7 + i, 12);
      image2 = spriteFacade.getDigit(i, Color.ORANGE);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getRedDigitTest() throws InvalidColorException, InvalidDigitException {
    BufferedImage image1;
    BufferedImage image2;

    for (int i = 0; i < 10; i++) {
      image1 = sprite.getSprite(7 + i, 14);
      image2 = spriteFacade.getDigit(i, Color.RED);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getTurquoiseDigitTest() throws InvalidColorException, InvalidDigitException {
    BufferedImage image1;
    BufferedImage image2;

    for (int i = 0; i < 10; i++) {
      image1 = sprite.getSprite(7 + i, 16);
      image2 = spriteFacade.getDigit(i, Color.TURQUOISE);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getYellowDigitTest() throws InvalidColorException, InvalidDigitException {
    BufferedImage image1;
    BufferedImage image2;

    for (int i = 0; i < 10; i++) {
      image1 = sprite.getSprite(7 + i, 18);
      image2 = spriteFacade.getDigit(i, Color.YELLOW);
      assertTrue(compareImages(image1, image2));
    }
  }

  @Test
  public void getInvalidNumberThrow() {
    try {
      spriteFacade.getDigit(18, Color.TURQUOISE);
    } catch (Exception exception) {
      assertEquals("Invalid digit: 18", exception.getMessage());
    }
  }

  @Test
  public void getPacGumTest() throws InvalidStateException {
    BufferedImage image1;
    BufferedImage image2;

    image1 = sprite.getSprite(0, 2);
    image2 = spriteFacade.getPacGum(PacGumState.STATE5);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(1, 2);
    image2 = spriteFacade.getPacGum(PacGumState.STATE4);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(2, 2);
    image2 = spriteFacade.getPacGum(PacGumState.STATE3);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(3, 2);
    image2 = spriteFacade.getPacGum(PacGumState.STATE2);
    assertTrue(compareImages(image1, image2));

    image1 = sprite.getSprite(4, 2);
    image2 = spriteFacade.getPacGum(PacGumState.STATE1);
    assertTrue(compareImages(image1, image2));
  }
}
