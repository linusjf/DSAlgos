package ds;

public class BubbleSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    if (!shouldSort(length)) return;
    int n = length;
    while (n > 1) {
      ++outerLoopCount;
      int newn = 0;
      for (int i = 1; i < n; ++i) {
        ++innerLoopCount;
        ++comparisonCount;
        if (a[i - 1] > a[i]) {
          swap(a, i - 1, i);
          newn = i;
          ++swapCount;
        }
      }
      n = newn;
    }
  }
}
