package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.SimpleStack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("SimpleStackTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class SimpleStackTest {

  @Test
  @DisplayName("SimpleStackTest.testPopEmpty")
  void testPopEmpty() {
    SimpleStack stack = new SimpleStack(0);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.pop(),
        "Empty stack pop throws exception.");
  }

  @DisplayName("SimpleStackTest.testPopEmptyOne")
  @Test
  void testPopEmptyOne() {
    SimpleStack stack = new SimpleStack(1);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.pop(),
        "Empty stack pop throws exception.");
  }

  @DisplayName("SimpleStackTest.testPop")
  @Test
  void testPop() {
    SimpleStack stack = new SimpleStack(1);
    long val = 20;
    stack.push(val);
    assertEquals(val, stack.pop(), "Pop returns last value pushed.");
  }
}
