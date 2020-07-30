package ds;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** Demonstrates array class with high-level interface. */
@SuppressWarnings("PMD.LawOfDemeter")
public class OrdArrayRecursive extends OrdArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(OrdArrayRecursive.class.getName());

  private final Lock w = new ReentrantReadWriteLock().writeLock();

  public OrdArrayRecursive() {
    this(100);
  }

  public OrdArrayRecursive(int max) {
    this(max, false);
  }

  public OrdArrayRecursive(int max, boolean strict) {
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
    w.lock();
    try {
      int length = nElems.intValue();
      if (length == a.length) throw new ArrayIndexOutOfBoundsException(length);
      return insert(value, length);
    } finally {
      w.unlock();
    }
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
    w.lock();
    try {
      return delete(value, nElems.intValue());
    } finally {
      w.unlock();
    }
  }

  public int syncInsert(long val) {
    return insert(val);
  }

  public boolean syncDelete(long val) {
    return delete(val);
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
    if (!(o instanceof OrdArrayRecursive)) return false;
    final OrdArrayRecursive other = (OrdArrayRecursive) o;
    if (!other.canEqual((Object) this)) return false;
    if (!super.equals(o)) return false;
    return true;
  }

  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof OrdArrayRecursive;
  }

  @Override
  @SuppressWarnings("all")
  public int hashCode() {
    final int result = super.hashCode();
    return result;
  }
}
