package ds;

import java.util.ArrayList;
import java.util.List;

public class AnagramGenerator {
  private char[] arrChar;
  private List<String> anagrams;

  public AnagramGenerator(String input) {
    arrChar = input.toCharArray();
    anagrams = new ArrayList<>();
  }

  public void generate() {
    generate(arrChar.length);
  }

  public void generate(int size) {
    if (size == 1) {
      addWordToList();
      return;
    }
    for (int j = 0; j < size; j++) {
      generate(size - 1);
      if (size == 2) addWordToList();
      rotate(size);
    }
  }

  public void rotate(int newSize) {
    int j;
    int size = arrChar.length;
    int position = size - newSize;
    char temp = arrChar[position];
    for (j = position + 1; j < size; j++) arrChar[j - 1] = arrChar[j];
    arrChar[j - 1] = temp;
  }

  private void addWordToList() {
    StringBuilder sb = new StringBuilder(arrChar.length);
    for (int j = 0; j < arrChar.length; j++) sb.append(arrChar[j]);
    String word = sb.toString();
    if (!anagrams.contains(word)) anagrams.add(word);
  }

  public List<String> getAnagrams() {
    return anagrams;
  }
}
