package ds;

/***
 * <p>Quick sort using random partitioning.</p>
 ***/
public class MergeSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    if (length < 0) throw new IllegalArgumentException("Invalid length parameter: " + length);
    reset();
    if (length <= 1) return;
    mergeSort(a, 0, length - 1);
  }

  private void mergeSort(long[] a, int l, int r) {
    if (l < r) {
      int m = l + ((r - l) >> 1);
      mergeSort(a, l, m);
      mergeSort(a, m + 1, r);
      merge(a, l, m, r);
    }
  }

  private void merge(long[] a, int start, int mid, int end) {
    int start2 = mid + 1;
    // If the direct merge is already sorted
    if (a[mid] <= a[start2]) {
      ++comparisonCount;
      return;
    }
    // Two pointers to maintain start
    // of both arrays to merge
    while (start <= mid && start2 <= end) {
      // If element 1 is in right place
      ++outerLoopCount;
      if (a[start] <= a[start2]) {
        ++comparisonCount;
        ++start;
      } else {
        long value = a[start2];
        int index = start2;
        // Shift all the elements between element 1
        // element 2, right by 1.
        while (index != start) {
          ++innerLoopCount;
          System.arraycopy(a, index - 1, a, index, 1);
          --index;
          ++copyCount;
        }
        a[start] = value;
        ++copyCount;
        // Update all the pointers
        ++start;
        ++mid;
        ++start2;
      }
    }
  }
}
