package ds;

public class CombSort extends AbstractSort {
  private static final double GAP_SHRINK_FACTOR = 1.3d;

  @Override
  protected void sort(long[] a, int length) {
    int gap = length;
    boolean sorted = false;
    resetCounts();
    for (int j = 0; j < a.length; j++) System.out.printf("%d ", a[j]);
    while (!sorted) {
      ++outerLoopCount;
      gap = (int) ((double) gap / GAP_SHRINK_FACTOR);
      if (gap <= 1) {
        gap = 1;
        sorted = true;
      }
      System.out.println("Gap = " + gap);
      int i = 0;
      while ((i + gap) < length) {
        ++innerLoopCount;
        ++comparisonCount;
        if (a[i] > a[i + gap]) {
          swap(a, i, i + gap);
          ++swapCount;
          sorted = false;
        }
        ++i;
      }
      for (int j = 0; j < a.length; j++) System.out.printf("%d ", a[j]);
      System.out.println("");
      System.out.println("Swap count = " + swapCount);
    }
  }
}
