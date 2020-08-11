package ds.tests;

import org.checkerframework.checker.formatter.qual.ConversionCategory;
import org.checkerframework.checker.formatter.qual.Format;

public final class TestConstants {

  static final @Format(ConversionCategory.INT) String NOT_AVAILABLE = "%d not available";

  static final String MOD_COUNT = "modCount";

  static final String STRICT = "strict";

  static final String WRITE = "w";

  static final String LOCK = "lock";
  
  static final String INITIAL_VALUE_ZERO = "Initial value must be zero.";

  private TestConstants() {
    // empty constructor
  }
}
