package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import model.IGameModel;
import model.Level;

public class GameCanvas extends JPanel {

  private final IGameModel model;
  private PacManPanel pacmanPanel;
  private LevelPanel levelPanel;
  private ScorePanel scorePanel;

  // Constant variables
  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 800;
  private static final String GAME_TITLE = "Pac-Man";
  private static final double SCORE_PANEL_PERCENTAGE = 0.2;
  private static final double LEVEL_PANEL_PERCENTAGE = 0.8;

  private final FlowLayout layoutCenter = new FlowLayout(FlowLayout.CENTER);
  private JLayeredPane layeredPane = new JLayeredPane();

  private JFrame window = new JFrame(GAME_TITLE);

  GameCanvas(IGameModel model) {
    this.model = model;

    // Setting the frame parameters
    window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Add level panel
    levelPanel = new LevelPanel(model);
    levelPanel.setBounds(0, 0, FRAME_WIDTH, (int) (FRAME_HEIGHT * LEVEL_PANEL_PERCENTAGE));
    levelPanel.setLayout(layoutCenter);
    layeredPane.add(levelPanel, JLayeredPane.DEFAULT_LAYER);

    // Add score panel
    scorePanel = new ScorePanel(model);
    scorePanel.setBounds(0, (int) (FRAME_HEIGHT * LEVEL_PANEL_PERCENTAGE), FRAME_WIDTH,
        (int) (FRAME_HEIGHT * SCORE_PANEL_PERCENTAGE));
    scorePanel.setLayout(layoutCenter);
    layeredPane.add(scorePanel, JLayeredPane.DEFAULT_LAYER);

    // Add Pac-Man panel
    pacmanPanel = new PacManPanel(model);
    pacmanPanel.setBounds(0, 0, FRAME_WIDTH, (int) (FRAME_HEIGHT * LEVEL_PANEL_PERCENTAGE));
    pacmanPanel.setLayout(layoutCenter);
    pacmanPanel.setOpaque(false);
    layeredPane.add(pacmanPanel, Integer.valueOf(1));



    // Add the frame content
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
    final int pixelTileSize = getPixelTileSize();
    levelPanel.setPixelTileSize(pixelTileSize);
    levelPanel.paint(graphic);
    pacmanPanel.setPixelTileSize(pixelTileSize);
    pacmanPanel.paint(graphic);
  }

  public void dispose() {
    window.dispose();
  }

  public int getPixelTileSize() {
    final Level level = model.getCurrentLevel();
    final float widthRatio = getWidth() / level.getWidth();
    final float heightRatio = getHeight() / level.getHeight();
    return (int) Math.min(Math.floor(widthRatio), Math.floor(heightRatio));
  }
}
