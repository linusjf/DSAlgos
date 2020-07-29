package ds;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/** Demonstrates array class with high-level interface. */
@SuppressWarnings("PMD.LawOfDemeter")
public class OrdArrayRecursive extends OrdArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(OrdArrayRecursive.class.getName());

  private Object lock = new Object();

  private Random random = new Random();

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
    int length = nElems.intValue();
    if (length == a.length) throw new ArrayIndexOutOfBoundsException(length);
    AtomicInteger index = new AtomicInteger();
    Thread inserter =
        new Thread(
            () -> {
              int ret = -1;
              while (ret == -1) {
                CompareAndCheck cac = new CompareAndCheck(modCount.intValue());
                ret = insert(value, length, cac);
                try {
                  TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException ie) {
                  Thread.currentThread().interrupt();
                }
              }
              index.set(ret);
            });
    inserter.start();
    try {
      inserter.join(0);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    return index.intValue();
  }

  protected int insert(long value, int length, CompareAndCheck cac) {
    int j = findIndex(value, length);
    j = j < 0 ? -1 * j - 1 : j;
    if (strict) {
      boolean areEqual = cac.compareTo(modCount.intValue());
      if (!areEqual) return -1;
      return checkedMoveAndInsert(j, length, value);
    }
    moveAndInsert(j, length, value);
    return j;
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  @Override
  public boolean delete(long value) {
    AtomicBoolean deleted = new AtomicBoolean();
    Thread deleter =
        new Thread(
            () -> {
              int ret = Integer.MIN_VALUE;
              while (ret == Integer.MIN_VALUE) {
                int length = nElems.intValue();
                CompareAndCheck cac = new CompareAndCheck(modCount.intValue());
                ret = delete(value, length, cac);
                try {
                  TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException ie) {
                  Thread.currentThread().interrupt();
                }
              }
              deleted.set(ret >= 0);
            });
    deleter.start();
    try {
      deleter.join(0);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    return deleted.get();
  }

  public int syncInsert(long val) {
    return insert(val);
  }

  public boolean syncDelete(long val) {
    return delete(val);
  }

  protected int delete(long value, int length, CompareAndCheck cac) {
    int j = findIndex(value, length);
    if (j < 0) return j;
    if (strict) {
      boolean areEqual = cac.compareTo(modCount.intValue());
      if (!areEqual) return Integer.MIN_VALUE;
      return checkedFastDelete(j, length);
    }
    fastDelete(j, length);
    return j;
  }

  private int checkedMoveAndInsert(int j, int length, long value) {
    synchronized (lock) {
      if (nElems.intValue() != length) return -1;
      System.out.println("checked move and insert");
      moveAndInsert(j, length, value);
      return j;
    }
  }

  private int checkedFastDelete(int j, int length) {
    synchronized (lock) {
      if (nElems.intValue() != length) return Integer.MIN_VALUE;
      System.out.println("checked fast delete");
      fastDelete(j, length);
      return j;
    }
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

  static class CompareAndCheck {
    int value;

    CompareAndCheck(int value) {
      this.value = value;
    }

    public boolean compareTo(int newValue) {
      return value == newValue;
    }
  }
}
