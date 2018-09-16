package view;

import static org.junit.Assert.assertEquals;
import java.awt.image.BufferedImage;
import org.junit.Test;


public class SpriteTest {
  private final String LEVEL_SPRITES = "level_sprite";
  private final int LEVEL_TILE_SIZE = 8;

  @Test
  public void getSprite() {
    // Assign
    final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);

    // Act
    BufferedImage spriteImage = sprite.getSprite(0, 0);

    // Assert
    assertEquals(LEVEL_TILE_SIZE, spriteImage.getWidth());
    assertEquals(LEVEL_TILE_SIZE, spriteImage.getHeight());
  }
}
