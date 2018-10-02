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
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public class GhostsPanel extends JPanel {

  private IGameModel model;
  private SpriteFacade spriteFacade = new SpriteFacade();
  private int pixelTileSize;
  private int offsetX = 0;
  private int offsetY = 0;
  private GhostSpriteToggler ghostSpritePeriodicToggler;

  private Map<Integer, Color> ghostIdToColor = new HashMap<>();


  public GhostsPanel(IGameModel model, int spriteTogglePeriod) {
    this.model = model;
    this.ghostSpritePeriodicToggler = new GhostSpriteToggler(spriteTogglePeriod);
    initializeGhostIdToColorMap();
  }

  private void initializeGhostIdToColorMap() {
    ghostIdToColor.put(1, Color.RED);
    ghostIdToColor.put(2, Color.TURQUOISE);
    ghostIdToColor.put(3, Color.PINK);
    ghostIdToColor.put(4, Color.ORANGE);
  }

  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);
    ghostSpritePeriodicToggler.update();
    for (Ghost ghost : model.getCurrentLevel().getGhosts()) {
      try {
        drawGhost(graphics, ghost);
      } catch (InvalidColorException | InvalidDirectionException | InvalidStateException e) {
        WarningDialog.display("Could not draw ghost", e);
      }
    }
  }

  private void drawGhost(Graphics graphics, Ghost ghost)
      throws InvalidColorException, InvalidDirectionException, InvalidStateException {
    Direction direction = ghost.getDirection();
    Color color = ghostIdToColor.get(ghost.getId());
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
