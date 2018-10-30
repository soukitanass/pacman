/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction;

import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.model.movements.astar.AStar;
import ca.usherbrooke.pacman.model.movements.astar.Node;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PinkyDirectionGenerator implements IDirectionGenerator {
  private IDirectionGenerator randomDirectionGenerator;
  private PacMan pacman;
  private Ghost ghost;
  private Level level;

  public PinkyDirectionGenerator(IDirectionGenerator randomDirectionGenerator, Ghost ghost,
      Level level) {
    this.randomDirectionGenerator = randomDirectionGenerator;
    this.pacman = level.getPacMan();
    this.level = level;
    this.ghost = ghost;
  }

  @Override
  public Direction get() {
    Direction direction = getPacmanDirectionIfInLineOfSight();

    if (direction == null) {
      direction = randomDirectionGenerator.get();
    }
    return direction;
  }

  @Override
  public Direction getOverridenDirection() {
    Direction direction = getPacmanDirectionIfInLineOfSight();

    if (direction == null) {
      final Position ghostPosition = ghost.getPosition();
      final Position pacmanPosition = pacman.getPosition();
      final Node initialNode = new Node(ghostPosition.getX(), ghostPosition.getY());
      final Node finalNode = new Node(pacmanPosition.getX(), pacmanPosition.getY());
      final AStar aStar = new AStar(level.getWidth(), level.getHeight(), initialNode, finalNode);
      aStar.setBlocks(getObstacleList());
      List<Node> path = aStar.findPath();
      if (path.size() >= 2) {
        final Node nextNode1 = path.get(1);
        final Position nextPosition1 = new Position(nextNode1.getRow(), nextNode1.getCol());
        direction = level.getDirectionIfInLineOfSight(ghostPosition, nextPosition1);
      }
    }
    return direction;
  }

  private Direction getPacmanDirectionIfInLineOfSight() {
    return level.getDirectionIfInLineOfSight(ghost.getPosition(), pacman.getPosition());
  }

  private List<Position> getObstacleList() {
    List<Position> obstacleList = new ArrayList<>();
    List<List<Integer>> map = level.getMap();
    for (int i = 0; i < map.size(); i++) {
      List<Integer> row = map.get(i);
      for (int j = 0; j < row.size(); j++) {
        int code = row.get(j);
        if (code != 39 && code != 40 && code != 0) {
          obstacleList.add(new Position(j, i));
        }
      }
    }
    return obstacleList;
  }

}
