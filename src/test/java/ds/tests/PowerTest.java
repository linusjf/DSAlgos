package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.Power;
import org.junit.jupiter.api.AfterEach;
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

  @AfterEach
  void teardown() {
    Power.resetIterations();
  }

  @Test
  @DisplayName("PowerTest.testConstructor")
  void testConstructor() {
    Power pow = new Power(2, SCORE);
    assertEquals(2, pow.getBase(), "Value equals constructor parameter.");
    assertEquals(SCORE, pow.getExponent(), "Value equals constructor parameter.");
  }

  @Test
  @DisplayName("PowerTest.testException")
  void testException() {
    assertThrows(IllegalArgumentException.class, () -> new Power(0, -1), "Exception expected.");
  }

  @Test
  @DisplayName("PowerTest.testZeroBase")
  void testZeroBase() {
    Power pow = new Power(0, 10);
    assertEquals(0, pow.compute(), "Zero value expected.");
    assertEquals(1, Power.iterationCount(), "Iterations must be one.");
  }
}
