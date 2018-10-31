/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2029 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.MockLevelFactory;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PinkyDirectionGeneratorTest {
  private static final int RANDOM_GENERATOR_SEED = 8544574;
  private Random randomNumberGenerator = new Random(RANDOM_GENERATOR_SEED);
  private IDirectionGenerator randomDirectionGenerator =
      new RandomDirectionGenerator(randomNumberGenerator);

  private PinkyDirectionGenerator pinkyDirectionGenerator;
  private Ghost ghost;
  private List<Ghost> ghosts = new ArrayList<>();
  private PacMan pacman;

  @Before
  public void setUp() {
    ghost = new Ghost();
    ghosts.add(ghost);
    pacman = new PacMan();

    Level level = MockLevelFactory.getMockLevelWithDifferentWallCombinationSurroundedByWall();
    level.setPacMan(pacman);
    level.setGhosts(ghosts);

    pinkyDirectionGenerator = new PinkyDirectionGenerator(randomDirectionGenerator, ghost, level);
  }

  @Test
  public void overridenDirectionDontDetectPacmanSoItAlwaysReturnNull() {

    ghost.setPosition(new Position(1, 1));
    pacman.setPosition(new Position(3, 3));
    assertNull(pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(3, 1));
    pacman.setPosition(new Position(1, 3));
    assertNull(pinkyDirectionGenerator.getOverridenDirection());
  }

  @Test
  public void overridenDirectionDetectPacmanSoItReturnTheDirectionToPacman() {
    ghost.setPosition(new Position(2, 1));
    pacman.setPosition(new Position(2, 3));
    assertEquals(Direction.DOWN, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(1, 1));
    pacman.setPosition(new Position(3, 1));
    assertEquals(Direction.RIGHT, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(3, 1));
    pacman.setPosition(new Position(1, 1));
    assertEquals(Direction.LEFT, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(2, 3));
    pacman.setPosition(new Position(2, 1));
    assertEquals(Direction.UP, pinkyDirectionGenerator.getOverridenDirection());
  }

  @Test
  public void directionGeneratorDetectPacmanThroughTheWallSoItReturnTheDirectionToReachPacman() {

    ghost.setPosition(new Position(1, 1));
    pacman.setPosition(new Position(1, 3));
    assertEquals(Direction.RIGHT, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(1, 3));
    pacman.setPosition(new Position(1, 1));
    assertEquals(Direction.RIGHT, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(7, 1));
    pacman.setPosition(new Position(7, 3));
    assertEquals(Direction.LEFT, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(7, 3));
    pacman.setPosition(new Position(7, 0));
    assertEquals(Direction.LEFT, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(6, 3));
    pacman.setPosition(new Position(4, 3));
    assertEquals(Direction.UP, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(4, 3));
    pacman.setPosition(new Position(6, 3));
    assertEquals(Direction.UP, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(2, 1));
    pacman.setPosition(new Position(4, 1));
    assertEquals(Direction.DOWN, pinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(4, 1));
    pacman.setPosition(new Position(2, 1));
    assertEquals(Direction.DOWN, pinkyDirectionGenerator.getOverridenDirection());
  }
}
