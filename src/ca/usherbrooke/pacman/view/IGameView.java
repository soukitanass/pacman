/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view;

import java.awt.event.KeyListener;
import ca.usherbrooke.pacman.view.utilities.CloseObserver;

public interface IGameView {
  void update();

  void addKeyListener(KeyListener keyListener);

  void display();

  void close();

  GameCanvas getCanvas();

  void setCanvas(GameCanvas gameCanvas);

  void addCloseObserver(CloseObserver closeObserver);

  void setFps(int fps);

  void setFpsEnabled(boolean isFpsEnabled);
}
