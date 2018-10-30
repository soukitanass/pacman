package ca.usherbrooke.pacman.mocktests;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
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
    JLabel label = view.getCanvas().getMenuPanel().getStartGameLabel();
    clickComponent(label);
  }

  private void clickPosition(final int x, final int y) throws InterruptedException {
    robot.mouseMove(x, y);
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

  public void clickAudio() throws InterruptedException {
    JLabel label = view.getCanvas().getMenuPanel().getAudioLabel();
    clickComponent(label);
  }

  public void clickAudioGoBack() throws InterruptedException {
    JLabel label = view.getCanvas().getAudioPanel().getGoBackLabel();
    clickComponent(label);
  }

  public void clickMuteMusicCheckbox() throws InterruptedException {
    JCheckBox checkbox = view.getCanvas().getAudioPanel().getMuteMusicCheckbox();
    clickComponent(checkbox);
  }

  private void clickComponent(JComponent component) throws InterruptedException {
    Point locationOnScreen = component.getLocationOnScreen();
    final int labelCenterX = locationOnScreen.x + component.getWidth() / 2;
    final int labelCenterY = locationOnScreen.y + component.getHeight() / 2;
    clickPosition(labelCenterX, labelCenterY);
  }

  public void clickMuteSoundCheckbox() throws InterruptedException {
    JCheckBox checkbox = view.getCanvas().getAudioPanel().getMuteSoundCheckbox();
    clickComponent(checkbox);
  }

  public void clickHighscores() throws InterruptedException {
    JLabel label = view.getCanvas().getMenuPanel().getHighscoresLabel();
    clickComponent(label);
  }

  public void clickHighscoreGoBack() throws InterruptedException {
    JLabel label = view.getCanvas().getHighscoresPanel().getGoBackLabel();
    clickComponent(label);
  }

  public void clickFramesPerSecondCheckbox() throws InterruptedException {
    JCheckBox checkbox = view.getCanvas().getMenuPanel().getFramesPerSecondCheckbox();
    clickComponent(checkbox);
  }

  public void setFramesPerSecond(int framesPerSecond) throws InterruptedException {
    JSpinner fpsSpinner = view.getCanvas().getMenuPanel().getFpsSpinner();
    setSpinnerValue(fpsSpinner, framesPerSecond);
  }

  private void setSpinnerValue(JSpinner spinner, int newValue) throws InterruptedException {
    spinner.setValue(newValue);
    sleep();
  }

}
