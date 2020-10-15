package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.Power;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("PowerTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class PowerTest {
  private static final String EXCEPTION_EXPECTED = "Exception expected.";

  @Test
  @DisplayName("PowerTest.testConstructor")
  void testConstructor() {
    Power pow = new Power(2, SCORE);
    assertEquals(2, pow.getBase(), "Value equals constructor parameter.");
    assertEquals(SCORE, pow.getExponent(), "Value equals constructor parameter.");
  }
}
