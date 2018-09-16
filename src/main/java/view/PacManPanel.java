package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.Direction;
import model.IGameModel;
import model.Level;
import model.PacMan;

public class PacManPanel extends JPanel {

  private IGameModel model;
  private int pixelTileSize;

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

    final int x = pacman.getPosition().getX() * pixelTileSize;
    final int y = pacman.getPosition().getY() * pixelTileSize;

    if (pacman.getDirection() == Direction.RIGHT) {
      drawPacmanRight(graphic, x, y);
    } else if (pacman.getDirection() == Direction.LEFT) {
      drawPacmanLeft(graphic, x, y);
    } else if (pacman.getDirection() == Direction.UP) {
      drawPacmanUp(graphic, x, y);
    } else if (pacman.getDirection() == Direction.DOWN) {
      drawPacmanDown(graphic, x, y);
    }
  }

  private void drawPacmanRight(Graphics graphic, int x, int y) {
    int angle = (int) (20 * (Math.sin((x + y) * 2 * Math.PI / pixelTileSize) + 1));
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, pixelTileSize, pixelTileSize, angle / 2, 360 - angle);
  }

  private void drawPacmanLeft(Graphics graphic, int x, int y) {
    int direction = 180;
    int angle = (int) (20 * (Math.sin((x + y) * 2 * Math.PI / pixelTileSize) + 1));
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, pixelTileSize, pixelTileSize, angle / 2 + direction, 360 - angle);
  }

  private void drawPacmanUp(Graphics graphic, int x, int y) {
    int direction = 90;
    int angle = (int) (20 * (Math.sin((x + y) * 2 * Math.PI / pixelTileSize) + 1));
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, pixelTileSize, pixelTileSize, angle / 2 + direction, 360 - angle);
  }

  private void drawPacmanDown(Graphics graphic, int x, int y) {
    int direction = 270;
    int angle = (int) (20 * (Math.sin((x + y) * 2 * Math.PI / pixelTileSize) + 1));
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, pixelTileSize, pixelTileSize, angle / 2 + direction, 360 - angle);
  }

}
