package ds;

import static ds.ArrayUtils.swapIfGreaterThan;
import static ds.AssertionUtils.*;
import static ds.ExecutorUtils.terminateExecutor;
import static ds.MathUtils.isOdd;
import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/** Not thread-safe with state variables. */
public class BrickSortUnrolled extends BrickSort {

  private static final int PROC_COUNT = Runtime.getRuntime().availableProcessors() - 1;
  private static final int THRESHOLD = 40;
  protected final AtomicInteger innerLoopCount = new AtomicInteger();
  protected final AtomicBoolean sorted = new AtomicBoolean();
  protected final AtomicInteger swapCount = new AtomicInteger();
  protected final AtomicInteger comparisonCount = new AtomicInteger();
  private int partitionSize;
  private int partitionCount;
  private int length;

  @Override
  protected void reset() {
    super.reset();
    sorted.getAndSet(false);
    swapCount.set(0);
    comparisonCount.set(0);
    innerLoopCount.set(0);
  }

  private void sequentialSort(long[] a, int length) {
    super.sort(a, length);
    sorted.getAndSet(super.sorted);
    swapCount.set(super.swapCount);
    comparisonCount.set(super.comparisonCount);
    innerLoopCount.set(super.innerLoopCount);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  protected void sort(long[] a, int length) {
    this.length = length;
    if (!shouldSort(length)) {
      sorted.getAndSet(true);
      return;
    }
    if (length <= THRESHOLD) {
      sequentialSort(a, length);
      return;
    }
    ExecutorService service = Executors.newSingleThreadExecutor();
    try {
      sortInterruptibly(a, length, service);
    } catch (ExecutionException | InterruptedException ee) {
      throw new CompletionException(ee);
    } finally {
      terminateExecutor(service, length, TimeUnit.MILLISECONDS);
    }
    assertServiceTerminated(service);
  }

  protected void sortInterruptibly(long[] a, int length, ExecutorService service)
      throws InterruptedException, ExecutionException {
    final int maxComparisons = computeMaxComparisons(length);
    final int oddTaskCount = computeOddTaskCount(length);
    final int evenTaskCount = computeEvenTaskCount(length);
    partitionSize = length / PROC_COUNT;
    if (isOdd(partitionSize)) partitionSize = partitionSize + 1;
    if (partitionSize * PROC_COUNT > length) partitionCount = PROC_COUNT + 1;
    else partitionCount = PROC_COUNT;
    while (!sorted.get()) {
      ++outerLoopCount;
      sorted.set(true);
      oddSort(a, length, service, oddTaskCount);
      if (swapCount.intValue() == maxComparisons) {
        sorted.set(true);
        break;
      }
      evenSort(a, length, service, evenTaskCount);
      if (swapCount.intValue() == maxComparisons) sorted.set(true);
    }
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "PMD.SystemPrintln"})
  protected void oddSort(
      long[] a, int unusedLength, ExecutorService service, int unusedOddTaskCount)
      throws InterruptedException, ExecutionException {
    List<Future<Void>> futures = new ArrayList<>(partitionCount);
    BubbleTask bt = new BubbleTask(this, a, 0);
    int newLength = partitionSize * partitionCount;
    for (int i = 1; i < newLength - 1; i += partitionSize) {
      BubbleTask task = BubbleTask.createCopy(bt);
      task.i = i;
      futures.add(service.submit(task));
    }
    assertEquality(futures.size(), partitionCount);
    for (Future future : futures) future.get();
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "PMD.SystemPrintln"})
  protected void evenSort(
      long[] a, int unusedLength, ExecutorService service, int unusedEvenTaskCount)
      throws InterruptedException, ExecutionException {
    List<Future<Void>> futures = new ArrayList<>(partitionCount);
    BubbleTask bt = new BubbleTask(this, a, 0);
    int newLength = partitionSize * partitionCount;
    for (int i = 0; i < newLength - 1; i += partitionSize) {
      BubbleTask task = BubbleTask.createCopy(bt);
      task.i = i;
      futures.add(service.submit(task));
    }
    assertEquality(futures.size(), partitionCount);
    for (Future future : futures) future.get();
  }

  @Override
  protected void bubble(long[] a, int i) {
    for (int j = i; j < i + partitionSize - 1 && j < length - 1; j += 2) {
      innerLoopCount.incrementAndGet();
      comparisonCount.incrementAndGet();
      if (swapIfGreaterThan(a, j, j + 1)) {
        swapCount.incrementAndGet();
        sorted.set(false);
      }
    }
  }

  @Override
  public int getSwapCount() {
    return swapCount.intValue();
  }

  @Override
  public int getTimeComplexity() {
    return innerLoopCount.intValue();
  }

  @Override
  public int getComparisonCount() {
    return comparisonCount.intValue();
  }

  @Override
  public boolean isSorted() {
    return sorted.get();
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(90);
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
        .append(lineSeparator);
    return sb.toString();
  }

  public static int computeOddTaskCount(int length) {
    if (length < 0) throw new IllegalArgumentException("Illegal argument value: " + length);
    return isOdd(length) ? length >> 1 : abs(length - 1) >> 1;
  }

  public static int computeEvenTaskCount(int length) {
    if (length < 0) throw new IllegalArgumentException("Illegal argument value: " + length);
    return length >> 1;
  }
}
