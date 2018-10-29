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
      Position ghostPosition = ghost.getPosition();
      Position pacmanPosition = pacman.getPosition();
      Node initialNode = new Node(ghostPosition.getX(), ghostPosition.getY());
      Node finalNode = new Node(pacmanPosition.getX(), pacmanPosition.getY());
      int rows = level.getWidth();
      int cols = level.getHeight();
      AStar aStar = new AStar(rows, cols, initialNode, finalNode);
      aStar.setBlocks(getObstacleList());
      List<Node> path = aStar.findPath();
      if (!path.isEmpty() && path != null) {
        Node nextNode = path.get(0);
        Position nextPosition = new Position(nextNode.getRow(), nextNode.getCol());
        direction = level.getDirectionIfInLineOfSight(ghost.getPosition(), nextPosition);
        System.out.println(direction);
      }
    }

    if (direction == null) {
      direction = randomDirectionGenerator.get();
    }
    return direction;
  }

  @Override
  public Direction getOverridenDirection() {
    return getPacmanDirectionIfInLineOfSight();
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
