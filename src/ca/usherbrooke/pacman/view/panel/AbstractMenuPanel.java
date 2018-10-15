/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import ca.usherbrooke.pacman.model.IGameModel;

@SuppressWarnings({"serial", "squid:S1948"})
public abstract class AbstractMenuPanel extends AbstractPanel {

  protected static final int IMAGE_SCALE_FACTOR = 2;
  protected static final int DELTA_Y = 40;
  protected static final int CHECKBOX_X_OFFSET = 10;

  private static final int SLIDER_WIDTH = 360;
  private static final int MINOR_TICK_SPACING = 2;
  private static final int MAJOR_TICK_SPACING = 10;
  private static final Color FOREGROUND_COLOR = Color.WHITE;
  private static final Color BACKGROUND_COLOR = Color.BLACK;
  private static final ca.usherbrooke.pacman.view.Color SPRITE_COLOR =
      ca.usherbrooke.pacman.view.Color.WHITE;

  protected IGameModel model;

  protected void setJCheckBox(JCheckBox checkbox, int x, int y) {
    checkbox.setLocation(x, y);
    checkbox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    checkbox.setBackground(BACKGROUND_COLOR);
    checkbox.setForeground(FOREGROUND_COLOR);
  }

  protected void setJSlider(JSlider slider, int y) {
    final int levelWidth = model.getCurrentLevel().getWidth() * pixelTileSize;
    final int x = (levelWidth / 2) - (slider.getWidth() / 2) + offsetX;

    slider.setLocation(x, y);
    slider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    slider.setForeground(FOREGROUND_COLOR);
    slider.setBackground(BACKGROUND_COLOR);
    slider.setMinorTickSpacing(MINOR_TICK_SPACING);
    slider.setMajorTickSpacing(MAJOR_TICK_SPACING);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setSize(SLIDER_WIDTH, slider.getHeight());
  }

  protected void setJLabel(JLabel jLabel, String text, int y, double scaleFactor) {
    final BufferedImage image = getLabelImage(text, SPRITE_COLOR, scaleFactor);
    final int levelWidth = model.getCurrentLevel().getWidth() * pixelTileSize;
    final int x = (levelWidth / 2) - (image.getWidth() / 2) + offsetX;
    final Point location = new Point(x, y);
    final ImageIcon imageIcon = new ImageIcon(image);

    jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    jLabel.setLocation(location);
    jLabel.setIcon(imageIcon);
  }
}
