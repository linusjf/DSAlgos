package ds;

import java.util.ConcurrentModificationException;

/** Demonstrates array class with high-level interface. */
public abstract class AbstractOrdArray extends AbstractArray {

  public AbstractOrdArray(int max, boolean strict) {
    super(max, strict);
  }

  public AbstractOrdArray(AbstractOrdArray array) {
    super(array);
  }

  @Override
  public int findIndex(long searchKey) {
    return findIndex(searchKey, nElems.intValue());
  }

  protected int findIndex(long searchKey, int length) {
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
    return insert(value, length);
  }

  protected int insert(long value, int length) {
    int expectedCount = modCount.intValue();
    int j = findIndex(value, length);
    j = j < 0 ? -1 * j - 1 : j;
    if (strict) checkInsertConcurrent(expectedCount, value);
    moveAndInsert(j, length, value);
    return j;
  }

  protected void moveAndInsert(int j, int count, long value) {
    modCount.incrementAndGet();
    int numMoved = count - j;
    System.arraycopy(a, j, a, j + 1, numMoved);
    nElems.getAndIncrement();
    a[j] = value;
  }

  protected void checkInsertConcurrent(int expectedCount, long value) {
    if (expectedCount < modCount.intValue()) {
      throw new ConcurrentModificationException("Error inserting value: " + value);
    }
  }

  protected void checkDeleteConcurrent(int expectedCount, long value) {
    if (expectedCount < modCount.intValue()) {
      throw new ConcurrentModificationException("Error deleting value: " + value);
    }
  }

  protected void fastDelete(int index, int length) {
    modCount.incrementAndGet();
    // move higher ones down
    int numMoved = length - index - 1;
    System.arraycopy(a, index + 1, a, index, numMoved);
    a[nElems.decrementAndGet()] = 0;
  }

  @Override
  public boolean delete(long value) {
    return delete(value, nElems.intValue());
  }

  @SuppressWarnings("PMD.PrematureDeclaration")
  protected boolean delete(long value, int length) {
    int expectedCount = modCount.intValue();
    int j = findIndex(value, length);
    if (j < 0) return false;
    if (strict) checkDeleteConcurrent(expectedCount, value);
    fastDelete(j, length);
    return true;
  }

  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof AbstractOrdArray)) return false;
    final AbstractOrdArray other = (AbstractOrdArray) o;
    if (!other.canEqual((Object) this)) return false;
    if (!super.equals(o)) return false;
    return true;
  }

  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof AbstractOrdArray;
  }

  @Override
  @SuppressWarnings("all")
  public int hashCode() {
    final int result = super.hashCode();
    return result;
  }
}
