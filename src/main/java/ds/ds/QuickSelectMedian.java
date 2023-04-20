package ds;

import static ds.MathUtils.isOdd;

public class QuickSelectMedian extends QuickSelect {

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  public QuickSelectMedian(long... arr) {
    super(arr);
  }

  public double find() {
    if (array.length == 0) return Double.NaN;
    if (array.length == 1) return array[0];
    int mid = array.length >> 1;
    if (isOdd(array.length)) return find(mid);
    return 0.5f * (find(mid) + find(mid - 1));
  }
}
