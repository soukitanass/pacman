package ca.usherbrooke.pacman.view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.Ghost;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.exceptions.InvalidColorException;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.exceptions.InvalidStateException;

@SuppressWarnings("serial")
public class GhostsPanel extends JPanel {

  private IGameModel model;
  private SpriteFacade spriteFacade = new SpriteFacade();
  private int pixelTileSize;
  private int offsetX = 0;
  private int offsetY = 0;

  public GhostsPanel(IGameModel model) {
    this.model = model;
  }

  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);
    for (Ghost ghost : model.getCurrentLevel().getGhost()) {
      try {
        drawGhost(graphics, ghost);
      } catch (InvalidColorException | InvalidDirectionException | InvalidStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private void drawGhost(Graphics graphics, Ghost ghost)
      throws InvalidColorException, InvalidDirectionException, InvalidStateException {
    Image ghostImage = spriteFacade.getGhost(Direction.UP, Color.TURQUOISE, GhostState.STATE1);
    final int x = ghost.getPosition().getX() * pixelTileSize + offsetX;
    final int y = ghost.getPosition().getY() * pixelTileSize + offsetY;
    final int width = pixelTileSize;
    final int height = pixelTileSize;
    final int tileSize = spriteFacade.getTileSize();
    graphics.drawImage(ghostImage, x, y, x + width, y + height, 0, 0, tileSize, tileSize, null);
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
