package ds;

import static ds.ArrayUtils.*;

import java.util.ConcurrentModificationException;

/** Demonstrates array class with high-level interface. */
@SuppressWarnings("PMD.LawOfDemeter")
public class OrdArray extends AbstractArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(OrdArray.class.getName());

  private boolean sorted = true;
  private boolean dirty;

  public OrdArray() {
    // empty constructor, implicitly calls super
  }

  public OrdArray(int max) {
    super(max);
  }

  public OrdArray(int max, boolean strict) {
    super(max, strict);
  }

  private void checkSorted() {
    sorted = isSorted(this);
  }

  @Override
  public int findIndex(long searchKey) {
    return findIndex(searchKey, nElems.intValue());
  }

  private int findIndex(long searchKey, int length) {
    int lowerBound = 0;
    int upperBound = length - 1;
    while (lowerBound <= upperBound) {
      int mid = lowerBound + ((upperBound - lowerBound) >>> 1);
      long midVal = a[mid];
      if (midVal == searchKey) return mid;
      if (midVal < searchKey) lowerBound = mid + 1;
      else upperBound = mid - 1;
    }
    // key not found
    return -(lowerBound + 1);
  }

  // -----------------------------------------------------------
  @Override
  public boolean find(long searchKey) {
    return findIndex(searchKey) >= 0;
  }

  /**
   * Insert element into array.
   *
   * @param value element to insert
   * @return index of inserted element.
   */
  @Override
  public int insert(long value) {
    int length = nElems.intValue();
    if (length == a.length) throw new ArrayIndexOutOfBoundsException(length);
    if (dirty) checkSorted();
    if (sorted) return insert(value, length);
    return -1;
  }

  private int insert(long value, int length) {
    int expectedCount = modCount.intValue();
    int j = findIndex(value, length);
    j = j < 0 ? -1 * j - 1 : j;
    checkForConcurrentUpdates(expectedCount, value, Operation.INSERT);
    moveAndInsert(j, length, value);
    return j;
  }

  private void moveAndInsert(int j, int count, long value) {
    modCount.incrementAndGet();
    int numMoved = count - j;
    System.arraycopy(a, j, a, j + 1, numMoved);
    nElems.getAndIncrement();
    a[j] = value;
  }

  private void checkForConcurrentUpdates(int expectedCount, long value, Operation operation) {
    if (strict && expectedCount < modCount.intValue()) {
      dirty = true;
      switch (operation) {
        case INSERT:
          throw new ConcurrentModificationException("Error inserting value: " + value);

        case DELETE:
          throw new ConcurrentModificationException("Error deleting value: " + value);

        default:
      }
    }
  }

  private void fastDelete(int index, int length) {
    modCount.incrementAndGet();
    // move higher ones down
    int numMoved = length - index - 1;
    System.arraycopy(a, index + 1, a, index, numMoved);
    a[nElems.decrementAndGet()] = 0;
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  @Override
  public boolean delete(long value) {
    int length = nElems.intValue();
    int j = findIndex(value, length);
    if (j < 0) return false;
    checkForConcurrentUpdates(modCount.intValue(), value, Operation.DELETE);
    fastDelete(j, length);
    return true;
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
    int result = PRIME + java.util.Arrays.hashCode(this.a);
    final Object $nElems = this.nElems;
    result = result * PRIME + ($nElems == null ? 43 : $nElems.hashCode());
    return result;
  }
}
