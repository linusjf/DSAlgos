package ds;

public class Power {
  private final int base;
  private final int exponent;

  public Power(int base, int exponent) {
    if (exponent < 0 && base == 0)
      throw new IllegalArgumentException(
          "It's impossible to raise 0 to the power of a negative number.");
    this.base = base;
    this.exponent = exponent;
  }

  public int getBase() {
    return base;
  }

  public int getExponent() {
    return exponent;
  }

  public double compute() {
    if (exponent == 0) return 1.0f;
    if (base == 0) return 0;
    if (exponent < 0) return 1.0f / new Power(base, -1 * exponent).compute();
    else {
      double powerOfHalfN = new Power(base, exponent >> 1).compute();
      double[] factor = {1f, base};
      return factor[exponent % 2] * powerOfHalfN * powerOfHalfN;
    }
  }
}
