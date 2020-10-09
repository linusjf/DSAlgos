package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.DoublyLinkedList;
import ds.INode;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.JUnitTestContainsTooManyAsserts"})
@DisplayName("DoublyLinkedListTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class DoublyLinkedListTest {
  private static final String SIZE_MUST_BE = "Size must be ";
  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String NULL_POINTER = "NullPointerException expected.";
  private static final String EXCEPTION = "Exception expected.";
  private static final String VALUES_EQUAL = "Values must be equal.";
  private static final String VALUE_MUST_BE = "Value must be ";
  private static final String NO_ELEMENTS = "No elements expected.";

  @Test
  @DisplayName("DoublyLinkedListTest.testConstructor")
  void testConstructor() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertEquals(0, list.size(), SIZE_ZERO);
    assertNull(list.getHead(), "List head must be null.");
    assertNull(list.getTail(), "List tail must be null.");
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testAdd")
  void testAdd() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    list.add(SCORE);
    INode<Integer> head = list.getHead();
    INode<Integer> tail = list.getTail();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertNotNull(tail, "List tail must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), VALUES_EQUAL);
    assertEquals(String.valueOf(SCORE), tail.toString(), VALUES_EQUAL);
    assertSame(head, tail, "Head and tail must be same.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("DoublyLinkedListTest.testAddNull")
  void testAddNull() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null), NULL_POINTER);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testAddIndex")
  void testAddIndex() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> head = list.getHead();
    INode<Integer> tail = list.getTail();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertNotNull(tail, "List tail must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), VALUES_EQUAL);
    assertEquals(String.valueOf(SCORE), tail.toString(), VALUES_EQUAL);
    assertSame(head, tail, "Head and tail must be the same.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("DoublyLinkedListTest.testAddIndexNull")
  void testAddIndexNull() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null, 0), NULL_POINTER);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testAddIndexException")
  void testAddIndexException() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, -1), EXCEPTION);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testAddIndexExcessException")
  void testAddIndexExcessException() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, TEN), EXCEPTION);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testFind")
  void testFind() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> node = list.find(Integer.valueOf(SCORE));
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(node, "Node must not be null.");
    assertEquals(String.valueOf(SCORE), node.toString(), VALUES_EQUAL);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("DoublyLinkedListTest.testFindNull")
  void testFindNull() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    list.add(SCORE, 0);
    assertThrows(NullPointerException.class, () -> list.find(null), NULL_POINTER);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testFindMultiple")
  void testFindMultiple() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.find(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testDeleteMultiple")
  void testDeleteMultiple() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN).forEach(i -> assertTrue(list.delete(i), "Deleted true."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testDeleteMultipleMiddle")
  void testDeleteMultipleMiddle() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, SCORE).forEach(i -> list.add(i));
    IntStream.rangeClosed(TEN, SCORE).forEach(i -> assertTrue(list.delete(i), "Deleted true."));
    assertEquals(TEN, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testDeleteMultipleReverse")
  void testDeleteMultipleReverse() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertTrue(list.delete(TEN - i), "Deleted true for " + (TEN - i) + "."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("DoublyLinkedListTest.testDeleteNull")
  void testDeleteNull() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.delete(null), NULL_POINTER);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testDeleteNotFound")
  void testDeleteNotFound() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testFindNotFound")
  void testFindNotFound() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertNull(list.find(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testDeleteNotFoundEmpty")
  void testDeleteNotFoundEmpty() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testEmptyToString")
  void testEmptyToString() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertEquals("[]", list.toString(), "Empty array string expected.");
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testGetMultiple")
  void testGetMultiple() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testAddAtFirstMultiple")
  void testAddFirstMultiple() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.addAtFirst(TEN - i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testAddMultiple")
  void testAddMultiple() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(0, TEN).forEach(i -> list.add(i, TEN));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testAddEndMultiple")
  void testAddEndMultiple() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(SCORE, SCORE + TEN).forEach(i -> list.add(i, i));
    IntStream.range(0, SCORE + TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Value must match index."));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testToStringMultiple")
  void testToStringMultiple() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertEquals("[0,1,2,3,4,5,6,7,8,9,10]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testToStringSingle")
  void testToStringSingle() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    IntStream.range(0, 1).forEach(i -> list.add(i));
    assertEquals("[0]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testGetNegativeIndex")
  void testGetNegativeIndex() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1), EXCEPTION);
  }

  @Test
  @DisplayName("DoublyLinkedListTest.testGetExcessIndex")
  void testGetExcessIndex() {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(TEN), EXCEPTION);
  }

  @Nested
  class IteratorTests {
    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testEmptyIterator")
    void testEmptyIterator() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      ListIterator<Integer> iter = list.getIterator();
      assertFalse(iter.hasNext(), NO_ELEMENTS);
      assertFalse(iter.hasPrevious(), NO_ELEMENTS);
      assertThrows(NoSuchElementException.class, () -> iter.next(), EXCEPTION);
      assertThrows(IllegalStateException.class, () -> iter.remove(), EXCEPTION);
      assertThrows(NoSuchElementException.class, () -> iter.previous(), EXCEPTION);
      assertThrows(IllegalStateException.class, () -> iter.set(SCORE), EXCEPTION);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testAddEmpty")
    void testAddEmpty() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      ListIterator<Integer> iter = list.getIterator();
      iter.add(TEN);
      iter.add(SCORE);
      iter.add(1);
      while (iter.hasNext()) {
        iter.next();
        iter.remove();
      }
      assertEquals(3, list.size(), SIZE_MUST_BE + 3);
      assertFalse(iter.hasNext(), NO_ELEMENTS);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testAddIterated")
    void testAddIterated() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      list.add(TEN);
      list.add(SCORE);
      list.add(1);
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) {
        int val = iter.next();
        iter.remove();
        iter.add(val + 1);
        iter.add(val - 1);
      }
      assertEquals(6, list.size(), SIZE_MUST_BE + 6);
      assertFalse(iter.hasNext(), NO_ELEMENTS);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testAddIteratedCheck")
    void testAddIteratedCheck() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      list.add(TEN);
      list.add(SCORE);
      list.add(1);
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) {
        int val = iter.next();
        iter.remove();
        iter.add(val + 1);
        if (iter.hasPrevious()) val = iter.previous();
        iter.add(val + 1);
        if (list.size() >= TEN) break;
      }
      assertEquals(TEN, list.size(), SIZE_MUST_BE + HUNDRED);
      assertTrue(iter.hasNext(), "More elements expected.");
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testPrevious")
    void testPrevious() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) iter.next();
      int i = SCORE - 1;
      while (iter.hasPrevious()) {
        Integer val = iter.previous();
        assertEquals(i--, val, VALUES_EQUAL);
      }
      assertEquals(-1, iter.previousIndex(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testPreviousDelete")
    void testPreviousDelete() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) iter.next();
      int i = SCORE - 1;
      while (iter.hasPrevious()) {
        Integer val = iter.previous();
        assertEquals(i--, val, VALUES_EQUAL);
        iter.remove();
      }
      assertEquals(-1, iter.previousIndex(), VALUES_EQUAL);
      assertEquals(0, list.size(), SIZE_ZERO);
      assertFalse(iter.hasNext(), NO_ELEMENTS);
      assertFalse(iter.hasPrevious(), NO_ELEMENTS);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testNext")
    void testNext() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) assertEquals(i++, iter.next(), VALUES_EQUAL);
      assertEquals(SCORE, i, () -> VALUE_MUST_BE + SCORE);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testRemove")
    void testRemove() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) {
        assertEquals(i++, iter.next(), VALUES_EQUAL);
        iter.remove();
      }
      assertEquals(0, list.size(), SIZE_ZERO);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testAdd")
    void testAdd() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) {
        iter.next();
        iter.add(i++);
      }
      assertEquals(SCORE * 2, list.size(), SIZE_MUST_BE + (SCORE * 2));
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testSet")
    void testSet() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = SCORE;
      while (iter.hasNext()) {
        iter.next();
        iter.set(i++);
      }
      ListIterator<Integer> iter2 = list.getIterator();
      IntStream.range(0, SCORE).forEach(j -> assertEquals(SCORE + j, iter2.next(), VALUES_EQUAL));
      assertEquals(SCORE, list.size(), SIZE_MUST_BE + SCORE);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testNextIndex")
    void testNextIndex() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) {
        assertEquals(i++, iter.nextIndex(), VALUES_EQUAL);
        iter.next();
      }
      assertEquals(SCORE, iter.nextIndex(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testPreviousIndex")
    void testPreviousIndex() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) iter.next();
      int i = SCORE;
      assertEquals(--i, iter.previousIndex(), VALUES_EQUAL);
      while (iter.hasPrevious()) {
        iter.previous();
        assertEquals(--i, iter.previousIndex(), VALUES_EQUAL);
      }
      assertEquals(-1, iter.previousIndex(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testAddAfterIteration")
    void testAddAfterIteration() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) iter.next();
      iter.add(SCORE);
      assertEquals(SCORE + 1, list.size(), () -> SIZE_MUST_BE + (SCORE + 1));
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testIndexedIterator")
    void testIndexedIterator() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIteratorFromIndex(TEN);
      int i = TEN;
      while (iter.hasNext()) assertEquals(i++, iter.next(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testIndexedIteratorReversed")
    void testIndexedIteratorReversed() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIteratorFromIndex(TEN);
      int i = TEN;
      while (iter.hasPrevious()) assertEquals(--i, iter.previous(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("DoublyLinkedListTest.IteratorTests.testIterable")
    void testIterable() {
      DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      int i = 0;
      for (Integer item : list) assertEquals(i++, item, VALUES_EQUAL);
    }
  }
}
