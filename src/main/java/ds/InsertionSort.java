package ds;

public class InsertionSort extends AbstractSort {

  protected void sort(long[] a, int length) {
    int in;
    int out;

    for (out = 1; out < length; out++) {
      long temp = a[out];
      in = out;
      while (in > 0 && a[in - 1] >= temp) {
        a[in] = a[in - 1];
        --in;
      }
      a[in] = temp;
    }
  }
}
