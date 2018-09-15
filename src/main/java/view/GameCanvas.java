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
  private PacManView pacmanView;
  private LevelView levelView;

  // Constant variables
  public static final int FRAME_WIDTH = 600;
  public static final int FRAME_HEIGHT = 800;
  public static final String GAME_TITLE = "Pac-Man";
  public static final String TEXT_FULL = "Full Screen";
  public static final String TEXT_REDUCE = "Reduce";

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
    window.add(this);
    window.add(toolbar, BorderLayout.NORTH);

    final int pixelRatio = getPixelTileSize(model);
    pacmanView = new PacManView(model);
    levelView = new LevelView(model, pixelRatio);

    // Add the frame content
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
    levelView.paint(graphic, window);
    pacmanView.paint(graphic, window);
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
