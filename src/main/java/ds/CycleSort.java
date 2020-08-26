package ds;

public class CycleSort extends AbstractSort {

  private int iterateTillPositioned(int cycleStart, int length, long item, long... a) {
    int pos = cycleStart;
    for (int i = cycleStart + 1; i < length; ++i) {
      ++innerLoopCount;
      ++comparisonCount;
      if (a[i] < item) ++pos;
    }
    return pos;
  }

  private int iterateDuplicates(int startPos, long item, long... a) {
    int pos = startPos;
    while (item == a[pos]) {
      ++innerLoopCount;
      ++comparisonCount;
      ++pos;
    }
    return pos;
  }

  private long swapOutItem(long item, int pos, long... a) {
    long temp = a[pos];
    a[pos] = item;
    return temp;
  }

  @Override
  protected void sort(long[] a, int length) {
    {
      if (length < 0) throw new IllegalArgumentException("Illegal value for length: " + length);
      reset();
      if (length <= 1) return;
      for (int cycleStart = 0; cycleStart <= length - 2; ++cycleStart) {
        ++outerLoopCount;
        // initialize item as starting point
        long item = a[cycleStart];
        // Find position where we put the item. We basically
        // count all smaller elements on right side of item.
        int pos = iterateTillPositioned(cycleStart, length, item, a);
        // If item is already in correct position
        if (pos == cycleStart) continue;
        // ignore all duplicate elements
        pos = iterateDuplicates(pos, item, a);
        ++comparisonCount;
        // put the item to its right position
        if (pos != cycleStart) {
          item = swapOutItem(item, pos, a);
          ++copyCount;
        }
        // Rotate rest of the cycle
        while (pos != cycleStart) {
          pos = iterateTillPositioned(cycleStart, length, item, a);
          // ignore all duplicate elements
          while (item == a[pos]) {
            ++innerLoopCount;
            ++comparisonCount;
            ++pos;
          }
          ++comparisonCount;
          // put the item to its right position
          if (item != a[pos]) {
            item = swapOutItem(item, pos, a);
            ++copyCount;
          }
        }
      }
    }
  }
}
