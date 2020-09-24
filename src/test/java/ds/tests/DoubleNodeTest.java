package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.DoubleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("DoubleNodeTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class DoubleNodeTest {

  @Test
  @DisplayName("DoubleNodeTest.testConstructor")
  void testConstructor() {
    DoubleNode<Integer> node = new DoubleNode<>(SCORE);
    assertEquals(SCORE, node.getData(), "Value equals constructor parameter.");
    assertNull(node.getNext(), "Next must be null.");
    assertNull(node.getPrev(), "Prev must be null.");
  }

  @SuppressWarnings("nullness:argument.type.incompatible")
  @Test
  @DisplayName("DoubleNodeTest.testSetPrev")
  void testSetPrev() {
    DoubleNode<Integer> node = new DoubleNode<>(SCORE);
    DoubleNode<Integer> prev = new DoubleNode<>(SCORE - 1);
    node.setPrev(prev);
    assertSame(prev, node.getPrev(), "References must be equal.");
    assertEquals(SCORE - 1, node.getPrev().getData(), "Values must be equal.");
  }

  @Test
  @DisplayName("DoubleNodeTest.testSetNext")
  void testSetNext() {
    DoubleNode<Integer> node = new DoubleNode<>(SCORE);
    DoubleNode<Integer> next = new DoubleNode<>(TEN);
    node.setNext(next);
    assertSame(next,node.getNext(), "References must be equal.");
    assertEquals(TEN, node.getNext().getData(), () -> "Value must be " + TEN + ".");
  }

  @Test
  @DisplayName("DoubleNodeTest.testSetData")
  void testSetData() {
    DoubleNode<Integer> node = new DoubleNode<>(SCORE);
    node.setData(TEN);
    assertEquals(TEN, node.getData(), () -> "Value must be " + TEN + ".");
  }

  @Test
  @DisplayName("DoubleNodeTest.testDistinctCompareFalse")
  void testDistinctCompareFalse() {
    DoubleNode<Integer> node = new DoubleNode<>(SCORE);
    DoubleNode<Integer> next = new DoubleNode<>(TEN);
    assertFalse(node.distinctCompare(next), () -> "Two different objects.");
  }

  @Test
  @DisplayName("DoubleNodeTest.testDistinctCompareTrue")
  void testDistinctCompareTrue() {
    DoubleNode<Integer> node = new DoubleNode<>(SCORE);
    assertTrue(node.distinctCompare(node), () -> "Same object.");
  }
}
