package ca.usherbrooke.pacman.game;

import ca.usherbrooke.pacman.threads.GameThread;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class Game  {
  private static GameThread gameThread;

  public static void main(String[] args) {
    gameThread = new GameThread();
    gameThread.setName("Game_Thread");
    gameThread.start();
    try {
      gameThread.join();
    } catch (InterruptedException e) {
      gameThread.interrupt();
      WarningDialog.display("An error occured when waiting for the view to stop", e);
    }
  }

  
}
