package ds.tests;

import static org.joor.Reflect.*;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("PMD.LawOfDemeter")
public final class TestUtils {
  static {
    try {
      Class<?> syncClass = Class.forName("java.util.concurrent.locks.ReentrantReadWriteLock.Sync");
      syncClass
          .getModule()
          .addOpens(
              syncClass.getPackageName(),
              nl.jqno.equalsverifier.internal.reflection.FieldAccessor.class.getModule());
    } catch (ClassNotFoundException cnfe) {
      System.err.println(cnfe.getMessage());
    }
  }

  private TestUtils() {
    throw new IllegalStateException("Private constructor for " + TestUtils.class.getName());
  }

  static int getModCount(Object arr) {
    return ((AtomicInteger) on(arr).get("modCount")).intValue();
  }
}
