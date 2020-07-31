package ds;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** Demonstrates array class with high-level interface. */
@SuppressWarnings("PMD.LawOfDemeter")
public class OrdArrayLock extends OrdArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(OrdArrayLock.class.getName());

  private final Lock w = new ReentrantReadWriteLock(true).writeLock();
  private final Random random = new Random();

  public OrdArrayLock() {
    this(100);
  }

  public OrdArrayLock(int max) {
    this(max, false);
  }

  public OrdArrayLock(int max, boolean strict) {
    super(max, strict);
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
    int j = findIndex(value, length);
    j = j < 0 ? -1 * j - 1 : j;
    moveAndInsert(j, length, value);
    return j;
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  @Override
  public boolean delete(long value) {
    return delete(value, nElems.intValue());
  }

  public int syncInsert(long val) {
    w.lock();
    try {
      return insert(val);
    } finally {
      w.unlock();
    }
  }

  public boolean syncDelete(long val) {
    w.lock();
    try {
      return delete(val);
    } finally {
      w.unlock();
    }
  }

  protected boolean delete(long value, int length) {
    int j = findIndex(value, length);
    if (j < 0) return false;
    fastDelete(j, length);
    return true;
  }

  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof OrdArrayLock)) return false;
    final OrdArrayLock other = (OrdArrayLock) o;
    if (!other.canEqual((Object) this)) return false;
    if (!super.equals(o)) return false;
    return true;
  }

  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof OrdArrayLock;
  }

  @Override
  @SuppressWarnings("all")
  public int hashCode() {
    final int result = super.hashCode();
    return result;
  }
}
