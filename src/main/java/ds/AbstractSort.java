package ds;

public abstract class AbstractSort implements ISort {
  protected int comparisonCount;
  protected int swapCount;
  protected int copyCount;

  protected abstract void sort(long[] array, int length);

  @Override
  public IArray sort(IArray array) {
    IArray copy = array.copy();
    sort(copy.get(), copy.count());
    return copy;
  }

  protected void swap(long[] a, int first, int second) {
    long temp = a[first];
    a[first] = a[second];
    a[second] = temp;
  }

  protected void resetCounts() {
    copyCount = swapCount = comparisonCount = 0;
  }

  public int getCopyCount() {
    return copyCount;
  }

  public int getSwapCount() {
    return swapCount;
  }

  public int getComparisonCount() {
    return comparisonCount;
  }
}