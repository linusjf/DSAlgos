package ds;

public class ShellSort extends AbstractSort {

  protected int gapCount;

  @Override
  public int getTimeComplexity() {
    return innerLoopCount > 0
        ? innerLoopCount
        : outerLoopCount > 0 ? gapCount * outerLoopCount : gapCount;
  }

  @Override
  protected void reset() {
    super.reset();
    gapCount = 0;
  }

  @Override
  protected void sort(long[] a, int length) {
    {
      if (!shouldSort(length)) return;
      int n = length;
      for (int gap = n >> 1; gap > 0; gap = gap >> 1) {
        ++gapCount;
        for (int i = gap; i < n; i += 1) {
          ++outerLoopCount;
          long temp = a[i];
          int j;
          for (j = i; j >= gap && a[j - gap] > temp; j -= gap) {
            ++comparisonCount;
            ++copyCount;
            ++innerLoopCount;
            System.arraycopy(a, j - gap, a, j, 1);
          }
          if (j >= gap) ++comparisonCount;
          if (i != j) a[j] = temp;
        }
      }
    }
  }
}
