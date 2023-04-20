package ds;

/*
 * *****************************************************************************
 *  Compilation:  javac AVLTree.java
 *  Data files:   https://algs4.cs.princeton.edu/33balanced/tinyST.txt
 *
 *  A binary tree implemented using an AVL tree.
 *
 *****************************************************************************
 */

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;

/**
 * This implementation of AVl tree requires that the value type implements the {@code Comparable}
 * interface and calls the {@code compareTo()} and method to compare two values. It does not call
 * either {@code equals()} or {@code hashCode()}. The <em>add</em>, <em>find</em>,
 * <em>contains</em>, <em>remove</em>, <em>minimum</em>, <em>maximum</em>, <em>ceiling</em>, and
 * <em>floor</em> operations each take logarithmic time in the worst case. The <em>size</em>, and
 * <em>is-empty</em> operations take constant time. Construction also takes constant time.
 *
 * @author Marcelo Silva
 */
@SuppressWarnings({
  "PMD.CommentSize",
  "nullness",
  "PMD.LawOfDemeter",
  "PMD.AvoidFieldNameMatchingMethodName"
})
public class AVLTree<T extends Comparable<T>> extends AbstractTree<T> {

  /**
   * Returns the value in the tree with the given value.
   *
   * @param val the val
   * @return the value in the binary tree and {@code null} if the value is not in the binary tree
   * @throws IllegalArgumentException if {@code val} is {@code null}
   */
  @Override
  public ITreeNode<T> find(T val) {
    requireNonNull(val);
    ITreeNode<T> x = find(treeRoot, val);
    if (isNull(x)) return null;
    return x;
  }

  /**
   * Returns value associated with the given value in the subtree or {@code null} if no such value.
   *
   * @param x the subtree
   * @param val the value
   * @return value associated with the given value in the subtree or {@code null} if no such value
   */
  @SuppressWarnings("checkstyle:ReturnCount")
  private ITreeNode<T> find(ITreeNode<T> x, T value) {
    if (isNull(x)) return null;
    int cmp = value.compareTo(x.value());
    if (cmp < 0) return find(x.left(), value);
    else if (cmp > 0) return find(x.right(), value);
    else return x;
  }

  /**
   * Checks if the tree contains the given value.
   *
   * @param val the value
   * @return {@code true} if the binary tree contains {@code val} and {@code false} otherwise
   * @throws IllegalArgumentException if {@code val} is {@code null}
   */
  @Override
  public boolean contains(T val) {
    return nonNull(find(val));
  }

  /**
   * Inserts the specified value-value pair into the binary tree, overwriting the old value with the
   * new value if the binary tree already contains the specified value. Deletes the specified value
   * (and its associated value) from this binary tree if the specified value is {@code null}.
   *
   * @param val the value
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  @Override
  public void add(T val) {
    requireNonNull(val);
    if (isNull(treeRoot)) treeRoot = new TreeNode<>(val);
    else treeRoot = add(treeRoot, val);
    assertChecks();
  }

  /**
   * Inserts the value-value pair in the subtree. It overrides the old value with the new value if
   * the binary tree already contains the specified value and removes the specified value (and its
   * associated value) from this binary tree if the specified value is {@code null}.
   *
   * @param x the subtree
   * @param val the value
   * @return the subtree
   */
  private ITreeNode<T> add(ITreeNode<T> node, T val) {
    ITreeNode<T> x = node;
    int cmp = val.compareTo(x.value());
    if (cmp < 0) {
      ITreeNode<T> left = x.left();
      if (isNull(left)) x.setLeft(new TreeNode<>(val));
      else x.setLeft(add(left, val));
    } else if (cmp > 0) {
      ITreeNode<T> right = x.right();
      if (isNull(right)) x.setRight(new TreeNode<>(val));
      else x.setRight(add(right, val));
    } else {
      x.incrementRefCount();
      x.setValue(val);
      return x;
    }
    x.setSize(x.refCount() + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return balance(x);
  }

  @Generated
  private void assertChecks() {
    if (!TreeChecks.check(this)) throw new AssertionError("Invalid state");
  }

  /**
   * Restores the AVL tree property of the subtree.
   *
   * @param x the subtree
   * @return the subtree with restored AVL property
   */
  private ITreeNode<T> balance(ITreeNode<T> node) {
    ITreeNode<T> x = node;
    if (balanceFactor(x) < -1) {
      if (balanceFactor(x.right()) > 0) {
        x.setRight(rotateRight(x.right()));
      }
      x = rotateLeft(x);
    } else if (balanceFactor(x) > 1) {
      if (balanceFactor(x.left()) < 0) x.setLeft(rotateLeft(x.left()));
      x = rotateRight(x);
    }
    return x;
  }

  /**
   * Returns the balance factor of the subtree. The balance factor is defined as the difference in
   * height of the left subtree and right subtree, in this order. Therefore, a subtree with a
   * balance factor of -1, 0 or 1 has the AVL property since the heights of the two child subtrees
   * differ by at most one.
   *
   * @param x the subtree
   * @return the balance factor of the subtree
   */
  private int balanceFactor(ITreeNode<T> x) {
    return x == null ? 0 : x.balanceFactor();
  }

  /**
   * Rotates the given subtree to the right.
   *
   * @param x the subtree
   * @return the right rotated subtree
   */
  private ITreeNode<T> rotateRight(ITreeNode<T> x) {
    ITreeNode<T> y = x.left();
    x.setLeft(y.right());
    y.setRight(x);
    y.setSize(x.size());
    x.setSize(x.refCount() + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    y.setHeight(1 + Math.max(height(y.left()), height(y.right())));
    return y;
  }

  /**
   * Rotates the given subtree to the left.
   *
   * @param x the subtree
   * @return the left rotated subtree
   */
  private ITreeNode<T> rotateLeft(ITreeNode<T> x) {
    ITreeNode<T> y = x.right();
    x.setRight(y.left());
    y.setLeft(x);
    y.setSize(x.size());
    x.setSize(x.refCount() + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    y.setHeight(1 + Math.max(height(y.left()), height(y.right())));
    return y;
  }

  /**
   * Removes the specified value and its associated value from the binary tree (if the value is in
   * the binary tree).
   *
   * @param val the value
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  @Override
  public void remove(T val) {
    if (isNull(val)) throw new IllegalArgumentException("argument to remove() is null");
    if (!contains(val)) return;
    treeRoot = remove(treeRoot, val);
    assertChecks();
  }

  /**
   * Removes the specified value and its associated value from the given subtree.
   *
   * @param x the subtree
   * @param value the value
   * @return the updated subtree
   */
  private ITreeNode<T> remove(ITreeNode<T> node, T val) {
    ITreeNode<T> x = node;
    int cmp = val.compareTo(x.value());
    if (cmp < 0) x.setLeft(remove(x.left(), val));
    else if (cmp > 0) x.setRight(remove(x.right(), val));
    else {
      if (x.refCount() > 1) {
        x.decrementRefCount();
        return x;
      }
      if (isNull(x.left())) return x.right();
      else if (isNull(x.right())) return x.left();
      else {
        ITreeNode<T> y = x;
        x = min(y.right());
        x.setRight(removeMin(y.right()));
        x.setLeft(y.left());
      }
    }
    x.setSize(x.refCount() + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return balance(x);
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
    assertChecks();
  }

  /**
   * Removes the smallest value and associated value from the given subtree.
   *
   * @param x the subtree
   * @return the updated subtree
   */
  private ITreeNode<T> removeMin(ITreeNode<T> x) {
    if (isNull(x.left())) return x.right();
    x.setLeft(removeMin(x.left()));
    x.setSize(x.refCount() + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return balance(x);
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
    assertChecks();
  }

  /**
   * Removes the largest value and associated value from the given subtree.
   *
   * @param x the subtree
   * @return the updated subtree
   */
  private ITreeNode<T> removeMax(ITreeNode<T> x) {
    if (isNull(x.right())) return x.left();
    x.setRight(removeMax(x.right()));
    x.setSize(x.refCount() + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return balance(x);
  }
}
