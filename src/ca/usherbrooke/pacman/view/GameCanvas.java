package ca.usherbrooke.pacman.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import ca.usherbrooke.pacman.model.IGameModel;

@SuppressWarnings({"serial", "squid:S1948"})
public class GameCanvas extends JPanel {
  private final IGameModel model;
  private GhostsPanel ghostsPanel;
  private PacManPanel pacmanPanel;
  private LevelPanel levelPanel;
  private TextPanel pausePanel;
  private TextPanel levelCompletedPanel;

  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 800;
  private static final String GAME_TITLE = "Pac-Man";
  private static final String pauseText = "PAUSE";

  private JLayeredPane layeredPane = new JLayeredPane();

  private JFrame window = new JFrame(GAME_TITLE);

  GameCanvas(IGameModel model, int spriteTogglePeriod) {
    this.model = model;

    window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    window.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    window.addWindowFocusListener(new WindowFocusListener() {
      @Override
      public void windowLostFocus(WindowEvent e) {
        model.pause();
      }

      @Override
      public void windowGainedFocus(WindowEvent e) {
        if (!model.isManuallyPaused()) {
          model.unpause();
        }
      }
    });

    levelPanel = new LevelPanel(model);
    levelPanel.setBackground(Color.BLACK);
    layeredPane.add(levelPanel, Integer.valueOf(1));

    ghostsPanel = new GhostsPanel(model, spriteTogglePeriod);
    ghostsPanel.setOpaque(false);
    layeredPane.add(ghostsPanel, Integer.valueOf(2));

    pacmanPanel = new PacManPanel(model);
    pacmanPanel.setOpaque(false);
    layeredPane.add(pacmanPanel, Integer.valueOf(3));

    window.add(layeredPane);
    window.add(this);
    window.setVisible(true);

  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    levelPanel.addKeyListener(keyListener);
  }

  @Override
  public void paint(Graphics graphic) {
    levelPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
    ghostsPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
    pacmanPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
    final int pixelTileSize = getPixelTileSize();
    levelPanel.setOffsetX(getOffsetX());
    levelPanel.setOffsetY(getOffsetY());
    levelPanel.setPixelTileSize(pixelTileSize);
    levelPanel.paint(graphic);
    ghostsPanel.setOffsetX(getOffsetX());
    ghostsPanel.setOffsetY(getOffsetY());
    ghostsPanel.setPixelTileSize(pixelTileSize);
    ghostsPanel.paint(graphic);
    pacmanPanel.setOffsetX(getOffsetX());
    pacmanPanel.setOffsetY(getOffsetY());
    pacmanPanel.setPixelTileSize(pixelTileSize);
    pacmanPanel.paint(graphic);

    if (model.isLevelCompleted()) {
      setLevelCompletedPanel();
      levelCompletedPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
      levelCompletedPanel.paint(graphic);
    } else if (model.isPaused()) {
      pausePanel.setBounds(0, 0, window.getWidth(), window.getHeight());
      pausePanel.paint(graphic);
    } else {
      levelCompletedPanel = null;
    }
  }

  public void dispose() {
    window.dispose();
  }

  public int getPixelTileSize() {
    final float panelWidthPixels = getWidth();
    final float panelHeightPixels = getHeight();
    final double availableWindowWidthPixels =
        Math.min(panelWidthPixels, 3.0 / 4.0 * panelHeightPixels);
    final double availableWindowHeightPixels =
        (int) Math.min(panelHeightPixels, 4.0 / 3.0 * panelWidthPixels);
    final double widthRatio = availableWindowWidthPixels / levelPanel.getWidthTiles();
    final double heightRatio = availableWindowHeightPixels / levelPanel.getHeightTiles();
    return (int) Math.min(Math.floor(widthRatio), Math.floor(heightRatio));
  }

  public int getOffsetX() {
    final int levelWidthPixels = levelPanel.getWidthTiles() * getPixelTileSize();
    return (getWidth() - levelWidthPixels) / 2;
  }

  public int getOffsetY() {
    final int levelHeightPixels = levelPanel.getHeightTiles() * getPixelTileSize();
    return (getHeight() - levelHeightPixels) / 2;
  }

  public void setPausePanel() {
    pausePanel = new TextPanel(model, ca.usherbrooke.pacman.view.Color.YELLOW, pauseText);
    pausePanel.setBackground(new Color(0, 0, 0, 80));
    pausePanel.setOpaque(true);
    window.add(pausePanel);
  }

  public void setLevelCompletedPanel() {
    if (levelCompletedPanel != null) {
      return;
    }

    final String levelText = "Level";
    final int levelNumberOffset = 2;

    levelCompletedPanel = new TextPanel(model, ca.usherbrooke.pacman.view.Color.YELLOW, levelText,
        model.getCurrentLevelIndex() + levelNumberOffset);
    levelCompletedPanel.setBackground(new Color(0, 0, 0, 80));
    levelCompletedPanel.setOpaque(true);
    window.add(levelCompletedPanel);
  }
}
