package ds;

import java.util.concurrent.atomic.AtomicInteger;

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
  private final AtomicInteger nElems;

  // number of data items
  // constructor
  // -----------------------------------------------------------
  public HighArray(int max) {
    a = new long[max];
    nElems = new AtomicInteger();
  }

  // -----------------------------------------------------------
  public int findIndex(long searchKey) {
    // find specified value
    for (int j = 0; j < nElems.intValue(); j++) if (a[j] == searchKey) return j;
    return -1;
  }

  // -----------------------------------------------------------
  public boolean find(long searchKey) {
    return findIndex(searchKey) >= 0;
  }

  // put element into array
  // -----------------------------------------------------------
  public void insert(long value) {
    a[nElems.getAndIncrement()] = value;
  }

  // clear array
  // -----------------------------------------------------------
  public void clear() {
    nElems.set(0);
  }

  // -----------------------------------------------------------
  public boolean delete(long value) {
    int j = findIndex(value);
    if (j == -1) return false;
    // move higher ones down
    int end = nElems.getAndDecrement();
    for (int k = j; k < end; k++) a[k] = a[k + 1];
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
    for (int j = 0; j < nElems.intValue(); j++) sb.append(a[j]).append(' ');
    return sb.toString();
  }

  public int count() {
    return nElems.intValue();
  }

  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof HighArray)) return false;
    final HighArray other = (HighArray) o;
    if (!other.canEqual((Object) this)) return false;
    if (!java.util.Arrays.equals(this.a, other.a)) return false;
    final Object this$nElems = this.nElems;
    final Object other$nElems = other.nElems;
    if (this$nElems == null ? other$nElems != null : !this$nElems.equals(other$nElems))
      return false;
    return true;
  }

  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof HighArray;
  }

  @Override
  @SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.a);
    final Object $nElems = this.nElems;
    result = result * PRIME + ($nElems == null ? 43 : $nElems.hashCode());
    return result;
  }
}
