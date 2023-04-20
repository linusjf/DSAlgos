package ds;

public class ShellSortByThree extends AbstractSort {

  private static final int THREE = 3;

  protected int gapCount;

  @Override
  public int getTimeComplexity() {
    return innerLoopCount > 0 ? innerLoopCount : outerLoopCount > 0 ? outerLoopCount : gapCount;
  }

  @Override
  protected void reset() {
    super.reset();
    gapCount = 0;
  }

  @Override
  protected void sort(long[] a, int length) {
    reset();
    if (length <= 1) return;
    int n = length;
    int h = 1;
    while (h <= n / THREE) h = h * THREE + 1;

    // decreasing h, until h=1
    while (h > 0) {
      ++gapCount;
      for (int outer = h; outer < n; outer++) {
        long temp = a[outer];
        ++outerLoopCount;
        int inner = outer;
        while (inner > h - 1 && a[inner - h] >= temp) {
          ++innerLoopCount;
          ++comparisonCount;
          ++copyCount;
          System.arraycopy(a, inner - h, a, inner, 1);
          inner -= h;
        }
        if (inner > h - 1) ++comparisonCount;
        a[inner] = temp;
      }
      h = (h - 1) / THREE;
    }
  }
}
