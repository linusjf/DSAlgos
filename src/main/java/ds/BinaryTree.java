package ds;

import static java.util.Objects.*;

import java.util.Iterator;
import java.util.Stack;

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
@SuppressWarnings("PMD.CommentSize")
public class BinaryTree<E extends Comparable<E>> implements Tree<E> {
  /**
   * A tree is a hierarchical structure of TreeNode objects. root references the first node on the
   * tree.
   */
  private TreeNode<E> root;

  @SuppressWarnings({"nullness", "PMD.NullAssignment"})
  public BinaryTree() {
    root = null;
  }

  /**
   * Store an object in the tree. The object must conform to type Comparable in order to be inserted
   * in the correct location. Multiple objects representing the same value can be added.
   *
   * @param obj reference to Comparable object to add.
   */
  @SuppressWarnings("nullness")
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
  @Override
  public boolean contains(E obj) {
    return isNull(root) ? false : nonNull(root.find(obj));
  }

  /**
   * Remove an object with a matching value from the tree. If multiple matches are possible, only
   * the first matching object is removed.
   *
   * @param obj Remove an object with a matching value from the tree.
   */
  @Override
  public void remove(E obj) {
    if (nonNull(root)) root = root.remove(obj, root);
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

  /**
   * Helper class used to implement tree nodes. As this is a private helper class it is acceptable
   * to have public instance variables. Instances of this class are never made available to client
   * code of the tree.
   */
  private static class TreeNode<T extends Comparable<T>> {
    /** Data object reference. */
    T val;

    /** Left and right child nodes. */
    TreeNode<T> left;

    TreeNode<T> right;

    /**
     * Constructor for TreeNode.
     *
     * @param val data object reference
     * @param left left child node reference or null
     * @param right right child node reference or null
     */
    @SuppressWarnings("nullness")
    TreeNode(T val, TreeNode<T> left, TreeNode<T> right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    /**
     * Insert an object into the tree.
     *
     * @param obj object to insert into tree.
     */
    @SuppressWarnings("nullness")
    public void insert(T obj) {
      requireNonNull(obj);
      if (val.compareTo(obj) < 0) {
        if (isNull(right)) right = new TreeNode<>(obj, null, null);
        else right.insert(obj);

      } else {
        if (isNull(left)) left = new TreeNode<>(obj, null, null);
        else left.insert(obj);
      }
    }

    /**
     * Find an object in the tree. Objects are compared using the compareTo method, so must conform
     * to type Comparable. Two objects are equal if they represent the same value.
     *
     * @param obj Object representing value to find in tree.
     * @return reference to matching node or null.
     */
    @SuppressWarnings("nullness")
    public TreeNode<T> find(T obj) {
      int temp = val.compareTo(obj);
      if (temp == 0) return this;

      if (temp < 0) return (isNull(right)) ? null : right.find(obj);

      return (isNull(left)) ? null : left.find(obj);
    }

    /**
     * Remove the node referencing an object representing the same value as the argument object.
     * This recursive method essentially restructures the tree as necessary and returns a reference
     * to the new root. The algorithm is straightforward apart from the case where the node to be
     * removed has two children. In that case the left-most leaf node of the right child is moved up
     * the tree to replace the removed node. Hand work some examples to see how this works.
     *
     * @param obj Object representing value to remove from tree.
     * @param t Root node of the sub-tree currently being examined (possibly null).
     * @return reference to the (possibly new) root node of the sub-tree being examined or null if
     *     no node.
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "nullness"})
    private TreeNode<T> remove(T obj, TreeNode<T> node) {
      requireNonNull(obj);
      TreeNode<T> t = node;
      if (isNull(t)) return t;

      if (obj.compareTo(t.val) < 0) t.left = remove(obj, t.left);
      else if (obj.compareTo(t.val) > 0) t.right = remove(obj, t.right);
      else if (isNull(t.left) || isNull(t.right)) t = isNull(t.left) ? t.right : t.left;
      else {
        t.val = findMin(t.right).val;
        t.right = remove(t.val, t.right);
      }
      return t;
    }

    /**
     * Helper method to find the left most leaf node in a sub-tree.
     *
     * @param t TreeNode to be examined.
     * @return reference to left most leaf node or null.
     */
    @SuppressWarnings("nullness")
    private TreeNode<T> findMin(TreeNode<T> node) {
      TreeNode<T> t = node;
      if (isNull(t)) return null;
      while (nonNull(t.left)) t = t.left;
      return t;
    }
  }

  /* an iterator class to iterate over binary trees
   * @author Biagioni, Edoardo
   * @assignment lecture 17
   * @date March 12,2008
   */

  private class TreeIterator implements Iterator<E> {
    /**
     * the class variables keep track of how much the iterator has done so far, and what remains to
     * be done. root is null when the iterator has not been initialized, or the entire tree has been
     * visited. the first stack keeps track of the last node to return and all its ancestors the
     * second stack keeps track of whether the node visited is to the left (false) or right (true)
     * of its parent
     */
    protected TreeNode<E> root;

    protected Stack<TreeNode<E>> visiting;
    protected Stack<Boolean> visitingRightChild;
    TraversalOrder order;

    /**
     * Constructor for traversal.
     *
     * @param root of the tree to traverse
     * @param order of the tree to traverse
     */
    TreeIterator(TreeNode<E> root, TraversalOrder order) {
      this.root = root;
      visiting = new Stack<>();
      visitingRightChild = new Stack<>();
      this.order = order;
    }

    @Override
    public boolean hasNext() {
      return nonNull(root);
    }

    @SuppressWarnings({"checkstyle:MissingSwitchDefault", "checkstyle:ReturnCount"})
    @Override
    public E next() {
      if (!hasNext()) throw new java.util.NoSuchElementException("no more elements");
      switch (order) {
        case PRE_ORDER:
          return preorderNext();
        case IN_ORDER:
          return inorderNext();
        case POST_ORDER:
          return postorderNext();
      }
      return null;
    }

    // return the node at the top of the stack, push the next node if any
    @SuppressWarnings("PMD.NullAssignment")
    private E preorderNext() {
      // at beginning of iterator
      if (visiting.empty()) visiting.push(root);
      TreeNode<E> node = visiting.pop();
      // need to visit the left subtree first, then the right
      // since a stack is a LIFO, push the right subtree first, then
      // the left.  Only push non-null trees
      if (nonNull(node.right)) visiting.push(node.right);
      if (nonNull(node.left)) visiting.push(node.left);
      // may not have pushed anything.  If so, we are at the end
      // no more nodes to visit
      if (visiting.empty()) root = null;
      return node.val;
    }

    /**
     * Find the leftmost node from this root, pushing all the intermediate nodes onto the visiting
     * stack. are trying to reach the leftmost node changes visiting takes all nodes between node
     * and the leftmost.
     *
     * @param node the root of the subtree
     */
    private void pushLeftmostNode(TreeNode<E> node) {
      // find the leftmost node
      if (nonNull(node)) {
        // push this node
        visiting.push(node);
        // recurse on next left node
        pushLeftmostNode(node.left);
      }
    }

    /* return the leftmost node that has not yet been visited
     * that node is normally on top of the stack
     * inorder traversal doesn't use the visitingRightChild stack
     */
    @SuppressWarnings("PMD.NullAssignment")
    private E inorderNext() {
      if (visiting.empty()) {
        // at beginning of iterator
        // find the leftmost node, pushing all the intermediate nodes
        // onto the visiting stack
        pushLeftmostNode(root);
      }
      // now the leftmost unvisited node is on top of the visiting stack
      TreeNode<E> node = visiting.pop();
      E result = node.val;
      // this is the value to return
      // if the node has a right child, its leftmost node is next
      if (nonNull(node.right)) {
        TreeNode<E> right = node.right;
        // find the leftmost node of the right child
        pushLeftmostNode(right);
        // note "node" has been replaced on the stack by its right child
      }
      // else: no right subtree, go back up the stack
      // next node on stack will be next returned
      // no next node left
      if (visiting.empty()) root = null;
      return result;
    }

    /**
     * Find the leftmost node from this root. pushing all the intermediate nodes onto the visiting
     * stack and also stating that each is a left child of its parent are trying to reach the
     * leftmost node
     *
     * @changes visiting takes all nodes between node and the leftmost
     * @param node the root of the subtree for which we
     */
    private void pushLeftmostNodeRecord(TreeNode<E> node) {
      // find the leftmost node
      if (nonNull(node)) {
        // push this node
        visiting.push(node);
        visitingRightChild.push(Boolean.FALSE);
        // record that it is on the left
        pushLeftmostNodeRecord(node.left);
        // continue looping
      }
    }

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.NullAssignment", "nullness"})
    private E postorderNext() {
      // at beginning of iterator
      // find the leftmost node, pushing all the intermediate nodes
      // onto the visiting stack
      // the node on top of the visiting stack is the next one to be
      // visited, unless it has a right subtree
      if (visiting.empty()) pushLeftmostNodeRecord(root);
      // no right subtree, or
      // already visited right child, time to visit the node on top
      if (visiting.peek().right == null || visitingRightChild.peek()) {
        // right subtree already visited
        E result = visiting.pop().val;
        visitingRightChild.pop();
        if (visiting.empty()) root = null;
        return result;
      } else {
        // now visit this node's right subtree
        // pop false and push true for visiting right child
        if (visitingRightChild.pop()) assert false;
        visitingRightChild.push(Boolean.TRUE);
        // now push everything down to the leftmost node
        // in the right subtree
        TreeNode<E> right = visiting.peek().right;
        assert nonNull(right);
        pushLeftmostNodeRecord(right);
        // use recursive call to visit that node
        return postorderNext();
      }
    }

    /* not implemented */
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove");
    }

    /* give the entire state of the iterator: the tree and the two stacks */
    @SuppressWarnings({"checkstyle:MissingSwitchDefault", "checkstyle:ReturnCount"})
    @Override
    public String toString() {
      switch (order) {
        case PRE_ORDER:
          return "pre: " + toString(root) + "\n" + visiting + "\n";
        case IN_ORDER:
          return "in: " + toString(root) + "\n" + visiting + "\n";
        case POST_ORDER:
          return "post: " + toString(root) + "\n" + visiting + "\n" + visitingRightChild;
      }
      return "none of pre-order, in-order, or post-order are true";
    }

    private String toString(TreeNode<E> node) {
      return (isNull(node))
          ? ""
          : node.toString() + "(" + toString(node.left) + ", " + toString(node.right) + ")";
    }
  }
}
