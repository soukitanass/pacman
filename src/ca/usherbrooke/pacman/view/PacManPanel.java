package ca.usherbrooke.pacman.view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.PacMan;

@SuppressWarnings({"serial", "squid:S1948"})
public class PacManPanel extends JPanel {

  private IGameModel model;
  private int pixelTileSize;
  private int offsetX = 0;
  private int offsetY = 0;

  public PacManPanel(IGameModel model) {
    this.model = model;
  }

  @Override
  public void paint(Graphics graphic) {

    super.paint(graphic);
    PacMan pacman = model.getPacman();

    final int x = pacman.getPosition().getX() * pixelTileSize + offsetX;
    final int y = pacman.getPosition().getY() * pixelTileSize + offsetY;

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

  public void setPixelTileSize(int pixelTileSize) {
    this.pixelTileSize = pixelTileSize;
  }

  public void setOffsetX(int offsetX) {
    this.offsetX = offsetX;
  }

  public void setOffsetY(int offsetY) {
    this.offsetY = offsetY;
  }
}
