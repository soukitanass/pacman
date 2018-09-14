package view;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class PacManView {

  private ImageIcon rightPacman = new ImageIcon("src\\main\\res\\pacman.png");

  public PacManView() {
    
  }

  public void paint(Graphics graphic) {
    graphic.drawImage(rightPacman.getImage(), 100, 100, null);
  }
}
