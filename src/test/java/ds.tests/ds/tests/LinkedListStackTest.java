package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.IStack;
import ds.LinkedListStack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("LinkedListStackTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class LinkedListStackTest {

  private static final long VAL = 20;

  @Test
  @DisplayName("LinkedListStackTest.testPopEmpty")
  void testPopEmpty() {
    IStack stack = new LinkedListStack();
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.pop(),
        "Empty stack pop throws exception.");
  }

  @DisplayName("LinkedListStackTest.testPopEmptyOne")
  @Test
  void testPopEmptyOne() {
    IStack stack = new LinkedListStack();
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.pop(),
        "Empty stack pop throws exception.");
  }

  @DisplayName("LinkedListStackTest.testPop")
  @Test
  void testPop() {
    IStack stack = new LinkedListStack();
    long val = VAL;
    stack.push(val);
    assertEquals(val, stack.pop(), "Pop returns last value pushed.");
  }

  @DisplayName("LinkedListStackTest.testPushOne")
  @Test
  void testPushOne() {
    IStack stack = new LinkedListStack();
    long val = VAL;
    stack.push(val);
    assertEquals(val, stack.peek(), "Pop returns last value pushed.");
  }

  @DisplayName("LinkedListStackTest.testPushTwo")
  @Test
  void testPushTwo() {
    IStack stack = new LinkedListStack();
    long val = VAL;
    stack.push(val);
    stack.push(val + 1);
    assertEquals(val + 1, stack.peek(), "Peek returns last value pushed.");
  }

  @DisplayName("LinkedListStackTest.testIsEmpty")
  @Test
  void testIsEmpty() {
    IStack stack = new LinkedListStack();
    assertTrue(stack.isEmpty(), "Stack must be empty.");
  }

  @DisplayName("LinkedListStackTest.testIsNotEmpty")
  @Test
  void testIsNotEmpty() {
    IStack stack = new LinkedListStack();
    stack.push(VAL);
    assertFalse(stack.isEmpty(), "Stack must not be empty.");
  }

  @DisplayName("LinkedListStackTest.testIsEmptySizeOne")
  @Test
  void testIsEmptySizeOne() {
    IStack stack = new LinkedListStack();
    assertTrue(stack.isEmpty(), "Stack must be empty.");
  }

  @DisplayName("LinkedListStackTest.testIsFull")
  @Test
  void testIsFull() {
    IStack stack = new LinkedListStack();
    assertFalse(stack.isFull(), "Stack must not be full.");
  }

  @DisplayName("LinkedListStackTest.testIsFullSizeOne")
  @Test
  void testIsFullSizeOne() {
    IStack stack = new LinkedListStack();
    assertFalse(stack.isFull(), "Stack must be empty.");
  }

  @Test
  @DisplayName("LinkedListStackTest.testPeekEmpty")
  void testPeekEmpty() {
    IStack stack = new LinkedListStack();
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.peek(),
        "Empty stack peep throws exception.");
  }

  @DisplayName("LinkedListStackTest.testPeekEmptyOne")
  @Test
  void testPeekEmptyOne() {
    IStack stack = new LinkedListStack();
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> stack.peek(),
        "Empty stack peek throws exception.");
  }

  @DisplayName("LinkedListStackTest.testPeek")
  @Test
  void testPeek() {
    IStack stack = new LinkedListStack();
    long val = VAL;
    stack.push(val);
    assertEquals(val, stack.peek(), "Peek returns last value pushed.");
  }

  @DisplayName("LinkedListStackTest.testSize")
  @Test
  void testSize() {
    IStack stack = new LinkedListStack();
    long val = VAL;
    stack.push(val);
    assertEquals(1, stack.size(), "Size is one.");
  }

  @DisplayName("LinkedListStackTest.testSizeEmpty")
  @Test
  void testSizeEmpty() {
    IStack stack = new LinkedListStack();
    assertEquals(0, stack.size(), "Size is zero.");
  }
}
