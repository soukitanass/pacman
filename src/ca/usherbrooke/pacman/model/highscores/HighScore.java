package ca.usherbrooke.pacman.model.highscores;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@SuppressWarnings("rawtypes")
public class HighScore implements Comparable<Object> {

  private int score;
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
