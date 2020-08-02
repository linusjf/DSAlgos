package ds;

public class SelectionSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    int out;
    int in;
    int min;
    resetCounts();
    for (out = 0; out < length - 1; out++) {
      min = out;
      for (in = out + 1; in < length; in++) {
        comparisonCount++;
        if (a[in] < a[min]) min = in;
      }
      swap(a, out, min);
      swapCount++;
    }
  }
}
