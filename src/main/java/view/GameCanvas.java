package view;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class GameCanvas extends JFrame {
	
	//Toolbar variables
	private JToolBar toolbar;
	private final JButton fullScr;
	
	//Constant variables
	private final int FrameWidth = 800;
	private final int FrameHeight = 800;
	private String GameTitle = "Pac-Man";
	private String TextFull = "FullScreen";
	private String TextReduce = "Reduce";
	
	GameCanvas(){
		super();
		//Setting the frame parameters
	    setTitle(GameTitle);
	    setSize(FrameWidth, FrameHeight);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Creating a toolbar for fullscreen option
	    toolbar = new JToolBar();
	    fullScr = new JButton(TextFull);
	    
	    //Adding a listener to the fullScreen button
	    fullScr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(getWidth() == FrameWidth) {
					
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					fullScr.setText(TextReduce);
					
				}else {
					
					fullScr.setText(TextFull);
					setSize(FrameWidth, FrameHeight);
					
				}
				
			}
	    	
	    });
	    toolbar.add(fullScr);
	    toolbar.setFloatable(false);
	    add(toolbar,BorderLayout.NORTH);
	    
	    //Add the frame content
	    addLabyrinth();
	 
	    setVisible(true);
    }   
	
	
	public void addLabyrinth() {
		
	}
}
