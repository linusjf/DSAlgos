package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.BracketChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("BracketCheckerTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class BracketCheckerTest {

  @Test
  @DisplayName("BracketCheckerTest.testEmptyString")
  void testEmptyString() {
    String empty = "";
    BracketChecker r = new BracketChecker(empty);
    assertTrue(r.check(), "Empty string works.");
  }

  @DisplayName("BracketCheckerTest.testNoBrackets")
  @Test
  void testNoBrackets() {
    String palindrome = "MALAYALAM";
    BracketChecker r = new BracketChecker(palindrome);
    assertTrue(r.check(), "No brackets works.");
  }

  @DisplayName("BracketCheckerTest.testSingleLetter")
  @Test
  void testSingleLetter() {
    String letter = "M";
    BracketChecker r = new BracketChecker(letter);
    assertTrue(r.check(), "Letter works.");
  }
}
