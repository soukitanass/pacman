package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;

public class AudioThreadTest {
  
  private AudioThread audioThread;
  private static IGameModel model;
  
  @Before
  public void setUp() throws Exception {
    model = new GameModel();
    audioThread = new AudioThread(model);
    audioThread.setName("Audio_Thread");
    audioThread.start();
    audioThread.join(100);
    assertTrue("thread dead", audioThread.isAlive());
  }

  @After
  public void tearDown() {
    boolean expectedStop = true;
    boolean actualStop = audioThread.isAlive();
    if (actualStop) {
      audioThread.setStop();
      try {
        audioThread.join(1500);
        actualStop = !audioThread.isAlive();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      assertEquals("Thread not stopped", expectedStop, actualStop);
    }
  }
 
  @Test
  public void test() {
    //TODO
  }
}
