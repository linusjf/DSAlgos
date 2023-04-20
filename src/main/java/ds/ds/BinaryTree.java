package ds;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;

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
@SuppressWarnings({"PMD.CommentSize", "nullness", "PMD.LawOfDemeter"})
public class BinaryTree<E extends Comparable<E>> extends AbstractTree<E> {
  /**
   * Store an object in the tree. The object must conform to type Comparable in order to be inserted
   * in the correct location. Multiple objects representing the same value can be added.
   *
   * @param obj reference to Comparable object to add.
   */
  @Override
  public void add(E obj) {
    requireNonNull(obj);
    if (isNull(treeRoot)) treeRoot = new TreeNode<>(obj, null, null);
    else treeRoot.insert(obj);
  }

  /**
   * Determine whether the tree contains an object with the same value as the argument.
   *
   * @param obj reference to Comparable object whose value will be searched for.
   * @return true if the value is found.
   */
  @Override
  public boolean contains(E obj) {
    return nonNull(find(obj));
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
    if (isNull(treeRoot)) return null;
    return treeRoot.find(obj);
  }

  /**
   * Remove an object with a matching value from the tree. If multiple matches are possible, only
   * the first matching object is removed.
   *
   * @param obj Remove an object with a matching value from the tree.
   */
  @Override
  public void remove(E obj) {
    if (nonNull(treeRoot)) treeRoot = treeRoot.remove(obj);
  }

  /**
   * Removes the smallest value and associated value from the binary tree.
   *
   * @throws NoSuchElementException if the binary tree is empty
   */
  @Override
  public void removeMin() {
    if (isEmpty()) throw new NoSuchElementException("called removeMin() with empty binary tree");
    treeRoot = removeMin(treeRoot);
  }

  /**
   * Removes the smallest value and associated value from the given subtree.
   *
   * @param x the subtree
   * @return the updated subtree
   */
  private ITreeNode<E> removeMin(ITreeNode<E> x) {
    if (isNull(x.left())) return x.right();
    x.setLeft(removeMin(x.left()));
    x.setSize(x.refCount() + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return x;
  }

  /**
   * Removes the largest value and associated value from the binary tree.
   *
   * @throws NoSuchElementException if the binary tree is empty
   */
  @Override
  public void removeMax() {
    if (isEmpty()) throw new NoSuchElementException("called removeMax() with empty binary tree");
    treeRoot = removeMax(treeRoot);
  }

  /**
   * Removes the largest value and associated value from the given subtree.
   *
   * @param x the subtree
   * @return the updated subtree
   */
  private ITreeNode<E> removeMax(ITreeNode<E> node) {
    ITreeNode<E> x = node;
    if (isNull(x.right())) return x.left();
    x.setRight(removeMax(x.right()));
    x.setSize(x.refCount() + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return x;
  }
}
