package ds;

import static ds.RandomUtils.randomInRange;

/** Quick sort using random partitioning. */
public class QuickSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    if (!shouldSort(length)) return;
    quickSort(a, 0, length - 1);
  }

  private int randomPartition(long[] a, int low, int high) {
    int random = randomInRange(low, high + 1);
    if (random != high && a[random] > a[high]) {
      swap(a, random, high);
      ++swapCount;
    }
    if (random != high) ++comparisonCount;
    return partition(a, low, high);
  }

  /*
   * This function takes last element as pivot, places
  the pivot element at its correct position in sorted
  array, and places all smaller (smaller than pivot)
  to left of pivot and all greater elements to right
  of pivot
  */
  int partition(long[] a, int low, int high) {
    long pivot = a[high];

    int i = low - 1;
    // Index of smaller element
    for (int j = low; j < high; ++j) {
      // If current element is smaller than or
      // equal to pivot
      ++innerLoopCount;
      ++comparisonCount;
      if (a[j] <= pivot) {
        ++i;
        if (i != j) {
          swap(a, i, j);
          ++swapCount;
        }
      }
    }
    ++i;
    if (i != high) {
      swap(a, i, high);
      ++swapCount;
    }
    return i;
  }

  /*
   * This QuickSort requires O(Log n) auxiliary space in worst case.
   */
  void quickSort(long[] a, int lower, int upper) {
    int low = lower;
    int high = upper;
    while (low < high) {
      ++outerLoopCount;
      /* pi is partitioning index, a[p] is now
      at right place */
      int pi = randomPartition(a, low, high);
      // If left part is smaller, then recur for left
      // part and handle right part iteratively
      if (pi - low < high - pi) {
        quickSort(a, low, pi - 1);
        low = pi + 1;
      } else {
        quickSort(a, pi + 1, high);
        high = pi - 1;
      }
    }
  }
}
