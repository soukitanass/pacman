package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;
import model.Color;
import model.IGameModel;
import model.Level;
import model.PacGumState;
import model.exceptions.InvalidColorException;
import model.exceptions.InvalidDigitException;
import model.exceptions.InvalidLetterException;
import view.utilities.WarningDialog;

@SuppressWarnings("serial")
public class LevelPanel extends JPanel {
  private IGameModel model;
  private int pixelTileSize;
  private int pixelTileSizeScore = 35;
  private String scoreText = "SCORE";
  private SpriteFacade spriteFacade = new SpriteFacade();

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
          WarningDialog.display("Error while painting the level. ", exception);
        }
        int iPos = j * pixelTileSize;
        int yPos = i * pixelTileSize;
        drawLevel(image, graphic, iPos, yPos, pixelTileSize, pixelTileSize);
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
    // draw text and score
    for (int i = 0; i < scoreText.length(); i++) {
      BufferedImage image = null;
      try {
        image = spriteFacade.getLetter((char) scoreText.charAt(i), Color.WHITE);
      } catch (Exception exception) {
        WarningDialog.display("Error while painting the panel. ", exception);
      }
      xPos = i * pixelTileSizeScore;
      drawLevel(image, graphic, xPos, y, pixelTileSizeScore, pixelTileSizeScore);
    }

    xPos = scoreText.length() * pixelTileSizeScore;

    // drawing the score
    Integer score = level.getScore();
    String scoreString = String.valueOf(score);
    for (int i = 0; i < scoreString.length(); i++) {
      int pos;
      int digit = Character.digit(scoreString.charAt(i), 10);
      BufferedImage image = null;
      try {
        image = spriteFacade.getDigit(digit, Color.ORANGE);
      } catch (Exception exception) {
        WarningDialog.display("Error while painting the panel. ", exception);
      }
      pos = i * pixelTileSizeScore + xPos;
      drawLevel(image, graphic, pos, y, pixelTileSizeScore, pixelTileSizeScore);
    }
  }
}
