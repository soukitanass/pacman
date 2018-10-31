/*******************************************************************************
 * Team agilea18b, Pacman
 *
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.highscores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class HighScores {

  private static final String HIGH_SCORES_PATH = "Highscores.json";
  private static final int MAX_HIGH_SCORES = 5;
  @SerializedName("highScores")
  @Expose
  private List<HighScore> listHighScores;

  public HighScores() {
    listHighScores = new ArrayList<>();
  }

  public HighScores(List<HighScore> highScores) {
    listHighScores = new ArrayList<>(highScores);
  }

  public List<HighScore> getListHighScores() {
    return listHighScores;
  }

  public void setListHighScores(List<HighScore> listHighScores) {
    this.listHighScores = listHighScores;
  }

  public static HighScores loadHighScores(String highScoresPath) {
    Gson gson = new Gson();
    HighScores highScores = new HighScores();
    File file = new File(GameModel.class.getClassLoader().getResource(highScoresPath).getFile());
    try (FileReader fileReader = new FileReader(file)) {
      highScores = gson.fromJson(new BufferedReader(fileReader), HighScores.class);
    } catch (Exception exception) {
      WarningDialog.display("Error while opening highScores file. ", exception);
    }
    return highScores;
  }

  public boolean isHighScore(int score) {
    Collections.sort(listHighScores);
    if (!listHighScores.isEmpty()) {
      int size = listHighScores.size();
      return (score > listHighScores.get(size - 1).getScore());
    }
    return true;
  }

  public void setHighScore(int score, String name) {
    int size = listHighScores.size();
    if (size >= MAX_HIGH_SCORES) {
      listHighScores.remove(size - 1);
      listHighScores.add(new HighScore(name, score));
    } else {
      listHighScores.add(new HighScore(name, score));
    }
    saveHighScores(HIGH_SCORES_PATH);
  }

  public void saveHighScores(String highScoresPath) {
    Gson gson = new Gson();
    File file = new File(GameModel.class.getClassLoader().getResource(highScoresPath).getFile());
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException exception) {
        WarningDialog.display("Error while creating highScores file. ", exception);
      }
    }
    try (FileWriter fileWriter = new FileWriter(file)) {
      gson.toJson(this, fileWriter);
    } catch (Exception exception) {
      WarningDialog.display("Error while opening highScores file. ", exception);
    }
  }

}
