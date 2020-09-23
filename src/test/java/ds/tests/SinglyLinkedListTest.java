package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.SingleNode;
import ds.SinglyLinkedList;
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

  @Test
  @DisplayName("SinglyLinkedListTest.testConstructor")
  void testConstructor() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    assertEquals(0, list.size(), "Size must be zero.");
    assertNull(list.getHead(), "List head must be null.");
  }

  @Test
  @DisplayName("SinglyLinkedListTest.testAdd")
  void testAdd() {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    list.add(SCORE);
    SingleNode<Integer> head = list.getHead();
    assertEquals(1, list.size(), "Size must be zero.");
    assertNotNull(head, "List head must not be null.");
    assertEquals(String.valueOf(SCORE), head.toString(), "Values must be equal.");
  }
}
