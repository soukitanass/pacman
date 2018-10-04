/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.utilities;

import java.util.Iterator;
import java.util.LinkedList;

public class CircularQueue<T> implements Iterable<T> {

  LinkedList<T> values = new LinkedList<>();
  private int maxSize;

  public CircularQueue(int maxSize) {
    this.maxSize = maxSize;
  }

  public int size() {
    return values.size();
  }

  public boolean isEmpty() {
    return values.isEmpty();
  }

  public void add(T value) {
    if (size() == maxSize) {
      values.removeFirst();
    }
    values.add(value);
  }

  public T get(int index) {
    return values.get(index);
  }

  @Override
  public Iterator<T> iterator() {
    return values.iterator();
  }

}
