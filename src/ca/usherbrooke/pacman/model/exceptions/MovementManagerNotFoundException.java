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
public class MovementManagerNotFoundException extends Exception {

  public MovementManagerNotFoundException(String errorMessage) {
    super(errorMessage);
  }

}
