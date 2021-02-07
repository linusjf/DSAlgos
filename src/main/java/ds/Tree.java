package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * General interface for trees. Copyright (c) 2006 Dept. of Computer Science, University College
 * London
 *
 * @author Graham Roberts
 * @version 2.0 01-Mar-06 Modified by
 * @author Linus Fernandes
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
   * Returns whether tree is empty or not.
   *
   * @return boolean.
   */
  boolean isEmpty();

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

  /**
   * Return a new tree iterator object from values.
   *
   * @return new iterator object.
   */
  Iterator<E> iteratorFromValues(TraversalOrder order);

  /**
   * Return tree size.
   *
   * @return int.
   */
  int size();

  /**
   * Returns the number of values in the binary tree in the given range.
   *
   * @param lo minimum endpoint
   * @param hi maximum endpoint
   * @return the number of values in the binary tree between {@code lo} (inclusive) and {@code hi}
   *     (exclusive)
   * @throws NullPointerException if either {@code lo} or {@code hi} is {@code null}
   */
  int size(E lo, E hi);

  /**
   * Return tree size from values.
   *
   * @return int.
   */
  int sizeFromValues();

  /**
   * Returns the number of values in the binary tree in the given range.
   *
   * @param lo minimum endpoint
   * @param hi maximum endpoint
   * @return the number of values in the binary tree between {@code lo} (inclusive) and {@code hi}
   *     (exclusive)
   * @throws NullPointerException if either {@code lo} or {@code hi} is {@code null}
   */
  int sizeFromValues(E lo, E hi);

  /**
   * Returns the height of the internal AVL tree. It is assumed that the height of an empty tree is
   * -1 and the height of a tree with just one node is 0.
   *
   * @return the height of the internal AVL tree
   */
  int height();

  /**
   * Removes the smallest value and associated value from the binary tree.
   *
   * @throws NoSuchElementException if the binary tree is empty
   */
  void removeMin();

  /**
   * Removes the largest value and associated value from the binary tree.
   *
   * @throws NoSuchElementException if the binary tree is empty
   */
  void removeMax();

  /**
   * Returns the smallest value in the binary tree.
   *
   * @return the smallest value in the binary tree
   * @throws NoSuchElementException if the binary tree is empty
   */
  E min();

  /**
   * Returns the largest value in the binary tree.
   *
   * @return the largest value in the binary tree
   * @throws NoSuchElementException if the binary tree is empty
   */
  E max();

  /**
   * Returns the largest value in the binary tree less than or equal to {@code value}.
   *
   * @param val the value
   * @return the largest value in the binary tree less than or equal to {@code value}
   * @throws NoSuchElementException if the binary tree is empty
   * @throws NullPointerException if {@code value} is {@code null}
   */
  E floor(E val);

  /**
   * Returns the smallest value in the binary tree greater than or equal to {@code value}.
   *
   * @param val the value
   * @return the smallest value in the binary tree greater than or equal to {@code value}
   * @throws NoSuchElementException if the binary tree is empty
   * @throws NullPointerException if {@code value} is {@code null}
   */
  E ceiling(E val);

  /**
   * Returns the kth smallest value in the binary tree.
   *
   * @param k the order statistic
   * @return the kth smallest value in the binary tree
   * @throws IllegalArgumentException unless {@code k} is between 0 and {@code size() -1 }
   */
  E select(int k);

  /**
   * Returns the number of values in the binary tree strictly less than {@code value}.
   *
   * @param val the value
   * @return the number of values in the binary tree strictly less than {@code value}
   * @throws NullPointerException if {@code value} is {@code null}
   */
  int rank(E val);

  /**
   * Returns the number of values in the binary tree strictly less than {@code value}.
   *
   * @param val the value
   * @return the number of values in the binary tree strictly less than {@code value}
   * @throws NullPointerException if {@code value} is {@code null}
   */
  int rankFromValues(E val);

  /**
   * Returns all values in the binary tree.
   *
   * @return all values in the binary tree
   */
  Iterable<E> values();

  /**
   * Returns all values in the binary tree in the given range.
   *
   * @param lo the lowest value
   * @param hi the highest value
   * @return all values in the binary tree between {@code lo} (inclusive) and {@code hi} (exclusive)
   * @throws NullPointerException if either {@code lo} or {@code hi} is {@code null}
   */
  Iterable<E> values(E lo, E hi);

  /**
   * Returns all values in the binary tree following an in-order traversal.
   *
   * @return all values in the binary tree following an in-order traversal
   */
  Iterable<E> valuesInOrder();

  /**
   * Returns all values in the binary tree following an pre-order traversal.
   *
   * @return all values in the binary tree following an pre-order traversal
   */
  Iterable<E> valuesPreOrder();

  /**
   * Returns all values in the binary tree following an post-order traversal.
   *
   * @return all values in the binary tree following an post-order traversal
   */
  Iterable<E> valuesPostOrder();

  /**
   * Returns all values in the binary tree following a level-order traversal.
   *
   * @return all values in the binary tree following a level-order traversal.
   */
  Iterable<E> valuesLevelOrder();
}
