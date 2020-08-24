package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Reverser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("ReverserTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class ReverserTest {

  @Test
  @DisplayName("ReverserTest.testEmptyString")
  void testEmptyString() {
    String empty = "";
    Reverser r = new Reverser(empty);
    assertEquals("", r.doRev(), "Empty string expected.");
  }

  @DisplayName("ReverserTest.testPalindrome")
  @Test
  void testPalindrome() {
    String palindrome = "MALAYALAM";
    Reverser r = new Reverser(palindrome);
    assertEquals(palindrome, r.doRev(), "Palindrome expected.");
  }

  @DisplayName("ReverserTest.testSingleLetter")
  @Test
  void testSingleLetter() {
    String letter = "M";
    Reverser r = new Reverser(letter);
    assertEquals(letter, r.doRev(), "Letter expected.");
  }

  @DisplayName("ReverserTest.testReverse")
  @Test
  void testReverse() {
    String reverse = "Reverse";
    Reverser r = new Reverser(reverse);
    assertEquals("esreveR", r.doRev(), "Reverse of Reverse expected.");
  }
}
