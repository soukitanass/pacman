/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class InvalidStateException extends Exception {
  public InvalidStateException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidStateException(String errorMessage) {
    super(errorMessage);
  }
}
