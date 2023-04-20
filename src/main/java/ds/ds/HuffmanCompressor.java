package ds;

import java.io.File;
import java.io.IOException;

/*
 *
 * Compress a binary input stream using the Huffman algorithm.
 *
 * <p>****************************************************************************
 * </p>
 *
 */

/**
 * The {@code HuffmanCompressor} class provides methods for compressing a binary input using Huffman
 * codes over the 8-bit extended ASCII alphabet.
 *
 * <p>For additional documentation, see <a
 * href="https://algs4.cs.princeton.edu/55compression">Section 5.5</a> of <i>Algorithms, 4th
 * Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class HuffmanCompressor extends HuffmanBase {

  public HuffmanCompressor(File input, File output) throws IOException {
    super(input, output);
  }

  /**
   * Reads a sequence of 8-bit bytes from standard input; compresses them using Huffman codes with
   * an 8-bit alphabet; and writes the results to standard output.
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public void compress() {
    // read the input
    String s = bis.readString();
    char[] input = s.toCharArray();

    // tabulate frequency counts
    int[] freq = new int[R];
    for (char i : input) freq[i]++;

    // build Huffman trie
    Node root = buildTrie(freq);

    // build code table
    String[] st = new String[R];
    buildCode(st, root, "");

    // print trie for decoder
    writeTrie(root);

    // print number of bytes in original uncompressed message
    bos.write(input.length);

    // use Huffman code to encode input
    for (char i : input) {
      String code = st[i];
      for (int j = 0; j < code.length(); j++) {
        if (code.charAt(j) == ZERO_CHARACTER) bos.write(false);
        else bos.write(true);
      }
    }

    // close output stream
    bos.close();
  }
}
