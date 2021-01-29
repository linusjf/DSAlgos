package ds;

/******************************************************************************
 *  Compilation:  javac AVLTreeST.java
 *  Execution:    java AVLTreeST < input.txt
 *  Data files:   https://algs4.cs.princeton.edu/33balanced/tinyST.txt
 *
 *  A symbol table implemented using an AVL tree.
 *
 ******************************************************************************/

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

/**
 * The {@code AVLTreeST} class represents an ordered symbol table of generic value-value pairs. It
 * supports the usual <em>put</em>, <em>get</em>, <em>contains</em>, <em>delete</em>, <em>size</em>,
 * and <em>is-empty</em> methods. It also provides ordered methods for finding the <em>minimum</em>,
 * <em>maximum</em>, <em>floor</em>, and <em>ceiling</em>. It also provides a <em>values</em> method
 * for iterating over all of the values. A symbol table implements the <em>associative array</em>
 * abstraction: when associating a value with a value that is already in the symbol table, the
 * convention is to replace the old value with the new value. Unlike {@link java.util.Map}, this
 * class uses the convention that values cannot be {@code null} —setting the value associated with a
 * value to {@code null} is equivalent to deleting the value from the symbol table.
 *
 * <p>This symbol table implementation uses internally an <a
 * href="https://en.wikipedia.org/wiki/AVL_tree">AVL tree </a> (Georgy Adelson-Velsky and Evgenii
 * Landis' tree) which is a self-balancing BST. In an AVL tree, the heights of the two child
 * subtrees of any node differ by at most one; if at any time they differ by more than one,
 * rebalancing is done to restore this property.
 *
 * <p>This implementation requires that the value type implements the {@code Comparable} interface
 * and calls the {@code compareTo()} and method to compare two values. It does not call either
 * {@code equals()} or {@code hashCode()}. The <em>put</em>, <em>get</em>, <em>contains</em>,
 * <em>delete</em>, <em>minimum</em>, <em>maximum</em>, <em>ceiling</em>, and <em>floor</em>
 * operations each take logarithmic time in the worst case. The <em>size</em>, and <em>is-empty</em>
 * operations take constant time. Construction also takes constant time.
 *
 * <p>For other implementations of the same API, see {@link ST}, {@link BinarySearchST}, {@link
 * SequentialSearchST}, {@link BST}, {@link RedBlackBST}, {@link SeparateChainingHashST}, and {@link
 * LinearProbingHashST}.
 *
 * @author Marcelo Silva
 */
public class AVLTree<T extends Comparable<T>> {

  /** The root node. */
  private Node<T> root;

  /** This class represents an inner node of the AVL tree. */
  private class Node<T> {
    // the value
    private T val;
    // height of the subtree
    private int height;
    // number of nodes in subtree
    private int size;
    // left subtree
    private Node<T> left;
    // right subtree
    private Node<T> right;

    public Node(T val, int height, int size) {
      this.val = val;
      this.size = size;
      this.height = height;
    }
  }

  /** Initializes an empty symbol table. */
  public AVLTree() {}

  /**
   * Checks if the symbol table is empty.
   *
   * @return {@code true} if the symbol table is empty.
   */
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * Returns the number value-value pairs in the symbol table.
   *
   * @return the number value-value pairs in the symbol table
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
  private int size(Node<T> x) {
    if (x == null) return 0;
    return x.size;
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
  private int height(Node<T> x) {
    if (x == null) return -1;
    return x.height;
  }

  /**
   * Returns the value in the tree with the given value.
   *
   * @param val the val
   * @return the value in the symbol table and {@code null} if the value is not in the symbol table
   * @throws IllegalArgumentException if {@code val} is {@code null}
   */
  public T get(T val) {
    if (val == null) throw new IllegalArgumentException("argument to get() is null");
    Node<T> x = get(root, val);
    if (x == null) return null;
    return x.val;
  }

  /**
   * Returns value associated with the given value in the subtree or {@code null} if no such value.
   *
   * @param x the subtree
   * @param val the value
   * @return value associated with the given value in the subtree or {@code null} if no such value
   */
  private Node<T> get(Node<T> x, T value) {
    if (x == null) return null;
    int cmp = value.compareTo(x.val);
    if (cmp < 0) return get(x.left, value);
    else if (cmp > 0) return get(x.right, value);
    else return x;
  }

  /**
   * Checks if the symbol table contains the given value.
   *
   * @param val the value
   * @return {@code true} if the symbol table contains {@code val} and {@code false} otherwise
   * @throws IllegalArgumentException if {@code val} is {@code null}
   */
  public boolean contains(T val) {
    return get(val) != null;
  }

  /**
   * Inserts the specified value-value pair into the symbol table, overwriting the old value with
   * the new value if the symbol table already contains the specified value. Deletes the specified
   * value (and its associated value) from this symbol table if the specified value is {@code null}.
   *
   * @param value the value
   * @param val the value
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  public void put(T val) {
    if (val == null) return;
    root = put(root, val);
    assert check();
  }

  /**
   * Inserts the value-value pair in the subtree. It overrides the old value with the new value if
   * the symbol table already contains the specified value and deletes the specified value (and its
   * associated value) from this symbol table if the specified value is {@code null}.
   *
   * @param x the subtree
   * @param val the value
   * @return the subtree
   */
  private Node put(Node<T> x, T val) {
    if (x == null) return new Node<>(val, 0, 1);
    int cmp = val.compareTo(x.val);
    if (cmp < 0) x.left = put(x.left, val);
    else if (cmp > 0) x.right = put(x.right, val);
    else {
      x.val = val;
      return x;
    }
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return balance(x);
  }

  /**
   * Restores the AVL tree property of the subtree.
   *
   * @param x the subtree
   * @return the subtree with restored AVL property
   */
  private Node<T> balance(Node<T> x) {
    if (balanceFactor(x) < -1) {
      if (balanceFactor(x.right) > 0) {
        x.right = rotateRight(x.right);
      }
      x = rotateLeft(x);
    } else if (balanceFactor(x) > 1) {
      if (balanceFactor(x.left) < 0) {
        x.left = rotateLeft(x.left);
      }
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
  private int balanceFactor(Node<T> x) {
    return height(x.left) - height(x.right);
  }

  /**
   * Rotates the given subtree to the right.
   *
   * @param x the subtree
   * @return the right rotated subtree
   */
  private Node<T> rotateRight(Node<T> x) {
    Node<T> y = x.left;
    x.left = y.right;
    y.right = x;
    y.size = x.size;
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    y.height = 1 + Math.max(height(y.left), height(y.right));
    return y;
  }

  /**
   * Rotates the given subtree to the left.
   *
   * @param x the subtree
   * @return the left rotated subtree
   */
  private Node<T> rotateLeft(Node<T> x) {
    Node<T> y = x.right;
    x.right = y.left;
    y.left = x;
    y.size = x.size;
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    y.height = 1 + Math.max(height(y.left), height(y.right));
    return y;
  }

  /**
   * Removes the specified value and its associated value from the symbol table (if the value is in
   * the symbol table).
   *
   * @param value the value
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  public void delete(T val) {
    if (val == null) throw new IllegalArgumentException("argument to delete() is null");
    if (!contains(val)) return;
    root = delete(root, val);
    assert check();
  }

  /**
   * Removes the specified value and its associated value from the given subtree.
   *
   * @param x the subtree
   * @param value the value
   * @return the updated subtree
   */
  private Node<T> delete(Node<T> x, T val) {
    int cmp = val.compareTo(x.val);
    if (cmp < 0) {
      x.left = delete(x.left, val);
    } else if (cmp > 0) {
      x.right = delete(x.right, val);
    } else {
      if (x.left == null) {
        return x.right;
      } else if (x.right == null) {
        return x.left;
      } else {
        Node<T> y = x;
        x = min(y.right);
        x.right = deleteMin(y.right);
        x.left = y.left;
      }
    }
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return balance(x);
  }

  /**
   * Removes the smallest value and associated value from the symbol table.
   *
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException("called deleteMin() with empty symbol table");
    root = deleteMin(root);
    assert check();
  }

  /**
   * Removes the smallest value and associated value from the given subtree.
   *
   * @param x the subtree
   * @return the updated subtree
   */
  private Node<T> deleteMin(Node<T> x) {
    if (x.left == null) return x.right;
    x.left = deleteMin(x.left);
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return balance(x);
  }

  /**
   * Removes the largest value and associated value from the symbol table.
   *
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMax() {
    if (isEmpty()) throw new NoSuchElementException("called deleteMax() with empty symbol table");
    root = deleteMax(root);
    assert check();
  }

  /**
   * Removes the largest value and associated value from the given subtree.
   *
   * @param x the subtree
   * @return the updated subtree
   */
  private Node<T> deleteMax(Node<T> x) {
    if (x.right == null) return x.left;
    x.right = deleteMax(x.right);
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return balance(x);
  }

  /**
   * Returns the smallest value in the symbol table.
   *
   * @return the smallest value in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public T min() {
    if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
    return min(root).val;
  }

  /**
   * Returns the node with the smallest value in the subtree.
   *
   * @param x the subtree
   * @return the node with the smallest value in the subtree
   */
  private Node<T> min(Node<T> x) {
    if (x.left == null) return x;
    return min(x.left);
  }

  /**
   * Returns the largest value in the symbol table.
   *
   * @return the largest value in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public T max() {
    if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
    return max(root).val;
  }

  /**
   * Returns the node with the largest value in the subtree.
   *
   * @param x the subtree
   * @return the node with the largest value in the subtree
   */
  private Node<T> max(Node<T> x) {
    if (x.right == null) return x;
    return max(x.right);
  }

  /**
   * Returns the largest value in the symbol table less than or equal to {@code value}.
   *
   * @param value the value
   * @return the largest value in the symbol table less than or equal to {@code value}
   * @throws NoSuchElementException if the symbol table is empty
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  public T floor(T val) {
    if (val == null) throw new IllegalArgumentException("argument to floor() is null");
    if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
    Node<T> x = floor(root, val);
    if (x == null) return null;
    else return x.val;
  }

  /**
   * Returns the node in the subtree with the largest value less than or equal to the given value.
   *
   * @param x the subtree
   * @param value the value
   * @return the node in the subtree with the largest value less than or equal to the given value
   */
  private Node<T> floor(Node<T> x, T val) {
    if (x == null) return null;
    int cmp = val.compareTo(x.val);
    if (cmp == 0) return x;
    if (cmp < 0) return floor(x.left, val);
    Node<T> y = floor(x.right, val);
    if (y != null) return y;
    else return x;
  }

  /**
   * Returns the smallest value in the symbol table greater than or equal to {@code value}.
   *
   * @param value the value
   * @return the smallest value in the symbol table greater than or equal to {@code value}
   * @throws NoSuchElementException if the symbol table is empty
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  public T ceiling(T val) {
    if (val == null) throw new IllegalArgumentException("argument to ceiling() is null");
    if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
    Node<T> x = ceiling(root, val);
    if (x == null) return null;
    else return x.val;
  }

  /**
   * Returns the node in the subtree with the smallest value greater than or equal to the given
   * value.
   *
   * @param x the subtree
   * @param value the value
   * @return the node in the subtree with the smallest value greater than or equal to the given
   *     value
   */
  private Node<T> ceiling(Node<T> x, T val) {
    if (x == null) return null;
    int cmp = val.compareTo(x.val);
    if (cmp == 0) return x;
    if (cmp > 0) return ceiling(x.right, val);
    Node<T> y = ceiling(x.left, val);
    if (y != null) return y;
    else return x;
  }

  /**
   * Returns the kth smallest value in the symbol table.
   *
   * @param k the order statistic
   * @return the kth smallest value in the symbol table
   * @throws IllegalArgumentException unless {@code k} is between 0 and {@code size() -1 }
   */
  public T select(int k) {
    if (k < 0 || k >= size())
      throw new IllegalArgumentException("k is not in range 0-" + (size() - 1));
    Node<T> x = select(root, k);
    return x.val;
  }

  /**
   * Returns the node with value the kth smallest value in the subtree.
   *
   * @param x the subtree
   * @param k the kth smallest value in the subtree
   * @return the node with value the kth smallest value in the subtree
   */
  private Node<T> select(Node<T> x, int k) {
    if (x == null) return null;
    int t = size(x.left);
    if (t > k) return select(x.left, k);
    else if (t < k) return select(x.right, k - t - 1);
    else return x;
  }

  /**
   * Returns the number of values in the symbol table strictly less than {@code value}.
   *
   * @param value the value
   * @return the number of values in the symbol table strictly less than {@code value}
   * @throws IllegalArgumentException if {@code value} is {@code null}
   */
  public int rank(T val) {
    if (val == null) throw new IllegalArgumentException("argument to rank() is null");
    return rank(val, root);
  }

  /**
   * Returns the number of values in the subtree less than value.
   *
   * @param value the value
   * @param x the subtree
   * @return the number of values in the subtree less than value
   */
  private int rank(T val, Node<T> x) {
    if (x == null) return 0;
    int cmp = val.compareTo(x.val);
    if (cmp < 0) return rank(val, x.left);
    else if (cmp > 0) return 1 + size(x.left) + rank(val, x.right);
    else return size(x.left);
  }

  /**
   * Returns all values in the symbol table.
   *
   * @return all values in the symbol table
   */
  public Iterable<T> values() {
    return valuesInOrder();
  }

  /**
   * Returns all values in the symbol table following an in-order traversal.
   *
   * @return all values in the symbol table following an in-order traversal
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
  private void valuesInOrder(Node<T> x, Queue<T> queue) {
    if (x == null) return;
    valuesInOrder(x.left, queue);
    queue.offer(x.val);
    valuesInOrder(x.right, queue);
  }

  /**
   * Returns all values in the symbol table following a level-order traversal.
   *
   * @return all values in the symbol table following a level-order traversal.
   */
  public Iterable<T> valuesLevelOrder() {
    Queue<T> queue = new ArrayDeque<>();
    if (!isEmpty()) {
      Queue<Node<T>> queue2 = new ArrayDeque<>();
      queue2.offer(root);
      while (!queue2.isEmpty()) {
        Node<T> x = queue2.poll();
        queue.offer(x.val);
        if (x.left != null) {
          queue2.offer(x.left);
        }
        if (x.right != null) {
          queue2.offer(x.right);
        }
      }
    }
    return queue;
  }

  /**
   * Returns all values in the symbol table in the given range.
   *
   * @param lo the lowest value
   * @param hi the highest value
   * @return all values in the symbol table between {@code lo} (inclusive) and {@code hi}
   *     (exclusive)
   * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
   */
  public Iterable<T> values(T lo, T hi) {
    if (lo == null) throw new IllegalArgumentException("first argument to values() is null");
    if (hi == null) throw new IllegalArgumentException("second argument to values() is null");
    Queue<T> queue = new ArrayDeque<>();
    values(root, queue, lo, hi);
    return queue;
  }

  /**
   * Adds the values between {@code lo} and {@code hi} in the subtree to the {@code queue}.
   *
   * @param x the subtree
   * @param queue the queue
   * @param lo the lowest value
   * @param hi the highest value
   */
  private void values(Node<T> x, Queue<T> queue, T lo, T hi) {
    if (x == null) return;
    int cmplo = lo.compareTo(x.val);
    int cmphi = hi.compareTo(x.val);
    if (cmplo < 0) values(x.left, queue, lo, hi);
    if (cmplo <= 0 && cmphi >= 0) queue.offer(x.val);
    if (cmphi > 0) values(x.right, queue, lo, hi);
  }

  /**
   * Returns the number of values in the symbol table in the given range.
   *
   * @param lo minimum endpoint
   * @param hi maximum endpoint
   * @return the number of values in the symbol table between {@code lo} (inclusive) and {@code hi}
   *     (exclusive)
   * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
   */
  public int size(T lo, T hi) {
    if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
    if (hi == null) throw new IllegalArgumentException("second argument to size() is null");
    if (lo.compareTo(hi) > 0) return 0;
    if (contains(hi)) return rank(hi) - rank(lo) + 1;
    else return rank(hi) - rank(lo);
  }

  /**
   * Checks if the AVL tree invariants are fine.
   *
   * @return {@code true} if the AVL tree invariants are fine
   */
  private boolean check() {
    if (!isBST()) System.out.println("Symmetric order not consistent");
    if (!isAVL()) System.out.println("AVL property not consistent");
    if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
    if (!isRankConsistent()) System.out.println("Ranks not consistent");
    return isBST() && isAVL() && isSizeConsistent() && isRankConsistent();
  }

  /**
   * Checks if AVL property is consistent.
   *
   * @return {@code true} if AVL property is consistent.
   */
  private boolean isAVL() {
    return isAVL(root);
  }

  /**
   * Checks if AVL property is consistent in the subtree.
   *
   * @param x the subtree
   * @return {@code true} if AVL property is consistent in the subtree
   */
  private boolean isAVL(Node<T> x) {
    if (x == null) return true;
    int bf = balanceFactor(x);
    if (bf > 1 || bf < -1) return false;
    return isAVL(x.left) && isAVL(x.right);
  }

  /**
   * Checks if the symmetric order is consistent.
   *
   * @return {@code true} if the symmetric order is consistent
   */
  private boolean isBST() {
    return isBST(root, null, null);
  }

  /**
   * Checks if the tree rooted at x is a BST with all values strictly between min and max (if min or
   * max is null, treat as empty constraint) Credit: Bob Dondero's elegant solution
   *
   * @param x the subtree
   * @param min the minimum value in subtree
   * @param max the maximum value in subtree
   * @return {@code true} if if the symmetric order is consistent
   */
  private boolean isBST(Node<T> x, T min, T max) {
    if (x == null) return true;
    if (min != null && x.val.compareTo(min) <= 0) return false;
    if (max != null && x.val.compareTo(max) >= 0) return false;
    return isBST(x.left, min, x.val) && isBST(x.right, x.val, max);
  }

  /**
   * Checks if size is consistent.
   *
   * @return {@code true} if size is consistent
   */
  private boolean isSizeConsistent() {
    return isSizeConsistent(root);
  }

  /**
   * Checks if the size of the subtree is consistent.
   *
   * @return {@code true} if the size of the subtree is consistent
   */
  private boolean isSizeConsistent(Node<T> x) {
    if (x == null) return true;
    if (x.size != size(x.left) + size(x.right) + 1) return false;
    return isSizeConsistent(x.left) && isSizeConsistent(x.right);
  }

  /**
   * Checks if rank is consistent.
   *
   * @return {@code true} if rank is consistent
   */
  private boolean isRankConsistent() {
    for (int i = 0; i < size(); i++) if (i != rank(select(i))) return false;
    for (T val : values()) if (val.compareTo(select(rank(val))) != 0) return false;
    return true;
  }

  /**
   * Unit tests the {@code AVLTreeST} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    AVLTree<Integer> st = new AVLTree<>();
    Random random = new Random();
    for (int i = 0; i < 100; i++) st.put(random.nextInt(1000));
    for (Integer s : st.values()) System.out.println(s + " " + st.get(s));
  }
}
