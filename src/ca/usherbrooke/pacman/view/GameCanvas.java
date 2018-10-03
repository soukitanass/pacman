package ca.usherbrooke.pacman.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.panel.AbstractMenuPanel;
import ca.usherbrooke.pacman.view.panel.AudioMenuPanel;
import ca.usherbrooke.pacman.view.panel.GameMenuPanel;
import ca.usherbrooke.pacman.view.panel.GhostsPanel;
import ca.usherbrooke.pacman.view.panel.LevelPanel;
import ca.usherbrooke.pacman.view.panel.PacManPanel;
import ca.usherbrooke.pacman.view.panel.TextPanel;

@SuppressWarnings({"serial", "squid:S1948"})
public class GameCanvas extends JPanel {
  private static final double RATIO_LEVEL_HEIGHT_TO_TOTAL_HEIGHT = 0.9;
  private static Color TEXT_PANEL_COLOR = new Color(0, 0, 0, 80);

  private final IGameModel model;
  private GhostsPanel ghostsPanel;
  private PacManPanel pacmanPanel;
  private LevelPanel levelPanel;
  private TextPanel pausePanel;
  private TextPanel gameOverPanel;
  private TextPanel levelCompletedPanel;
  private GameMenuPanel gameMenu;
  private AudioMenuPanel audioMenu;

  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 800;
  private static final String GAME_TITLE = "Pac-Man";
  private static final String PAUSE_TEXT = "PAUSE";
  private static final String GAMEOVER_TEXT = "GAME OVER";



  private JLayeredPane layeredPane = new JLayeredPane();

  private JFrame window = new JFrame(GAME_TITLE);

  GameCanvas(IGameModel model, int ghostSpriteTogglePeriod, int pacmanSpriteTogglePeriod) {
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

    window.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        model.quit();
      }
    });

    gameMenu = new GameMenuPanel(model);
    gameMenu.setVisible(false);

    audioMenu = new AudioMenuPanel(model);
    audioMenu.setVisible(false);

    levelPanel = new LevelPanel(model);
    levelPanel.setBackground(Color.BLACK);

    ghostsPanel = new GhostsPanel(model, ghostSpriteTogglePeriod);
    ghostsPanel.setOpaque(false);

    pacmanPanel = new PacManPanel(model, pacmanSpriteTogglePeriod);
    pacmanPanel.setOpaque(false);

    layeredPane.add(levelPanel, Integer.valueOf(1));
    layeredPane.add(ghostsPanel, Integer.valueOf(2));
    layeredPane.add(pacmanPanel, Integer.valueOf(3));

    window.add(layeredPane);
    window.add(gameMenu);
    window.add(audioMenu);
    window.add(this);
    window.setVisible(true);
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    levelPanel.addKeyListener(keyListener);
  }

  @Override
  public void paint(Graphics graphic) {
    GameState gameState = model.getGameState();

    switch (gameState) {
      case GAME_MENU:
        paintMenu(graphic, gameMenu);
        break;
      case AUDIO_MENU:
        paintMenu(graphic, audioMenu);
        break;
      case GAME:
        paintGame(graphic);
        break;
      default:
        break;
    }
  }

  private void showComponent(Component panel) {
    List<Component> components = new ArrayList<>();
    components.add(audioMenu);
    components.add(gameMenu);
    components.add(layeredPane);

    for (Component component : components) {
      if (component != panel) {
        component.setVisible(false);
      }
    }

    panel.setVisible(true);
  }

  private void paintMenu(Graphics graphic, AbstractMenuPanel menuPanel) {
    menuPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
    final int pixelTileSize = getPixelTileSize();
    menuPanel.setBackground(Color.BLACK);
    menuPanel.setOffsetX(getOffsetX());
    menuPanel.setOffsetY(getOffsetY());
    menuPanel.setPixelTileSize(pixelTileSize);
    menuPanel.paint(graphic);;
    showComponent(menuPanel);
  }

  private void paintGame(Graphics graphic) {
    levelPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
    ghostsPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
    pacmanPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
    final int pixelTileSize = getPixelTileSize();
    levelPanel.setOffsetX(getOffsetX());
    levelPanel.setOffsetY(getOffsetY());
    levelPanel.setPixelTileSize(pixelTileSize);
    levelPanel.requestFocusInWindow();
    levelPanel.paint(graphic);
    ghostsPanel.setOffsetX(getOffsetX());
    ghostsPanel.setOffsetY(getOffsetY());
    ghostsPanel.setPixelTileSize(pixelTileSize);
    ghostsPanel.paint(graphic);
    pacmanPanel.setOffsetX(getOffsetX());
    pacmanPanel.setOffsetY(getOffsetY());
    pacmanPanel.setPixelTileSize(pixelTileSize);
    pacmanPanel.paint(graphic);
    showComponent(layeredPane);

    if (model.isLevelCompleted()) {
      setLevelCompletedPanel();
      levelCompletedPanel.setPixelTileSize(pixelTileSize);
      levelCompletedPanel.setOffsetX(getOffsetX());
      levelCompletedPanel.setOffsetY(getOffsetY());
      levelCompletedPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
      levelCompletedPanel.paint(graphic);
    } else {
      levelCompletedPanel = null;
    }
    if (model.isManuallyPaused()) {
      setPausePanel();
      pausePanel.setBounds(0, 0, window.getWidth(), window.getHeight());
      pausePanel.setOffsetX(getOffsetX());
      pausePanel.setOffsetY(getOffsetY());
      pausePanel.setPixelTileSize(pixelTileSize);
      pausePanel.paint(graphic);
    } else {
      if (pausePanel != null) {
        removePausePanel();
      }
    }
    if (model.isGameOver()) {
      setGameOverPanel();
      gameOverPanel.setPixelTileSize(pixelTileSize);
      gameOverPanel.setOffsetX(getOffsetX());
      gameOverPanel.setOffsetY(getOffsetY());
      gameOverPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
      gameOverPanel.paint(graphic);
    }
  }

  public void dispose() {
    window.dispose();
  }

  public int getPixelTileSize() {
    final int width = model.getCurrentLevel().getWidth();
    final int height =
        (int) (model.getCurrentLevel().getHeight() / RATIO_LEVEL_HEIGHT_TO_TOTAL_HEIGHT);
    final float panelWidthPixels = getWidth();
    final float panelHeightPixels = getHeight();
    final double availableWindowWidthPixels =
        Math.min(panelWidthPixels, 3.0 / 4.0 * panelHeightPixels);
    final double availableWindowHeightPixels =
        (int) Math.min(panelHeightPixels, 4.0 / 3.0 * panelWidthPixels);
    final double widthRatio = availableWindowWidthPixels / width;
    final double heightRatio = availableWindowHeightPixels / height;
    return (int) Math.min(Math.floor(widthRatio), Math.floor(heightRatio));
  }

  public int getOffsetX() {
    final int width = model.getCurrentLevel().getWidth();
    final int levelWidthPixels = width * getPixelTileSize();
    return (getWidth() - levelWidthPixels) / 2;
  }

  public int getOffsetY() {
    final int height =
        (int) (model.getCurrentLevel().getHeight() / RATIO_LEVEL_HEIGHT_TO_TOTAL_HEIGHT);
    final int levelHeightPixels = height * getPixelTileSize();
    return (getHeight() - levelHeightPixels) / 2;
  }

  public void setPausePanel() {
    pausePanel = new TextPanel(model, PAUSE_TEXT);
    pausePanel.setBackground(TEXT_PANEL_COLOR);
    pausePanel.setOpaque(true);
    layeredPane.add(pausePanel, Integer.valueOf(1));
  }

  public void setLevelCompletedPanel() {
    if (levelCompletedPanel != null) {
      return;
    }

    final int levelNumberOffset = 2;
    final String levelText = "Level " + model.getCurrentLevelIndex() + levelNumberOffset;

    levelCompletedPanel = new TextPanel(model, levelText);
    levelCompletedPanel.setBackground(TEXT_PANEL_COLOR);
    levelCompletedPanel.setOpaque(true);
    layeredPane.add(levelCompletedPanel, Integer.valueOf(1));
  }

  public void setGameOverPanel() {
    gameOverPanel = new TextPanel(model, GAMEOVER_TEXT);
    gameOverPanel.setBackground(TEXT_PANEL_COLOR);
    gameOverPanel.setOpaque(true);
    layeredPane.add(gameOverPanel, Integer.valueOf(1));

  }

  public void removePausePanel() {
    layeredPane.remove(pausePanel);
    pausePanel = null;
  }
}
