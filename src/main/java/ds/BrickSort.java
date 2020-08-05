package ds;

public class BrickSort extends AbstractSort {

  private boolean isSorted;

  private void reset() {
    resetCounts();
    isSorted = false;
  }

  @Override
  protected void sort(long[] a, int length) {
    reset();
    final int maxComparisons =
        (length & 1) == 1 ? length * ((length - 1) >>> 1) : (length >>> 1) * (length - 1);
    while (!isSorted) {
      ++outerLoopCount;
      isSorted = true;
      oddSort(a, length);
      if (swapCount == maxComparisons) {
        isSorted = true;
        break;
      }
      evenSort(a, length);
      if (swapCount == maxComparisons) isSorted = true;
    }
  }

  private void oddSort(long[] a, int length) {
    for (int i = 1; i < length - 1; i = i + 2) {
      ++innerLoopCount;
      ++comparisonCount;
      bubble(a, i);
    }
  }

  private void evenSort(long[] a, int length) {
    for (int i = 0; i < length - 1; i = i + 2) {
      ++innerLoopCount;
      ++comparisonCount;
      bubble(a, i);
    }
  }

  protected void bubble(long[] a, int i) {
    if (a[i] > a[i + 1]) {
      swap(a, i, i + 1);
      isSorted = false;
      ++swapCount;
    }
  }
}
