package ds;

public class CombSort extends AbstractSort {
  private static final double GAP_SHRINK_FACTOR = 1.3d;

  private int getNextGap(int gap) {
    // Shrink gap by Shrink factor
    gap = (gap * 10) / 13;
    if (gap < 1) return 1;
    return gap;
  }

  @Override
  protected void sort(long[] a, int length) {
    int gap = length;
    boolean swapped = true;
    resetCounts();
    while (gap != 1 || swapped) {
      ++outerLoopCount;
      gap = getNextGap(gap);
      swapped = false;
      for (int i = 0; i < length - gap; i++) {
        ++innerLoopCount;
        ++comparisonCount;
        if (a[i] > a[i + gap]) {
          swap(a, i, i + gap);
          swapped = true;
          ++swapCount;
        }
      }
    }
  }
}
