package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Tree;
import ds.Tree.TraversalOrder;
import java.util.Iterator;
import java.util.NoSuchElementException;

class BaseTreeTest<T extends Comparable<T>> {

  protected static final String MISSING_FROM_TREE = " missing from tree";
  protected static final String NOT_REACHED = "Not reached end of iteration";

  protected void assertTreeEmpty(Tree<T> tree) {
    Iterator<T> iterator = tree.iterator(TraversalOrder.PRE_ORDER);
    assertFalse(iterator.hasNext(), "Tree empty");
    assertNull(tree.root(), "Root must be null.");
    assertTrue(tree.isEmpty(), "Tree must be empty.");
  }

  protected void assertTreeEmptyIteratorException(Tree<T> tree) {
    Iterator<T> iterator = tree.iterator(TraversalOrder.PRE_ORDER);
    assertThrows(NoSuchElementException.class, () -> iterator.next(), "Tree empty: exception");
  }

  protected void assertRemoveAll(Tree<T> tree, Iterable<T> elements) {
    for (T elem : elements) {
      tree.remove(elem);
      assertFalse(tree.contains(elem), () -> elem + " Still in tree after being removed");
    }
    assertTreeEmpty(tree);
  }

  protected void assertContains(Tree<T> tree, Iterable<T> elements) {
    for (T elem : elements) assertTrue(tree.contains(elem), () -> elem + " not in tree");
  }

  protected void assertDoesNotContain(Tree<T> tree, Iterable<T> elements) {
    for (T elem : elements)
      assertFalse(tree.contains(elem), () -> elem + " unexpectedly found in tree");
  }

  protected void assertIterationValid(Tree<T> tree, Iterable<T> elements) {
    Iterator<T> iterator = tree.iterator(TraversalOrder.IN_ORDER);
    for (T elem : elements) assertEquals(elem, iterator.next(), () -> elem + MISSING_FROM_TREE);
    assertFalse(iterator.hasNext(), NOT_REACHED);
  }

  protected void assertPreOrderIterationValid(Tree<T> tree, Iterable<T> elements) {
    Iterator<T> iterator = tree.iterator(TraversalOrder.PRE_ORDER);
    for (T elem : elements) assertEquals(elem, iterator.next(), () -> elem + MISSING_FROM_TREE);
    assertFalse(iterator.hasNext(), NOT_REACHED);
  }

  protected void assertPostOrderIterationValid(Tree<T> tree, Iterable<T> elements) {
    Iterator<T> iterator = tree.iterator(TraversalOrder.POST_ORDER);
    for (T elem : elements) assertEquals(elem, iterator.next(), () -> elem + MISSING_FROM_TREE);
    assertFalse(iterator.hasNext(), NOT_REACHED);
  }

  protected void assertBreadthFirstOrderIterationValid(Tree<T> tree, Iterable<T> elements) {
    Iterator<T> iterator = tree.iterator(TraversalOrder.BREADTH_FIRST_ORDER);
    for (T elem : elements) assertEquals(elem, iterator.next(), () -> elem + MISSING_FROM_TREE);
    assertFalse(iterator.hasNext(), NOT_REACHED);
  }
}
