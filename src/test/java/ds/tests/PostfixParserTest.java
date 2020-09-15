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

  @Test
  @DisplayName("PostfixParserTest.testSingleDigit")
  void testSingleDigit() {
    String digit = "8";
    PostfixParser r = new PostfixParser(digit);
    assertEquals(Integer.valueOf(digit), r.parse(), "Digit value expected.");
  }

  @Test
  @DisplayName("PostfixParserTest.testPostfix")
  void testPostfix() {
    String number = "345+*612+/-";
    PostfixParser r = new PostfixParser(number);
    assertEquals(25, r.parse(), "Number value expected.");
  }

  @Test
  @DisplayName("PostfixParserTest.testOperatorsOnly")
  void testOperatorsOnly() {
    String expr = "+*+/-";
    PostfixParser r = new PostfixParser(expr);
    assertThrows(EmptyStackException.class, () -> r.parse(), "Exception expected.");
  }
  
  @Test
  @DisplayName("PostfixParserTest.testPostAsciiNine")
  void testPostAsciiNine() {
    String expr = ":;<=>";
    PostfixParser r = new PostfixParser(expr);
    assertThrows(EmptyStackException.class, () -> r.parse(), "Exception expected.");
  }

  @Test
  @DisplayName("PostfixParserTest.testModuloOperator")
  void testModuloOperator() {
    String expr = "345+%612+/-";
    PostfixParser r = new PostfixParser(expr);
    assertEquals(1, r.parse(), "One expected.");
  }

  @Test
  @DisplayName("PostfixParserTest.testUndefinedOperator")
  void testUndefinedOperator() {
    String expr = "3456+%!612+/-";
    PostfixParser r = new PostfixParser(expr);
    assertEquals(-2, r.parse(), "Minus two expected.");
  }

  @Test
  @DisplayName("PostfixParserTest.testEmptyStackException")
  void testEmptyStackException() {
    String expr = "345+*%612+/-";
    PostfixParser r = new PostfixParser(expr);
    assertThrows(EmptyStackException.class, () -> r.parse(), "Empty stack expected.");
  }

  @Test
  @DisplayName("PostfixParserTest.testEdgeValues")
  void testEdgeValues() {
    String expr = "905+*618+/%";
    PostfixParser r = new PostfixParser(expr);
    assertThrows(ArithmeticException.class, () -> r.parse(), "Arithmetic exception expected.");
  }
}
