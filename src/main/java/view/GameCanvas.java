package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import model.IGameModel;
import model.Level;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel {
  private final IGameModel model;
  private PacManPanel pacmanPanel;
  private LevelPanel levelPanel;

  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 800;
  private static final String GAME_TITLE = "Pac-Man";

  private JLayeredPane layeredPane = new JLayeredPane();

  private JFrame window = new JFrame(GAME_TITLE);

  GameCanvas(IGameModel model) {
    this.model = model;

    window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    window.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    levelPanel = new LevelPanel(model);
    levelPanel.setBackground(Color.BLACK);
    levelPanel.setOpaque(true);
    layeredPane.add(levelPanel, Integer.valueOf(1));

    pacmanPanel = new PacManPanel(model);
    pacmanPanel.setOpaque(false);
    layeredPane.add(pacmanPanel, Integer.valueOf(2));

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
    pacmanPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
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
    final float widthRatio = (float) (getWidth()) / level.getWidth();
    final float heightRatio = (float) (getHeight()) / level.getHeight();
    return (int) Math.min(Math.floor(widthRatio), Math.floor(heightRatio));
  }
}
