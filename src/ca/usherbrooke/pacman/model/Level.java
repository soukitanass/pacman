/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level {
  private static final int EMPTY_CODE = 0;
  private static final int PACGUM_CODE = 39;
  private static final int SUPER_PACGUM_CODE = 40;
  private static final int WALL_CODE_MIN = 1;
  private static final int WALL_CODE_MAX = 36;
  private static final int GHOST_GATE_CODE = 37;
  private static final int GHOST_ROOM_CODE = 38;
  private static final int TUNNEL_CODE = 325;
  private static final int INITIAL_NUMBER_OF_LIVES = 3;

  private Integer score = 0;
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("width")
  @Expose
  private Integer width;
  @SerializedName("height")
  @Expose
  private Integer height;
  @SerializedName("super_pac_gum")
  @Expose
  private SuperPacgum superPacgum;
  @SerializedName("pac-man")
  @Expose
  private PacMan pacMan;
  @SerializedName("ghost")
  @Expose
  private List<Ghost> ghosts = null;
  @SerializedName("map")
  @Expose
  private List<List<Integer>> map = null;
  private int lives = INITIAL_NUMBER_OF_LIVES;

  public int getLives() {
    return lives;
  }

  public void setLives(int lives) {
    this.lives = lives;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public SuperPacgum getSuperPacgum() {
    return superPacgum;
  }

  public void setSuperPacgum(SuperPacgum superPacgum) {
    this.superPacgum = superPacgum;
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

  public void setGhost(List<Ghost> ghost) {
    this.ghosts = ghost;
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

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
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

}
