package ds;

public final class MathUtils {

  @Generated
  private MathUtils() throws InstantiationException {
    throw new InstantiationException("Private constructor for: " + MathUtils.class.getName());
  }

  public static boolean isOdd(int num) {
    return (num & 1) == 1;
  }
}
