package ds;

public class CocktailShakerSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    resetCounts();
    int start = 0;
    int end = length - 2;
    while (start <= end) {
      ++outerLoopCount;
      int newStart = end;
      int newEnd = start;
      for (int i = start; i <= end; ++i) {
        ++innerLoopCount;
        ++comparisonCount;
        if (a[i] > a[i + 1]) {
          swap(a, i, i + 1);
          ++swapCount;
          newEnd = i;
        }
      }
      end = newEnd - 1;
      for (int i = end; i >= start; --i) {
        ++innerLoopCount;
        ++comparisonCount;
        if (a[i] > a[i + 1]) {
          swap(a, i, i + 1);
          ++swapCount;
          newStart = i;
        }
      }
      start = newStart + 1;
    }
  }
}
