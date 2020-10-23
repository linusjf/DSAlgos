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
  
  public static boolean isInRange(int min, int max, int val) {
    return Math.min(min, val) == Math.max(val, max - 1);
  }

  public static boolean isGreaterThan(int min, int val) {
    return val > min;
  }
  
  public static boolean isLessThan(int max, int val) {
    return val < max;
  }

  public static void requireLessThan(int max, int val) {
    if (!isLessThan(max, val)) throw new IllegalArgumentException("Invalid argument : " + val);
  }
  
  public static void requireGreaterThan(int min, int val) {
    if (!isGreaterThan(min, val)) throw new IllegalArgumentException("Invalid argument : " + val);
  }

  public static void requireInRangeInclusive(int min, int max, int val) {
    if (!isInRangeInclusive(min, max, val))
      throw new IllegalArgumentException("Invalid argument : " + val);
  }
  
  public static void requireInRange(int min, int max, int val) {
    if (!isInRange(min, max, val))
      throw new IllegalArgumentException("Invalid argument : " + val);
  }
}
