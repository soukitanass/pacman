package ca.usherbrooke.pacman.view;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
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
  private GhostSpriteToggler ghostSpritePeriodicToggler;

  private static final Map<Integer, Color> GHOST_ID_TO_COLOR = new HashMap<Integer, Color>() {
    {
      put(1, Color.RED);
      put(2, Color.TURQUOISE);
      put(3, Color.PINK);
      put(4, Color.ORANGE);
    }
  };

  public GhostsPanel(IGameModel model, int spriteTogglePeriod) {
    this.model = model;
    this.ghostSpritePeriodicToggler = new GhostSpriteToggler(spriteTogglePeriod);
  }

  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);
    ghostSpritePeriodicToggler.update();
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
    Direction direction = ghost.getDirection();
    Color color = GHOST_ID_TO_COLOR.get(ghost.getId());
    GhostState ghostSpriteState = ghostSpritePeriodicToggler.getGhostState();
    Image ghostImage = spriteFacade.getGhost(direction, color, ghostSpriteState);
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
