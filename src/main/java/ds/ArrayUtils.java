package ds;

public final class ArrayUtils {

  @Generated
  private ArrayUtils() throws InstantiationException {
    throw new InstantiationException("Private constructor for: " + ArrayUtils.class.getName());
  }

  public static boolean isSorted(long... a) {
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

  public static void swap(long[] a, int first, int second) {
    if (first < 0 || second < 0)
      throw new IllegalArgumentException("Invalid range specified: " + first + " - " + second);
    if (first > a.length - 1 || second > a.length - 1)
      throw new IllegalArgumentException("Invalid range specified: " + first + " - " + second);
    if (first == second) return;
    long temp = a[first];
    a[first] = a[second];
    a[second] = temp;
  }
}
