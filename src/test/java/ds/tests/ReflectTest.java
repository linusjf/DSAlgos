package ds.tests;

import java.lang.reflect.Field;
import org.joor.Reflect;

public class ReflectTest {
  static {
    Field.class.getModule().addOpens(Field.class.getPackage().getName(), Reflect.class.getModule());
  }
}
