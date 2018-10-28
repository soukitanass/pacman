package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;

public class AudioThreadTest {

  private static final long THREAD_SLEEP = 100;
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
      audioThread.stopThread();
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
  public void changeSoundVolumeTest() throws InterruptedException {
    int expectedVolume = 10;
    audioThread.setSoundVolume(expectedVolume);
    Thread.sleep(THREAD_SLEEP);
    int actualVolume = audioThread.getSoundVolume();
    audioThread.stopThread();
    assertEquals(expectedVolume, actualVolume);
  }

  @Test
  public void changeMusicVolumeTest() throws InterruptedException {
    int expectedVolume = 10;
    audioThread.setMusicVolume(expectedVolume);
    Thread.sleep(THREAD_SLEEP);
    int actualVolume = audioThread.getMusicVolume();
    assertEquals(expectedVolume, actualVolume);
  }
}
