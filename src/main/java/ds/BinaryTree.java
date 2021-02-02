package ds;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.toString;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
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
@SuppressWarnings({"PMD.CommentSize", "nullness"})
public class BinaryTree<E extends Comparable<E>> implements Tree<E> {
  /**
   * A tree is a hierarchical structure of TreeNode objects. root references the first node on the
   * tree.
   */
  private TreeNode<E> root;

  @SuppressWarnings("PMD.NullAssignment")
  public BinaryTree() {
    root = null;
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

  /* an iterator class to iterate over binary trees
   * @author Biagioni, Edoardo
   * @assignment lecture 17
   * @date March 12,2008
   */

  private class TreeIterator<E extends Comparable<E>> implements Iterator<E> {
    /**
     * the class variables keep track of how much the iterator has done so far, and what remains to
     * be done. root is null when the iterator has not been initialized, or the entire tree has been
     * visited. the first stack keeps track of the last node to return and all its ancestors the
     * second stack keeps track of whether the node visited is to the left (false) or right (true)
     * of its parent
     */
    protected TreeNode<E> root;

    protected Queue<TreeNode<E>> queue;
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
      queue = new ArrayDeque<>();
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
        case BREADTH_FIRST_ORDER:
          return breadthfirstNext();
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

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.NullAssignment"})
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
        popRightChild();
        visitingRightChild.push(Boolean.TRUE);
        // now push everything down to the leftmost node
        // in the right subtree
        TreeNode<E> right = visiting.peek().right;
        assertNonNull(right);
        pushLeftmostNodeRecord(right);
        // use recursive call to visit that node
        return postorderNext();
      }
    }

    @Generated
    private void popRightChild() {
      if (visitingRightChild.pop()) throw new AssertionError("Shouldn't be here...");
    }

    @Generated
    private void assertNonNull(TreeNode<E> node) {
      if (isNull(node)) throw new AssertionError("Node shouldn't be null.");
    }

    // return the node at the top of the queue, enqueue the next nodes if any
    @SuppressWarnings("PMD.NullAssignment")
    private E breadthfirstNext() {
      // at beginning of iterator
      if (queue.isEmpty()) queue.add(root);
      TreeNode<E> node = queue.poll();
      if (nonNull(node.left)) queue.add(node.left);
      if (nonNull(node.right)) queue.add(node.right);
      if (queue.isEmpty()) root = null;
      return node.val;
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
        case BREADTH_FIRST_ORDER:
          return "post: " + toString(root) + "\n" + visiting + "\n" + visitingRightChild;
      }
      return "none of pre-order, in-order, or post-order are true";
    }

    private String toString(TreeNode<E> node) {
      return isNull(node)
          ? ""
          : node.toString() + "(" + toString(node.left) + ", " + toString(node.right) + ")";
    }
  }
}
