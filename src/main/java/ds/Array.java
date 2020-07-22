package ds;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Array {

  protected final long[] a;
  protected final AtomicInteger nElems;
  protected final Object lock = new Object();
  protected final boolean strict;
  protected AtomicInteger modCount;

  enum Operation {
    INSERT,
    DELETE
  }

  public Array() {
    this(100, false);
  }

  public Array(int max) {
    this(max, false);
  }

  public Array(int max, boolean strict) {
    if (max <= 0) throw new IllegalArgumentException("Invalid size: " + max);
    a = new long[max];
    nElems = new AtomicInteger();
    modCount = new AtomicInteger();
    this.strict = strict;
  }

  public long[] get() {
    return a.clone();
  }

  public abstract int findIndex(long searchKey);

  public abstract boolean find(long searchKey);

  public abstract int insert(long value);

  public abstract boolean delete(long value);

  public int syncInsert(long value) {
    synchronized (lock) {
      return insert(value);
    }
  }

  public void clear() {
    int length = nElems.intValue();
    if (length == 0) return;
    modCount.incrementAndGet();
    Arrays.fill(a, 0, length, 0L);
    nElems.set(0);
  }

  public boolean syncDelete(long value) {
    synchronized (lock) {
      return delete(value);
    }
  }

  @SuppressWarnings({"PMD.SystemPrintln", "PMD.LawOfDemeter"})
  public void display() {
    System.out.println(this);
  }

  @Override
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public String toString() {
    int length = nElems.intValue();
    StringBuilder sb = new StringBuilder();
    sb.append("nElems = ").append(length).append(System.lineSeparator());
    long[] newArray = a.clone();
    for (int j = 0; j < length; j++) sb.append(newArray[j]).append(' ');
    return sb.toString();
  }

  public int count() {
    return nElems.intValue();
  }
}
