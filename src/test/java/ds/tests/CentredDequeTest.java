package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.CentredDeque;
import ds.IDeque;
import ds.IQueue;
import ds.IStack;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("CentredDequeTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class CentredDequeTest {
  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String SIZE_THREE = "Size must be three.";
  private static final String QUEUE_EMPTY = "Queue must be empty.";
  private static final String LEFT_FULL = "Left must be full.";
  private static final String RIGHT_FULL = "Right must be full.";
  private static final String QUEUE_FULL = "Queue must be full.";
  private static final String QUEUE_NOT_FULL = "Queue must not be full.";
  private static final String VALUE_MUST_BE = "Value must be ";

  private static final long VAL = 20;

  @Nested
  class ConstructorTests {
    @Test
    @DisplayName("CentredDequeTest.ConstructorTests.testConstructorException")
    void testConstructorException() {
      assertThrows(
          NegativeArraySizeException.class,
          () -> new CentredDeque(-1),
          "Constructor throws exception.");
    }
  }

  @Nested
  class QueueTests {
    @Test
    @DisplayName("CentredDequeTest.QueueTests.testPollEmpty")
    void testPollEmpty() {
      IQueue queue = new CentredDeque(0);
      assertThrows(
          IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
    }

    @Test
    @DisplayName("CentredDequeTest.QueueTests.testSizeEmpty")
    void testSizeEmpty() {
      IQueue queue = new CentredDeque(0);
      assertEquals(0, queue.size(), "Size must be zero.");
    }

    @Test
    @DisplayName("CentredDequeTest.QueueTests.testSizeOne")
    void testSizeOne() {
      IQueue queue = new CentredDeque(TEN);
      queue.insert(SCORE);
      assertEquals(1, queue.size(), "Size must be one.");
    }

    @Test
    @DisplayName("CentredDequeTest.QueueTests.testSizeTwo")
    void testSizeTwo() {
      IQueue queue = new CentredDeque(TEN);
      queue.insert(SCORE);
      queue.insert(SCORE + 1);
      assertEquals(2, queue.size(), "Size must be two.");
    }

    @Test
    @DisplayName("CentredDequeTest.QueueTests.testSizeEmptyNonZero")
    void testSizeEmptyNonZero() {
      IQueue queue = new CentredDeque(1);
      assertEquals(0, queue.size(), "Size must be zero.");
    }

    @DisplayName("CentredDequeTest.QueueTests.testPollEmptyOne")
    @Test
    void testPollEmptyOne() {
      IQueue queue = new CentredDeque(1);
      assertThrows(
          IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
    }

    @DisplayName("CentredDequeTest.QueueTests.testPoll")
    @Test
    void testPoll() {
      IQueue queue = new CentredDeque(1);
      long val = VAL;
      queue.insert(val);
      assertEquals(val, queue.poll(), "Poll returns first value inserted.");
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("CentredDequeTest.QueueTests.testTwoElementQueuePoll")
    @Test
    void testTwoElementQueuePoll() {
      IQueue queue = new CentredDeque(2);
      long val = VAL;
      queue.insert(val);
      queue.insert(val + 1);
      assertEquals(val, queue.poll(), "Poll returns first value inserted.");
      assertEquals(val + 1, queue.poll(), "Poll returns second value inserted.");
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("CentredDequeTest.QueueTests.testInsert")
    @Test
    void testInsertSizeOne() {
      IQueue queue = new CentredDeque(1);
      long val = VAL;
      queue.insert(val);
      assertEquals(val, queue.peek(), "Peek returns first value inserted.");
      assertEquals(1, queue.size(), SIZE_ONE);
    }

    @DisplayName("CentredDequeTest.QueueTests.testInsertResize")
    @Test
    void testInsertResize() {
      IQueue queue = new CentredDeque(1);
      long val = VAL;
      queue.insert(val);
      queue.insert(val);
      assertEquals(2, queue.size(), "Size must be two.");
    }

    @DisplayName("CentredDequeTest.QueueTests.testInsertZeroSizeException")
    @Test
    void testInsertZeroSizeException() {
      IQueue queue = new CentredDeque(0);
      long val = VAL;
      assertThrows(
          IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
    }

    @DisplayName("CentredDequeTest.QueueTests.testIsEmpty")
    @Test
    void testIsEmpty() {
      IQueue queue = new CentredDeque(0);
      assertTrue(queue.isEmpty(), QUEUE_EMPTY);
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("CentredDequeTest.QueueTests.testIsNotEmpty")
    @Test
    void testIsNotEmpty() {
      IQueue queue = new CentredDeque(1);
      queue.insert(VAL);
      assertFalse(queue.isEmpty(), "Queue must not be empty.");
      assertFalse(queue.isFull(), "Queue must not be full.");
      assertEquals(1, queue.size(), SIZE_ONE);
    }

    @DisplayName("CentredDequeTest.QueueTests.testIsEmptySizeOne")
    @Test
    void testIsEmptySizeOne() {
      IQueue queue = new CentredDeque(1);
      assertTrue(queue.isEmpty(), QUEUE_EMPTY);
      assertFalse(queue.isFull(), QUEUE_EMPTY);
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("CentredDequeTest.QueueTests.testIsFull")
    @Test
    void testIsFull() {
      IQueue queue = new CentredDeque(0);
      assertTrue(queue.isFull(), QUEUE_FULL);
      assertTrue(queue.isEmpty(), QUEUE_EMPTY);
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("CentredDequeTest.QueueTests.testIsFullSizeOne")
    @Test
    void testIsFullSizeOne() {
      IQueue queue = new CentredDeque(1);
      assertFalse(queue.isFull(), QUEUE_EMPTY);
      assertTrue(queue.isEmpty(), QUEUE_EMPTY);
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @Test
    @DisplayName("CentredDequeTest.QueueTests.testPeekEmpty")
    void testPeekEmpty() {
      IQueue queue = new CentredDeque(0);
      assertThrows(
          IllegalStateException.class, () -> queue.peek(), "Empty queue peep throws exception.");
    }

    @DisplayName("CentredDequeTest.QueueTests.testPeekEmptyOne")
    @Test
    void testPeekEmptyOne() {
      IQueue queue = new CentredDeque(1);
      assertThrows(
          IllegalStateException.class, () -> queue.peek(), "Empty queue peek throws exception.");
    }

    @DisplayName("CentredDequeTest.QueueTests.testPeek")
    @Test
    void testPeek() {
      IQueue queue = new CentredDeque(1);
      long val = VAL;
      queue.insert(val);
      assertEquals(val, queue.peek(), "Peek returns first value inserted.");
      assertEquals(1, queue.size(), SIZE_ONE);
    }
  }

  @Nested
  class StackTests {
    @Test
    @DisplayName("CentredDequeTest.StackTests.testPopEmpty")
    void testPopEmpty() {
      IStack stack = new CentredDeque(0);
      assertThrows(
          IllegalStateException.class, () -> stack.pop(), "Empty stack pop throws exception.");
    }

    @DisplayName("CentredDequeTest.StackTests.testPopEmptyOne")
    @Test
    void testPopEmptyOne() {
      IStack stack = new CentredDeque(1);
      assertThrows(
          IllegalStateException.class, () -> stack.pop(), "Empty stack pop throws exception.");
    }

    @DisplayName("CentredDequeTest.StackTests.testPop")
    @Test
    void testPop() {
      IStack stack = new CentredDeque(1);
      long val = VAL;
      stack.push(val);
      assertEquals(val, stack.pop(), "Pop returns last value pushed.");
    }

    @DisplayName("CentredDequeTest.StackTests.testPush")
    @Test
    void testPushSizeOne() {
      IStack stack = new CentredDeque(1);
      long val = VAL;
      stack.push(val);
      assertEquals(val, stack.peek(), "Pop returns last value pushed.");
    }

    @DisplayName("CentredDequeTest.StackTests.testPushException")
    @Test
    void testPushException() {
      IStack stack = new CentredDeque(1);
      long val = VAL;
      stack.push(val);
      stack.push(val);
      assertEquals(2, stack.size(), "Stack is of size two.");
    }

    @DisplayName("CentredDequeTest.StackTests.testPushZeroSizeException")
    @Test
    void testPushZeroSizeException() {
      IStack stack = new CentredDeque(0);
      long val = VAL;
      assertThrows(IllegalStateException.class, () -> stack.push(val), "Push throws exception.");
    }
  }

  @Nested
  class CentredDequeTests {
    @DisplayName("CentredDequeTest.CentredDequeTests.testAddFirstThrice")
    @Test
    void testAddFirstThrice() {
      IDeque deque = new CentredDeque(3);
      deque.addFirst(VAL - 1);
      deque.addFirst(VAL);
      deque.addFirst(VAL + 1);
      assertEquals(3, deque.size(), SIZE_THREE);
    }

    @DisplayName("CentredDequeTest.CentredDequeTests.testAddLastThrice")
    @Test
    void testAddLastThrice() {
      IDeque deque = new CentredDeque(3);
      deque.addLast(VAL - 1);
      deque.addLast(VAL);
      deque.addLast(VAL + 1);
      assertEquals(3, deque.size(), SIZE_THREE);
    }

    @DisplayName("CentredDequeTest.CentredDequeTests.testAddPollLast")
    @Test
    void testAddPollLast() {
      IDeque deque = new CentredDeque(3);
      deque.addLast(VAL - 1);
      deque.addFirst(VAL + 1);
      deque.pollLast();
      assertEquals(1, deque.size(), SIZE_THREE);
      assertEquals(VAL + 1, deque.peekLast(), () -> VALUE_MUST_BE + (VAL + 1) + ".");
    }

    @DisplayName("CentredDequeTest.CentredDequeTests.testPollLastReverse")
    @Test
    void testPollLastReverse() {
      IDeque deque = new CentredDeque(3);
      deque.addLast(VAL - 1);
      deque.addLast(VAL);
      deque.pollLast();
      deque.addLast(VAL + 1);
      assertEquals(2, deque.size(), "Size must be two.");
      assertEquals(VAL + 1, deque.peekLast(), VALUE_MUST_BE + (VAL + 1) + ".");
    }

    @DisplayName("CentredDequeTest.CentredDequeTests.testPollFirstIndexWrapAround")
    @Test
    void testPollFirstIndexWrapAround() {
      IDeque deque = new CentredDeque(3);
      deque.addFirst(VAL - 1);
      deque.addFirst(VAL);
      deque.pollFirst();
      deque.addFirst(VAL + 1);
      assertEquals(2, deque.size(), "Size must be 3.");
      assertEquals(VAL + 1, deque.peekFirst(), VALUE_MUST_BE + (VAL + 1) + ".");
    }

    @DisplayName("CentredDequeTest.CentredDequeTests.testPollFirstOneElement")
    @Test
    void testPollFirstOneElement() {
      IDeque deque = new CentredDeque(3);
      deque.addFirst(VAL - 1);
      assertEquals(VAL - 1, deque.pollFirst(), VALUE_MUST_BE + (VAL - 1) + ".");
    }

    @Test
    @DisplayName("CentredDequeTest.CentredDequeTests.testPollLastSizeOne")
    void testPollLastSizeOne() {
      CentredDeque deque = new CentredDeque(1);
      deque.insert(VAL);
      assertEquals(1, deque.size(), "Size must be one.");
      assertEquals(VAL, deque.pollLast(), VALUE_MUST_BE + VAL + ".");
    }

    @Test
    @DisplayName("CentredDequeTest.CentredDequeTests.testPollFirstEmpty")
    void testPollFirstEmpty() {
      IDeque deque = new CentredDeque(0);
      assertThrows(IllegalStateException.class, () -> deque.pollFirst(), "Exception expected.");
    }

    @Test
    @DisplayName("CentredDequeTest.CentredDequeTests.testPollLastEmpty")
    void testPollLastEmpty() {
      IDeque deque = new CentredDeque(0);
      assertThrows(IllegalStateException.class, () -> deque.pollLast(), "Exception expected.");
    }

    @Test
    @DisplayName("CentredDequeTest.CentredDequeTests.testPeekLastEmpty")
    void testPeekLastEmpty() {
      IDeque deque = new CentredDeque(0);
      assertThrows(IllegalStateException.class, () -> deque.peekLast(), "Exception expected.");
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    @Test
    @DisplayName("CentredDequeTest.CentredDequeTests.testInsertFirstDoubling")
    void testInsertFirstDoubling() {
      IDeque deque = new CentredDeque(TEN);
      LongStream.rangeClosed(1, SCORE).forEach(i -> deque.addFirst(i));
      assertEquals(SCORE, deque.peekFirst(), VALUE_MUST_BE + "twenty.");
      assertEquals(SCORE, deque.size(), "Size must be twenty.");
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    @Test
    @DisplayName("CentredDequeTest.CentredDequeTests.testInsertLastDoubling")
    void testInsertLastDoubling() {
      IDeque deque = new CentredDeque(TEN);
      LongStream.rangeClosed(1, SCORE).forEach(i -> deque.addLast(i));
      assertEquals(SCORE, deque.peekLast(), VALUE_MUST_BE + "twenty.");
      assertEquals(SCORE, deque.size(), "Size must be twenty.");
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    @Test
    @DisplayName("CentredDequeTest.CentredDequeTests.testDoublingRemoval")
    void testDoublingRemoval() {
      IDeque deque = new CentredDeque(SCORE);
      LongStream.rangeClosed(1, TEN)
          .forEach(
              i -> {
                deque.addLast(i);
                deque.addFirst(i);
              });
      LongStream.rangeClosed(1, TEN)
          .forEach(
              i -> {
                deque.pollLast();
                deque.pollFirst();
              });
      assertEquals(0, deque.size(), SIZE_ZERO);
    }

    @DisplayName("CentredDequeTest.CentredDequeTests.testIsFullSizeOne")
    @Test
    void testIsFull() {
      IDeque deque = new CentredDeque(4);
      deque.addFirst(VAL);
      deque.addFirst(VAL);
      deque.addLast(VAL);
      deque.addLast(VAL);
      assertTrue(deque.isFull(), QUEUE_FULL);
      assertFalse(deque.isEmpty(), QUEUE_FULL);
      assertEquals(4, deque.size(), SIZE_ZERO);
    }

    @DisplayName("CentredDequeTest.CentredDequeTests.testIsLeftFull")
    @Test
    void testIsLeftFull() {
      CentredDeque deque = new CentredDeque(4);
      deque.addFirst(VAL);
      deque.addFirst(VAL);
      assertTrue(deque.isLeftFull(), LEFT_FULL);
      assertFalse(deque.isFull(), QUEUE_NOT_FULL);
      assertEquals(2, deque.size(), SIZE_ZERO);
    }

    @DisplayName("CentredDequeTest.CentredDequeTests.testIsRightFull")
    @Test
    void testIsRightFull() {
      CentredDeque deque = new CentredDeque(4);
      deque.addLast(VAL);
      deque.addLast(VAL);
      assertTrue(deque.isRightFull(), RIGHT_FULL);
      assertFalse(deque.isFull(), QUEUE_NOT_FULL);
      assertEquals(2, deque.size(), SIZE_ZERO);
    }
  }
}
