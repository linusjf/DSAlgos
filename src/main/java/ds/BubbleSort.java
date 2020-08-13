package ds;

public class BubbleSort extends AbstractSort {

  protected boolean sorted;

  @Override
  protected void reset() {
    super.reset();
    sorted = false;
  }

  @Override
  protected void sort(long[] a, int length) {
    if (length < 0)
      throw new IllegalArgumentException("Invalid length : " + length);
    reset();
    if (length <= 1)
    {
      sorted = true;
      return;
    }
    int n = length;
    while (n > 1) {
      ++outerLoopCount;
      int newn = 0;
      for (int i = 1; i < n; ++i) {
        ++innerLoopCount;
        ++comparisonCount;
        if (a[i - 1] > a[i]) {
          swap(a, i - 1, i);
          newn = i;
          ++swapCount;
        }
      }
      n = newn;
    }
  }
  sorted = true;
}
