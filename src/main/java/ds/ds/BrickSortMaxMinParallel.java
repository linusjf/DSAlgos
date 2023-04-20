package ds;

import static ds.ArrayUtils.swapIfGreaterThan;
import static ds.AssertionUtils.*;
import static ds.ExecutorUtils.terminateExecutor;
import static ds.MathUtils.*;

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

@Generated
/** Not thread-safe with state variables. */
public class BrickSortMaxMinParallel extends BrickSort {

  private static final int THRESHOLD = 40;
  private final AtomicBoolean sorted = new AtomicBoolean();
  private final AtomicInteger swapCount = new AtomicInteger();

  @Override
  protected void reset() {
    super.reset();
    sorted.getAndSet(false);
    swapCount.set(0);
  }

  private void sequentialSort(long[] a, int length) {
    super.sort(a, length);
    sorted.getAndSet(super.sorted);
    swapCount.set(super.swapCount);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  protected void sort(long[] a, int length) {
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
    while (!sorted.get()) {
      ++outerLoopCount;
      sorted.set(true);
      brickSort(a, length, service);
      if (swapCount.intValue() >= maxComparisons) sorted.set(true);
    }
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "PMD.SystemPrintln"})
  protected void brickSort(long[] a, int length, ExecutorService service)
      throws InterruptedException, ExecutionException {
    int taskCount = computeOddPairCount(length) + computeEvenPairCount(length);
    List<Future<Void>> futures = new ArrayList<>(taskCount);
    BubbleTask bt = new BubbleTask(this, a, 0);
    int i = 0;
    for (int iterations = 0; i < length - 1; i += 2, iterations++) {
      ++innerLoopCount;
      ++comparisonCount;
      BubbleTask task = BubbleTask.createCopy(bt);
      task.i = i;
      futures.add(service.submit(task));
      if (iterations > 0) {
        ++comparisonCount;
        BubbleTask oddTask = BubbleTask.createCopy(bt);
        oddTask.i = i - 1;
        futures.add(service.submit(oddTask));
      }
    }
    if (isOdd(length)) {
      ++comparisonCount;
      BubbleTask oddTask = BubbleTask.createCopy(bt);
      oddTask.i = i - 1;
      futures.add(service.submit(oddTask));
    }
    assertEquality(futures.size(), taskCount);
    for (Future future : futures) future.get();
  }

  @Override
  protected void bubble(long[] a, int i) {
    if (swapIfGreaterThan(a, i, i + 1)) {
      swapCount.incrementAndGet();
      sorted.set(false);
    }
  }

  @Override
  public int getSwapCount() {
    return swapCount.intValue();
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
}
