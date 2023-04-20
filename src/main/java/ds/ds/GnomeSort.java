package ds;

public class GnomeSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    if (!shouldSort(length)) return;
    for (int pos = 1; pos < length; ++pos) gnomeSort(a, pos);
    outerLoopCount = length - 1;
  }

  private void gnomeSort(long[] a, int upper) {
    int pos = upper;
    while (pos > 0 && a[pos - 1] > a[pos]) {
      ++comparisonCount;
      ++innerLoopCount;
      swap(a, pos - 1, pos--);
      ++swapCount;
    }
    if (pos != 0) ++comparisonCount;
  }
}
