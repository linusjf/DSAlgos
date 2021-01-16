package ds;

import static ds.ArrayUtils.swap;

public class Median {

  private long[] array;

  public Median(long[] arr) {
    this.array = arr;
  }

  public double find() {
    return 0f;
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
    return (int) Math.floor((left + right) / 2);
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
}
