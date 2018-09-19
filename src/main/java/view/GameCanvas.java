package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

  // Constant variables
  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 800;
  private static final String GAME_TITLE = "Pac-Man";


  private final FlowLayout layoutCenter = new FlowLayout(FlowLayout.CENTER);
  private JLayeredPane layeredPane = new JLayeredPane();

  private JFrame window = new JFrame(GAME_TITLE);

  GameCanvas(IGameModel model) {
    this.model = model;

    // Setting the frame parameters
    window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    window.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.getContentPane().setBackground(Color.BLACK);

    // Add level panel
    levelPanel = new LevelPanel(model);
    levelPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
    levelPanel.setLayout(layoutCenter);
    levelPanel.setBackground(Color.BLACK);
    layeredPane.add(levelPanel, JLayeredPane.DEFAULT_LAYER);


    // Add Pac-Man panel
    pacmanPanel = new PacManPanel(model);
    pacmanPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
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
