package ds.tests;

import static org.joor.Reflect.*;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.joor.Reflect;

public class BaseTest {
  static {
    Field.class.getModule().addOpens(Field.class.getPackage().getName(), Reflect.class.getModule());
  }

  static int getModCount(Object arr) {
    return ((AtomicInteger) on(arr).get("modCount")).intValue();
  }

  static Stream<Integer> provideArraySize() {
    return Stream.of(10_000);
  }
}
