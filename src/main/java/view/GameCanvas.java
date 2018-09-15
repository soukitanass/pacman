package view;

import javax.swing.*;
import model.IGameModel;
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
  public static final int FRAME_WIDTH = 800;
  public static final int FRAME_HEIGHT = 800;
  public static final String GAME_TITLE = "Pac-Man";
  public static final String TEXT_FULL = "Full Screen";
  public static final String TEXT_REDUCE = "Reduce";

  private JFrame window = new JFrame(GAME_TITLE);

  GameCanvas(IGameModel model) {
    super();
    pacmanView = new PacManView(model);
    levelView = new LevelView(model);

    // Setting the frame parameters
    window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    window.setResizable(false);
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Creating a toolbar for fullScreeneen option
    toolbar = new JToolBar();
    fullScreen = new JButton(TEXT_FULL);

    // Adding a listener to the fullScreeneen button
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

    // Add the frame content
    addLabyrinth();
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

  public void addLabyrinth() {}

  public void dispose() {
    window.dispose();
  }
}
