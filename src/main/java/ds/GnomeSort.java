package ds;

public class GnomeSort extends AbstractSort {

  protected boolean sorted;

  public boolean isSorted() {
    return sorted;
  }

  @Override
  protected void reset() {
    super.reset();
    sorted = false;
  }

  @Override
  protected void sort(long[] a, int length) {
    if (length < 0) throw new IllegalArgumentException("Illegal value for length: " + length);
    reset();
    if (length <= 1) {
      sorted = true;
      return;
    }
    for (int pos = 1; pos < length; ++pos) gnomeSort(a, pos);
    outerLoopCount = length - 1;
    sorted = true;
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
