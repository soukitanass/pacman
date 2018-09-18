package view;

import java.awt.Graphics;

import javax.swing.JPanel;

import model.IGameModel;
import model.Level;

public class ScorePanel extends JPanel{
		private IGameModel model;
	

	  public ScorePanel(IGameModel model) {
	    this.model = model;
	    
	  }

	  @Override
	  public void paint(Graphics graphic) {
	    super.paint(graphic);
	    final Level level = this.model.getCurrentLevel();
	    //draw text and score
	    
	  }
}
