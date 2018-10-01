package ca.usherbrooke.pacman.view.panel;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.IGameModel;

@SuppressWarnings("serial")
public class GameMenuPanel extends AbstractMenuPanel {

  private static int Y_OFFSET = 400;
  private static String START_GAME_LABEL = "START GAME";
  private static String PAUSE_GAME_LABEL = "RESUME GAME";
  private static String AUDIO_LABEL = "AUDIO";
  private static String EXIT_GAME_LABEL = "EXIT GAME";
  private static String FPS_LABEL = "FPS";

  private JLabel startGameMenuOption = new JLabel();
  private JLabel audioMenuOption = new JLabel();
  private JLabel exitGameMenuOption = new JLabel();
  private JLabel fpsMenuOption = new JLabel();
  private JCheckBox fpsCheckBox = new JCheckBox("On/Off");

  public GameMenuPanel(IGameModel model) {
    this.model = model;
    this.add(startGameMenuOption);
    this.add(audioMenuOption);
    this.add(fpsMenuOption);
    this.add(fpsCheckBox);
    this.add(exitGameMenuOption);

    addMouseListeners();
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);

    // Start Game:
    int y = Y_OFFSET;
    if (model.isPaused()) {
      setJLabel(startGameMenuOption, PAUSE_GAME_LABEL, y, IMAGE_SCALE_FACTOR);
    } else {
      setJLabel(startGameMenuOption, START_GAME_LABEL, y, IMAGE_SCALE_FACTOR);
    }

    // Audio:
    y += startGameMenuOption.getHeight() + DELTA_Y;
    setJLabel(audioMenuOption, AUDIO_LABEL, y, IMAGE_SCALE_FACTOR);

    // FPS:
    y += audioMenuOption.getHeight() + DELTA_Y;
    setJLabel(fpsMenuOption, FPS_LABEL, y, IMAGE_SCALE_FACTOR);
    final int x = fpsMenuOption.getX() + fpsMenuOption.getWidth() + CHECKBOX_X_OFFSET;
    setJCheckBox(fpsCheckBox, x, y);

    // Exit Game:
    y += fpsMenuOption.getHeight() + DELTA_Y;
    setJLabel(exitGameMenuOption, EXIT_GAME_LABEL, y, IMAGE_SCALE_FACTOR);
  }

  private void addMouseListeners() {
    startGameMenuOption.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        model.setGameState(GameState.GAME);
        model.unpause();
      }
    });

    audioMenuOption.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        model.setGameState(GameState.AUDIO_MENU);
      }
    });

    exitGameMenuOption.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        model.quit();
      }
    });
  }
}
