package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.IQueue;
import ds.NoCountQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("NoCountQueueTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class NoCountQueueTest {

  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String QUEUE_EMPTY = "Queue must be empty.";
  private static final long VAL = 20;

  @Test
  @DisplayName("NoCountQueueTest.testConstructorException")
  void testConstructorException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new NoCountQueue(-1),
        "Constructor throws exception.");
  }

  @Test
  @DisplayName("NoCountQueueTest.testRemoveEmpty")
  void testRemoveEmpty() {
    IQueue queue = new NoCountQueue(0);
    assertThrows(
        IllegalStateException.class, () -> queue.remove(), "Empty queue remove throws exception.");
  }

  @DisplayName("NoCountQueueTest.testRemoveEmptyOne")
  @Test
  void testRemoveEmptyOne() {
    IQueue queue = new NoCountQueue(1);
    assertThrows(
        IllegalStateException.class, () -> queue.remove(), "Empty queue remove throws exception.");
  }

  @DisplayName("NoCountQueueTest.testRemove")
  @Test
  void testRemove() {
    IQueue queue = new NoCountQueue(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.remove(), "Remove returns first value inserted.");
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("NoCountQueueTest.testCircularInsert")
  @Test
  void testCircularInsert() {
    IQueue queue = new NoCountQueue(1);
    long val = VAL;
    queue.insert(val);
    queue.remove();
    queue.insert(val);
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("NoCountQueueTest.testCircularRemove")
  @Test
  void testCircularRemove() {
    IQueue queue = new NoCountQueue(1);
    long val = VAL;
    queue.insert(val);
    queue.remove();
    queue.insert(val);
    queue.remove();
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("NoCountQueueTest.testInsert")
  @Test
  void testInsertSizeOne() {
    IQueue queue = new NoCountQueue(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("NoCountQueueTest.testInsertException")
  @Test
  void testInsertException() {
    IQueue queue = new NoCountQueue(1);
    long val = VAL;
    queue.insert(val);
    assertThrows(IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("NoCountQueueTest.testInsertZeroSizeException")
  @Test
  void testInsertZeroSizeException() {
    IQueue queue = new NoCountQueue(0);
    long val = VAL;
    assertThrows(IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("NoCountQueueTest.testIsEmpty")
  @Test
  void testIsEmpty() {
    IQueue queue = new NoCountQueue(0);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("NoCountQueueTest.testIsNotEmpty")
  @Test
  void testIsNotEmpty() {
    IQueue queue = new NoCountQueue(1);
    queue.insert(VAL);
    assertFalse(queue.isEmpty(), "Queue must not be empty.");
    assertTrue(queue.isFull(), "Queue must be full.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("NoCountQueueTest.testIsEmptySizeOne")
  @Test
  void testIsEmptySizeOne() {
    IQueue queue = new NoCountQueue(1);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertFalse(queue.isFull(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("NoCountQueueTest.testIsFull")
  @Test
  void testIsFull() {
    IQueue queue = new NoCountQueue(0);
    assertTrue(queue.isFull(), "Queue must be full.");
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("NoCountQueueTest.testIsFullSizeOne")
  @Test
  void testIsFullSizeOne() {
    IQueue queue = new NoCountQueue(1);
    assertFalse(queue.isFull(), QUEUE_EMPTY);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("NoCountQueueTest.testPeekEmpty")
  void testPeekEmpty() {
    IQueue queue = new NoCountQueue(0);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peep throws exception.");
  }

  @DisplayName("NoCountQueueTest.testPeekEmptyOne")
  @Test
  void testPeekEmptyOne() {
    IQueue queue = new NoCountQueue(1);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peek throws exception.");
  }

  @DisplayName("NoCountQueueTest.testPeek")
  @Test
  void testPeek() {
    IQueue queue = new NoCountQueue(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("NoCountQueueTest.testTwoElementQueueInsert")
  @Test
  void testTwoElementQueueInsert() {
    IQueue queue = new NoCountQueue(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val + 1);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
    assertEquals(2, queue.size(), "Size must be two.");
  }

  @DisplayName("NoCountQueueTest.testTwoElementQueueRemove")
  @Test
  void testTwoElementQueueRemove() {
    IQueue queue = new NoCountQueue(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val + 1);
    assertEquals(val, queue.remove(), "Remove returns first value inserted.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("NoCountQueueTest.testTwoElementQueueRemoveTwice")
  @Test
  void testTwoElementQueueRemoveTwice() {
    IQueue queue = new NoCountQueue(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val + 1);
    assertEquals(val, queue.remove(), "Remove returns first value inserted.");
    assertEquals(val + 1, queue.remove(), "Remove returns second value inserted.");
    assertEquals(0, queue.size(), SIZE_ONE);
  }
}
