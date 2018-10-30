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

  private static final double NUMBER_OF_HIGHSCORES = 5;
  private static final double Y_OFFSET_FACTOR = 0.1;
  private static final String SPACE = " ";

  private List<HighScore> highscores;
  private List<JLabel> scoreLabels = new ArrayList<>();
  private JLabel goBackMenuOption = new JLabel();

  @SuppressWarnings("unchecked")
  public HighScoresPanel(IGameModel model) {
    this.model = model;

    for (int i = 0; i < NUMBER_OF_HIGHSCORES; i++) {
      JLabel jLabel = new JLabel();
      scoreLabels.add(jLabel);
      this.add(jLabel);
    }
    this.add(goBackMenuOption);

    addGoBackMouseListeners(goBackMenuOption);
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);

    highscores = new ArrayList<>(model.getHighScores().getListHighScores());
    Collections.sort(highscores);

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
    final int scoreIndex = index + 1;
    String label = scoreIndex + "." + score.getName() + SPACE + score.getScore();
    setMenuJLabel(jLabel, label, LABEL_COLOR, y, IMAGE_SCALE_FACTOR);
  }

  public JLabel getGoBackLabel() {
    return goBackMenuOption;
  }

}
