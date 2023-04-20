package ds;

/***
 * <p>Merge sort in place.</p>
 ***/
public class MergeSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    if (!shouldSort(length)) return;
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

  private void merge(long[] a, int begin, int middle, int end) {
    int mid = middle;
    int start2 = mid + 1;
    // If the direct merge is already sorted
    ++comparisonCount;
    if (a[mid] <= a[start2]) {
      return;
    }
    int start = begin;
    // Two pointers to maintain start
    // of both arrays to merge
    while (start <= mid && start2 <= end) {
      // If element 1 is in right place
      ++outerLoopCount;
      ++comparisonCount;
      if (a[start] <= a[start2]) {
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
