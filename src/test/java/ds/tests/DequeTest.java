package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Deque;
import ds.IDeque;
import ds.IQueue;
import ds.IStack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("DequeTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class DequeTest {
  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String QUEUE_EMPTY = "Queue must be empty.";

  private static final long VAL = 20;

  @Test
  @DisplayName("DequeTest.testConstructorException")
  void testConstructorException() {
    assertThrows(
        NegativeArraySizeException.class, () -> new Deque(-1), "Constructor throws exception.");
  }

  @Test
  @DisplayName("DequeTest.testPollEmpty")
  void testPollEmpty() {
    IQueue queue = new Deque(0);
    assertThrows(
        IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
  }

  @Test
  @DisplayName("DequeTest.testSizeEmpty")
  void testSizeEmpty() {
    IQueue queue = new Deque(0);
    assertEquals(0, queue.size(), "Size must be zero.");
  }

  @Test
  @DisplayName("DequeTest.testSizeEmptyNonZero")
  void testSizeEmptyNonZero() {
    IQueue queue = new Deque(1);
    assertEquals(0, queue.size(), "Size must be zero.");
  }

  @Test
  @DisplayName("DequeTest.testPollFirstEmpty")
  void testPollFirstEmpty() {
    IDeque deque = new Deque(0);
    assertThrows(IllegalStateException.class, () -> deque.pollFirst(), "Exception expected.");
  }

  @Test
  @DisplayName("DequeTest.testPollLastEmpty")
  void testPollLastEmpty() {
    IDeque deque = new Deque(0);
    assertThrows(IllegalStateException.class, () -> deque.pollLast(), "Exception expected.");
  }

  @Test
  @DisplayName("DequeTest.testPeekLastEmpty")
  void testPeekLastEmpty() {
    IDeque deque = new Deque(0);
    assertThrows(IllegalStateException.class, () -> deque.peekLast(), "Exception expected.");
  }

  @DisplayName("DequeTest.testPollEmptyOne")
  @Test
  void testPollEmptyOne() {
    IQueue queue = new Deque(1);
    assertThrows(
        IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
  }

  @DisplayName("DequeTest.testPoll")
  @Test
  void testPoll() {
    IQueue queue = new Deque(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.poll(), "Poll returns first value inserted.");
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("DequeTest.testTwoElementQueuePoll")
  @Test
  void testTwoElementQueuePoll() {
    IQueue queue = new Deque(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val + 1);
    assertEquals(val, queue.poll(), "Poll returns first value inserted.");
    assertEquals(val + 1, queue.poll(), "Poll returns second value inserted.");
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("DequeTest.testCircularInsert")
  @Test
  void testCircularInsert() {
    IQueue queue = new Deque(1);
    long val = VAL;
    queue.insert(val);
    queue.poll();
    queue.insert(val);
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("DequeTest.testCircularPoll")
  @Test
  void testCircularPoll() {
    IQueue queue = new Deque(1);
    long val = VAL;
    queue.insert(val);
    queue.poll();
    queue.insert(val);
    queue.poll();
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("DequeTest.testInsert")
  @Test
  void testInsertSizeOne() {
    IQueue queue = new Deque(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("DequeTest.testInsertResize")
  @Test
  void testInsertResize() {
    IQueue queue = new Deque(1);
    long val = VAL;
    queue.insert(val);
    queue.insert(val);
    assertEquals(2, queue.size(), "Size must be two.");
  }

  @DisplayName("DequeTest.testInsertZeroSizeException")
  @Test
  void testInsertZeroSizeException() {
    IQueue queue = new Deque(0);
    long val = VAL;
    assertThrows(IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("DequeTest.testIsEmpty")
  @Test
  void testIsEmpty() {
    IQueue queue = new Deque(0);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("DequeTest.testIsNotEmpty")
  @Test
  void testIsNotEmpty() {
    IQueue queue = new Deque(1);
    queue.insert(VAL);
    assertFalse(queue.isEmpty(), "Queue must not be empty.");
    assertTrue(queue.isFull(), "Queue must be full.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("DequeTest.testIsEmptySizeOne")
  @Test
  void testIsEmptySizeOne() {
    IQueue queue = new Deque(1);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertFalse(queue.isFull(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("DequeTest.testIsFull")
  @Test
  void testIsFull() {
    IQueue queue = new Deque(0);
    assertTrue(queue.isFull(), "Queue must be full.");
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("DequeTest.testIsFullSizeOne")
  @Test
  void testIsFullSizeOne() {
    IQueue queue = new Deque(1);
    assertFalse(queue.isFull(), QUEUE_EMPTY);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("DequeTest.testPeekEmpty")
  void testPeekEmpty() {
    IQueue queue = new Deque(0);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peep throws exception.");
  }

  @DisplayName("DequeTest.testPeekEmptyOne")
  @Test
  void testPeekEmptyOne() {
    IQueue queue = new Deque(1);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peek throws exception.");
  }

  @DisplayName("DequeTest.testPeek")
  @Test
  void testPeek() {
    IQueue queue = new Deque(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @Test
  @DisplayName("DequeTest.testPopEmpty")
  void testPopEmpty() {
    IStack stack = new Deque(0);
    assertThrows(
        IllegalStateException.class, () -> stack.pop(), "Empty stack pop throws exception.");
  }

  @DisplayName("DequeTest.testPopEmptyOne")
  @Test
  void testPopEmptyOne() {
    IStack stack = new Deque(1);
    assertThrows(
        IllegalStateException.class, () -> stack.pop(), "Empty stack pop throws exception.");
  }

  @DisplayName("DequeTest.testPop")
  @Test
  void testPop() {
    IStack stack = new Deque(1);
    long val = VAL;
    stack.push(val);
    assertEquals(val, stack.pop(), "Pop returns last value pushed.");
  }

  @DisplayName("DequeTest.testPush")
  @Test
  void testPushSizeOne() {
    IStack stack = new Deque(1);
    long val = VAL;
    stack.push(val);
    assertEquals(val, stack.peek(), "Pop returns last value pushed.");
  }

  @DisplayName("DequeTest.testPushException")
  @Test
  void testPushException() {
    IStack stack = new Deque(1);
    long val = VAL;
    stack.push(val);
    stack.push(val);
    assertEquals(2, stack.size(), "Stack is of size 2.");
  }

  @DisplayName("DequeTest.testPushZeroSizeException")
  @Test
  void testPushZeroSizeException() {
    IStack stack = new Deque(0);
    long val = VAL;
    assertThrows(IllegalStateException.class, () -> stack.push(val), "Push throws exception.");
  }

  @DisplayName("DequeTest.testAddFirstThrice")
  @Test
  void testAddFirstThrice() {
    IDeque deque = new Deque(3);
    deque.addFirst(VAL - 1);
    deque.addFirst(VAL);
    deque.addFirst(VAL + 1);
    assertEquals(3, deque.size(), "Size must be 3.");
  }

  @DisplayName("DequeTest.testAddLastThrice")
  @Test
  void testAddLastThrice() {
    IDeque deque = new Deque(3);
    deque.addLast(VAL - 1);
    deque.addLast(VAL);
    deque.addLast(VAL + 1);
    assertEquals(3, deque.size(), "Size must be 3.");
  }

  @DisplayName("DequeTest.testLastIndexWrapAround")
  @Test
  void testAddLastIndexWrapAround() {
    IDeque deque = new Deque(3);
    deque.addLast(VAL - 1);
    deque.addLast(VAL);
    deque.addLast(VAL + 1);
    deque.pollFirst();
    deque.addLast(VAL - 1);
    assertEquals(3, deque.size(), "Size must be 3.");
    assertEquals(VAL, deque.peekFirst(), "Value must be " + VAL + ".");
  }

  @DisplayName("DequeTest.testPollLastIndexWrapAround")
  @Test
  void testPollLastIndexWrapAround() {
    IDeque deque = new Deque(3);
    deque.addLast(VAL - 1);
    deque.addLast(VAL);
    deque.addLast(VAL + 1);
    deque.pollLast();
    deque.addLast(VAL + 1);
    assertEquals(3, deque.size(), "Size must be 3.");
    assertEquals(VAL + 1, deque.peekLast(), "Value must be " + (VAL + 1) + ".");
  }

  @DisplayName("DequeTest.testPollFirstIndexWrapAround")
  @Test
  void testPollFirstIndexWrapAround() {
    IDeque deque = new Deque(3);
    deque.addFirst(VAL - 1);
    deque.addFirst(VAL);
    deque.addFirst(VAL + 1);
    deque.pollFirst();
    deque.addFirst(VAL + 1);
    assertEquals(3, deque.size(), "Size must be 3.");
    assertEquals(VAL + 1, deque.peekFirst(), "Value must be " + (VAL + 1) + ".");
  }
}
