package ca.usherbrooke.pacman.model.direction;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.TimeGetter;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.MockLevelFactory;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class InkyDirectionGeneratorTest {

  private TimeGetter timeGetter = mock(TimeGetter.class);
  private IDirectionGenerator randomDirectionGenerator = mock(IDirectionGenerator.class);
  private InkyDirectionGenerator inkyDirectionGenerator;
  private Ghost ghost = new Ghost();
  private PacMan pacman = new PacMan();

  @Before
  public void setUp() {
    List<Ghost> ghosts = new ArrayList<>();
    ghosts.add(ghost);

    Level level = MockLevelFactory.getMockLevelThreeByThreeEmpty();
    level.setPacMan(pacman);
    level.setGhosts(ghosts);

    when(timeGetter.getMilliseconds()).thenReturn((long) 0);
    inkyDirectionGenerator =
        new InkyDirectionGenerator(randomDirectionGenerator, ghost, level, timeGetter);
  }

  @Test
  public void returnRandomDirectionIfNotInLineOfSight() {
    pacman.setPosition(new Position(1, 1));
    ghost.setPosition(new Position(2, 2));

    inkyDirectionGenerator.get();
    verify(randomDirectionGenerator, times(1)).get();

    inkyDirectionGenerator.get();
    verify(randomDirectionGenerator, times(2)).get();

    inkyDirectionGenerator.get();
    verify(randomDirectionGenerator, times(3)).get();

    inkyDirectionGenerator.get();
    verify(randomDirectionGenerator, times(4)).get();
  }

  @Test
  public void returnDirectionToFollowOrToRunAwayPacman() {
    pacman.setPosition(new Position(1, 1));
    ghost.setPosition(new Position(1, 3));

    Direction direction;

    direction = inkyDirectionGenerator.getOverridenDirection();
    assertEquals(direction, Direction.DOWN);

    direction = inkyDirectionGenerator.getOverridenDirection();
    assertEquals(direction, Direction.DOWN);

    when(timeGetter.getMilliseconds()).thenReturn((long) 2000);

    direction = inkyDirectionGenerator.getOverridenDirection();
    assertEquals(direction, Direction.UP);

    when(timeGetter.getMilliseconds()).thenReturn((long) 4000);

    direction = inkyDirectionGenerator.getOverridenDirection();
    assertEquals(direction, Direction.DOWN);
  }

}
