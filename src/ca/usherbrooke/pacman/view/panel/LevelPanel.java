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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.view.spirites.SpriteFacade;
import ca.usherbrooke.pacman.view.states.PacGumState;
import ca.usherbrooke.pacman.view.states.PacManState;
import ca.usherbrooke.pacman.view.utilities.Color;
import ca.usherbrooke.pacman.view.utilities.ImageUtilities;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public class LevelPanel extends AbstractPanel {

  private static final double LEVEL_NUMBER_OVER_SCORE_PANEL_TILE_SIZE_RATIO = 2.0;
  private static final String LEVEL_TEXT = "LEVEL";
  private static String SCORE_TEXT = "SCORE";
  private static String LIVES_TEXT = "LIVES";
  private static final int LEVEL_OFFSET = 1;

  private static final int EMPTY_TILE_CODE = 0;
  private static final int GHOST_ROOM_TILE_CODE = 38;
  private static final int PANEL_WIDTH_IN_SCORE_TILES = 13;
  private static final String PAINTING_ERROR = "Error while painting the level. ";
  private IGameModel model;
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

  private void drawScorePanel(Graphics graphics, int y) {
    final double scaleFactor = 1.0 / PANEL_WIDTH_IN_SCORE_TILES * pixelTileSize;
    Image scoreImage = getScorePanelImage(scaleFactor);
    Image livesImage = getLivesPanelImage(scaleFactor);
    final int xLeftPos = offsetX;
    final int xMiddlePos = xLeftPos + (int) (0.5 * this.getWidthTiles() * pixelTileSize);
    final int yPos = y + offsetY;
    graphics.drawImage(scoreImage, xLeftPos, yPos, null);
    graphics.drawImage(livesImage, xMiddlePos, yPos, null);
    // TODO: Move to the model
    if (model.getLives() == 0) {
      model.setGameOver();
      model.setLives(-1);
    }
  }

  private Image getLivesPanelImage(final double scaleFactor) {
    BufferedImage livesTextImage =
        ImageUtilities.getTextImage(LIVES_TEXT, Color.WHITE, scaleFactor);
    BufferedImage livesIconsImage = getLivesIconsImage(model.getLives(), scaleFactor);
    if (null == livesIconsImage) {
      return livesTextImage;
    }
    return ImageUtilities.joinImages(Arrays.asList(livesTextImage, livesIconsImage));
  }

  private Image getScorePanelImage(final double scaleFactor) {
    BufferedImage scoreTextImage =
        ImageUtilities.getTextImage(SCORE_TEXT, Color.WHITE, scaleFactor);
    Integer score = model.getScore();
    BufferedImage scoreValueImage =
        ImageUtilities.getTextImage(score.toString(), Color.YELLOW, scaleFactor);
    return ImageUtilities.joinImages(Arrays.asList(scoreTextImage, scoreValueImage));
  }

  private BufferedImage getLivesIconsImage(int lives, double scaleFactor) {
    List<BufferedImage> livesImages = new ArrayList<BufferedImage>();
    for (int i = 0; i < lives; i++) {
      try {
        BufferedImage image = spriteFacade.getPacman(Direction.LEFT, PacManState.STATE3);
        livesImages.add(image);
      } catch (Exception exception) {
        WarningDialog.display(PAINTING_ERROR, exception);
      }
    }
    if (livesImages.isEmpty()) {
      return null;
    }
    BufferedImage livesIconsImageNotScaled = ImageUtilities.joinImages(livesImages);
    final int newWidth = (int) (scaleFactor * livesIconsImageNotScaled.getWidth());
    final int newHeight = (int) (scaleFactor * livesIconsImageNotScaled.getHeight());
    return ImageUtilities.resize(livesIconsImageNotScaled, newWidth, newHeight);
  }

  private void drawLevelNumber(Graphics graphic) {
    final int height = (int) this.getBounds().getHeight();
    BufferedImage levelNumberImage = getLevelNumberImage();

    TextPanelPositioningStrategy positioningStrategy =
        new BottomCenteredInLevelPositionStrategy(height);
    final Position positionToDrawInBottomCenter =
        positioningStrategy.getPosition(levelNumberImage, model, pixelTileSize, offsetX, offsetY);

    graphic.drawImage(levelNumberImage, positionToDrawInBottomCenter.getX(),
        positionToDrawInBottomCenter.getY(), null);
  }

  private BufferedImage getLevelNumberImage() {
    final double scaleFactor = (LEVEL_NUMBER_OVER_SCORE_PANEL_TILE_SIZE_RATIO)
        / PANEL_WIDTH_IN_SCORE_TILES * pixelTileSize;
    final int levelNumber = model.getCurrentLevelIndex() + LEVEL_OFFSET;
    BufferedImage levelNumberTextImage =
        ImageUtilities.getTextImage(LEVEL_TEXT, Color.WHITE, scaleFactor);
    BufferedImage levelNumberValueImage =
        ImageUtilities.getTextImage(Integer.toString(levelNumber), Color.YELLOW, scaleFactor);
    BufferedImage levelNumberImage =
        ImageUtilities.joinImages(Arrays.asList(levelNumberTextImage, levelNumberValueImage));
    return levelNumberImage;
  }

  public int getWidthTiles() {
    return model.getCurrentLevel().getWidth();
  }
}
