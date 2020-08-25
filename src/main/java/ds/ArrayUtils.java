package ds;

public final class ArrayUtils {

  @Generated
  private ArrayUtils() {
    throw new IllegalStateException("Private constructor for" + ArrayUtils.class.getName());
  }
  
  public static boolean isSorted(long[] a) {
    return isSorted(a, a.length);
  }

  public static boolean isSorted(long[] a, int length) {
    if (length < 0 || length > a.length)
      throw new IllegalArgumentException("Length " + length + " is invalid.");
    for (int j = 0; j < length - 1; j++) {
      if (a[j] > a[j + 1]) {
        return false;
      }
    }
    return true;
  }

  public static boolean isSorted(IArray arr) {
    return isSorted(arr.get(), arr.count());
  }
}
