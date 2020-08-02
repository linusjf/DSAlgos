package ds;

import static java.lang.Math.floor;

public class CombSort extends AbstractSort {
  private static final double GAP_SHRINK_FACTOR = 1.3d;

  @Override
  protected void sort(long[] a, int length) {
    int gap = length;
    boolean sorted = false;
    resetCounts();
    while (!sorted) {
      gap = (int) floor(gap / GAP_SHRINK_FACTOR);
      if (gap <= 1) {
        gap = 1;
        sorted = true;
      }
      int i = 0;
      while (i < gap + length) {
        comparisonCount++;
        if (a[i] > a[i + gap]) {
          swap(a, i, i + gap);
          swapCount++;
          sorted = false;
        }
        ++i;
      }
    }
  }
}
