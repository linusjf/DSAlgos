package ds.tests;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import ds.BinaryTree;
import ds.Tree;
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
class BinaryTreeTest extends BaseTreeTest<Integer> {

  private Tree<Integer> empty;
  private Tree<Integer> one;
  private Tree<Integer> several;
  private Tree<Integer> duplicates;

  @BeforeEach
  public void setUp() {
    empty = new BinaryTree<>();
    one = new BinaryTree<>();
    one.add(0);
    several = new BinaryTree<>();
    several.add(5);
    several.add(2);
    several.add(1);
    several.add(9);
    several.add(8);
    several.add(10);
    duplicates = new BinaryTree<>();
    duplicates.add(5);
    duplicates.add(3);
    duplicates.add(3);
    duplicates.add(5);
    duplicates.add(8);
    duplicates.add(5);
    duplicates.add(10);
  }

  @Test
  @DisplayName("BinaryTreeTest.testEmptyContainsZeroItems")
  public void testEmptyContainsZeroItems() {
    assertTreeEmpty(empty);
  }

  @Test
  @DisplayName("BinaryTreeTest.testEmptyTreeIteratorException")
  public void testEmptyTreeIteratorException() {
    assertTreeEmptyIteratorException(empty);
  }

  @Test
  @DisplayName("BinaryTreeTest.testOneContainsOneItem")
  public void testOneContainsOneItem() {
    assertTrue(one.contains(0), "One should contain 0");
    assertIterationValid(one, asList(new Integer[] {0}));
  }

  @Test
  @DisplayName("BinaryTreeTest.testSeveralContainsSixItems")
  public void testSeveralContainsSixItems() {
    assertContains(several, asList(new Integer[] {1, 2, 5, 8, 9, 10}));
    assertIterationValid(several, asList(new Integer[] {1, 2, 5, 8, 9, 10}));
  }

  @Test
  @DisplayName("BinaryTreeTest.testPreOrderIteration")
  public void testPreOrderIteration() {
    assertPreOrderIterationValid(several, asList(new Integer[] {5, 2, 1, 9, 8, 10}));
  }

  @Test
  @DisplayName("BinaryTreeTest.testPostOrderIteration")
  public void testPostOrderIteration() {
    assertPostOrderIterationValid(several, asList(new Integer[] {1, 2, 8, 10, 9, 5}));
  }

  @Test
  @DisplayName("BinaryTreeTest.testBreadthFirstOrderIteration")
  public void testBreadthFirstOrderIteration() {
    assertBreadthFirstOrderIterationValid(several, asList(new Integer[] {5, 2, 9, 1, 8, 10}));
  }

  @Test
  @DisplayName("BinaryTreeTest.testSeveralDoesNotContain")
  public void testSeveralDoesNotContain() {
    assertDoesNotContain(several, asList(new Integer[] {-1, 0, 3, 4, 6, 7, 11}));
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveFromEmpty")
  public void testRemoveFromEmpty() {
    empty.remove(0);
    assertTreeEmpty(empty);
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveFromOne")
  public void testRemoveFromOne() {
    one.remove(0);
    assertFalse(one.contains(0), "0 not removed from one");
    assertTreeEmpty(one);
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveByLeaf")
  public void testRemoveByLeaf() {
    assertRemoveAll(several, asList(new Integer[] {5, 2, 1, 8, 10, 9, 5}));
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveByRoot")
  public void testRemoveByRoot() {
    assertRemoveAll(several, asList(new Integer[] {5, 8, 9, 10, 2, 1}));
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("BinaryTreeTest.testDuplicates")
  public void testDuplicates() {
    empty.add(1);
    empty.add(1);
    empty.add(1);
    assertIterationValid(empty, asList(new Integer[] {1, 1, 1}));
    assertTrue(empty.contains(1), "Should contain 1");
    empty.remove(1);
    assertTrue(empty.contains(1), "Should still contain 1");
    assertIterationValid(empty, asList(new Integer[] {1, 1}));
    empty.remove(1);
    assertTrue(empty.contains(1), "Should still contain 1");
    assertIterationValid(empty, asList(new Integer[] {1}));
    empty.remove(1);
    assertFalse(empty.contains(1), "Should not contain 1");
    assertTreeEmpty(empty);
  }
}
