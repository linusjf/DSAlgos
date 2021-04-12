package ds;

public class SelectionSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    reset();
    for (int out = 0; out < length - 1; ++out) {
      int min = out;
      ++outerLoopCount;
      for (int in = out + 1; in < length; ++in) {
        ++comparisonCount;
        ++innerLoopCount;
        if (a[in] < a[min]) min = in;
      }
      if (min != out) {
        swap(a, out, min);
        ++swapCount;
      }
    }
  }
}
