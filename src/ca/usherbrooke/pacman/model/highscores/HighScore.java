package ca.usherbrooke.pacman.model.highscores;

@SuppressWarnings("rawtypes")
public class HighScore implements Comparable {

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

}
