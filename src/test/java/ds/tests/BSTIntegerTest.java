package ds.tests;

import static org.junit.jupiter.api.Assertions.*;
import static java.util.Arrays.asList;

import ds.BinaryTree;
import ds.Tree;
import java.util.List;
import java.util.ArrayList;
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
class BSTIntegerTest extends AbstractBinaryTreeTest<Integer> {

  @Override
  Tree<Integer> emptyTree() {
    return new BinaryTree<>();
  }

  @Override
  Tree<Integer> singleElementTree() {
    Tree<Integer> one = new BinaryTree<>();
    one.add(0);
    return one; 
  }

  @Override
  Tree<Integer> severalElementsTree() {
    Tree<Integer> several = new BinaryTree<>();
    several.add(5);
    several.add(2);
    several.add(1);
    several.add(9);
    several.add(8);
    several.add(10);
    return several;
  }

  @Override
  Tree<Integer> duplicatesTree() {
    Tree<Integer> duplicates = new BinaryTree<>();
    duplicates.add(5);
    duplicates.add(3);
    duplicates.add(3);
    duplicates.add(5);
    duplicates.add(8);
    duplicates.add(5);
    duplicates.add(10);
    return duplicates;
  }

  @Override
  Integer singleElement() {
    return 0;
  }

  @Override
  List<Integer> singleElementList() {
   return asList(new Integer[] {0});
  }

  @Override
   List<Integer> severalElementsList() {
    return asList(new Integer[] {1, 2, 5, 8, 9, 10});
   }

  @Override
   List<Integer> severalElementsInOrderList() {
    return asList(new Integer[] {1, 2, 5, 8, 9, 10});
   }

  @Override
   List<Integer> severalElementsPreOrderList() {
return asList(new Integer[] {1, 2, 8, 10, 9, 5});
   }

  @Override
  List<Integer> severalElementsPostOrderList() {
   return asList(new Integer[] {1, 2, 8, 10, 9, 5});
}

  @Override
   List<Integer> severalElementsLevelOrderList() {
return asList(new Integer[] {5, 2, 9, 1, 8, 10});
}

  @Override
   List<Integer> severalNonExistentList() {
return asList(new Integer[] {-1, 0, 3, 4, 6, 7, 11});
}

  @Override
   Integer duplicateElement() {
   return 1;
   }

  @Override
   List<Integer> duplicateElementList() {
     return asList(new Integer[] {1});
   } 

  @Override
   List<Integer> duplicateTwoElementList() {
     return asList(new Integer[] {1,1});
   }

  @Override
   List<Integer> duplicateThreeElementList() {
     return asList(new Integer[] {1,1,1});
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
    assertIterationValid(one, new Integer[] {0});
  }

  @Test
  @DisplayName("BinaryTreeTest.testSeveralContainsSixItems")
  public void testSeveralContainsSixItems() {
    assertContains(several, new Integer[] {1, 2, 5, 8, 9, 10});
    assertIterationValid(several, new Integer[] {1, 2, 5, 8, 9, 10});
  }

  @Test
  @DisplayName("BinaryTreeTest.testPreOrderIteration")
  public void testPreOrderIteration() {
    assertPreOrderIterationValid(several, new Integer[] {5, 2, 1, 9, 8, 10});
  }

  @Test
  @DisplayName("BinaryTreeTest.testPostOrderIteration")
  public void testPostOrderIteration() {
    assertPostOrderIterationValid(several, new Integer[] {1, 2, 8, 10, 9, 5});
  }

  @Test
  @DisplayName("BinaryTreeTest.testBreadthFirstOrderIteration")
  public void testBreadthFirstOrderIteration() {
    assertBreadthFirstOrderIterationValid(several, new Integer[] {5, 2, 9, 1, 8, 10});
  }

  @Test
  @DisplayName("BinaryTreeTest.testSeveralDoesNotContain")
  public void testSeveralDoesNotContain() {
    assertDoesNotContain(several, new Integer[] {-1, 0, 3, 4, 6, 7, 11});
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
    assertRemoveAll(several, new Integer[] {5, 2, 1, 8, 10, 9, 5});
  }

  @Test
  @DisplayName("BinaryTreeTest.testRemoveByRoot")
  public void testRemoveByRoot() {
    assertRemoveAll(several, new Integer[] {5, 8, 9, 10, 2, 1});
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("BinaryTreeTest.testDuplicates")
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
