package ca.usherbrooke.pacman.mocktests;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import javax.swing.JLabel;
import ca.usherbrooke.pacman.view.GameView;

public class MockTestController {

  private int sleepTimeBetweenActionsMilliseconds = 100;
  private GameView view;
  private Robot robot;

  public MockTestController(GameView view, int sleepTimeBetweenActionsMilliseconds)
      throws AWTException {
    this.view = view;
    this.sleepTimeBetweenActionsMilliseconds = sleepTimeBetweenActionsMilliseconds;
    robot = new Robot();
  }

  public void tapKey(int key) throws InterruptedException {
    robot.keyPress(key);
    robot.keyRelease(key);
    sleep();
  }

  public void clickStartOrResumeGame() throws InterruptedException {
    JLabel startGameLabel = view.getCanvas().getStartGameLabel();
    clickLabel(startGameLabel);
  }

  private void clickLabel(JLabel label) throws InterruptedException {
    Point labelLocationOnScreen = label.getLocationOnScreen();
    final int labelCenterX = labelLocationOnScreen.x + label.getWidth() / 2;
    final int labelCenterY = labelLocationOnScreen.y + label.getHeight() / 2;
    robot.mouseMove(labelCenterX, labelCenterY);
    robot.mousePress(InputEvent.BUTTON1_MASK);
    robot.mouseRelease(InputEvent.BUTTON1_MASK);
    sleep();
  }

  private void sleep() throws InterruptedException {
    Thread.sleep(sleepTimeBetweenActionsMilliseconds);
  }

  public void close() {
    view.close();
  }

}
