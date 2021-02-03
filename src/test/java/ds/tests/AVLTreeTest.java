package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.AVLTree;
import java.util.Iterator;
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
class AVLTreeTest {
  private static final String MISSING_FROM_TREE = " missing from tree";
  private static final String NOT_REACHED = "Not reached end of iteration";

  private AVLTree<Integer> empty;
  private AVLTree<Integer> one;
  private AVLTree<Integer> several;

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
  }

  @Test
  @DisplayName("AVLTreeTest.testEmptyContainsZeroItems")
  public void testEmptyContainsZeroItems() {
    assertTreeEmpty(empty);
  }

  private void assertTreeEmpty(AVLTree<Integer> tree) {
    Iterator<Integer> iterator = tree.values().iterator();
    assertFalse(iterator.hasNext(), "Tree must be empty");
  }
}
