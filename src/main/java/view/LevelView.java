package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JFrame;
import model.IGameModel;
import model.Level;

public class LevelView {
  private final String LEVEL_SPRITES = "level_sprite";
  private final int LEVEL_TILE_SIZE = 8;
  private IGameModel model;
  private int pixelTileSize;

  public LevelView(IGameModel model, int pixelTileSize) {
    this.model = model;
    this.pixelTileSize = pixelTileSize;
  }

  public void paint(Graphics graphic, JFrame window) {
    final Level level = this.model.getCurrentLevel();
    final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);
    List<List<Integer>> map = level.getMap();

    for (int i = 0; i < map.size(); i++) {
      List<Integer> row = map.get(i);
      for (int j = 0; j < row.size(); j++) {
        BufferedImage image = sprite.getSprite(row.get(j), 0);
        int iPos = j * pixelTileSize;
        int yPos = i * pixelTileSize;
        drawLevel(image, graphic, iPos, yPos, pixelTileSize, pixelTileSize);
      }
    }
  }

  private void drawLevel(Image source, Graphics graphics, int x, int y, int width, int height) {
    graphics.drawImage(source, x, y, x + width, y + height, 0, 0, LEVEL_TILE_SIZE, LEVEL_TILE_SIZE,
        null);
  }
}
