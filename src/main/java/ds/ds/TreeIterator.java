package ds;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import ds.Tree.TraversalOrder;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

/* an iterator class to iterate over binary trees
 * @author Biagioni, Edoardo
 * @assignment lecture 17
 * @date March 12,2008
 */
@SuppressWarnings({"nullness", "PMD.LawOfDemeter"})
public class TreeIterator<E extends Comparable<E>> implements Iterator<E> {
  /**
   * the class variables keep track of how much the iterator has done so far, and what remains to be
   * done. root is null when the iterator has not been initialized, or the entire tree has been
   * visited. the first stack keeps track of the last node to return and all its ancestors the
   * second stack keeps track of whether the node visited is to the left (false) or right (true) of
   * its parent
   */
  protected ITreeNode<E> root;

  protected ITreeNode<E> prevNode;

  protected Queue<ITreeNode<E>> queue;
  protected Stack<ITreeNode<E>> visiting;

  protected Stack<Boolean> visitingRightChild;
  TraversalOrder order;

  /**
   * Constructor for traversal.
   *
   * @param root of the tree to traverse
   * @param order of the tree to traverse
   */
  TreeIterator(ITreeNode<E> root, TraversalOrder order) {
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

  private void checkRefCount(ITreeNode<E> node) {
    if (node.refCount() > 1) {
      prevNode = node.clone();
      prevNode.decrementRefCount();
    }
  }

  @SuppressWarnings({"checkstyle:MissingSwitchDefault", "checkstyle:ReturnCount"})
  @Override
  public E next() {
    if (!hasNext()) throw new java.util.NoSuchElementException("no more elements");
    E next = handleDuplicates();
    if (nonNull(next)) return next;
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

  @SuppressWarnings({"PMD.NullAssignment", "PMD.AvoidDeeplyNestedIfStmts"})
  private E handleDuplicates() {
    if (nonNull(prevNode) && prevNode.refCount() > 0) {
      E val = prevNode.value();
      prevNode.decrementRefCount();
      if (prevNode.refCount() == 0) {
        prevNode = null;
        switch (order) {
          case PRE_ORDER:
          case IN_ORDER:
          case POST_ORDER:
            if (visiting.empty()) root = null;
            break;
          case BREADTH_FIRST_ORDER:
            if (queue.isEmpty()) root = null;
            break;
        }
      }
      return val;
    }
    return null;
  }

  // return the node at the top of the stack, push the next node if any
  @SuppressWarnings("PMD.NullAssignment")
  private E preorderNext() {
    // at beginning of iterator
    if (visiting.empty()) visiting.push(root);
    ITreeNode<E> node = visiting.pop();
    // need to visit the left subtree first, then the right
    // since a stack is a LIFO, push the right subtree first, then
    // the left.  Only push non-null trees
    ITreeNode<E> right = node.right();
    ITreeNode<E> left = node.left();
    if (nonNull(right)) visiting.push(right);

    if (nonNull(left)) visiting.push(left);
    checkRefCount(node);
    // may not have pushed anything.  If so, we are at the end
    // no more nodes to visit
    if (visiting.empty()) root = null;
    return node.value();
  }

  /**
   * Find the leftmost node from this root, pushing all the intermediate nodes onto the visiting
   * stack. are trying to reach the leftmost node changes visiting takes all nodes between node and
   * the leftmost.
   *
   * @param node the root of the subtree
   */
  private void pushLeftmostNode(ITreeNode<E> node) {
    // find the leftmost node
    if (nonNull(node)) {
      // push this node
      visiting.push(node);
      // recurse on next left node
      pushLeftmostNode(node.left());
    }
  }

  /* return the leftmost node that has not yet been visited
   * that node is normally on top of the stack
   * inorder traversal doesn't use the visitingRightChild stack
   */
  @SuppressWarnings("PMD.NullAssignment")
  private E inorderNext() {
    // at beginning of iterator
    // find the leftmost node, pushing all the intermediate nodes
    // onto the visiting stack
    if (visiting.empty()) pushLeftmostNode(root);
    // now the leftmost unvisited node is on top of the visiting stack
    ITreeNode<E> node = visiting.pop();
    // if the node has a right child, its leftmost node is next
    ITreeNode<E> right = node.right();
    // find the leftmost node of the right child
    // note "node" has been replaced on the stack by its right child
    if (nonNull(right)) pushLeftmostNode(right);
    // else: no right subtree, go back up the stack
    // next node on stack will be next returned
    // no next node left
    checkRefCount(node);
    if (visiting.empty() && isNull(prevNode)) root = null;
    return node.value();
  }

  /**
   * Find the leftmost node from this root. pushing all the intermediate nodes onto the visiting
   * stack and also stating that each is a left child of its parent are trying to reach the leftmost
   * node
   *
   * @changes visiting takes all nodes between node and the leftmost
   * @param node the root of the subtree for which we
   */
  private void pushLeftmostNodeRecord(ITreeNode<E> node) {
    // find the leftmost node
    if (nonNull(node)) {
      // push this node
      visiting.push(node);
      visitingRightChild.push(Boolean.FALSE);
      // record that it is on the left
      pushLeftmostNodeRecord(node.left());
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
    if (visiting.peek().right() == null || visitingRightChild.peek()) {
      // right subtree already visited
      ITreeNode<E> node = visiting.pop();
      visitingRightChild.pop();
      checkRefCount(node);
      if (visiting.empty() && isNull(prevNode)) root = null;
      return node.value();
    } else {
      // now visit this node's right subtree
      // pop false and push true for visiting right child
      popRightChild();
      visitingRightChild.push(Boolean.TRUE);
      // now push everything down to the leftmost node
      // in the right subtree
      ITreeNode<E> right = visiting.peek().right();
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
  private void assertNonNull(ITreeNode<E> node) {
    if (isNull(node)) throw new AssertionError("Node shouldn't be null.");
  }

  // return the node at the top of the queue, enqueue the next nodes if any
  @SuppressWarnings("PMD.NullAssignment")
  private E breadthfirstNext() {
    // at beginning of iterator
    if (queue.isEmpty()) queue.add(root);
    ITreeNode<E> node = queue.poll();
    ITreeNode<E> left = node.left();
    ITreeNode<E> right = node.right();
    if (nonNull(left)) queue.add(left);
    if (nonNull(right)) queue.add(right);
    checkRefCount(node);
    if (queue.isEmpty() && isNull(prevNode)) root = null;
    return node.value();
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
        return "breadth-first : " + toString(root) + "\n" + queue;
    }
    return "none of pre-order, in-order, or post-order are true";
  }

  private String toString(ITreeNode<E> node) {
    return isNull(node)
        ? ""
        : node.toString() + "(" + toString(node.left()) + ", " + toString(node.right()) + ")";
  }
}
