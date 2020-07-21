package ds;

public final class ArrayUtils {

  private ArrayUtils() {
    throw new IllegalStateException("Private constructor for" + ArrayUtils.class.getName());
  }

  public static boolean isSorted(long[] a, int length) {
    for (int j = 0; j < length - 1; j++) {
      if (a[j] > a[j + 1]) {
        return false;
      }
    }
    return true;
  }

  public static boolean isSorted(OrdArray arr) {
    return isSorted(arr.get(), arr.count());
  }
}
