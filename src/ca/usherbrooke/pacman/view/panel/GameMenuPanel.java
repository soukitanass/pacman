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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.IGameModel;

@SuppressWarnings({"serial", "squid:S1948"})
public class GameMenuPanel extends AbstractMenuPanel {

  private static final double Y_OFFSET_FACTOR = 0.6;
  private static final double Y_OFFSET_TITLE_FACTOR = 0.3;
  private static final String START_GAME_LABEL = "START GAME";
  private static final String PAUSE_GAME_LABEL = "RESUME GAME";
  private static final String AUDIO_LABEL = "AUDIO";
  private static final String HIGHSCORES_LABEL = "HIGHSCORES";
  private static final String PACMAN_LABEL = "PACMAN";
  private static final String EXIT_GAME_LABEL = "EXIT GAME";
  private static final String FPS_LABEL = "FPS";
  private static final int FPS_STEP = 1;
  private static final int MINIMUM_FPS_VALUE = 30;
  private static final int MAXIMUM_FPS_VALUE = 60;
  private static final ca.usherbrooke.pacman.view.utilities.Color LABEL_TITLE_COLOR =
      ca.usherbrooke.pacman.view.utilities.Color.YELLOW;
  private static final int TITLE_IMAGE_SCALE_FACTOR = 3;

  private JLabel pacmanTitle = new JLabel();
  private JLabel startGameMenuOption = new JLabel();
  private JLabel audioMenuOption = new JLabel();
  private JLabel highScoresMenuOption = new JLabel();
  private JLabel exitGameMenuOption = new JLabel();
  private JLabel fpsMenuOption = new JLabel();
  private JCheckBox fpsCheckBox = new JCheckBox("On/Off");
  private SpinnerModel spinnerModel =
      new SpinnerNumberModel(MINIMUM_FPS_VALUE, MINIMUM_FPS_VALUE, MAXIMUM_FPS_VALUE, FPS_STEP);
  private JSpinner fpsSpinner = new JSpinner(spinnerModel);
  private FpsOptionListener fpsOptionListener;

  public GameMenuPanel(IGameModel model, FpsOptionListener fpsOptionListener) {
    this.model = model;
    this.fpsOptionListener = fpsOptionListener;
    this.add(pacmanTitle);
    this.add(startGameMenuOption);
    this.add(highScoresMenuOption);
    this.add(audioMenuOption);
    this.add(fpsMenuOption);
    this.add(fpsCheckBox);
    this.add(fpsSpinner);
    this.add(exitGameMenuOption);

    addMouseListeners();
    addFpsOptionListener();
    addFpsSpinnerListener();
  }

  private void addFpsOptionListener() {
    fpsCheckBox.addActionListener(e -> fpsOptionListener.setFpsEnabled(fpsCheckBox.isSelected()));
  }

  private void addFpsSpinnerListener() {
    fpsSpinner.addChangeListener(e -> fpsOptionListener.setFps((int) fpsSpinner.getValue()));
  }

  public void setFpsEnableListener(FpsOptionListener fpsOptionListener) {
    this.fpsOptionListener = fpsOptionListener;
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);

    int y = (int) (model.getCurrentLevel().getHeight() * pixelTileSize * Y_OFFSET_TITLE_FACTOR);
    paintTitle(pacmanTitle, PACMAN_LABEL, y);
    y = (int) (model.getCurrentLevel().getHeight() * pixelTileSize * Y_OFFSET_FACTOR);
    paintStartGameOption(y);
    y += startGameMenuOption.getHeight() + DELTA_Y;
    paintMenuOption(audioMenuOption, AUDIO_LABEL, y);
    y += audioMenuOption.getHeight() + DELTA_Y;
    paintMenuOption(highScoresMenuOption, HIGHSCORES_LABEL, y);
    y += highScoresMenuOption.getHeight() + DELTA_Y;
    paintMenuOption(fpsMenuOption, FPS_LABEL, y);
    paintFpsOption(y);
    y += fpsMenuOption.getHeight() + DELTA_Y;
    paintMenuOption(exitGameMenuOption, EXIT_GAME_LABEL, y);
  }

  private void paintStartGameOption(int y) {
    if (model.isPaused()) {
      paintMenuOption(startGameMenuOption, PAUSE_GAME_LABEL, y);
    } else {
      paintMenuOption(startGameMenuOption, START_GAME_LABEL, y);
    }
  }

  private void paintMenuOption(JLabel jLabel, String label, int y) {
    setMenuJLabel(jLabel, label, LABEL_COLOR, y, IMAGE_SCALE_FACTOR);
  }
  
  private void paintTitle(JLabel jLabel, String label, int y) {
    setMenuJLabel(jLabel, label, LABEL_TITLE_COLOR, y, TITLE_IMAGE_SCALE_FACTOR);
  }

  private void paintFpsOption(int y) {
    int x = fpsMenuOption.getX() + fpsMenuOption.getWidth() + CHECKBOX_X_OFFSET;
    setJCheckBox(fpsCheckBox, x, y);
    x += fpsCheckBox.getWidth() + CHECKBOX_X_OFFSET;
    setJSpinner(fpsSpinner, x, y);
  }

  private void addMouseListeners() {
    startGameMenuOption.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (model.isGameOver()) {
          model.startNewGame();
        }
        model.setGameState(GameState.GAME);
      }
    });

    audioMenuOption.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        model.setGameState(GameState.AUDIO_MENU);
      }
    });

    highScoresMenuOption.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        model.setGameState(GameState.HIGHSCORES_MENU);
      }
    });

    exitGameMenuOption.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        model.quit();
      }
    });
  }

  public JLabel getStartGameLabel() {
    return startGameMenuOption;
  }

  public JLabel getAudioLabel() {
    return audioMenuOption;
  }

  public JLabel getHighscoresLabel() {
    return highScoresMenuOption;
  }

  public JCheckBox getFramesPerSecondCheckbox() {
    return fpsCheckBox;
  }

  public JSpinner getFpsSpinner() {
    return fpsSpinner;
  }

  public JLabel getExitGameLabel() {
    return exitGameMenuOption;
  }
}
