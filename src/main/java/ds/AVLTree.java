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

import static ds.MathUtils.requireInRangeInclusive;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import ds.Tree.TraversalOrder;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

/**
 * This implementation of AVl tree requires that the value type implements the {@code Comparable}
 * interface and calls the {@code compareTo()} and method to compare two values. It does not call
 * either {@code equals()} or {@code hashCode()}. The <em>add</em>, <em>find</em>,
 * <em>contains</em>, <em>delete</em>, <em>minimum</em>, <em>maximum</em>, <em>ceiling</em>, and
 * <em>floor</em> operations each take logarithmic time in the worst case. The <em>size</em>, and
 * <em>is-empty</em> operations take constant time. Construction also takes constant time.
 *
 * @author Marcelo Silva
 */
@SuppressWarnings({"PMD.CommentSize", "nullness"})
public class AVLTree<T extends Comparable<T>> implements Tree<T> {

  /** The root node. */
  ITreeNode<T> root;

  /**
   * Unit tests the {@code AVLTreeST} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    AVLTree<Integer> st = new AVLTree<>();
    Random random = new Random();
    for (int i = 0; i < 100; i++) st.add(random.nextInt(1000));
    for (Integer s : st.values()) System.out.println(s + " " + st.find(s));
  }

  @Override
  public ITreeNode<T> root() {
    return root;
  }

  /**
   * Checks if the binary tree is empty.
   *
   * @return {@code true} if the binary tree is empty.
   */
  public boolean isEmpty() {
    return isNull(root);
  }

  @Override
  public Iterator<T> iterator(TraversalOrder order) {
    return null;
  }

  /**
   * Returns the number value-value pairs in the binary tree.
   *
   * @return the number value-value pairs in the binary tree
   */
  public int size() {
    return size(root);
  }

  /**
   * Returns the number of nodes in the subtree.
   *
   * @param x the subtree
   * @return the number of nodes in the subtree
   */
  private int size(ITreeNode<T> x) {
    return isNull(x) ? 0 : x.size();
  }

  /**
   * Returns the number of values in the binary tree in the given range.
   *
   * @param lo minimum endpoint
   * @param hi maximum endpoint
   * @return the number of values in the binary tree between {@code lo} (inclusive) and {@code hi}
   *     (exclusive)
   * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
   */
  public int size(T lo, T hi) {
    requireNonNull(lo);
    requireNonNull(hi);
    if (lo.compareTo(hi) > 0) return 0;
    if (contains(hi)) return rank(hi) - rank(lo) + 1;
    else return rank(hi) - rank(lo);
  }

  /**
   * Returns the height of the internal AVL tree. It is assumed that the height of an empty tree is
   * -1 and the height of a tree with just one node is 0.
   *
   * @return the height of the internal AVL tree
   */
  public int height() {
    return height(root);
  }

  /**
   * Returns the height of the subtree.
   *
   * @param x the subtree
   * @return the height of the subtree.
   */
  private int height(ITreeNode<T> x) {
    return isNull(x) ? -1 : x.height();
  }

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
    ITreeNode<T> x = find(root, val);
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
    root = add(root, val);
    assertChecks();
  }

  @Generated
  private void assertChecks() {
    if (!Checks.check(this)) throw new AssertionError("Invalid state");
  }

  /**
   * Inserts the value-value pair in the subtree. It overrides the old value with the new value if
   * the binary tree already contains the specified value and deletes the specified value (and its
   * associated value) from this binary tree if the specified value is {@code null}.
   *
   * @param x the subtree
   * @param val the value
   * @return the subtree
   */
  private ITreeNode<T> add(ITreeNode<T> node, T val) {
    ITreeNode<T> x = (ITreeNode<T>) node;
    if (isNull(x)) return new TreeNode<>(val, 0, 1);
    int cmp = val.compareTo(x.value());
    if (cmp < 0) x.setLeft(add(x.left(), val));
    else if (cmp > 0) x.setRight(add(x.right(), val));
    else {
      x.setValue(val);
      return x;
    }
    x.setSize(1 + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return balance(x);
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
    return height(x.left()) - height(x.right());
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
    x.setSize(1 + size(x.left()) + size(x.right()));
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
    x.setSize(1 + size(x.left()) + size(x.right()));
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
    if (isNull(val)) throw new IllegalArgumentException("argument to delete() is null");
    if (!contains(val)) return;
    root = delete(root, val);
    assertChecks();
  }

  /**
   * Removes the specified value and its associated value from the given subtree.
   *
   * @param x the subtree
   * @param value the value
   * @return the updated subtree
   */
  private ITreeNode<T> delete(ITreeNode<T> node, T val) {
    ITreeNode<T> x = node;
    int cmp = val.compareTo(x.value());
    if (cmp < 0) x.setLeft(delete(x.left(), val));
    else if (cmp > 0) x.setRight(delete(x.right(), val));
    else {
      if (x.left() == null) return x.right();
      else if (x.right() == null) return x.left();
      else {
        ITreeNode<T> y = x;
        x = min(y.right());
        x.setRight(deleteMin(y.right()));
        x.setLeft(y.left());
      }
    }
    x.setSize(1 + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return balance(x);
  }

  /**
   * Removes the smallest value and associated value from the binary tree.
   *
   * @throws NoSuchElementException if the binary tree is empty
   */
  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException("called deleteMin() with empty binary tree");
    root = deleteMin(root);
    assertChecks();
  }

  /**
   * Removes the smallest value and associated value from the given subtree.
   *
   * @param x the subtree
   * @return the updated subtree
   */
  private ITreeNode<T> deleteMin(ITreeNode<T> x) {
    if (isNull(x.left())) return x.right();
    x.setLeft(deleteMin(x.left()));
    x.setSize(1 + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return balance(x);
  }

  /**
   * Removes the largest value and associated value from the binary tree.
   *
   * @throws NoSuchElementException if the binary tree is empty
   */
  public void deleteMax() {
    if (isEmpty()) throw new NoSuchElementException("called deleteMax() with empty binary tree");
    root = deleteMax(root);
    assertChecks();
  }

  /**
   * Removes the largest value and associated value from the given subtree.
   *
   * @param x the subtree
   * @return the updated subtree
   */
  private ITreeNode<T> deleteMax(ITreeNode<T> x) {
    if (isNull(x.right())) return x.left();
    x.setRight(deleteMax(x.right()));
    x.setSize(1 + size(x.left()) + size(x.right()));
    x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
    return balance(x);
  }

  /**
   * Returns the smallest value in the binary tree.
   *
   * @return the smallest value in the binary tree
   * @throws NoSuchElementException if the binary tree is empty
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public T min() {
    if (isEmpty()) throw new NoSuchElementException("called min() with empty binary tree");
    return min(root).value();
  }

  /**
   * Returns the node with the smallest value in the subtree.
   *
   * @param x the subtree
   * @return the node with the smallest value in the subtree
   */
  private ITreeNode<T> min(ITreeNode<T> node) {
    ITreeNode<T> x = node;
    while (nonNull(x.left())) x = x.left();
    return x;
  }

  /**
   * Returns the largest value in the binary tree.
   *
   * @return the largest value in the binary tree
   * @throws NoSuchElementException if the binary tree is empty
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public T max() {
    if (isEmpty()) throw new NoSuchElementException("called max() with empty binary tree");
    return max(root).value();
  }

  /**
   * Returns the node with the largest value in the subtree.
   *
   * @param x the subtree
   * @return the node with the largest value in the subtree
   */
  private ITreeNode<T> max(ITreeNode<T> node) {
    ITreeNode<T> x = node;
    while (nonNull(x.right())) x = x.right();
    return x;
  }

  /**
   * Returns the largest value in the binary tree less than or equal to {@code value}.
   *
   * @param val the value
   * @return the largest value in the binary tree less than or equal to {@code value}
   * @throws NoSuchElementException if the binary tree is empty
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  public T floor(T val) {
    requireNonNull(val);
    if (isEmpty()) throw new NoSuchElementException("called floor() with empty binary tree");
    ITreeNode<T> x = floor(root, val);
    if (isNull(x)) return null;
    else return x.value();
  }

  /**
   * Returns the node in the subtree with the largest value less than or equal to the given value.
   *
   * @param x the subtree
   * @param value the value
   * @return the node in the subtree with the largest value less than or equal to the given value
   */
  @SuppressWarnings("checkstyle:ReturnCount")
  private ITreeNode<T> floor(ITreeNode<T> x, T val) {
    if (isNull(x)) return null;
    int cmp = val.compareTo(x.value());
    if (cmp == 0) return x;
    if (cmp < 0) return floor(x.left(), val);
    ITreeNode<T> y = floor(x.right(), val);
    if (isNull(y)) return x;
    else return y;
  }

  /**
   * Returns the smallest value in the binary tree greater than or equal to {@code value}.
   *
   * @param val the value
   * @return the smallest value in the binary tree greater than or equal to {@code value}
   * @throws NoSuchElementException if the binary tree is empty
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  public T ceiling(T val) {
    requireNonNull(val);
    if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty binary tree");
    ITreeNode<T> x = ceiling(root, val);
    if (isNull(x)) return null;
    else return x.value();
  }

  /**
   * Returns the node in the subtree with the smallest value greater than or equal to the given
   * value.
   *
   * @param x the subtree
   * @param val the value
   * @return the node in the subtree with the smallest value greater than or equal to the given
   *     value
   */
  @SuppressWarnings("checkstyle:ReturnCount")
  private ITreeNode<T> ceiling(ITreeNode<T> x, T val) {
    if (isNull(x)) return null;
    int cmp = val.compareTo(x.value());
    if (cmp == 0) return x;
    if (cmp > 0) return ceiling(x.right(), val);
    ITreeNode<T> y = ceiling(x.left(), val);
    if (isNull(y)) return x;
    else return y;
  }

  /**
   * Returns the kth smallest value in the binary tree.
   *
   * @param k the order statistic
   * @return the kth smallest value in the binary tree
   * @throws IllegalArgumentException unless {@code k} is between 0 and {@code size() -1 }
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public T select(int k) {
    requireInRangeInclusive(0, size(), k);
    return select(root, k).value();
  }

  /**
   * Returns the node with value the kth smallest value in the subtree.
   *
   * @param x the subtree
   * @param k the kth smallest value in the subtree
   * @return the node with value the kth smallest value in the subtree
   */
  @SuppressWarnings("checkstyle:ReturnCount")
  private ITreeNode<T> select(ITreeNode<T> x, int k) {
    if (isNull(x)) return null;
    int t = size(x.left());
    if (t > k) return select(x.left(), k);
    else if (t < k) return select(x.right(), k - t - 1);
    else return x;
  }

  /**
   * Returns the number of values in the binary tree strictly less than {@code value}.
   *
   * @param val the value
   * @return the number of values in the binary tree strictly less than {@code value}
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  public int rank(T val) {
    requireNonNull(val);
    return rank(val, root);
  }

  /**
   * Returns the number of values in the subtree less than value.
   *
   * @param val the value
   * @param x the subtree
   * @return the number of values in the subtree less than value
   */
  @SuppressWarnings("checkstyle:ReturnValue")
  private int rank(T val, ITreeNode<T> x) {
    if (isNull(x)) return 0;
    int cmp = val.compareTo(x.value());
    if (cmp < 0) return rank(val, x.left());
    else if (cmp > 0) return 1 + size(x.left()) + rank(val, x.right());
    else return size(x.left());
  }

  /**
   * Returns all values in the binary tree.
   *
   * @return all values in the binary tree
   */
  public Iterable<T> values() {
    return valuesInOrder();
  }

  /**
   * Adds the values between {@code lo} and {@code hi} in the subtree to the {@code queue}.
   *
   * @param x the subtree
   * @param queue the queue
   * @param lo the lowest value
   * @param hi the highest value
   */
  private void values(ITreeNode<T> x, Queue<T> queue, T lo, T hi) {
    if (isNull(x)) return;
    int cmplo = lo.compareTo(x.value());
    int cmphi = hi.compareTo(x.value());
    if (cmplo < 0) values(x.left(), queue, lo, hi);
    if (cmplo <= 0 && cmphi >= 0) queue.offer(x.value());
    if (cmphi > 0) values(x.right(), queue, lo, hi);
  }

  /**
   * Returns all values in the binary tree in the given range.
   *
   * @param lo the lowest value
   * @param hi the highest value
   * @return all values in the binary tree between {@code lo} (inclusive) and {@code hi} (exclusive)
   * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
   */
  public Iterable<T> values(T lo, T hi) {
    requireNonNull(lo);
    requireNonNull(hi);
    Queue<T> queue = new ArrayDeque<>();
    values(root, queue, lo, hi);
    return queue;
  }

  /**
   * Returns all values in the binary tree following an in-order traversal.
   *
   * @return all values in the binary tree following an in-order traversal
   */
  public Iterable<T> valuesInOrder() {
    Queue<T> queue = new ArrayDeque<>();
    valuesInOrder(root, queue);
    return queue;
  }

  /**
   * Adds the values in the subtree to queue following an in-order traversal.
   *
   * @param x the subtree
   * @param queue the queue
   */
  private void valuesInOrder(ITreeNode<T> x, Queue<T> queue) {
    if (isNull(x)) return;
    valuesInOrder(x.left(), queue);
    queue.offer(x.value());
    valuesInOrder(x.right(), queue);
  }

  /**
   * Returns all values in the binary tree following a level-order traversal.
   *
   * @return all values in the binary tree following a level-order traversal.
   */
  public Iterable<T> valuesLevelOrder() {
    Queue<T> queue = new ArrayDeque<>();
    if (!isEmpty()) {
      Queue<ITreeNode<T>> queue2 = new ArrayDeque<>();
      queue2.offer(root);
      while (!queue2.isEmpty()) {
        ITreeNode<T> x = queue2.poll();
        queue.offer(x.value());
        if (nonNull(x.left())) queue2.offer(x.left());
        if (nonNull(x.right())) queue2.offer(x.right());
      }
    }
    return queue;
  }

  private static class Checks {
    /**
     * Checks if the AVL tree invariants are fine.
     *
     * @return {@code true} if the AVL tree invariants are fine
     */
    @SuppressWarnings("PMD.SystemPrintln")
    private static <T extends Comparable<T>> boolean check(AVLTree<T> tree) {
      if (!isBST(tree)) System.out.println("Symmetric order not consistent");
      if (!isAVL(tree)) System.out.println("AVL property not consistent");
      if (!isSizeConsistent(tree)) System.out.println("Subtree counts not consistent");
      if (!isRankConsistent(tree)) System.out.println("Ranks not consistent");
      return isBST(tree) && isAVL(tree) && isSizeConsistent(tree) && isRankConsistent(tree);
    }

    /**
     * Checks if AVL property is consistent.
     *
     * @return {@code true} if AVL property is consistent.
     */
    private static <T extends Comparable<T>> boolean isAVL(AVLTree<T> tree) {
      return isAVL(tree, tree.root());
    }

    /**
     * Checks if AVL property is consistent in the subtree.
     *
     * @param x the subtree
     * @return {@code true} if AVL property is consistent in the subtree
     */
    private static <T extends Comparable<T>> boolean isAVL(AVLTree<T> tree, ITreeNode<T> x) {
      if (isNull(x)) return true;
      int bf = tree.balanceFactor(x);
      if (bf > 1 || bf < -1) return false;
      return isAVL(tree, x.left()) && isAVL(tree, x.right());
    }

    /**
     * Checks if the symmetric order is consistent.
     *
     * @return {@code true} if the symmetric order is consistent
     */
    private static <T extends Comparable<T>> boolean isBST(AVLTree<T> tree) {
      return isBST(tree.root, null, null);
    }

    /**
     * Checks if the tree rooted at x is a BST. With all values strictly between min and max. (if
     * min or max is null, treat as empty constraint). Credit: Bob Dondero's elegant solution
     *
     * @param x the subtree
     * @param min the minimum value in subtree
     * @param max the maximum value in subtree
     * @return {@code true} if if the symmetric order is consistent
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:ReturnCount"})
    private static <T extends Comparable<T>> boolean isBST(ITreeNode<T> x, T min, T max) {
      if (isNull(x)) return true;
      if (nonNull(min) && x.value().compareTo(min) <= 0) return false;
      if (nonNull(max) && x.value().compareTo(max) >= 0) return false;
      return isBST(x.left(), min, x.value()) && isBST(x.right(), x.value(), max);
    }

    /**
     * Checks if size is consistent.
     *
     * @return {@code true} if size is consistent
     */
    private static <T extends Comparable<T>> boolean isSizeConsistent(AVLTree<T> tree) {
      return isSizeConsistent(tree, tree.root);
    }

    /**
     * Checks if the size of the subtree is consistent.
     *
     * @return {@code true} if the size of the subtree is consistent
     */
    private static <T extends Comparable<T>> boolean isSizeConsistent(
        AVLTree<T> tree, ITreeNode<T> x) {
      if (isNull(x)) return true;
      if (x.size() != tree.size(x.left()) + tree.size(x.right()) + 1) return false;
      return isSizeConsistent(tree, x.left()) && isSizeConsistent(tree, x.right());
    }

    /**
     * Checks if rank is consistent.
     *
     * @return {@code true} if rank is consistent
     */
    private static <T extends Comparable<T>> boolean isRankConsistent(AVLTree<T> tree) {
      for (int i = 0; i < tree.size(); i++) if (i != tree.rank(tree.select(i))) return false;
      for (T val : tree.values()) if (val.compareTo(tree.select(tree.rank(val))) != 0) return false;
      return true;
    }
  }
}
