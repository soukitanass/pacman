/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Random;
import org.junit.Test;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.RandomDirectionGenerator;

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
