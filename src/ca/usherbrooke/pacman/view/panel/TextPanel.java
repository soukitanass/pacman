package ca.usherbrooke.pacman.view.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.Color;

@SuppressWarnings({"serial", "squid:S1948"})
public class TextPanel extends AbstractPanel {

  private static final Color COLOR = Color.YELLOW;
  private static final double TEXT_SCALE_FACTOR = 2.2;
  private static final int Y_TILES_OFFSET = 2;

  private IGameModel model;
  private String text;

  public TextPanel(IGameModel model, String text) {
    this.model = model;
    this.text = text;
    setFocusable(true);
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    final BufferedImage image = getJLabelImage(text, COLOR, TEXT_SCALE_FACTOR);
    final int height = model.getCurrentLevel().getHeight() * pixelTileSize;
    final int width = model.getCurrentLevel().getWidth() * pixelTileSize;
    final int y = (height / 2) - (image.getHeight() / 2) + offsetY;
    final int x = (width / 2) - (image.getWidth() / 2) + offsetX;
    final int yOffset = Y_TILES_OFFSET * pixelTileSize;

    graphic.drawImage(image, x, y + yOffset, null);
  }
}
