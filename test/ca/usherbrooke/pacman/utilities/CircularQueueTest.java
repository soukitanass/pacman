/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CircularQueueTest {

  CircularQueue<Integer> queue = new CircularQueue<Integer>(3);

  @Test
  public void isInitiallyEmpty() {
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test
  public void addValues() {
    queue.add(42);
    assertEquals(1, queue.size());
    assertEquals(Integer.valueOf(42), queue.get(0));
    queue.add(123);
    assertEquals(2, queue.size());
    assertEquals(Integer.valueOf(42), queue.get(0));
    assertEquals(Integer.valueOf(123), queue.get(1));
  }

  @Test
  public void isIterable() {
    queue.add(1);
    queue.add(2);
    queue.add(3);
    int sum = 0;
    for (Integer value : queue) {
      sum += value;
    }
    assertEquals(6, sum);
  }

  @Test
  public void forgetOldValuesWhenAddingMoreThanMaxSize() {
    queue.add(1);
    queue.add(2);
    queue.add(3);
    queue.add(4);
    assertEquals(3, queue.size());
    assertEquals(Integer.valueOf(2), queue.get(0));
    assertEquals(Integer.valueOf(3), queue.get(1));
    assertEquals(Integer.valueOf(4), queue.get(2));
  }

}
