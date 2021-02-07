package ds;

import ds.Tree.TraversalOrder;

public abstract class AbstractTree<T extends Comparable>> implements Tree<T> {
  /** The root node. */
  ITreeNode<T> root;

  @Override
  public ITreeNode<T> root() {
    return root;
  }

  /**
   * Checks if the binary tree is empty.
   *
   * @return {@code true} if the binary tree is empty.
   */
  @Override
  public boolean isEmpty() {
    return isNull(root);
  }

  @Override
  public Iterator<T> iterator(TraversalOrder order) {
    return new TreeIterator<>(root, order);
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
   * @throws NullPointerException if either {@code lo} or {@code hi} is {@code null}
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

}
