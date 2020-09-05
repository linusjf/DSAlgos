package ds;

import java.util.ConcurrentModificationException;

/**
 * Demonstrates array class with high-level interface.
 *
 * <p>To run this program: C&gt;java
 */
@SuppressWarnings("PMD.LawOfDemeter")
public class HighArray extends AbstractArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(HighArray.class.getName());

  public HighArray() {
  }

  public HighArray(int max) {
    this(max, false);
  }

  public HighArray(int max, boolean strict) {
    super(max, strict);
  }

  public HighArray(HighArray array) {
    super(array);
  }

  @Override
  public IArray copy() {
    return new HighArray(this);
  }

  @Override
  public int findIndex(long searchKey) {
    int length = nElems.intValue();
    for (int j = 0; j < length; j++) {
      if (a[j] == searchKey) return j;
    }
    return -1;
  }

  // -----------------------------------------------------------
  @Override
  public boolean find(long searchKey) {
    return findIndex(searchKey) >= 0;
  }

  @Override
  public int insert(long value) {
    int length = nElems.intValue();
    if (length == a.length) throw new ArrayIndexOutOfBoundsException(length);
    modCount.incrementAndGet();
    a[nElems.getAndIncrement()] = value;
    return length;
  }

  private void fastDelete(int index, int length) {
    modCount.incrementAndGet();
    // move higher ones down
    int numMoved = length - index - 1;
    System.arraycopy(a, index + 1, a, index, numMoved);
    a[nElems.decrementAndGet()] = 0;
  }

  protected void checkDeleteConcurrent(int expectedCount, long value) {
    if (expectedCount < modCount.intValue()) {
      throw new ConcurrentModificationException("Error deleting value: " + value);
    }
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  @Override
  public boolean delete(long value) {
    int length = nElems.intValue();
    int expectedCount = modCount.intValue();
    for (int j = 0; j < length; j++) {
      if (strict) checkDeleteConcurrent(expectedCount, value);
      if (a[j] == value) {
        fastDelete(j, length);
        return true;
      }
    }
    return false;
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
    int result = PRIME + java.util.Arrays.hashCode(this.a);
    final Object $nElems = this.nElems;
    result = result * PRIME + ($nElems == null ? 43 : $nElems.hashCode());
    return result;
  }
}
