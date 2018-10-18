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
import ca.usherbrooke.pacman.model.position.Position;

public class BottomCenteredInLevelPositionStrategy implements TextPanelPositioningStrategy {

  private int panelHeight;
  private int bottomMargin;

  public BottomCenteredInLevelPositionStrategy(int panelHeight, int bottomMargin) {
    this.panelHeight = panelHeight;
    this.bottomMargin = bottomMargin;
  }

  @Override
  public Position getPosition(BufferedImage image, IGameModel model, int pixelTileSize, int offsetX,
      int offsetY) {
    final int width = model.getCurrentLevel().getWidth() * pixelTileSize;
    final int x = (width / 2) - (image.getWidth() / 2) + offsetX;
    final int y = panelHeight - image.getHeight() - offsetY - bottomMargin;

    return new Position(x, y);
  }

}
