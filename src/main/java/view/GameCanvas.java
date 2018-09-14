package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class GameCanvas extends JFrame {

	// Toolbar variables
	private JToolBar toolbar;
	private final JButton fullScr;

	// Constant variables
	private final int frameWidth = 800;
	private final int frameHeight = 800;
	private final String gameTitle = "Pac-Man";
	private final String textFull = "FullScreen";
	private final String textReduce = "Reduce";

	GameCanvas() {
		super();
		// Setting the frame parameters
		setTitle(gameTitle);
		setSize(frameWidth, frameHeight);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creating a toolbar for fullscreen option
		toolbar = new JToolBar();
		fullScr = new JButton(textFull);

		// Adding a listener to the fullScreen button
		fullScr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getWidth() == frameWidth) {
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					fullScr.setText(textReduce);
				} else {
					fullScr.setText(textFull);
					setSize(frameWidth, frameHeight);
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

	}
}
