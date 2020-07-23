package ds.tests;

import static org.joor.Reflect.*;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import org.joor.Reflect;

@SuppressWarnings("PMD.LawOfDemeter")
public final class TestUtils {
  static {
    Field.class.getModule().addOpens(Field.class.getPackageName(), Reflect.class.getModule());
  }

  private TestUtils() {
    throw new IllegalStateException("Private constructor for " + TestUtils.class.getName());
  }

  static int getModCount(Object arr) {
    return ((AtomicInteger) on(arr).get("modCount")).intValue();
  }
  
  static boolean getSorted(Object arr) {
    return (boolean) on(arr).get("sorted");
  }
}
