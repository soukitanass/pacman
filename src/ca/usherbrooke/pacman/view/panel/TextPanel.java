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
import java.awt.image.BufferedImage;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.view.utilities.Color;

@SuppressWarnings({"serial", "squid:S1948"})
public class TextPanel extends AbstractPanel {

  private double textScaleFactor;
  private IGameModel model;
  private String text;
  private Color textColor;
  private TextPanelPositioningStrategy positioningStrategy;

  public TextPanel(IGameModel model, String text, Color textColor, double textScaleFactor,
      TextPanelPositioningStrategy positioningStrategy) {
    this.model = model;
    this.text = text;
    this.textColor = textColor;
    this.textScaleFactor = textScaleFactor;
    this.positioningStrategy = positioningStrategy;
    setFocusable(true);
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    final BufferedImage image = getLabelImage(text, textColor, textScaleFactor);
    final Position position =
        positioningStrategy.getPosition(image, model, pixelTileSize, offsetX, offsetY);
    graphic.drawImage(image, position.getX(), position.getY(), null);
  }

}
