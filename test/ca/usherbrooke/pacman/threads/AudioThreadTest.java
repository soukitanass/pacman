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
  public void changeSoundVolumeTest() throws InterruptedException  {
    int expectedVolume = 10;
    audioThread.setSoundVolumeChanged(expectedVolume);
    Thread.sleep(THREAD_SLEEP);
    int actualVolume = audioThread.getSoundVolume();
    audioThread.setStop();
    assertEquals(expectedVolume, actualVolume);
  }

  @Test
  public void changeMusicVolumeTest() throws InterruptedException {
    int expectedVolume = 10;
    audioThread.setMusicVolumeChanged(expectedVolume);
    Thread.sleep(THREAD_SLEEP);
    int actualVolume = audioThread.getMusicVolume();
    assertEquals(expectedVolume, actualVolume);
  }

  @Test
  public void muteSoundTest() throws InterruptedException  {
    boolean expected = true;
    audioThread.setTheSoundPlay(expected);
    Thread.sleep(THREAD_SLEEP);
    boolean actual = audioThread.isSoundPlaying();
    assertEquals(expected, actual);
  }

  @Test
  public void muteMusicTest() throws InterruptedException {
    boolean expected = true;
    audioThread.setTheMusicPlay(expected);
    Thread.sleep(THREAD_SLEEP);
    boolean actual = audioThread.isMusicPlay();
    assertEquals(expected, actual);
  }


}
