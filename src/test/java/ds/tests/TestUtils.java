package ds.tests;

import static org.joor.Reflect.*;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.joor.Reflect;

@SuppressWarnings("PMD.LawOfDemeter")
public final class TestUtils {
  static {
    Field.class.getModule().addOpens(Field.class.getPackageName(), Reflect.class.getModule());
    ReentrantReadWriteLock.WriteLock.class
        .getModule()
        .addOpens(
            ReentrantReadWriteLock.WriteLock.class.getPackageName(),
            nl.jqno.equalsverifier.internal.reflection.FieldAccessor.class.getModule());
    java.util.Random.class
        .getModule()
        .addOpens(
            java.util.Random.class.getPackageName(),
            nl.jqno.equalsverifier.internal.reflection.FieldAccessor.class.getModule());
  }

  private TestUtils() {
    throw new IllegalStateException("Private constructor for " + TestUtils.class.getName());
  }

  static int getModCount(Object arr) {
    return ((AtomicInteger) on(arr).get("modCount")).intValue();
  }
}
