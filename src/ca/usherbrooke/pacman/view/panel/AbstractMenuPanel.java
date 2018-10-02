package ca.usherbrooke.pacman.view.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.SpriteFacade;
import ca.usherbrooke.pacman.view.utilities.ImageUtilities;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

@SuppressWarnings("serial")
public abstract class AbstractMenuPanel extends AbstractPanel {

  protected static int IMAGE_SCALE_FACTOR = 2;
  protected static int DELTA_Y = 40;
  protected static int CHECKBOX_X_OFFSET = 10;

  private static int SLIDER_WIDTH = 360;
  private static int MINOR_TICK_SPACING = 2;
  private static int MAJOR_TICK_SPACING = 10;
  private static Color FOREGROUND_COLOR = Color.WHITE;
  private static Color BACKGROUND_COLOR = Color.BLACK;

  protected IGameModel model;
  private ImageUtilities imageUtilities = new ImageUtilities();
  private SpriteFacade spriteFacade = new SpriteFacade();

  private BufferedImage getJLabelImage(final String label, final int scaleFactor) {
    List<BufferedImage> images = new ArrayList<>();

    for (int i = 0; i < label.length(); i++) {
      BufferedImage image = null;
      try {
        image =
            spriteFacade.getLetter((char) label.charAt(i), ca.usherbrooke.pacman.view.Color.WHITE);
        BufferedImage resizedImage = imageUtilities.resize(image, image.getWidth() * scaleFactor,
            image.getHeight() * scaleFactor);
        images.add(resizedImage);
      } catch (Exception exception) {
        WarningDialog.display("Error while painting the panel. ", exception);
      }
    }

    return imageUtilities.joinImages(images);
  }

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

  protected void setJLabel(JLabel jLabel, String text, int y, int scaleFactor) {
    final BufferedImage image = getJLabelImage(text, scaleFactor);
    final int levelWidth = model.getCurrentLevel().getWidth() * pixelTileSize;
    final int x = (levelWidth / 2) - (image.getWidth() / 2) + offsetX;
    final Point location = new Point(x, y);
    final ImageIcon imageIcon = new ImageIcon(image);

    jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    jLabel.setLocation(location);
    jLabel.setIcon(imageIcon);
  }
}
