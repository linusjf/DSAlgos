package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.CountQueue;
import ds.IQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("CountQueueTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class CountQueueTest {

  private static final long VAL = 20;

  @Test
  @DisplayName("CountQueueTest.testRemoveEmpty")
  void testRemoveEmpty() {
    IQueue queue = new CountQueue(0);
    assertThrows(
        IllegalStateException.class, () -> queue.remove(), "Empty queue remove throws exception.");
  }

  @DisplayName("CountQueueTest.testRemoveEmptyOne")
  @Test
  void testRemoveEmptyOne() {
    IQueue queue = new CountQueue(1);
    assertThrows(
        IllegalStateException.class, () -> queue.remove(), "Empty queue remove throws exception.");
  }

  @DisplayName("CountQueueTest.testRemove")
  @Test
  void testRemove() {
    IQueue queue = new CountQueue(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.remove(), "Remove returns first value inserted.");
  }

  @DisplayName("CountQueueTest.testInsert")
  @Test
  void testInsertSizeOne() {
    IQueue queue = new CountQueue(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
  }

  @DisplayName("CountQueueTest.testInsertException")
  @Test
  void testInsertException() {
    IQueue queue = new CountQueue(1);
    long val = VAL;
    queue.insert(val);
    assertThrows(IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("CountQueueTest.testInsertZeroSizeException")
  @Test
  void testInsertZeroSizeException() {
    IQueue queue = new CountQueue(0);
    long val = VAL;
    assertThrows(
        IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("CountQueueTest.testIsEmpty")
  @Test
  void testIsEmpty() {
    IQueue queue = new CountQueue(0);
    assertTrue(queue.isEmpty(), "Queue must be empty.");
  }

  @DisplayName("CountQueueTest.testIsNotEmpty")
  @Test
  void testIsNotEmpty() {
    IQueue queue = new CountQueue(1);
    queue.insert(VAL);
    assertFalse(queue.isEmpty(), "Queue must not be empty.");
    assertTrue(queue.isFull(), "Queue must be full.");
  }

  @DisplayName("CountQueueTest.testIsEmptySizeOne")
  @Test
  void testIsEmptySizeOne() {
    IQueue queue = new CountQueue(1);
    assertTrue(queue.isEmpty(), "Queue must be empty.");
    assertFalse(queue.isFull(), "Queue must be empty.");
  }

  @DisplayName("CountQueueTest.testIsFull")
  @Test
  void testIsFull() {
    IQueue queue = new CountQueue(0);
    assertTrue(queue.isFull(), "Queue must be full.");
    assertTrue(queue.isEmpty(), "Queue must be empty.");
  }

  @DisplayName("CountQueueTest.testIsFullSizeOne")
  @Test
  void testIsFullSizeOne() {
    IQueue queue = new CountQueue(1);
    assertFalse(queue.isFull(), "Queue must be empty.");
    assertTrue(queue.isEmpty(), "Queue must be empty.");
  }

  @Test
  @DisplayName("CountQueueTest.testPeekEmpty")
  void testPeekEmpty() {
    IQueue queue = new CountQueue(0);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peep throws exception.");
  }

  @DisplayName("CountQueueTest.testPeekEmptyOne")
  @Test
  void testPeekEmptyOne() {
    IQueue queue = new CountQueue(1);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peek throws exception.");
  }

  @DisplayName("CountQueueTest.testPeek")
  @Test
  void testPeek() {
    IQueue queue = new CountQueue(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
  }
}
