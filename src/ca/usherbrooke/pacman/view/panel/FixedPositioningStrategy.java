package ca.usherbrooke.pacman.view.panel;

import java.awt.image.BufferedImage;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.Position;

public class FixedPositioningStrategy implements TextPanelPositioningStrategy {

  private Position fixedPosition;

  public FixedPositioningStrategy(Position fixedPosition) {
    this.fixedPosition = fixedPosition;
  }

  @Override
  public Position getPosition(BufferedImage image, IGameModel model, int pixelTileSize, int offsetX,
      int offsetY) {
    return new Position(fixedPosition.getX() + offsetX, fixedPosition.getY() + offsetY);
  }

}
