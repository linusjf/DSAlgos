package ds;

public class BubbleSort extends AbstractSort {

  protected void sort(long[] a, int length) {
    int out;
    int in;
    for (out = length - 1; out > 1; out--)
      for (in = 0; in < out; in++) if (a[in] > a[in + 1]) swap(a, in, in + 1);
  }
}
