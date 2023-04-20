package ds;

import static ds.ArrayUtils.swapIfGreaterThan;
import static ds.MathUtils.isOdd;

/** Not thread-safe with state variables. */
public class BrickSortMaxMin extends AbstractBrickSort {

  protected boolean sorted;

  @Override
  public boolean isSorted() {
    return sorted;
  }

  @Override
  protected void reset() {
    super.reset();
    sorted = false;
  }

  @Override
  protected void sort(long[] a, int length) {
    reset();
    if (length <= 1) {
      sorted = true;
      return;
    }
    final int maxComparisons = computeMaxComparisons(length);
    while (!sorted) {
      ++outerLoopCount;
      sorted = true;
      brickSort(a, length);
      if (swapCount == maxComparisons) sorted = true;
    }
  }

  protected void brickSort(long[] a, int length) {
    int i = 0;
    for (int iterations = 0; i < length - 1; i += 2, iterations++) {
      ++innerLoopCount;
      bubble(a, i);
      if (iterations > 0) bubble(a, i - 1);
    }
    if (isOdd(length)) bubble(a, i - 1);
  }

  @Override
  protected void bubble(long[] a, int i) {
    ++comparisonCount;
    if (swapIfGreaterThan(a, i, i + 1)) {
      sorted = false;
      ++swapCount;
    }
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
