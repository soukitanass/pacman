package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import model.Direction;
import model.IGameModel;
import model.Level;
import model.PacMan;

public class PacManView {

  private IGameModel model;

  public static final int PACMAN_RADIUS = 50;

  public PacManView(IGameModel model) {
    this.model = model;
  }

  public void paint(Graphics graphic, JFrame window) {
    PacMan pacman = model.getPacman();
    Level level = model.getCurrentLevel();

    if (pacman == null || level == null) {
      return;
    }

    int xLevelPosition = pacman.getPosition().getX();
    int yLevelPosition = pacman.getPosition().getY();
    int levelWidth = level.getWidth();
    int levelHeight = level.getHeight();
    int windowWidth = window.getWidth() - PACMAN_RADIUS;
    int windowHeight = window.getHeight() - PACMAN_RADIUS;
    int x = (int) xLevelPosition * windowWidth / levelWidth;
    int y = (int) yLevelPosition * windowHeight / levelHeight;

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
    int angle = (int) (20 * (Math.sin((x + y) * 2 * Math.PI / PACMAN_RADIUS) + 1));
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, PACMAN_RADIUS, PACMAN_RADIUS, angle / 2, 360 - angle);
  }

  private void drawPacmanLeft(Graphics graphic, int x, int y) {
    int direction = 180;
    int angle = (int) (20 * (Math.sin((x + y) * 2 * Math.PI / PACMAN_RADIUS) + 1));
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, PACMAN_RADIUS, PACMAN_RADIUS, angle / 2 + direction, 360 - angle);
  }

  private void drawPacmanUp(Graphics graphic, int x, int y) {
    int direction = 90;
    int angle = (int) (20 * (Math.sin((x + y) * 2 * Math.PI / PACMAN_RADIUS) + 1));
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, PACMAN_RADIUS, PACMAN_RADIUS, angle / 2 + direction, 360 - angle);
  }

  private void drawPacmanDown(Graphics graphic, int x, int y) {
    int direction = 270;
    int angle = (int) (20 * (Math.sin((x + y) * 2 * Math.PI / PACMAN_RADIUS) + 1));
    graphic.setColor(Color.YELLOW);
    graphic.fillArc(x, y, PACMAN_RADIUS, PACMAN_RADIUS, angle / 2 + direction, 360 - angle);
  }

}
