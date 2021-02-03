package ds;

import java.util.Iterator;

/**
 * General interface for trees. Copyright (c) 2006 Dept. of Computer Science, University College
 * London
 *
 * @author Graham Roberts
 * @version 2.0 01-Mar-06
 */
public interface Tree<E> {
  enum TraversalOrder {
    PRE_ORDER,
    IN_ORDER,
    POST_ORDER,
    BREADTH_FIRST_ORDER
  }

  /**
   * Store an object in the tree. The object must conform to type Comparable in order to be inserted
   * in the correct location. Multiple objects representing the same value can be added.
   *
   * @param obj reference to Comparable object to add.
   */
  void add(E obj);

  /**
   * Determine whether the tree contains an object with the same value as the argument.
   *
   * @param obj reference to Comparable object whose value will be searched for.
   * @return true if the value is found.
   */
  boolean contains(E obj);

  /**
   * Returns the node containing the same value as the argument. Null otherwise.
   *
   * @param obj reference to Comparable object whose value will be searched for.
   * @return true if the value is found.
   */
  ITreeNode<E> find(E obj);

  /**
   * Returns the root node of tree.
   *
   * @return root of tree.
   */
  ITreeNode<E> root();

  /**
   * Remove an object with a matching value from the tree. If multiple matches are possible, only
   * the first matching object is removed.
   *
   * @param obj Remove an object with a matching value from the tree.
   */
  void remove(E obj);

  /**
   * Return a new tree iterator object.
   *
   * @return new iterator object.
   */
  Iterator<E> iterator(TraversalOrder order);
}
