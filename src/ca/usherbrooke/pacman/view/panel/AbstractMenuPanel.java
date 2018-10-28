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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.view.utilities.ImageUtilities;

@SuppressWarnings({"serial", "squid:S1948"})
public abstract class AbstractMenuPanel extends AbstractPanel {

  protected static final double Y_OFFSET_FACTOR = 0.1;
  protected static final int IMAGE_SCALE_FACTOR = 2;
  protected static final int DELTA_Y = 40;
  protected static final int CHECKBOX_X_OFFSET = 10;
  protected static final ca.usherbrooke.pacman.view.utilities.Color LABEL_COLOR =
      ca.usherbrooke.pacman.view.utilities.Color.WHITE;

  private static final ca.usherbrooke.pacman.view.utilities.Color ACTION_LABEL_COLOR =
      ca.usherbrooke.pacman.view.utilities.Color.YELLOW;
  private static final String GO_BACK_LABEL = "GO BACK";
  private static final int NUMBER_OF_TEXT_FIELD_COLUMNS = 2;
  private static final int SLIDER_WIDTH = 360;
  private static final int MINOR_TICK_SPACING = 2;
  private static final int MAJOR_TICK_SPACING = 10;
  private static final Color FOREGROUND_COLOR = Color.WHITE;
  private static final Color BACKGROUND_COLOR = Color.BLACK;

  protected IGameModel model;

  private void setJLabel(JLabel jLabel, Point location, ImageIcon imageIcon) {
    jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    jLabel.setLocation(location);
    jLabel.setIcon(imageIcon);
  }

  private int getXCenteredPosition(int width) {
    final int levelWidth = model.getCurrentLevel().getWidth() * pixelTileSize;
    return (levelWidth / 2) - (width / 2) + offsetX;
  }

  protected void setJCheckBox(JCheckBox checkbox, int x, int y) {
    checkbox.setLocation(x, y);
    checkbox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    checkbox.setBackground(BACKGROUND_COLOR);
    checkbox.setForeground(FOREGROUND_COLOR);
  }

  protected void setJSlider(JSlider slider, int y) {
    final int x = getXCenteredPosition(slider.getWidth());

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

  protected void setMenuJLabel(JLabel jLabel, String text,
      ca.usherbrooke.pacman.view.utilities.Color color, int y, double scaleFactor) {
    final BufferedImage image = ImageUtilities.getTextImage(text, color, scaleFactor);
    final int x = getXCenteredPosition(image.getWidth());
    final Point location = new Point(x, y);
    final ImageIcon imageIcon = new ImageIcon(image);

    setJLabel(jLabel, location, imageIcon);
  }

  protected void setBottomMenuJLabel(JLabel jLabel, String text, double scaleFactor) {
    final BufferedImage image =
        ImageUtilities.getTextImage(text, ACTION_LABEL_COLOR, scaleFactor);
    final int height =
        (int) (this.getBounds().getHeight() - (this.getBounds().getHeight() * Y_OFFSET_FACTOR));
    final BottomCenteredInLevelPositioningStrategy strategy =
        new BottomCenteredInLevelPositioningStrategy(height);
    final ImageIcon imageIcon = new ImageIcon(image);
    final Position position = strategy.getPosition(image, model, pixelTileSize, offsetX, offsetY);
    final Point location = new Point(position.getX(), position.getY());

    setJLabel(jLabel, location, imageIcon);
  }

  protected void setJSpinner(JSpinner jSpinner, int x, int y) {
    final Point location = new Point(x, y);

    JFormattedTextField textField = ((JSpinner.DefaultEditor) jSpinner.getEditor()).getTextField();
    textField.setColumns(NUMBER_OF_TEXT_FIELD_COLUMNS);
    textField.setEditable(false);
    textField.setHorizontalAlignment(SwingConstants.CENTER);

    jSpinner.setLocation(location);
  }

  protected void paintGoBackOption(JLabel jLabel) {
    setBottomMenuJLabel(jLabel, GO_BACK_LABEL, IMAGE_SCALE_FACTOR);
  }

  protected void addGoBackMouseListeners(JLabel jLabel) {
    jLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        model.setGameState(GameState.GAME_MENU);
      }
    });
  }
}
