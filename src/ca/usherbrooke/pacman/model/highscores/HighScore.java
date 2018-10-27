package ca.usherbrooke.pacman.model.highscores;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("rawtypes")
public class HighScore implements Comparable<Object> {

  @SerializedName("score")
  @Expose
  private int score;
  @SerializedName("name")
  @Expose
  private String name;

  public HighScore(String name, int score) {
    this.score = score;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  @Override
  public int compareTo(Object score) {
    int compareScore = ((HighScore) score).getScore();
    return compareScore - this.score;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
