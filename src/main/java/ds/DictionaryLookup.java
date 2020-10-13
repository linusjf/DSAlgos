package ds;

import static java.util.Objects.*;

import java.util.Map;
import java.util.NoSuchElementException;

public class DictionaryLookup {

  private static final String LOCAL_STORE = "local.ser";

  private static Map<String, Boolean> lookupTable = null;

  private static boolean isValidWord(String word) {
    boolean isValid;
    try {
      isValid = isWordAvailable(word);
    } catch (NoSuchElementException nsee) {
      System.out.println(
          word + " not found in local store..." + System.lineSeparator() + "Searching online...");
      isValid = isWordOnline(word);
    }
    return isValid;
  }

  private static boolean isWordAvailable(String word) {
    if (isNull(lookupTable)) deserializeLookupTable();
    if (lookupTable.containsKey(word)) return lookupTable.get(word);
    throw new NoSuchElementException(word + " does not exist in local store.");
  }

  private static boolean isWordOnline(String word) {
    return false;
  }

  private static void deserializeLookupTable() {}

  private static void serializeLookupTable() {}
}
