package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.SingleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("SingleNodeTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class SingleNodeTest {
  private static final String EXCEPTION_EXPECTED = "Exception expected.";

  @Test
  @DisplayName("SingleNodeTest.testConstructor")
  void testConstructor() {
    SingleNode<Integer> node = new SingleNode<Integer>(SCORE);
    assertEquals(SCORE, node.getData(), "Value equals constructor parameter.");
    assertNull(node.getNext(), "Next must be null.");
    assertThrows(UnsupportedOperationException.class, () -> node.getPrev(), EXCEPTION_EXPECTED);
  }

  @Test
  @DisplayName("SingleNodeTest.testSetPrev")
  void testSetPrev() {
    SingleNode<Integer> node = new SingleNode<Integer>(SCORE);
    assertThrows(UnsupportedOperationException.class, () -> node.setPrev(null), EXCEPTION_EXPECTED);
  }

  @Test
  @DisplayName("SingleNodeTest.testSetNext")
  void testSetNext() {
    SingleNode<Integer> node = new SingleNode<Integer>(SCORE);
    SingleNode<Integer> next = new SingleNode<Integer>(TEN);
    node.setNext(next);
    assertEquals(TEN, node.getNext().getData(), () -> "Value must be " + TEN + ".");
  }

  @Test
  @DisplayName("SingleNodeTest.testDistinctCompareFalse")
  void testDistinctCompareFalse() {
    SingleNode<Integer> node = new SingleNode<Integer>(SCORE);
    SingleNode<Integer> next = new SingleNode<Integer>(TEN);
    assertFalse(node.distinctCompare(next), () -> "Two different objects.");
  }

  @Test
  @DisplayName("SingleNodeTest.testDistinctCompareTrue")
  void testDistinctCompareTrue() {
    SingleNode<Integer> node = new SingleNode<Integer>(SCORE);
    assertTrue(node.distinctCompare(node), () -> "Same object.");
  }
}
