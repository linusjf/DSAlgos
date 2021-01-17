package ds;

import static ds.ArrayUtils.swap;

public class Median {

  private static final int FOUR = 4;
  private static final int FIVE = 5;
  private static final int TEN = 10;

  private final long[] array;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  public Median(long... arr) {
    this.array = arr;
  }

  public double find() {
    if (array.length == 0) return Double.NaN;
    if (array.length == 1) return array[0];
    if (array.length % 2 == 1) return array[pivot(0, array.length - 1)];
    else return 0.5 * (array[pivot(0, array.length - 1)] + array[pivot(0, array.length - 2)]);
  }

  int partition5(int left, int right) {
    int i = left + 1;
    while (i <= right) {
      int j = i;
      while (j > left && array[j - 1] > array[j]) {
        swap(array, j - 1, j);
        --j;
        ++i;
      }
    }
    return (left + right) / 2;
  }

  int select(int l, int r, int n) {
    int left = l;
    int right = r;
    while (true) {
      if (left == right) return left;
      int pivotIndex = pivot(left, right);
      pivotIndex = partition(left, right, pivotIndex, n);
      if (n == pivotIndex) return n;
      else if (n < pivotIndex) right = pivotIndex - 1;
      else left = pivotIndex + 1;
    }
  }

  int partition(int left, int right, int pivotIndex, int n) {
    long pivotValue = array[pivotIndex];
    swap(array, pivotIndex, right);
    int storeIndex = left;
    // Move all elements smaller than the pivot to the left of the pivot

    for (int i = left; i < right; i++) {
      if (array[i] < pivotValue) {
        swap(array, storeIndex, i);
        ++storeIndex;
      }
    }
    // Move all elements equal to the pivot right after
    // the smaller elements
    int storeIndexEq = storeIndex;
    for (int i = storeIndex; i < right - 1; i++) {
      if (array[i] == pivotValue) swap(array, storeIndexEq, i);
      ++storeIndexEq;
    }
    swap(array, right, storeIndexEq);
    // Move pivot to its final place
    // Return location of pivot considering the desired location n
    if (n < storeIndex) return storeIndex;
    // n is in the group of smaller elements
    if (n <= storeIndexEq) return n;
    // n is in the group equal to pivot
    return storeIndexEq;
    // n is in the group of larger elements
  }

  int pivot(int left, int right) {
    // for 5 or less elements just get median
    if (right - left < FIVE) return partition5(left, right);
    // otherwise move the medians of five-element subgroups to the first n/5 positions
    for (int i = left; i <= right; i += FIVE) {
      // get the median position of the i'th five-element subgroup
      int subRight = i + FOUR;
      if (subRight > right) subRight = right;
      int median5 = partition5(i, subRight);
      swap(array, median5, left + (i - left) / FIVE);
    }
    // compute the median of the n/5 medians-of-five
    int mid = (right - left) / TEN + left + 1;
    return select(left, left + (right - left) / FIVE, mid);
  }
}
