package ds;

import static ds.AssertionUtils.assertEquality;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"nullness", "PMD.SystemPrintln", "PMD.LawOfDemeter"})
public final class TreeChecks {
  @Generated
  private TreeChecks() {
    throw new IllegalStateException("Private constructor");
  }

  /**
   * Checks if the AVL tree invariants are fine.
   *
   * @return {@code true} if the AVL tree invariants are fine
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public static <T extends Comparable<T>> boolean check(Tree<T> tree) {
    boolean bst = isBST(tree);
    if (!bst) System.out.println("Symmetric order not consistent");
    boolean avl = isAVL(tree);
    if (!avl) System.out.println("AVL property not consistent");
    boolean sizeConsistent = isSizeConsistent(tree);
    if (!sizeConsistent) System.out.println("Subtree counts not consistent");
    checkRankConsistency(tree);
    return bst && avl && sizeConsistent;
  }

  /**
   * Checks if AVL property is consistent.
   *
   * @return {@code true} if AVL property is consistent.
   */
  public static <T extends Comparable<T>> boolean isAVL(Tree<T> tree) {
    return isAVL(tree, tree.root());
  }

  /**
   * Checks if AVL property is consistent in the subtree.
   *
   * @param x the subtree
   * @return {@code true} if AVL property is consistent in the subtree
   */
  private static <T extends Comparable<T>> boolean isAVL(Tree<T> tree, ITreeNode<T> x) {
    if (isNull(x)) return true;
    int bf = x == null ? 0 : x.balanceFactor();
    if (bf > 1 || bf < -1) return false;
    return isAVL(tree, x.left()) && isAVL(tree, x.right());
  }

  /**
   * Checks if the symmetric order is consistent.
   *
   * @return {@code true} if the symmetric order is consistent
   */
  public static <T extends Comparable<T>> boolean isBST(Tree<T> tree) {
    return isBST(tree.root(), null, null);
  }

  /**
   * Checks if the tree rooted at x is a BST. With all values strictly between min and max. (if min
   * or max is null, treat as empty constraint). Credit: Bob Dondero's elegant solution
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
  @SuppressWarnings("PMD.SystemPrintln")
  public static <T extends Comparable<T>> boolean isSizeConsistent(Tree<T> tree) {
    boolean sizing = tree.size() == tree.sizeFromValues();
    if (!sizing) System.out.println("Computed tree sizes don't match");
    return sizing && isSizeConsistent(tree, tree.root());
  }

  /**
   * Checks if the size of the subtree is consistent.
   *
   * @return {@code true} if the size of the subtree is consistent
   */
  private static <T extends Comparable<T>> boolean isSizeConsistent(Tree<T> tree, ITreeNode<T> x) {
    if (isNull(x)) return true;
    if (x.size() != size(x.left()) + size(x.right()) + x.refCount()) return false;
    return isSizeConsistent(tree, x.left()) && isSizeConsistent(tree, x.right());
  }

  /**
   * Returns the number of nodes in the subtree.
   *
   * @param x the subtree
   * @return the number of nodes in the subtree
   */
  private static <T extends Comparable<T>> int size(ITreeNode<T> x) {
    return TreeUtils.size(x);
  }

  /** Checks if rank is consistent. */
  public static <T extends Comparable<T>> void checkRankConsistency(Tree<T> tree) {
    int treeSize = tree.size();
    Iterable<T> iterable = tree.values();
    List<T> result = new ArrayList<>();
    iterable.forEach(result::add);

    for (int i = 0; i < treeSize; i++) {
      T val = result.get(i);
      int rank = tree.rank(val);
      int rankIt = tree.rankFromValues(val);
      assertEquality(rank, rankIt);
    }
  }
}
