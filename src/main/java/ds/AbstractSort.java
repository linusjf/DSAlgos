package ds;

public abstract class AbstractSort implements ISort {
  protected int comparisonCount;
  protected int swapCount;
  protected int copyCount;
  protected int innerLoopCount;
  protected int outerLoopCount;

  protected abstract void sort(long[] array, int length);

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public IArray sort(IArray array) {
    IArray copy = array.copy();
    sort(copy.get(), copy.count());
    return copy;
  }

  protected final void swap(long[] a, int first, int second) {
    if (first < 0 || second < 0)
      throw new IllegalArgumentException("Invalid range specified: " + first + " - " + second);
    if (first > a.length - 1 || second > a.length - 1)
      throw new IllegalArgumentException("Invalid range specified: " + first + " - " + second);
    if (first == second) return;
    long temp = a[first];
    a[first] = a[second];
    a[second] = temp;
  }

  protected void resetCounts() {
    copyCount = swapCount = comparisonCount = innerLoopCount = outerLoopCount = 0;
  }

  @Override
  public int getCopyCount() {
    return copyCount;
  }

  @Override
  public int getSwapCount() {
    return swapCount;
  }

  @Override
  public int getComparisonCount() {
    return comparisonCount;
  }

  @Override
  public int getTimeComplexity() {
    return innerLoopCount == 0 ? outerLoopCount : innerLoopCount;
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
        .append(outerLoopCount);
    return sb.toString();
  }
}
