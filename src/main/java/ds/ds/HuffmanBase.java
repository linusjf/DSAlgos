package ds;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
@SuppressWarnings({
  "checkstyle:AbstractClassName",
  "PMD.AbstractClassWithoutAbstractMethod",
  "nullness"
})
public abstract class HuffmanBase {

  // alphabet size of extended ASCII
  static final int R = 256;

  static final char NULL_CHARACTER = '\0';
  static final char ZERO_CHARACTER = '0';
  static final char ONE_CHARACTER = '1';

  protected final BinaryInputStream bis;
  protected final BinaryOutputStream bos;

  public HuffmanBase(File input, File output) throws IOException {
    this.bis = new BinaryInputStream(Files.newInputStream(input.toPath()));
    this.bos = new BinaryOutputStream(Files.newOutputStream(output.toPath()));
  }

  // build the Huffman trie given frequencies
  protected Node buildTrie(int... freq) {

    // initialize priority queue with singleton trees
    PriorityQueue<Node> pq = new PriorityQueue<>();
    Node node = new Node('a', 0, null, null);
    for (char c = 0; c < R; c++)
      if (freq[c] > 0) {
        Node newNode = node.clone();
        newNode.ch = c;
        newNode.freq = freq[c];
        pq.offer(newNode);
      }

    // merge two smallest trees
    while (pq.size() > 1) {
      Node left = pq.poll();
      Node right = pq.poll();
      Node parent = node.clone();
      parent.left = left;
      parent.right = right;
      parent.ch = NULL_CHARACTER;
      parent.freq = left.freq + right.freq;
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
    if (isLeaf) {
      char ch = bis.readChar();
      return new Node(ch, -1, null, null);
    } else return new Node(NULL_CHARACTER, -1, readTrie(), readTrie());
  }

  // Huffman trie node
  protected static final class Node implements Cloneable, Comparable<Node> {
    char ch;
    int freq;
    Node left;
    Node right;

    Node(char ch, int freq, Node left, Node right) {
      this.ch = ch;
      this.freq = freq;
      this.left = left;
      this.right = right;
    }

    // is the node a leaf node?
    @Generated
    boolean isLeaf() {
      return left == null && right == null;
    }

    // compare, based on frequency
    @Override
    public int compareTo(Node that) {
      return this.freq - that.freq;
    }

    @Generated
    @SuppressWarnings("checkstyle:NoClone")
    @Override
    public Node clone() {
      try {
        return (Node) super.clone();
      } catch (CloneNotSupportedException cnse) {
        throw new AssertionError("Shouldn't get here..." + cnse.getMessage(), cnse);
      }
    }
  }
}
