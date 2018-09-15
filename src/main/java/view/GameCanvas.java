package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class GameCanvas extends JFrame {

  // Constant variables
  public static final int FRAME_WIDTH = 800;
  public static final int FRAME_HEIGHT = 800;
  public static final String GAME_TITLE = "Pac-Man";
  public static final String TEXT_FULL = "fullScreeneen";
  public static final String TEXT_REDUCE = "Reduce";
  // Toolbar variables
  private JToolBar toolbar;
  private JButton fullScreen;

  GameCanvas() {
    super();
    // Setting the frame parameters
    setTitle(GAME_TITLE);
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Creating a toolbar for fullScreeneen option
    toolbar = new JToolBar();
    fullScreen = new JButton(TEXT_FULL);

    // Adding a listener to the fullScreeneen button
    fullScreen.addActionListener(action -> new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (getWidth() == FRAME_WIDTH) {
          setExtendedState(JFrame.MAXIMIZED_BOTH);
          fullScreen.setText(TEXT_REDUCE);
        } else {
          fullScreen.setText(TEXT_FULL);
          setSize(FRAME_WIDTH, FRAME_HEIGHT);
        }
      }

    });
    toolbar.add(fullScreen);
    toolbar.setFloatable(false);
    add(toolbar, BorderLayout.NORTH);

    // Add the frame content
    addLabyrinth();
    setVisible(true);
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    super.addKeyListener(keyListener);
    toolbar.addKeyListener(keyListener);
    fullScreen.addKeyListener(keyListener);
  }

  public void addLabyrinth() {
    // TODO: Load labyrinth
  }
}
