package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.INode;
import ds.SinglyLinkedList;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("SinglyLinkedListTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class SinglyLinkedListTest {
  private static final String SIZE_ZERO = "Size must be zero.";
  private static final String SIZE_ONE = "Size must be one.";
  private static final String NULL_POINTER = "NullPointerException expected.";
  private static final String EXCEPTION = "Exception expected.";

  @Test
  @DisplayName("SinglyLinkedListTest.testConstructor")
  void testConstructor() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertEquals(0, list.size(), SIZE_ZERO);
    assertNull(list.getHead(), "List head must be null.");
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Test
  @DisplayName("SinglyLinkedListTest.testAdd")
  void testAdd() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE);
    INode<Integer> head = list.getHead();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), "Values must be equal.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testAddNull")
  void testAddNull() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(
        NullPointerException.class, () -> list.add(null), NULL_POINTER);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndex")
  void testAddIndex() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> head = list.getHead();
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(head, "List head must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), "Values must be equal.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndexNull")
  void testAddIndexNull() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(
        NullPointerException.class, () -> list.add(null, 0), NULL_POINTER);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndexException")
  void testAddIndexException() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(SCORE, -1), EXCEPTION);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Test
  @DisplayName("SinglyLinkedListTest.testAddIndexExcessException")
  void testAddIndexExcessException() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(
        IndexOutOfBoundsException.class, () -> list.add(SCORE, TEN), EXCEPTION);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Test
  @DisplayName("SinglyLinkedListTest.testFind")
  void testFind() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE, 0);
    INode<Integer> node = list.find(Integer.valueOf(SCORE));
    assertEquals(1, list.size(), SIZE_ONE);
    assertNotNull(node, "Node must not be null.");
    assertEquals(String.valueOf(SCORE), node.toString(), "Values must be equal.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testFindNull")
  void testFindNull() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE, 0);
    assertThrows(
        NullPointerException.class, () -> list.find(null), NULL_POINTER);
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("SinglyLinkedListTest.testDeleteNull")
  void testDeleteNull() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertThrows(
        NullPointerException.class, () -> list.delete(null), NULL_POINTER);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Test
  @DisplayName("SinglyLinkedListTest.testEmptyToString")
  void testEmptyToString() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertEquals("[]", list.toString(), "Empty array string expected.");
  }
  
  @SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert",
  "PMD.LawOfDemeter"})
  @Test
  @DisplayName("SinglyLinkedListTest.testGetMultiple")
  void testGetMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    IntStream.rangeClosed(0, TEN)
        .forEach(i -> assertEquals(i, list.get(i).getData(), "Values must equal index."));
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Test
  @DisplayName("SinglyLinkedListTest.testToStringMultiple")
  void testToStringMultiple() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    IntStream.rangeClosed(0, TEN).forEach(i -> list.add(i));
    assertEquals("[0,1,2,3,4,5,6,7,8,9,10]", list.toString(), "Strings must be equal.");
  }

  @SuppressWarnings("PMD.LawOfDemeter")
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
}
