package ds;

public class CycleSort extends AbstractSort {

  protected boolean sorted;

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
    {
      if (length < 0) throw new IllegalArgumentException("Illegal value for length: " + length);
      reset();
      if (length <= 1) {
        sorted = true;
        return;
      }
      for (int cycle_start = 0; cycle_start <= length - 2; ++cycle_start) {
        ++outerLoopCount;
        // initialize item as starting point
        long item = a[cycle_start];
        // Find position where we put the item. We basically
        // count all smaller elements on right side of item.
        int pos = cycle_start;
        for (int i = cycle_start + 1; i < length; ++i) {
          ++innerLoopCount;
          ++comparisonCount;
          if (a[i] < item) ++pos;
        }
        // If item is already in correct position
        if (pos == cycle_start) continue;
        // ignore all duplicate elements
        while (item == a[pos]) {
          ++innerLoopCount;
          ++comparisonCount;
          ++pos;
        }
        ++comparisonCount;
        // put the item to its right position
        if (pos != cycle_start) {
          long temp = item;
          item = a[pos];
          a[pos] = temp;
          ++copyCount;
        }
        // Rotate rest of the cycle
        while (pos != cycle_start) {
          pos = cycle_start;
          // Find position where we put the element
          for (int i = cycle_start + 1; i < length; ++i) {
            ++innerLoopCount;
            ++comparisonCount;
            if (a[i] < item) ++pos;
          }
          // ignore all duplicate elements
          while (item == a[pos]) {
            ++innerLoopCount;
            ++comparisonCount;
            ++pos;
          }
          ++comparisonCount;
          // put the item to it's right position
          if (item != a[pos]) {
            long temp = item;
            item = a[pos];
            a[pos] = temp;
            ++copyCount;
          }
        }
      }
    }
    sorted = true;
  }
}
