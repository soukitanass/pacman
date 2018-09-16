package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.Direction;
import model.IGameModel;
import model.Level;
import model.PacMan;

public class PacManPanel extends JPanel {

  private int NUMBER_OF_SPRITES = 6;
  private IGameModel model;
  private int pixelTileSize;
  private int pacmanSpriteIndex = 0;

  public PacManPanel(IGameModel model, int pixelTileSize) {
    this.model = model;
    this.pixelTileSize = pixelTileSize;
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    PacMan pacman = model.getPacman();
    Level level = model.getCurrentLevel();

    if (pacman == null || level == null) {
      return;
    }

    if (pacmanSpriteIndex >= NUMBER_OF_SPRITES) {
      pacmanSpriteIndex = 0;
    }

    if (pacman.getDirection() == Direction.RIGHT) {
      drawPacmanRight(graphic, pacman);
    } else if (pacman.getDirection() == Direction.LEFT) {
      drawPacmanLeft(graphic, pacman);
    } else if (pacman.getDirection() == Direction.UP) {
      drawPacmanUp(graphic, pacman);
    } else if (pacman.getDirection() == Direction.DOWN) {
      drawPacmanDown(graphic, pacman);
    }
    pacmanSpriteIndex++;
  }

  private void drawPacmanRight(Graphics graphic, PacMan pacman) {
    final int x = (int) (pacman.getPosition().getX() * pixelTileSize
        + (pacmanSpriteIndex / (float) (NUMBER_OF_SPRITES)) * pixelTileSize);
    final int y = pacman.getPosition().getY() * pixelTileSize;
    final int angle = getMouthAngle();
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, pixelTileSize, pixelTileSize, angle / 2, 360 - angle);
  }

  private void drawPacmanLeft(Graphics graphic, PacMan pacman) {
    final int x = (int) (pacman.getPosition().getX() * pixelTileSize
        - (pacmanSpriteIndex / (float) (NUMBER_OF_SPRITES)) * pixelTileSize);
    final int y = pacman.getPosition().getY() * pixelTileSize;
    final int direction = 180;
    final int angle = getMouthAngle();
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, pixelTileSize, pixelTileSize, angle / 2 + direction, 360 - angle);
  }

  private void drawPacmanUp(Graphics graphic, PacMan pacman) {
    final int x = pacman.getPosition().getX() * pixelTileSize;
    final int y = (int) (pacman.getPosition().getY() * pixelTileSize
        - (pacmanSpriteIndex / (float) (NUMBER_OF_SPRITES)) * pixelTileSize);
    final int direction = 90;
    final int angle = getMouthAngle();
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, pixelTileSize, pixelTileSize, angle / 2 + direction, 360 - angle);
  }

  private void drawPacmanDown(Graphics graphic, PacMan pacman) {
    final int x = pacman.getPosition().getX() * pixelTileSize;
    final int y = (int) (pacman.getPosition().getY() * pixelTileSize
        + (pacmanSpriteIndex / (float) (NUMBER_OF_SPRITES)) * pixelTileSize);
    final int direction = 270;
    final int angle = getMouthAngle();
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, pixelTileSize, pixelTileSize, angle / 2 + direction, 360 - angle);
  }

  private int getMouthAngle() {
    switch (pacmanSpriteIndex) {
      case 0:
        return 0;
      case 1:
        return 20;
      case 2:
        return 40;
      case 3:
        return 60;
      case 4:
        return 40;
      case 5:
        return 20;
      default:
        return 0;
    }
  }
}
