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
      System.out.println("medianIndex = " + medianIndex);
      return array[medianIndex];
    } else {
      int medIndex1 = partitionIt(0, array.length - 1, pivot, (array.length - 1) >> 1);
      int medIndex2 = partitionIt(0, array.length - 1, pivot, array.length >> 1);
      System.out.println("medIndex1 = " + medIndex1);
      System.out.println("medIndex2 = " + medIndex2);
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
      long newPivot = array[leftPtr + 1 + pivotIndex];
      return partitionIt(leftPtr + 1, right, newPivot, k);
    } else {
      int pivotIndex = random.nextInt(leftPtr);
      long newPivot = array[pivotIndex];
      return partitionIt(0, leftPtr - 1, newPivot, k);
    }
  }
}
