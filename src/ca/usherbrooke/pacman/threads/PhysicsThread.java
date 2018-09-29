package ca.usherbrooke.pacman.threads;


import java.util.concurrent.atomic.AtomicBoolean;
import ca.usherbrooke.pacman.model.Level;
import ca.usherbrooke.pacman.model.PacMan;
import ca.usherbrooke.pacman.model.Position;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PhysicsThread extends Thread {
  private volatile boolean isRunning = false;
  private static final int SLEEP_TIME = 10;

  private AtomicBoolean isPacgumConsumed = new AtomicBoolean();
  private AtomicBoolean isSuperPacgumConsumed = new AtomicBoolean();
  
  private Level level;
  private PacMan pacman;
  
  public PhysicsThread(Level level) {
    this.setName("Physic_Thread");
    
    this.level = level;
    this.pacman = level.getPacMan();
  }

  public synchronized void stopThread() {
    isRunning = false;
  }

  @Override
  public void run() {
    isRunning = true;
    System.out.println("START - " + this.getName());

    while (isRunning) {
      try {
        setIsPacgumConsumed();
        setIsSuperPacgumConsumed();
        
        Thread.sleep(SLEEP_TIME);

      } catch (InterruptedException exception) {
        WarningDialog.display("Interrupt error in" + this.getName(), exception);
      }
    }
    System.out.println("STOP - " + this.getName());
  }

  public boolean isPacgumConsumed() {
    return isPacgumConsumed.get();
  }
  
  public boolean isSuperPacgumConsumed() {
    return isSuperPacgumConsumed.get();
  }
  
  private void setIsPacgumConsumed() {
    Position position = pacman.getPosition();
    if (!level.isPacgum(position)) {
      isPacgumConsumed.set(false);
    } else {
      isPacgumConsumed.set(true);
    }
  }
  
  private void setIsSuperPacgumConsumed() {
    Position position = pacman.getPosition();
    if (!level.isSuperPacgum(position)) {
      isSuperPacgumConsumed.set(false);
    } else {
      isSuperPacgumConsumed.set(true);
    }
  }
}
