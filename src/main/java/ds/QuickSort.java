package ds;

import java.util.Random;

public class QuickSort extends AbstractSort {

  private static int randomInRange(int low, int high) {
    Random rand = new Random();
    return rand.nextInt(high - low) + low;
  }

  @Override
  protected void sort(long[] a, int length) {
    quickSort(a, 0, length);
  }

  private int randomPartition(long[] a, int low, int high) {
    int random = randomInRange(low, high);
    swap(a, random, high);
    return partition(a, low, high);
  }

  /* This function takes last element as pivot, places
  the pivot element at its correct position in sorted
  array, and places all smaller (smaller than pivot)
  to left of pivot and all greater elements to right
  of pivot */
  int partition(long[] a, int low, int high) {
    long pivot = a[high];

    int i = low - 1;
    // Index of smaller element
    for (int j = low; j <= high - 1; ++j) {
      // If current element is smaller than or
      // equal to pivot
      ++innerLoopCount;
      ++comparisonCount;
      if (a[j] <= pivot) {
        ++i;
        swap(a, i, j);
        ++swapCount;
      }
    }
    swap(a, i + 1, high);
    ++swapCount;
    return i + 1;
  }

  /* This QuickSort requires O(Log n) auxiliary space in
  worst case. */
  void quickSort(long[] a, int low, int high) {
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
