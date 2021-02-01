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
    one.put(0);
    several = new AVLTree<>();
    several.put(5);
    several.put(2);
    several.put(1);
    several.put(9);
    several.put(8);
    several.put(10);
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
