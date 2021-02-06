package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.BinaryTree;
import ds.Tree;
import ds.Tree.TraversalOrder;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
class BinaryTreeTest {

  private static final String MISSING_FROM_TREE = " missing from tree";
  private static final String NOT_REACHED = "Not reached end of iteration";

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
    assertIterationValid(one, new int[] {0});
  }

  @Test
  @DisplayName("BinaryTreeTest.testSeveralContainsSixItems")
  public void testSeveralContainsSixItems() {
    assertContains(several, new int[] {1, 2, 5, 8, 9, 10});
    assertIterationValid(several, new int[] {1, 2, 5, 8, 9, 10});
  }

  @Test
  @DisplayName("BinaryTreeTest.testPreOrderIteration")
  public void testPreOrderIteration() {
    assertPreOrderIterationValid(several, new int[] {5, 2, 1, 9, 8, 10});
  }

  @Test
  @DisplayName("BinaryTreeTest.testPostOrderIteration")
  public void testPostOrderIteration() {
    assertPostOrderIterationValid(several, new int[] {1, 2, 8, 10, 9, 5});
  }

  @Test
  @DisplayName("BinaryTreeTest.testBreadthFirstOrderIteration")
  public void testBreadthFirstOrderIteration() {
    assertBreadthFirstOrderIterationValid(several, new int[] {5, 2, 9, 1, 8, 10});
  }

  @Test
  @DisplayName("BinaryTreeTest.testSeveralDoesNotContain")
  public void testSeveralDoesNotContain() {
    assertDoesNotContain(several, new int[] {-1, 0, 3, 4, 6, 7, 11});
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
    assertRemoveAll(several, new int[] {5, 2, 1, 8, 10, 9, 5});
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveByRoot")
  public void testRemoveByRoot() {
    assertRemoveAll(several, new int[] {5, 8, 9, 10, 2, 1});
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("BinaryTreeTest.testDuplicates")
  public void testDuplicates() {
    empty.add(1);
    empty.add(1);
    empty.add(1);
    assertIterationValid(empty, new int[] {1, 1, 1});
    assertTrue(empty.contains(1), "Should contain 1");
    empty.remove(1);
    assertTrue(empty.contains(1), "Should still contain 1");
    assertIterationValid(empty, new int[] {1, 1});
    empty.remove(1);
    assertTrue(empty.contains(1), "Should still contain 1");
    assertIterationValid(empty, new int[] {1});
    empty.remove(1);
    assertFalse(empty.contains(1), "Should not contain 1");
    assertTreeEmpty(empty);
  }

  private void assertTreeEmpty(Tree<Integer> tree) {
    Iterator<Integer> iterator = tree.iterator(TraversalOrder.PRE_ORDER);
    assertFalse(iterator.hasNext(), "Tree empty");
    assertNull(tree.root(), "Root must be null.");
  }

  private void assertTreeEmptyIteratorException(Tree<Integer> tree) {
    Iterator<Integer> iterator = tree.iterator(TraversalOrder.PRE_ORDER);
    assertThrows(NoSuchElementException.class, () -> iterator.next(), "Tree empty: exception");
  }

  private void assertRemoveAll(Tree<Integer> tree, int... elements) {
    for (int elem : elements) {
      tree.remove(elem);
      assertFalse(tree.contains(elem), () -> elem + " Still in tree after being removed");
    }
    assertTreeEmpty(tree);
  }

  private void assertContains(Tree<Integer> tree, int... elements) {
    for (int elem : elements) assertTrue(tree.contains(elem), () -> elem + " not in tree");
  }

  private void assertDoesNotContain(Tree<Integer> tree, int... elements) {
    for (int elem : elements)
      assertFalse(tree.contains(elem), () -> elem + " unexpectedly found in tree");
  }

  private void assertIterationValid(Tree<Integer> tree, int... elements) {
    Iterator<Integer> iterator = tree.iterator(TraversalOrder.IN_ORDER);
    for (int elem : elements)
      assertEquals(Integer.valueOf(elem), iterator.next(), () -> elem + MISSING_FROM_TREE);
    assertFalse(iterator.hasNext(), NOT_REACHED);
  }

  private void assertPreOrderIterationValid(Tree<Integer> tree, int... elements) {
    Iterator<Integer> iterator = tree.iterator(TraversalOrder.PRE_ORDER);
    for (int elem : elements)
      assertEquals(Integer.valueOf(elem), iterator.next(), () -> elem + MISSING_FROM_TREE);
    assertFalse(iterator.hasNext(), NOT_REACHED);
  }

  private void assertPostOrderIterationValid(Tree<Integer> tree, int... elements) {
    Iterator<Integer> iterator = tree.iterator(TraversalOrder.POST_ORDER);
    for (int elem : elements)
      assertEquals(Integer.valueOf(elem), iterator.next(), () -> elem + MISSING_FROM_TREE);
    assertFalse(iterator.hasNext(), NOT_REACHED);
  }

  private void assertBreadthFirstOrderIterationValid(Tree<Integer> tree, int... elements) {
    Iterator<Integer> iterator = tree.iterator(TraversalOrder.BREADTH_FIRST_ORDER);
    for (int elem : elements)
      assertEquals(Integer.valueOf(elem), iterator.next(), () -> elem + MISSING_FROM_TREE);
    assertFalse(iterator.hasNext(), NOT_REACHED);
  }
}
