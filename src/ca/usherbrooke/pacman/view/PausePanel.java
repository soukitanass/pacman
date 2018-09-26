package ca.usherbrooke.pacman.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public class PausePanel extends JPanel {

  public static final int PANEL_WIDTH_IN_SCORE_TILES = 25;
  public static final double RATIO_LEVEL_HEIGHT_TO_TOTAL_HEIGHT = 0.9;
  private IGameModel model;
  private int pixelTileSize = 25;
  private String displayText = "PAUSE";
  private Color color;
  private SpriteFacade spriteFacade = new SpriteFacade();
  private static final int PANEL_X = 220;
  private static final int PANEL_Y = 375;

  public void setPixelTileSize(int pixelTileSize) {
    this.pixelTileSize = pixelTileSize;
  }

  public PausePanel(IGameModel model, String text, Color c) {
    this.model = model;
    this.displayText = text;
    this.color = c;
    setFocusable(true);
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    for (int i = 0; i < displayText.length(); i++) {
      BufferedImage image = null;
      try {
        image = spriteFacade.getLetter((char) displayText.charAt(i), color);
      } catch (Exception exception) {
        WarningDialog.display("Error while painting the panel. ", exception);
      }
      int x = i * getScoreTileSizePixels();
      drawSpirite(image, graphic, PANEL_X + x, PANEL_Y, getScoreTileSizePixels(),
          getScoreTileSizePixels());
    }
  }

  private void drawSpirite(Image source, Graphics graphics, int x, int y, int width, int height) {
    final int tileSize = spriteFacade.getTileSize();
    graphics.drawImage(source, x, y, x + width, y + height, 0, 0, tileSize, tileSize, null);
  }

  private int getScoreTileSizePixels() {
    final double totalWidthPixels = (double) getWidthTiles() * pixelTileSize;
    return (int) (totalWidthPixels / PANEL_WIDTH_IN_SCORE_TILES);
  }

  public int getWidthTiles() {
    return model.getCurrentLevel().getWidth();
  }

  public int getHeightTiles() {
    return (int) (model.getCurrentLevel().getHeight() / RATIO_LEVEL_HEIGHT_TO_TOTAL_HEIGHT);
  }

}
