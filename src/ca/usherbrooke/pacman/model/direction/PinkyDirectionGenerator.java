/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ca.usherbrooke.pacman.model.movements.astar.AStar;
import ca.usherbrooke.pacman.model.movements.astar.Node;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.view.states.PacGumState;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PinkyDirectionGenerator implements IDirectionGenerator {
  private static final int RANDOM_GENERATOR_SEED = 8544574;
  Random randomNumberGenerator = new Random(RANDOM_GENERATOR_SEED);
  IDirectionGenerator randomDirectionGenerator =
      new RandomDirectionGenerator(randomNumberGenerator);

  private PacMan pacman;
  private Ghost ghost;
  private Level level;

  public PinkyDirectionGenerator(Ghost ghost, Level level) {
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
      for (Node node : path) {
          System.out.println(node);
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
  
  private List<Position> getObstacleList()
  {
    List<Position> obstacleList = new ArrayList<>();
    List<List<Integer>> map = level.getMap();
    for (int i = 0; i < map.size(); i++) {
      List<Integer> row = map.get(i);
      for (int j = 0; j < row.size(); j++) {
        int code = row.get(j);
        if (code != 39 || code != 40 || code != 0) {
          obstacleList.add(new Position(j, i));
        }
      }
    }
    return obstacleList;
  }
  
}
