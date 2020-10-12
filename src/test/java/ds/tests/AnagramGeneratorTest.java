package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.AnagramGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("AnagramGeneratorTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class AnagramGeneratorTest {

  @Test
  @DisplayName("AnagramGeneratorTest.testEmptyString")
  void testEmptyString() {
    String empty = "";
    AnagramGenerator r = new AnagramGenerator(empty);
    r.generate();
    assertEquals(0, r.getAnagrams().size(), "Empty list expected.");
  }

  @DisplayName("AnagramGeneratorTest.testFourLetters")
  @Test
  void testFourLetters() {
    String input = "cats";
    AnagramGenerator r = new AnagramGenerator(input);
    r.generate();
    assertEquals(24, r.getAnagrams().size(), "24 words expected.");
  }

  @DisplayName("AnagramGeneratorTest.testOneLetter")
  @Test
  void testOneLetter() {
    String input = "s";
    AnagramGenerator r = new AnagramGenerator(input);
    r.generate();
    assertEquals(1, r.getAnagrams().size(), "1 word expected.");
  }

  @DisplayName("AnagramGeneratorTest.testTwoLetters")
  @Test
  void testTwoLetters() {
    String input = "as";
    AnagramGenerator r = new AnagramGenerator(input);
    r.generate();
    assertEquals(2, r.getAnagrams().size(), "2 words expected.");
  }
}
