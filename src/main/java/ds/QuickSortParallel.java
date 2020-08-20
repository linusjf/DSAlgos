package ds;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class QuickSortParallel extends AbstractSort {
  private ForkJoinPool pool = new ForkJoinPool();
  private final AtomicInteger swapCount = new AtomicInteger();
  private final AtomicInteger comparisonCount = new AtomicInteger();
  private final AtomicInteger innerLoopCount = new AtomicInteger();
  private final AtomicInteger outerLoopCount = new AtomicInteger();

  @Override
  public int getSwapCount() {
    return swapCount.intValue();
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
    pool = new ForkJoinPool();
    swapCount.set(0);
    innerLoopCount.set(0);
    outerLoopCount.set(0);
    comparisonCount.set(0);
  }

  @Override
  protected void sort(long[] a, int length) {
    if (length < 0) throw new IllegalArgumentException("Invalid length parameter: " + length);
    reset();
    if (length <= 1) return;
    pool.invoke(new QuickSortAction(a, 0, length - 1));
    ExecutorUtils.terminateExecutor(pool, length, TimeUnit.MILLISECONDS);
  }

  public static boolean less(long v, long w) {
    return v < w;
  }

  // return the index of the median element among a[i], a[j], and a[k]
  public static int median3(long[] a, int i, int j, int k) {
    return less(a[i], a[j])
        ? less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i
        : less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i;
  }

  class QuickSortAction extends RecursiveAction {
    private static final long serialVersionUID = 1L;

    final long[] a;
    int low;
    int high;

    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    QuickSortAction(long[] a, int low, int high) {
      this.a = a;
      this.low = low;
      this.high = high;
    }

    @Override
    protected void compute() {
      while (low < high) {

        int n = high - low + 1;

        int m = median3(a, low, low + n / 2, high);
        if (m != low && a[low] > a[m]) {
          swap(a, m, low);
          swapCount.incrementAndGet();
        }
        int pivotIndex = partition(a, low, high);

        if ((pivotIndex - low) <= (high - pivotIndex)) {
          invokeAll(new QuickSortAction(a, low, pivotIndex - 1));
          low = pivotIndex + 1;
        } else {
          invokeAll(new QuickSortAction(a, pivotIndex + 1, high));
          high = pivotIndex - 1;
        }
      }
    }

    private int partition(long[] a, int lo, int hi) {
      int i = lo;
      int j = hi + 1;
      long v = a[lo];
      while (true) {
        outerLoopCount.incrementAndGet();
        // find item on lo to swap
        while (less(a[++i], v)) {
          innerLoopCount.incrementAndGet();
          comparisonCount.incrementAndGet();
          if (i == hi) break;
        }
        comparisonCount.incrementAndGet();
        // find item on hi to swap
        while (less(v, a[--j])) {
          innerLoopCount.incrementAndGet();
          comparisonCount.incrementAndGet();
          if (j == lo) break;
          // redundant since a[lo] acts as sentinel
        }
        comparisonCount.incrementAndGet();
        // check if pointers cross
        if (i >= j || a[i] == a[j]) break;
        swap(a, i, j);
        swapCount.incrementAndGet();
      }
      // put v = a[j] into position
      if (lo != j && a[lo] != a[j]) {
        swap(a, lo, j);
        swapCount.incrementAndGet();
      }
      // with a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
      return j;
    }
  }
}
