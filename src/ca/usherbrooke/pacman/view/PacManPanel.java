package ca.usherbrooke.pacman.view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.PacMan;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.exceptions.InvalidStateException;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings({"serial", "squid:S1948"})
public class PacManPanel extends JPanel {

  private IGameModel model;
  private int pixelTileSize;
  private int offsetX = 0;
  private int offsetY = 0;
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
    } catch (InvalidDirectionException | InvalidStateException exception) {
      WarningDialog.display("Error while painting pacman. ", exception);
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
