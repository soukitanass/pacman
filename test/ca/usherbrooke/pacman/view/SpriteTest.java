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
import java.awt.image.BufferedImage;
import org.junit.Test;

public class SpriteTest {
  @Test
  public void getSprite() {
    // Assign
    final String LEVEL_SPRITES = "sprites";
    final int LEVEL_TILE_SIZE = 8;
    final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);

    // Act
    BufferedImage spriteImage = sprite.getSprite(0, 0);

    // Assert
    assertEquals(LEVEL_TILE_SIZE, spriteImage.getWidth());
    assertEquals(LEVEL_TILE_SIZE, spriteImage.getHeight());
  }
}
