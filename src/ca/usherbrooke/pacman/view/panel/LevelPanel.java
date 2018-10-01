package ca.usherbrooke.pacman.view.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.Level;
import ca.usherbrooke.pacman.view.Color;
import ca.usherbrooke.pacman.view.PacGumState;
import ca.usherbrooke.pacman.view.PacManState;
import ca.usherbrooke.pacman.view.SpriteFacade;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public class LevelPanel extends JPanel {

  public static final int PANEL_WIDTH_IN_SCORE_TILES = 25;
  public static final double RATIO_LEVEL_HEIGHT_TO_TOTAL_HEIGHT = 0.9;
  private static final String PAINTING_ERROR = "Error while painting the level. ";
  private IGameModel model;
  private int pixelTileSize;
  private String scoreText = "SCORE";
  private String liveText = "LIVES";
  private SpriteFacade spriteFacade = new SpriteFacade();
  private int offsetX = 0;
  private int offsetY = 0;

  public void setPixelTileSize(int pixelTileSize) {
    this.pixelTileSize = pixelTileSize;
  }

  public LevelPanel(IGameModel model) {
    this.model = model;
    setFocusable(true);
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    final Level level = this.model.getCurrentLevel();
    List<List<Integer>> map = level.getMap();

    for (int i = 0; i < map.size(); i++) {
      List<Integer> row = map.get(i);
      for (int j = 0; j < row.size(); j++) {

        BufferedImage image = null;
        try {
          int code = row.get(j);
          if (code == 39) {
            image = spriteFacade.getPacGum(PacGumState.STATE1);
          } else if (code == 40) {
            image = spriteFacade.getPacGum(PacGumState.STATE5);
          } else {
            image = spriteFacade.getWall(code);
          }
        } catch (Exception exception) {
          WarningDialog.display(PAINTING_ERROR, exception);
        }
        int iPos = j * pixelTileSize;
        int yPos = i * pixelTileSize;
        drawLevel(image, graphic, iPos + offsetX, yPos + offsetY, pixelTileSize, pixelTileSize);
      }
      drawScorePanel(graphic, map.size() * pixelTileSize, level);
    }

  }

  private void drawLevel(Image source, Graphics graphics, int x, int y, int width, int height) {
    final int tileSize = spriteFacade.getTileSize();
    graphics.drawImage(source, x, y, x + width, y + height, 0, 0, tileSize, tileSize, null);
  }

  private void drawScorePanel(Graphics graphic, int y, Level level) {
    int xPos = 0;
    drawText(graphic, y, xPos, this.scoreText);

    xPos = scoreText.length() * getScoreTileSizePixels() + offsetX;
    drawScore(graphic, y, level, xPos);

    xPos = xPos + 4 * getScoreTileSizePixels();
    drawTextLive(graphic, y, xPos, this.liveText);


    xPos = xPos + liveText.length() * getScoreTileSizePixels();
    if (level.getLives() == 0) {
      model.setGameOver();
      level.setLives(-1);
    }
    drawLives(graphic, y, xPos, level.getLives());

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

  public void setOffsetX(int offsetX) {
    this.offsetX = offsetX;
  }

  public void setOffsetY(int offsetY) {
    this.offsetY = offsetY;
  }

  public void drawScore(Graphics graphic, int y, Level level, int x) {
    Integer score = level.getScore();
    String scoreString = String.valueOf(score);
    for (int i = 0; i < scoreString.length(); i++) {
      int pos;
      int digit = Character.digit(scoreString.charAt(i), 10);
      BufferedImage image = null;
      try {
        image = spriteFacade.getDigit(digit, Color.YELLOW);
      } catch (Exception exception) {
        WarningDialog.display(PAINTING_ERROR, exception);
      }
      pos = i * getScoreTileSizePixels() + x;
      drawLevel(image, graphic, pos, y + offsetY, getScoreTileSizePixels(),
          getScoreTileSizePixels());
    }

  }

  public void drawText(Graphics graphic, int y, int x, String text) {
    int xPos = 0;
    for (int i = 0; i < text.length(); i++) {
      BufferedImage image = null;
      try {
        image = spriteFacade.getLetter((char) text.charAt(i), Color.WHITE);
      } catch (Exception exception) {
        WarningDialog.display(PAINTING_ERROR, exception);
      }
      xPos = i * getScoreTileSizePixels() + offsetX + x;
      drawLevel(image, graphic, xPos, y + offsetY, getScoreTileSizePixels(),
          getScoreTileSizePixels());
    }
  }

  public void drawTextLive(Graphics graphic, int y, int x, String text) {
    int xPos = 0;
    for (int i = 0; i < text.length(); i++) {
      BufferedImage image = null;
      try {
        image = spriteFacade.getLetter((char) text.charAt(i), Color.WHITE);
      } catch (Exception exception) {
        WarningDialog.display(PAINTING_ERROR, exception);
      }
      xPos = i * getScoreTileSizePixels() + x;
      drawLevel(image, graphic, xPos, y + offsetY, getScoreTileSizePixels(),
          getScoreTileSizePixels());
    }
  }

  public void drawLives(Graphics graphic, int y, int x, int lives) {

    for (int i = 0; i < lives; i++) {
      int pos;
      BufferedImage image = null;
      try {
        image = spriteFacade.getPacman(Direction.RIGHT, PacManState.STATE2);
      } catch (Exception exception) {
        WarningDialog.display(PAINTING_ERROR, exception);
      }
      pos = i * getScoreTileSizePixels() + x;
      drawLevel(image, graphic, pos, y + offsetY, getScoreTileSizePixels(),
          getScoreTileSizePixels());
    }
  }
}
