package ds;

import static ds.ArrayUtils.swap;
import static ds.RandomUtils.randomInRange;
import static java.util.Objects.checkIndex;

public class QuickSelect {

  protected final long[] array;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  public QuickSelect(long... arr) {
    this.array = arr;
  }

  public long find(int index) {
    checkIndex(index, array.length);
    return select(0, array.length - 1, index);
  }

  private int partition(int left, int right, int pivotIndex) {
    long pivotValue = array[pivotIndex];
    // Move pivot to end
    swap(array, pivotIndex, right);
    int storeIndex = left;
    for (int i = left; i < right; i++) {
      if (array[i] < pivotValue) {
        swap(array, storeIndex, i);
        ++storeIndex;
      }
    }
    swap(array, right, storeIndex);
    return storeIndex;
  }

  private long select(int left, int right, int k) {
    if (left == right) return array[left];
    int pivotIndex = randomInRange(left, right);
    pivotIndex = partition(left, right, pivotIndex);

    if (k == pivotIndex) return array[k];
    else if (k < pivotIndex) return select(left, pivotIndex - 1, k);
    else return select(pivotIndex + 1, right, k);
  }
}
