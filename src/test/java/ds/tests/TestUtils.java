package ds.tests;

import static org.joor.Reflect.*;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("PMD.LawOfDemeter")
public final class TestUtils {

  private TestUtils() {
    throw new IllegalStateException("Private constructor for " + TestUtils.class.getName());
  }

  static int getModCount(Object arr) {
    return ((AtomicInteger) on(arr).get("modCount")).intValue();
  }
}
