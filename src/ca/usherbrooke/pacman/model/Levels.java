package ca.usherbrooke.pacman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Levels {

  @SerializedName("current_level")
  @Expose
  private Integer currentLevel;
  @SerializedName("levels")
  @Expose
  private List<Level> levelsList = null;

  public Integer getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(Integer currentLevel) {
    this.currentLevel = currentLevel;
  }

  public List<Level> getLevels() {
    return levelsList;
  }

  public void setLevels(List<Level> levels) {
    this.levelsList = levels;
  }

}