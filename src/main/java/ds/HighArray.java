package ds;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
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
  private final AtomicInteger nElems;
  private final Object lock = new Object();
  private int modCount;

  public HighArray(int max) {
    a = new long[max];
    nElems = new AtomicInteger();
  }

  public int findIndex(long searchKey) {
    for (int j = 0; j < nElems.intValue(); j++) {
      if (a[j] == searchKey) return j;
    }
    return -1;
  }

  // -----------------------------------------------------------
  public boolean find(long searchKey) {
    return findIndex(searchKey) >= 0;
  }

  public void insert(long value) {
    int index = nElems.intValue();
    if (index == a.length) throw new ArrayIndexOutOfBoundsException(index);
    ++modCount;
    a[nElems.getAndIncrement()] = value;
  }

  public void clear() {
    ++modCount;
    Arrays.fill(a, 0L);
    nElems.set(0);
  }

  private void fastDelete(int index) {
    ++modCount;
    // move higher ones down
    int numMoved = nElems.intValue() - index - 1;
    if (numMoved > 0) System.arraycopy(a, index + 1, a, index, numMoved);
    a[nElems.decrementAndGet()] = 0;
  }

  public boolean syncDelete(long value) {
    synchronized (lock) {
      return delete(value);
    }
  }

  // -----------------------------------------------------------
  public boolean delete(long value) {
    int expectedModCount = modCount;
    for (int j = 0; j < nElems.intValue(); j++) {
      if (expectedModCount != modCount)
        throw new ConcurrentModificationException("Error deleting value: " + value);
      if (a[j] == value) {
        fastDelete(j);
        return true;
      }
    }
    return false;
  }

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
