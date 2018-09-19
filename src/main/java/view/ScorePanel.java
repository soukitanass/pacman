package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import model.Color;
import model.IGameModel;
import model.Level;
import model.exceptions.InvalidColorException;
import model.exceptions.InvalidDigitException;
import model.exceptions.InvalidLetterException;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
  private IGameModel model;
  private int pixelTileSize;
  private String scoreText = "SCORE";
  private SpriteFacade spriteFacade = new SpriteFacade();

  public ScorePanel(IGameModel model) {
    this.model = model;

  }

  public void setPixelTileSize(int pixelTileSize) {
    this.pixelTileSize = pixelTileSize;
  }

  public int getPixelTileSize() {
    return this.pixelTileSize;
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    setBackground(java.awt.Color.BLACK);
    final Level level = this.model.getCurrentLevel();
    int xPos = 0;
    // draw text and score
    for (int i = 0; i < scoreText.length(); i++) {
      BufferedImage image = null;
      try {
        image = spriteFacade.getLetter((char) scoreText.charAt(i), Color.WHITE);
      } catch (InvalidLetterException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvalidColorException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      xPos = i * pixelTileSize;
      drawText(image, graphic, xPos, 0, pixelTileSize, pixelTileSize);
    }

    xPos = scoreText.length() * pixelTileSize;

    // drawing the score
    Integer score = level.getScore();
    String scoreString = String.valueOf(score);
    for (int i = 0; i < scoreString.length(); i++) {
      int pos;
      int digit = Character.digit(scoreString.charAt(i), 10);
      BufferedImage image = null;
      try {
        image = spriteFacade.getDigit(digit, Color.ORANGE);
      } catch (InvalidColorException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvalidDigitException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      pos = i * pixelTileSize + xPos;
      drawText(image, graphic, pos, 0, pixelTileSize, pixelTileSize);
    }

  }

  private void drawText(Image source, Graphics graphics, int x, int y, int width, int height) {
    final int tileSize = spriteFacade.getTileSize();
    graphics.drawImage(source, x, y, x + width, y + height, 0, 0, tileSize, tileSize, null);
  }
}
