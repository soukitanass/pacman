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

  public LevelView(IGameModel model) {
    this.model = model;
  }

  public void paint(Graphics graphic, JFrame window) {
    int pixelRatio;
    final Level level = this.model.getCurrentLevel();
    final float widthRatio = window.getWidth() / level.getWidth();
    final float heightRatio = window.getHeight() / level.getHeight();

    if (widthRatio < heightRatio) {
      pixelRatio = (int) Math.floor(widthRatio);
    } else {
      pixelRatio = (int) Math.floor(heightRatio);
    }

    final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);
    List<List<Integer>> map = level.getMap();
    for (int i = 0; i < map.size(); i++) {
      List<Integer> row = map.get(i);
      for (int j = 0; j < row.size(); j++) {
        BufferedImage image = sprite.getSprite(row.get(j), 0);
        int iPos = i * pixelRatio;
        int yPos = j * pixelRatio;

        drawLevel(image, graphic, iPos, yPos, pixelRatio, pixelRatio);
      }
    }
  }

  private void drawLevel(Image source, Graphics graphics, int x, int y, int width, int height) {
    graphics.drawImage(source, x, y, x + width, y + height, 0, 0, LEVEL_TILE_SIZE, LEVEL_TILE_SIZE,
        null);
  }
}
