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

public class BrickSortParallel extends AbstractSort {
  private static final int NO_OF_PROCESSORS = Runtime.getRuntime().availableProcessors();

  protected ExecutorService service = Executors.newFixedThreadPool(NO_OF_PROCESSORS);
  private final AtomicBoolean sorted = new AtomicBoolean();
  private final AtomicInteger swapCount = new AtomicInteger();

  protected void reset() {
    resetCounts();
    sorted.getAndSet(false);
    swapCount.set(0);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public IArray sort(IArray arr) {
    IArray copy = arr.copy();
    sort(copy.get(), copy.count());
    return copy;
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  protected void sort(long[] a, int length) {
    try {
      sortInterruptibly(a, length);
    } catch (ExecutionException ee) {
      throw new CompletionException(ee);
    } catch (InterruptedException ie) {
      //      Thread.currentThread().interrupt();
      throw new CompletionException(ie);
    }
    assert sorted.get();
  }

  protected void sortInterruptibly(long[] a, int length)
      throws InterruptedException, ExecutionException {
    reset();
    if (length <= 1) {
      sorted.set(true);
      return;
    }

    final int maxComparisons =
        (length & 1) == 1 ? length * ((length - 1) >>> 1) : (length >>> 1) * (length - 1);
    while (!sorted.get()) {
      ++outerLoopCount;
      sorted.set(true);
      oddSort(a, length);
      if (swapCount.intValue() == maxComparisons) {
        sorted.set(true);
        break;
      }
      evenSort(a, length);
      if (swapCount.intValue() == maxComparisons) sorted.set(true);
    }
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  protected void oddSort(long[] a, int length) throws InterruptedException, ExecutionException {
    int capacity = (length & 1) == 1 ? length >>> 1 : (length - 1) >>> 1;
    List<Future<Void>> futures = new ArrayList<>(capacity);
    for (int i = 1; i < length - 1; i += 2) {
      ++innerLoopCount;
      ++comparisonCount;
      futures.add(service.submit(new BubbleTask(this, a, i)));
    }
    assert futures.size() == capacity;
    for (Future future : futures) future.get();
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  protected void evenSort(long[] a, int length) throws InterruptedException, ExecutionException {
    int capacity = length >>> 1;
    List<Future<Void>> futures = new ArrayList<>(capacity);
    for (int i = 0; i < length - 1; i += 2) {
      ++innerLoopCount;
      ++comparisonCount;
      futures.add(service.submit(new BubbleTask(this, a, i)));
    }
    assert futures.size() == capacity;
    for (Future future : futures) future.get();
  }

  protected void bubble(long[] a, int i) {
    if (a[i] > a[i + 1]) {
      swap(a, i, i + 1);
      sorted.set(false);
      swapCount.incrementAndGet();
    }
  }

  @Override
  public int getSwapCount() {
    return swapCount.intValue();
  }

  public boolean isSorted() {
    return sorted.get();
  }

  @SuppressWarnings("PMD.LawOfDemeter")
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
        .append("sorted: ")
        .append(sorted)
        .append(lineSeparator)
        .append("ExecutorService: ")
        .append(service);
    return sb.toString();
  }

  static final class BubbleTask implements Callable<Void> {
    long[] a;
    int i;
    BrickSortParallel sorter;

    private BubbleTask(BrickSortParallel sorter, long[] a, int i) {
      this.a = a;
      this.i = i;
      this.sorter = sorter;
    }

    @Override
    public Void call() throws InterruptedException {
      sorter.bubble(a, i);
      return null;
    }
  }
}
