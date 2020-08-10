package ds;

public abstract class AbstractBrickSort extends AbstractSort {

  @Override
  protected abstract void sort(long[] a, int length);

  protected abstract void oddSort(long[] a, int length) throws Exception;

  protected abstract void evenSort(long[] a, int length) throws Exception;

  protected abstract void bubble(long[] a, int i);

  protected int computeMaxComparisons(int length) {
    return (length & 1) == 1 ? length * ((length - 1) >>> 1) : (length >>> 1) * (length - 1);
  }

  public abstract boolean isSorted();
}
