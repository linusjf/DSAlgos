package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.InToPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("InToPostTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class InToPostTest {

  private static final String POSTFIX_NOTATION_EXPECTED = "Postfix notation must be as expected.";

  @Test
  @DisplayName("InToPostTest.testEmptyString")
  void testEmptyString() {
    String empty = "";
    InToPost r = new InToPost(empty);
    assertEquals("", r.translate(), "Empty string expected.");
  }

  @DisplayName("InToPostTest.testSingleLetter")
  @Test
  void testSingleLetter() {
    String letter = "M";
    InToPost r = new InToPost(letter);
    assertEquals(letter, r.translate(), "Letter expected.");
  }

  @DisplayName("InToPostTest.testInfix")
  @Test
  void testInfix() {
    String infix = "A*(B+C)-D/(E+F)";
    InToPost r = new InToPost(infix);
    assertEquals("ABC+*DEF+/-", r.translate(), POSTFIX_NOTATION_EXPECTED);
  }

  @DisplayName("InToPostTest.testSimpleInfix")
  @Test
  void testSimpleInfix() {
    String infix = "2+3*4";
    InToPost r = new InToPost(infix);
    assertEquals("234*+", r.translate(), POSTFIX_NOTATION_EXPECTED);
  }

  @DisplayName("InToPostTest.testSimplerInfix")
  @Test
  void testSimplerInfix() {
    String infix = "5+7";
    InToPost r = new InToPost(infix);
    assertEquals("57+", r.translate(), POSTFIX_NOTATION_EXPECTED);
  }

  @DisplayName("InToPostTest.testOnlyParentheses")
  @Test
  void testOnlyParentheses() {
    String infix = "()";
    InToPost r = new InToPost(infix);
    assertEquals("", r.translate(), "Empty string expected.");
  }

  @DisplayName("InToPostTest.testOnlyOperators")
  @Test
  void testOnlyOperators() {
    String infix = "*+-/";
    InToPost r = new InToPost(infix);
    assertEquals("*+/-", r.translate(), POSTFIX_NOTATION_EXPECTED);
  }

  @DisplayName("InToPostTest.testOnlyOperatorsAgain")
  @Test
  void testOnlyOperatorsAgain() {
    String infix = "+-*/";
    InToPost r = new InToPost(infix);
    assertEquals("+*/-", r.translate(), POSTFIX_NOTATION_EXPECTED);
  }

  @DisplayName("InToPostTest.testOnlyRightParenthesis")
  @Test
  void testOnlyRightParenthesis() {
    String infix = ")";
    InToPost r = new InToPost(infix);
    assertEquals("", r.translate(), "Empty string expected.");
  }
}
