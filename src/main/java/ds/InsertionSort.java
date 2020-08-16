package ds;

public class InsertionSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    if (length < 0) throw new IllegalArgumentException("Invalid length parameter: " + length);
    reset();
    if (length <= 1) return;
    int in;
    int out;

    for (out = 1; out < length; ++out) {
      long temp = a[out];
      in = out;
      ++outerLoopCount;
      while (in > 0 && a[in - 1] > temp) {
        --in;
        ++comparisonCount;
        ++innerLoopCount;
        ++copyCount;
      }
      System.arraycopy(a, in, a, in + 1, out - in);
      if (in > 0) ++comparisonCount;
      if (in != out) a[in] = temp;
    }
  }
}
