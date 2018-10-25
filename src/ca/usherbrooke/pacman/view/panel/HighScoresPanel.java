package ca.usherbrooke.pacman.view.panel;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.highscores.HighScore;

@SuppressWarnings("serial")
public class HighScoresPanel extends AbstractMenuPanel {

  private static final double Y_OFFSET_FACTOR = 0.1;
  private static final String SPACE = " ";

  private List<HighScore> highscores = new ArrayList<>();
  private List<JLabel> scoreLabels = new ArrayList<>();
  private JLabel goBackMenuOption = new JLabel();

  @SuppressWarnings("unchecked")
  public HighScoresPanel(IGameModel model) {
    // TODO: Replace with the model highscores list
    highscores.add(new HighScore("Souk", 5000));
    highscores.add(new HighScore("Max", 5000));
    highscores.add(new HighScore("Ben", 10000));
    highscores.add(new HighScore("Marc", 10));
    Collections.sort(highscores);

    this.model = model;

    for (int i = 0; i < highscores.size(); i++) {
      JLabel jLabel = new JLabel();
      scoreLabels.add(jLabel);
      this.add(jLabel);
    }
    this.add(goBackMenuOption);
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);

    int y = (int) (model.getCurrentLevel().getHeight() * pixelTileSize * Y_OFFSET_FACTOR);

    for (int i = 0; i < highscores.size(); i++) {
      paintHighScore(i, y);
      y += scoreLabels.get(i).getHeight() + DELTA_Y;
    }

    paintGoBackOption(goBackMenuOption);
  }

  private void paintHighScore(int index, int y) {
    JLabel jLabel = scoreLabels.get(index);
    HighScore score = highscores.get(index);
    String label = index + "." + String.valueOf(score.getName()) + SPACE + score.getScore();
    setJLabel(jLabel, label, LABEL_COLOR, y, IMAGE_SCALE_FACTOR);
  }

}
