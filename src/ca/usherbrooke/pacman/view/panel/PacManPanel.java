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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.exceptions.InvalidStateException;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.view.spirites.PacmanSpriteToggler;
import ca.usherbrooke.pacman.view.spirites.SpriteFacade;
import ca.usherbrooke.pacman.view.states.PacManState;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public class PacManPanel extends AbstractPanel {
  private IGameModel model;
  private PacmanSpriteToggler pacmanSpritePeriodicToggler;
  private SpriteFacade spriteFacade = new SpriteFacade();

  public PacManPanel(IGameModel model, int spriteTogglePeriod) {
    this.model = model;
    pacmanSpritePeriodicToggler = new PacmanSpriteToggler(spriteTogglePeriod, model);
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    pacmanSpritePeriodicToggler.update();
    PacManState pacmanSpriteState = pacmanSpritePeriodicToggler.getPacmanState();
    try {
      drawPacman(graphic, model.getPacman(), pacmanSpriteState);
    } catch (InvalidDirectionException | InvalidStateException e) {
      WarningDialog.display("Could not draw pacman", e);
    }
  }

  public void drawPacman(Graphics graphic, PacMan pacman, PacManState state)
      throws InvalidStateException, InvalidDirectionException {
    Image pacmanImage = getPacmanImage(pacman, state);
    final int x = pacman.getPosition().getX() * pixelTileSize + offsetX;
    final int y = pacman.getPosition().getY() * pixelTileSize + offsetY;
    final int width = pixelTileSize;
    final int height = pixelTileSize;
    final int tileSize = spriteFacade.getTileSize();
    graphic.drawImage(pacmanImage, x, y, x + width, y + height, 0, 0, tileSize, tileSize, null);
  }

  private BufferedImage getPacmanImage(PacMan pacman, PacManState state)
      throws InvalidStateException, InvalidDirectionException {
    Direction direction = pacman.getDirection();
    List<PacManState> alivePacManStates = getAlivePacmanStates();

    if (alivePacManStates.contains(state)) {
      return spriteFacade.getPacman(direction, state);
    } else {
      return spriteFacade.getDeadPacman(state);
    }
  }

  private List<PacManState> getAlivePacmanStates() {
    List<PacManState> alivePacManStates = new ArrayList<>();
    alivePacManStates.add(PacManState.STATE1);
    alivePacManStates.add(PacManState.STATE2);
    alivePacManStates.add(PacManState.STATE3);
    alivePacManStates.add(PacManState.STATE4);
    alivePacManStates.add(PacManState.STATE5);
    return alivePacManStates;
  }
}
