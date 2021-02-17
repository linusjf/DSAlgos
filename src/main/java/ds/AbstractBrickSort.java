package ds;

import static ds.MathUtils.isOdd;

public abstract class AbstractBrickSort extends AbstractSort {

  @Override
  protected abstract void sort(long[] a, int length);

  protected abstract void bubble(long[] a, int i);

  protected void bubbleStartOdd(long... unusedArray) {
    throw new UnsupportedOperationException("Unsupported..");
  }

  protected void bubbleStartEven(long... unusedArray) {
    throw new UnsupportedOperationException("Unsupported..");
  }

  protected int computeMaxComparisons(int length) {
    return isOdd(length) ? length * ((length - 1) >> 1) : (length >> 1) * (length - 1);
  }

  public abstract boolean isSorted();
}
