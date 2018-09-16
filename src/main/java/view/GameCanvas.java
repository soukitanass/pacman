package view;

import javax.swing.*;
import model.IGameModel;
import model.Level;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class GameCanvas extends JPanel {

  // Toolbar variables
  private JToolBar toolbar;
  private JButton fullScreen;
  private PacManPanel pacmanPanel;
  private LevelPanel levelPanel;

  // Constant variables
  private final int FRAME_WIDTH = 600;
  private final int FRAME_HEIGHT = 800;
  private final String GAME_TITLE = "Pac-Man";
  private final String TEXT_FULL = "Full Screen";
  private final String TEXT_REDUCE = "Reduce";

  private final FlowLayout layoutCenter = new FlowLayout(FlowLayout.CENTER);
  private JLayeredPane layeredPane = new JLayeredPane();
  private JFrame window = new JFrame(GAME_TITLE);

  GameCanvas(IGameModel model) {
    super();

    // Setting the frame parameters

    window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    window.setResizable(false);
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Creating a toolbar for fullscreen option
    toolbar = new JToolBar();
    fullScreen = new JButton(TEXT_FULL);

    // Adding a listener to the fullscreen button
    fullScreen.addActionListener(action -> new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (window.getWidth() == FRAME_WIDTH) {
          window.setExtendedState(JFrame.MAXIMIZED_BOTH);
          fullScreen.setText(TEXT_REDUCE);
        } else {
          fullScreen.setText(TEXT_FULL);
          window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        }
      }
    });
    toolbar.add(fullScreen);
    toolbar.setFloatable(false);
    window.add(toolbar, BorderLayout.NORTH);

    final int pixelRatio = getPixelTileSize(model);

    // Add level panel
    levelPanel = new LevelPanel(model, pixelRatio);
    levelPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
    levelPanel.setLayout(layoutCenter);
    layeredPane.add(levelPanel, JLayeredPane.DEFAULT_LAYER);

    // Add Pac-Man panel
    pacmanPanel = new PacManPanel(model, pixelRatio);
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
    super.addKeyListener(keyListener);
    toolbar.addKeyListener(keyListener);
    fullScreen.addKeyListener(keyListener);
  }

  public void paint(Graphics graphic) {
    super.paint(graphic);
    levelPanel.paint(graphic);
    pacmanPanel.paint(graphic);
  }

  public void dispose() {
    window.dispose();
  }

  public int getPixelTileSize(IGameModel model) {
    final Level level = model.getCurrentLevel();
    final float widthRatio = window.getWidth() / level.getWidth();
    final float heightRatio = window.getHeight() / level.getHeight();
    return (int) Math.min(Math.floor(widthRatio), Math.floor(heightRatio));
  }
}
