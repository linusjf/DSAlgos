package ds;

import static ds.ExecutorUtils.*;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * <p>Merge sort using parallelism in-place.</p>
 ***/
public class MergeSortParallel extends MergeSort {

  private static final int SEQ_SORT_BARRIER = 8192;
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
    if (!shouldSort(length)) return;
    if (length <= SEQ_SORT_BARRIER) {
      super.sort(a, length);
      sequentialSort(a, length);
      return;
    }
    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(new MergeSortAction(a, 0, length - 1));
    terminateExecutor(pool, length, TimeUnit.MILLISECONDS);
  }

  private void sequentialSort(long[] a, int length) {
    super.sort(a, length);
    copyCount.set(super.copyCount);
    innerLoopCount.set(super.innerLoopCount);
    outerLoopCount.set(super.outerLoopCount);
    comparisonCount.set(super.comparisonCount);
  }

  private void merge(long[] a, int begin, int middle, int end) {
    int mid = middle;
    int start2 = mid + 1;
    // If the direct merge is already sorted
    comparisonCount.incrementAndGet();
    if (a[mid] <= a[start2]) {
      return;
    }
    int start = begin;
    // Two pointers to maintain start
    // of both arrays to merge
    while (start <= mid && start2 <= end) {
      // If element 1 is in right place
      outerLoopCount.incrementAndGet();
      comparisonCount.incrementAndGet();
      if (a[start] <= a[start2]) {
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

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(66);
    String lineSeparator = System.lineSeparator();
    sb.append(getClass().getName())
        .append(lineSeparator)
        .append("Comparison count: ")
        .append(comparisonCount)
        .append(lineSeparator)
        .append("Copy count: ")
        .append(copyCount)
        .append(lineSeparator)
        .append("inner loop count: ")
        .append(innerLoopCount)
        .append(lineSeparator)
        .append("outer loop count: ")
        .append(outerLoopCount)
        .append(lineSeparator);
    return sb.toString();
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
        int m = low + ((high - low) >> 1);
        invokeAll(new MergeSortAction(a, low, m));
        invokeAll(new MergeSortAction(a, m + 1, high));
        merge(a, low, m, high);
      }
    }
  }
}
