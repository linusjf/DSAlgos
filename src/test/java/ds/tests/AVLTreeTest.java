package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.AVLTree;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("AVLTreeTest")
@SuppressWarnings("PMD.LawOfDemeter")
class AVLTreeTest extends BaseTreeTest<Integer> {

  private AVLTree<Integer> empty;
  private AVLTree<Integer> one;
  private AVLTree<Integer> several;
  private AVLTree<Integer> duplicates;

  @BeforeEach
  public void setUp() {
    empty = new AVLTree<>();
    one = new AVLTree<>();
    one.add(0);
    several = new AVLTree<>();
    several.add(5);
    several.add(2);
    several.add(1);
    several.add(9);
    several.add(8);
    several.add(10);
    duplicates = new AVLTree<>();
    duplicates.add(5);
    duplicates.add(3);
    duplicates.add(3);
    duplicates.add(5);
    duplicates.add(8);
    duplicates.add(5);
    duplicates.add(10);
  }

  @Test
  @DisplayName("AVLTreeTest.testRandomInserts")
  public void testRandomInserts() {
    AVLTree<Integer> st = new AVLTree<>();
    Random random = new Random();
    for (int i = 0; i < 100; i++) st.add(random.nextInt(1000));
    for (Integer s : st.values()) assertEquals(s, st.find(s).value(), "Value not found!");
  }

  @Test
  @DisplayName("AVLTreeTest.testEmptyContainsZeroItems")
  public void testEmptyContainsZeroItems() {
    assertTreeEmpty(empty);
  }

  @Test
  @DisplayName("AVLTreeTest.testEmptyTreeIteratorException")
  public void testEmptyTreeIteratorException() {
    assertTreeEmptyIteratorException(empty);
  }

  @Test
  @DisplayName("AVLTreeTest.testOneContainsOneItem")
  public void testOneContainsOneItem() {
    assertTrue(one.contains(0), "One should contain 0");
    assertIterationValid(one, new Integer[] {0});
  }

  @Test
  @DisplayName("AVLTreeTest.testSeveralContainsSixItems")
  public void testSeveralContainsSixItems() {
    assertContains(several, new Integer[] {1, 2, 5, 8, 9, 10});
    assertIterationValid(several, new Integer[] {1, 2, 5, 8, 9, 10});
  }

  @Test
  @DisplayName("AVLTreeTest.testPreOrderIteration")
  public void testPreOrderIteration() {
    assertPreOrderIterationValid(several, new Integer[] {8, 2, 1, 5, 9, 10});
  }

  @Test
  @DisplayName("AVLTreeTest.testPostOrderIteration")
  public void testPostOrderIteration() {
    assertPostOrderIterationValid(several, new Integer[] {1, 5, 2, 10, 9, 8});
  }

  @Test
  @DisplayName("AVLTreeTest.testBreadthFirstOrderIteration")
  public void testBreadthFirstOrderIteration() {
    assertBreadthFirstOrderIterationValid(several, new Integer[] {8, 2, 9, 1, 5, 10});
  }

  @Test
  @DisplayName("AVLTreeTest.testSeveralDoesNotContain")
  public void testSeveralDoesNotContain() {
    assertDoesNotContain(several, new Integer[] {-1, 0, 3, 4, 6, 7, 11});
  }

  @Test
  @DisplayName("AVLTreeTest.testRemoveFromEmpty")
  public void testRemoveFromEmpty() {
    empty.remove(0);
    assertTreeEmpty(empty);
  }

  @Test
  @DisplayName("AVLTreeTest.testRemoveFromOne")
  public void testRemoveFromOne() {
    one.remove(0);
    assertFalse(one.contains(0), "0 not removed from one");
    assertTreeEmpty(one);
  }

  @Test
  @DisplayName("AVLTreeTest.testRemoveByLeaf")
  public void testRemoveByLeaf() {
    assertRemoveAll(several, new Integer[] {5, 2, 1, 8, 10, 9, 5});
  }

  @Test
  @DisplayName("AVLTreeTest.testRemoveByRoot")
  public void testRemoveByRoot() {
    assertRemoveAll(several, new Integer[] {5, 8, 9, 10, 2, 1});
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("AVLTreeTest.testDuplicates")
  public void testDuplicates() {
    empty.add(1);
    empty.add(1);
    empty.add(1);
    assertIterationValid(empty, new Integer[] {1, 1, 1});

    assertTrue(empty.contains(1), "Should contain 1");

    empty.remove(1);
    assertTrue(empty.contains(1), "Should still contain 1");
    assertIterationValid(empty, new Integer[] {1, 1});
    empty.remove(1);
    assertTrue(empty.contains(1), "Should still contain 1");
    assertIterationValid(empty, new Integer[] {1});
    empty.remove(1);
    assertFalse(empty.contains(1), "Should not contain 1");
    assertTreeEmpty(empty);
  }
}
