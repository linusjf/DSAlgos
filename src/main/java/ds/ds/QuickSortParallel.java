package ds;

import static ds.ExecutorUtils.terminateExecutor;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class QuickSortParallel extends AbstractSort {
  private static final int CUTOFF = 8;
  private static final int MEDIUM = 40;
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
    swapCount.set(0);
    innerLoopCount.set(0);
    outerLoopCount.set(0);
    comparisonCount.set(0);
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
        .append("Swap count: ")
        .append(swapCount)
        .append(lineSeparator)
        .append("inner loop count: ")
        .append(innerLoopCount)
        .append(lineSeparator)
        .append("outer loop count: ")
        .append(outerLoopCount)
        .append(lineSeparator);
    return sb.toString();
  }

  @Override
  protected void sort(long[] a, int length) {
    if (!shouldSort(length)) return;
    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(new QuickSortAction(a, 0, length - 1));
    terminateExecutor(pool, length, TimeUnit.MILLISECONDS);
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

  final class QuickSortAction extends RecursiveAction implements Cloneable {
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

    @Generated
    @SuppressWarnings("checkstyle:NoClone")
    @Override
    public QuickSortAction clone() {
      try {
        return (QuickSortAction) super.clone();
      } catch (CloneNotSupportedException cnse) {
        throw new AssertionError("Shouldn't get here..." + cnse.getMessage(), cnse);
      }
    }

    @Override
    protected void compute() {
      while (low < high) {

        int n = high - low + 1;

        if (n <= CUTOFF) {
          Arrays.sort(a, low, low + n);
          return;
        }

        int s = n >> 3;
        int middle = n >> 1;
        int m =
            n > MEDIUM
                ? median3(
                    a,
                    median3(a, low, low + s, low + (s << 1)),
                    median3(a, middle - s, middle, middle + s),
                    median3(a, high - (s << 1), high - s, high))
                : median3(a, low, low + middle, high);

        if (m != low && a[low] > a[m]) {
          swap(a, m, low);
          swapCount.incrementAndGet();
        }
        int pivotIndex = partition(a, low, high);

        if ((pivotIndex - low) <= (high - pivotIndex)) {
          QuickSortAction copy = this.clone();
          copy.low = low;
          copy.high = pivotIndex + 1;
          invokeAll(copy);
          low = pivotIndex + 1;
        } else {
          QuickSortAction copy = this.clone();
          copy.low = pivotIndex + 1;
          copy.high = high;
          invokeAll(copy);
          high = pivotIndex - 1;
        }
      }
    }

    private int partition(long[] array, int lo, int hi) {
      int i = lo;
      int j = hi + 1;
      long v = array[lo];
      while (true) {
        outerLoopCount.incrementAndGet();
        // find item on lo to swap
        while (less(array[++i], v)) {
          innerLoopCount.incrementAndGet();
          comparisonCount.incrementAndGet();
        }
        comparisonCount.incrementAndGet();
        // find item on hi to swap
        while (less(v, array[--j])) {
          innerLoopCount.incrementAndGet();
          comparisonCount.incrementAndGet();
        }
        comparisonCount.incrementAndGet();
        // check if pointers cross
        if (i >= j || array[i] == array[j]) break;
        swap(array, i, j);
        swapCount.incrementAndGet();
      }
      if (lo != j && array[lo] != array[j]) {
        swap(array, lo, j);
        swapCount.incrementAndGet();
      }
      return j;
    }
  }
}
