package ds;

import static java.lang.System.arraycopy;
import static java.util.Arrays.fill;

public class RadixSort {

  private static final int TEN = 10;

  int[] a;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  public RadixSort(int... array) {
    this.a = array;
  }

  public void sort() {
    int max = getMax();

    for (int exp = 1; max / exp > 0; exp *= TEN) countSort(exp);
  }

  int getMax() {
    int max = a[0];
    for (int i = 1; i < a.length; i++) if (a[i] > max) max = a[i];
    return max;
  }

  void countSort(int exp) {

    int[] output = new int[a.length];

    int[] count = new int[TEN];

    fill(count, 0);

    for (int val : a) count[(val / exp) % TEN]++;

    for (int i = 1; i < TEN; i++) count[i] += count[i - 1];

    for (int i = a.length - 1; i >= 0; i--) {
      output[count[(a[i] / exp) % TEN] - 1] = a[i];

      count[(a[i] / exp) % TEN]--;
    }

    arraycopy(output, 0, a, 0, a.length);
  }
}
