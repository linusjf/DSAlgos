package ds;

import static ds.MathUtils.isOdd;

public abstract class AbstractBrickSort extends AbstractSort {

  public AbstractBrickSort() {
    // implicit super invoked
  }

  @Override
  protected abstract void sort(long[] a, int length);

  protected abstract void bubble(long[] a, int i);

  protected int computeMaxComparisons(int length) {
    return isOdd(length) ? length * ((length - 1) >> 1) : (length >> 1) * (length - 1);
  }

  public abstract boolean isSorted();
}
