package ca.usherbrooke.pacman.model.random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Random;
import org.junit.Test;
import ca.usherbrooke.pacman.model.Direction;

public class RandomDirectionGeneratorTest {

  @Test
  public void generatesRandomDirections() {
    Random randomNumberGenerator = mock(Random.class);
    when(randomNumberGenerator.nextInt(4)).thenReturn(0).thenReturn(1).thenReturn(2).thenReturn(3);
    RandomDirectionGenerator generator = new RandomDirectionGenerator(randomNumberGenerator);

    assertEquals(Direction.UP, generator.get());
    assertEquals(Direction.DOWN, generator.get());
    assertEquals(Direction.LEFT, generator.get());
    assertEquals(Direction.RIGHT, generator.get());
  }

}
