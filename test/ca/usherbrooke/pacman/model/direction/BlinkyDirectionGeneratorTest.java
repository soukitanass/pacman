/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
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

public class BlinkyDirectionGeneratorTest {
  private BlinkyDirectionGenerator blinkyDirectionGenerator;
  private Ghost ghost;
  private List<Ghost> ghosts = new ArrayList<>();
  private PacMan pacman;

  @Before
  public void setUp() {
    ghost = new Ghost();
    ghosts.add(ghost);

    pacman = new PacMan();
    pacman.setPosition(new Position(1, 1));

    Level level = MockLevelFactory.getMockLevelThreeByThreeEmpty();
    level.setPacMan(pacman);
    level.setGhost(ghosts);

    blinkyDirectionGenerator = new BlinkyDirectionGenerator(new Random(12345), ghost, level);
  }

  @Test
  public void overridenDirectionDontDetectPacman() {

    ghost.setPosition(new Position(0, 0));
    assertNull(blinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(3, 0));
    assertNull(blinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(0, 3));
    assertNull(blinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(3, 3));
    assertNull(blinkyDirectionGenerator.getOverridenDirection());
  }

  @Test
  public void overridenDirectionDetectPacman() {

    ghost.setPosition(new Position(1, 0));
    assertEquals(Direction.DOWN, blinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(0, 1));
    assertEquals(Direction.RIGHT, blinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(3, 1));
    assertEquals(Direction.LEFT, blinkyDirectionGenerator.getOverridenDirection());

    ghost.setPosition(new Position(1, 3));
    assertEquals(Direction.UP, blinkyDirectionGenerator.getOverridenDirection());
  }

  @Test
  public void directionGeneratorDetectPacman() {

    ghost.setPosition(new Position(1, 0));
    assertEquals(Direction.DOWN, blinkyDirectionGenerator.get());

    ghost.setPosition(new Position(0, 1));
    assertEquals(Direction.RIGHT, blinkyDirectionGenerator.get());

    ghost.setPosition(new Position(3, 1));
    assertEquals(Direction.LEFT, blinkyDirectionGenerator.get());

    ghost.setPosition(new Position(1, 3));
    assertEquals(Direction.UP, blinkyDirectionGenerator.get());
  }

  @Test
  public void ghostWasFollowingPacmanAndPacmanGoesUpSoHeDoesNotSeeHimAgainSoHeTriesToFindHimAndItsALittleBitSad() {

    ghost.setPosition(new Position(0, 1));
    pacman.setPosition(new Position(2, 1));
    pacman.setDesiredDirection(Direction.UP);
    assertEquals(Direction.RIGHT, blinkyDirectionGenerator.getOverridenDirection());

    pacman.setPosition(new Position(2, 0));
    assertEquals(Direction.RIGHT, blinkyDirectionGenerator.get());

    ghost.setPosition(new Position(1, 1));
    assertEquals(Direction.RIGHT, blinkyDirectionGenerator.get());

    ghost.setPosition(new Position(2, 1));
    pacman.setPosition(new Position(0, 0));
    assertEquals(Direction.UP, blinkyDirectionGenerator.get());

    ghost.setPosition(new Position(2, 0));
    pacman.setPosition(new Position(0, 0));
    assertEquals(Direction.LEFT, blinkyDirectionGenerator.get());
    // It's a beautiful ending and they both live happily ever after.
  }

  @Test
  public void afterLosingSightOfPacmanTheGostTryToGoToTheLastPositioHeSawPacman() {

    ghost.setPosition(new Position(0, 1));
    assertEquals(Direction.RIGHT, blinkyDirectionGenerator.getOverridenDirection());

    pacman.setPosition(new Position(1, 0));
    assertEquals(Direction.RIGHT, blinkyDirectionGenerator.get());
  }
}
