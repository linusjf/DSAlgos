package ds;

import static ds.MathUtils.isOdd;
import static java.lang.Math.abs;

public abstract class AbstractBrickSort extends AbstractSort {

  @Override
  protected abstract void sort(long[] a, int length);

  protected abstract void bubble(long[] a, int i);

  protected int computeMaxComparisons(int length) {
    return isOdd(length) ? length * ((length - 1) >> 1) : (length >> 1) * (length - 1);
  }

  public abstract boolean isSorted();

  public static int computeOddTaskCount(int length) {
    if (length < 0) throw new IllegalArgumentException("Illegal argument value: " + length);
    return isOdd(length) ? length >> 1 : abs(length - 1) >> 1;
  }

  public static int computeEvenTaskCount(int length) {
    if (length < 0) throw new IllegalArgumentException("Illegal argument value: " + length);
    return length >> 1;
  }
}
