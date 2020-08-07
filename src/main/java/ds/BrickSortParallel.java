package ds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BrickSortParallel extends AbstractSort implements ISortInterruptible {
  private static final int NO_OF_PROCESSORS = Runtime.getRuntime().availableProcessors();

  protected ExecutorService service = Executors.newFixedThreadPool(NO_OF_PROCESSORS);
  protected List<Future<Void>> futures = new ArrayList<>();
  private AtomicBoolean isSorted = new AtomicBoolean();
  private AtomicInteger swapCount = new AtomicInteger();

  private void reset() {
    resetCounts();
    isSorted.getAndSet(false);
    swapCount.set(0);
  }

  @Override
  public IArray sort(IArray arr) {
    try {
      return sortInterruptibly(arr);
    } catch (InterruptedException ie) {
      System.err.println(ie.getMessage());
      throw new CompletionException(ie);
    } catch (ExecutionException ee) {
      System.err.println(ee.getMessage());
      throw new CompletionException(ee);
    }
  }

  @Override
  public IArray sortInterruptibly(IArray arr) throws InterruptedException, ExecutionException {
    IArray copy = arr.copy();
    sortInterruptibly(copy.get(), copy.count());
    return copy;
  }

  @Override
  protected void sort(long[] a, int length) {
    try {
      sortInterruptibly(a, length);
    } catch (ExecutionException ee) {
      System.err.println(ee.getMessage());
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
  }

  protected void sortInterruptibly(long[] a, int length)
      throws InterruptedException, ExecutionException {
    reset();
    final int maxComparisons =
        (length & 1) == 1 ? length * ((length - 1) >>> 1) : (length >>> 1) * (length - 1);
    while (!isSorted.get()) {
      ++outerLoopCount;
      isSorted.set(true);
      oddSort(a, length);
      if (swapCount.intValue() == maxComparisons) {
        isSorted.set(true);
        break;
      }
      evenSort(a, length);
      if (swapCount.intValue() == maxComparisons) isSorted.set(true);
    }
  }

  protected void oddSort(long[] a, int length) throws InterruptedException, ExecutionException {
    futures = new ArrayList<>(length - 2);
    for (int i = 1; i < length - 1; i += 2) {
      ++innerLoopCount;
      ++comparisonCount;
      futures.add(service.submit(new Task(this, a, i)));
    }
    for (int i = 0; i < futures.size(); i++) futures.get(i).get();
  }

  protected void evenSort(long[] a, int length) throws InterruptedException, ExecutionException {
    futures = new ArrayList<>(length - 1);
    for (int i = 0; i < length - 1; i += 2) {
      ++innerLoopCount;
      ++comparisonCount;
      futures.add(service.submit(new Task(this, a, i)));
    }
    for (int i = 0; i < futures.size(); i++) futures.get(i).get();
  }

  protected void bubble(long[] a, int i) {
    if (a[i] > a[i + 1]) {
      swap(a, i, i + 1);
      isSorted.set(false);
      swapCount.incrementAndGet();
    }
  }

  @Override
  public int getSwapCount() {
    return swapCount.intValue();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String lineSeparator = System.lineSeparator();
    sb.append(getClass().getName())
        .append(lineSeparator)
        .append("Comparison count: ")
        .append(comparisonCount)
        .append(lineSeparator)
        .append("Swap count: ")
        .append(swapCount)
        .append(lineSeparator)
        .append("Copy count: ")
        .append(copyCount)
        .append(lineSeparator)
        .append("inner loop count: ")
        .append(innerLoopCount)
        .append(lineSeparator)
        .append("outer loop count: ")
        .append(outerLoopCount)
        .append(lineSeparator)
        .append("isSorted: ")
        .append(isSorted)
        .append(lineSeparator)
        .append("ExecutorService: ")
        .append(service);
    return sb.toString();
  }

  static class Task implements Callable<Void> {
    long[] a;
    int i;
    BrickSortParallel sorter;

    Task(BrickSortParallel sorter, long[] a, int i) {
      this.a = a;
      this.i = i;
      this.sorter = sorter;
    }

    public Void call() throws InterruptedException {
      sorter.bubble(a, i);
      return null;
    }
  }
}
