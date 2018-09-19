package view;

import java.awt.image.BufferedImage;
import view.Color;
import view.Direction;
import view.GhostState;
import view.PacGumState;
import view.PacManState;
import model.exceptions.InvalidColorException;
import model.exceptions.InvalidDirectionException;
import model.exceptions.InvalidLetterException;
import model.exceptions.InvalidDigitException;
import model.exceptions.InvalidScoreException;
import model.exceptions.InvalidStateException;
import model.exceptions.InvalidWallCodeException;
import view.Sprite;

/*
 * Uses the Facade Pattern to hide the complexity of the system by proving an interface to the
 * client
 */

public class SpriteFacade {

  private static final String FILE_NAME = "sprites";
  private static final int TILE_SIZE = 16;
  private Sprite sprite;

  public SpriteFacade() {
    sprite = new Sprite(FILE_NAME, TILE_SIZE);
  }

  public BufferedImage getWall(int code) throws InvalidWallCodeException {
    final int y = 0;
    final int numberOfColumns = 19;

    if (code < numberOfColumns) {
      return sprite.getSprite(code, y);
    } else if (code < 2 * numberOfColumns) {
      return sprite.getSprite(code % numberOfColumns, y + 1);
    } else {
      throw new InvalidWallCodeException("Invalid wall code: " + code);
    }
  }

  public BufferedImage getPacman(Direction direction, PacManState state)
      throws InvalidStateException, InvalidDirectionException {
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
        throw new InvalidDirectionException("Invalid Pac-Man direction");
    }
    return sprite.getSprite(stateXOffset + directionXOffset, y);
  }

  public BufferedImage getGhost(Direction direction, Color color, GhostState state)
      throws InvalidColorException, InvalidDirectionException, InvalidStateException {
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

  public BufferedImage getScore(int score) throws InvalidScoreException {
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
      throw new InvalidScoreException("Invalid score:" + score);
    }
  }

  public BufferedImage getLetter(char letter, Color color)
      throws InvalidLetterException, InvalidColorException {
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
      case YELLOW:
        y = 17;
        break;
      default:
        throw new InvalidColorException("Invalid letter color");
    }

    if (letterPosition < numberOfColumns) {
      return sprite.getSprite(letterPosition, y);
    } else if (letterPosition < numberOfLetters) {
      return sprite.getSprite(letterPosition % numberOfColumns, y + 1);
    } else {
      throw new InvalidLetterException("Invalid letter:" + letter);
    }
  }

  public BufferedImage getDigit(int digit, Color color)
      throws InvalidColorException, InvalidDigitException {
    int y;
    final int xOffset = 7;

    if (digit < 0 || digit > 9) {
      throw new InvalidDigitException("Invalid digit: " + digit);
    }

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
      case YELLOW:
        y = 18;
        break;
      default:
        throw new InvalidColorException("Invalid digit color");
    }

    return sprite.getSprite(digit + xOffset, y);
  }

  public BufferedImage getPacGum(PacGumState state) throws InvalidStateException {
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

  public int getTileSize() {
    return TILE_SIZE;
  }
}
