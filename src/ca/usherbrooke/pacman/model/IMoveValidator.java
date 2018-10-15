/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;

public interface IMoveValidator {
  boolean isValid(IMoveRequest moveRequest) throws InvalidDirectionException;

  boolean isDesiredDirectionValid(IMoveRequest moveRequest) throws InvalidDirectionException;

  Position getTargetPosition(IMoveRequest moveRequest) throws InvalidDirectionException;
}
