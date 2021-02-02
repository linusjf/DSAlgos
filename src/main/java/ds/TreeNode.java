package ds;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Helper class used to implement tree nodes. As this is a private helper class it is acceptable to
 * have public instance variables. Instances of this class are never made available to client code
 * of the tree.
 */
public class TreeNode<T extends Comparable<T>> implements ITreeNode<T> {
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
  @Override
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
   * Find an object in the tree. Objects are compared using the compareTo method, so must conform to
   * type Comparable. Two objects are equal if they represent the same value.
   *
   * @param obj Object representing value to find in tree.
   * @return reference to matching node or null.
   */
  @Override
  public ITreeNode<T> find(T obj) {
    int temp = val.compareTo(obj);
    if (temp == 0) return this;

    if (temp < 0) return isNull(right) ? null : right.find(obj);

    return isNull(left) ? null : left.find(obj);
  }

  /**
   * Remove the node referencing an object representing the same value as the argument object. This
   * recursive method essentially restructures the tree as necessary and returns a reference to the
   * new root. The algorithm is straightforward apart from the case where the node to be removed has
   * two children. In that case the left-most leaf node of the right child is moved up the tree to
   * replace the removed node. Hand work some examples to see how this works.
   *
   * @param obj Object representing value to remove from tree.
   * @param t Root node of the sub-tree currently being examined (possibly null).
   * @return reference to the (possibly new) root node of the sub-tree being examined or null if no
   *     node.
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public ITreeNode<T> remove(T obj) {
    requireNonNull(obj);
    TreeNode<T> t = this;

    if (obj.compareTo(t.val) < 0) t.left = (TreeNode<T>) t.left.remove(obj);
    else if (obj.compareTo(t.val) > 0) t.right = (TreeNode<T>) t.right.remove(obj);
    else if (isNull(t.left) || isNull(t.right)) t = isNull(t.left) ? t.right : t.left;
    else {
      t.val = findMin(t.right).val;
      t.right = (TreeNode<T>) t.right.remove(t.val);
    }
    return t;
  }

  /**
   * Helper method to find the left most leaf node in a sub-tree.
   *
   * @param t TreeNode to be examined.
   * @return reference to left most leaf node or null.
   */
  private TreeNode<T> findMin(TreeNode<T> node) {
    TreeNode<T> t = node;
    while (nonNull(t.left)) t = t.left;
    return t;
  }

  @Generated
  @SuppressWarnings("PMD.UnnecessaryFullyQualifiedName")
  @Override
  public String toString() {
    return Objects.toString(val);
  }
}
