/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.panel;

import java.awt.Graphics;
import java.awt.Image;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.PacMan;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.exceptions.InvalidStateException;
import ca.usherbrooke.pacman.view.PacManState;
import ca.usherbrooke.pacman.view.PacmanSpriteToggler;
import ca.usherbrooke.pacman.view.SpriteFacade;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public class PacManPanel extends AbstractPanel {
  private IGameModel model;
  private PacmanSpriteToggler pacmanSpritePeriodicToggler;
  private SpriteFacade spriteFacade = new SpriteFacade();

  public PacManPanel(IGameModel model, int spriteTogglePeriod) {
    this.model = model;
    pacmanSpritePeriodicToggler = new PacmanSpriteToggler(spriteTogglePeriod);
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    pacmanSpritePeriodicToggler.update();
    try {
      drawPacman(graphic, model.getPacman());
    } catch (InvalidDirectionException | InvalidStateException e) {
      WarningDialog.display("Could not draw pacman", e);
    }
  }

  public void drawPacman(Graphics graphic, PacMan pacman)
      throws InvalidStateException, InvalidDirectionException {
    Direction direction = pacman.getDirection();
    PacManState pacmanSpriteState = pacmanSpritePeriodicToggler.getPacmanState();
    Image ghostImage = spriteFacade.getPacman(direction, pacmanSpriteState);
    final int x = pacman.getPosition().getX() * pixelTileSize + offsetX;
    final int y = pacman.getPosition().getY() * pixelTileSize + offsetY;
    final int width = pixelTileSize;
    final int height = pixelTileSize;
    final int tileSize = spriteFacade.getTileSize();
    graphic.drawImage(ghostImage, x, y, x + width, y + height, 0, 0, tileSize, tileSize, null);
  }
}
