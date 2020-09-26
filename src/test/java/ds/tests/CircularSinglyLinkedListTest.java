package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.CircularSinglyLinkedList;
import ds.INode;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.JUnitTestContainsTooManyAsserts"})
@DisplayName("CircularSinglyLinkedListTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class CircularSinglyLinkedListTest {
  private static final String SIZE_MUST_BE = "Size must be ";
  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String NULL_POINTER = "NullPointerException expected.";
  private static final String EXCEPTION = "Exception expected.";
  private static final String VALUES_EQUAL = "Values must be equal.";

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testConstructor")
  void testConstructor() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertEquals(0, list.size(), SIZE_ZERO);
    assertNull(list.getHead(), "List head must be null.");
    assertNull(list.getTail(), "List tail must be null.");
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testAdd")
  void testAdd() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
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
  @DisplayName("CircularSinglyLinkedListTest.testAddNull")
  void testAddNull() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testAddIndex")
  void testAddIndex() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
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
  @DisplayName("CircularSinglyLinkedListTest.testAddIndexNull")
  void testAddIndexNull() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.add(null, 0), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testAddIndexException")
  void testAddIndexException() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, -1), EXCEPTION);
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testAddIndexExcessException")
  void testAddIndexExcessException() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, TEN), EXCEPTION);
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testFind")
  void testFind() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> node = list.find(Integer.valueOf(SCORE));
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(node, "Node must not be null.");
    assertEquals(String.valueOf(SCORE), node.toString(), VALUES_EQUAL);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("CircularSinglyLinkedListTest.testFindNull")
  void testFindNull() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    list.add(SCORE, 0);
    assertThrows(NullPointerException.class, () -> list.find(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testFindMultiple")
  void testFindMultiple() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.find(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testDeleteMultiple")
  void testDeleteMultiple() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN).forEach(i -> assertTrue(list.delete(i), "Deleted true."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testDeleteMultipleReverse")
  void testDeleteMultipleReverse() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertTrue(list.delete(TEN - i), "Deleted true for " + (TEN - i) + "."));
    assertEquals(0, list.size(), SIZE_ZERO);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("CircularSinglyLinkedListTest.testDeleteNull")
  void testDeleteNull() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertThrows(NullPointerException.class, () -> list.delete(null), NULL_POINTER);
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testDeleteNotFound")
  void testDeleteNotFound() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testFindNotFound")
  void testFindNotFound() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertNull(list.find(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testDeleteNotFoundEmpty")
  void testDeleteNotFoundEmpty() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertFalse(list.delete(SCORE), "Not found expected.");
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testEmptyToString")
  void testEmptyToString() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertEquals("[]", list.toString(), "Empty array string expected.");
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testGetMultiple")
  void testGetMultiple() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testAddAtFirstMultiple")
  void testAddFirstMultiple() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.addAtFirst(TEN - i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
    assertEquals(TEN + 1, list.size(), () -> SIZE_MUST_BE + (TEN + 1));
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testAddMultiple")
  void testAddMultiple() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(0, TEN).forEach(i -> list.add(i, TEN));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testAddEndMultiple")
  void testAddEndMultiple() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.range(0, SCORE).forEach(i -> list.add(i));
    IntStream.range(SCORE, SCORE + TEN).forEach(i -> list.add(i, i));
    IntStream.range(0, SCORE + TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Value must match index."));
    assertEquals(TEN + SCORE, list.size(), () -> SIZE_MUST_BE + (TEN + SCORE));
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testToStringMultiple")
  void testToStringMultiple() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertEquals("[0,1,2,3,4,5,6,7,8,9,10]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testToStringSingle")
  void testToStringSingle() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    IntStream.range(0, 1).forEach(i -> list.add(i));
    assertEquals("[0]", list.toString(), "Strings must be equal.");
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testGetNegativeIndex")
  void testGetNegativeIndex() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1), EXCEPTION);
  }

  @Test
  @DisplayName("CircularSinglyLinkedListTest.testGetExcessIndex")
  void testGetExcessIndex() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(TEN), EXCEPTION);
  }
}
