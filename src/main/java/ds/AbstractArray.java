package ds;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractArray implements IArray {

  protected final long[] a;
  protected final AtomicInteger nElems;
  protected final Object lock = new Object();
  protected boolean strict;
  protected AtomicInteger modCount;

  public AbstractArray(int max, boolean strict) {
    if (max <= 0) throw new IllegalArgumentException("Invalid size: " + max);
    a = new long[max];
    nElems = new AtomicInteger();
    modCount = new AtomicInteger();
    this.strict = strict;
  }

  @Override
  public long[] get() {
    return a.clone();
  }

  @Override
  public long[] getExtentArray() {
    return Arrays.copyOfRange(a, 0, nElems.intValue());
  }

  @Override
  public abstract int findIndex(long searchKey);

  @Override
  public abstract boolean find(long searchKey);

  @Override
  public abstract int insert(long value);

  @Override
  public abstract boolean delete(long value);

  public int syncInsert(long value) {
    synchronized (lock) {
      return insert(value);
    }
  }

  @Override
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

  @Override
  @SuppressWarnings({"PMD.SystemPrintln", "PMD.LawOfDemeter"})
  public void display() {
    System.out.println(this);
  }

  @Override
  @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.LawOfDemeter"})
  public String toString() {
    int length = nElems.intValue();
    StringBuilder sb = new StringBuilder();
    String lineSeparator = System.lineSeparator();
    sb.append(getClass().getName())
        .append(lineSeparator)
        .append("nElems = ")
        .append(length)
        .append(lineSeparator);
    long[] newArray = a.clone();
    for (int j = 0; j < length; j++) sb.append(newArray[j]).append(' ');
    return sb.toString();
  }

  @Override
  public int count() {
    return nElems.intValue();
  }
}
