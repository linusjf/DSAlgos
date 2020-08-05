package ds;

public class InsertionSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    int in;
    int out;
    resetCounts();

    for (out = 1; out < length; ++out) {
      long temp = a[out];
      in = out;
      ++outerLoopCount;
      while (in > 0 && a[in - 1] >= temp) {
        --in;
        ++comparisonCount;
        ++innerLoopCount;
      }
      System.arraycopy(a, in, a, in + 1, out - in);
      copyCount += out - in;
      if (in > 0) ++comparisonCount;
      if (in != out) {
        a[in] = temp;
        ++swapCount;
        --copyCount;
      }
    }
  }
}
