package ds;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** Demonstrates array class with high-level interface. */
public class OrdArrayLock extends AbstractOrdArray {
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(OrdArrayLock.class.getName());
  private final Lock w = new ReentrantReadWriteLock(true).writeLock();

  public OrdArrayLock() {
    this(100);
  }

  public OrdArrayLock(int max) {
    this(max, false);
  }

  public OrdArrayLock(int max, boolean strict) {
    super(max, strict);
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
    return super.hashCode();
  }
}
