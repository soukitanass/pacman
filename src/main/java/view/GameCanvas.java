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
    private JButton fullScr;
    private PacManView pacmanView;
    
    // Constant variables
    public static final int FRAMEWIDTH = 800;
    public static final int FRAMEHEIGHT = 800;
    public static final String GAMETITLE = "Pac-Man";
    public static final String TEXTFULL = "FullScreen";
    public static final String TEXTREDUCE = "Reduce";

    private JFrame window = new JFrame(TEXTFULL);
    
    GameCanvas(IGameModel model) {
        super();
        pacmanView = new PacManView(model);
        
        // Setting the frame parameters
        window.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating a toolbar for fullscreen option
        toolbar = new JToolBar();
        fullScr = new JButton(TEXTFULL);

        // Adding a listener to the fullScreen button
        fullScr.addActionListener(action -> new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (window.getWidth() == FRAMEWIDTH) {
                    window.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    fullScr.setText(TEXTREDUCE);
                } else {
                    fullScr.setText(TEXTFULL);
                    window.setSize(FRAMEWIDTH, FRAMEHEIGHT);
                }
            }

        });
        toolbar.add(fullScr);
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
    fullScr.addKeyListener(keyListener);
  }

    public void paint(Graphics graphic)
    {
        super.paint(graphic);
        pacmanView.paint(graphic);
    }

    //Le chargement du labyrinthe
    public void addLabyrinth() { }
}
