package ds;

public class SelectionSort extends AbstractSort {

  protected void sort(long[] a, int length) {
    int out;
    int in;
    int min;
    for (out = 0; out < length - 1; out++) {
      min = out;
      for (in = out + 1; in < length; in++) if (a[in] < a[min]) min = in;
      swap(a, out, min);
    }
  }
}
