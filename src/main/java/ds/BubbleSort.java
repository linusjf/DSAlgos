package ds;

public class BubbleSort extends AbstractSort {

  protected void sort(long[] a, int length) {
    int n = length;
    resetCounts();
    while (n > 1) {
      int newn = 0;
      for (int i = 1; i < n; i++) {
        comparisonCount++;
        if (a[i - 1] > a[i]) {
          swap(a, i - 1, i);
          newn = i;
          swapCount++;
        }
      }
      n = newn;
    }
  }
}
