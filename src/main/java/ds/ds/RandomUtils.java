package ds;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class RandomUtils {

  private static final Random RANDOM = new Random();

  private RandomUtils() throws InstantiationException {
    throw new InstantiationException("Private constructor invoked for class: " + RandomUtils.class);
  }

  public static int randomInRange(int low, int high) {
    return RANDOM.nextInt(high - low) + low;
  }

  @Generated
  public static LongStream longStream() {
    return RANDOM.longs();
  }

  @Generated
  public static IntStream intStream() {
    return RANDOM.ints();
  }
}
