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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.threads.AudioThread;

@SuppressWarnings("serial")
public class AudioMenuPanel extends AbstractMenuPanel {

  private static final double Y_OFFSET_FACTOR = 0.1;
  private static final String MUTE_LABEL = "Mute";
  private static final String MUSIC_LABEL = "MUSIC";
  private static final String SOUND_LABEL = "SOUND";
  private static final int MINIMAL_VOLUME = 0;
  private static final int MAXIMAL_VOLUME = 100;

  private JLabel soundMenuOption = new JLabel();
  private JLabel musicMenuOption = new JLabel();
  private JLabel goBackMenuOption = new JLabel();
  private JCheckBox musicCheckbox = new JCheckBox(MUTE_LABEL);
  private JCheckBox soundCheckbox = new JCheckBox(MUTE_LABEL);
  private JSlider musicSlider =
      new JSlider(JSlider.HORIZONTAL, MINIMAL_VOLUME, MAXIMAL_VOLUME, MAXIMAL_VOLUME);
  private JSlider soundSlider =
      new JSlider(JSlider.HORIZONTAL, MINIMAL_VOLUME, MAXIMAL_VOLUME, MAXIMAL_VOLUME);
  private AudioThread audioThread;

  public AudioMenuPanel(IGameModel model, AudioThread audioThread) {
    this.model = model;
    this.audioThread = audioThread;
    this.add(musicMenuOption);
    this.add(musicCheckbox);
    this.add(musicSlider);
    this.add(soundMenuOption);
    this.add(soundCheckbox);
    this.add(soundSlider);
    this.add(goBackMenuOption);

    addChangeListeners();
    addItemListeners();
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);

    int y = (int) (model.getCurrentLevel().getHeight() * pixelTileSize * Y_OFFSET_FACTOR);
    y = paintMusicOption(y);
    y += musicSlider.getHeight() + DELTA_Y;
    y = paintSoundOption(y);
    y += soundSlider.getHeight() + DELTA_Y;
    paintGoBackOption(goBackMenuOption, y);
  }

  private int paintMusicOption(int y) {
    setJLabel(musicMenuOption, MUSIC_LABEL, LABEL_COLOR, y, IMAGE_SCALE_FACTOR);
    final int x = musicMenuOption.getX() + musicMenuOption.getWidth() + CHECKBOX_X_OFFSET;
    setJCheckBox(musicCheckbox, x, y);
    y += musicMenuOption.getHeight() + DELTA_Y;
    setJSlider(musicSlider, y);
    return y;
  }

  private int paintSoundOption(int y) {
    setJLabel(soundMenuOption, SOUND_LABEL, LABEL_COLOR, y, IMAGE_SCALE_FACTOR);
    final int x = soundMenuOption.getX() + soundMenuOption.getWidth() + CHECKBOX_X_OFFSET;
    setJCheckBox(soundCheckbox, x, y);
    y += soundMenuOption.getHeight() + DELTA_Y;
    setJSlider(soundSlider, y);
    return y;
  }

  private void addChangeListeners() {
    musicSlider.addChangeListener(e -> {
      if (musicSlider.getValueIsAdjusting()) {
        int value = musicSlider.getValue();
        audioThread.setMusicVolumeChanged(value);
      }
    });

    soundSlider.addChangeListener(e -> {
      if (soundSlider.getValueIsAdjusting()) {
        int value = soundSlider.getValue();
        audioThread.setSoundVolumeChanged(value);
      }
    });
  }

  private void addItemListeners() {
    musicCheckbox.addItemListener(e -> {
      if (e.getSource() == musicCheckbox) {
        boolean value = musicCheckbox.isSelected();
        audioThread.setMusicPlay(value);
      }
    });

    soundCheckbox.addItemListener(e -> {
      if (e.getSource() == soundCheckbox) {
        boolean value = soundCheckbox.isSelected();
        audioThread.setIsSoundPlaying(value);
      }
    });
  }

}
