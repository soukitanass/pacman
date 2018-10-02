package ca.usherbrooke.pacman.view.panel;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.IGameModel;

@SuppressWarnings("serial")
public class AudioMenuPanel extends AbstractMenuPanel {

  private static double Y_OFFSET_FACTOR = 0.1;
  private static String MUTE_LABEL = "Mute";
  private static String MUSIC_LABEL = "MUSIC";
  private static String SOUND_LABEL = "SOUND";
  private static String GO_BACK_LABEL = "GO BACK";
  private static int MINIMAL_VOLUME = 0;
  private static int MAXIMAL_VOLUME = 100;

  private JLabel soundMenuOption = new JLabel();
  private JLabel musicMenuOption = new JLabel();
  private JLabel goBackMenuOption = new JLabel();
  private JCheckBox musicCheckbox = new JCheckBox(MUTE_LABEL);
  private JCheckBox soundCheckbox = new JCheckBox(MUTE_LABEL);
  private JSlider musicSlider =
      new JSlider(JSlider.HORIZONTAL, MINIMAL_VOLUME, MAXIMAL_VOLUME, MINIMAL_VOLUME);
  private JSlider soundSlider =
      new JSlider(JSlider.HORIZONTAL, MINIMAL_VOLUME, MAXIMAL_VOLUME, MINIMAL_VOLUME);

  public AudioMenuPanel(IGameModel model) {
    this.model = model;
    this.add(musicMenuOption);
    this.add(musicCheckbox);
    this.add(musicSlider);
    this.add(soundMenuOption);
    this.add(soundCheckbox);
    this.add(soundSlider);
    this.add(goBackMenuOption);

    addMouseListeners();
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);

    int y = (int) (model.getCurrentLevel().getHeight() * pixelTileSize * Y_OFFSET_FACTOR);
    y = paintMusicOption(y);
    y += musicSlider.getHeight() + DELTA_Y;
    y = paintSoundOption(y);
    y += soundSlider.getHeight() + DELTA_Y;
    paintGoBackOption(y);
  }

  private int paintMusicOption(int y) {
    setJLabel(musicMenuOption, MUSIC_LABEL, y, IMAGE_SCALE_FACTOR);
    final int x = musicMenuOption.getX() + musicMenuOption.getWidth() + CHECKBOX_X_OFFSET;
    setJCheckBox(musicCheckbox, x, y);
    y += musicMenuOption.getHeight() + DELTA_Y;
    setJSlider(musicSlider, y);
    return y;
  }

  private int paintSoundOption(int y) {
    setJLabel(soundMenuOption, SOUND_LABEL, y, IMAGE_SCALE_FACTOR);
    final int x = soundMenuOption.getX() + soundMenuOption.getWidth() + CHECKBOX_X_OFFSET;
    setJCheckBox(soundCheckbox, x, y);
    y += soundMenuOption.getHeight() + DELTA_Y;
    setJSlider(soundSlider, y);
    return y;
  }

  private void paintGoBackOption(int y) {
    setJLabel(goBackMenuOption, GO_BACK_LABEL, y, IMAGE_SCALE_FACTOR);
  }

  private void addMouseListeners() {
    goBackMenuOption.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        model.setGameState(GameState.GAME_MENU);
      }
    });
  }

}
