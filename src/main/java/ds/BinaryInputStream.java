package ds;

/******************************************************************************
 *  Supports reading binary data from input stream.
 *
 *
 ******************************************************************************/

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

/**
 * <i>Binary input stream</i>. This class provides methods for reading in bits from input stteam,
 * either one bit at a time (as a {@code boolean}), 8 bits at a time (as a {@code byte} or {@code
 * char}), 16 bits at a time (as a {@code short}), 32 bits at a time (as an {@code int} or {@code
 * float}), or 64 bits at a time (as a {@code double} or {@code long}).
 *
 * <p>All primitive types are assumed to be represented using their standard Java representations,
 * in big-endian (most significant byte first) order.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
@SuppressWarnings({"PMD.CommentSize", "PMD.SystemPrintln"})
public final class BinaryInputStream {
  // end of file
  private static final int EOF = -1;

  private static final String READING_EMPTY = "Reading from empty input stream";

  private static final int BYTE_SIZE = 8;
  private static final int CHAR_SIZE = 16;
  private static final int INT_SIZE = 32;

  // input stream
  private final BufferedInputStream in;
  // one character buffer
  private int buffer;
  // number of bits left in buffer
  private int n;

  public BinaryInputStream(InputStream in) {
    this.in = new BufferedInputStream(in);
    buffer = 0;
    n = 0;
    fillBuffer();
  }

  private void fillBuffer() {
    try {
      buffer = in.read();
      n = BYTE_SIZE;
    } catch (IOException e) {
      System.err.println("EOF: " + e.getMessage());
      buffer = EOF;
      n = -1;
    }
  }

  /** Close this input stream and release any associated system resources. */
  public void close() {
    try {
      in.close();
    } catch (IOException ioe) {
      throw new IllegalStateException("Could not close BinaryInputStream", ioe);
    }
  }

  /**
   * Returns true if standard input is empty.
   *
   * @return true if and only if standard input is empty
   */
  public boolean isEmpty() {
    return buffer == EOF;
  }

  /**
   * Reads the next bit of data from standard input and return as a boolean.
   *
   * @return the next bit of data from standard input as a {@code boolean}
   * @throws NoSuchElementException if standard input is empty
   */
  public boolean readBoolean() {
    if (isEmpty()) throw new NoSuchElementException(READING_EMPTY);
    n--;
    boolean bit = ((buffer >> n) & 1) == 1;
    if (n == 0) fillBuffer();
    return bit;
  }

  /**
   * Reads the next 8 bits from standard input and return as an 8-bit char. Note that {@code char}
   * is a 16-bit type; to read the next 16 bits as a char, use {@code readChar(16)}.
   *
   * @return the next 8 bits of data from standard input as a {@code char}
   * @throws NoSuchElementException if there are fewer than 8 bits available on standard input
   */
  @SuppressWarnings("PMD.PrematureDeclaration")
  public char readChar() {
    if (isEmpty()) throw new NoSuchElementException(READING_EMPTY);

    // special case when aligned byte
    if (n == BYTE_SIZE) {
      int x = buffer;
      fillBuffer();
      return (char) (x & 0xff);
    }

    // combine last n bits of current buffer with first 8-n bits of new buffer
    int x = buffer;
    x <<= 8 - n;
    int oldN = n;
    fillBuffer();
    if (isEmpty()) throw new NoSuchElementException(READING_EMPTY);
    n = oldN;
    x |= buffer >>> n;
    return (char) (x & 0xff);
    // the above code doesn't quite work for the last character if n = 8
    // because buffer will be -1, so there is a special case for aligned byte
  }

  /**
   * Reads the next <em>r</em> bits from standard input and return as an <em>r</em>-bit character.
   *
   * @param r number of bits to read.
   * @return the next r bits of data from standard input as a {@code char}
   * @throws NoSuchElementException if there are fewer than {@code r} bits available on standard
   *     input
   * @throws IllegalArgumentException unless {@code 1 <= r <= 16}
   */
  public char readChar(int r) {
    if (r < 1 || r > CHAR_SIZE) throw new IllegalArgumentException("Illegal value of r = " + r);

    // optimize r = 8 case
    if (r == BYTE_SIZE) return readChar();

    char x = 0;
    for (int i = 0; i < r; i++) {
      x <<= 1;
      boolean bit = readBoolean();
      if (bit) x |= 1;
    }
    return x;
  }

  /**
   * Reads the remaining bytes of data from standard input and return as a string.
   *
   * @return the remaining bytes of data from standard input as a {@code String}
   * @throws NoSuchElementException if standard input is empty or if the number of bits available on
   *     standard input is not a multiple of 8 (byte-aligned)
   */
  public String readString() {
    if (isEmpty()) throw new NoSuchElementException(READING_EMPTY);

    StringBuilder sb = new StringBuilder();
    while (!isEmpty()) {
      char c = readChar();
      sb.append(c);
    }
    return sb.toString();
  }

  /**
   * Reads the next 16 bits from standard input and return as a 16-bit short.
   *
   * @return the next 16 bits of data from standard input as a {@code short}
   * @throws NoSuchElementException if there are fewer than 16 bits available on standard input
   */
  @SuppressWarnings("PMD.AvoidUsingShortType")
  public short readShort() {
    short x = 0;
    for (int i = 0; i < 2; i++) {
      char c = readChar();
      x <<= 8;
      x |= c;
    }
    return x;
  }

  /**
   * Reads the next 32 bits from standard input and return as a 32-bit int.
   *
   * @return the next 32 bits of data from standard input as a {@code int}
   * @throws NoSuchElementException if there are fewer than 32 bits available on standard input
   */
  public int readInt() {
    int x = 0;
    for (int i = 0; i < 4; i++) {
      char c = readChar();
      x <<= 8;
      x |= c;
    }
    return x;
  }

  /**
   * Reads the next <em>r</em> bits from standard input and return as an <em>r</em>-bit int.
   *
   * @param r number of bits to read.
   * @return the next r bits of data from standard input as a {@code int}
   * @throws NoSuchElementException if there are fewer than {@code r} bits available on standard
   *     input
   * @throws IllegalArgumentException unless {@code 1 <= r <= 32}
   */
  public int readInt(int r) {
    if (r < 1 || r > INT_SIZE) throw new IllegalArgumentException("Illegal value of r = " + r);

    // optimize r = 32 case
    if (r == INT_SIZE) return readInt();

    int x = 0;
    for (int i = 0; i < r; i++) {
      x <<= 1;
      boolean bit = readBoolean();
      if (bit) x |= 1;
    }
    return x;
  }

  /**
   * Reads the next 64 bits from standard input and return as a 64-bit long.
   *
   * @return the next 64 bits of data from standard input as a {@code long}
   * @throws NoSuchElementException if there are fewer than 64 bits available on standard input
   */
  public long readLong() {
    long x = 0;
    for (int i = 0; i < 8; i++) {
      char c = readChar();
      x <<= 8;
      x |= c;
    }
    return x;
  }

  /**
   * Reads the next 64 bits from standard input and return as a 64-bit double.
   *
   * @return the next 64 bits of data from standard input as a {@code double}
   * @throws NoSuchElementException if there are fewer than 64 bits available on standard input
   */
  public double readDouble() {
    return Double.longBitsToDouble(readLong());
  }

  /**
   * Reads the next 32 bits from standard input and return as a 32-bit float.
   *
   * @return the next 32 bits of data from standard input as a {@code float}
   * @throws NoSuchElementException if there are fewer than 32 bits available on standard input
   */
  public float readFloat() {
    return Float.intBitsToFloat(readInt());
  }

  /**
   * Reads the next 8 bits from standard input and return as an 8-bit byte.
   *
   * @return the next 8 bits of data from standard input as a {@code byte}
   * @throws NoSuchElementException if there are fewer than 8 bits available on standard input
   */
  public byte readByte() {
    char c = readChar();
    return (byte) (c & 0xff);
  }
}
