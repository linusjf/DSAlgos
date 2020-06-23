package ds;

import java.util.Arrays;

/**
 * Demonstrates array class with high-level interface.
 *
 * <p>To run this program: C&gt;java
 */
public class HighArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(HighArray.class.getName());

  private final long[] a;
  // ref to array a
  private int nElems;

  // number of data items
  // constructor
  // -----------------------------------------------------------
  public HighArray(int max) {
    a = new long[max];
    // create the array
    nElems = 0;
    // no items yet
  }

  // -----------------------------------------------------------
  public int findIndex(long searchKey) {
    // find specified value
    for (int j = 0; j < nElems; j++) if (a[j] == searchKey) return j;
    return -1;
  }

  // -----------------------------------------------------------
  public boolean find(long searchKey) {
    return findIndex(searchKey) >= 0;
  }

  // put element into array
  // -----------------------------------------------------------
  public void insert(long value) {
    // insert it
    a[nElems] = value;
    // increment size
    ++nElems;
  }

  // clear array
  // -----------------------------------------------------------
  public void clear() {
    Arrays.fill(a, 0, nElems, 0L);
    nElems = 0;
  }

  // -----------------------------------------------------------
  public boolean delete(long value) {
    int j = findIndex(value);
    if (j == -1) return false;
    // move higher ones down
    for (int k = j; k < nElems; k++) a[k] = a[k + 1];
    a[nElems] = 0;
    // decrement size
    --nElems;
    return true;
  }

  // displays array contents
  // -----------------------------------------------------------
  @SuppressWarnings({"PMD.SystemPrintln", "PMD.LawOfDemeter"})
  public void display() {
    System.out.println(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("nElems = ").append(nElems).append(System.lineSeparator());
    for (int j = 0; j < nElems; j++) sb.append(a[j]).append(' ');
    return sb.toString();
  }

  public int count() {
    return nElems;
  }

  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof HighArray)) return false;
    final HighArray other = (HighArray) o;
    if (!other.canEqual((Object) this)) return false;
    if (!java.util.Arrays.equals(this.a, other.a)) return false;
    return true;
  }

  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof HighArray;
  }

  @Override
  @SuppressWarnings("all")
  public final int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.a);
    return result;
  }
}
