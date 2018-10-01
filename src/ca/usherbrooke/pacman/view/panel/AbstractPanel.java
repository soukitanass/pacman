package ca.usherbrooke.pacman.view.panel;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractPanel extends JPanel {
  protected int pixelTileSize;
  protected int offsetX;
  protected int offsetY;

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
