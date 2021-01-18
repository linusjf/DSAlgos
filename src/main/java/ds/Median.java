package ds;

import static ds.ArrayUtils.swap;

public class Median {

  private final long[] array;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  public Median(long... arr) {
    this.array = arr;
  }

  public double find() {
    if (array.length == 0) return Double.NaN;
    if (array.length == 1) return array[0];
    long pivot = array[array.length - 1];
    if (array.length % 2 == 1) {
      int medianIndex = partitionIt(0, array.length - 1, pivot, array.length >> 1);
      return array[medianIndex];
    } else {
      int medIndex1 = partitionIt(0, array.length - 1, pivot, (array.length - 1) >> 1);
      int medIndex2 = partitionIt(0, array.length - 1, pivot, array.length >> 1);
      return 0.5 * (array[medIndex1] + array[medIndex2]);
    }
  }

  private int partitionIt(int left, int right, long pivot, int k) {
    int leftPtr = left - 1;
    int rightPtr = right + 1;
    while (true) {
      while (leftPtr < right && array[++leftPtr] < pivot)
        ;
      while (rightPtr > left && array[--rightPtr] > pivot)
        ;
      if (leftPtr >= rightPtr) break;
      else swap(array, leftPtr, rightPtr);
    }
    if (leftPtr == k) return leftPtr;
    else if (leftPtr < k) return partitionIt(leftPtr + 1, right, array[right], k);
    else return partitionIt(0, leftPtr - 1, array[leftPtr - 1], k);
  }
}
