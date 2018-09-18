package view;

import java.awt.image.BufferedImage;
import model.Color;
import model.Direction;
import model.GhostState;
import model.PacGumState;
import model.PacManState;
import model.exceptions.InvalidColorException;
import model.exceptions.InvalidDirectionException;
import model.exceptions.InvalidStateException;

public class SpriteFacade {

  private static final String FILE_NAME = "sprites";
  private static final int TILE_SIZE = 8;
  private Sprite sprite;

  public SpriteFacade() {
    sprite = new Sprite(FILE_NAME, TILE_SIZE);
  }

  public BufferedImage getWall(int code) throws Exception {
    final int y = 0;
    final int numberOfColumns = 19;

    if (code < numberOfColumns) {
      return sprite.getSprite(code, y);
    } else if (code >= numberOfColumns && code < 2 * numberOfColumns - 1) {
      return sprite.getSprite(code % numberOfColumns, y + 1);
    } else {
      throw new Exception("Invalid wall code");
    }
  }

  public BufferedImage getPacman(Direction direction, PacManState state) throws Exception {
    final int fullPacManX = 16;
    final int y = 3;
    int directionXOffset;
    int stateXOffset;

    switch (state) {
      case STATE1:
        return sprite.getSprite(fullPacManX, y);
      case STATE2:
        stateXOffset = 3;
        break;
      case STATE3:
        stateXOffset = 2;
        break;
      case STATE4:
        stateXOffset = 1;
        break;
      case STATE5:
        stateXOffset = 0;
        break;
      default:
        throw new InvalidStateException("Invalid pacman state");
    }

    switch (direction) {
      case LEFT:
        directionXOffset = 4;
        break;
      case RIGHT:
        directionXOffset = 0;
        break;
      case UP:
        directionXOffset = 12;
        break;
      case DOWN:
        directionXOffset = 8;
        break;
      default:
        throw new InvalidDirectionException("Invalid pacman direction");
    }
    return sprite.getSprite(stateXOffset + directionXOffset, y);
  }

  public BufferedImage getGhost(Direction direction, Color color, GhostState state)
      throws Exception {
    int colorXOffset = 0;
    int directionXOffset = 0;
    int stateXOffset = 0;
    int y;

    switch (color) {
      case RED:
        y = 4;
        break;
      case ORANGE:
        y = 4;
        colorXOffset = 8;
        break;
      case PINK:
        y = 5;
        break;
      case TURQUOISE:
        y = 5;
        colorXOffset = 8;
        break;
      default:
        throw new InvalidColorException("Invalid ghost color");
    }

    switch (direction) {
      case LEFT:
        directionXOffset = 2;
        break;
      case RIGHT:
        directionXOffset = 6;
        break;
      case UP:
        directionXOffset = 0;
        break;
      case DOWN:
        directionXOffset = 4;
        break;
      default:
        throw new InvalidDirectionException("Invalid ghost direction");
    }

    switch (state) {
      case STATE1:
        stateXOffset = 0;
        break;
      case STATE2:
        stateXOffset = 1;
        break;
      default:
        throw new InvalidStateException("Invalid ghost state");
    }
    return sprite.getSprite(stateXOffset + directionXOffset + colorXOffset, y);
  }

  public BufferedImage getScore(int score) throws Exception {
    final int y = 2;
    final int x = 15;

    if (score == 200) {
      return sprite.getSprite(x, y);
    } else if (score == 400) {
      return sprite.getSprite(x + 1, y);
    } else if (score == 800) {
      return sprite.getSprite(x + 2, y);
    } else if (score == 1600) {
      return sprite.getSprite(x + 3, y);
    } else {
      throw new Exception("Invalid score");
    }
  }

  public BufferedImage getLetter(char letter, Color color) throws Exception {
    int y;
    final int numberOfColumns = 19;
    final int letterPosition = Character.toUpperCase(letter) - 65;
    final int numberOfLetters = 26;

    switch (color) {
      case WHITE:
        y = 7;
        break;
      case PINK:
        y = 9;
        break;
      case ORANGE:
        y = 11;
        break;
      case RED:
        y = 13;
        break;
      case TURQUOISE:
        y = 15;
        break;
      default:
        throw new InvalidColorException("Invalid letter color");
    }

    if (letterPosition < numberOfColumns) {
      return sprite.getSprite(letterPosition, y);
    } else if (letterPosition >= numberOfColumns && letterPosition < numberOfLetters) {
      return sprite.getSprite(letterPosition % numberOfColumns, y + 1);
    } else {
      throw new Exception("Invalid letter");
    }
  }

  public BufferedImage getNumber(int number, Color color) throws Exception {
    int y;
    final int xOffset = 8;

    switch (color) {
      case WHITE:
        y = 8;
        break;
      case PINK:
        y = 10;
        break;
      case ORANGE:
        y = 12;
        break;
      case RED:
        y = 14;
        break;
      case TURQUOISE:
        y = 16;
        break;
      default:
        throw new InvalidColorException("Invalid number color");
    }

    if (number < 0 || number > 9) {
      throw new Exception("Invalid number");
    }

    return sprite.getSprite(number + xOffset, y);
  }

  public BufferedImage getPacGum(PacGumState state) throws Exception {
    final int y = 2;
    int x;

    switch (state) {
      case STATE1:
        x = 4;
        break;
      case STATE2:
        x = 3;
        break;
      case STATE3:
        x = 2;
        break;
      case STATE4:
        x = 1;
        break;
      case STATE5:
        x = 0;
        break;
      default:
        throw new InvalidStateException("Invalid pacgum state");
    }
    return sprite.getSprite(x, y);
  }
}
