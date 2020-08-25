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

  @DisplayName("BracketCheckerTest.testEmptyParentheses")
  @Test
  void testEmptyParentheses() {
    String input = "()";
    BracketChecker r = new BracketChecker(input);
    assertTrue(r.check(), "Empty parentheses valid.");
  }

  @DisplayName("BracketCheckerTest.testEmptyCurlyBraces")
  @Test
  void testEmptyCurlyBraces() {
    String input = "{}";
    BracketChecker r = new BracketChecker(input);
    assertTrue(r.check(), "Empty parentheses valid.");
  }

  @DisplayName("BracketCheckerTest.testEmptySquareBrackets")
  @Test
  void testEmptySquareBrackets() {
    String input = "[]";
    BracketChecker r = new BracketChecker(input);
    assertTrue(r.check(), "Empty parentheses valid.");
  }

  @DisplayName("BracketCheckerTest.testValidInput")
  @Test
  void testValidInput() {
    String input = "{call(a[i]);}";
    BracketChecker r = new BracketChecker(input);
    assertTrue(r.check(), "Valid result expected.");
  }

  @DisplayName("BracketCheckerTest.testValidInput2")
  @Test
  void testValidInput2() {
    String input = "[call{a(i)};]";
    BracketChecker r = new BracketChecker(input);
    assertTrue(r.check(), "Valid result expected.");
  }

  @DisplayName("BracketCheckerTest.testValidInput3")
  @Test
  void testValidInput3() {
    String input = "(call[a{i};])";
    BracketChecker r = new BracketChecker(input);
    assertTrue(r.check(), "Valid result expected.");
  }

  @DisplayName("BracketCheckerTest.testInvalidInput")
  @Test
  void testInvalidInput() {
    String input = "{call(a[i]);]";
    BracketChecker r = new BracketChecker(input);
    assertFalse(r.check(), "Invalid result expected.");
  }

  @DisplayName("BracketCheckerTest.testInvalidInput2")
  @Test
  void testInvalidInput2() {
    String input = "[call{a(i)];}";
    BracketChecker r = new BracketChecker(input);
    assertFalse(r.check(), "Invalid result expected.");
  }

  @DisplayName("BracketCheckerTest.testInvalidInput3")
  @Test
  void testInvalidInput3() {
    String input = "[call[a{i};])";
    BracketChecker r = new BracketChecker(input);
    assertFalse(r.check(), "Invalid result expected.");
  }

  @DisplayName("BracketCheckerTest.testInvalidInput4")
  @Test
  void testInvalidInput4() {
    String input = "[call{a{i};])";
    BracketChecker r = new BracketChecker(input);
    assertFalse(r.check(), "Invalid result expected.");
  }

  @DisplayName("BracketCheckerTest.testInvalidInput5")
  @Test
  void testInvalidInput5() {
    String input = "{calla[i]);]";
    BracketChecker r = new BracketChecker(input);
    assertFalse(r.check(), "Invalid result expected.");
  }

  @DisplayName("BracketCheckerTest.testInvalidInput6")
  @Test
  void testInvalidInput6() {
    String input = "[call{a(i];}";
    BracketChecker r = new BracketChecker(input);
    assertFalse(r.check(), "Invalid result expected.");
  }

  @DisplayName("BracketCheckerTest.testNoLeftBrackets")
  @Test
  void testNoLeftBrackets() {
    String input = ")]}";
    BracketChecker r = new BracketChecker(input);
    assertFalse(r.check(), "Invalid result expected.");
  }
}
