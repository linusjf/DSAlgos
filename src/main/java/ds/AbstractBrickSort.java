package ds;

import static ds.MathUtils.*;

public abstract class AbstractBrickSort extends AbstractSort {

  @Override
  protected abstract void sort(long[] a, int length);

@SuppressWarnings("PMD.SignatureDeclareThrowsException")
  protected abstract void oddSort(long[] a, int length) throws Exception;

@SuppressWarnings("PMD.SignatureDeclareThrowsException")
  protected abstract void evenSort(long[] a, int length) throws Exception;

  protected abstract void bubble(long[] a, int i);

  protected int computeMaxComparisons(int length) {
    return isOdd(length) ? length * ((length - 1) >>> 1) : (length >>> 1) * (length - 1);
  }

  public abstract boolean isSorted();
}
