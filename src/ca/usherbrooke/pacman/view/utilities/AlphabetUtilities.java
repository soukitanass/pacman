package ca.usherbrooke.pacman.view.utilities;

public class AlphabetUtilities {

  private static final int ASCII_LOWER_CASE_LETTERS_INDEX = 96;
  private static final int ALPHABET_FIRST_POSITION = 1;
  private static final int ALPHABET_LAST_POSITION = 26;

  private AlphabetUtilities() {
    throw new IllegalStateException("Utility class");
  }

  public static int findIndexInAlphabet(final char letter) {
    final int asciiValue = letter;
    return asciiValue - ASCII_LOWER_CASE_LETTERS_INDEX;
  }

  public static char findLetterInAlphabet(int index) {
    if (index > ALPHABET_LAST_POSITION) {
      index = ALPHABET_FIRST_POSITION;
    }
    if (index < ALPHABET_FIRST_POSITION) {
      index = ALPHABET_LAST_POSITION;
    }
    final int asciiValue = index + ASCII_LOWER_CASE_LETTERS_INDEX;
    return (char) asciiValue;
  }
}
