package ds;

import static ds.MathUtils.isOdd;

public abstract class AbstractBrickSort extends AbstractSort {

  @Override
  protected abstract void sort(long[] a, int length);
  
  protected int computeMaxComparisons(int length) {
    return isOdd(length) ? length * ((length - 1) >> 1) : (length >> 1) * (length - 1);
  }

  protected abstract void bubble(long[] a, int i);

  public abstract boolean isSorted();
}
