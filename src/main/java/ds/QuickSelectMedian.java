package ds;

import static ds.ArrayUtils.swap;
import static ds.MathUtils.isOdd;
import static ds.RandomUtils.randomInRange;

public class QuickSelectMedian {

  private final long[] array;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  public QuickSelectMedian(long... arr) {
    this.array = arr;
  }

  public double find() {
    if (array.length == 0) return Double.NaN;
    if (array.length == 1) return array[0];
    if (isOdd(array.length)) return select(0, array.length - 1, array.length >> 1);
    return 0.5f
        * (select(0, array.length - 1, array.length >> 1)
            + select(0, array.length - 1, (array.length >> 1) - 1));
  }

  int partition(int left, int right, int pivotIndex) {
    long pivotValue = array[pivotIndex];
    // Move pivot to end
    swap(array, pivotIndex, right);
    int storeIndex = left;
    for (int i = left; i < right; i++) {
      if (array[i] < pivotValue) {
        swap(array, storeIndex, i);
        ++storeIndex;
      }
    }
    swap(array, right, storeIndex);
    return storeIndex;
  }

  long select(int left, int right, int k) {
    if (left == right) return array[left];
    int pivotIndex = randomInRange(left, right);
    pivotIndex = partition(left, right, pivotIndex);

    if (k == pivotIndex) return array[k];
    else if (k < pivotIndex) return select(left, pivotIndex - 1, k);
    else return select(pivotIndex + 1, right, k);
  }
}
