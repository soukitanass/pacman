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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.highscores.HighScores;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.view.utilities.AlphabetUtilities;
import ca.usherbrooke.pacman.view.utilities.Color;
import ca.usherbrooke.pacman.view.utilities.ImageUtilities;

@SuppressWarnings("serial")
public class NewHighScorePanel extends AbstractMenuPanel implements KeyListener {

  private static final int BLINKING_PERIOD = 110;
  private static final char INITIAL_CHARACTER = 'a';
  private static final String NEW_HIGH_SCORE_LABEL = "new highscore";
  private static final String SAVE_HIGH_SCORE_LABEL = "Save";
  private static final int DELTA_X = 10;
  private static final int LETTER_SCALE_FACTOR = 5;
  private static final int NUMBER_OF_LETTERS = 3;
  private static final String SPACE = " ";
  private static final String HIGH_SCORES_PATH = "Highscores.json";

  private JLabel highScoreLabel = new JLabel();
  private JLabel saveLabel = new JLabel();
  private List<JLabel> letterLabels = new ArrayList<>();
  private List<Character> letters = new ArrayList<>();
  private int index = 0;
  private int updatesCounter = 0;
  private int blinkingCounter = 0;

  @SuppressWarnings("unchecked")
  public NewHighScorePanel(IGameModel model) {
    this.model = model;
    this.addKeyListener(this);
    this.setFocusable(true);

    for (int i = 0; i < NUMBER_OF_LETTERS; i++) {
      letters.add(INITIAL_CHARACTER);
      letterLabels.add(new JLabel());
    }

    addComponents();
    addMouseListeners();
  }

  @Override
  public void paint(Graphics graphic) {
    super.paint(graphic);
    int newHighScore = model.getScore();
    final int y = (int) (getBounds().getHeight() * Y_OFFSET_FACTOR);
    paintNewHighScore(highScoreLabel, newHighScore, y);

    for (int i = 0; i < NUMBER_OF_LETTERS; i++) {
      paintLetter(i);
    }

    paintSaveOption(saveLabel, SAVE_HIGH_SCORE_LABEL);
    updateCounters();
  }

  private void addComponents() {
    for (int i = 0; i < NUMBER_OF_LETTERS; i++) {
      this.add(letterLabels.get(i));
    }
    this.add(highScoreLabel);
    this.add(saveLabel);
  }

  private void paintNewHighScore(JLabel jLabel, int newHighScore, int y) {
    final String label = NEW_HIGH_SCORE_LABEL + SPACE + newHighScore;
    setMenuJLabel(jLabel, label, LABEL_COLOR, y, IMAGE_SCALE_FACTOR);
  }

  private void paintLetter(int currentIndex) {
    Color color;
    if (currentIndex == index && blinkingCounter % 2 == 0) {
      color = Color.YELLOW;
    } else {
      color = Color.WHITE;
    }
    final char character = letters.get(currentIndex);
    final JLabel jLabel = letterLabels.get(currentIndex);
    final BufferedImage image =
        ImageUtilities.getTextImage(String.valueOf(character), color, LETTER_SCALE_FACTOR);
    final ImageIcon imageIcon = new ImageIcon(image);
    final int levelWidth = model.getCurrentLevel().getWidth() * pixelTileSize;
    int x = (levelWidth / 2) - (image.getWidth() / 2) + offsetX;
    x += (currentIndex - 1) * (imageIcon.getIconWidth() + DELTA_X);
    final CenteredInLevelPositioningStrategy strategy = new CenteredInLevelPositioningStrategy();
    final Position location = strategy.getPosition(image, model, pixelTileSize, offsetX, offsetY);

    jLabel.setLocation(x, location.getY());
    jLabel.setIcon(imageIcon);
  }

  private void paintSaveOption(JLabel jLabel, String label) {
    setBottomMenuJLabel(jLabel, label, IMAGE_SCALE_FACTOR);
  }

  private void updateCounters() {
    updatesCounter++;
    if (updatesCounter % BLINKING_PERIOD == 0) {
      blinkingCounter++;
    }
  }

  private void resetCounters() {
    updatesCounter = 0;
    blinkingCounter = 0;
  }

  private void addMouseListeners() {
    saveLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        saveHighScores();
      }
    });
  }
  
  private void saveHighScores() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < letters.size(); ++i) {
      builder.append(letters.get(i));
    }
    String name = builder.toString();
    model.getHighScores().setHighScore(model.getScore(), name);
    model.setHighScores(HighScores.loadHighScores(HIGH_SCORES_PATH));
    model.setGameState(GameState.HIGHSCORES_MENU);
  }

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    switch (keyEvent.getKeyCode()) {
      case KeyEvent.VK_RIGHT:
        index = index >= 2 ? 0 : index + 1;
        resetCounters();
        break;
      case KeyEvent.VK_LEFT:
        index = index <= 0 ? 2 : index - 1;
        resetCounters();
        break;
      case KeyEvent.VK_UP:
        int nextPosition = AlphabetUtilities.findIndexInAlphabet(letters.get(index));
        final char nextCharacter = AlphabetUtilities.findLetterInAlphabet(nextPosition + 1);
        letters.set(index, nextCharacter);
        break;
      case KeyEvent.VK_DOWN:
        final int previousPosition = AlphabetUtilities.findIndexInAlphabet(letters.get(index));
        final char previousCharacter = AlphabetUtilities.findLetterInAlphabet(previousPosition - 1);
        letters.set(index, previousCharacter);
        break;
      case KeyEvent.VK_ENTER:
        saveHighScores();
        break;
      default:
        break;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Not implemented
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Not implemented
  }

}
