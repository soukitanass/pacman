package ca.usherbrooke.pacman.model.highscores;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HighScores {

  @SerializedName("highScores")
  @Expose
  private List<HighScore> listHighScores;

  public HighScores() {
    listHighScores = new ArrayList<HighScore>();
  }

  public HighScores(List<HighScore> highScores) {
    listHighScores = new ArrayList<HighScore>(highScores);
  }

  public List<HighScore> getListHighScores() {
    return listHighScores;
  }

  public void setListHighScores(List<HighScore> listHighScores) {
    this.listHighScores = listHighScores;
  }

}
