package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.CircularDoublyLinkedList;
import ds.INode;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.JUnitTestContainsTooManyAsserts"})
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

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testConstructor")
  void testConstructor() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertEquals(0, list.size(), SIZE_ZERO);
    assertNull(list.getHead(), "List head must be null.");
    assertNull(list.getTail(), "List tail must be null.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAdd")
  void testAdd() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
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
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddIndex")
  void testAddIndex() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
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
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null, 0), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddIndexException")
  void testAddIndexException() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, -1), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddIndexExcessException")
  void testAddIndexExcessException() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, TEN), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testFind")
  void testFind() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
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
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    list.add(SCORE, 0);
    assertThrows(NullPointerException.class, () -> list.find(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testFindMultiple")
  void testFindMultiple() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.find(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteMultiple")
  void testDeleteMultiple() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN).forEach(i -> assertTrue(list.delete(i), "Deleted true."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteMultipleReverse")
  void testDeleteMultipleReverse() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertTrue(list.delete(TEN - i), "Deleted true for " + (TEN - i) + "."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteNull")
  void testDeleteNull() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.delete(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteNotFound")
  void testDeleteNotFound() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testFindNotFound")
  void testFindNotFound() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertNull(list.find(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testDeleteNotFoundEmpty")
  void testDeleteNotFoundEmpty() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testEmptyToString")
  void testEmptyToString() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertEquals("[]", list.toString(), "Empty array string expected.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testGetMultiple")
  void testGetMultiple() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddAtFirstMultiple")
  void testAddFirstMultiple() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.addAtFirst(TEN - i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddMultiple")
  void testAddMultiple() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(0, TEN).forEach(i -> list.add(i, TEN));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testAddEndMultiple")
  void testAddEndMultiple() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(SCORE, SCORE + TEN).forEach(i -> list.add(i, i));
    IntStream.range(0, SCORE + TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Value must match index."));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testToStringMultiple")
  void testToStringMultiple() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertEquals("[0,1,2,3,4,5,6,7,8,9,10]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testToStringSingle")
  void testToStringSingle() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    IntStream.range(0, 1).forEach(i -> list.add(i));
    assertEquals("[0]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testGetNegativeIndex")
  void testGetNegativeIndex() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1), EXCEPTION);
  }

  @Test
  @DisplayName("CircularDoublyLinkedListTest.testGetExcessIndex")
  void testGetExcessIndex() {
    CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(TEN), EXCEPTION);
  }
}
