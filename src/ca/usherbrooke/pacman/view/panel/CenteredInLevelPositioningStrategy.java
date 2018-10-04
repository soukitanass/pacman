/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.panel;

import java.awt.image.BufferedImage;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.Position;

public class CenteredInLevelPositioningStrategy implements TextPanelPositioningStrategy {

  @Override
  public Position getPosition(BufferedImage image, IGameModel model, int pixelTileSize, int offsetX,
      int offsetY) {
    final int width = model.getCurrentLevel().getWidth() * pixelTileSize;
    final int height = model.getCurrentLevel().getHeight() * pixelTileSize;
    final int x = (width / 2) - (image.getWidth() / 2) + offsetX;
    final int y = (height / 2) - (image.getHeight() / 2) + offsetY;
    return new Position(x, y);
  }

}
