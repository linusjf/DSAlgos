package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.IStack;
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

  private static final long VAL = 20;

  @Test
  @DisplayName("SimpleStackTest.testPopEmpty")
  void testPopEmpty() {
    IStack stack = new SimpleStack(0);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.pop(),
        "Empty stack pop throws exception.");
  }

  @DisplayName("SimpleStackTest.testPopEmptyOne")
  @Test
  void testPopEmptyOne() {
    IStack stack = new SimpleStack(1);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.pop(),
        "Empty stack pop throws exception.");
  }

  @DisplayName("SimpleStackTest.testPop")
  @Test
  void testPop() {
    IStack stack = new SimpleStack(1);
    long val = VAL;
    stack.push(val);
    assertEquals(val, stack.pop(), "Pop returns last value pushed.");
  }

  @DisplayName("SimpleStackTest.testSize")
  @Test
  void testSize() {
    IStack stack = new SimpleStack(1);
    long val = VAL;
    stack.push(val);
    assertEquals(1, stack.size(), "Size is one.");
  }

  @DisplayName("SimpleStackTest.testSizeEmpty")
  @Test
  void testSizeEmpty() {
    IStack stack = new SimpleStack(1);
    assertEquals(0, stack.size(), "Size is zero.");
  }

  @DisplayName("SimpleStackTest.testPush")
  @Test
  void testPushSizeOne() {
    IStack stack = new SimpleStack(1);
    long val = VAL;
    stack.push(val);
    assertEquals(val, stack.peek(), "Pop returns last value pushed.");
  }

  @DisplayName("SimpleStackTest.testPushException")
  @Test
  void testPushException() {
    IStack stack = new SimpleStack(1);
    long val = VAL;
    stack.push(val);
    assertThrows(
        ArrayIndexOutOfBoundsException.class, () -> stack.push(val), "Push throws exception.");
  }

  @DisplayName("SimpleStackTest.testPushZeroSizeException")
  @Test
  void testPushZeroSizeException() {
    IStack stack = new SimpleStack(0);
    long val = VAL;
    assertThrows(
        ArrayIndexOutOfBoundsException.class, () -> stack.push(val), "Push throws exception.");
  }

  @DisplayName("SimpleStackTest.testIsEmpty")
  @Test
  void testIsEmpty() {
    IStack stack = new SimpleStack(0);
    assertTrue(stack.isEmpty(), "Stack must be empty.");
  }

  @DisplayName("SimpleStackTest.testIsNotEmpty")
  @Test
  void testIsNotEmpty() {
    IStack stack = new SimpleStack(1);
    stack.push(VAL);
    assertFalse(stack.isEmpty(), "Stack must not be empty.");
  }

  @DisplayName("SimpleStackTest.testIsEmptySizeOne")
  @Test
  void testIsEmptySizeOne() {
    IStack stack = new SimpleStack(1);
    assertTrue(stack.isEmpty(), "Stack must be empty.");
  }

  @DisplayName("SimpleStackTest.testIsFull")
  @Test
  void testIsFull() {
    IStack stack = new SimpleStack(0);
    assertTrue(stack.isFull(), "Stack must be full.");
  }

  @DisplayName("SimpleStackTest.testIsFullSizeOne")
  @Test
  void testIsFullSizeOne() {
    IStack stack = new SimpleStack(1);
    assertFalse(stack.isFull(), "Stack must be empty.");
  }

  @Test
  @DisplayName("SimpleStackTest.testPeekEmpty")
  void testPeekEmpty() {
    IStack stack = new SimpleStack(0);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.peek(),
        "Empty stack peep throws exception.");
  }

  @DisplayName("SimpleStackTest.testPeekEmptyOne")
  @Test
  void testPeekEmptyOne() {
    IStack stack = new SimpleStack(1);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.peek(),
        "Empty stack peek throws exception.");
  }

  @DisplayName("SimpleStackTest.testPeek")
  @Test
  void testPeek() {
    IStack stack = new SimpleStack(1);
    long val = VAL;
    stack.push(val);
    assertEquals(val, stack.peek(), "Peek returns last value pushed.");
  }
}
