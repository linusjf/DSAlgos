package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Tree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("BinaryTreeTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
abstract class AbstractBinaryTreeTest<T extends Comparable<T>> extends BaseTreeTest<T> {

  private Tree<T> empty;
  private Tree<T> one;
  private Tree<T> several;
  private Tree<T> duplicates;
  private T singleElement;
  private T[] singleElementArray;
  private T[] severalElementsArray;
  private T[] severalElementsInOrderArray;
  private T[] severalElementsPreOrderArray;
  private T[] severalElementsPostOrderArray;
  private T[] severalElementsLevelOrderArray;
  private T[] severalNonExistentArray;

  private T duplicateElement;
  private T[] duplicateElementArray;
  private T[] duplicateTwoElementArray;
  private T[] duplicateThreeElementArray;

  abstract Tree<T> emptyTree();

  abstract Tree<T> singleElementTree();

  abstract Tree<T> severalElementsTree();

  abstract Tree<T> duplicatesTree();

  abstract T singleElement();

  abstract T[] singleElementArray();

  abstract T[] severalElementsArray();

  abstract T[] severalElementsInOrderArray();

  abstract T[] severalElementsPreOrderArray();

  abstract T[] severalElementsPostOrderArray();

  abstract T[] severalElementsLevelOrderArray();

  abstract T[] severalNonExistentArray();

  abstract T duplicateElement();

  abstract T[] duplicateElementArray();

  abstract T[] duplicateTwoElementArray();

  abstract T[] duplicateThreeElementArray();

  @BeforeAll
  public void init() {
    singleElement = singleElement();
    singleElementArray = singleElementArray();
    severalElementsArray = severalElementsArray();
    severalElementsInOrderArray = severalElementsInOrderArray();
    severalElementsPreOrderArray = severalElementsPreOrderArray();
    severalElementsPostOrderArray = severalElementsPostOrderArray();
    severalElementsLevelOrderArray = severalElementsLevelOrderArray();
    severalNonExistentArray = severalNonExistentArray();

    duplicateElement = duplicateElement();
    duplicateElementArray = duplicateElementArray();
    duplicateTwoElementArray = duplicateTwoElementArray();
    duplicateThreeElementArray = duplicateThreeElementArray();
  }

  @BeforeEach
  public void setup() {
    empty = emptyTree();
    one = singleElementTree();
    several = severalElementsTree();
    duplicates = duplicatesTree();
  }

  @Test
  @DisplayName("BinaryTreeTest.testEmptyContainsZeroItems")
  public void testEmptyContainsZeroItems() {
    assertTreeEmpty(emptyTree());
  }

  @Test
  @DisplayName("BinaryTreeTest.testEmptyTreeIteratorException")
  public void testEmptyTreeIteratorException() {
    assertTreeEmptyIteratorException(emptyTree());
  }

  @Test
  @DisplayName("BinaryTreeTest.testOneContainsOneItem")
  public void testOneContainsOneItem() {
    assertTrue(
        singleElementTree().contains(singleElement()),
        () -> "One should contain " + singleElement());
    assertIterationValid(singleElementTree(), singleElementArray());
  }

  @Test
  @DisplayName("BinaryTreeTest.testSeveralContainsSixItems")
  public void testSeveralContainsSixItems() {
    assertContains(severalElementsTree(), severalElementsArray());
    assertIterationValid(severalElementsTree(), severalElementsInOrderArray());
  }

  @Test
  @DisplayName("BinaryTreeTest.testPreOrderIteration")
  public void testPreOrderIteration() {
    assertPreOrderIterationValid(severalElementsTree(), severalElementsPreOrderArray());
  }

  @Test
  @DisplayName("BinaryTreeTest.testPostOrderIteration")
  public void testPostOrderIteration() {
    assertPostOrderIterationValid(severalElementsTree(), severalElementsPostOrderArray());
  }

  @Test
  @DisplayName("BinaryTreeTest.testBreadthFirstOrderIteration")
  public void testBreadthFirstOrderIteration() {
    assertBreadthFirstOrderIterationValid(severalElementsTree(), severalElementsLevelOrderArray());
  }

  @Test
  @DisplayName("BinaryTreeTest.testSeveralDoesNotContain")
  public void testSeveralDoesNotContain() {
    assertDoesNotContain(severalElementsTree(), severalNonExistentArray());
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveFromEmpty")
  public void testRemoveFromEmpty() {
    emptyTree().remove(singleElement());
    assertTreeEmpty(emptyTree());
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveFromOne")
  public void testRemoveFromOne() {
    singleElementTree().remove(singleElement());
    assertFalse(
        singleElementTree().contains(singleElement()),
        () -> singleElement() + "not removed from one");
    assertTreeEmpty(singleElementTree());
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveByLeaf")
  public void testRemoveByLeaf() {
    assertRemoveAll(severalElementsTree(), severalElementsArray());
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveByRoot")
  public void testRemoveByRoot() {
    assertRemoveAll(severalElementsTree(), severalElementsArray());
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("BinaryTreeTest.testDuplicates")
  public void testDuplicates() {
    emptyTree().add(duplicateElement());
    emptyTree().add(duplicateElement());
    emptyTree().add(duplicateElement());
    assertIterationValid(emptyTree(), duplicateThreeElementArray());
    assertTrue(
        emptyTree().contains(duplicateElement()), () -> "Should contain " + duplicateElement());
    emptyTree().remove(duplicateElement());
    assertTrue(
        emptyTree().contains(duplicateElement()),
        () -> "Should still contain " + duplicateElement());
    assertIterationValid(emptyTree(), duplicateTwoElementArray);
    emptyTree().remove(duplicateElement());
    assertTrue(
        emptyTree().contains(duplicateElement()),
        () -> "Should still contain " + duplicateElement());
    assertIterationValid(emptyTree(), duplicateElementArray());
    emptyTree().remove(duplicateElement());
    assertFalse(
        emptyTree().contains(duplicateElement()), () -> "Should not contain " + duplicateElement());
    assertTreeEmpty(emptyTree());
  }
}
