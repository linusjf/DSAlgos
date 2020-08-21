package ds;

import static ds.ExecutorUtils.*;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * <p>Merge sort using parallelism.</p>
 ***/
public class MergeSortParallel extends MergeSort {

  private static final int SEQ_SORT_BARRIER = 8192;
  private static final int CUTOFF = 8;
  private final AtomicInteger copyCount = new AtomicInteger();
  private final AtomicInteger comparisonCount = new AtomicInteger();
  private final AtomicInteger innerLoopCount = new AtomicInteger();
  private final AtomicInteger outerLoopCount = new AtomicInteger();

  @Override
  public int getCopyCount() {
    return copyCount.intValue();
  }

  @Override
  public int getComparisonCount() {
    return comparisonCount.intValue();
  }

  @Override
  public int getTimeComplexity() {
    int count = innerLoopCount.intValue();
    return count > 0 ? count : outerLoopCount.intValue();
  }

  @Override
  public void reset() {
    copyCount.set(0);
    innerLoopCount.set(0);
    outerLoopCount.set(0);
    comparisonCount.set(0);
  }

  @Override
  protected void sort(long[] a, int length) {
    if (length < 0) throw new IllegalArgumentException("Invalid length parameter: " + length);
    reset();
    if (length <= 1) return;
    if (length <= SEQ_SORT_BARRIER) {
      super.sort(a, length);
      return;
    }
    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(new MergeSortAction(a, 0, length - 1));
    terminateExecutor(pool, length, TimeUnit.MILLISECONDS);
  }

  private void merge(long[] a, int start, int mid, int end) {
    int start2 = mid + 1;
    // If the direct merge is already sorted
    if (a[mid] <= a[start2]) {
      comparisonCount.incrementAndGet();
      return;
    }
    // Two pointers to maintain start
    // of both arrays to merge
    while (start <= mid && start2 <= end) {
      // If element 1 is in right place
      outerLoopCount.incrementAndGet();
      if (a[start] <= a[start2]) {
        comparisonCount.incrementAndGet();
        ++start;
      } else {
        long value = a[start2];
        int index = start2;
        // Shift all the elements between element 1
        // element 2, right by 1.
        while (index != start) {
          innerLoopCount.incrementAndGet();
          System.arraycopy(a, index - 1, a, index, 1);
          --index;
          copyCount.incrementAndGet();
        }
        a[start] = value;
        copyCount.incrementAndGet();
        // Update all the pointers
        ++start;
        ++mid;
        ++start2;
      }
    }
  }

  class MergeSortAction extends RecursiveAction {
    private static final long serialVersionUID = 1L;

    final long[] a;
    int low;
    int high;

    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    MergeSortAction(long[] a, int low, int high) {
      this.a = a;
      this.low = low;
      this.high = high;
    }

    @Override
    protected void compute() {
      if (low < high) {
        int size = high - low + 1;
        if (size <= CUTOFF) {
          Arrays.sort(a, low, low + size);
          return;
        }
        int m = low + ((high - low) >> 1);
        invokeAll(new MergeSortAction(a, low, m));
        invokeAll(new MergeSortAction(a, m + 1, high));
        merge(a, low, m, high);
      }
    }
  }

  static class SeqMergeSort extends MergeSort {
    public void sort(long[] a, int length) {
      super.sort(a, length);
    }
  }
}
