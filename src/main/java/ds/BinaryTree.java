package ds;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;

/**
 * A simple generic binary tree class to demonstrate the basic principles of implementing a tree
 * data structure. This should not be taken as a production quality class (see the text book
 * instead). Copyright (c) 2006 Dept. of Computer Science, University College London
 *
 * <p>Objects stored in a tree must conform to Comparable so that their values can be compared. The
 * type parameter is constrained to conform to Comparable to enforce this.
 *
 * @author Graham Roberts
 * @version 2.0 01-Mar-06
 */
@SuppressWarnings({"PMD.CommentSize", "nullness"})
public class BinaryTree<E extends Comparable<E>> implements Tree<E> {
  /**
   * A tree is a hierarchical structure of TreeNode objects. root references the first node on the
   * tree.
   */
  @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
  private TreeNode<E> root;

  @SuppressWarnings("PMD.NullAssignment")
  public BinaryTree() {
    root = null;
  }

  @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
  @Override
  public ITreeNode<E> root() {
    return root;
  }

  /**
   * Store an object in the tree. The object must conform to type Comparable in order to be inserted
   * in the correct location. Multiple objects representing the same value can be added.
   *
   * @param obj reference to Comparable object to add.
   */
  @Override
  public void add(E obj) {
    requireNonNull(obj);
    if (isNull(root)) root = new TreeNode<>(obj, null, null);
    else root.insert(obj);
  }

  /**
   * Determine whether the tree contains an object with the same value as the argument.
   *
   * @param obj reference to Comparable object whose value will be searched for.
   * @return true if the value is found.
   */
  @SuppressWarnings("PMD.SimplifiedTernary")
  @Override
  public boolean contains(E obj) {
    requireNonNull(obj);
    return nonNull(root) ? nonNull(root.find(obj)) : false;
  }

  /**
   * Returns the node comtaining the argument value. null otherwise
   *
   * @param obj reference to Comparable object whose value will be searched for.
   * @return node.
   */
  @Override
  public ITreeNode<E> find(E obj) {
    requireNonNull(obj);
    if (isNull(root)) return null;
    return root.find(obj);
  }

  /**
   * Remove an object with a matching value from the tree. If multiple matches are possible, only
   * the first matching object is removed.
   *
   * @param obj Remove an object with a matching value from the tree.
   */
  @Override
  public void remove(E obj) {
    if (nonNull(root)) root = (TreeNode<E>) root.remove(obj);
  }

  /**
   * Return a new tree iterator object.
   *
   * @return new iterator object.
   */
  @Override
  public Iterator<E> iterator(TraversalOrder order) {
    return new TreeIterator(root, order);
  }
}
