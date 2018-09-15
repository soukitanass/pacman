package view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import model.Direction;
import model.IGameModel;
import model.Level;
import model.PacMan;

public class PacManView {

  private IGameModel model;
  private ImageIcon rightPacman = new ImageIcon("src\\main\\res\\pacman_right.png");
  private ImageIcon leftPacman = new ImageIcon("src\\main\\res\\pacman_left.png");
  private ImageIcon upPacman = new ImageIcon("src\\main\\res\\pacman_up.png");
  private ImageIcon downPacman = new ImageIcon("src\\main\\res\\pacman_down.png");

  public PacManView(IGameModel model) {
    this.model = model;
  }

  public void paint(Graphics graphic, JFrame window) {
    PacMan pacman = model.getPacman();
    Level level = model.getCurrentLevel();

    if (pacman == null || level == null) {
      return;
    }

    if (pacman.getDirection() == Direction.RIGHT) {
      drawRightPacman(graphic, window, pacman, level);
    } else if (pacman.getDirection() == Direction.LEFT) {
      drawLeftPacman(graphic, window, pacman, level);
    } else if (pacman.getDirection() == Direction.UP) {
      drawUpPacman(graphic, window, pacman, level);
    } else if (pacman.getDirection() == Direction.DOWN) {
      drawDownPacman(graphic, window, pacman, level);
    }
  }
  
  private void drawRightPacman(Graphics graphic, JFrame window, PacMan pacman, Level level) {
    Image image = rightPacman.getImage();
    int xLevelPosition = pacman.getPosition().getX();
    int yLevelPosition = pacman.getPosition().getY();
    int levelWidth = level.getWidth();
    int levelHeight = level.getHeight();
    int windowWidth = window.getWidth() - image.getWidth(null);
    int windowHeight = window.getHeight() - image.getHeight(null);
    int x = (int) xLevelPosition * windowWidth / levelWidth;
    int y = (int) yLevelPosition * windowHeight / levelHeight;

    graphic.drawImage(image, x, y, null);
  }
  
  private void drawLeftPacman(Graphics graphic, JFrame window, PacMan pacman, Level level) {
    Image image = leftPacman.getImage();
    int xLevelPosition = pacman.getPosition().getX();
    int yLevelPosition = pacman.getPosition().getY();
    int levelWidth = level.getWidth();
    int levelHeight = level.getHeight();
    int windowWidth = window.getWidth() - image.getWidth(null);
    int windowHeight = window.getHeight() - image.getHeight(null);
    int x = (int) xLevelPosition * windowWidth / levelWidth;
    int y = (int) yLevelPosition * windowHeight / levelHeight;
    
    graphic.drawImage(image, x, y, null);
  }
  
  private void drawUpPacman(Graphics graphic, JFrame window, PacMan pacman, Level level) {
    Image image = upPacman.getImage();
    int xLevelPosition = pacman.getPosition().getX();
    int yLevelPosition = pacman.getPosition().getY();
    int levelWidth = level.getWidth();
    int levelHeight = level.getHeight();
    int windowWidth = window.getWidth() - image.getWidth(null);
    int windowHeight = window.getHeight() - image.getHeight(null);
    int x = (int) xLevelPosition * windowWidth / levelWidth;
    int y = (int) yLevelPosition * windowHeight / levelHeight;
    
    graphic.drawImage(image, x, y, null);
  }
  
  private void drawDownPacman(Graphics graphic, JFrame window, PacMan pacman, Level level) {
    Image image = downPacman.getImage();
    int xLevelPosition = pacman.getPosition().getX();
    int yLevelPosition = pacman.getPosition().getY();
    int levelWidth = level.getWidth();
    int levelHeight = level.getHeight();
    int windowWidth = window.getWidth() - image.getWidth(null);
    int windowHeight = window.getHeight() - image.getHeight(null);
    int x = (int) xLevelPosition * windowWidth / levelWidth;
    int y = (int) yLevelPosition * windowHeight / levelHeight;
    
    graphic.drawImage(image, x, y, null);
  }
  
}
