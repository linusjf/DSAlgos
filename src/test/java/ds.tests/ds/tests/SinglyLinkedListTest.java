package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.IList;
import ds.INode;
import ds.SinglyLinkedList;
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
  "nullness:generic.argument"
})
@DisplayName("SinglyLinkedListTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class SinglyLinkedListTest {
  private static final String SIZE_MUST_BE = "Size must be ";
  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String NULL_POINTER = "NullPointerException expected.";
  private static final String EXCEPTION = "Exception expected.";
  private static final String VALUES_EQUAL = "Values must be equal.";
  private static final String VALUE_MUST_BE = "Value must be ";
  private static final String NO_ELEMENTS = "No elements expected.";

  @Test
  @DisplayName("SinglyLinkedListTest.testConstructor")
  void testConstructor() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertEquals(0, list.size(), SIZE_ZERO);
    assertNull(list.getHead(), "List head must be null.");
    assertNull(list.getTail(), "List tail must be null.");
    assertTrue(list.isEmpty(), "List must be empty.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAdd")
  void testAdd() {
    IList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE);
    INode<Integer> head = list.getHead();
    INode<Integer> tail = list.getTail();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertNotNull(tail, "List tail must not be null.");
    assertEquals(tail, head, "List head and tail must be same.");
    assertEquals(String.valueOf(SCORE), head.toString(), VALUES_EQUAL);
    assertFalse(list.isEmpty(), "List must not be empty.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testAddNull")
  void testAddNull() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null), NULL_POINTER);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndex")
  void testAddIndex() {
    IList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> head = list.getHead();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), VALUES_EQUAL);
    assertFalse(list.isEmpty(), "List must not be empty.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndexNull")
  void testAddIndexNull() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null, 0), NULL_POINTER);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndexException")
  void testAddIndexException() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, -1), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndexExcessException")
  void testAddIndexExcessException() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, TEN), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testFind")
  void testFind() {
    IList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> node = list.find(Integer.valueOf(SCORE));
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(node, "Node must not be null.");
    assertEquals(String.valueOf(SCORE), node.toString(), VALUES_EQUAL);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testFindNull")
  void testFindNull() {
    IList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE, 0);
    assertThrows(NullPointerException.class, () -> list.find(null), NULL_POINTER);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testFindMultiple")
  void testFindMultiple() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.find(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteMultiple")
  void testDeleteMultiple() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN).forEach(i -> assertTrue(list.delete(i), "Deleted true."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteMultipleReverse")
  void testDeleteMultipleReverse() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertTrue(list.delete(TEN - i), "Deleted true for " + (TEN - i) + "."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteNull")
  void testDeleteNull() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.delete(null), NULL_POINTER);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteNotFound")
  void testDeleteNotFound() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteAt")
  void testDeleteAt() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(
            i ->
                assertEquals(
                    TEN - i, list.deleteAt(TEN - i), "Deleted at index " + (TEN - i) + "."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteAtBefore")
  void testDeleteAtBefore() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(-1), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteAtAfter")
  void testDeleteAtAfter() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(SCORE), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteAtEmpty")
  void testDeleteAtEmpty() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(0), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testFindNotFound")
  void testFindNotFound() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertNull(list.find(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteNotFoundEmpty")
  void testDeleteNotFoundEmpty() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testEmptyToString")
  void testEmptyToString() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertEquals("[]", list.toString(), "Empty array string expected.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testGetMultiple")
  void testGetMultiple() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddAtFirstMultiple")
  void testAddFirstMultiple() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.addAtFirst(TEN - i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(0, list.getHead().getData(), VALUES_EQUAL);
    assertEquals(TEN, list.getTail().getData(), VALUES_EQUAL);
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddMultiple")
  void testAddMultiple() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(0, TEN).forEach(i -> list.add(i, TEN));
    assertEquals(0, list.getHead().getData(), VALUES_EQUAL);
    assertEquals(SCORE - 1, list.getTail().getData(), VALUES_EQUAL);
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddEndMultiple")
  void testAddEndMultiple() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(SCORE, SCORE + TEN).forEach(i -> list.add(i, i));
    IntStream.range(0, SCORE + TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Value must match index."));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testToStringMultiple")
  void testToStringMultiple() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertEquals("[0,1,2,3,4,5,6,7,8,9,10]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testToStringSingle")
  void testToStringSingle() {
    IList<Integer> list = new SinglyLinkedList<>();
    IntStream.range(0, 1).forEach(i -> list.add(i));
    assertEquals("[0]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testGetNegativeIndex")
  void testGetNegativeIndex() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testGetExcessIndex")
  void testGetExcessIndex() {
    IList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(TEN), EXCEPTION);
  }

  @Nested
  class IteratorTests {
    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testEmptyIterator")
    void testEmptyIterator() {
      IList<Integer> list = new SinglyLinkedList<>();
      ListIterator<Integer> iter = list.getIterator();
      assertFalse(iter.hasNext(), NO_ELEMENTS);
      assertFalse(iter.hasPrevious(), NO_ELEMENTS);
      assertThrows(NoSuchElementException.class, () -> iter.next(), EXCEPTION);
      assertThrows(IllegalStateException.class, () -> iter.remove(), EXCEPTION);
      assertThrows(NoSuchElementException.class, () -> iter.previous(), EXCEPTION);
      assertThrows(IllegalStateException.class, () -> iter.set(SCORE), EXCEPTION);
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testAddEmpty")
    void testAddEmpty() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testAddIterated")
    void testAddIterated() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testAddIteratedCheck")
    void testAddIteratedCheck() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testPrevious")
    void testPrevious() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testPreviousDelete")
    void testPreviousDelete() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testNext")
    void testNext() {
      IList<Integer> list = new SinglyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) assertEquals(i++, iter.next(), VALUES_EQUAL);
      assertEquals(SCORE, i, () -> VALUE_MUST_BE + SCORE);
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testRemove")
    void testRemove() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testAdd")
    void testAdd() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testSet")
    void testSet() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testNextIndex")
    void testNextIndex() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testPreviousIndex")
    void testPreviousIndex() {
      IList<Integer> list = new SinglyLinkedList<>();
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
    @DisplayName("SinglyLinkedListTest.IteratorTests.testAddAfterIteration")
    void testAddAfterIteration() {
      IList<Integer> list = new SinglyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIterator();
      while (iter.hasNext()) iter.next();
      iter.add(SCORE);
      assertEquals(SCORE + 1, list.size(), () -> SIZE_MUST_BE + (SCORE + 1));
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testIndexedIterator")
    void testIndexedIterator() {
      IList<Integer> list = new SinglyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIteratorFromIndex(TEN);
      int i = TEN;
      while (iter.hasNext()) assertEquals(i++, iter.next(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testIndexedIteratorReversed")
    void testIndexedIteratorReversed() {
      IList<Integer> list = new SinglyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      ListIterator<Integer> iter = list.getIteratorFromIndex(TEN);
      int i = TEN;
      while (iter.hasPrevious()) assertEquals(--i, iter.previous(), VALUES_EQUAL);
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testIterable")
    void testIterable() {
      IList<Integer> list = new SinglyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      int i = 0;
      for (Integer item : list) assertEquals(i++, item, VALUES_EQUAL);
    }
  }
}
