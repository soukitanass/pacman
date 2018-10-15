/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin dupm2216 - Maxime Dupuis nass2801 - Soukaina Nassib royb2006 -
 * Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.panel;

import ca.usherbrooke.pacman.threads.RenderThread;

public class FpsOptionListener {
  private RenderThread renderThread;

  public void setRenderThread(RenderThread renderThread) {
    this.renderThread = renderThread;
  }

  public void setFpsEnabled(final boolean isFpsEnabled) {
    renderThread.setFpsEnabled(isFpsEnabled);
  }

  public void setFps(final int fps) {
    renderThread.setFps(fps);
  }
}
