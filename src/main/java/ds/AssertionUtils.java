package ds;

import java.util.concurrent.ExecutorService;

@SuppressWarnings("PMD.LawOfDemeter")
public final class AssertionUtils {

  private AssertionUtils() throws InstantiationException {
    throw new InstantiationException("Private constructor for: " + AssertionUtils.class.getName());
  }

  @Generated
  public static void assertEquality(int size, int count) {
    if (size != count) throw new AssertionError("Size is not the same as count.");
  }

  @Generated
  public static void assertServiceTerminated(ExecutorService service) {
    if (!service.isShutdown()) throw new AssertionError("ExecutorService must be shutdown.");
    if (!Thread.currentThread().isInterrupted() && !service.isTerminated())
      throw new AssertionError("ExecutorService must terminate cleanly.");
  }
}
