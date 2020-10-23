package ds;

public final class MathUtils {

  @Generated
  private MathUtils() throws InstantiationException {
    throw new InstantiationException("Private constructor for: " + MathUtils.class.getName());
  }

  public static boolean isOdd(int num) {
    return (num & 1) == 1;
  }

  public static boolean isInRangeInclusive(int min, int max, int val) {
    return Math.min(min, val) == Math.max(val, max);
  }

  public static boolean isGreaterThan(int min, int val) {
    return val > min;
  }

  public static void requireGreaterThan(int min, int val) {
    if (isGreaterThan(min, val)) throw new IllegalArgumentException("Invalid argument : " + val);
  }

  public static void requireInRangeInclusive(int min, int max, int val) {
    if (!isInRangeInclusive(min, max, val))
      throw new IllegalArgumentException("Invalid argument : " + val);
  }
}
