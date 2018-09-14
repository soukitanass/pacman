package view;

import model.IGameModel;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameView implements IGameView {
  private final String LEVEL_SPRITES = "level_sprite";
  private final int LEVEL_TILE_SIZE = 8;
  private final IGameModel model;
  private GameCanvas canvas;
  private ArrayList<KeyListener> keyListeners = new ArrayList<>();

  public GameView(IGameModel model) {
    this.model = model;
  }

  /*
   * public void displayLevel() { final Level level = this.model.getCurrentLevel(); final int width
   * = level.getWidth(); final int height = level.getHeight(); final Sprite sprite = new
   * Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE); List<List<Integer>> map = level.getMap();
   * 
   * for (List<Integer> row : map) { for (Integer spriteId : row) { sprite.getSprite(spriteId, 1); }
   * }
   * 
   * }
   * 
   * private void drawSprite(Image source, Graphics2D g2d, int x, int y, int columns, int frame, int
   * width, int height) { int frameX = (frame % columns) * width; int frameY = (frame / columns) *
   * height; // g2d.drawImage(source, x, y, x + width, y + height, frameX, frameY, frameX + //
   * width, frameY + height, this); }
   */
  public void update() {
    if (this.canvas != null) {
      this.canvas.repaint();
    }
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    keyListeners.add(keyListener);
  }

  @Override
  public void display() {
    this.canvas = new GameCanvas(model);
    this.canvas.repaint();
    for (KeyListener keyListener : keyListeners) {
      canvas.addKeyListener(keyListener);
    }
  }

  @Override
  public void close() {
    canvas.dispose();
  }
}
