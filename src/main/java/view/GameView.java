package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Direction;
import model.IGameModel;

public class GameView implements IGameView {
    private final IGameModel model;

    public GameView(IGameModel model) {
        this.model = model;
        this.display();
    }

    private void display() {
        JFrame jFrame = new JFrame("PacMan");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.add(new GamePanel());
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    public void update() {

    }
    
    @SuppressWarnings("serial")
    private static class GamePanel extends JPanel implements KeyListener {
        public GamePanel() {
            this.addKeyListener(this);
            this.setFocusable(true);
            this.requestFocusInWindow();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                System.out.println(Direction.RIGHT);
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                System.out.println(Direction.LEFT);
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP) {
                System.out.println(Direction.UP);
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println(Direction.DOWN);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}
