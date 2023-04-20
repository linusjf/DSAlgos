package ds;

/** Heap sort. */
public class HeapSort extends AbstractSort {

  @Override
  protected void sort(long[] a, int length) {
    if (!shouldSort(length)) return;
    heapSort(a, length);
  }

  void heapSort(long[] a, int length) {
    // Build heap (rearrange array)
    for (int i = (length >> 1) - 1; i >= 0; --i) {
      ++outerLoopCount;
      heapify(a, length, i);
    }

    // One by one extract an element from heap
    for (int i = length - 1; i > 0; --i) {
      ++outerLoopCount;
      // Move current root to end
      ++comparisonCount;
      if (a[i] != a[0]) {
        swap(a, i, 0);
        ++swapCount;
      }
      // call max heapify on the reduced heap
      heapify(a, i, 0);
    }
  }

  // To heapify a subtree rooted with node i which is
  // an index in a[]. n is size of heap
  void heapify(long[] a, int n, int i) {
    int largest = i;
    // Initialize largest as root
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    // If left child is larger than root
    if (left < n && a[left] > a[largest]) {
      largest = left;
      ++comparisonCount;
    }

    // If right child is larger than largest so far
    if (right < n && a[right] > a[largest]) {
      largest = right;
      ++comparisonCount;
    }

    // If largest is not root
    if (largest != i) {
      swap(a, i, largest);
      ++swapCount;
      ++comparisonCount;
      // Recursively heapify the affected sub-tree
      heapify(a, n, largest);
    }
  }
}
