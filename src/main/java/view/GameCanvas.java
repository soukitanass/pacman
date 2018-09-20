package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import model.IGameModel;

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
    levelPanel.setOffsetX(getOffsetX());
    levelPanel.setOffsetY(getOffsetY());
    levelPanel.setPixelTileSize(pixelTileSize);
    levelPanel.paint(graphic);
    pacmanPanel.setOffsetX(getOffsetX());
    pacmanPanel.setOffsetY(getOffsetY());
    pacmanPanel.setPixelTileSize(pixelTileSize);
    pacmanPanel.paint(graphic);
  }

  public void dispose() {
    window.dispose();
  }

  public int getPixelTileSize() {
    final float panelWidthPixels = getWidth();
    final float panelHeightPixels = getHeight();
    final int availableWindowWidthPixels =
        (int) Math.min(panelWidthPixels, 3.0 / 4.0 * panelHeightPixels);
    final int availableWindowHeightPixels =
        (int) Math.min(panelHeightPixels, 4.0 / 3.0 * panelWidthPixels);
    final float widthRatio = availableWindowWidthPixels / levelPanel.getWidthTiles();
    final float heightRatio = availableWindowHeightPixels / levelPanel.getHeightTiles();
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
}
