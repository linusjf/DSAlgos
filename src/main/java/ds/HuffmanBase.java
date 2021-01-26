package ds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.PriorityQueue;

/*
 * Compress or expand a binary input stream using the Huffman algorithm.
 *
 * <p>****************************************************************************
 * </p>
 */

/**
 * The {@code HuffmanBase} class provides methods for a binary input using Huffman codes over the
 * 8-bit extended ASCII alphabet.
 *
 * <p>For additional documentation, see <a
 * href="https://algs4.cs.princeton.edu/55compression">Section 5.5</a> of <i>Algorithms, 4th
 * Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
@SuppressWarnings({"checkstyle:AbstractClassName",
"PMD.AbstractClassWithoutAbstractMethod"})
public abstract class HuffmanBase {

  // alphabet size of extended ASCII
  static final int R = 256;

  static final char NULL_CHARACTER = '\0';
  static final char ZERO_CHARACTER = '0';
  static final char ONE_CHARACTER = '1';

  protected final String input;
  protected final BinaryInputStream bis;
  protected final BinaryOutputStream bos;

  public HuffmanBase(String input) {
    this.input = input;
    this.bis = new BinaryInputStream(new ByteArrayInputStream("".getBytes()));
    this.bos = new BinaryOutputStream(new ByteArrayOutputStream());
  }

  // build the Huffman trie given frequencies
  protected Node buildTrie(int... freq) {

    // initialize priority queue with singleton trees
    PriorityQueue<Node> pq = new PriorityQueue<>();
    for (char c = 0; c < R; c++) if (freq[c] > 0) pq.offer(new Node(c, freq[c], null, null));

    // merge two smallest trees
    while (pq.size() > 1) {
      Node left = pq.poll();
      Node right = pq.poll();
      Node parent = new Node(NULL_CHARACTER, left.freq + right.freq, left, right);
      pq.offer(parent);
    }
    return pq.poll();
  }

  // write bitstring-encoded trie to standard output
  protected void writeTrie(Node x) {
    if (x.isLeaf()) {
      bos.write(true);
      bos.write(x.ch, 8);
      return;
    }
    bos.write(false);
    writeTrie(x.left);
    writeTrie(x.right);
  }

  // make a lookup table from symbols and their encodings
  protected void buildCode(String[] st, Node x, String s) {
    if (x.isLeaf()) st[x.ch] = s;
    else {
      buildCode(st, x.left, s + '0');
      buildCode(st, x.right, s + '1');
    }
  }

  protected Node readTrie() {
    boolean isLeaf = bis.readBoolean();
    if (isLeaf) return new Node(bis.readChar(), -1, null, null);
    else return new Node(NULL_CHARACTER, -1, readTrie(), readTrie());
  }

  // Huffman trie node
  @SuppressWarnings("nullness")
  static class Node implements Comparable<Node> {
    final char ch;
    final int freq;
    final Node left;
    final Node right;

    Node(char ch, int freq, Node left, Node right) {
      this.ch = ch;
      this.freq = freq;
      this.left = left;
      this.right = right;
    }

    // is the node a leaf node?
    boolean isLeaf() {
      assert left == null && right == null || left != null && right != null;
      return left == null && right == null;
    }

    // compare, based on frequency
    @Override
    public int compareTo(Node that) {
      return this.freq - that.freq;
    }
  }
}
