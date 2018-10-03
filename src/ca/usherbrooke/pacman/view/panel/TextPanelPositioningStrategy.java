package ca.usherbrooke.pacman.view.panel;

import java.awt.image.BufferedImage;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.Position;

public interface TextPanelPositioningStrategy {
  Position getPosition(BufferedImage image, IGameModel model, int pixelTileSize, int offsetX,
      int offsetY);
}
