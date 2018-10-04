package ca.usherbrooke.pacman.game;

import ca.usherbrooke.pacman.threads.GameThread;

public class Game {
  private static GameThread gameThread;

  public static void main(String[] args) {
    gameThread = new GameThread();
    gameThread.setName("Game_Thread");
    gameThread.start();
  }
}
