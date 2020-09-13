package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.PostfixParser;
import java.util.EmptyStackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("PostfixParserTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class PostfixParserTest {

  @Test
  @DisplayName("PostfixParserTest.testEmptyString")
  void testEmptyString() {
    String empty = "";
    PostfixParser r = new PostfixParser(empty);
    assertEquals(0, r.parse(), "Zero value expected.");
  }

  @DisplayName("PostfixParserTest.testSingleDigit")
  @Test
  void testSingleDigit() {
    String digit = "8";
    PostfixParser r = new PostfixParser(digit);
    assertEquals(Integer.valueOf(digit), r.parse(), "Digit value expected.");
  }

  @DisplayName("PostfixParserTest.testPostfix")
  @Test
  void testPostfix() {
    String number = "345+*612+/-";
    PostfixParser r = new PostfixParser(number);
    assertEquals(25, r.parse(), "Number value expected.");
  }

  @DisplayName("PostfixParserTest.testOperatorsOnly")
  @Test
  void testOperatorsOnly() {
    String expr = "+*+/-";
    PostfixParser r = new PostfixParser(expr);
    assertEquals(0, r.parse(), "Zero expected.");
  }

  @DisplayName("PostfixParserTest.testUndefinedOperator")
  @Test
  void testUndefinedOperator() {
    String expr = "345+%612+/-";
    PostfixParser r = new PostfixParser(expr);
    assertEquals(-2, r.parse(), "Zero expected.");
  }

  @DisplayName("PostfixParserTest.testEmptyStackException")
  @Test
  void testEmptyStackException() {
    String expr = "345+*%612+/-";
    PostfixParser r = new PostfixParser(expr);
    assertThrows(EmptyStackException.class, () -> r.parse(), "Empty stack expected.");
  }
}
