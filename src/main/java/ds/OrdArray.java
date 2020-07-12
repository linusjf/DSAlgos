package ds;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicInteger;

/** Demonstrates array class with high-level interface. */
@SuppressWarnings("PMD.LawOfDemeter")
public class OrdArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(OrdArray.class.getName());

  private final long[] a;
  private final AtomicInteger nElems;
  private final Object lock = new Object();
  private final boolean strict;
  private int modCount;

  public OrdArray(int max) {
    this(max, false);
  }

  public OrdArray(int max, boolean strict) {
    a = new long[max];
    nElems = new AtomicInteger();
    this.strict = strict;
  }

  public int findIndex(long searchKey) {
    int lowerBound = 0;
    int upperBound = nElems.intValue() - 1;
    int curIn;
    while (true) {
      curIn = (lowerBound + upperBound) / 2;
      if (a[curIn] == searchKey) return curIn;
      else if (lowerBound > upperBound) return nElems.intValue();
      else {
        if (a[curIn] < searchKey) lowerBound = curIn + 1;
        else upperBound = curIn - 1;
      }
    }
  }

  // -----------------------------------------------------------
  public boolean find(long searchKey) {
    return findIndex(searchKey) < nElems.intValue();
  }

  /**
   * Insert element into array.
   *
   * @param value element to insert
   * @return index of inserted element.
   */
  public int insert(long value) {
    int expectedCount = modCount;
    int index = nElems.intValue();
    if (index == a.length) throw new ArrayIndexOutOfBoundsException(index);
    int j;
    for (j = 0; j < index; j++) {
      if (strict && expectedCount != modCount)
        throw new ConcurrentModificationException("Error inserting value: " + value);
      if (a[j] > value) break;
    }
    for (int k = index; k > j; k--) {
      if (strict && expectedCount != modCount)
        throw new ConcurrentModificationException("Error inserting value: " + value);
      a[k] = a[k - 1];
    }
    ++modCount;
    a[j] = value;
    nElems.getAndIncrement();
    return j;
  }

  public int syncInsert(long value) {
    synchronized (lock) {
      return insert(value);
    }
  }

  public void clear() {
    if (nElems.intValue() == 0) return;
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

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public boolean delete(long value) {
    int expectedModCount = modCount;
    int j = findIndex(value);
    if (j == nElems.intValue()) return false;
    if (strict && expectedModCount != modCount)
      throw new ConcurrentModificationException("Error deleting value: " + value);
    fastDelete(j);
    return true;
  }

  @SuppressWarnings({"PMD.SystemPrintln", "PMD.LawOfDemeter"})
  public void display() {
    System.out.println(this);
  }

  @Override
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("nElems = ").append(nElems).append(System.lineSeparator());
    long[] newArray = a.clone();
    for (int j = 0; j < nElems.intValue(); j++) sb.append(newArray[j]).append(' ');
    return sb.toString();
  }

  public int count() {
    return nElems.intValue();
  }

  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof OrdArray)) return false;
    final OrdArray other = (OrdArray) o;
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
    return other instanceof OrdArray;
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
