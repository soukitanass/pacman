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

  private static int Y_OFFSET = 40;
  private static String MUTE_LABEL = "Mute";
  private static String MUSIC_LABEL = "MUSIC";
  private static String SOUND_LABEL = "SOUND";
  private static String GO_BACK_LABEL = "GO BACK";
  private static int MINIMAL_VOLUMNE = 0;
  private static int MAXIMAL_VOLUMNE = 100;

  private JLabel soundMenuOption = new JLabel();
  private JLabel musicMenuOption = new JLabel();
  private JLabel goBackMenuOption = new JLabel();
  private JCheckBox musicCheckbox = new JCheckBox(MUTE_LABEL);
  private JCheckBox soundCheckbox = new JCheckBox(MUTE_LABEL);
  private JSlider musicSlider =
      new JSlider(JSlider.HORIZONTAL, MINIMAL_VOLUMNE, MAXIMAL_VOLUMNE, MINIMAL_VOLUMNE);
  private JSlider soundSlider =
      new JSlider(JSlider.HORIZONTAL, MINIMAL_VOLUMNE, MAXIMAL_VOLUMNE, MINIMAL_VOLUMNE);

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

    // Music:
    int y = Y_OFFSET;
    setJLabel(musicMenuOption, MUSIC_LABEL, y, IMAGE_SCALE_FACTOR);
    int x = musicMenuOption.getX() + musicMenuOption.getWidth() + CHECKBOX_X_OFFSET;
    setJCheckBox(musicCheckbox, x, y);
    y += musicMenuOption.getHeight() + DELTA_Y;
    setJSlider(musicSlider, y);

    // Sound:
    y += musicSlider.getHeight() + DELTA_Y;
    setJLabel(soundMenuOption, SOUND_LABEL, y, IMAGE_SCALE_FACTOR);
    x = soundMenuOption.getX() + soundMenuOption.getWidth() + CHECKBOX_X_OFFSET;
    setJCheckBox(soundCheckbox, x, y);
    y += soundMenuOption.getHeight() + DELTA_Y;
    setJSlider(soundSlider, y);

    // Go back:
    y += soundSlider.getHeight() + DELTA_Y;
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
