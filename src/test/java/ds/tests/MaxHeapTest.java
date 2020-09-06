package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.IQueue;
import ds.MaxHeap;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("MaxHeapTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class MaxHeapTest {

  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String QUEUE_EMPTY = "Queue must be empty.";
  private static final String POLL_MAX_VALUE = "Poll returns max value present.";
  private static final String PEEK_MAX_VALUE = "Peek returns max value present.";
  private static final long VAL = 20;

  @Test
  @DisplayName("MaxHeapTest.testConstructorException")
  void testConstructorException() {
    assertThrows(
        IllegalArgumentException.class, () -> new MaxHeap(-1), "Constructor throws exception.");
  }

  @Test
  @DisplayName("MaxHeapTest.testPollEmpty")
  void testPollEmpty() {
    IQueue queue = new MaxHeap(0);
    assertThrows(
        IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
  }

  @DisplayName("MaxHeapTest.testPollEmptyOne")
  @Test
  void testPollEmptyOne() {
    IQueue queue = new MaxHeap(1);
    assertThrows(
        IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
  }

  @DisplayName("MaxHeapTest.testPoll")
  @Test
  void testPoll() {
    IQueue queue = new MaxHeap(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.poll(), "Poll returns first value inserted.");
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MaxHeapTest.testInsertPollInsert")
  @Test
  void testInsertPollInsert() {
    IQueue queue = new MaxHeap(1);
    long val = VAL;
    queue.insert(val);
    queue.poll();
    queue.insert(val);
    assertEquals(1, queue.size(), SIZE_ONE);
    assertEquals(val, queue.peek(), "Value must be " + val);
  }

  @DisplayName("MaxHeapTest.testInsertPollTwice")
  @Test
  void testInsertPollTwice() {
    IQueue queue = new MaxHeap(1);
    long val = VAL;
    queue.insert(val);
    queue.poll();
    queue.insert(val);
    assertEquals(val, queue.poll(), "Value must be " + val);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MaxHeapTest.testInsert")
  @Test
  void testInsertSizeOne() {
    IQueue queue = new MaxHeap(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("MaxHeapTest.testInsertException")
  @Test
  void testInsertException() {
    IQueue queue = new MaxHeap(1);
    long val = VAL;
    queue.insert(val);
    assertThrows(IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("MaxHeapTest.testInsertZeroSizeException")
  @Test
  void testInsertZeroSizeException() {
    IQueue queue = new MaxHeap(0);
    long val = VAL;
    assertThrows(IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("MaxHeapTest.testIsEmpty")
  @Test
  void testIsEmpty() {
    IQueue queue = new MaxHeap(0);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MaxHeapTest.testIsNotEmpty")
  @Test
  void testIsNotEmpty() {
    IQueue queue = new MaxHeap(1);
    queue.insert(VAL);
    assertFalse(queue.isEmpty(), "Queue must not be empty.");
    assertTrue(queue.isFull(), "Queue must be full.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("MaxHeapTest.testIsEmptySizeOne")
  @Test
  void testIsEmptySizeOne() {
    IQueue queue = new MaxHeap(1);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertFalse(queue.isFull(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MaxHeapTest.testIsFull")
  @Test
  void testIsFull() {
    IQueue queue = new MaxHeap(0);
    assertTrue(queue.isFull(), "Queue must be full.");
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MaxHeapTest.testIsFullSizeOne")
  @Test
  void testIsFullSizeOne() {
    IQueue queue = new MaxHeap(1);
    assertFalse(queue.isFull(), QUEUE_EMPTY);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("MaxHeapTest.testPeekEmpty")
  void testPeekEmpty() {
    IQueue queue = new MaxHeap(0);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peep throws exception.");
  }

  @DisplayName("MaxHeapTest.testPeekEmptyOne")
  @Test
  void testPeekEmptyOne() {
    IQueue queue = new MaxHeap(1);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peek throws exception.");
  }

  @DisplayName("MaxHeapTest.testPeek")
  @Test
  void testPeek() {
    IQueue queue = new MaxHeap(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), PEEK_MAX_VALUE);
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("MaxHeapTest.testTwoElementQueueInsert")
  @Test
  void testTwoElementQueueInsert() {
    IQueue queue = new MaxHeap(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val + 1);
    assertEquals(val + 1, queue.peek(), PEEK_MAX_VALUE);
    assertEquals(2, queue.size(), "Size must be two.");
  }

  @DisplayName("MaxHeapTest.testTwoElementQueuePoll")
  @Test
  void testTwoElementQueuePoll() {
    IQueue queue = new MaxHeap(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val + 1);
    assertEquals(val + 1, queue.poll(), POLL_MAX_VALUE);
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("MaxHeapTest.testTwoElementQueuePollTwice")
  @Test
  void testTwoElementQueuePollTwice() {
    IQueue queue = new MaxHeap(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val + 1);
    assertEquals(val + 1, queue.poll(), POLL_MAX_VALUE);
    assertEquals(val, queue.poll(), POLL_MAX_VALUE);
    assertEquals(0, queue.size(), SIZE_ONE);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @DisplayName("MaxHeapTest.testRandomInsertsMaximum")
  @Test
  void testRandomInsertsMaximum() {
    IQueue queue = new MaxHeap(MYRIAD);
    long val = Long.MAX_VALUE;
    queue.insert(val);
    Random random = new Random();
    random.longs().limit(MYRIAD - 1).forEach(i -> queue.insert(i));
    assertEquals(val, queue.peek(), PEEK_MAX_VALUE);
    assertEquals(val, queue.poll(), POLL_MAX_VALUE);
    assertEquals(MYRIAD - 1, queue.size(), "Size must be " + (MYRIAD - 1));
  }
  
  @SuppressWarnings("PMD.LawOfDemeter")
  @DisplayName("MaxHeapTest.testReverse")
  @Test
  void testReverse() {
    IQueue queue = new MaxHeap(MYRIAD);
    revRange(1, MYRIAD).forEach(i -> queue.insert(i));
    assertEquals(MYRIAD, queue.peek(), PEEK_MAX_VALUE);
    assertEquals(MYRIAD, queue.poll(), POLL_MAX_VALUE);
    assertEquals(MYRIAD - 1, queue.size(), "Size must be " + (MYRIAD - 1));
  }
}
