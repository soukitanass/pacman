package ca.usherbrooke.pacman.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

  public void incrementCurrentLevel() {
    if (currentLevel < levelsList.size() - 1) {
      currentLevel++;
    }
  }

  public List<Level> getLevels() {
    return levelsList;
  }

  public void setLevels(List<Level> levels) {
    this.levelsList = levels;
  }

}
