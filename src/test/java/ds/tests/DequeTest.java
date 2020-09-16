package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.Deque;
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

@DisplayName("DequeTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class DequeTest {
  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String SIZE_THREE = "Size must be three.";
  private static final String QUEUE_EMPTY = "Queue must be empty.";
  private static final String VALUE_MUST_BE = "Value must be ";

  private static final long VAL = 20;

  @Nested
  class ConstructorTests {
    @Test
    @DisplayName("DequeTest.ConstructorTests.testConstructorException")
    void testConstructorException() {
      assertThrows(
          NegativeArraySizeException.class, () -> new Deque(-1), "Constructor throws exception.");
    }
  }

  @Nested
  class QueueTests {
    @Test
    @DisplayName("DequeTest.QueueTests.testPollEmpty")
    void testPollEmpty() {
      IQueue queue = new Deque(0);
      assertThrows(
          IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
    }

    @Test
    @DisplayName("DequeTest.QueueTests.testSizeEmpty")
    void testSizeEmpty() {
      IQueue queue = new Deque(0);
      assertEquals(0, queue.size(), "Size must be zero.");
    }

    @Test
    @DisplayName("DequeTest.QueueTests.testSizeOne")
    void testSizeOne() {
      IQueue queue = new Deque(TEN);
      queue.insert(SCORE);
      assertEquals(1, queue.size(), "Size must be one.");
    }

    @Test
    @DisplayName("DequeTest.QueueTests.testSizeTwo")
    void testSizeTwo() {
      IQueue queue = new Deque(TEN);
      queue.insert(SCORE);
      queue.insert(SCORE + 1);
      assertEquals(2, queue.size(), "Size must be two.");
    }

    @Test
    @DisplayName("DequeTest.QueueTests.testSizeEmptyNonZero")
    void testSizeEmptyNonZero() {
      IQueue queue = new Deque(1);
      assertEquals(0, queue.size(), "Size must be zero.");
    }

    @DisplayName("DequeTest.QueueTests.testPollEmptyOne")
    @Test
    void testPollEmptyOne() {
      IQueue queue = new Deque(1);
      assertThrows(
          IllegalStateException.class, () -> queue.poll(), "Empty queue poll throws exception.");
    }

    @DisplayName("DequeTest.QueueTests.testPoll")
    @Test
    void testPoll() {
      IQueue queue = new Deque(1);
      long val = VAL;
      queue.insert(val);
      assertEquals(val, queue.poll(), "Poll returns first value inserted.");
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("DequeTest.QueueTests.testTwoElementQueuePoll")
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

    @DisplayName("DequeTest.QueueTests.testCircularInsert")
    @Test
    void testCircularInsert() {
      IQueue queue = new Deque(1);
      long val = VAL;
      queue.insert(val);
      queue.poll();
      queue.insert(val);
      assertEquals(1, queue.size(), SIZE_ONE);
    }

    @DisplayName("DequeTest.QueueTests.testCircularPoll")
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

    @DisplayName("DequeTest.QueueTests.testInsert")
    @Test
    void testInsertSizeOne() {
      IQueue queue = new Deque(1);
      long val = VAL;
      queue.insert(val);
      assertEquals(val, queue.peek(), "Peek returns first value inserted.");
      assertEquals(1, queue.size(), SIZE_ONE);
    }

    @DisplayName("DequeTest.QueueTests.testInsertResize")
    @Test
    void testInsertResize() {
      IQueue queue = new Deque(1);
      long val = VAL;
      queue.insert(val);
      queue.insert(val);
      assertEquals(2, queue.size(), "Size must be two.");
    }

    @DisplayName("DequeTest.QueueTests.testInsertZeroSizeException")
    @Test
    void testInsertZeroSizeException() {
      IQueue queue = new Deque(0);
      long val = VAL;
      assertThrows(
          IllegalStateException.class, () -> queue.insert(val), "Insert throws exception.");
    }

    @DisplayName("DequeTest.QueueTests.testIsEmpty")
    @Test
    void testIsEmpty() {
      IQueue queue = new Deque(0);
      assertTrue(queue.isEmpty(), QUEUE_EMPTY);
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("DequeTest.QueueTests.testIsNotEmpty")
    @Test
    void testIsNotEmpty() {
      IQueue queue = new Deque(1);
      queue.insert(VAL);
      assertFalse(queue.isEmpty(), "Queue must not be empty.");
      assertTrue(queue.isFull(), "Queue must be full.");
      assertEquals(1, queue.size(), SIZE_ONE);
    }

    @DisplayName("DequeTest.QueueTests.testIsEmptySizeOne")
    @Test
    void testIsEmptySizeOne() {
      IQueue queue = new Deque(1);
      assertTrue(queue.isEmpty(), QUEUE_EMPTY);
      assertFalse(queue.isFull(), QUEUE_EMPTY);
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("DequeTest.QueueTests.testIsFull")
    @Test
    void testIsFull() {
      IQueue queue = new Deque(0);
      assertTrue(queue.isFull(), "Queue must be full.");
      assertTrue(queue.isEmpty(), QUEUE_EMPTY);
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @DisplayName("DequeTest.QueueTests.testIsFullSizeOne")
    @Test
    void testIsFullSizeOne() {
      IQueue queue = new Deque(1);
      assertFalse(queue.isFull(), QUEUE_EMPTY);
      assertTrue(queue.isEmpty(), QUEUE_EMPTY);
      assertEquals(0, queue.size(), SIZE_ZERO);
    }

    @Test
    @DisplayName("DequeTest.QueueTests.testPeekEmpty")
    void testPeekEmpty() {
      IQueue queue = new Deque(0);
      assertThrows(
          IllegalStateException.class, () -> queue.peek(), "Empty queue peep throws exception.");
    }

    @DisplayName("DequeTest.QueueTests.testPeekEmptyOne")
    @Test
    void testPeekEmptyOne() {
      IQueue queue = new Deque(1);
      assertThrows(
          IllegalStateException.class, () -> queue.peek(), "Empty queue peek throws exception.");
    }

    @DisplayName("DequeTest.QueueTests.testPeek")
    @Test
    void testPeek() {
      IQueue queue = new Deque(1);
      long val = VAL;
      queue.insert(val);
      assertEquals(val, queue.peek(), "Peek returns first value inserted.");
      assertEquals(1, queue.size(), SIZE_ONE);
    }
  }

  @Nested
  class StackTests {
    @Test
    @DisplayName("DequeTest.StackTests.testPopEmpty")
    void testPopEmpty() {
      IStack stack = new Deque(0);
      assertThrows(
          IllegalStateException.class, () -> stack.pop(), "Empty stack pop throws exception.");
    }

    @DisplayName("DequeTest.StackTests.testPopEmptyOne")
    @Test
    void testPopEmptyOne() {
      IStack stack = new Deque(1);
      assertThrows(
          IllegalStateException.class, () -> stack.pop(), "Empty stack pop throws exception.");
    }

    @DisplayName("DequeTest.StackTests.testPop")
    @Test
    void testPop() {
      IStack stack = new Deque(1);
      long val = VAL;
      stack.push(val);
      assertEquals(val, stack.pop(), "Pop returns last value pushed.");
    }

    @DisplayName("DequeTest.StackTests.testPush")
    @Test
    void testPushSizeOne() {
      IStack stack = new Deque(1);
      long val = VAL;
      stack.push(val);
      assertEquals(val, stack.peek(), "Pop returns last value pushed.");
    }

    @DisplayName("DequeTest.StackTests.testPushException")
    @Test
    void testPushException() {
      IStack stack = new Deque(1);
      long val = VAL;
      stack.push(val);
      stack.push(val);
      assertEquals(2, stack.size(), "Stack is of size two.");
    }

    @DisplayName("DequeTest.StackTests.testPushZeroSizeException")
    @Test
    void testPushZeroSizeException() {
      IStack stack = new Deque(0);
      long val = VAL;
      assertThrows(IllegalStateException.class, () -> stack.push(val), "Push throws exception.");
    }
  }

  @Nested
  class DequeTests {
    @DisplayName("DequeTest.DequeTests.testAddFirstThrice")
    @Test
    void testAddFirstThrice() {
      IDeque deque = new Deque(3);
      deque.addFirst(VAL - 1);
      deque.addFirst(VAL);
      deque.addFirst(VAL + 1);
      assertEquals(3, deque.size(), SIZE_THREE);
    }

    @DisplayName("DequeTest.DequeTests.testAddLastThrice")
    @Test
    void testAddLastThrice() {
      IDeque deque = new Deque(3);
      deque.addLast(VAL - 1);
      deque.addLast(VAL);
      deque.addLast(VAL + 1);
      assertEquals(3, deque.size(), SIZE_THREE);
    }

    @DisplayName("DequeTest.DequeTests.testLastIndexWrapAround")
    @Test
    void testAddLastIndexWrapAround() {
      IDeque deque = new Deque(3);
      deque.addLast(VAL - 1);
      deque.addLast(VAL);
      deque.addLast(VAL + 1);
      deque.pollFirst();
      deque.addLast(VAL - 1);
      assertEquals(3, deque.size(), SIZE_THREE);
      assertEquals(VAL, deque.peekFirst(), VALUE_MUST_BE + VAL + ".");
    }

    @DisplayName("DequeTest.DequeTests.testPollLastIndexWrapAround")
    @Test
    void testPollLastIndexWrapAround() {
      IDeque deque = new Deque(3);
      deque.addLast(VAL - 1);
      deque.addLast(VAL);
      deque.addLast(VAL + 1);
      deque.pollLast();
      deque.addLast(VAL + 1);
      assertEquals(3, deque.size(), SIZE_THREE);
      assertEquals(VAL + 1, deque.peekLast(), VALUE_MUST_BE + (VAL + 1) + ".");
    }

    @DisplayName("DequeTest.DequeTests.testAddPollLast")
    @Test
    void testAddPollLast() {
      IDeque deque = new Deque(3);
      deque.addLast(VAL - 1);
      deque.addFirst(VAL + 1);
      deque.pollLast();
      assertEquals(1, deque.size(), SIZE_THREE);
      assertEquals(VAL + 1, deque.peekLast(), VALUE_MUST_BE + (VAL + 1) + ".");
    }

    @DisplayName("DequeTest.DequeTests.testPollLastReverse")
    @Test
    void testPollLastReverse() {
      IDeque deque = new Deque(3);
      deque.addLast(VAL - 1);
      deque.addLast(VAL);
      deque.pollLast();
      deque.addLast(VAL + 1);
      assertEquals(2, deque.size(), "Size must be two.");
      assertEquals(VAL + 1, deque.peekLast(), VALUE_MUST_BE + (VAL + 1) + ".");
    }

    @DisplayName("DequeTest.DequeTests.testPollFirstIndexWrapAround")
    @Test
    void testPollFirstIndexWrapAround() {
      IDeque deque = new Deque(3);
      deque.addFirst(VAL - 1);
      deque.addFirst(VAL);
      deque.pollFirst();
      deque.addFirst(VAL + 1);
      assertEquals(2, deque.size(), "Size must be 3.");
      assertEquals(VAL + 1, deque.peekFirst(), VALUE_MUST_BE + (VAL + 1) + ".");
    }

    @DisplayName("DequeTest.DequeTests.testPollLastOneElement")
    @Test
    void testPollLastOneElement() {
      IDeque deque = new Deque(3);
      deque.addFirst(VAL - 1);
      assertEquals(VAL - 1, deque.pollLast(), VALUE_MUST_BE + (VAL - 1) + ".");
    }

    @Test
    @DisplayName("DequeTest.DequeTests.testPollLastSizeOne")
    void testPollLastSizeOne() {
      Deque deque = new Deque(1);
      deque.insert(VAL);
      assertEquals(1, deque.size(), "Size must be one.");
      assertEquals(VAL, deque.pollLast(), VALUE_MUST_BE + VAL + ".");
    }

    @Test
    @DisplayName("DequeTest.DequeTests.testPollFirstEmpty")
    void testPollFirstEmpty() {
      IDeque deque = new Deque(0);
      assertThrows(IllegalStateException.class, () -> deque.pollFirst(), "Exception expected.");
    }

    @Test
    @DisplayName("DequeTest.DequeTests.testPollLastEmpty")
    void testPollLastEmpty() {
      IDeque deque = new Deque(0);
      assertThrows(IllegalStateException.class, () -> deque.pollLast(), "Exception expected.");
    }

    @Test
    @DisplayName("DequeTest.DequeTests.testPeekLastEmpty")
    void testPeekLastEmpty() {
      IDeque deque = new Deque(0);
      assertThrows(IllegalStateException.class, () -> deque.peekLast(), "Exception expected.");
    }

    @Test
    @DisplayName("DequeTest.DequeTests.testInsertFirstDoubling")
    void testInsertFirstDoubling() {
      IDeque deque = new Deque(TEN);
      LongStream.rangeClosed(1, SCORE).forEach(i -> deque.addFirst(i));
      assertEquals(SCORE, deque.peekFirst(), VALUE_MUST_BE + "twenty.");
      assertEquals(1, deque.peekLast(), VALUE_MUST_BE + "one.");
    }

    @Test
    @DisplayName("DequeTest.DequeTests.testInsertLastDoubling")
    void testInsertLastDoubling() {
      IDeque deque = new Deque(TEN);
      LongStream.rangeClosed(1, SCORE).forEach(i -> deque.addLast(i));
      assertEquals(SCORE, deque.peekLast(), VALUE_MUST_BE + "twenty.");
      assertEquals(1, deque.peekFirst(), VALUE_MUST_BE + "one.");
    }
  }
}
