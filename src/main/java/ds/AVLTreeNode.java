package ds;

  /** This class represents a node of the AVL tree. */
  @SuppressWarnings("nullness")
  public class AVLTreeNode<T extends Comparable<T>> extends TreeNode<T> implements IAVLTreeNode<T> {
    // height of the subtree
    int height;
    // number of nodes in subtree
    int size;

    AVLTreeNode(T val, int height, int size) {
      this(val, null, null);
      this.size = size;
      this.height = height;
    }

    AVLTreeNode(T val, AVLTreeNode<T> left, AVLTreeNode<T> right) {
      super(val, left, right);
    }

    public int height() {
      return this.height;
    }
    
    public int size() {
      return this.size;
    }
  }
