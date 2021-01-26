package ds;

/*
 *
 * Expand a binary input stream using the Huffman algorithm.
 *
 * <p>****************************************************************************
 * </p>
 *
 */

/**
 * The {@code HuffmanDecompressor} class provides methods for expanding a binary input using Huffman
 * codes over the 8-bit extended ASCII alphabet.
 *
 * <p>For additional documentation, see <a
 * href="https://algs4.cs.princeton.edu/55compression">Section 5.5</a> of <i>Algorithms, 4th
 * Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class HuffmanDecompressor extends HuffmanBase {

  public HuffmanDecompressor(String input) {
    super(input);
  }

  /**
   * Reads a sequence of bits that represents a Huffman-compressed message from standard input;
   * expands them; and writes the results to standard output.
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public void expand() {

    // read in Huffman trie from input stream
    Node root = readTrie();

    // number of bytes to write
    int length = bis.readInt();

    // decode using the Huffman trie
    for (int i = 0; i < length; i++) {
      Node x = root;
      while (!x.isLeaf()) {
        boolean bit = bis.readBoolean();
        if (bit) x = x.right;
        else x = x.left;
      }
      bos.write(x.ch, 8);
    }
    bos.close();
  }
}
