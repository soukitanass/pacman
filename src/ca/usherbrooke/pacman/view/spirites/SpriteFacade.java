/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.spirites;

import java.awt.image.BufferedImage;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.exceptions.InvalidColorException;
import ca.usherbrooke.pacman.model.exceptions.InvalidDigitException;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.exceptions.InvalidLetterException;
import ca.usherbrooke.pacman.model.exceptions.InvalidScoreException;
import ca.usherbrooke.pacman.model.exceptions.InvalidStateException;
import ca.usherbrooke.pacman.model.exceptions.InvalidWallCodeException;
import ca.usherbrooke.pacman.view.states.GhostState;
import ca.usherbrooke.pacman.view.states.PacGumState;
import ca.usherbrooke.pacman.view.states.PacManState;
import ca.usherbrooke.pacman.view.utilities.Color;

/*
 * Uses the Facade Pattern to hide the complexity of the system by proving an interface to the
 * client
 */

public class SpriteFacade {

  private static final String FILE_NAME = "sprites";
  private static final int TILE_SIZE = 16;
  private static final int TUNNEL_CODE = 325;
  private Sprite sprite;

  public SpriteFacade() {
    sprite = new Sprite(FILE_NAME, TILE_SIZE);
  }

  public BufferedImage getWall(int code) throws InvalidWallCodeException {
    final int y = 0;
    final int numberOfColumns = 19;

    if (code == TUNNEL_CODE) {
      return sprite.getSprite(0, y);
    }

    if (code < numberOfColumns) {
      return sprite.getSprite(code, y);
    } else if (code < 2 * numberOfColumns) {
      return sprite.getSprite(code % numberOfColumns, y + 1);
    } else {
      throw new InvalidWallCodeException("Invalid wall code: " + code);
    }
  }

  public BufferedImage getDeadPacman(PacManState state) throws InvalidDirectionException {
    final int y = 6;

    switch (state) {
      case STATE6:
        return sprite.getSprite(0, y);
      case STATE7:
        return sprite.getSprite(1, y);
      case STATE8:
        return sprite.getSprite(2, y);
      case STATE9:
        return sprite.getSprite(3, y);
      case STATE10:
        return sprite.getSprite(4, y);
      case STATE11:
        return sprite.getSprite(5, y);
      case STATE12:
        return sprite.getSprite(6, y);
      case STATE13:
        return sprite.getSprite(7, y);
      case STATE14:
        return sprite.getSprite(8, y);
      case STATE15:
        return sprite.getSprite(9, y);
      case STATE16:
        return sprite.getSprite(10, y);
      default:
        throw new InvalidDirectionException("Invalid Pac-Man state");
    }
  }

  @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
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

  @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
  public BufferedImage getAfraidGhost(GhostState state)
      throws InvalidColorException, InvalidDirectionException, InvalidStateException {
    int stateXOffset = 0;

    switch (state) {
      case STATE1:
        stateXOffset = 13;
        break;
      case STATE2:
        stateXOffset = 14;
        break;
      default:
        throw new InvalidStateException("Invalid ghost state");
    }
    return sprite.getSprite(stateXOffset, 2);
  }

  @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
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

  public BufferedImage getDot(Color color) throws InvalidColorException {
    final int x = 17;

    switch (color) {
      case WHITE:
        return sprite.getSprite(x, 8);
      case PINK:
        return sprite.getSprite(x, 10);
      case ORANGE:
        return sprite.getSprite(x, 12);
      case RED:
        return sprite.getSprite(x, 14);
      case TURQUOISE:
        return sprite.getSprite(x, 16);
      case YELLOW:
        return sprite.getSprite(x, 18);
      default:
        throw new InvalidColorException("Invalid dot color");
    }
  }

  public BufferedImage getSpace() {
    return sprite.getSprite(0, 0);
  }

  @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
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

  @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
  public BufferedImage getDigit(int digit, Color color)
      throws InvalidColorException, InvalidDigitException {
    if (digit < 0 || digit > 9) {
      throw new InvalidDigitException("Invalid digit: " + digit);
    }

    final int xOffset = 7;
    final int x = digit + xOffset;

    switch (color) {
      case WHITE:
        return sprite.getSprite(x, 8);
      case PINK:
        return sprite.getSprite(x, 10);
      case ORANGE:
        return sprite.getSprite(x, 12);
      case RED:
        return sprite.getSprite(x, 14);
      case TURQUOISE:
        return sprite.getSprite(x, 16);
      case YELLOW:
        return sprite.getSprite(x, 18);
      default:
        throw new InvalidColorException("Invalid digit color");
    }
  }

  public BufferedImage getPacGum(PacGumState state) throws InvalidStateException {
    final int y = 2;

    switch (state) {
      case STATE1:
        return sprite.getSprite(4, y);
      case STATE2:
        return sprite.getSprite(3, y);
      case STATE3:
        return sprite.getSprite(2, y);
      case STATE4:
        return sprite.getSprite(1, y);
      case STATE5:
        return sprite.getSprite(0, y);
      default:
        throw new InvalidStateException("Invalid pacgum state");
    }
  }

  public int getTileSize() {
    return TILE_SIZE;
  }
}
