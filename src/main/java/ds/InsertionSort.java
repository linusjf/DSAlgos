package ds;

public class InsertionSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    int in;
    int out;
    resetCounts();

    System.out.println("a: " + a);
    for (out = 1; out < length; ++out) {
      long temp = a[out];
      in = out;
      int loopCount = 0;
      while (in > 0 && a[in - 1] >= temp) {
        //  a[in] = a[in - 1];
        --in;
        ++comparisonCount;
        ++copyCount;
        ++loopCount;
      }
      System.arraycopy(a, in, a, in + 1, loopCount);
      if (in > 0) ++comparisonCount;
      a[in] = temp;
      ++copyCount;
    }
  }
}
