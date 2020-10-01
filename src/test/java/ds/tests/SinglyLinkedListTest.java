package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.INode;
import ds.Iterator;
import ds.SinglyLinkedList;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings({"PMD.LawOfDemeter",
"PMD.JUnitTestContainsTooManyAsserts"})
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

  @Test
  @DisplayName("SinglyLinkedListTest.testConstructor")
  void testConstructor() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertEquals(0, list.size(), SIZE_ZERO);
    assertNull(list.getHead(), "List head must be null.");
    assertTrue(list.isEmpty(), "List must be empty.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAdd")
  void testAdd() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE);
    INode<Integer> head = list.getHead();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), VALUES_EQUAL);
    assertFalse(list.isEmpty(), "List must not be empty.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testAddNull")
  void testAddNull() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null), NULL_POINTER);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndex")
  void testAddIndex() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
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
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null, 0), NULL_POINTER);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndexException")
  void testAddIndexException() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, -1), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndexExcessException")
  void testAddIndexExcessException() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, TEN), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testFind")
  void testFind() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
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
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE, 0);
    assertThrows(NullPointerException.class, () -> list.find(null), NULL_POINTER);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testFindMultiple")
  void testFindMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.find(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteMultiple")
  void testDeleteMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN).forEach(i -> assertTrue(list.delete(i), "Deleted true."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteMultipleReverse")
  void testDeleteMultipleReverse() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertTrue(list.delete(TEN - i), "Deleted true for " + (TEN - i) + "."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteNull")
  void testDeleteNull() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.delete(null), NULL_POINTER);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteNotFound")
  void testDeleteNotFound() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testFindNotFound")
  void testFindNotFound() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertNull(list.find(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteNotFoundEmpty")
  void testDeleteNotFoundEmpty() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testEmptyToString")
  void testEmptyToString() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertEquals("[]", list.toString(), "Empty array string expected.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testGetMultiple")
  void testGetMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddAtFirstMultiple")
  void testAddFirstMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.addAtFirst(TEN - i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddMultiple")
  void testAddMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(0, TEN).forEach(i -> list.add(i, TEN));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAddEndMultiple")
  void testAddEndMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(SCORE, SCORE + TEN).forEach(i -> list.add(i, i));
    IntStream.range(0, SCORE + TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Value must match index."));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testToStringMultiple")
  void testToStringMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertEquals("[0,1,2,3,4,5,6,7,8,9,10]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testToStringSingle")
  void testToStringSingle() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.range(0, 1).forEach(i -> list.add(i));
    assertEquals("[0]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testGetNegativeIndex")
  void testGetNegativeIndex() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1), EXCEPTION);
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testGetExcessIndex")
  void testGetExcessIndex() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(TEN), EXCEPTION);
  }

  @Nested
  class IteratorTests {
    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testEmptyIterator")
    void testEmptyIterator() {
      SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
      Iterator<Integer> iter = list.getIterator();
      assertFalse(iter.hasNext(), "No elements expected.");
      assertFalse(iter.hasPrevious(), "No elements expected.");
      assertThrows(NoSuchElementException.class, () -> iter.next(), EXCEPTION);
      assertThrows(IllegalStateException.class, () -> iter.remove(), EXCEPTION);
      assertThrows(UnsupportedOperationException.class, () -> iter.previous(), EXCEPTION);
      assertThrows(IllegalStateException.class, () -> iter.set(SCORE), EXCEPTION);
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testInsertAfterException")
    void testInsertAfterException() {
      SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
      Iterator<Integer> iter = list.getIterator();
      assertThrows(IllegalStateException.class, () -> iter.insertAfter(TEN), EXCEPTION);
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testInsertBeforeException")
    void testInsertBeforeException() {
      SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
      Iterator<Integer> iter = list.getIterator();
      assertThrows(UnsupportedOperationException.class, () -> iter.insertBefore(TEN), EXCEPTION);
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testIterateNext")
    void testIterateNext() {
      SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      Iterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) assertEquals(i++, iter.next(), VALUES_EQUAL);
      assertEquals(SCORE, i, () -> VALUE_MUST_BE + SCORE);
    }

    @Test
    @DisplayName("SinglyLinkedListTest.IteratorTests.testIterateRemove")
    void testIterateRemove() {
      SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
      IntStream.range(0, SCORE).forEach(i -> list.add(i));
      Iterator<Integer> iter = list.getIterator();
      int i = 0;
      while (iter.hasNext()) {
        assertEquals(i, iter.next(), VALUES_EQUAL);
        assertEquals(i, iter.remove(), VALUES_EQUAL);
        ++i;
      }
      assertEquals(0, list.size(), SIZE_ZERO);
    }
  }
}
