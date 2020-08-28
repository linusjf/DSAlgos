package ds;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public final class ExecutorUtils {

  private ExecutorUtils() throws InstantiationException {
    throw new InstantiationException("Private constructor for: " + ExecutorUtils.class.getName());
  }

  public static void terminateExecutor(ExecutorService service, long timeUnits, TimeUnit unit) {
    service.shutdown();
    try {
      if (!service.awaitTermination(timeUnits, unit)) service.shutdownNow();
    } catch (InterruptedException ie) {
      service.shutdownNow();
    }
    assertServiceTerminated(service);
  }

  @Generated
  private static void assertServiceTerminated(ExecutorService service) {
    if (!service.isTerminated())
      throw new AssertionError("ExecutorService must terminate cleanly.");
  }
}
