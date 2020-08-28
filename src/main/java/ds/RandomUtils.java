package ds;

import java.util.Random;

public final class RandomUtils {

  private RandomUtils() throws InstantiationException {
    throw new InstantiationException("Private constructor invoked for class: " + RandomUtils.class);
  }

  public static int randomInRange(int low, int high) {
    Random rand = new Random();
    return rand.nextInt(high - low) + low;
  }
}
