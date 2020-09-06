package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.IQueue;
import ds.MinHeap;
import java.util.Random;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("MinHeapTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class MinHeapTest implements SortProvider {

  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String QUEUE_EMPTY = "Queue must be empty.";
  private static final String POLL_MIN_VALUE = "Poll returns min value present.";
  private static final String PEEK_MIN_VALUE = "Peek returns min value present.";
  private static final long VAL = 20;

  @Test
  @DisplayName("MinHeapTest.testConstructorException")
  void testConstructorException() {
    assertThrows(
        IllegalArgumentException.class, () -> new MinHeap(-1), "Constructor throws exception.");
  }

  @Test
  @DisplayName("MinHeapTest.testPollEmpty")
  void testPollEmpty() {
    IQueue queue = new MinHeap(0);
    assertThrows(
        IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
  }

  @DisplayName("MinHeapTest.testPollEmptyOne")
  @Test
  void testPollEmptyOne() {
    IQueue queue = new MinHeap(1);
    assertThrows(
        IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
  }

  @DisplayName("MinHeapTest.testPoll")
  @Test
  void testPoll() {
    IQueue queue = new MinHeap(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.poll(), "Poll returns first value inserted.");
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MinHeapTest.testInsertPollInsert")
  @Test
  void testInsertPollInsert() {
    IQueue queue = new MinHeap(1);
    long val = VAL;
    queue.insert(val);
    queue.poll();
    queue.insert(val);
    assertEquals(1, queue.size(), SIZE_ONE);
    assertEquals(val, queue.peek(), "Value must be " + val);
  }

  @DisplayName("MinHeapTest.testInsertPollTwice")
  @Test
  void testInsertPollTwice() {
    IQueue queue = new MinHeap(1);
    long val = VAL;
    queue.insert(val);
    queue.poll();
    queue.insert(val);
    assertEquals(val, queue.poll(), "Value must be " + val);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MinHeapTest.testInsert")
  @Test
  void testInsertSizeOne() {
    IQueue queue = new MinHeap(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), "Peek returns first value inserted.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("MinHeapTest.testInsertException")
  @Test
  void testInsertException() {
    IQueue queue = new MinHeap(1);
    long val = VAL;
    queue.insert(val);
    assertThrows(IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("MinHeapTest.testInsertZeroSizeException")
  @Test
  void testInsertZeroSizeException() {
    IQueue queue = new MinHeap(0);
    long val = VAL;
    assertThrows(IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
  }

  @DisplayName("MinHeapTest.testIsEmpty")
  @Test
  void testIsEmpty() {
    IQueue queue = new MinHeap(0);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MinHeapTest.testIsNotEmpty")
  @Test
  void testIsNotEmpty() {
    IQueue queue = new MinHeap(1);
    queue.insert(VAL);
    assertFalse(queue.isEmpty(), "Queue must not be empty.");
    assertTrue(queue.isFull(), "Queue must be full.");
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("MinHeapTest.testIsEmptySizeOne")
  @Test
  void testIsEmptySizeOne() {
    IQueue queue = new MinHeap(1);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertFalse(queue.isFull(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MinHeapTest.testIsFull")
  @Test
  void testIsFull() {
    IQueue queue = new MinHeap(0);
    assertTrue(queue.isFull(), "Queue must be full.");
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @DisplayName("MinHeapTest.testIsFullSizeOne")
  @Test
  void testIsFullSizeOne() {
    IQueue queue = new MinHeap(1);
    assertFalse(queue.isFull(), QUEUE_EMPTY);
    assertTrue(queue.isEmpty(), QUEUE_EMPTY);
    assertEquals(0, queue.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("MinHeapTest.testPeekEmpty")
  void testPeekEmpty() {
    IQueue queue = new MinHeap(0);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peep throws exception.");
  }

  @DisplayName("MinHeapTest.testPeekEmptyOne")
  @Test
  void testPeekEmptyOne() {
    IQueue queue = new MinHeap(1);
    assertThrows(
        IllegalStateException.class, () -> queue.peek(), "Empty queue peek throws exception.");
  }

  @DisplayName("MinHeapTest.testPeek")
  @Test
  void testPeek() {
    IQueue queue = new MinHeap(1);
    long val = VAL;
    queue.insert(val);
    assertEquals(val, queue.peek(), PEEK_MIN_VALUE);
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("MinHeapTest.testTwoElementQueueInsert")
  @Test
  void testTwoElementQueueInsert() {
    IQueue queue = new MinHeap(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val - 1);
    assertEquals(val - 1, queue.peek(), PEEK_MIN_VALUE);
    assertEquals(2, queue.size(), "Size must be two.");
  }

  @DisplayName("MinHeapTest.testTwoElementQueuePoll")
  @Test
  void testTwoElementQueuePoll() {
    IQueue queue = new MinHeap(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val - 1);
    assertEquals(val - 1, queue.poll(), POLL_MIN_VALUE);
    assertEquals(1, queue.size(), SIZE_ONE);
  }

  @DisplayName("MinHeapTest.testTwoElementQueuePollTwice")
  @Test
  void testTwoElementQueuePollTwice() {
    IQueue queue = new MinHeap(2);
    long val = VAL;
    queue.insert(val);
    queue.insert(val - 1);
    assertEquals(val - 1, queue.poll(), POLL_MIN_VALUE);
    assertEquals(val, queue.poll(), POLL_MIN_VALUE);
    assertEquals(0, queue.size(), SIZE_ONE);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @DisplayName("MinHeapTest.testRandomInsertsMinimum")
  @Test
  void testRandomInsertsMinimum() {
    IQueue queue = new MinHeap(MYRIAD);
    long val = Long.MIN_VALUE;
    queue.insert(val);
    Random random = new Random();
    random.longs().limit(MYRIAD - 1).forEach(i -> queue.insert(i));
    assertEquals(val, queue.peek(), PEEK_MIN_VALUE);
    assertEquals(val, queue.poll(), POLL_MIN_VALUE);
    assertEquals(MYRIAD - 1, queue.size(), "Size must be " + (MYRIAD - 1));
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @DisplayName("MinHeapTest.testReverse")
  @Test
  void testReverse() {
    IQueue queue = new MinHeap(MYRIAD);
    revRange(1, MYRIAD).forEach(i -> queue.insert(i));
    assertEquals(1, queue.peek(), PEEK_MIN_VALUE);
    assertEquals(1, queue.poll(), POLL_MIN_VALUE);
    assertEquals(MYRIAD - 1, queue.size(), "Size must be " + (MYRIAD - 1));
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @DisplayName("MinHeapTest.testDuplicates")
  @Test
  void testDuplicates() {
    long maxVal = 1023L;
    IQueue queue = new MinHeap(MYRIAD);
    LongStream.rangeClosed(1, MYRIAD).forEach(i -> queue.insert(i & maxVal));
    assertEquals(0, queue.peek(), PEEK_MIN_VALUE);
    assertEquals(0, queue.poll(), POLL_MIN_VALUE);
    assertEquals(MYRIAD - 1, queue.size(), "Size must be " + (MYRIAD - 1));
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @DisplayName("MinHeapTest.testSingleValue")
  @Test
  void testSingleValue() {
    final long maxVal = 1023L;
    IQueue queue = new MinHeap(MYRIAD);
    LongStream.iterate(
            maxVal,
            i -> {
              return i;
            })
        .limit(MYRIAD)
        .forEach(i -> queue.insert(i));
    assertEquals(maxVal, queue.peek(), PEEK_MIN_VALUE);
    assertEquals(maxVal, queue.poll(), POLL_MIN_VALUE);
    assertEquals(MYRIAD - 1, queue.size(), "Size must be " + (MYRIAD - 1));
  }
}
