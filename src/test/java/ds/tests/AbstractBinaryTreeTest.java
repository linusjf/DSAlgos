package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Tree;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AvoidFieldNameMatchingMethodName", "PMD.TooManyFields"})
@DisplayName("AbstractBinaryTreeTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
abstract class AbstractBinaryTreeTest<T extends Comparable<T>> extends BaseTreeTest<T> {

  Tree<T> empty;
  Tree<T> one;
  Tree<T> several;
  Tree<T> duplicates;
  Tree<T> randomTree;
  T singleElement;
  List<T> singleElementList;
  List<T> severalElementsList;
  List<T> severalElementsInOrderList;
  List<T> severalElementsPreOrderList;
  List<T> severalElementsPostOrderList;
  List<T> severalElementsLevelOrderList;
  List<T> severalNonExistentList;

  T duplicateElement;
  List<T> duplicateElementList;
  List<T> duplicateTwoElementList;
  List<T> duplicateThreeElementList;

  @Test
  @DisplayName("AbstractBinaryTreeTest.testRandomInserts")
  public void testRandomInserts() {
    for (T s : randomTree.values()) assertEquals(s, randomTree.find(s).value(), "Value not found!");
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testRandomRemoves")
  public void testRandomRemoves() {
    for (T s : randomTree.values()) randomTree.remove(s);
    assertTreeEmpty(randomTree);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testEmptyContainsZeroItems")
  public void testEmptyContainsZeroItems() {
    assertTreeEmpty(empty);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testEmptyTreeIteratorException")
  public void testEmptyTreeIteratorException() {
    assertTreeEmptyIteratorException(empty);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testOneContainsOneItem")
  public void testOneContainsOneItem() {
    assertTrue(one.contains(singleElement), () -> "One should contain " + singleElement);
    assertIterationValid(one, singleElementList);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testSeveralContainsSixItems")
  public void testSeveralContainsSixItems() {
    assertContains(several, severalElementsList);
    assertIterationValid(several, severalElementsInOrderList);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testPreOrderIteration")
  public void testPreOrderIteration() {
    assertPreOrderIterationValid(several, severalElementsPreOrderList);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testPostOrderIteration")
  public void testPostOrderIteration() {
    assertPostOrderIterationValid(several, severalElementsPostOrderList);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testBreadthFirstOrderIteration")
  public void testBreadthFirstOrderIteration() {
    assertBreadthFirstOrderIterationValid(several, severalElementsLevelOrderList);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testSeveralDoesNotContain")
  public void testSeveralDoesNotContain() {
    assertDoesNotContain(several, severalNonExistentList);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testRemoveFromEmpty")
  public void testRemoveFromEmpty() {
    empty.remove(singleElement);
    assertTreeEmpty(empty);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testRemoveFromOne")
  public void testRemoveFromOne() {
    one.remove(singleElement);
    assertFalse(one.contains(singleElement), () -> singleElement + "not removed from one");
    assertTreeEmpty(one);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testRemoveByLeaf")
  public void testRemoveByLeaf() {
    assertRemoveAll(several, severalElementsList);
  }

  @Test
  @DisplayName("AbstractBinaryTreeTest.testRemoveByRoot")
  public void testRemoveByRoot() {
    assertRemoveAll(several, severalElementsList);
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("AbstractBinaryTreeTest.testDuplicates")
  public void testDuplicates() {
    empty.add(duplicateElement);
    empty.add(duplicateElement);
    empty.add(duplicateElement);
    assertIterationValid(empty, duplicateThreeElementList);
    assertTrue(empty.contains(duplicateElement), () -> "Should contain " + duplicateElement);
    empty.remove(duplicateElement);
    assertTrue(empty.contains(duplicateElement), () -> "Should still contain " + duplicateElement);
    assertIterationValid(empty, duplicateTwoElementList);
    empty.remove(duplicateElement);
    assertTrue(empty.contains(duplicateElement), () -> "Should still contain " + duplicateElement);
    assertIterationValid(empty, duplicateElementList);
    empty.remove(duplicateElement);
    assertFalse(empty.contains(duplicateElement), () -> "Should not contain " + duplicateElement);
    assertTreeEmpty(empty);
  }
}
