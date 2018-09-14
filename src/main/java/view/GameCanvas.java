import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class GameCanvas extends JFrame {
	
	private JToolBar jb;
	private final JButton full;
	
	GameCanvas(){
		super();
	    setTitle("Pac-Man");
	    setSize(800, 600);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jb = new JToolBar();
	    full = new JButton("FullScreen");
	    full.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(getWidth() == 800) {
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					full.setText("Reduce");
				}else {
					full.setText("FullScreen");
					setSize(800, 600);
					
				}
				
			}
	    	
	    });
	    jb.add(full);
	    jb.setFloatable(false);
	    add(jb,BorderLayout.NORTH);
	    
	    addLabyrinth();
	    setVisible(true);
    }   
	
	/*public static void main(String [ ] args) {
		new GameCanvas();
	}*/
	public void addLabyrinth() {
		
	}
}
