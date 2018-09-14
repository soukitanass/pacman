package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class GameCanvas extends JFrame {

    // Constant variables
    public static final int FRAMEWIDTH = 800;
    public static final int FRAMEHEIGHT = 800;
    public static final String GAMETITLE = "Pac-Man";
    public static final String TEXTFULL = "FullScreen";
    public static final String TEXTREDUCE = "Reduce";
    // Toolbar variables
    private JToolBar toolbar;
    private JButton fullScr;

    GameCanvas() {
        super();
        // Setting the frame parameters
        setTitle(GAMETITLE);
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating a toolbar for fullscreen option
        toolbar = new JToolBar();
        fullScr = new JButton(TEXTFULL);

        // Adding a listener to the fullScreen button
        fullScr.addActionListener(action -> new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getWidth() == FRAMEWIDTH) {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                    fullScr.setText(TEXTREDUCE);
                } else {
                    fullScr.setText(TEXTFULL);
                    setSize(FRAMEWIDTH, FRAMEHEIGHT);
                }
            }

        });
        toolbar.add(fullScr);
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
        fullScr.addKeyListener(keyListener);
    }

    public void addLabyrinth() {
        // TODO: Load labyrinth
    }
}
