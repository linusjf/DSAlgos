package ds.tests;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import ds.BinaryTree;
import ds.Tree;
import java.util.List;
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
    return singletonList(0);
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
    return asList(new Integer[] {5, 2, 1, 9, 8, 10});
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
    return singletonList(1);
  }

  @Override
  List<Integer> duplicateTwoElementList() {
    return asList(new Integer[] {1, 1});
  }

  @Override
  List<Integer> duplicateThreeElementList() {
    return asList(new Integer[] {1, 1, 1});
  }
}
