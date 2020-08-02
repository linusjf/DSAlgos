package ds;

public class BrickSort extends AbstractSort {

  protected void sort(long[] a, int length) {
    boolean isSorted = false;
    resetCounts();
    while (!isSorted) {
      isSorted = true;
      int temp = 0;
      // Perform Bubble sort on odd indexed element
      for (int i = 1; i <= length - 2; i = i + 2) {
        comparisonCount++;
        if (a[i] > a[i + 1]) {
          swap(a, i, i + 1);
          isSorted = false;
          swapCount++;
        }
      }
      // Perform Bubble sort on even indexed element
      for (int i = 0; i <= length - 2; i = i + 2) {
        comparisonCount++;
        if (a[i] > a[i + 1]) {
          swap(a, i, i + 1);
          isSorted = false;
          swapCount++;
        }
      }
    }
  }
}
