package ca.usherbrooke.pacman.view.utilities;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AlphabetUtilitiesTest {

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  @Test
  public void findIndexInAlphabet() {
    for (int i = 0; i < ALPHABET.length(); i++) {
      int index = AlphabetUtilities.findIndexInAlphabet(ALPHABET.charAt(i));
      assertEquals(i + 1, index);
    }
  }

  @Test
  public void findLetterInAlphabet() {
    for (int i = 0; i < 25; i++) {
      char letter = AlphabetUtilities.findLetterInAlphabet(i + 1);
      assertEquals(ALPHABET.charAt(i), letter);
    }
  }

}
