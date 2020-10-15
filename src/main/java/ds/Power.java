package ds;

public class Power {
  private static int iterations = 0;
  private int base;
  private int exponent;

  public Power(int base, int exponent) {
    if (exponent < 0 && base == 0)
      throw new IllegalArgumentException(
          "It's impossible to raise 0 to the power of a negative number.");
    this.base = base;
    this.exponent = exponent;
    ++iterations;
  }

  public double compute() {
    if (exponent == 0) return 1.0f;
    if (exponent < 0) return 1.0f / new Power(base, -1 * exponent).compute();
    else {
      double powerOfHalfN = new Power(base, exponent >> 1).compute();
      double[] factor = {1f, base};
      return factor[exponent % 2] * powerOfHalfN * powerOfHalfN;
    }
  }

  public static int iterationCount() {
    return iterations;
  }

  public static void resetIterations() {
    iterations = 0;
  }
}
