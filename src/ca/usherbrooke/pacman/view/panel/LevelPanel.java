/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.view.spirites.SpriteFacade;
import ca.usherbrooke.pacman.view.states.PacGumState;
import ca.usherbrooke.pacman.view.states.PacManState;
import ca.usherbrooke.pacman.view.utilities.Color;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public class LevelPanel extends AbstractPanel {

  private static final String LEVEL_TEXT = "Level ";
  private static final Color LEVEL_COLOR = Color.WHITE;
  private static final int LEVEL_SCALE_FACTOR = 2;
  private static final int LEVEL_OFFSET = 1;
  private static final int Y_DELTA_TILES = 2;

  private static final int EMPTY_TILE_CODE = 0;
  private static final int GHOST_ROOM_TILE_CODE = 38;
  public static final int PANEL_WIDTH_IN_SCORE_TILES = 25;
  private static final String PAINTING_ERROR = "Error while painting the level. ";
  private IGameModel model;
  private String scoreText = "SCORE";
  private String liveText = "LIVES";
  private SpriteFacade spriteFacade = new SpriteFacade();

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
          } else if (code == GHOST_ROOM_TILE_CODE) {
            image = spriteFacade.getWall(EMPTY_TILE_CODE);
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

      drawScorePanel(graphic, map.size() * pixelTileSize);
      drawLevelNumber(graphic);
    }

  }

  private void drawLevel(Image source, Graphics graphics, int x, int y, int width, int height) {
    final int tileSize = spriteFacade.getTileSize();
    graphics.drawImage(source, x, y, x + width, y + height, 0, 0, tileSize, tileSize, null);
  }

  private void drawScorePanel(Graphics graphic, int y) {
    int xPos = 0;
    drawText(graphic, y, xPos, this.scoreText);

    xPos = scoreText.length() * getScoreTileSizePixels() + offsetX;
    drawScore(graphic, y, xPos);

    xPos = xPos + 4 * getScoreTileSizePixels();
    drawTextLive(graphic, y, xPos, this.liveText);


    xPos = xPos + liveText.length() * getScoreTileSizePixels();
    if (model.getLives() == 0) {
      model.setGameOver();
      model.setLives(-1);
    }
    drawLives(graphic, y, xPos, model.getLives());

  }

  private void drawLevelNumber(Graphics graphic) {
    final int bottomMargin = Y_DELTA_TILES * pixelTileSize;
    final int height = (int) this.getBounds().getHeight();
    final int levelNumber = model.getCurrentLevelIndex() + LEVEL_OFFSET;
    final String levelLabel = LEVEL_TEXT + levelNumber;
    BufferedImage image = getLabelImage(levelLabel, LEVEL_COLOR, LEVEL_SCALE_FACTOR);

    TextPanelPositioningStrategy positioningStrategy =
        new BottomCenteredInLevelPositionStrategy(height, bottomMargin);
    final Position position =
        positioningStrategy.getPosition(image, model, pixelTileSize, offsetX, offsetY);

    graphic.drawImage(image, position.getX(), position.getY(), null);
  }

  private int getScoreTileSizePixels() {
    final double totalWidthPixels = (double) getWidthTiles() * pixelTileSize;
    return (int) (totalWidthPixels / PANEL_WIDTH_IN_SCORE_TILES);
  }

  public int getWidthTiles() {
    return model.getCurrentLevel().getWidth();
  }

  public void drawScore(Graphics graphic, int y, int x) {
    Integer score = model.getScore();
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
        image = spriteFacade.getLetter(text.charAt(i), Color.WHITE);
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
        image = spriteFacade.getLetter(text.charAt(i), Color.WHITE);
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
