/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.position.Position;

public class Level {
  private static final int EMPTY_CODE = 0;
  private static final int PACGUM_CODE = 39;
  private static final int SUPER_PACGUM_CODE = 40;
  private static final int WALL_CODE_MIN = 1;
  private static final int WALL_CODE_MAX = 36;
  private static final int GHOST_GATE_CODE = 37;
  private static final int GHOST_ROOM_CODE = 38;
  private static final int TUNNEL_CODE = 325;

  @SerializedName("width")
  @Expose
  private Integer width;
  @SerializedName("height")
  @Expose
  private Integer height;
  @SerializedName("pac-man")
  @Expose
  private PacMan pacMan;
  @SerializedName("ghost")
  @Expose
  private List<Ghost> ghosts = null;
  @SerializedName("map")
  @Expose
  private List<List<Integer>> map = null;

  public Level(Level level) {
    this.width = level.width;
    this.height = level.height;
    this.pacMan = new PacMan(level.pacMan);
    this.ghosts = new ArrayList<>();
    for (Ghost ghost : level.getGhosts()) {
      this.ghosts.add(new Ghost(ghost));
    }
    this.map = new ArrayList<>();
    for (List<Integer> list : level.getMap()) {
      this.map.add(new ArrayList<Integer>(list));
    }
  }

  public Level() {

  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public PacMan getPacMan() {
    return pacMan;
  }

  public void setPacMan(PacMan pacMan) {
    this.pacMan = pacMan;
  }

  public List<Ghost> getGhosts() {
    return ghosts;
  }

  public void setGhosts(List<Ghost> ghosts) {
    this.ghosts = ghosts;
  }

  public List<List<Integer>> getMap() {
    return map;
  }

  public void setMap(List<List<Integer>> map) {
    setWidth(map.get(0).size());
    setHeight(map.size());
    this.map = map;
  }

  public boolean isWall(Position position) {
    final int code = getCodeAtPosition(position);
    return WALL_CODE_MIN <= code && code <= WALL_CODE_MAX;
  }

  public boolean isTunnel(Position position) {
    final int code = getCodeAtPosition(position);
    return TUNNEL_CODE == code;
  }

  public boolean isGhostGate(Position position) {
    final int code = getCodeAtPosition(position);
    return GHOST_GATE_CODE == code;
  }

  public boolean isPacgum(Position position) {
    return PACGUM_CODE == getCodeAtPosition(position);
  }

  private int getCodeAtPosition(Position position) {
    final int row = position.getY();
    final int column = position.getX();
    return map.get(row).get(column);
  }

  public void setEmptyMapTile(Position position) {
    final int row = position.getY();
    final int column = position.getX();
    map.get(row).set(column, EMPTY_CODE);
  }

  public boolean isSuperPacgum(Position position) {
    return SUPER_PACGUM_CODE == getCodeAtPosition(position);
  }

  public boolean isCompleted() {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (map.get(j).get(i) == PACGUM_CODE || map.get(j).get(i) == SUPER_PACGUM_CODE) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isGhostRoom(Position position) {
    final int code = getCodeAtPosition(position);
    return GHOST_ROOM_CODE == code;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object other) {
    return EqualsBuilder.reflectionEquals(this, other);
  }

  public Direction getDirectionIfInLineOfSight(final Position fromPosition,
      final Position toPosition) {
    final boolean isSameX = fromPosition.getX() == toPosition.getX();
    final boolean isSameY = fromPosition.getY() == toPosition.getY();
    final boolean isLeft = toPosition.getX() < fromPosition.getX();
    final boolean isUp = toPosition.getY() < fromPosition.getY();
    if (isSameX && isSameY) {
      return null;
    }
    final Set<Position> positionsInbetween = getPositionsInbetween(fromPosition, toPosition);
    if (positionsInbetween == null) {
      return null;
    }
    final boolean isWallInbetween =
        positionsInbetween.stream().anyMatch(position -> isWall(position));
    if (isWallInbetween) {
      return null;
    }
    if (isSameX && isUp) {
      return Direction.UP;
    }
    if (isSameX) {
      return Direction.DOWN;
    }
    if (isSameY && isLeft) {
      return Direction.LEFT;
    }
    if (isSameY) {
      return Direction.RIGHT;
    }
    return null;
  }

  public static Set<Position> getPositionsInbetween(final Position positionA,
      final Position positionB) {
    Set<Position> positions = new HashSet<>();
    final int minX = Math.min(positionA.getX(), positionB.getX());
    final int minY = Math.min(positionA.getY(), positionB.getY());
    final int maxX = Math.max(positionA.getX(), positionB.getX());
    final int maxY = Math.max(positionA.getY(), positionB.getY());
    final boolean isSameX = minX == maxX;
    final boolean isSameY = minY == maxY;
    if (!isSameX && !isSameY) {
      return null;
    }
    for (int x = minX + 1; x < maxX; ++x) {
      positions.add(new Position(x, minY));
    }
    for (int y = minY + 1; y < maxY; ++y) {
      positions.add(new Position(minX, y));
    }
    return positions;
  }

}
