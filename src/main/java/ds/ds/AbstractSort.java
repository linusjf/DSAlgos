package ds;

public abstract class AbstractSort implements ISort {
  protected int comparisonCount;
  protected int swapCount;
  protected int copyCount;
  protected int innerLoopCount;
  protected int outerLoopCount;

  protected boolean shouldSort(int length) {
    if (length < 0) throw new IllegalArgumentException("Illegal value for length: " + length);
    reset();
    return length <= 1 ? false : true;
  }

  protected abstract void sort(long[] array, int length);

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public IArray sort(IArray array) {
    IArray copy = array.copy();
    sort(copy.get(), copy.count());
    return copy;
  }

  protected final boolean swap(long[] a, int first, int second) {
    return ArrayUtils.swap(a, first, second);
  }

  protected void reset() {
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
    StringBuilder sb = new StringBuilder(80);
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
