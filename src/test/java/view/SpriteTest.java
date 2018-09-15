package view;

import static org.junit.Assert.assertEquals;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import javax.imageio.IIOException;
import org.junit.Test;


public class SpriteTest {
  private final String LEVEL_SPRITES = "level_sprite";
  private final String INVALID_LEVEL_SPRITE = "invalid_level_sprite";
  private final int LEVEL_TILE_SIZE = 8;
  private final int INVALID_LEVEL_TILE_SIZE = 12;

  @Test(expected = IIOException.class)
  public void invalidSpriteSheetTest() {
    new Sprite(INVALID_LEVEL_SPRITE, LEVEL_TILE_SIZE);
  }

  @Test
  public void getValidSprite() {
    // Assign
    final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);

    // Act
    BufferedImage spriteImage = sprite.getSprite(0, 0);

    // Assert
    assertEquals(LEVEL_TILE_SIZE, spriteImage.getWidth());
    assertEquals(LEVEL_TILE_SIZE, (int) spriteImage.getHeight());
  }

  @Test(expected = RasterFormatException.class)
  public void getInvalidTileSizeSprite() {
    final Sprite sprite = new Sprite(LEVEL_SPRITES, INVALID_LEVEL_TILE_SIZE);
    sprite.getSprite(0, 0);

  }

  @Test(expected = RasterFormatException.class)
  public void getInvalidXIndexSprite() {
    final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);
    sprite.getSprite(999, 0);
  }

  @Test(expected = RasterFormatException.class)
  public void getInvalidYIndexSprite() {
    final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);
    sprite.getSprite(0, 999);
  }

}
