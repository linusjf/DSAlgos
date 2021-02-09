package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Tree;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

  private Tree<T> empty;
  private Tree<T> one;
  private Tree<T> several;
  private Tree<T> duplicates;
  private T singleElement;
  private List<T> singleElementList;
  private List<T> severalElementsList;
  private List<T> severalElementsInOrderList;
  private List<T> severalElementsPreOrderList;
  private List<T> severalElementsPostOrderList;
  private List<T> severalElementsLevelOrderList;
  private List<T> severalNonExistentList;

  private T duplicateElement;
  private List<T> duplicateElementList;
  private List<T> duplicateTwoElementList;
  private List<T> duplicateThreeElementList;

  abstract Tree<T> emptyTree();

  abstract Tree<T> singleElementTree();

  abstract Tree<T> severalElementsTree();

  abstract Tree<T> duplicatesTree();

  abstract T singleElement();

  abstract List<T> singleElementList();

  abstract List<T> severalElementsList();

  abstract List<T> severalElementsInOrderList();

  abstract List<T> severalElementsPreOrderList();

  abstract List<T> severalElementsPostOrderList();

  abstract List<T> severalElementsLevelOrderList();

  abstract List<T> severalNonExistentList();

  abstract T duplicateElement();

  abstract List<T> duplicateElementList();

  abstract List<T> duplicateTwoElementList();

  abstract List<T> duplicateThreeElementList();

  @BeforeAll
  public void init() {
    singleElement = singleElement();
    singleElementList = singleElementList();
    severalElementsList = severalElementsList();
    severalElementsInOrderList = severalElementsInOrderList();
    severalElementsPreOrderList = severalElementsPreOrderList();
    severalElementsPostOrderList = severalElementsPostOrderList();
    severalElementsLevelOrderList = severalElementsLevelOrderList();
    severalNonExistentList = severalNonExistentList();

    duplicateElement = duplicateElement();
    duplicateElementList = duplicateElementList();
    duplicateTwoElementList = duplicateTwoElementList();
    duplicateThreeElementList = duplicateThreeElementList();
  }

  @BeforeEach
  public void setup() {
    empty = emptyTree();
    one = singleElementTree();
    several = severalElementsTree();
    duplicates = duplicatesTree();
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
    assertTrue(
        empty.contains(duplicateElement), () -> "Should still contain " + duplicateElement());
    assertIterationValid(empty, duplicateElementList);
    empty.remove(duplicateElement);
    assertFalse(empty.contains(duplicateElement), () -> "Should not contain " + duplicateElement);
    assertTreeEmpty(empty);
  }
}
