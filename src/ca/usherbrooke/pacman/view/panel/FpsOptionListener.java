package ca.usherbrooke.pacman.view.panel;

import ca.usherbrooke.pacman.threads.RenderThread;

public class FpsOptionListener {
  private RenderThread renderThread;

  public void setRenderThread(RenderThread renderThread) {
    this.renderThread = renderThread;
  }

  public void setFpsEnabled(boolean isFpsEnabled) {
    renderThread.setFpsEnabled(isFpsEnabled);
  }
}
