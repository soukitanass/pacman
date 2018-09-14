package view;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import model.Direction;
import model.IGameModel;
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

  public void paint(Graphics graphic) {
    PacMan pacman = model.getPacman();
    
    if (pacman == null) {
      return;
    }
    
    int x = pacman.getPosition().getX();
    int y = pacman.getPosition().getY();
    
    if (pacman.getDirection() == Direction.RIGHT) {
      graphic.drawImage(rightPacman.getImage(), x, y, null);
    } else if (pacman.getDirection() == Direction.LEFT) {
      graphic.drawImage(leftPacman.getImage(), x, y, null);
    } else if (pacman.getDirection() == Direction.UP) {
      graphic.drawImage(upPacman.getImage(), x, y, null);
    } else if (pacman.getDirection() == Direction.DOWN) {
      graphic.drawImage(downPacman.getImage(), x, y, null);
    }
  }
}
