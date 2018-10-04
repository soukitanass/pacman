/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.random;

import java.util.Random;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IDirectionGenerator;

public class RandomDirectionGenerator implements IDirectionGenerator {

  private Random randomNumberGenerator;
  private static final Direction[] DIRECTIONS =
      {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

  public RandomDirectionGenerator(Random randomNumberGenerator) {
    this.randomNumberGenerator = randomNumberGenerator;
  }

  @Override
  public Direction get() {
    final int exclusiveMaxBound = 4;
    final int randomIntFrom0To3 = randomNumberGenerator.nextInt(exclusiveMaxBound);
    return DIRECTIONS[randomIntFrom0To3];
  }

}
