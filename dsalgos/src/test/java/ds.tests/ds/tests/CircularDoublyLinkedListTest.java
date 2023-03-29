package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.CircularDoublyLinkedList;
import ds.IList;
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

@SuppressWarnings({
  "PMD.LawOfDemeter",
  "PMD.JUnitTestContainsTooManyAsserts",
  "PMD.GodClass",
  "initialization.field.uninitialized"
})
@DisplayName("CircularDoublyLinkedListTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class CircularDoublyLinkedListTest {
  private static final String SIZE_MUST_BE = "Size must be ";
  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String NULL_POINTER = "NullPointerException expected.";
  private static final String EXCEPTION = "Exception expected.";
  private static final String VALUES_EQUAL = "Values must be equal.";
  private static final String VALUE_MUST_BE = "Value must be ";
  private static final String NO_ELEMENTS = "No elements expected.";
  private static final String ELEMENTS = "More elements expected.";
  private static final int THREE = 3;

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testConstructor")
  void testConstructor() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertEquals(0, list.size(), SIZE_ZERO);
    assertNull(list.getHead(), "List head must be null.");
    assertNull(list.getTail(), "List tail must be null.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAdd")
  void testAdd() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    list.add(SCORE);
    INode<Integer> head = list.getHead();
    INode<Integer> tail = list.getTail();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertNotNull(tail, "List tail must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), VALUES_EQUAL);
    assertEquals(String.valueOf(SCORE), tail.toString(), VALUES_EQUAL);
    assertSame(head, tail, "Same object.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddNull")
  void testAddNull() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddIndex")
  void testAddIndex() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> head = list.getHead();
    INode<Integer> tail = list.getTail();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertNotNull(tail, "List tail must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), VALUES_EQUAL);
    assertEquals(String.valueOf(SCORE), tail.toString(), VALUES_EQUAL);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddIndexNull")
  void testAddIndexNull() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null, 0), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddIndexException")
  void testAddIndexException() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, -1), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddIndexExcessException")
  void testAddIndexExcessException() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, TEN), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testFind")
  void testFind() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> node = list.find(Integer.valueOf(SCORE));
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(node, "Node must not be null.");
    assertEquals(String.valueOf(SCORE), node.toString(), VALUES_EQUAL);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("CircularDoublyLinkedListTest.testFindNull")
  void testFindNull() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    list.add(SCORE, 0);
    assertThrows(NullPointerException.class, () -> list.find(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testFindMultiple")
  void testFindMultiple() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.find(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteMultiple")
  void testDeleteMultiple() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN).forEach(i -> assertTrue(list.delete(i), "Deleted true."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteMultipleReverse")
  void testDeleteMultipleReverse() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertTrue(list.delete(TEN - i), "Deleted true for " + (TEN - i) + "."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteNull")
  void testDeleteNull() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.delete(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteNotFound")
  void testDeleteNotFound() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteAt")
  void testDeleteAt() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(
            i ->
                assertEquals(
                    TEN - i, list.deleteAt(TEN - i), "Deleted at index " + (TEN - i) + "."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteAtBefore")
  void testDeleteAtBefore() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(-1), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteAtAfter")
  void testDeleteAtAfter() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(SCORE), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteAtEmpty")
  void testDeleteAtEmpty() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(0), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testFindNotFound")
  void testFindNotFound() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertNull(list.find(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteNotFoundEmpty")
  void testDeleteNotFoundEmpty() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testEmptyToString")
  void testEmptyToString() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertEquals("[]", list.toString(), "Empty array string expected.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testGetMultiple")
  void testGetMultiple() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddAtFirstMultiple")
  void testAddFirstMultiple() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.addAtFirst(TEN - i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddMultiple")
  void testAddMultiple() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(0, TEN).forEach(i -> list.add(i, TEN));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddEndMultiple")
  void testAddEndMultiple() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(SCORE, SCORE + TEN).forEach(i -> list.add(i, i));
    IntStream.range(0, SCORE + TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Value must match index."));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testToStringMultiple")
  void testToStringMultiple() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertEquals("[0,1,2,3,4,5,6,7,8,9,10]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testToStringSingle")
  void testToStringSingle() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.range(0, 1).forEach(i -> list.add(i));
    assertEquals("[0]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testGetNegativeIndex")
  void testGetNegativeIndex() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testGetExcessIndex")
  void testGetExcessIndex() {
    IList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(TEN), EXCEPTION);
  }

  @Nested
  class IteratorTests {
    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testEmptyIterator")
    void testEmptyIterator() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      ListIterator<Integer> iter = list.getIterator();
      assertFalse(iter.hasNext(), NO_ELEMENTS);
      assertFalse(iter.hasPrevious(), NO_ELEMENTS);
      assertThrows(NoSuchElementException.class, () -> iter.next(), EXCEPTION);
      assertThrows(IllegalStateException.class, () -> iter.remove(), EXCEPTION);
      assertThrows(NoSuchElementException.class, () -> iter.previous(), EXCEPTION);
      assertThrows(IllegalStateException.class, () -> iter.set(SCORE), EXCEPTION);
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testAddEmpty")
    void testAddEmpty() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      ListIterator<Integer> iter = list.getIterator();
      iter.add(TEN);
      iter.add(SCORE);
      iter.add(1);
      while (iter.hasNext()) {
        iter.next();
        iter.remove();
      }
      assertEquals(0, list.size(), SIZE_ZERO);
      assertFalse(iter.hasNext(), NO_ELEMENTS);
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testAddIterated")
    void testAddIterated() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      list.add(TEN);
      list.add(SCORE);
      list.add(1);
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) {
        int val = iter.next();
        iter.remove();
        iter.add(val + 1);
        iter.add(val - 1);
        if (++i == THREE) break;
      }
      assertEquals(6, list.size(), SIZE_MUST_BE + 6);
      assertTrue(iter.hasNext(), ELEMENTS);
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testAddIteratedCheck")
    void testAddIteratedCheck() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
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
        if (list.size() >= HUNDRED) break;
      }
      assertEquals(HUNDRED, list.size(), SIZE_MUST_BE + HUNDRED);
      assertTrue(iter.hasNext(), "More elements expected.");
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testPrevious")
    void testPrevious() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) {
        iter.next();
        if (iter.nextIndex() == 0) break;
      }
      int i = SCORE - 1;
      assertEquals(list.size() - 1, iter.previousIndex(), VALUES_EQUAL);
      while (iter.hasPrevious()) {
        Integer val = iter.previous();
        assertEquals(i--, val, VALUES_EQUAL);
        if (iter.previousIndex() == list.size() - 1) break;
      }
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testSinglePrevious")
    void testSinglePrevious() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      list.add(TEN);
      ListIterator<Integer> iter = list.getIterator();
      assertEquals(list.getHead().getData(), iter.previous(), VALUES_EQUAL);
      assertEquals(list.getHead().getData(), iter.next(), VALUES_EQUAL);
      assertEquals(list.getTail().getData(), iter.next(), VALUES_EQUAL);
      assertEquals(list.getTail().getData(), iter.previous(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testNext")
    void testNext() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) {
        assertEquals(i++, iter.next(), VALUES_EQUAL);
        if (i == SCORE) break;
      }
      assertEquals(SCORE, i, () -> VALUE_MUST_BE + SCORE);
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testRemove")
    void testRemove() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
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
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testAdd")
    void testAdd() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) {
        iter.next();
        iter.add(i++);
        if (list.size() == SCORE * 2) break;
      }
      assertEquals(SCORE * 2, list.size(), SIZE_MUST_BE + (SCORE * 2));
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testSet")
    void testSet() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = SCORE;
      while (iter.hasNext()) {
        iter.next();
        iter.set(i++);
        if (iter.nextIndex() == 0) break;
      }
      ListIterator<Integer> iter2 = list.getIterator();
      IntStream.range(0, SCORE).forEach(j -> assertEquals(SCORE + j, iter2.next(), VALUES_EQUAL));
      assertEquals(SCORE, list.size(), SIZE_MUST_BE + SCORE);
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testNextIndex")
    void testNextIndex() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) {
        assertEquals(i++, iter.nextIndex(), VALUES_EQUAL);
        iter.next();
        if (iter.nextIndex() == 0) break;
      }
      assertEquals(0, iter.nextIndex(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testPreviousIndex")
    void testPreviousIndex() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) {
        iter.next();
        if (iter.nextIndex() == 0) break;
      }
      int i = SCORE;
      assertEquals(--i, iter.previousIndex(), VALUES_EQUAL);
      while (iter.hasPrevious()) {
        assertEquals(i--, iter.previousIndex(), VALUES_EQUAL);
        iter.previous();
        if (iter.previousIndex() == list.size() - 1) break;
      }
      assertEquals(list.size() - 1, iter.previousIndex(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testIndexedIterator")
    void testIndexedIterator() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIteratorFromIndex(TEN);
      int i = TEN;
      while (iter.hasNext()) {
        int val = iter.next();
        assertEquals(i++, val, VALUES_EQUAL);
        if (i == SCORE) {
          assertEquals(SCORE - 1, val, VALUES_EQUAL);
          break;
        }
      }
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testIterable")
    void testIterable() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      int i = 0;
      for (Integer item : list) {
        assertEquals(i++, item, VALUES_EQUAL);
        if (i == SCORE) break;
      }
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testAddAfterIteration")
    void testAddAfterIteration() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) {
        iter.next();
        if (iter.nextIndex() == 0) break;
      }
      iter.add(SCORE);
      assertEquals(SCORE + 1, list.size(), () -> SIZE_MUST_BE + (SCORE + 1));
    }

    @Test
    @DisplayName("CircularDoublyLinkedListTest.IteratorTests.testIndexedIteratorReversed")
    void testIndexedIteratorReversed() {
      IList<Integer> list = new CircularDoublyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIteratorFromIndex(TEN);
      int i = TEN;
      while (iter.hasPrevious()) {
        assertEquals(--i, iter.previous(), VALUES_EQUAL);
        if (i == 0) break;
      }
    }
  }
}
