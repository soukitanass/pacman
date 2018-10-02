package ca.usherbrooke.pacman.model;

import java.util.ArrayList;
import java.util.List;

public class PacmanGhostCollisionManager {
  private final PacMan pacman;
  private final Level level;
  private IGameModel model = new GameModel();
  private final String LEVELS_PATH = "Levels.json";
  private List<Position> listPositions;

  public PacmanGhostCollisionManager( Level level) {
    model.loadLevels(LEVELS_PATH);
    listPositions = new ArrayList<Position>();
    this.pacman = level.getPacMan();
    this.level = level;
  }

  public void update() {
    level.setLives(level.getLives() - 1);
    pacman.setPosition(loadPacmanInitialPosition());
    pacman.setDirection(Direction.LEFT);
    loadGhostInitialPosition();
    int i = 0;
    for (Ghost ghost : level.getGhosts()) {
      ghost.setPosition(listPositions.get(i));
      i++;
    }
  }
  
  private Position loadPacmanInitialPosition() {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    return pacman.getPosition();
  }
  private List<Position> loadGhostInitialPosition() {
    for (Ghost ghost : model.getCurrentLevel().getGhosts()) {
      listPositions.add(ghost.getPosition());
    }
    return listPositions;
  }
}
