package ds.tests;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import ds.BinaryTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("BinaryTreeTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class BSTIntegerTest extends AbstractBinaryTreeTest<Integer> {

  @BeforeAll
  public void init() {
    singleElement = 0;
    singleElementList = singletonList(0);
    severalElementsList = asList(new Integer[] {1, 2, 5, 8, 9, 10});
    severalElementsInOrderList = asList(new Integer[] {1, 2, 5, 8, 9, 10});
    severalElementsPreOrderList = asList(new Integer[] {5, 2, 1, 9, 8, 10});
    severalElementsPostOrderList = asList(new Integer[] {1, 2, 8, 10, 9, 5});
    severalElementsLevelOrderList = asList(new Integer[] {5, 2, 9, 1, 8, 10});
    severalNonExistentList = asList(new Integer[] {-1, 0, 3, 4, 6, 7, 11});

    duplicateElement = 1;
    duplicateElementList = singletonList(1);
    duplicateTwoElementList = asList(new Integer[] {1, 1});
    duplicateThreeElementList = asList(new Integer[] {1, 1, 1});
  }

  @BeforeEach
  public void setup() {
    empty = new BinaryTree<Integer>();
    one = new BinaryTree<Integer>();
    one.add(0);
    several = new BinaryTree<Integer>();
    several.add(5);
    several.add(2);
    several.add(1);
    several.add(9);
    several.add(8);
    several.add(10);
    duplicates = new BinaryTree<Integer>();
    duplicates.add(5);
    duplicates.add(3);
    duplicates.add(3);
    duplicates.add(5);
    duplicates.add(8);
    duplicates.add(5);
    duplicates.add(10);
  }
}
