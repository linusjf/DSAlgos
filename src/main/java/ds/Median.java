package ds;

import static ds.ArrayUtils.swap;

import java.util.Random;

public class Median {

  private final Random random;

  private final long[] array;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  public Median(long... arr) {
    this.array = arr;
    this.random = new Random();
  }

  public double find() {
    if (array.length == 0) return Double.NaN;
    if (array.length == 1) return array[0];

    // long pivot = array[array.length - 1];
    // use random pivot
    int pivotIndex = random.nextInt(array.length);
    long pivot = array[pivotIndex];
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
    if (leftPtr < k) {
      int pivotIndex = random.nextInt(right - leftPtr);
      if( array[leftPtr + 1 + pivotIndex] > array[right])
          swap(array,leftPtr + 1 + pivotIndex, right);
      return partitionIt(leftPtr + 1, right, array[right], k);
    } else {
      int pivotIndex = random.nextInt(leftPtr);
      if (array[pivotIndex] > array[leftPtr - 1])
          swap(array,pivotIndex,leftPtr - 1);
      return partitionIt(0, leftPtr - 1, array[leftPtr - 1], k);
    }
  }
}
